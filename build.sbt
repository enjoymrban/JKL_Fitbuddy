name := """JKL_Fitbuddy"""
organization := "ch.ntb"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

libraryDependencies += guice


libraryDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "4.0.0",
  "org.webjars" % "jquery" % "3.2.1",
  "org.webjars" % "popper.js" % "1.12.9",
  "org.webjars" % "sammy" % "0.7.4",
  "org.webjars" % "material-design-icons" % "3.0.1"
)
