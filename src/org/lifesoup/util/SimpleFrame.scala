package org.lifesoup.util

import java.awt.Dimension
import javax.swing.{JComponent, JFrame}

/**
 * A Swing JFrame with sensible default settings.
 * Makes UI prototyping easier.
 *
 * @author Hans Haggstrom
 */
class SimpleFrame(title : String, content : JComponent, width: Int = 800, height: Int = 600) extends JFrame {

  setTitle(title)
  setContentPane( content )
  setPreferredSize( new Dimension(width, height) )

  setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE )

  pack()
  setVisible(true)
}


