package dev.joseluisgs.demofx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        // This method is called when the application starts
        println("On Application Start")
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("views/demo-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hola Java FX Demo 1º DAW"
        stage.scene = scene
        stage.show()
    }
    
    override fun stop() {
        // Clean up resources before the application stops
        println("On Application Stop")
    }
    
    override fun init() {
        // Prepare the application before it starts
        println("On Application Init")
    }
    
}

fun main() {
    Application.launch(HelloApplication::class.java)
}