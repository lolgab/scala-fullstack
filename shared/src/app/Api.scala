package app

trait Api[F[_]] {
  def increment(i: Int): F[Int]

  def decrement(i: Int): F[Int]
}
