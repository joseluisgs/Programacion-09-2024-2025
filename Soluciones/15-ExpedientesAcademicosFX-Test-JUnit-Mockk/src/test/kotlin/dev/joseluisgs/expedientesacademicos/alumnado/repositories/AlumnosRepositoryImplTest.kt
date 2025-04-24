package dev.joseluisgs.expedientesacademicos.alumnado.repositories

import dev.joseluisgs.expedientesacademicos.alumnado.dao.AlumnosDao
import dev.joseluisgs.expedientesacademicos.alumnado.mappers.toEntity
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test

@DisplayName("AlumnosRepository Test")
@ExtendWith(MockitoExtension::class)
class AlumnosRepositoryImplTest {
    @Mock
    private lateinit var dao: AlumnosDao

    @InjectMocks
    private lateinit var repository: AlumnosRepositoryImpl

    private val alumno = Alumno(
        id = 1L,
        nombre = "Test",
        apellidos = "Test",
        email = "test@test.com",
        fechaNacimiento = LocalDate.parse("2000-01-01"),
        calificacion = 8.5,
        repetidor = false,
        imagen = "test.jpg",
        createdAt = LocalDateTime.parse("2023-01-01T00:00:00"),
        updatedAt = LocalDateTime.parse("2023-01-01T00:00:00")
    )

    @Nested
    @DisplayName("Casos correctos")
    inner class CasosCorrectos {
        @Test
        @DisplayName("findAll devuelve lista de alumnos correctamente")
        fun findAll() {
            // Given
            whenever(dao.selectAll()).thenReturn(listOf(alumno.toEntity()))

            // When
            val result = repository.findAll()

            // Then
            assertAll(
                { assertEquals(1, result.size, "El tamaño de la lista debe ser 1") },
                { assertEquals(alumno, result[0], "El alumno debe ser igual al esperado") }
            )
            verify(dao, times(1)).selectAll()
        }

        @Test
        @DisplayName("findById encuentra alumno correctamente")
        fun findById() {
            // Given
            whenever(dao.selectById(1L)).thenReturn(alumno.toEntity())

            // When
            val result = repository.findById(1L)

            // Then
            assertAll(
                { assertEquals(alumno, result, "El alumno debe ser igual al esperado") }
            )
            verify(dao, times(1)).selectById(1L)
        }

        @Test
        @DisplayName("save crea nuevo alumno correctamente")
        fun saveCreate() {
            // Given
            val nuevoAlumno = alumno.copy(id = -1L)

            // Debemos huir de las fechas fijas, por lo que usamos LocalDateTime.now()
            whenever(dao.insert(argThat {
                this.nombre == nuevoAlumno.nombre &&
                        this.apellidos == nuevoAlumno.apellidos &&
                        this.email == nuevoAlumno.email &&
                        this.fechaNacimiento == nuevoAlumno.fechaNacimiento &&
                        this.calificacion == nuevoAlumno.calificacion &&
                        this.repetidor == nuevoAlumno.repetidor &&
                        this.imagen == nuevoAlumno.imagen
            })).thenReturn(1L)

            // When
            val result = repository.save(nuevoAlumno)

            // Then
            assertAll(
                { assertEquals(1L, result.id, "El ID debe ser 1") },
                { assertEquals(nuevoAlumno.nombre, result.nombre, "El nombre debe ser igual") },
                { assertEquals(nuevoAlumno.apellidos, result.apellidos, "Los apellidos deben ser iguales") },
                { assertEquals(nuevoAlumno.email, result.email, "El email debe ser igual") },
                {
                    assertEquals(
                        nuevoAlumno.fechaNacimiento,
                        result.fechaNacimiento,
                        "La fecha de nacimiento debe ser igual"
                    )
                },
                { assertEquals(nuevoAlumno.calificacion, result.calificacion, "La calificación debe ser igual") },
                { assertEquals(nuevoAlumno.repetidor, result.repetidor, "El repetidor debe ser igual") },
                { assertEquals(nuevoAlumno.imagen, result.imagen, "La imagen debe ser igual") }
            )
            verify(dao, times(1)).insert(any())
        }

        @Test
        @DisplayName("save actualiza alumno correctamente")
        fun saveUpdate() {
            // Given
            val alumnoActualizado = alumno.copy(nombre = "Nuevo Nombre")

            // When
            whenever(
                dao.update(
                    argThat {
                        this.id == alumnoActualizado.id &&
                                this.nombre == alumnoActualizado.nombre &&
                                this.apellidos == alumnoActualizado.apellidos &&
                                this.email == alumnoActualizado.email &&
                                this.fechaNacimiento == alumnoActualizado.fechaNacimiento &&
                                this.calificacion == alumnoActualizado.calificacion &&
                                this.repetidor == alumnoActualizado.repetidor &&
                                this.imagen == alumnoActualizado.imagen
                    }
                )).thenReturn(1)

            val result = repository.save(alumnoActualizado)

            // Then
            assertAll(
                { assertEquals(alumnoActualizado.nombre, result.nombre, "El nombre debe ser igual") },
                { assertEquals(alumnoActualizado.apellidos, result.apellidos, "Los apellidos deben ser iguales") },
                { assertEquals(alumnoActualizado.email, result.email, "El email debe ser igual") },
                {
                    assertEquals(
                        alumnoActualizado.fechaNacimiento,
                        result.fechaNacimiento,
                        "La fecha de nacimiento debe ser igual"
                    )
                },
                { assertEquals(alumnoActualizado.calificacion, result.calificacion, "La calificación debe ser igual") },
                { assertEquals(alumnoActualizado.repetidor, result.repetidor, "El repetidor debe ser igual") },
                { assertEquals(alumnoActualizado.imagen, result.imagen, "La imagen debe ser igual") }
            )
            verify(dao, times(1)).update(any())
        }

        @Test
        @DisplayName("deleteById elimina alumno correctamente")
        fun deleteById() {
            // Given
            whenever(dao.delete(1L)).thenReturn(1)

            // When
            repository.deleteById(1L)

            // Then
            verify(dao, times(1)).delete(1L)
        }
    }

    @Nested
    @DisplayName("Casos incorrectos")
    inner class CasosIncorrectos {
        @Test
        @DisplayName("findById no encuentra alumno")
        fun findByIdNotFound() {
            // Given
            whenever(dao.selectById(1L)).thenReturn(null)

            // When
            val result = repository.findById(1L)

            // Then
            assertEquals(null, result, "El resultado debe ser null")
            verify(dao, times(1)).selectById(1L)
        }

        @Test
        @DisplayName("save update no encuentra alumno")
        fun saveUpdateNotFound() {
            // Given
            val alumnoActualizado = alumno.copy(nombre = "Nuevo Nombre")

            whenever(
                dao.update(
                    argThat {
                        this.id == alumnoActualizado.id &&
                                this.nombre == alumnoActualizado.nombre &&
                                this.apellidos == alumnoActualizado.apellidos &&
                                this.email == alumnoActualizado.email &&
                                this.fechaNacimiento == alumnoActualizado.fechaNacimiento &&
                                this.calificacion == alumnoActualizado.calificacion &&
                                this.repetidor == alumnoActualizado.repetidor &&
                                this.imagen == alumnoActualizado.imagen
                    }
                )).thenReturn(0)

            // When
            val result = repository.save(alumnoActualizado)

            // Then
            assertAll(
                { assertEquals(alumnoActualizado.id, result.id, "El ID debe ser el mismo") },
                { assertEquals(alumnoActualizado.nombre, result.nombre, "El nombre debe ser igual") }
            )
            verify(dao, times(1)).update(any())
        }

        @Test
        @DisplayName("deleteById no encuentra alumno")
        fun deleteByIdNotFound() {
            // Given
            whenever(dao.delete(1L)).thenReturn(0)

            // When
            repository.deleteById(1L)

            // Then
            verify(dao, times(1)).delete(1L)
        }

        @Test
        @DisplayName("findAll devuelve lista vacía")
        fun findAllEmpty() {
            // Given
            whenever(dao.selectAll()).thenReturn(emptyList())

            // When
            val result = repository.findAll()

            // Then
            assertAll(
                { assertEquals(0, result.size, "La lista debe estar vacía") }
            )
            verify(dao, times(1)).selectAll()
        }

        @Test
        @DisplayName("save insert falla")
        fun saveCreateFails() {
            // Given
            val nuevoAlumno = alumno.copy(id = -1L)

            whenever(dao.insert(any())).thenReturn(-1L)

            // When
            val result = repository.save(nuevoAlumno)

            // Then
            assertAll(
                { assertEquals(-1L, result.id, "El ID debe ser -1") },
                { assertEquals(nuevoAlumno.nombre, result.nombre, "El nombre debe ser igual") }
            )
            verify(dao, times(1)).insert(any())
        }
    }


}