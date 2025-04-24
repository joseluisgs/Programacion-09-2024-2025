package dev.joseluisgs.listasmenuresult.viewmodel

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.joseluisgs.listasmenuresult.error.ListaError
import javafx.beans.property.SimpleObjectProperty

class ListaMenuViewModel {
    
    // Definimos el estado de la vista
    val estado: SimpleObjectProperty<Estado> = SimpleObjectProperty(Estado())
    
    data class Estado(
        val listaNombres: List<String> = emptyList(),
        val contador: Int = 0,
    )
    
    // Métodos para cambiar el estado de la vista
    fun addNombre(nombre: String): Result<Unit, ListaError> {
        val listaNombres = estado.value.listaNombres.toMutableList()
        // Validar
        if (nombre.isBlank()) {
            return Err(ListaError.ErrorValidation("El nombre no puede estar vacío"))
        }
        if (nombre in listaNombres) {
            return Err(ListaError.ErrorValidation("El nombre ya existe en la lista"))
        }
        if (nombre.contains("@")) {
            return Err(ListaError.ErrorValidation("El nombre no puede contener el carácter @"))
        }
        listaNombres.add(nombre)
        estado.value = estado.value.copy(
            listaNombres = listaNombres,
            contador = listaNombres.size
        )
        return Ok(Unit)
    }
    
    fun reset(): Result<Unit, ListaError> {
        estado.value = Estado()
        return Ok(Unit)
    }
    
    fun removeNombreByIndex(index: Int): Result<Unit, ListaError> {
        val listaNombres = estado.value.listaNombres.toMutableList()
        // Validar
        if (index < 0 || index >= listaNombres.size) {
            return Err(ListaError.ErrorValidation("Índice fuera de rango"))
        }
        listaNombres.removeAt(index)
        estado.value = estado.value.copy(
            listaNombres = listaNombres,
            contador = listaNombres.size
        )
        return Ok(Unit)
    }
    
    fun removeNombreByName(nombre: String): Result<Unit, ListaError> {
        val listaNombres = estado.value.listaNombres.toMutableList()
        // Validar
        if (nombre !in listaNombres) {
            return Err(ListaError.ErrorValidation("El nombre no existe en la lista"))
        }
        listaNombres.remove(nombre)
        estado.value = estado.value.copy(
            listaNombres = listaNombres,
            contador = listaNombres.size
        )
        return Ok(Unit)
    }
}