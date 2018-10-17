package app

import scala.concurrent.Future

object ApiImpl extends Api[Future] {
  def increment(i: Int): Future[Int] = Future.successful(i + 1)

  def decrement(i: Int): Future[Int] = Future.successful(i - 1)
}
