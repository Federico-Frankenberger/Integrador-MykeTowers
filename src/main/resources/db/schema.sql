CREATE TABLE domicilio (
                           id_domicilio INT AUTO_INCREMENT PRIMARY KEY,
                           calle VARCHAR(100) NOT NULL,
                           numero VARCHAR(20) NOT NULL,
                           ciudad VARCHAR(50) NOT NULL,
                           provincia VARCHAR(50),
                           codigo_postal VARCHAR(10)
);

CREATE TABLE persona (
                         id_persona INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         fecha_nacimiento DATE,
                         id_domicilio INT,
                         CONSTRAINT fk_domicilio FOREIGN KEY (id_domicilio) REFERENCES domicilio(id_domicilio)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE
);