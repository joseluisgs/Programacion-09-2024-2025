package dev.joseluisgs.expedientesacademicos.alumnado.services

import com.github.benmanes.caffeine.cache.Cache
import dev.joseluisgs.expedientesacademicos.alumnado.errors.AlumnoError
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import dev.joseluisgs.expedientesacademicos.alumnado.repositories.AlumnosRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDate
import kotlin.test.Test


@ExtendWith(MockitoExtension::class)
@DisplayName("Alumnos Service Test")
class AlumnosServiceImplTest {
    @Mock
    lateinit var repository: AlumnosRepository

    @Mock
    lateinit var cache: Cache<Long, Alumno>

    @InjectMocks
    lateinit var service: AlumnosServiceImpl

    private val alumno = Alumno(1L, "Pepe", "Perez", "pepe", LocalDate.of(2024, 5, 16), 9.0, false, "pepe")

    @Nested
    @DisplayName("Buscar todos los alumnos")
    inner class FindAll {
        @Test
        @DisplayName("✔ Devuelve lista de alumnos correctamente")
        fun findAllOk() {
            // Given
            whenever(repository.findAll()).thenReturn(listOf(alumno))

            // When
            val result = service.findAll()

            // Then
            assertAll(
                { assertTrue(result.isOk, "El resultado debe ser Ok") },
                { assertEquals(listOf(alumno), result.value, "La lista debe contener el alumno") }
            )

            verify(repository, times(1)).findAll()
        }
    }

    @Nested
    @DisplayName("Buscar alumno por ID")
    inner class FindById {
        @Test
        @DisplayName("✔ Encuentra alumno en caché")
        fun findByIdFromCache() {
            // Given
            whenever(cache.getIfPresent(alumno.id)).thenReturn(alumno)

            // When
            val result = service.findById(alumno.id)

            // Then
            assertAll(
                { assertTrue(result.isOk, "El resultado debe ser Ok") },
                { assertEquals(alumno, result.value, "Debe devolver el alumno correcto") }
            )

            verify(cache, times(1)).getIfPresent(alumno.id)
            verify(repository, never()).findById(any())
        }

        @Test
        @DisplayName("✔ Encuentra alumno en repositorio")
        fun findByIdFromRepository() {
            // Given
            whenever(cache.getIfPresent(alumno.id)).thenReturn(null)
            whenever(repository.findById(alumno.id)).thenReturn(alumno)

            // When
            val result = service.findById(alumno.id)

            // Then
            assertAll(
                { assertTrue(result.isOk, "El resultado debe ser Ok") },
                { assertEquals(alumno, result.value, "Debe devolver el alumno correcto") }
            )

            verify(cache, times(1)).getIfPresent(alumno.id)
            verify(repository, times(1)).findById(alumno.id)
            verify(cache, times(1)).put(alumno.id, alumno)
        }

        @Test
        @DisplayName("❌ No encuentra el alumno")
        fun findByIdNotFound() {
            // Given
            whenever(cache.getIfPresent(alumno.id)).thenReturn(null)
            whenever(repository.findById(alumno.id)).thenReturn(null)

            // When
            val result = service.findById(alumno.id)

            // Then
            assertAll(
                { assertTrue(result.isErr, "El resultado debe ser Error") },
                { assertTrue(result.error is AlumnoError.NotFound, "Debe ser error NotFound") }
            )

            verify(cache, times(1)).getIfPresent(alumno.id)
            verify(repository, times(1)).findById(alumno.id)
        }
    }

    @Nested
    @DisplayName("Guardar alumno")
    inner class Save {
        @Test
        @DisplayName("✔ Guarda alumno correctamente")
        fun saveOk() {
            // Given
            whenever(repository.save(alumno)).thenReturn(alumno)

            // When
            val result = service.save(alumno)

            // Then
            assertAll(
                { assertTrue(result.isOk, "El resultado debe ser Ok") },
                { assertEquals(alumno, result.value, "Debe devolver el alumno guardado") }
            )

            verify(repository, times(1)).save(alumno)
            verify(cache, times(1)).put(alumno.id, alumno)
        }
    }

    @Nested
    @DisplayName("Borrar alumno")
    inner class Delete {
        @Test
        @DisplayName("✔ Borra alumno correctamente")
        fun deleteByIdOk() {
            // Given
            doNothing().whenever(repository).deleteById(alumno.id)

            // When
            val result = service.deleteById(alumno.id)

            // Then
            assertTrue(result.isOk, "El resultado debe ser Ok")

            verify(repository, times(1)).deleteById(alumno.id)
            verify(cache, times(1)).invalidate(alumno.id)
        }
    }

    @Nested
    @DisplayName("Operaciones con múltiples alumnos")
    inner class BulkOperations {
        @Test
        @DisplayName("✔ Guarda lista de alumnos correctamente")
        fun saveAllOk() {
            // Given
            val alumnos = listOf(alumno)
            whenever(repository.saveAll(alumnos)).thenReturn(alumnos)

            // When
            val result = service.saveAll(alumnos)

            // Then
            assertAll(
                { assertTrue(result.isOk, "El resultado debe ser Ok") },
                { assertEquals(alumnos, result.value, "Debe devolver la lista de alumnos guardados") }
            )

            verify(repository, times(1)).saveAll(alumnos)
            verify(cache, times(1)).invalidateAll()
        }

        @Test
        @DisplayName("✔ Borra todos los alumnos correctamente")
        fun deleteAllOk() {
            // Given
            doNothing().whenever(repository).deleteAll()

            // When
            val result = service.deleteAll()

            // Then
            assertTrue(result.isOk, "El resultado debe ser Ok")

            verify(repository, times(1)).deleteAll()
            verify(cache, times(1)).invalidateAll()
        }
    }
}