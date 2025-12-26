package day09

import se.horv.util.Coordinate
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.collections.Set

class DrawRect(val colored: Set<Coordinate>) : JPanel() {

    protected override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        // draw the rectangle here
        val minX = colored.minOf { it.x } - 5
        val maxX = colored.maxOf { it.x } + 5
        val minY = colored.minOf { it.y } - 5
        val maxY = colored.maxOf { it.y } + 5
        g.drawRect(minX, minY, (maxX-minX)*3, (maxY-minY)*3)
        for(c in colored) {
            g.drawRect(c.x*3, c.y*3, 1*3,1*3)
        }
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(
            RECT_WIDTH + 2 * RECT_X,
            RECT_HEIGHT + 2 * RECT_Y
        )
    }

    companion object {
        private const val RECT_X = 20
        private val RECT_Y: Int = RECT_X
        private const val RECT_WIDTH = 100
        private val RECT_HEIGHT: Int = RECT_WIDTH

        // create the GUI explicitly on the Swing event thread
        private fun createAndShowGui(colored: Set<Coordinate>) {
            val mainPanel = DrawRect(colored)

            val frame: JFrame = JFrame("DrawRect")
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
            frame.getContentPane().add(mainPanel)
            frame.pack()
            frame.setLocationByPlatform(true)
            frame.setVisible(true)
        }

        fun ui(colored: Set<Coordinate>) {
            SwingUtilities.invokeLater(object : Runnable {
                override fun run() {
                    createAndShowGui(colored)
                }
            })
        }
    }
}