package com.qingclass.dispatcher.global.resource

trait BaseStatusCodeEnum extends Enumeration {

  type StatusCodeEnum = Value

  val SUCCESS: Value = Value(0, "success")

  val HTTP_4XX: Value = Value(400, "")

  val HTTP_5XX: Value = Value(500, "")

}
