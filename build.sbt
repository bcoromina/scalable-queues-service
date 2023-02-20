ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "scalable-queues-service"
  )

libraryDependencies += "com.disneystreaming" %% "weaver-cats" % "0.8.1" % Test
testFrameworks += new TestFramework("weaver.framework.CatsEffect")