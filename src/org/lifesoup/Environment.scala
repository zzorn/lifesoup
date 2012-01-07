package org.lifesoup

import util.FastImage
import java.util.Random

/**
 * 
 */
class Environment(w: Int, h: Int) {

  val fastImage = new FastImage(w, h)

  val random = new Random()

  def update(seconds: Float) {
    random.setSeed(System.currentTimeMillis())
    var i = 0
    while (i < 10000) {
      val x = random.nextInt(fastImage.width)
      val y = random.nextInt(fastImage.height)
      fastImage.buffer(x + y * fastImage.width) = random.nextInt()
      i += 1
    }

  }

}