package dev.joseluisgs.formulariofx

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.stage.Stage
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()

class FormularioApplication : Application() {
    // Sobre escribimos el método start
    // Este método es obligatorio y lanza la aplicación: stage inicial
    override fun start(stage: Stage) {
        logger.debug { "Cargando vista" }
        // Cargamos el FXML
        val fxmlLoader = FXMLLoader(
            // Ojo al path siempre relativo a la carpeta resources desde donde se ejecuta
            // Cuidad con los paquetes
            { }::class.java.getResource("views/formulario-view.fxml")
        )
        
        // Creamos la escena
        val mainScene = Scene(fxmlLoader.load(), 500.0, 400.0)
        
        val myLocale = Locale.of("es", "ES") // Cambiamos el locale a español
        
        // Creamos la ventana
        logger.debug { "Creando ventana" }
        stage.apply {
            // Cambiamos el locale
            // Ponemos el tamaño mínimo, si queremos, sino no es necesario
            minWidth = mainScene.width
            minHeight = mainScene.height
            isMaximized = false // No maximizar por defecto
            isIconified = false // No minimizar por defecto
            isResizable = false // No redimensionable por defecto
            // Ponemos el título
            title = "Registro"
            // Ponemos la escena
            scene = mainScene
            // Le ponemos un icono
            icons.add(
                Image(
                    { }::class.java.getResourceAsStream("icons/app-icon.png")
                )
            )
            setOnCloseRequest { event ->
                logger.debug { "Cerrando aplicación" }
                // Si queremos hacer algo al cerrar la ventana
                // Por ejemplo, preguntar si queremos salir
                Alert(Alert.AlertType.CONFIRMATION).apply {
                    title = "Confirmación"
                    headerText = "¿Estás seguro de que quieres salir?"
                    contentText = "Si sales perderás los datos introducidos"
                }.showAndWait()
                    .filter { b -> b == ButtonType.OK }
                    .ifPresent { Platform.exit() }
                // Si no queremos hacer nada, podemos dejarlo así
                event.consume() // Evitamos que se cierre la ventana
            }
        }.show() // Mostramos la ventana
    }
    
    // Opcionalmente podemos sobreescribir el método stop e init, si queremos hacer algo antes o después
    override fun stop() {
        println("Stop")
    }
    
    override fun init() {
        println("Init")
    }
}

fun main() {
    // Lanzamos la aplicación
    // Usamos el método launch de la clase Application, que llama al método start
    Application.launch(FormularioApplication::class.java)
}