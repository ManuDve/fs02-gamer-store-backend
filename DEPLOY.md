# Guía de Despliegue en Vultr (VC2-1c-1gb)

## Requisitos Previos

- Servidor Vultr VC2-1c-1gb (1 vCPU, 1GB RAM)
- Ubuntu 22.04 LTS (recomendado)
- Acceso SSH al servidor
- Variables de entorno configuradas
- IP del servidor: **66.135.22.150**

## Variables de Entorno Necesarias

Asegúrate de tener estos valores:
- `DB_USER`: Usuario de la base de datos
- `DB_PASS`: Contraseña de la base de datos
- `DB_URL`: URL de conexión a MySQL (formato: host:puerto)
- `JWT_SECRET`: Clave secreta para JWT
- `JWT_EXPIRATION`: Tiempo de expiración del token (ejemplo: 86400000 para 24 horas)
- `CORS_ALLOWED_ORIGINS`: Orígenes permitidos separados por comas

## Pasos de Despliegue

### 1. Conectar al servidor Vultr

```bash
ssh root@66.135.22.150
```

### 2. Actualizar el sistema

```bash
apt update && apt upgrade -y
```

### 3. Instalar Docker

```bash
# Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Verificar instalación
docker --version
```

### 4. Instalar Docker Compose (opcional)

```bash
apt install docker-compose -y
docker-compose --version
```

### 5. Instalar Git

```bash
apt install git -y
```

### 6. Clonar el repositorio

```bash
cd /opt
git clone https://github.com/manudve/fs02-gamer-store-backend.git
cd fs02-gamer-store-backend
```

### 7. Crear archivo de variables de entorno

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
CORS_ALLOWED_ORIGINS=https://manudve.github.io
```

Guardar con `CTRL+O`, `ENTER`, salir con `CTRL+X`

### 8. Dar permisos de ejecución al script

```bash
chmod +x deploy.sh
```

### 9. Cargar variables de entorno y desplegar

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

### 10. Verificar que la aplicación está corriendo

```bash
# Ver logs
docker logs -f gamer-store-back

# Verificar contenedor
docker ps

# Probar endpoint
curl http://localhost:8080/swagger-ui/index.html
```

## Configurar Nginx como Reverse Proxy (Recomendado)

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
    server_name 66.135.22.150;

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

## Actualizar la Aplicación

```bash
cd /opt/fs02-gamer-store-backend
git pull
export $(cat .env | xargs)
./deploy.sh
```

## Comandos Útiles

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

## Firewall (UFW)

```bash
# Habilitar firewall
ufw allow OpenSSH
ufw allow 80/tcp
ufw allow 443/tcp
ufw enable
ufw status
```

## Optimización para 1GB RAM

El Dockerfile ya está optimizado con:
- `-Xmx512m`: Máximo 512MB de heap
- `-Xms256m`: Mínimo 256MB de heap
- Imagen JRE Alpine (más liviana)
- Pool de conexiones limitado a 10

## Troubleshooting

### Blocked Mixed Content (IMPORTANTE para GitHub Pages)

Si tu frontend en GitHub Pages (HTTPS) no puede conectarse al backend y aparece el error `blocked:mixed-content`, necesitas configurar HTTPS en tu servidor Vultr. Los navegadores modernos bloquean peticiones HTTP desde sitios HTTPS.

**Solución: Configurar Nginx + SSL (OBLIGATORIO)**

#### Paso 1: Instalar Nginx

```bash
apt install nginx -y
```

#### Paso 2: Crear configuración básica

```bash
nano /etc/nginx/sites-available/gamer-store
```

Agregar esta configuración inicial:

```nginx
server {
    listen 80;
    server_name 66.135.22.150;

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
        
        # CORS headers (por si acaso)
        add_header 'Access-Control-Allow-Origin' 'https://manudve.github.io' always;
        add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS' always;
        add_header 'Access-Control-Allow-Headers' 'Authorization, Content-Type' always;
        
        if ($request_method = 'OPTIONS') {
            return 204;
        }
    }
}
```

#### Paso 3: Habilitar sitio

```bash
ln -s /etc/nginx/sites-available/gamer-store /etc/nginx/sites-enabled/
nginx -t
systemctl restart nginx
```

#### Paso 4: Configurar SSL con Certbot

**Si tienes un dominio:**

```bash
apt install certbot python3-certbot-nginx -y
certbot --nginx -d tu-dominio.com
```

**Si SOLO tienes IP (sin dominio):**

No puedes usar Let's Encrypt con una IP. Tienes 2 opciones:

**Opción A: Usar un dominio gratuito**
1. Registra un dominio gratuito en [FreeDNS](https://freedns.afraid.org/) o [No-IP](https://www.noip.com/)
2. Apunta el dominio a tu IP de Vultr
3. Usa Certbot con ese dominio

**Opción B: Certificado autofirmado (solo para desarrollo)**

```bash
# Generar certificado autofirmado
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout /etc/ssl/private/nginx-selfsigned.key \
  -out /etc/ssl/certs/nginx-selfsigned.crt \
  -subj "/C=CL/ST=Santiago/L=Santiago/O=GamerStore/CN=66.135.22.150"

# Actualizar configuración de Nginx
nano /etc/nginx/sites-available/gamer-store
```

Modificar el archivo:

```nginx
server {
    listen 443 ssl;
    server_name 66.135.22.150;

    ssl_certificate /etc/ssl/certs/nginx-selfsigned.crt;
    ssl_certificate_key /etc/ssl/private/nginx-selfsigned.key;
    
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;

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

server {
    listen 80;
    server_name 66.135.22.150;
    return 301 https://$server_name$request_uri;
}
```

```bash
nginx -t
systemctl restart nginx
```

**NOTA:** Con certificado autofirmado verás una advertencia en el navegador. Debes aceptarla manualmente.

#### Paso 5: Abrir puerto HTTPS en firewall

```bash
ufw allow 443/tcp
ufw reload
```

#### Paso 6: Actualizar la URL en tu frontend

Cambia la URL de tu API en el frontend de:
```javascript
http://66.135.22.150:8080
```

a:
```javascript
https://66.135.22.150
```

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

## Endpoints Importantes

- API: `http://66.135.22.150:8080/api` (temporal, cambiará a HTTPS)
- API con HTTPS: `https://66.135.22.150/api` (después de configurar SSL)
- Swagger: `http://66.135.22.150:8080/swagger-ui/index.html`
- Swagger con HTTPS: `https://66.135.22.150/swagger-ui/index.html`
- Health: `http://66.135.22.150:8080/actuator/health` (si está habilitado)

## Seguridad

1. Cambia el puerto SSH por defecto
2. Usa autenticación por llave SSH
3. Mantén el sistema actualizado
4. Usa SSL/TLS (Let's Encrypt)
5. Configura el firewall correctamente
6. No expongas el puerto 8080 directamente (usa Nginx)

---

¡Tu aplicación debería estar corriendo ahora!
