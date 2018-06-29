package com.qingclass.dispatcher.global.controller

import com.google.inject.Inject
import com.qingclass.dispatcher.global.service.InternalService
import com.qingclass.dispatcher.v1.controller.common.ApiResponse
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class InternalController @Inject()(internalService: InternalService) extends Controller {

  get("/internal/health") { request: Request =>
    val result = internalService.internalHealth
    ApiResponse.success(Map("result" -> result))
  }

}
