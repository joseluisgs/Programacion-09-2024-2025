CREATE TABLE IF NOT EXISTS Alumnos (
    id IDENTITY PRIMARY KEY,
    apellidos VARCHAR NOT NULL,
    nombre VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    fechaNacimiento DATE NOT NULL,
    calificacion DOUBLE NOT NULL,
    repetidor BOOLEAN NOT NULL,
    imagen VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
