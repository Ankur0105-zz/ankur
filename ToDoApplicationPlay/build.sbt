name := """ToDoApplicationPlay"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.1",
  "org.webjars" % "swagger-ui" % "2.2.0"
)

routesGenerator := InjectedRoutesGenerator

swaggerDomainNameSpaces := Seq("model")