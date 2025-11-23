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
   - Hay stock suficiente para cada producto
3. Si todo es válido:
   - Se genera número de orden automáticamente
   - Se obtiene información del cliente del usuario autenticado (nombre, email, teléfono)
   - Se calcula el subtotal basado en precios actuales de la base de datos
   - Se calcula el total (subtotal + envío)
   - Se crea la orden
   - Se descuenta el stock
   - Se guarda en la base de datos
   - Se retorna boleta en formato JSON

### Validaciones de Stock

El sistema valida automáticamente:
- Existencia de productos
- Stock suficiente para cada producto
- Retorna error 400 con mensaje descriptivo si:
  - La cantidad solicitada excede el stock disponible
  - El producto no existe
  - El producto no tiene stock disponible

### Historial de Órdenes

#### Para usuarios regulares:
- `GET /api/orders` - Obtiene todas las órdenes del usuario autenticado
- `GET /api/orders/{orderNumber}` - Obtiene una orden específica (solo si le pertenece)

#### Para administradores:
- `GET /api/orders/all` - Obtiene todas las órdenes de todos los usuarios
- `GET /api/orders/{orderNumber}` - Obtiene cualquier orden del sistema

Las órdenes se guardan permanentemente en la base de datos y pueden consultarse en cualquier momento. Esto permite:
- Dashboard de administración con últimas compras
- Historial de compras del usuario
- Generación de reportes y estadísticas
- Seguimiento de ventas totales

### Ejemplo de Request Simplificado

El backend calcula automáticamente precios, subtotales y totales. La información del cliente se obtiene del JWT. Solo enviar:

```json
{
  "order": {
    "shippingAddress": {
      "address": "Avenida Libertador Bernardo O'Higgins 1564",
      "city": "Santiago",
      "state": "Región Metropolitana",
      "zipCode": "8320000"
    },
    "items": [
      {
        "id": "GM001",
        "quantity": 1
      },
      {
        "id": "GM002",
        "quantity": 2
      }
    ],
    "shipping": 0,
    "payment": {
      "cardNumber": "1234567890123456",
      "cardName": "USUARIO DUOC",
      "cardExpiry": "12/28",
      "cardCVV": "123"
    }
  }
}
```

### Campos Calculados Automáticamente

- `orderNumber`: Generado automáticamente con formato `ORD-{timestamp}-{uuid}`
- `timestamp`: Fecha y hora actual del servidor
- `customerInfo` (firstName, lastName, email, phone): Obtenido del usuario autenticado vía JWT
- `price` (por item): Obtenido de la base de datos (precio actual)
- `name` (por item): Obtenido de la base de datos
- `subtotal`: Suma de (precio × cantidad) de todos los items
- `total`: subtotal + shipping

### Ejemplo de Response

```json
{
  "orderNumber": "ORD-1700768450123-A1B2C3D4",
  "timestamp": "2025-11-23T14:00:50.123",
  "status": "COMPLETADA",
  "message": "Orden creada exitosamente",
  "customerInfo": {
    "firstName": "Usuario",
    "lastName": "Duoc",
    "email": "usuario@duoc.cl",
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
      "quantity": 1,
      "subtotal": 34990
    },
    {
      "id": "GM002",
      "name": "Dixit",
      "price": 28990,
      "quantity": 2,
      "subtotal": 57980
    }
  ],
  "summary": {
    "subtotal": 92970,
    "shipping": 0,
    "total": 92970
  }
}
```

### Manejo de Errores

#### Stock Insuficiente (400 Bad Request)
```json
{
  "message": "Stock insuficiente para el producto: Catan. Disponible: 5, Solicitado: 10",
  "statusCode": 400
}
```

#### Producto No Encontrado (404 Not Found)
```json
{
  "message": "Producto no encontrado con id: GM999",
  "statusCode": 404
}
```

#### Sin Autenticación (401 Unauthorized)
```json
{
  "message": "Error de autenticación",
  "statusCode": 401
}
```
