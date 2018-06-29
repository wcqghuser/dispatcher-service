package com.qingclass.dispatcher.global.service

import java.util.UUID

import com.qingclass.dispatcher.global.config.ServerConfig
import com.qingclass.dispatcher.global.domain.HealthResult
import com.qingclass.dispatcher.global.util.SysLogging

/**
  * Created by wangchaoqun on 17/8/18.
  */
class InternalService extends SysLogging {

  def internalHealth: HealthResult = {
    val errMsg = new StringBuilder()

    //mysql
    //redis
    if (!checkConnection(Category.REDIS)) errMsg.append(" redis connection failure")
    //mongo
    //线程数
    val threads = Thread.activeCount()
    if (errMsg.nonEmpty) HealthResult(ServerConfig.SERVICE_NAME, "error", threads, errMsg.toString())
    else HealthResult(ServerConfig.SERVICE_NAME, "OK", threads)
  }

  private def checkConnection(category: String) = {
    try {
      category match {
        case Category.MYSQL => false
        case Category.REDIS => RedisService.withRedisClient(client => !client.exists(UUID.randomUUID.toString))
        case Category.MONGO => false
        case _ => false
      }
    } catch {
      case e: Exception => error("internal health error", e)
        false
    }
  }
}

object Category {
  val MYSQL = "mysql"
  val REDIS = "redis"
  val MONGO = "mongo"
}