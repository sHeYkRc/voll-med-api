# voll-med-api

API REST desarollada en Java con Spring Boot para la gestión de una clínica medica. Permite registrar médicos y pacientes, agendar y cancelar consultas, y autenticarse mediante JWT.

---
## Tecnologías utilizadas

- java 17
- Spring boot
- Spring Data JPA
- Spirng Securuty + JWT
- Flyway (migraciones de base de datos)
- MySQl
- Lonbok
- Maven

---

## Funcionalidades pricipales

- Autenticación con JWT
- CRUD de médicos.
- CRUD de pacientes.
- Agendamiento y cancelación de consutas.
- Validación de reglas de negocio.
- Migraciones automáticas con Flyway

---

## ¿Cómo ejecutar el proyecto?

> Esta explicación asume que vas a trabajar con un IDE como IntelliJ o Eclipse.

1. **Clona este repositorio**
   - Desde GitHub o tu IDE:
     ```
     https://github.com/sHeYkRc/voll-med-api.git
     ```

2. **Importa el proyecto como un proyecto Maven**
   - Si usas IntelliJ: `File → Open` y selecciona la carpeta.
   - Si usas Eclipse: `Import → Existing Maven Projects`.

3. **Configura la base de datos**
   - Crea una base de datos en MySQL, por ejemplo:
     ```sql
     CREATE DATABASE vollmed;
     ```
   - Crea un archivo `application.properties` dentro de `src/main/resources/` con lo siguiente:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost/vollmed
     spring.datasource.username=TU_USUARIO
     spring.datasource.password=TU_PASSWORD
     
     spring.jpa.hibernate.ddl-auto=validate
     spring.jpa.show-sql=true
     
     api.security.secret=tu_clave_secreta
     ```

4. **Ejecuta la aplicación desde el IDE**
   - Ubica la clase `VollMedApiApplication.java` y ejecútala como `Spring Boot App`.

> Opcional: puedes compilar el proyecto con Maven (`mvn clean install`) si prefieres usar la terminal, pero no es obligatorio para desarrollo en IDE.

---

## Estado

Proyecto realizado como parte de la formación Oracle ONE Next Education. Repositorio en evolución.
  
