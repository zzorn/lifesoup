package org.lifesoup.util

import javax.swing.JPanel
import java.awt.Graphics


/**
 * 
 */
case class FastImagePanel(fastImage: FastImage) extends JPanel {

  override def paint(g: Graphics) {
    val bi = fastImage.createBufferedImage
    //fastImage.renderToGraphics(g)
    g.drawImage(bi, 0, 0, null)

  }

}
