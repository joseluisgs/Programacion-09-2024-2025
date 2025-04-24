package dev.joseluisgs.listasmenuresult.controllers

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.control.Hyperlink
import org.lighthousegames.logging.logging


class AcercaDeViewController {
    private val logger = logging()
    
    @FXML
    private lateinit var linkGitHub: Hyperlink
    
    // Inicializamos
    @FXML
    fun initialize() {
        logger.debug { "Inicializando AcercaDeViewController FXML" }
        linkGitHub.setOnAction {
            val url = "https://github.com/joseluisgs"
            logger.debug { "Abriendo navegador en el link: $url" }
            Open.open(url)
        }
    }
}
