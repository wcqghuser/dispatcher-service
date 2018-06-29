package com.qingclass.dispatcher.global.util

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.util.{Calendar, Date}

object TimeUtil {

  def getCurrentDateTime(): String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())

  def getStudentCardExpTime(): String = {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, 30)
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime)
  }

  def getCurrentDayInSlash(): String = new SimpleDateFormat(DATE_FORMAT_INSLASH).format(new Date())

  def getCurrentDay(): String = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime())

  def getYesterdayDay(): String = {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1)
    new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime())
  }

  def parseToDate(dateStr: String) = new SimpleDateFormat(DATE_FORMAT).parse(dateStr)

  def parseToDateFull(dateStr: String) = new SimpleDateFormat(DATE_FULL_FORMAT).parse(dateStr)

  def parseToString(date: Date) = new SimpleDateFormat(DATE_FORMAT).format(date)

  def parseToStringFull(date: Date) = new SimpleDateFormat(DATE_FULL_FORMAT).format(date)

  def formatLocalDateTime(dateTime: LocalDateTime): String = dateTime.format(DateTimeFormatter.ofPattern(DATE_FULL_FORMAT))

  def formatLocalDate(dateTime: LocalDate): String = dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

  def formatLocalTime(dateTime: LocalTime): String = dateTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT))

  def formatDateTimestamp(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern(Date_Timestamp))

  val Date_Timestamp: String = "yyyyMMdd"
  val DATE_FORMAT: String = "yyyy-MM-dd"
  val DATE_FORMAT_INSLASH: String = "yyyy/MM/dd"
  val DATE_FULL_FORMAT: String = "yyyy-MM-dd HH:mm:ss"
  val TIME_FORMAT: String = "HH:mm:ss"
}
