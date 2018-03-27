
name := "twitter-sentiment-analysis"

version := "0.1"

scalaVersion := "2.12.5"

resolvers ++= Seq(
  "MavenCentral" at "http://repo1.maven.org/maven2"
)
libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "5.5",
  "edu.stanford.nlp"%"stanford-corenlp"% "3.9.1",
  "edu.stanford.nlp"%"stanford-corenlp"% "3.9.1" classifier "models"
)