package dev.joseluisgs.expedientesacademicos.alumnado.repositories

import dev.joseluisgs.expedientesacademicos.alumnado.dao.AlumnosDao
import dev.joseluisgs.expedientesacademicos.alumnado.mappers.toEntity
import dev.joseluisgs.expedientesacademicos.alumnado.mappers.toModel
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class AlumnosRepositoryImpl(
    private val dao: AlumnosDao
) : AlumnosRepository {


    override fun findAll(): List<Alumno> {
        logger.debug { "findAll" }
        return dao.selectAll().toModel()
    }

    override fun findById(id: Long): Alumno? {
        logger.debug { "findById: $id" }
        return dao.selectById(id)?.toModel()
    }

    override fun save(alumno: Alumno): Alumno {
        logger.debug { "save: $alumno" }
        // Nunca se le cambia el ID, por lo que si es nuevo, lo creamos, si no lo actualizamos
        return if (alumno.isNewAlumno) {
            create(alumno)
        } else {
            update(alumno)
        }
    }

    private fun create(alumno: Alumno): Alumno {
        logger.debug { "create: $alumno" }
        val timestamp = LocalDateTime.now()
        val toSave = alumno.copy(createdAt = timestamp, updatedAt = timestamp)
        val id = dao.insert(toSave.toEntity())
        return toSave.copy(id = id)
    }

    private fun update(alumno: Alumno): Alumno {
        logger.debug { "update: $alumno" }
        val timestamp = LocalDateTime.now()
        val toUpdate = alumno.copy(updatedAt = timestamp)
        val res = dao.update(toUpdate.toEntity())
        logger.debug { "Nuestra consulta de actualización ha devuelto: $res" }
        logger.debug { "Alumno actualizado: $toUpdate" }
        return toUpdate
    }

    override fun deleteById(id: Long) {
        logger.debug { "deleteById: $id" }
        val res = dao.delete(id)
        logger.debug { "Nuestra consulta de borrado ha devuelto: $res" }
        logger.debug { "Alumno eliminado con id: $id" }
    }

    override fun deleteAll() {
        logger.debug { "deleteAll" }
        return dao.deleteAll()
    }

    override fun saveAll(alumnos: List<Alumno>): List<Alumno> {
        logger.debug { "saveAll: $alumnos" }
        return alumnos.map { save(it) }
    }
}