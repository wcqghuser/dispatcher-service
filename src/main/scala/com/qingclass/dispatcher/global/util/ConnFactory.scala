package com.qingclass.dispatcher.global.util

import com.rabbitmq.client.{Connection, ConnectionFactory}
import org.apache.commons.pool.PoolableObjectFactory
import org.apache.commons.pool.impl.GenericObjectPool

private class ConnFactory(host: String, username: String, password: String, virtualHost: String)
  extends PoolableObjectFactory[Connection] {
  override def destroyObject(conn: Connection): Unit = conn.close()

  override def validateObject(conn: Connection): Boolean = conn.isOpen

  override def activateObject(conn: Connection): Unit = {}

  override def passivateObject(conn: Connection): Unit = {}

  override def makeObject(): Connection = factory.newConnection()

  val factory = new ConnectionFactory() {
    this.setUsername(username)
    this.setPassword(password)
    this.setHost(host)
    this.setVirtualHost(virtualHost)
    //尝试每10秒恢复一次
    this.setNetworkRecoveryInterval(10000)
  }
}

object ConnectionPool {
  val UNLIMITED_CONNECTIONS = -1

  val WHEN_EXHAUSTED_BLOCK = GenericObjectPool.WHEN_EXHAUSTED_BLOCK
  val WHEN_EXHAUSTED_FAIL = GenericObjectPool.WHEN_EXHAUSTED_FAIL
  val WHEN_EXHAUSTED_GROW = GenericObjectPool.WHEN_EXHAUSTED_GROW
}

class ConnectionPool(host: String, username: String, password: String, virtualHost: String) {
  val pool = new GenericObjectPool[Connection](new ConnFactory(host, username, password, virtualHost))

  def getConnection[T](body: Connection => T) = {
    val connection = pool.borrowObject()
    try {
      body(connection)
    } finally {
      pool.returnObject(connection)
    }
  }

  def close = pool.close
}
