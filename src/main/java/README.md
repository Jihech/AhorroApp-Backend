# 💰 AhorroApp

AhorroApp es una aplicación móvil y web desarrollada con el objetivo de ayudar a los usuarios a **gestionar sus finanzas personales**, registrar ingresos, gastos, deudas, metas de ahorro y recibir notificaciones importantes.

---

- Jimmy Herencia Chambi

---
## 🚀 Tecnologías utilizadas

### 🧠 Backend
- Java 17
- Spring Boot 
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL (pgAdmin)
- Thymeleaf (para el backoffice web)

### Frontend Spring boot
- JavaScript(Nativo)
- HTML5
- Thymeleaf
- Css

### 📱 Frontend Android
- Java (Android Studio)
- Retrofit2
- Arquitectura MVVM (parcialmente implementada)

---

## 📦 Estructura del Proyecto

### 📂 Entidades principales

- `Usuario`: modelo base para login, registro y control de autenticación.
- `Movimiento`: fusión de ingresos y gastos. Tiene tipo, monto, descripción y fecha.
- `Categoria`: permite clasificar los movimientos por tipo.
- `Deuda`: controla deudas pendientes, monto, fecha límite y estado de pago.
- `MetaAhorro`: objetivos financieros con montos y fechas objetivo.
- `Notificacion`: sistema interno para alertas sobre pagos o metas próximas.
<!--
- `UsuarioResponse`: clase auxiliar para respuestas, omitida en backend persistente.
-->

---

## 🔒 Seguridad

- Se implementó **Spring Security** con autenticación basada en correo y clave.
- Acceso restringido a rutas internas (`/dashboard`, etc.).
- Las rutas móviles (`/api/**`) son públicas por ahora, ya que no se ha integrado sesión desde Android.

---

## 🌐 Funcionalidades actuales

| Funcionalidad                      | Web           | Android       |
|-----------------------------------|---------------|----------------|
| Registro de usuarios              | ✅ (modal)     | ✅ (pantalla)   |
| Login de usuarios                 | ✅ (modal)     | ✅ (con Retrofit) |
| Registrar movimientos             | ✅             | ✅              |
| Visualización de movimientos      | ✅             | ✅              |
| Categorías de movimientos         | ⏳             | ⏳              |
| Registro de deudas                | ⏳             | ⏳              |
| Metas de ahorro                   | ⏳             | ⏳              |
| Notificaciones (alertas)          | ⏳             | ⏳              |

> ⏳ = pendiente de implementación

---
## Ruta Retrofit
BASE_URL = "http://10.0.2.2:8081/api/"

### Última actualización: 
Agregadas entidades Deuda y MetaAhorro

---