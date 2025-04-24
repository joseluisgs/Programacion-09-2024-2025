package dev.joseluisgs.expedientesacademicos.alumnado.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.joseluisgs.expedientesacademicos.alumnado.errors.AlumnoError
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import dev.joseluisgs.expedientesacademicos.alumnado.repositories.AlumnosRepository
import org.lighthousegames.logging.logging

class AlumnosServiceImpl(
    private val repository: AlumnosRepository,
    private val cache: Cache<Long, Alumno>,
) : AlumnosService {
    private val logger = logging()

    override fun findAll(): Result<List<Alumno>, AlumnoError> {
        logger.debug { "findAll" }
        return Ok(repository.findAll())
    }

    override fun deleteAll(): Result<Unit, AlumnoError> {
        logger.debug { "deleteAll" }
        repository.deleteAll().also {
            cache.invalidateAll()
            return Ok(it)
        }
    }

    override fun saveAll(alumnos: List<Alumno>): Result<List<Alumno>, AlumnoError> {
        logger.debug { "saveAll" }
        repository.saveAll(alumnos).also {
            cache.invalidateAll()
            return Ok(it)
        }
    }

    override fun save(alumno: Alumno): Result<Alumno, AlumnoError> {
        logger.debug { "save" }
        repository.save(alumno).also { nuevoAlumno ->
            cache.put(nuevoAlumno.id, alumno)
            logger.debug { "Alumno salvado/actualizado: $nuevoAlumno" }
            return Ok(nuevoAlumno)
        }
    }

    override fun deleteById(id: Long): Result<Unit, AlumnoError> {
        logger.debug { "deleteById" }
        repository.deleteById(id).also {
            cache.invalidate(id)
            return Ok(it)
        }
    }

    override fun findById(id: Long): Result<Alumno, AlumnoError> {
        logger.debug { "findById" }
        return cache.getIfPresent(id)?.let {
            Ok(it)
        } ?: repository.findById(id)?.also {
            cache.put(id, it)
        }?.let {
            Ok(it)
        } ?: Err(AlumnoError.NotFound("Alumno con ID $id no encontrado"))
    }
}