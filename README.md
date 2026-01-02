# 💰 FinanPro - Sistema de Gestión de Finanzas Personales

> **API REST** completa para gestión de finanzas personales con autenticación JWT, sistema de roles/permisos, integración con API externa del dólar argentino y arquitectura escalable.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Auth-red.svg)](https://jwt.io/)

---

## 📖 Sobre el Proyecto

**FinanPro** es una API REST backend para gestión de finanzas personales que desarrollé como proyecto de portfolio. La aplicación permite a los usuarios llevar un control detallado de sus ingresos, gastos, balances y movimientos financieros, con un sistema robusto de autenticación y autorización.

### 🎯 Objetivo del Proyecto
Crear una solución completa de backend que integre las mejores prácticas de desarrollo con Spring Boot, implementando seguridad avanzada, arquitectura en capas y consumo de APIs externas.

---

## 🚀 Stack Tecnológico

### **Backend & Framework**
- **Java 17** - Programación orientada a objetos moderna
- **Spring Boot 3.4.3** - Framework principal
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **Hibernate** - ORM para mapeo objeto-relacional

### **Seguridad**
- **JWT (JSON Web Tokens)** - Autenticación stateless
- **BCrypt** - Encriptación de contraseñas
- **Bean Validation** - Validaciones robustas de datos

### **Base de Datos**
- **MySQL 8.0** - Base de datos relacional
- **JPA Repositories** - Acceso a datos

### **Herramientas & DevOps**
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código boilerplate
- **Bucket4j** - Rate limiting
- **Docker** - Containerización (disponible)

### **APIs Externas**
- **DolarAPI** - Cotizaciones del dólar en Argentina

---

## 🎓 Aprendizajes Clave

A lo largo del desarrollo de este proyecto, profundicé y apliqué conocimientos en:

### 1. **Spring Security & JWT** 🔐
- Implementación completa de autenticación con JSON Web Tokens
- Sistema de **Roles y Permisos** granular (RBAC)
- Filtros personalizados para validación de tokens (`JwtTokenValidator`)
- **Token Blacklist** para logout seguro
- Gestión de sesiones stateless
- Configuración de CORS personalizada

**Desafío superado**: Entender el flujo completo de autenticación desde la petición HTTP hasta la validación del token y la autorización basada en roles.

### 2. **Arquitectura en Capas** 🏗️
- **Controller** → **Service** → **Repository** → **Model**
- Separación clara de responsabilidades
- Uso de **DTOs** (Data Transfer Objects) para transferencia segura de datos
- Patrón Repository para acceso a datos
- Inyección de dependencias con Spring

**Aprendí**: La importancia de desacoplar la lógica de negocio de la capa de presentación y persistencia.

### 3. **Validaciones & Seguridad de Datos** ✅
- Bean Validation con anotaciones (`@NotBlank`, `@Size`, `@Pattern`)
- Validaciones personalizadas de contraseñas seguras
- DTOs específicos para entrada de datos (`CreateUserDTO`, `AuthLoginRequestDTO`)
- Protección contra inyección SQL con JPA
- Ocultación de contraseñas en respuestas JSON (`@JsonProperty`)

**Problema resuelto**: Cómo validar datos ANTES de encriptar contraseñas, separando la validación del modelo de persistencia.

### 4. **Relaciones JPA & Base de Datos** 💾
- Relaciones **@OneToOne** (Usuario-Balance)
- Relaciones **@OneToMany** (Usuario-Categorías, Usuario-Movimientos)
- Relaciones **@ManyToMany** (Usuario-Roles, Rol-Permisos)
- Manejo de cascadas y huérfanos
- Prevención de referencias circulares con `@JsonManagedReference` / `@JsonBackReference`
- Creación automática de entidades relacionadas con `@PrePersist`

**Desafío**: Diseñar un esquema de base de datos normalizado que soporte un sistema complejo de permisos.

### 5. **Consumo de APIs Externas** 🌐
- Integración con **DolarAPI** para cotizaciones en tiempo real
- Uso de `RestTemplate` / `WebClient`
- Caché de respuestas con `@Cacheable`
- Manejo de errores en llamadas HTTP

**Implementé**: Un servicio que consume datos externos y los expone a través de mi API.

### 6. **Rate Limiting & Performance** ⚡
- Implementación de **Rate Limiter** con Bucket4j
- Filtro personalizado para limitar peticiones por IP
- Protección contra ataques de fuerza bruta
- Configuración de límites por endpoint

**Aprendizaje**: Cómo proteger una API pública de abuso sin afectar usuarios legítimos.

### 7. **Buenas Prácticas & Código Limpio** ✨
- Nomenclatura clara y descriptiva
- Comentarios donde son necesarios
- Manejo de excepciones apropiado
- Uso de Lombok para reducir boilerplate
- Configuración externalizada en `application.properties`
- Separación de configuraciones (CORS, Security, Rate Limit)

---

## 📂 Estructura del Proyecto

```
src/main/java/com/lisandro/gestorfinanzas/
├── 📁 config/                    # Configuraciones
│   ├── CorsConfig.java          # Configuración CORS
│   └── RateLimitConfig.java     # Configuración de Rate Limiting
│
├── 📁 controller/                # Controladores REST
│   ├── AuthenticationController.java  # Login/Logout
│   ├── UserController.java            # Gestión de usuarios
│   ├── RoleController.java            # Gestión de roles
│   ├── PermissionController.java      # Gestión de permisos
│   ├── BalanceController.java         # Gestión de balances
│   ├── CategoryController.java        # Gestión de categorías
│   ├── MovementController.java        # Gestión de movimientos
│   └── DolarController.java           # Cotizaciones del dólar
│
├── 📁 dto/                       # Data Transfer Objects
│   ├── AuthLoginRequestDTO.java
│   ├── AuthResponseDTO.java
│   ├── CreateUserDTO.java       # DTO para creación de usuarios
│   ├── BalanceDTO.java
│   └── Movement/
│
├── 📁 model/                     # Entidades JPA
│   ├── UserSec.java             # Usuario del sistema
│   ├── Role.java                # Roles de usuario
│   ├── Permission.java          # Permisos granulares
│   ├── Balance.java             # Balance financiero
│   ├── Category.java            # Categorías de gastos/ingresos
│   ├── Movement.java            # Movimientos financieros
│   └── Stock.java               # Inversiones (futuro)
│
├── 📁 repository/                # Repositorios JPA
│   ├── IUserRepository.java
│   ├── IRoleRepository.java
│   └── ...
│
├── 📁 service/                   # Lógica de negocio
│   ├── auth/                    # Servicios de autenticación
│   │   ├── UserDetailsServiceImp.java
│   │   └── TokenBlacklistService.java
│   ├── user/                    # Servicios de usuarios
│   ├── role/                    # Servicios de roles
│   ├── balance/                 # Servicios de balances
│   ├── category/                # Servicios de categorías
│   ├── movement/                # Servicios de movimientos
│   └── dolarapi/                # Integración con DolarAPI
│
├── 📁 Security/                  # Configuración de seguridad
│   ├── SecurityConfig.java      # Config principal de Spring Security
│   └── Filter/
│       └── JwtTokenValidator.java  # Validador de tokens JWT
│
├── 📁 filter/                    # Filtros HTTP
│   └── RateLimitFilter.java    # Rate limiting por IP
│
├── 📁 utils/                     # Utilidades
│   └── JwtUtils.java            # Generación y validación de JWT
│
└── GestorFinanzasApplication.java
```

---

## 📌 Funcionalidades Implementadas

### 🔐 Autenticación & Autorización
- ✅ Login con JWT
- ✅ Logout con blacklist de tokens
- ✅ Registro de usuarios con validaciones
- ✅ Sistema de roles y permisos (RBAC)
- ✅ Endpoints protegidos según roles

### 👥 Gestión de Usuarios
- ✅ CRUD completo de usuarios
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Validaciones de seguridad (username, password)
- ✅ Tutorial de onboarding (completar/skipear)

### 💼 Gestión Financiera
- ✅ Balance por usuario (ARS y USD)
- ✅ Categorías personalizadas de gastos/ingresos
- ✅ Movimientos financieros con tracking completo
- ✅ Consulta de cotizaciones del dólar (DolarAPI)

### 🔧 Características Técnicas
- ✅ Rate Limiting por IP
- ✅ CORS configurado para frontend
- ✅ Caché de respuestas externas
- ✅ Validaciones con Bean Validation
- ✅ DTOs para transferencia segura de datos

---

## 🎯 Roadmap Futuro

- [ ] Apartado de estadisticas mas completo
- [ ] Metas de ahorro
- [ ] Recordatorios de pagos
- [ ] Integración con servicios de inversión
- [ ] Notificaciones por email
- [ ] Gastos fijos

---


## 🛠️ Instalación y Configuración

### **Prerequisitos**
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- (Opcional) Docker

### **1️⃣ Clonar el repositorio**
```bash
git clone https://github.com/Lichu0800/GestorFinanzas-API.git
cd GestorFinanzas-API
```

### **2️⃣ Configurar Base de Datos**
Crear la base de datos en MySQL:
```sql
CREATE DATABASE gestorfinanzas;
```

Configurar `src/main/resources/application.properties`:
```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/gestorfinanzas?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
security.jwt.private.key=tu_clave_secreta_aqui
security.jwt.user.generator=GESTORFINANZAS-SEC
```

### **3️⃣ Compilar y Ejecutar**
```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### **4️⃣ (Opcional) Docker**
```bash
# Construir imagen
docker build -t finanpro-api .

# Ejecutar contenedor
docker-compose up
```

---

## 📡 Endpoints Principales

### **Autenticación** (`/auth`)
```http
POST /auth/login       # Login (público)
POST /auth/logout      # Logout (requiere token)
```

### **Usuarios** (`/api/users`)
```http
POST   /api/users                    # Crear usuario (público)
PUT    /api/users/tutorial/complete  # Marcar tutorial completo (auth)
GET    /api/users/tutorial           # Estado del tutorial (auth)
```

### **Roles** (`/api/roles`)
```http
GET    /api/roles       # Listar roles (auth)
POST   /api/roles       # Crear rol (admin)
GET    /api/roles/{id}  # Obtener rol (auth)
```

### **Balances** (`/api/balance`)
```http
GET    /api/balance            # Obtener balance del usuario (auth)
PUT    /api/balance/update     # Actualizar balance (auth)
```

### **Categorías** (`/api/categories`)
```http
GET    /api/categories         # Listar categorías del usuario (auth)
POST   /api/categories         # Crear categoría (auth)
DELETE /api/categories/{id}    # Eliminar categoría (auth)
```

### **Movimientos** (`/api/movements`)
```http
GET    /api/movements          # Listar movimientos del usuario (auth)
POST   /api/movements          # Crear movimiento (auth)
GET    /api/movements/{id}     # Obtener movimiento (auth)
DELETE /api/movements/{id}     # Eliminar movimiento (auth)
```

### **Dólar** (`/api/dolar`)
```http
GET    /api/dolar              # Cotizaciones del dólar (público)
```

---

## 🧪 Testing

Puedes probar la API con herramientas como:
- **Postman** / **Insomnia**
- **curl**
- **Thunder Client** (VS Code)

Ejemplo de login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin1234!"
  }'
```

---

## 🤝 Contribuciones

Este es un proyecto de portfolio personal, pero si tienes sugerencias o encuentras algún bug, no dudes en abrir un **issue** o **pull request**.

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

⭐ **Si te gustó este proyecto, no olvides darle una estrella en GitHub!**

---

## 🤝 Contribuir
Si deseas contribuir a este proyecto, puedes hacer un **fork**, crear una rama con tus cambios y enviar un pull request. 🙌

---

## 👤 Autor
💡 **Lisandro** - [GitHub](https://github.com/LujanLisandro)

---

## 📜 Licencia
Este proyecto está bajo la licencia **MIT**.

