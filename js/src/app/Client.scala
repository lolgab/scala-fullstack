package app

import boopickle.Default._
import chameleon.ext.boopickle._
import java.nio.ByteBuffer
import covenant.http.HttpClient
import scala.concurrent.Future
import scalajs.concurrent.JSExecutionContext.Implicits.queue


object Client {
  val client = HttpClient[ByteBuffer]("http://localhost:8080")
  val api: Api[Future] = client.wire[Api[Future]]
}
