package dev.joseluisgs.expedientesacademicos.alumnado.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging


@RegisterKotlinMapper(AlumnoEntity::class) // Indicamos que usaremos un mapper de Kotlin, cuidado con los tipos
interface AlumnosDao {

    @SqlUpdate("DELETE FROM alumnos")
    fun deleteAll()

    @SqlQuery("SELECT * FROM alumnos ORDER BY apellidos ASC")
    fun selectAll(): List<AlumnoEntity>

    @SqlQuery("SELECT * FROM alumnos WHERE id = :id")
    fun selectById(@Bind("id") id: Long): AlumnoEntity?

    @SqlUpdate("INSERT INTO alumnos (apellidos, nombre, email, fechaNacimiento, calificacion, repetidor, imagen, created_at, updated_at) VALUES (:apellidos, :nombre, :email, :fechaNacimiento, :calificacion, :repetidor, :imagen, :createdAt, :updatedAt)")
    @GetGeneratedKeys
    fun insert(@BindBean alumno: AlumnoEntity): Long

    @SqlUpdate("UPDATE alumnos SET apellidos = :apellidos, nombre = :nombre, email = :email, fechaNacimiento = :fechaNacimiento, calificacion = :calificacion, repetidor = :repetidor, imagen = :imagen, updated_at = :updatedAt WHERE id = :id")
    fun update(@BindBean alumno: AlumnoEntity): Int

    @SqlUpdate("DELETE FROM alumnos WHERE id = :id")
    fun delete(@Bind("id") id: Long): Int
}

fun provideAlumnosDao(jdbi: Jdbi): AlumnosDao {
    val logger = logging()
    logger.debug { "Inicializando AlumnosDao" }
    return jdbi.onDemand(AlumnosDao::class.java)
}