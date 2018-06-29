package com.qingclass.dispatcher.v1.domain

trait AbstractPassiveMessage {
  val ToUserName: String
  val FromUserName: String
  val CreateTime: String
  val MsgType: String
  val MsgId: String
}

abstract case class AbstractPassiveTextMessage(Content: String, raw: String = "") extends AbstractPassiveMessage

abstract case class AbstractPassivePictureMessage(PicUrl: String, MediaId: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveVoiceMessage(MediaId: String, Format: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveVideoMessage(MediaId: String, ThumbMediaId: String, Title: String, Description: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveImageMessage(MediaId: String, PicUrl: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveThumbMediaMessage(MediaId: String, ThumbMediaId: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveGeolocationMessage(Location_X: String, Location_Y: String, Scale: String, Label: String) extends AbstractPassiveMessage

abstract case class AbstractPassiveLinkMessage(Title: String, Description: String, Url: String, pic: String) extends AbstractPassiveMessage