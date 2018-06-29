name := "dispatcher-service"

version := "1.0"

scalaVersion := "2.12.3"

val versions = new {
  val FINATRA = "2.11.0"
  val SCALA_CACHE = "0.9.4"
  val BIJECTION = "0.9.5"
  val RABBITMQ = "4.2.0"
}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.FINATRA,
  "com.twitter" %% "finatra-slf4j" % versions.FINATRA,
  "com.github.cb372" %% "scalacache-core" % versions.SCALA_CACHE,
  "com.github.cb372" %% "scalacache-guava" % versions.SCALA_CACHE,
  "com.twitter" %% "bijection-core" % versions.BIJECTION,
  "com.twitter" %% "bijection-util" % versions.BIJECTION,
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "net.debasishg" %% "redisclient" % "3.4",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe" % "config" % "1.3.1",
  "com.squareup.okhttp3" % "okhttp" % "3.8.1",
  "com.squareup.okio" % "okio" % "1.13.0",
  "commons-codec" % "commons-codec" % "1.10",
  "org.mockito" % "mockito-all" % "1.10.19",
  "com.rabbitmq" % "amqp-client" % "4.2.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "twttr maven" at "http://maven.twttr.com"
