# AhorroApp - Backend y Panel Administrativo

Este proyecto contiene el **backend REST API** desarrollado en **Spring Boot** y el **panel de administración (backoffice)** desarrollado con **Thymeleaf + SB Admin 2**.

## 🔧 Funcionalidades principales

- API REST para usuarios y movimientos (ingresos/gastos)
- Seguridad con Spring Security (login con correo y clave)
- Control de sesiones
- CRUD de usuarios y movimientos
- Panel web responsive con SB Admin 2 y Thymeleaf

## 📁 Estructura del proyecto
ahorroapp-backend/
├── controller/
├── entidad/
├── repository/
├── service/
├── security/
├── templates/
└── static/

## 🔐 Autenticación

- Basada en Spring Security
- Se usa correo + clave para login
- Acceso protegido al dashboard (`/index`)

## 🧪 Herramientas de prueba

- Postman para pruebas de endpoints
- Navegador para probar el panel admin

## ▶️ Requisitos

- Java 17+
- Spring Boot 3+
- PostgreSQL o pgAdmin configurado
- Maven

## 🧰 Cómo ejecutar

1. Clona el repositorio
2. Configura `application.properties` con tu conexión a PostgreSQL
3. Ejecuta la app desde tu IDE
4. Accede al panel en: [http://localhost:8081/](http://localhost:8081/)
5. La API estará disponible en: `/api/...`

---