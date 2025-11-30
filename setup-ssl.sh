#!/bin/bash

echo "Configurando HTTPS para Gamer Store Backend..."

# 1. Eliminar configuración previa si existe
echo "Limpiando configuraciones anteriores..."
rm -f /etc/nginx/sites-enabled/gamer-store
rm -f /etc/nginx/sites-enabled/default

# 2. Verificar que Docker esté corriendo
echo "Verificando que la aplicación Docker esté corriendo..."
if ! docker ps | grep -q gamer-store-back; then
    echo "ERROR: El contenedor gamer-store-back no está corriendo"
    echo "Por favor, primero despliega la aplicación con ./deploy.sh"
    exit 1
fi

# 3. Generar certificado SSL autofirmado
echo "Generando certificado SSL autofirmado..."
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout /etc/ssl/private/nginx-selfsigned.key \
  -out /etc/ssl/certs/nginx-selfsigned.crt \
  -subj "/C=CL/ST=Santiago/L=Santiago/O=GamerStore/CN=66.135.22.150"

# 4. Verificar que los certificados se crearon
if [ ! -f /etc/ssl/private/nginx-selfsigned.key ] || [ ! -f /etc/ssl/certs/nginx-selfsigned.crt ]; then
    echo "ERROR: No se pudieron crear los certificados SSL"
    exit 1
fi

echo "Certificados SSL creados exitosamente"

# 5. Copiar configuración de Nginx
echo "Configurando Nginx..."
cp nginx-gamer-store.conf /etc/nginx/sites-available/gamer-store

# 6. Habilitar el sitio
ln -s /etc/nginx/sites-available/gamer-store /etc/nginx/sites-enabled/

# 7. Verificar configuración de Nginx
echo "Verificando configuración de Nginx..."
if ! nginx -t; then
    echo "ERROR: La configuración de Nginx tiene errores"
    exit 1
fi

# 8. Reiniciar Nginx
echo "Reiniciando Nginx..."
systemctl restart nginx

# 9. Verificar que Nginx esté corriendo
if ! systemctl is-active --quiet nginx; then
    echo "ERROR: Nginx no pudo iniciarse"
    systemctl status nginx
    exit 1
fi

# 10. Abrir puerto 443 en firewall
echo "Configurando firewall..."
ufw allow 443/tcp
ufw allow 80/tcp

# 11. Verificar puertos
echo "Verificando puertos abiertos..."
ss -tlnp | grep nginx

echo ""
echo "================================================"
echo "Configuración completada exitosamente!"
echo "================================================"
echo ""
echo "Tu API ahora está disponible en:"
echo "  HTTPS: https://66.135.22.150"
echo "  HTTP:  http://66.135.22.150 (redirige a HTTPS)"
echo ""
echo "IMPORTANTE:"
echo "1. Abre https://66.135.22.150 en tu navegador"
echo "2. Verás una advertencia de seguridad (certificado autofirmado)"
echo "3. Haz clic en 'Avanzado' y luego 'Continuar a 66.135.22.150'"
echo "4. Actualiza la URL en tu frontend a: https://66.135.22.150"
echo ""
echo "Para ver los logs de Nginx:"
echo "  tail -f /var/log/nginx/error.log"
echo ""
server {
    listen 443 ssl http2;
    server_name 66.135.22.150;

    ssl_certificate /etc/ssl/certs/nginx-selfsigned.crt;
    ssl_certificate_key /etc/ssl/private/nginx-selfsigned.key;

    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;

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

        # CORS
        add_header 'Access-Control-Allow-Origin' 'https://manudve.github.io' always;
        add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS' always;
        add_header 'Access-Control-Allow-Headers' 'Authorization, Content-Type' always;
        add_header 'Access-Control-Allow-Credentials' 'true' always;

        if ($request_method = 'OPTIONS') {
            add_header 'Access-Control-Allow-Origin' 'https://manudve.github.io' always;
            add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS' always;
            add_header 'Access-Control-Allow-Headers' 'Authorization, Content-Type' always;
            add_header 'Content-Length' 0;
            return 204;
        }
    }
}

server {
    listen 80;
    server_name 66.135.22.150;
    return 301 https://$server_name$request_uri;
}

