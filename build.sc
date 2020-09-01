import ammonite.ops._
import mill._
import mill.define.Target
import mill.scalajslib._
import mill.scalalib._
import mill.util.Ctx
import coursier.maven.MavenRepository

object Config {
  def scalaVersion = "2.13.3"
  def scalaJSVersion = "1.1.0"
  def laminarVersion = "0.10.2"
  def covenantVersion = "3d41e5f"
  def akkaHttpVersion = "10.2.0"
  def akkaStreamVersion = "2.6.8"
  def boopickleVersion = "1.3.3"

  def sharedDependencies = Agg(
    ivy"io.suzaku::boopickle::$boopickleVersion",
    ivy"com.github.cornerman.covenant::covenant-http::$covenantVersion"
  )

  def jvmDependencies = Agg(
    ivy"com.typesafe.akka::akka-http:$akkaHttpVersion",
    ivy"com.typesafe.akka::akka-stream:$akkaStreamVersion"
  )

  def jsDependencies = Agg(
    ivy"com.raquo::laminar::$laminarVersion"
  )
}

trait Common extends ScalaModule {
  def scalaVersion = Config.scalaVersion

  //waiting covenant release
  def repositories = super.repositories ++ Seq(
    MavenRepository("https://jitpack.io")
  )

  def ivyDeps = super.ivyDeps() ++ Config.sharedDependencies

  def sources = T.sources(
    millSourcePath / "src",
    millSourcePath / up / "shared" / "src"
  )
}
object shared extends Common //needed for intellij

object jvm extends Common {
  def ivyDeps = super.ivyDeps() ++ Config.jvmDependencies
}

object js extends Common with ScalaJSModule {
  def scalaJSVersion = Config.scalaJSVersion

  def ivyDeps = super.ivyDeps() ++ Config.jsDependencies
}
