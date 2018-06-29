package com.qingclass.dispatcher.v1.controller.common

import com.qingclass.dispatcher.v1.resource.StatusCodeEnum
import com.qingclass.dispatcher.v1.service.common.BaseDTO
import com.qingclass.dispatcher.global.util.SysLogging

object ApiResponse extends SysLogging {
  def success = BaseDTO(StatusCodeEnum.SUCCESS.id, StatusCodeEnum.SUCCESS.toString)

  def success(map: Map[String, Any]) = BaseDTO(StatusCodeEnum.SUCCESS.id, StatusCodeEnum.SUCCESS.toString, map)

  def http4xx(error: String) = BaseDTO(StatusCodeEnum.HTTP_4XX.id, StatusCodeEnum.HTTP_4XX.toString, error)

  def http5xx(error: String) = BaseDTO(StatusCodeEnum.HTTP_5XX.id, StatusCodeEnum.HTTP_5XX.toString, error)
}
