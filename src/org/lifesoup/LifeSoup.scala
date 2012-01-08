package org.lifesoup

import javax.swing.JPanel
import util.{FastImage, FastImagePanel, SimpleFrame}

/**
 * 
 */
object LifeSoup {

  val width = 800
  val height = 600
  val environment = new Environment(width, height)

  def main(args: Array[ String ]) {

    val panel = FastImagePanel(environment.fastImage)
    val simpleFrame = new SimpleFrame("Lifesoup", panel)

    var x = 0
    var y = 0
    var i = 0
    while (true) {
      environment.update(1)
//      panel.invalidate()
//      panel.validate()
      panel.repaint()
//      simpleFrame.invalidate()
//      simpleFrame.repaint()


      environment.fastImage.buffer((x % width) + (y % height) * width) = 0x88888888
      //environment.fastImage.updated()

//      println("updated, x " + x + " y " + y)

//      Thread.sleep(100)

      x += 1
      y += 1
      i += 1
    }


  }

}
