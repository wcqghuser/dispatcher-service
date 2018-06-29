package com.qingclass.dispatcher.global.resource

object PointCodeEnum extends Enumeration {

  type PointCodeEnum = Value

  val SUBSCRIBE: Value = Value(10101, "subscribe_official_account")

  val UNSUBSCRIBE: Value = Value(10102, "unsubscribe_official_account")

  val SCAN: Value = Value(10103, "user_scan_event")

}
