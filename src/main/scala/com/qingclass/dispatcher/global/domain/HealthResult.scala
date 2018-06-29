package com.qingclass.dispatcher.global.domain

case class HealthResult(name: String, status: String, threads: Int, message: String = "")
