package dev.joseluisgs.expedientesacademicos.alumnado.mappers

import dev.joseluisgs.expedientesacademicos.alumnado.dao.AlumnoEntity
import dev.joseluisgs.expedientesacademicos.alumnado.dto.json.AlumnoDto
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import dev.joseluisgs.expedientesacademicos.alumnado.viewmodels.ExpedientesViewModel.AlumnoState
import java.time.LocalDate
import java.time.LocalDateTime

fun AlumnoDto.toModel(): Alumno {
    return Alumno(
        id,
        apellidos,
        nombre,
        email,
        LocalDate.parse(fechaNacimiento),
        calificacion,
        repetidor,
        imagen,
        LocalDateTime.parse(createdAt),
        LocalDateTime.parse(updatedAt)
    )
}

@JvmName("dtoToModelList") // Para evitar conflictos con el nombre de la función
fun List<AlumnoDto>.toModel(): List<Alumno> {
    return map { it.toModel() }
}

fun Alumno.toDto(): AlumnoDto {
    return AlumnoDto(
        id,
        apellidos,
        nombre,
        email,
        fechaNacimiento.toString(),
        calificacion,
        repetidor,
        imagen,
        createdAt.toString(),
        updatedAt.toString()
    )
}

@JvmName("modelToDtoList") // Para evitar conflictos con el nombre de la función
fun List<Alumno>.toDto(): List<AlumnoDto> {
    return map { it.toDto() }
}

fun AlumnoEntity.toModel(): Alumno {
    return Alumno(
        id,
        apellidos,
        nombre,
        email,
        fechaNacimiento,
        calificacion,
        repetidor,
        imagen,
        createdAt,
        updatedAt
    )
}

@JvmName("entityToModelList") // Para evitar conflictos con el nombre de la función
fun List<AlumnoEntity>.toModel(): List<Alumno> {
    return map { it.toModel() }
}

fun Alumno.toEntity(): AlumnoEntity {
    return AlumnoEntity(
        id,
        apellidos,
        nombre,
        email,
        fechaNacimiento,
        calificacion,
        repetidor,
        imagen,
        createdAt,
        updatedAt
    )
}

@JvmName("modelToEntityList") // Para evitar conflictos con el nombre de la función
fun List<Alumno>.toEntity(): List<AlumnoEntity> {
    return map { it.toEntity() }
}

fun AlumnoState.toModel(): Alumno {
    return Alumno(
        id = if (numero.value.trim().isBlank()) Alumno.NEW_ALUMNO else numero.value.toLong(),
        apellidos = apellidos.value.trim(),
        nombre = nombre.value.trim(),
        email = email.value.trim(),
        fechaNacimiento = fechaNacimiento.value,
        calificacion = calificacion.value.trim().replace(",", ".").toDouble(),
        repetidor = repetidor.value,
        imagen = imagen.value.url ?: "sin-imagen.png",
    )
}

