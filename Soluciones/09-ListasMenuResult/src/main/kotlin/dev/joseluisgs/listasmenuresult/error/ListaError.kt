package dev.joseluisgs.listasmenuresult.error

sealed class ListaError(val message: String) {
    class ErrorValidation(message: String = "Error de validación") : ListaError(message)
}