package org.lifesoup

import util.FastImage
import java.util.Random

/**
 * 
 */
class Environment(width: Int, height: Int) {

  val size = width * height
  val mineralsA = new Array[Float](size)
  val mineralsB = new Array[Float](size)
  var minerals = mineralsA
  var oldMinerals = mineralsB

  val topFallAmount = 0.6f
  val sideFallAmount = 0.2f

  private var randomOrderIndex = 0
  private val moveOrderRandomTable = new Array[Boolean](1013)

  init()

  def init() {
    val r = new Random()

    var j = 0
    while (j < moveOrderRandomTable.length) {
      moveOrderRandomTable(j) = r.nextBoolean()
      j += 1
    }

    var i = 0
    var y = 0
    while (y < height) {

      var x = 0
      while (x < width) {

        if (x == 0 || x == width - 1 || y == 0) {
          mineralsA(i) = 0f
        }
        else if (y == height - 1) {
          mineralsA(i) = 1f
        }
        else {
          mineralsA(i) = r.nextFloat() * r.nextFloat()
        }

        mineralsB(i) = mineralsA(i)

        i += 1
        x += 1
      }
      y += 1
    }
  }

  def nextRandomDir(): Boolean = {
    randomOrderIndex += 1
    if (randomOrderIndex >= moveOrderRandomTable.length) {
      randomOrderIndex = 0
    }
    moveOrderRandomTable(randomOrderIndex)
  }

  def update(seconds: Float) {

    // Swap
    val t = oldMinerals
    oldMinerals = minerals
    minerals = t

    // Border


    def moveMatter(currentMatter: Float, source: Int, moveWeight: Float): Float = {
      var matterToMove = math.max(0, math.min(1f - currentMatter, oldMinerals(source)) * moveWeight)
      if (matterToMove > 0) {
        oldMinerals(source) -= matterToMove
      }
      matterToMove
    }


    // Calculate movement
    var y = height - 1
    while (y >= 0) {

      // Process rows in random order, to avoid drifting.
      var xStart = 0
      var xEnd   = width - 1
      var xDelta = 1
      if (nextRandomDir()) {
        xStart = width - 1
        xEnd   = 0
        xDelta = -1
      }

      var x = xStart
      while (x != xEnd) {
        if (y > 0 && y < height - 1 &&
            x > 0 && x < width  - 1) {
          val above = x + (y - 1) * width
          val below = x + (y + 1) * width
          val aboveLeft = x - 1 + (y - 1) * width
          val aboveRight = x + 1 + (y - 1) * width
          val left = x - 1 + y * width
          val right = x + 1 + y * width
          val self = x + y * width

          // Fall down if we are empty and minerals above
          
          var currentMatter = oldMinerals(self)

          var packingFactor = 1f - currentMatter
          packingFactor *= packingFactor

          currentMatter += moveMatter(currentMatter, above, topFallAmount * packingFactor)

          // Randomly change the order left and right drops are checked to avoid drifting.
          if (nextRandomDir()) {
            currentMatter += moveMatter(currentMatter, aboveLeft, sideFallAmount * packingFactor)
            currentMatter += moveMatter(currentMatter, aboveRight, sideFallAmount * packingFactor)
          }
          else {
            currentMatter += moveMatter(currentMatter, aboveRight, sideFallAmount * packingFactor)
            currentMatter += moveMatter(currentMatter, aboveLeft, sideFallAmount * packingFactor)
          }

          minerals(self) = currentMatter

/*
          val aboveStatus = oldMinerals(above)
          if (minerals(self) < 0.5f && aboveStatus > 0.5f) {
            minerals(self) = aboveStatus
            oldMinerals(above) = 0
          }
          else {
            minerals(self) = oldMinerals(self)
          }
*/
        }
        
        x += xDelta
      }
      y -= 1
    }


  }

  def render(fastImage: FastImage) {
    def clamp(x: Float, min: Float, max: Float): Float = {
      if (x < min) {
        min
      }
      else if (x > max) {
        max
      }
      else {
        x
      }
    }

    val buf = fastImage.buffer

    var i = 0
    var y = 0
    while (y < fastImage.height) {
      var x = 0
      while (x < fastImage.width) {

        // TODO: Scaling
        val srcIndex = i
        val destIndex = i

        val value = minerals(srcIndex)
        val blue  = clamp((value * 2f)      , 0, 1f)
        val green = clamp((value - 0.5f) * 2, 0, 1f)

        val color = 0xff000000 + ((0xff * green).toInt << 8) + (0xff * blue).toInt

        buf(destIndex) = color

        x += 1
        i += 1
      }
      y += 1
    }
  }
}