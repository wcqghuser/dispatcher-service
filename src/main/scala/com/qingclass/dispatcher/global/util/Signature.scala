package com.qingclass.dispatcher.global.util

import scala.util.Random

object Signature {

  def checkSignature(signature: String, timestamp: String, nonce: String, token: String): Boolean = {

    val array = List(token, timestamp, nonce).sorted
    val tmpStr: String = array.mkString("")
    val codec = DigestUtil.sha1(tmpStr)

    if (codec == signature) true
    else false

  }

  def packSignature(token: String) = {

    val timestamp: Long = System.currentTimeMillis / 1000
    val nonce = Random.nextLong()

    val array = List(token, timestamp.toString, nonce.toString).sorted
    val tmpStr = array.mkString("")
    val codec = DigestUtil.sha1(tmpStr)

    new SignedSignature(codec, timestamp.toString, nonce.toString, token)
  }

//  def main(args: Array[String]) {
//    val signature = "fb584aea5e465613e6f5817ebe52fa8bc8b656d7"
//    val timestamp = "1506496557"
//    val nonce = "141584541"
//    val token = "U5aOqMg9tSseDcGN0xyD5W21hhDZ8om8HjmrNPmGStV"
//    val result = checkSignature(signature, timestamp, nonce, token)
//    println(s"result=$result")
//  }

}

case class SignedSignature(signature: String, timestamp: String, nonce: String, token: String)