package dev.joseluisgs.listasmenuresult.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dev.joseluisgs.listasmenuresult.routes.RoutesManager
import dev.joseluisgs.listasmenuresult.viewmodel.ListaMenuViewModel
import javafx.fxml.FXML
import javafx.scene.control.*
import org.lighthousegames.logging.logging

class ListaMenuController {
    private val logger = logging()
    private val viewModel = ListaMenuViewModel()
    
    @FXML
    private lateinit var menuCerrar: MenuItem
    
    @FXML
    private lateinit var menuAcercaDe: MenuItem
    
    @FXML
    private lateinit var textNombre: TextField
    
    @FXML
    private lateinit var botonAdd: Button
    
    @FXML
    private lateinit var botonEliminar: Button
    
    @FXML
    private lateinit var labelContador: Label
    
    @FXML
    private lateinit var listaNombres: ListView<String>
    
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando ListaMenuController FXML" }
        
        initDefaultsValues()
        initBindings()
        initEvents()
    }
    
    private fun initBindings() {
        logger.debug { "Estableciendo bindings entre los componentes" }
        viewModel.estado.addListener { _, oldEstado, newEstado ->
            if (oldEstado == newEstado) {
                logger.debug { "El estado no ha cambiado" }
                return@addListener
            }
            logger.debug { "Cambiando el estado de la vista" }
            // Actualizamos el contador
            labelContador.text = "Contador: ${newEstado.contador}"
            // Actualizamos la lista
            listaNombres.items.setAll(newEstado.listaNombres)
            // Habilitamos o deshabilitamos el botón de eliminar
            botonEliminar.isDisable = newEstado.listaNombres.isEmpty()
        }
    }
    
    private fun initDefaultsValues() {
        logger.debug { "Inicializando valores por defecto" }
        botonAdd.isDisable = true
        botonEliminar.isDisable = true
    }
    
    private fun initEvents() {
        logger.debug { "Inicializando eventos de la vista" }
        menuCerrar.setOnAction {
            logger.debug { "Cerrando la aplicación" }
            RoutesManager.onAppExit()
        }
        
        menuAcercaDe.setOnAction {
            logger.debug { "Abriendo la ventana de Acerca de" }
            // Abrimos la ventana de acerca de
            RoutesManager.initAcercaDeStage()
        }
        
        textNombre.textProperty().addListener { _, _, newText ->
            logger.debug { "Texto en el TextField: $newText" }
            botonAdd.isDisable = newText.isNullOrBlank()
        }
        
        botonAdd.setOnAction {
            logger.debug { "Agregando nombre al listado" }
            viewModel.addNombre(textNombre.text).onFailure {
                logger.error { "Error al agregar el nombre: ${it.message}" }
                // Mostramos un error
                Alert(Alert.AlertType.ERROR).apply {
                    title = "Error Validación"
                    headerText = "Error al agregar el nombre"
                    contentText = it.message
                }.showAndWait()
            }.onSuccess {
                logger.debug { "Nombre agregado correctamente" }
                // Limpiamos el TextField
                //textNombre.clear()
            }
        }
        
        botonEliminar.setOnAction {
            logger.debug { "Eliminando nombre del listado" }
            val index = listaNombres.selectionModel.selectedIndex
            if (index >= 0) {
                viewModel.removeNombreByIndex(index).onFailure {
                    logger.error { "Error al eliminar el nombre: ${it.message}" }
                    // Mostramos un error
                    Alert(Alert.AlertType.ERROR).apply {
                        title = "Error Validación"
                        headerText = "Error al eliminar el nombre"
                        contentText = it.message
                    }.showAndWait()
                }
                /* viewModel.removeNombreByName(listaNombres.selectionModel.selectedItem).onFailure {
                     logger.error { "Error al eliminar el nombre: ${it.message}" }
                     // Mostramos un error
                     Alert(Alert.AlertType.ERROR).apply {
                         title = "Error Validación"
                         headerText = "Error al eliminar el nombre"
                         contentText = it.message
                     }.showAndWait()
                 }*/
            } else {
                logger.error { "No se ha seleccionado ningún nombre" }
                // Mostramos un error
                Alert(Alert.AlertType.ERROR).apply {
                    title = "Error Validación"
                    headerText = "Error al eliminar el nombre"
                    contentText = "No se ha seleccionado ningún nombre"
                }.showAndWait()
            }
        }
    }
}