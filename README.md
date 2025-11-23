# GamerStore API

API RESTful para la gestión de una tienda de productos gaming y contenido de blog relacionado. Incluye gestión de inventario, stock y sistema de órdenes de compra.

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
   - Respuesta: Token JWT con roles del usuario
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "type": "Bearer",
       "email": "admin@admin.com",
       "name": "Admin User",
       "roles": ["ROLE_USER", "ROLE_ADMIN"]
     }
     ```

3. **Validación en Frontend**
   - Guardar respuesta completa en localStorage/sessionStorage
   - Verificar si `roles` incluye `ROLE_ADMIN` para mostrar panel de administración
   - Ejemplo:
     ```javascript
     const isAdmin = loginResponse.roles.includes('ROLE_ADMIN');
     if (isAdmin) {
       // Mostrar panel de admin
     }
     ```

4. **Uso del Token**
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

- `GET /api/products` - Listar todos los productos (incluye stock)
- `GET /api/products/{id}` - Obtener producto por ID (incluye stock)
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
- `POST /api/orders` - Crear orden de compra

### Protegidos (Requieren Rol ADMIN)

- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto
- `POST /api/blog-posts` - Crear artículo
- `PUT /api/blog-posts/{id}` - Actualizar artículo
- `DELETE /api/blog-posts/{id}` - Eliminar artículo
- `GET/POST/PUT/DELETE /api/authorities/*` - Gestión de roles

## Sistema de Órdenes de Compra

### Flujo de Compra

1. Usuario autenticado envía orden de compra a `POST /api/orders`
2. Sistema valida:
   - Token JWT vigente
   - Productos existen
   - Hay stock suficiente
   - Precios coinciden con la base de datos
   - Total calculado es correcto
3. Si todo es válido:
   - Se crea la orden
   - Se descuenta el stock
   - Se retorna boleta en formato JSON

### Ejemplo de Request

```json
{
  "order": {
    "orderNumber": "ORD-1700768450123-456",
    "timestamp": "2025-11-23T14:00:50Z",
    "customerInfo": {
      "firstName": "Manuel",
      "lastName": "Dávila",
      "email": "manuel.davila@email.com",
      "phone": "+56912345678"
    },
    "shippingAddress": {
      "address": "Avenida Libertador Bernardo O'Higgins 1564",
      "city": "Santiago",
      "state": "Región Metropolitana",
      "zipCode": "8320000"
    },
    "items": [
      {
        "id": "GM001",
        "name": "Catan",
        "price": 34990,
        "quantity": 1
      },
      {
        "id": "GM002",
        "name": "Dixit",
        "price": 28990,
        "quantity": 1
      }
    ],
    "summary": {
      "subtotal": 63980,
      "shipping": 0,
      "total": 63980
    },
    "payment": {
      "cardNumber": "1234567890123456",
      "cardName": "MANUEL DAVILA",
      "cardExpiry": "12/28",
      "cardCVV": "123"
    }
  }
}
```

### Validaciones de Orden

- Token JWT debe estar vigente
- Productos deben existir en la base de datos
- Debe haber stock suficiente para cada producto
- Los precios deben coincidir con los de la base de datos
- El subtotal debe ser la suma de (precio * cantidad) de cada item
- El total debe ser subtotal + shipping

### Errores Comunes

- `401 Unauthorized` - Token inválido o expirado
- `404 Not Found` - Producto no encontrado
- `400 Bad Request` - Stock insuficiente, precio incorrecto, o total no coincide

## Estructura de Datos

### Categorías de Productos

- Juego de Mesa
- Periférico Gamer
- Consola

### Stock de Productos

Cada producto tiene un stock asociado que se actualiza automáticamente al procesar órdenes. Los productos se inicializan con cantidades entre 10 y 20 unidades.

### Roles de Usuario

- `ROLE_USER` - Acceso básico a endpoints protegidos y puede realizar compras
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
- Endpoints protegidos: gestión de usuarios, contenido y órdenes de compra

### Datos Iniciales

La aplicación carga automáticamente:
- 2 usuarios (admin y usuario regular)
- 31 productos con stock (juegos de mesa, periféricos, consolas)
- 11 artículos de blog con etiquetas
- Stock inicial entre 10-20 unidades por producto

## Troubleshooting

### Error de Conexión a Base de Datos

Verificar variables de entorno y que MySQL esté ejecutándose.

### Error "sql_require_primary_key"

Ejecutar en MySQL: `SET GLOBAL sql_require_primary_key = 0;`

### Stock Insuficiente

El sistema valida automáticamente el stock disponible. Si el error persiste, verificar que los datos se hayan cargado correctamente.
