# Proyecto Integrador CRUD


---
## Tabla de Contenidos

- [Introducción](#introducción)
- [Tecnologías](#tecnologías)
- [Base de Datos con Docker (Opcional)](#base-de-datos-con-docker-opcional)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Integrantes](#integrantes)

---

## Introducción

Este proyecto integrador es una aplicación desarrollada en **Java 17** que permite realizar operaciones CRUD sobre registros de personas y sus domicilios asociados.

El backend utiliza **JDBC** para interactuar con una base de datos **MySQL**, la cual se ejecuta en un contenedor **Docker**, junto con **phpMyAdmin** como herramienta de administración visual.

La aplicación está organizada con una arquitectura de capas que incluye clases modelo, DAO y servicios. Además, utiliza **Lombok** para reducir la cantidad de código repetitivo y **Gradle** como sistema de construcción y gestión de dependencias.

---

## Tecnologías
- Java 17
- Gradle
- JDBC
- Lombok
- MySQL
- phpMyAdmin
- Docker

---

## Base de Datos con Docker (Opcional)

Instrucciones para levantar MySQL y phpMyAdmin con `docker-compose`.

#### Levantar los contenedores

- **Primer inicio** (crea y levanta los contenedores):
  ```bash
  docker-compose up -d
  
- **Inicios posteriores** levanta los contenedores ya creados:
  ```bash
   docker-compose start
  
#### Acceder a phpMyAdmin

- Abrir en el navegador la siguiente URL:
  ```bash
  http://localhost:8080

- Ingresar las credenciales:

  - ****Usuario:**** `root`
  - ****Contraseña:**** `rootpassword`

---
## Uso

### 1. Cargar el esquema de la base de datos

El esquema SQL necesario para crear las tablas `persona` y `domicilio` se encuentra en `src/main/resources/db/schema.sql`.

### 2. Configuraciones

Asegúrate de tener configurados los parámetros de conexión a la base de datos (host, puerto, usuario, contraseña) en el archivo de configuración correspondiente.

### 3. Ejecutar la aplicación Java

Ejecutar la clase `main` para iniciar la aplicación.

#### Menú por consola

La aplicación Java cuenta con un menú por consola que permite realizar operaciones CRUD sobre personas y domicilios:

- Registrar una nueva persona con su domicilio.
- Consultar todas las personas y domicilios registrados.
- Modificar datos de personas o domicilios.
- Eliminar registros.

---
## Estructura del Proyecto

- `src/main/java`: código fuente de la aplicación
- `src/main/resources`: script SQL (`schema.sql`)
- `docker-compose.yml`: definición de servicios Docker para levantar MySQL y phpMyAdmin
- `build.gradle.kts`: archivo de configuración de Gradle con las dependencias y ajustes del proyecto

---

## Integrantes

- Maricchiolo Guadalupe
- Barrera Miguel
- Barros Emilia
- Frankenberger Federico 

---

