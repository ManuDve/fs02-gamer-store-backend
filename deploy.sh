#!/bin/bash

# Script de despliegue para Vultr
# Uso: ./deploy.sh

set -e

echo "🚀 Iniciando despliegue en Vultr..."

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Construir la imagen
echo -e "${YELLOW}📦 Construyendo imagen Docker...${NC}"
docker build -t gamer-store-back:latest .

# Detener y eliminar contenedor anterior si existe
echo -e "${YELLOW}🛑 Deteniendo contenedor anterior...${NC}"
docker stop gamer-store-back || true
docker rm gamer-store-back || true

# Ejecutar nuevo contenedor
echo -e "${YELLOW}🔄 Iniciando nuevo contenedor...${NC}"
docker run -d \
  --name gamer-store-back \
  --restart unless-stopped \
  -p 8080:8080 \
  -e DB_USER="${DB_USER}" \
  -e DB_PASS="${DB_PASS}" \
  -e DB_URL="${DB_URL}" \
  -e JWT_SECRET="${JWT_SECRET}" \
  -e JWT_EXPIRATION="${JWT_EXPIRATION}" \
  -e CORS_ALLOWED_ORIGINS="${CORS_ALLOWED_ORIGINS}" \
  gamer-store-back:latest

echo -e "${GREEN}✅ Despliegue completado!${NC}"
echo -e "${GREEN}📊 Verificando estado del contenedor...${NC}"
sleep 5
docker logs --tail 50 gamer-store-back

echo -e "${GREEN}🎉 La aplicación está corriendo en http://localhost:8080${NC}"

