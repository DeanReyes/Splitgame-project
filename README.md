# 🎲 Splitgame Project

Una **aplicación web full-stack** para decidir de manera aleatoria quién paga la cuenta en reuniones con amigos.  
Desarrollada con **arquitectura hexagonal** en el backend y **componentes standalone de Angular 17** en el frontend.

---

## 📚 Tabla de Contenidos

- [✨ Características](#-características)
- [🛠 Tecnologías](#-tecnologías)
- [📦 Prerrequisitos](#-prerrequisitos)
- [🚀 Instalación](#-instalación)
- [⚙️ Configuración](#️-configuración)
- [🎮 Ejecución](#-ejecución)
- [📡 API Endpoints](#-api-endpoints)
- [📂 Estructura del Proyecto](#-estructura-del-proyecto)
- [🖼️ Capturas de Pantalla](#️-capturas-de-pantalla)
- [🤝 Contribuir](#-contribuir)
- [📄 Licencia](#-licencia)

---

## ✨ Características

- 🎲 **Selección Aleatoria:** Elige al azar quién paga entre los participantes.  
- 👥 **Gestión de Personas:** Crea, lista y elimina jugadores.  
- 📊 **Historial de Juegos:** Registro de todas las partidas realizadas.  
- 🎨 **Interfaz Moderna:** Diseño responsive y atractivo.  
- 🔄 **Actualización en Tiempo Real:** Refrescado automático de listas.  
- ✅ **Validaciones Completas:** En frontend y backend.  
- 🧱 **Arquitectura Limpia:** Basada en principios de la *Hexagonal Architecture*.  

---

## 🛠 Tecnologías

### Backend
- Java 17  
- Spring Boot 3.2  
- Spring Data JPA  
- PostgreSQL  
- Lombok  
- Maven  

### Frontend
- Angular 17 (Standalone Components)  
- TypeScript  
- RxJS  
- CSS3  

### Base de Datos
- PostgreSQL 15+  

---

## 📦 Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

| Herramienta | Versión mínima | Enlace |
|--------------|----------------|--------|
| Java | 17+ | [Descargar](https://adoptium.net) |
| Node.js | 18+ | [Descargar](https://nodejs.org) |
| PostgreSQL | 15+ | [Descargar](https://www.postgresql.org/download/) |
| Maven | 3.8+ | [Descargar](https://maven.apache.org/download.cgi) |
| Angular CLI | — | `npm install -g @angular/cli` |
| Git | — | [Descargar](https://git-scm.com/downloads) |

---

## 🚀 Instalación

1️⃣ **Clonar el repositorio**
git clone https://github.com/DeanReyes/Splitgame-project.git
cd Splitgame-project

2️⃣ **Configurar la Base de Datos**
-- Conectarse a PostgreSQL
psql -U postgres

-- Crear la base de datos
CREATE DATABASE splitgame;

-- Ejecutar script de tablas
\c splitgame
\i database/schema.sql

3️⃣ **Configurar Backend**
cd splitgame-backend

Editar src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/splitgame
spring.datasource.username=postgres
spring.datasource.password=your_password

Instalar dependencias
mvn clean install

4️⃣ **Configurar Frontend**
cd ../splitgame-frontend
npm install

⚙️ **Configuración**
Backend

Archivo: src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/splitgame
spring.datasource.username=postgres
spring.datasource.password=your_password

server.port=8080
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

Frontend

Archivo: src/environments/environment.ts

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};

🎮 **Ejecución**
cd splitgame-backend
mvn spring-boot:run

cd splitgame-frontend
ng serve
