name := """JKL_Fitbuddy"""
organization := "ch.ntb"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

libraryDependencies += guice



libraryDependencies ++= Seq(
  // for bootstrap
  "org.webjars" % "bootstrap" % "4.0.0",
  "org.webjars" % "jquery" % "3.2.1",
  "org.webjars" % "popper.js" % "1.12.9",

  // routing for single page app
  "org.webjars" % "sammy" % "0.7.4",

  // Icons
  "org.webjars" % "material-design-icons" % "3.0.1",
  "org.webjars" % "font-awesome" % "5.3.1",

  // leaflet map with tooltips
  "org.webjars" % "leaflet" % "1.3.1",

  // Vue.js
  "org.webjars" % "vue" % "2.5.16",
    evolutions,
    javaJdbc,
    javaJpa,
    "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
    "com.h2database" % "h2" % "1.4.192"
  )

herokuAppName in Compile := "jklfitbuddy"
