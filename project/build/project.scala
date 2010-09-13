import sbt._

class HudsonGenProject(info: ProjectInfo) extends DefaultProject(info) {
  
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"

  lazy val scalate = "org.fusesource.scalate" % "scalate-core" % "1.3-SNAPSHOT"
  lazy val slf4j = "org.slf4j" % "slf4j-jdk14" % "1.6.1"

}

