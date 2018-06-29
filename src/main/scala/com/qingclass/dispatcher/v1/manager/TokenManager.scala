package com.qingclass.dispatcher.v1.manager

import com.qingclass.dispatcher.global.config.EnvironmentConfig
import com.qingclass.dispatcher.v1.domain.WechatAccount
import com.qingclass.dispatcher.global.util.RestClient
import com.qingclass.dispatcher.global.util.JsonUtil._

object TokenManager {

  def getWechatAccount(wechatAccountId: String): WechatAccount = {
    val url = WECHAT_ACCOUNT.replace(REAPLACE_ACCOUNT_ID, wechatAccountId)
    val responseMap = RestClient.getAny(url)

    val map = responseMap("data").toJsonObjectString.toJsonMap[Any]
    val wechatAccountInfo = map("wechat_account").toJsonObjectString.toJsonMap[Any]
    val token = wechatAccountInfo("token").toString
    val forwardingUrl = wechatAccountInfo("forwarding_url").toString
    val appId = wechatAccountInfo("app_id").toString

    new WechatAccount(token, forwardingUrl, appId)
  }
  private val REAPLACE_ACCOUNT_ID = "WECHATACCOUNTID"
  private val WECHAT_ACCOUNT = EnvironmentConfig.getString("service.account.wechat_account")
}