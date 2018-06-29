package com.qingclass.dispatcher.global.config

import scalacache.ScalaCache
import scalacache.guava.GuavaCache

trait QingClassCache {
  implicit val scalaCache = ScalaCache(GuavaCache())
}
