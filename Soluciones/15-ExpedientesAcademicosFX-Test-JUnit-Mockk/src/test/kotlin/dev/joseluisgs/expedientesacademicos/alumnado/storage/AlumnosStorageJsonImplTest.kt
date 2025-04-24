package dev.joseluisgs.expedientesacademicos.alumnado.storage

import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import kotlin.test.Test

@DisplayName("Tests de AlumnosStorageJson")
class AlumnosStorageJsonImplTest {
    private lateinit var alumnosStorageJson: AlumnosStorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        alumnosStorageJson = AlumnosStorageJsonImpl()
        myFile = Files.createTempFile("alumnos", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    @DisplayName("Almacenar datos en JSON")
    fun almacenarDatosJson() {
        // Given
        val data = listOf(
            Alumno(1L, "Pepe", "Perez", "pepe", LocalDate.of(2024, 5, 16), 9.0, false, "pepe")
        )

        // When
        val result = alumnosStorageJson.storeDataJson(myFile, data)

        // Then
        assertAll(
            { assertTrue(result.isOk, "El resultado debería ser correcto") },
            { assertEquals(data.size.toLong(), result.value, "El número de elementos almacenados debe coincidir") },
            { assertTrue(myFile.exists(), "El archivo debería existir después de almacenar los datos") },
            { assertTrue(myFile.readText().isNotEmpty(), "El archivo no debería estar vacío") },
            { assertTrue(myFile.readText().contains("Pepe"), "El archivo debería contener el nombre del alumno") },
            { assertTrue(myFile.readText().contains("Perez"), "El archivo debería contener el apellido del alumno") }
        )
    }

    @Test
    @DisplayName("Cargar datos desde JSON")
    fun cargarDatosJson() {
        // Given
        val json = """
        [
          {
            "id": 1,
            "apellidos": "Pepe",
            "nombre": "Perez",
            "email": "pepe",
            "fechaNacimiento": "2024-05-16",
            "calificacion": 9.0,
            "repetidor": false,
            "imagen": "pepe",
            "createdAt": "2025-04-04T20:11:16.296848400",
            "updatedAt": "2025-04-04T20:11:16.296848400"
          }
        ]
    """.trimIndent()

        myFile.writeText(json)

        // When
        val result = alumnosStorageJson.loadDataJson(myFile)

        // Then
        assertAll(
            { assertTrue(result.isOk, "El resultado debería ser correcto") },
            { assertEquals(1, result.value.size, "El número de elementos cargados debe coincidir") },
        )
    }

}