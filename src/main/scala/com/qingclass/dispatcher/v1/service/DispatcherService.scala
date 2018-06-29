package com.qingclass.dispatcher.v1.service

import com.qingclass.dispatcher.global.config.{EventTypeConfig, MessageTypeConfig}
import com.qingclass.dispatcher.global.resource.PointCodeEnum
import com.qingclass.dispatcher.global.service.MQService
import com.qingclass.dispatcher.global.util.JsonUtil._
import com.qingclass.dispatcher.global.util.{RestClient, Signature, SysLogging}
import com.qingclass.dispatcher.v1.cache.WechatAccountCache
import com.rabbitmq.client.MessageProperties

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.xml.Elem

class DispatcherService extends SysLogging {

  def processingMsg(signature: String, timestamp: String, nonce: String, body: String): String = {
    val ele = scala.xml.XML.loadString(body)
    val wechatAccountId = (ele \ "ToUserName").text
    val wechatAccount = WechatAccountCache.getWechatAccount(wechatAccountId)
    val token = wechatAccount.token
    val url = wechatAccount.forwardingUrl
    val appId = wechatAccount.appId
    if (Signature.checkSignature(signature, timestamp, nonce, token)) {
      forwardMsg(url, body)
      dispatcherMsg(ele, appId)
    } else {
      ""
    }
  }

  private def forwardMsg(url: String, body: String) = {
    if (url.nonEmpty) {
      Future {
        val header = Some(Seq(("Content-Type", "application/xml")))
        RestClient.post(url, body, header)
      }
    }
  }

  private def dispatcherMsg(ele: Elem, appId: String) = {
    val createTime = (ele \ "CreateTime").text
    val openId = (ele \ "FromUserName").text
    val messageType = (ele \ "MsgType").text
    val wechatAccountId = (ele \ "ToUserName").text
    val eventType = if ((ele \ "Event").isEmpty) None else Some((ele \ "Event").text)
    val content = if ((ele \ "Content").isEmpty) None else Some((ele \ "Content").text)
    val mediaId = if ((ele \ "MediaId").isEmpty) None else Some((ele \ "MediaId").text)
    info(new UserMessageRecord(appId, wechatAccountId, openId, createTime, messageType, eventType, content, mediaId))
    messageType match {
      case MessageTypeConfig.EVENT => dispatcherEventMsg(ele, appId)
      case _ => ""
    }

  }

  private def dispatcherEventMsg(ele: Elem, appId: String) = {
    val openId = (ele \ "FromUserName").text
    val eventType = (ele \ "Event").text.toLowerCase

    eventType match {
      case EventTypeConfig.SUBSCRIBE =>
        val ticket = (ele \ "Ticket").text
        val way = if (ticket.isEmpty) "normal" else "scan"
        info(s"code=${PointCodeEnum.SUBSCRIBE.id}, " +
          s"key=${PointCodeEnum.SUBSCRIBE.toString}, " +
          s"appId=$appId, " +
          s"openId=$openId, " +
          s"ticket=$ticket, " +
          s"desc=User subscribe by ${way}")
        val message = Map("appId" -> appId, "openId" -> openId, "ticket" -> ticket).toJsonString
        add2MQ(eventType, message)
        ""
      case EventTypeConfig.UNSUBSCRIBE =>
        info(s"code=${PointCodeEnum.UNSUBSCRIBE.id}, " +
          s"key=${PointCodeEnum.UNSUBSCRIBE.toString}, " +
          s"appId=$appId, " +
          s"openId=$openId, " +
          s"desc=User unsubscribe")
        val message = Map("appId" -> appId, "openId" -> openId).toJsonString
        add2MQ(eventType, message)
        ""
      case EventTypeConfig.SCAN =>
        val ticket = (ele \ "Ticket").text
        info(
          s"code=${PointCodeEnum.SCAN.id}, " +
            s"key=${PointCodeEnum.SCAN.toString}, " +
            s"appId=$appId, " +
            s"openId=$openId, " +
            s"ticket=$ticket, " +
            s"desc=User scan"
        )
        val message = Map("appId" -> appId, "openId" -> openId, "ticket" -> ticket).toJsonString
        add2MQ(eventType, message)
        ""
      case _ => ""
    }
  }

  private def add2MQ(messageType: String, message: String): Unit = {
    MQService.getConnection { connection =>
      val channel = connection.createChannel()
      val headers = MQService.getXHaPolicyConf
      channel.queueDeclare(messageType, true, false, false, headers)
      channel.basicPublish("", messageType, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"))
      channel.close()
    }
  }

}

case class UserMessageRecord(
                              appId: String,
                              wechatAccountId: String,
                              openid: String,
                              createTime: String,
                              messageType: String,
                              eventType: Option[String],
                              content: Option[String],
                              mediaId: Option[String]
                            ) {
  override def toString = {
    val sb = new StringBuilder(512, "UserMessageRecord")
    sb.append("[")
      .append("appId=").append(appId)
      .append(",wechatAccountId=").append(wechatAccountId)
      .append(",openid=").append(openid)
      .append(",createTime=").append(createTime)
      .append(",messageType=").append(messageType)
    if (eventType.nonEmpty) sb.append(",eventType=").append(eventType.get)
    if (content.nonEmpty) sb.append(",content=").append(content.get)
    if (mediaId.nonEmpty) sb.append(",mediaId=").append(mediaId.get)
    sb.append("]")
    sb.toString()
  }
}
