package com.qingclass.dispatcher.v1.controller

import com.google.inject.Inject
import com.qingclass.dispatcher.global.util.SysLogging
import com.qingclass.dispatcher.v1.service.DispatcherService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class DispatcherController @Inject()(dispatcherService: DispatcherService) extends Controller with SysLogging {

  get("/dispatcher") { request: Request =>
    request.params("echostr")
  }

  post("/dispatcher") { request: Request =>
    val signature = request.params("signature")
    val timestamp = request.params("timestamp")
    val nonce = request.params("nonce")
    val body = request.getContentString()

    dispatcherService.processingMsg(signature, timestamp, nonce, body)
  }
}
