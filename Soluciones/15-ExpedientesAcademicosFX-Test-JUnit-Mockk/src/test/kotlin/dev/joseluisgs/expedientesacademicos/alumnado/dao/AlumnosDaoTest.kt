package dev.joseluisgs.expedientesacademicos.alumnado.dao

import dev.joseluisgs.expedientesacademicos.database.JdbiManager

// Cuidado debes importar los correctos (org.junit.jupiter.api.Assertions.*) que son los de JUnit 5
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlumnosDaoTest {
    private lateinit var jdbi: JdbiManager
    private lateinit var dao: AlumnosDao

    private val alumno = AlumnoEntity(
        id = 1L,
        apellidos = "García",
        nombre = "Ana",
        email = "ana@test.com",
        fechaNacimiento = LocalDate.parse("2000-01-01"),
        calificacion = 8.5,
        repetidor = false,
        imagen = "ana.jpg",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    @BeforeAll
    fun setUp() {
        // Inicializamos la BD
        val jdbi = JdbiManager(
            databaseUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            databaseInitTables = true,
            databaseInitData = true,
            databaseLogger = false
        ).jdbi
        dao = provideAlumnosDao(jdbi)
    }

    @AfterEach
    fun tearDown() {
        dao.deleteAll()
    }

    @Nested
    @DisplayName("Casos correctos para AlumnosDao")
    inner class CasosCorrectos {

        @Test
        @DisplayName("Insertar alumno correctamente")
        fun insertarAlumno() {
            val id = dao.insert(alumno)

            val alumnoInsertado = dao.selectById(id)
            assertAll(
                { assertNotNull(alumnoInsertado, "El alumno insertado no debe ser nulo") },
                { assertEquals(alumno.nombre, alumnoInsertado?.nombre, "El nombre del alumno no coincide") },
                {
                    assertEquals(
                        alumno.apellidos,
                        alumnoInsertado?.apellidos,
                        "Los apellidos del alumno no coinciden"
                    )
                },
                { assertEquals(alumno.email, alumnoInsertado?.email, "El email del alumno no coincide") }
            )
        }

        @Test
        @DisplayName("Actualizar alumno correctamente")
        fun actualizarAlumno() {
            val id = dao.insert(alumno)
            val alumnoActualizado = alumno.copy(
                id = id,
                nombre = "Ana María",
                updatedAt = LocalDateTime.now()
            )

            val resultado = dao.update(alumnoActualizado)
            val alumnoRecuperado = dao.selectById(id)

            assertAll(
                { assertEquals(1, resultado, "La actualización debe afectar a una fila") },
                {
                    assertEquals(
                        alumnoActualizado.nombre,
                        alumnoRecuperado?.nombre,
                        "El nombre actualizado no coincide"
                    )
                }
            )
        }

        @Test
        @DisplayName("Eliminar alumno correctamente")
        fun eliminarAlumno() {
            val id = dao.insert(alumno)

            val resultado = dao.delete(id)
            val alumnoEliminado = dao.selectById(id)

            assertAll(
                { assertEquals(1, resultado, "La eliminación debe afectar a una fila") },
                { assertNull(alumnoEliminado, "El alumno eliminado no debe existir") }
            )
        }

        @Test
        @DisplayName("Obtener todos los alumnos correctamente")
        fun obtenerTodosLosAlumnos() {
            dao.insert(alumno)
            dao.insert(
                alumno.copy(
                    nombre = "Pedro",
                    email = "pedro@test.com"
                )
            )

            val alumnos = dao.selectAll()

            assertAll(
                { assertEquals(2, alumnos.size, "La lista debe contener dos alumnos") },
                { assertTrue(alumnos.any { it.nombre == "Ana" }, "Debe existir un alumno llamado Ana") },
                { assertTrue(alumnos.any { it.nombre == "Pedro" }, "Debe existir un alumno llamado Pedro") }
            )
        }
    }

    @Nested
    @DisplayName("Casos incorrectos para AlumnosDao")
    inner class CasosIncorrectos {

        @Test
        @DisplayName("Buscar alumno que no existe")
        fun buscarAlumnoInexistente() {
            val alumno = dao.selectById(999L)
            assertNull(alumno, "No debe existir un alumno con ID 999")
        }

        @Test
        @DisplayName("Actualizar alumno que no existe")
        fun actualizarAlumnoInexistente() {
            val alumnoInexistente = alumno.copy(id = 999L)
            val resultado = dao.update(alumnoInexistente)
            assertEquals(0, resultado, "No se debe actualizar ningún alumno inexistente")
        }

        @Test
        @DisplayName("Eliminar alumno que no existe")
        fun eliminarAlumnoInexistente() {
            val resultado = dao.delete(999L)
            assertEquals(0, resultado, "No se debe eliminar ningún alumno inexistente")
        }
    }
}
