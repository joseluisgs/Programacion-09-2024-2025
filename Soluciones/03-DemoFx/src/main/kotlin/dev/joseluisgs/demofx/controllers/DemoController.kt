package dev.joseluisgs.demofx.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField

class DemoController {
    
    @FXML
    private lateinit var botonAdios: Button
    
    @FXML
    private lateinit var botonHola: Button
    
    @FXML
    private lateinit var labelTexto: Label
    
    @FXML
    private lateinit var textNombre: TextField
    
    @FXML
    private fun initialize() {
        // This method is called after the FXML file has been loaded
        println("On View Initialize")
        // cosa que queremos que pase al inicializar la vista
        initDefaultValues()
        initEvents()
        
    }
    
    private fun initDefaultValues() {
        labelTexto.text = "Hola, mundo!"
        botonAdios.isDisable = true
        botonHola.text = "Hola 1º DAW"
        textNombre.apply {
            text = ""
            promptText = "Introduce tu nombre"
            isDisable = true
        }
    }
    
    private fun initEvents() {
        botonHola.setOnAction {
            botonHolaAccion()
        }
        botonAdios.setOnAction {
            botonAdiosAccion()
        }
    }
    
    private fun botonAdiosAccion() {
        println("Adios, mundo!")
        labelTexto.text = "Adios, 1º DAW: ${textNombre.text}"
        botonAdios.isDisable = true
        botonHola.isDisable = false
        textNombre.apply {
            isDisable = true
            text = ""
            promptText = "Introduce tu nombre"
        }
    }
    
    private fun botonHolaAccion() {
        println("Hola, mundo!")
        labelTexto.text = "Hola, 1º DAW"
        botonAdios.isDisable = false
        botonHola.isDisable = true
        textNombre.apply {
            isDisable = false
            text = ""
            promptText = "Introduce tu nombre"
            requestFocus()
        }
    }
    
    
}