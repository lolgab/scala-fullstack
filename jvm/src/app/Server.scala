package app

import java.nio.ByteBuffer

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.`Access-Control-Allow-Origin`
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.DebuggingDirectives
import akka.stream.ActorMaterializer
import covenant.http.AkkaHttpRoute
import covenant.http.ByteBufferImplicits._
import sloth.Router
import boopickle.Default._
import chameleon.ext.boopickle._
import cats.implicits._

import scala.concurrent.Future

object Server {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val router = Router[ByteBuffer, Future].route[Api[Future]](ApiImpl)

  val apiRoute = pathPrefix("api")(AkkaHttpRoute.fromFutureRouter(router))

  val filesRoutes = pathPrefix("") {
    getFromDirectory("www")
  } ~ path("") {
    getFromFile("www/index.html")
  } ~ path("index.js") {
    getFromFile("out/js/fastOpt/dest/out.js")
  }

  val loggedRoute =
    DebuggingDirectives.logRequestResult("Client Rest", Logging.InfoLevel)(
      filesRoutes ~ apiRoute
    )

  def main(args: Array[String]): Unit = {
    Http().bindAndHandle(loggedRoute, interface = "0.0.0.0", port = 8080)

    println(s"Server online at http://0.0.0.0:8080/")
  }
}
