package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal{
      val bValue=b()
      (bValue * bValue) - (4 * a() * c())
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    val twiceA= Signal(2 * a())
    val negB = Signal(-1 * b())
    val srtDelta = Signal(Math.sqrt(delta()))
    Signal{
      if(delta() < 0)Set()
      else Set(
        (negB() + srtDelta())/twiceA(),
        (negB() - srtDelta())/twiceA()
      )
    }
  }
}
