package com.qingclass.dispatcher.v1.cache

import com.qingclass.dispatcher.global.config.{CacheName, QingClassCache}
import com.qingclass.dispatcher.v1.domain.WechatAccount
import com.qingclass.dispatcher.v1.manager.TokenManager

import scala.concurrent.duration._
import scalacache._

object WechatAccountCache extends QingClassCache {

    def getWechatAccount(wechatAccountId: String): WechatAccount = {
      val key = CacheName.WECHAT_ACCOUNT.format(wechatAccountId)
      val wechatAccount = sync.get(key).asInstanceOf[Option[WechatAccount]]
      wechatAccount match {
        case Some(account) => account
        case _ => loadWechatAccount(wechatAccountId)
      }
    }

    def loadWechatAccount(wechatAccountId: String): WechatAccount = {
      val key = CacheName.WECHAT_ACCOUNT.format(wechatAccountId)
      val wechatAccount = TokenManager.getWechatAccount(wechatAccountId)
      put(key)(wechatAccount, Some(5 minutes))
      wechatAccount
    }

}

