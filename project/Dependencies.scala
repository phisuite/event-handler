import sbt._

import scalapb.compiler.Version.scalapbVersion
import scalapb.compiler.Version.grpcJavaVersion

object Dependencies {
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val scalaLog = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val scalaPB = "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion
  lazy val scalaGRPC = "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapbVersion
  lazy val javaGRPC = "io.grpc" % "grpc-netty" % grpcJavaVersion
}
