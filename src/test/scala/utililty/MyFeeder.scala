package utililty


import io.gatling.core.feeder.Feeder

import scala.util.Random

/**
  * Created by deeptik on 22.05.17.
  */
object MyFeeder {

  val random = new Random()

  def apply():  Feeder[String] = {
    Iterator.continually(Map(
      ("username", Random.alphanumeric.take(5).mkString.toUpperCase))
    )
  }

}
