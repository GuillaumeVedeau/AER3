name := "AER"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaCore
)     

play.Project.playJavaSettings
