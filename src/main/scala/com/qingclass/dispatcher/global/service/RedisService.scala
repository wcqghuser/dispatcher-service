package com.qingclass.dispatcher.global.service

import com.qingclass.dispatcher.global.config.EnvironmentConfig
import com.qingclass.dispatcher.global.util.SysLogging
import com.redis.{RedisClient, RedisClientPool}

object RedisService extends SysLogging {

  val server: String = EnvironmentConfig.getString("redis.server")
  val port: Int = EnvironmentConfig.getInt("redis.port")
  val maxIdle: Int = EnvironmentConfig.getInt("redis.maxIdle")
  val database = EnvironmentConfig.getInt("redis.database")
  val secret = EnvironmentConfig.getString("redis.secret")
  val setSecret = if (secret.isEmpty) None else Some(secret)

  val clients: RedisClientPool = new RedisClientPool(server, port, maxIdle, database, setSecret)

  def withRedisClient[T](body: RedisClient => T) = clients.withClient(body)
}
