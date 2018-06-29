package com.qingclass.dispatcher.global.filters

import com.qingclass.dispatcher.global.util.{KMP, SysLogging}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

class TemplateCallbackFilter extends SimpleFilter[Request, Response] with SysLogging {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    if (KMP.searchTSJF(request.contentString) != -1) {
      try {
        val body = request.contentString.replaceAll("\r|\n", "")
        logger.info(s"send message callback, msg=$body")
      }
      Future.apply(Response.apply())
    } else service(request)
  }

}