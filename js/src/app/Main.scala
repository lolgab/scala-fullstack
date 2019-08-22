package app

import com.raquo.laminar.api.L._
import org.scalajs.dom.document

import scala.util.Try

object Main {
  val nameInput = input(
    placeholder := "Insert your name"
  )

  val nameStream = nameInput.events(onInput).mapTo(nameInput.ref.value)

  val incrementInput = input(
    placeholder := "Insert a number"
  )

  val incrementStream = incrementInput.events(onInput).mapTo(incrementInput.ref.value)

  val resultStream: EventStream[Int] = incrementStream.map { s =>
    val optionInt = Try(s.toInt).toOption
    val result    = optionInt.map(Client.api.increment) //executed on server!
    result
      .map { f =>
        EventStream.fromFuture(f)
      }
      .getOrElse(EventStream.fromSeq(Seq.empty, emitOnce = true))
  }.flatten

  val app = div(
    nameInput,
    p("Hello ", child.text <-- nameStream),
    h3("Server implemented function:"),
    incrementInput,
    p("result from server: ", child.text <-- resultStream.map(_.toString))
  )

  def main(args: Array[String]): Unit = {
    render(document.body, app)
  }
}
