Nice.scalaProject

name          := "sequences"
organization  := "ohnosequences"
description   := "sequences project"

bucketSuffix  := "era7.com"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % Test

libraryDependencies ++=
  Seq(
    "ohnosequences" %% "cosas" % "0.7.0-SNAPSHOT"
  )
