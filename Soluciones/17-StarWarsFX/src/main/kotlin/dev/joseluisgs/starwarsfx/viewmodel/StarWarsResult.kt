package dev.joseluisgs.starwarsfx.viewmodel

sealed class StarWarsResultado {
    data class StarWarsFin(val message: String) : StarWarsResultado()
    data object StarWarsEjecutandose : StarWarsResultado()
    data object StarWarsInicio : StarWarsResultado()
}