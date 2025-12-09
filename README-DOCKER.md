# 🐳 Docker Setup - Gestor de Finanzas

## Prerrequisitos

- Docker Desktop instalado
- Docker Compose instalado

## 🚀 Inicio Rápido

### 1. Configurar Variables de Entorno

Copia el archivo de ejemplo:
```bash
cp .env.example .env
```

Edita `.env` con tus valores:
```env
DB_USERNAME=gestoruser
DB_PASSWORD=tu_password_seguro
JWT_SECRET_KEY=tu_clave_jwt_super_segura
ADMIN_USERNAME=admin
ADMIN_PASSWORD=tu_password_admin
```

### 2. Levantar los Servicios

```bash
docker-compose up -d
```

Esto levantará:
- MySQL en el puerto `3307`
- API en el puerto `8080`

### 3. Verificar que Está Corriendo

```bash
# Ver logs
docker-compose logs -f app

# Ver estado de contenedores
docker-compose ps

# Verificar salud de la API
curl http://localhost:8080/actuator/health
```

## 📋 Comandos Útiles

### Construir solo la imagen
```bash
docker-compose build
```

### Detener servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes (⚠️ borra la BD)
```bash
docker-compose down -v
```

### Ver logs en tiempo real
```bash
# Todos los servicios
docker-compose logs -f

# Solo la app
docker-compose logs -f app

# Solo MySQL
docker-compose logs -f mysql
```

### Reiniciar un servicio
```bash
docker-compose restart app
```

### Ejecutar comandos dentro del contenedor
```bash
# Acceder al contenedor de la app
docker-compose exec app sh

# Acceder a MySQL
docker-compose exec mysql mysql -u root -p
```

## 🔧 Configuración

### Variables de Entorno Disponibles

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_USERNAME` | Usuario de MySQL | `gestoruser` |
| `DB_PASSWORD` | Contraseña de MySQL | `rootpassword` |
| `JWT_SECRET_KEY` | Clave secreta para JWT | (ver .env.example) |
| `JWT_ISSUER` | Emisor del token JWT | `GESTORFINANZAS-SEC` |
| `ADMIN_USERNAME` | Usuario administrador | `admin` |
| `ADMIN_PASSWORD` | Contraseña admin | `changeme` |
| `JPA_DDL_AUTO` | Modo JPA (update/validate/none) | `update` |

### Cambiar Puerto de la API

Edita `docker-compose.yml`:
```yaml
ports:
  - "9090:8080"  # API en puerto 9090
```

### Aumentar Memoria de Java

Edita `docker-compose.yml`:
```yaml
environment:
  JAVA_OPTS: "-Xmx1024m -Xms512m"
```

## 🗄️ Base de Datos

### Acceder a MySQL desde el Host

```bash
mysql -h 127.0.0.1 -P 3307 -u gestoruser -p
```

### Backup de la Base de Datos

```bash
docker-compose exec mysql mysqldump -u root -p gestorfinanzas > backup.sql
```

### Restaurar desde Backup

```bash
docker-compose exec -T mysql mysql -u root -p gestorfinanzas < backup.sql
```

## 🐛 Troubleshooting

### La app no puede conectarse a MySQL

1. Verifica que MySQL esté listo:
   ```bash
   docker-compose logs mysql
   ```

2. Espera a que MySQL complete la inicialización (puede tardar 30-60 segundos)

3. Reinicia la app:
   ```bash
   docker-compose restart app
   ```

### Puerto 3306 o 8080 ya en uso

Cambia los puertos en `docker-compose.yml`:
```yaml
ports:
  - "3308:3306"  # MySQL
  - "8081:8080"  # App
```

### Ver logs detallados

```bash
docker-compose logs -f --tail=100 app
```

### Limpiar todo y empezar de cero

```bash
docker-compose down -v
docker system prune -a
docker-compose up -d --build
```

## 📦 Producción

### Cambios Recomendados para Producción

1. **Cambiar JPA_DDL_AUTO a `validate` o `none`**
2. **Usar contraseñas fuertes**
3. **Generar nueva JWT_SECRET_KEY**
4. **No exponer puerto de MySQL**
5. **Configurar HTTPS con proxy inverso (nginx)**

### Ejemplo de .env para Producción

```env
DB_USERNAME=produser
DB_PASSWORD=SuperSecurePassword123!
JWT_SECRET_KEY=RandomGeneratedKey64CharactersLongMinimum
ADMIN_PASSWORD=AnotherSecurePassword456!
JPA_DDL_AUTO=validate
```

## 🔐 Seguridad

- ⚠️ **Nunca** commitear el archivo `.env`
- ⚠️ **Cambiar** todas las contraseñas por defecto
- ⚠️ **Generar** una nueva JWT_SECRET_KEY para producción
- ✅ El archivo `.env` ya está en `.gitignore`

## 📝 Notas

- El contenedor usa un usuario no-root por seguridad
- Los datos de MySQL persisten en un volumen Docker
- La app espera a que MySQL esté listo antes de iniciar
- Healthchecks configurados para ambos servicios
