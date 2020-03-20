import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.0.0-SNAPSHOT"
ThisBuild / organization     := "com.phisuite"
ThisBuild / organizationName := "phisuite"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

test in assembly := {}
assemblyJarName in assembly := "event-handler.jar"
assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}

lazy val root = (project in file("."))
  .settings(
    name := "Event Handler",
    libraryDependencies ++= Seq(
      logback,
      scalaLog,
      scalaTest % Test,
      scalaPB % "protobuf",
      scalaGRPC,
      javaGRPC
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
