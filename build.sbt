name := "Areca"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies ++= Seq(
 "com.github.scopt" %% "scopt" % "3.1.0"
// "javax.time" % "jsr-310-ri" % "20100212"
)
