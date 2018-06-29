package com.qingclass.dispatcher.global.exception

import com.google.inject.{Inject, Singleton}
import com.qingclass.dispatcher.global.config.ServerConfig
import com.qingclass.dispatcher.global.util.SysLogging
import com.qingclass.dispatcher.v1.controller.common.ApiResponse
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

@Singleton
class ServiceExceptionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[ServiceException] with SysLogging {

  override def toResponse(request: Request, exception: ServiceException): Response = {
    error("ServiceException", exception.getMessage)
    response.internalServerError(ApiResponse.http5xx(ServerConfig.SERVICE_NAME.concat(s"(${exception.getMessage})")))
  }
}

class ServiceException(msg: String = "service exception") extends Exception(msg)