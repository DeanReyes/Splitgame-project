Una aplicación web full-stack para decidir de manera aleatoria quién paga la cuenta en reuniones con amigos. Construida con arquitectura hexagonal en el backend y componentes standalone en Angular 17.
📋 Tabla de Contenidos

Características
Tecnologías
Arquitectura
Prerequisitos
Instalación
Configuración
Ejecución
API Endpoints
Estructura del Proyecto
Capturas de Pantalla
Contribuir
Licencia

✨ Características

🎲 Selección Aleatoria: Elige al azar quién paga entre los participantes
👥 Gestión de Personas: Crea, lista y elimina participantes
📊 Historial de Juegos: Registro de todos los juegos realizados
🎨 Interfaz Moderna: UI responsive y atractiva
🔄 Tiempo Real: Actualización automática de listas
✅ Validaciones: Validación robusta en frontend y backend
🏗️ Arquitectura Limpia: Separación de responsabilidades (Hexagonal Architecture)

🛠 Tecnologías
Backend

Java 17
Spring Boot 3.2
Spring Data JPA
PostgreSQL
Lombok
Maven

Frontend

Angular 17 (Standalone Components)
TypeScript
RxJS
CSS3

Base de Datos

PostgreSQL 15+

📦 Prerequisitos
Antes de comenzar, asegúrate de tener instalado:

Java 17 o superior - Descargar
Node.js 18+ y npm - Descargar
PostgreSQL 15+ - Descargar
Maven 3.8+ - Descargar
Angular CLI - npm install -g @angular/cli
Git - Descargar

🚀 Instalación

1. Clonar el repositorio
   git clone https://github.com/tu-usuario/splitgame.git
   cd splitgame

2. Configurar Base de Datos
   -- Conectarse a PostgreSQL
   psql -U postgres

   -- Crear la base de datos
   CREATE DATABASE splitgame;

  -- Ejecutar el script de creación de tablas
  \c splitgame
  \i database/schema.sql

3. Configurar Backend
   cd splitgame-backend

  # Editar application.properties
  # Actualiza las credenciales de PostgreSQL
  nano src/main/resources/application.properties

Configura:
spring.datasource.url=jdbc:postgresql://localhost:5432/splitgame
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# Instalar dependencias
mvn clean install

cd ../splitgame-frontend

4. Configurar Frontend
# Instalar dependencias
npm install

⚙️ Configuración
Variables de Entorno - Backend
Edita src/main/resources/application.properties:
# Base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/splitgame
spring.datasource.username=postgres
spring.datasource.password=your_password

# Puerto del servidor
server.port=8080

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

Variables de Entorno - Frontend
Edita src/environments/environment.ts:
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};

🎮 Ejecución
Iniciar Backend
cd splitgame-backend
mvn spring-boot:run
El backend estará disponible en: http://localhost:8080

Iniciar Frontend
cd splitgame-frontend
ng serve
El frontend estará disponible en: http://localhost:4200
