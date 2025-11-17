# GamerStore API

API RESTful para la gestión de una tienda de productos gaming y contenido de blog relacionado.

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Variables de entorno configuradas (ver sección de Configuración)

## Configuración

### Variables de Entorno

Configurar las siguientes variables en el sistema o en el IDE:

```
DB_USER=<usuario_base_datos>
DB_PASS=<contraseña_base_datos>
DB_URL=<host>:<puerto>/<nombre_base_datos>
JWT_SECRET=<clave_secreta_jwt_minimo_256_bits>
JWT_EXPIRATION=3600000
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

### Configuración de VM Options (IntelliJ IDEA)

Para ejecutar con el perfil local:

```
-Dspring.profiles.active=local
```

### Base de Datos

1. Crear base de datos MySQL
2. Ejecutar la aplicación - Hibernate creará las tablas automáticamente
3. Los datos iniciales se cargarán desde `src/main/resources/data.sql`

## Ejecución

### Desarrollo Local

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Construcción

```bash
./mvnw clean package
java -jar target/gamer-store-back-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en `http://localhost:8080`

## Autenticación

### Flujo de Autenticación

1. **Registro de Usuario** (opcional - usuarios de prueba ya existen)
   - Endpoint: `POST /api/users`
   - Público, no requiere autenticación

2. **Login**
   - Endpoint: `POST /api/auth/login`
   - Body:
     ```json
     {
       "email": "admin@admin.com",
       "password": "admin123"
     }
     ```
   - Respuesta: Token JWT

3. **Uso del Token**
   - Incluir en header: `Authorization: Bearer <token>`
   - Requerido para endpoints protegidos (CREATE, UPDATE, DELETE)

### Usuarios de Prueba

#### Administrador
- Email: `admin@admin.com`
- Password: `admin123`
- Roles: ROLE_USER, ROLE_ADMIN

#### Usuario Regular
- Email: `usuario@duoc.cl`
- Password: `password123`
- Roles: ROLE_USER

## Endpoints Principales

### Públicos (Sin Autenticación)

- `GET /api/products` - Listar todos los productos
- `GET /api/products/{id}` - Obtener producto por ID
- `GET /api/products/category/{category}` - Filtrar por categoría
- `GET /api/products/search?name={name}` - Buscar productos
- `GET /api/blog-posts` - Listar artículos del blog
- `GET /api/blog-posts/{id}` - Obtener artículo por ID
- `GET /api/blog-posts/tag/{tag}` - Filtrar por etiqueta
- `GET /api/blog-posts/author/{author}` - Filtrar por autor
- `POST /api/auth/login` - Autenticación

### Protegidos (Requieren Autenticación)

- `GET /api/users` - Listar usuarios
- `GET /api/users/{id}` - Obtener usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Protegidos (Requieren Rol ADMIN)

- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto
- `POST /api/blog-posts` - Crear artículo
- `PUT /api/blog-posts/{id}` - Actualizar artículo
- `DELETE /api/blog-posts/{id}` - Eliminar artículo
- `GET/POST/PUT/DELETE /api/authorities/*` - Gestión de roles

## Estructura de Datos

### Categorías de Productos

- Juego de Mesa
- Periférico Gamer
- Consola

### Roles de Usuario

- `ROLE_USER` - Acceso básico a endpoints protegidos
- `ROLE_ADMIN` - Acceso completo, incluyendo operaciones CRUD

## Documentación API

- OpenAPI Specification: `src/main/resources/openapi.yaml`
- Postman Collection: `GamerStoreBack.postman_collection.json`

### Importar Colección Postman

1. Abrir Postman
2. Import > File > Seleccionar `GamerStoreBack.postman_collection.json`
3. Configurar variable `base_url` (por defecto: `http://localhost:8080`)
4. Ejecutar endpoint "Login - Admin" para obtener token automáticamente

## Consideraciones Importantes

### URLs con Espacios

Las categorías y etiquetas con espacios deben codificarse en URLs:
- Correcto: `/api/products/category/Juego%20de%20Mesa`
- Incorrecto: `/api/products/category/Juego de Mesa`

Postman codifica automáticamente. En navegadores o clientes HTTP, usar `encodeURIComponent()`.

### Seguridad

- JWT configurado para expirar en 24 horas (configurable)
- Contraseñas encriptadas con BCrypt
- CORS configurado para orígenes específicos
- Endpoints públicos: productos y blog (lectura)
- Endpoints protegidos: gestión de usuarios y contenido

### Datos Iniciales

La aplicación carga automáticamente:
- 2 usuarios (admin y usuario regular)
- 31 productos (juegos de mesa, periféricos, consolas)
- 11 artículos de blog con etiquetas

## Troubleshooting

### Error de Conexión a Base de Datos

Verificar variables de entorno y que MySQL esté ejecutándose.

### Error "sql_require_primary_key"

La aplicación ya maneja este requisito. Si persiste, verificar versión MySQL 8.0+.

### Token JWT Inválido

- Verificar que el token no haya expirado
- Confirmar formato: `Authorization: Bearer <token>`
- Generar nuevo token con endpoint de login

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/cl/maotech/gamerstoreback/
│   │   ├── config/          # Configuración (Security, JWT, CORS)
│   │   ├── controller/      # Endpoints REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Manejo de excepciones
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Interfaces JPA
│   │   ├── service/         # Lógica de negocio
│   │   └── util/            # Utilidades (JWT)
│   └── resources/
│       ├── application.yml  # Configuración principal
│       ├── application-local.yml  # Configuración local
│       ├── data.sql         # Datos iniciales
│       └── openapi.yaml     # Especificación OpenAPI
```
