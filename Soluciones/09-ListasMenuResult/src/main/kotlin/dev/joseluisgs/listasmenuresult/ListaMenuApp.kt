package dev.joseluisgs.listasmenuresult

import dev.joseluisgs.listasmenuresult.routes.RoutesManager
import javafx.application.Application
import javafx.stage.Stage

class ListaMenuApp : Application() {
    override fun start(stage: Stage) {
        RoutesManager.apply {
            app = this@ListaMenuApp
        }.run {
            // Iniciamos la aplicación, podiamos hacerlo con also!!
            initMainStage(stage)
        }
    }
}

fun main() {
    Application.launch(ListaMenuApp::class.java)
}