DELETE
FROM Alumnos;

-- Asignar valores a las columnas de las tablas autonumericos para reiniciar el contador

ALTER TABLE Alumnos
    ALTER COLUMN id RESTART WITH 1;

-- Datos de prueba
INSERT INTO Alumnos (apellidos, nombre, email, fechaNacimiento, calificacion, repetidor, imagen, created_at,
                     updated_at)
VALUES ('García Pérez', 'Juan', 'juan@correo.com', '1990-01-01', 7.5, FALSE, '1682763355431.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500'),
       ('López Gómez', 'María', 'maria@correo.com', '1991-02-02', 8.5, FALSE, '1682763355432.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500'),
       ('González Álvarez', 'Luis', 'luis@correo.com', '1990-03-03', 9.5, TRUE, '1682763355433.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500'),
       ('Rodríguez Fernández', 'Ana', 'ana@correo.com', '1992-04-04', 5.5, FALSE, '1682763355434.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500'),
       ('Martínez Sánchez', 'José', 'jose@correo.com', '1991-05-05', 2.5, TRUE, '1682763355435.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500'),
       ('Sánchez Díaz', 'Pedro', 'pedro@correo.com', '1993-06-06', 6.5, FALSE, '1682763355436.png',
        '2024-04-26T18:54:47.097563500', '2024-04-26T18:54:47.097563500');
