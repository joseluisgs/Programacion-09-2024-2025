package dev.joseluisgs.expedientesacademicos.alumnado.dao

import java.time.LocalDate
import java.time.LocalDateTime

data class AlumnoEntity(
    val id: Long,
    val apellidos: String,
    val nombre: String,
    val email: String,
    val fechaNacimiento: LocalDate,
    val calificacion: Double,
    val repetidor: Boolean,
    val imagen: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)