package org.lifesoup

import javax.swing.JPanel
import util.{FastImage, FastImagePanel, SimpleFrame}

/**
 * 
 */
object LifeSoup {

  val width = 400
  val height = 300
  val environment = new Environment(width, height)

  def main(args: Array[ String ]) {

    val fastImage = new FastImage(width, height)
    val panel = FastImagePanel(fastImage)
    new SimpleFrame("Lifesoup", panel, width + 10, height + 30)

    while (true) {
      environment.update(1)

      environment.render(fastImage)
      fastImage.updated()
      panel.repaint()

      Thread.sleep(10)

    }


  }

}
