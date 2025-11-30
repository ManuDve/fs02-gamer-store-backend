# 📦 Guía de Despliegue en Vultr (VC2-1c-1gb)

## 📋 Requisitos Previos

- Servidor Vultr VC2-1c-1gb (1 vCPU, 1GB RAM)
- Ubuntu 22.04 LTS (recomendado)
- Acceso SSH al servidor
- Variables de entorno configuradas

## 🔧 Variables de Entorno Necesarias

Asegúrate de tener estos valores:
- `DB_USER`: Usuario de la base de datos
- `DB_PASS`: Contraseña de la base de datos
- `DB_URL`: URL de conexión a MySQL (formato: host:puerto)
- `JWT_SECRET`: Clave secreta para JWT
- `JWT_EXPIRATION`: Tiempo de expiración del token (ejemplo: 86400000 para 24 horas)
- `CORS_ALLOWED_ORIGINS`: Orígenes permitidos separados por comas

## 🚀 Pasos de Despliegue

### 1️⃣ Conectar al servidor Vultr

```bash
ssh root@TU_IP_DE_VULTR
```

### 2️⃣ Actualizar el sistema

```bash
apt update && apt upgrade -y
```

### 3️⃣ Instalar Docker

```bash
# Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Verificar instalación
docker --version
```

### 4️⃣ Instalar Docker Compose (opcional)

```bash
apt install docker-compose -y
docker-compose --version
```

### 5️⃣ Instalar Git

```bash
apt install git -y
```

### 6️⃣ Clonar el repositorio

```bash
cd /opt
git clone TU_REPOSITORIO gamer-store-back
cd gamer-store-back
```

### 7️⃣ Crear archivo de variables de entorno

```bash
nano .env
```

Agregar las siguientes líneas (reemplaza con tus valores):

```env
DB_USER=tu_usuario
DB_PASS=tu_contraseña
DB_URL=tu-host:puerto
JWT_SECRET=tu_secret_muy_seguro_y_largo_minimo_256_bits
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=https://tu-dominio.com,https://www.tu-dominio.com
```

Guardar con `CTRL+O`, `ENTER`, salir con `CTRL+X`

### 8️⃣ Dar permisos de ejecución al script

```bash
chmod +x deploy.sh
```

### 9️⃣ Cargar variables de entorno y desplegar

**Opción A: Usando el script de despliegue**

```bash
# Cargar variables de entorno
export $(cat .env | xargs)

# Ejecutar despliegue
./deploy.sh
```

**Opción B: Usando Docker Compose**

```bash
docker-compose up -d --build
```

### 🔟 Verificar que la aplicación está corriendo

```bash
# Ver logs
docker logs -f gamer-store-back

# Verificar contenedor
docker ps

# Probar endpoint
curl http://localhost:8080/swagger-ui/index.html
```

## 🌐 Configurar Nginx como Reverse Proxy (Recomendado)

### 1. Instalar Nginx

```bash
apt install nginx -y
```

### 2. Crear configuración

```bash
nano /etc/nginx/sites-available/gamer-store
```

Agregar:

```nginx
server {
    listen 80;
    server_name tu-dominio.com www.tu-dominio.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_cache_bypass $http_upgrade;
    }
}
```

### 3. Habilitar sitio y reiniciar Nginx

```bash
ln -s /etc/nginx/sites-available/gamer-store /etc/nginx/sites-enabled/
nginx -t
systemctl restart nginx
```

### 4. Instalar SSL con Let's Encrypt

```bash
apt install certbot python3-certbot-nginx -y
certbot --nginx -d tu-dominio.com -d www.tu-dominio.com
```

## 🔄 Actualizar la Aplicación

```bash
cd /opt/gamer-store-back
git pull
export $(cat .env | xargs)
./deploy.sh
```

## 📊 Comandos Útiles

```bash
# Ver logs en tiempo real
docker logs -f gamer-store-back

# Reiniciar contenedor
docker restart gamer-store-back

# Detener aplicación
docker stop gamer-store-back

# Ver uso de recursos
docker stats gamer-store-back

# Entrar al contenedor
docker exec -it gamer-store-back sh

# Limpiar imágenes antiguas
docker image prune -a
```

## 🔥 Firewall (UFW)

```bash
# Habilitar firewall
ufw allow OpenSSH
ufw allow 80/tcp
ufw allow 443/tcp
ufw enable
ufw status
```

## 💾 Optimización para 1GB RAM

El Dockerfile ya está optimizado con:
- `-Xmx512m`: Máximo 512MB de heap
- `-Xms256m`: Mínimo 256MB de heap
- Imagen JRE Alpine (más liviana)
- Pool de conexiones limitado a 10

## 🆘 Troubleshooting

### La aplicación no inicia
```bash
docker logs gamer-store-back
```

### Error de conexión a la base de datos
- Verifica que las variables de entorno estén correctas
- Verifica que el servidor de BD permita conexiones desde tu IP de Vultr

### Memoria insuficiente
```bash
# Ver memoria disponible
free -h

# Crear swap de 2GB
fallocate -l 2G /swapfile
chmod 600 /swapfile
mkswap /swapfile
swapon /swapfile
echo '/swapfile none swap sw 0 0' | tee -a /etc/fstab
```

## 📱 Endpoints Importantes

- API: `http://tu-ip:8080/api`
- Swagger: `http://tu-ip:8080/swagger-ui/index.html`
- Health: `http://tu-ip:8080/actuator/health` (si está habilitado)

## 🔒 Seguridad

1. Cambia el puerto SSH por defecto
2. Usa autenticación por llave SSH
3. Mantén el sistema actualizado
4. Usa SSL/TLS (Let's Encrypt)
5. Configura el firewall correctamente
6. No expongas el puerto 8080 directamente (usa Nginx)

---

¡Tu aplicación debería estar corriendo ahora! 🎉
# Etapa de construcción
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Crear usuario no privilegiado
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Xmx512m", "-Xms256m", "app.jar"]

