package dev.joseluisgs

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

fun main() {
    val frame = JFrame("My First Swing App")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(300, 300)
    val panel = JPanel()
    panel.background = java.awt.Color.MAGENTA
    val button = JButton("Click Me")
    val label = JLabel("Hello, World!")
    button.addActionListener {
        println("Button clicked")
        label.text = "Button clicked!"
        label.foreground = java.awt.Color.YELLOW
    }
    panel.add(button)
    panel.add(label)
    frame.contentPane = panel
    frame.isVisible = true
}