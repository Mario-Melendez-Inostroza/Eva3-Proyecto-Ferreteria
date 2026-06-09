# Ferretería — Arquitectura de Microservicios

## Arquitectura

```
[Postman / Frontend]
        │ Puerto 8080
        ▼
┌─────────────────────────────────────────────────────┐
│                  BFF-SERVICE (8080)                  │
│   AuthController  ProductController  SalesController │
│   InventoryController  NotificationController        │
│   BffService → valida token con ms-auth antes de     │
│                reenviar a cada microservicio          │
└──────┬──────────┬──────────┬──────────┬─────────────┘
       │          │          │          │
  ms-auth    ms-product  ms-inventory  ms-sales  ms-notification
  (8081)      (8082)       (8083)      (8084)       (8085)
       │          │          │          │              │
       └──────────┴──────────┴──────────┴──────────────┘
                              │
                       PostgreSQL (5432)
              auth_db / product_db / inventory_db
              sales_db / notification_db
```

## Responsabilidades

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| bff-service | 8080 | Punto de entrada único. Valida JWT y coordina microservicios |
| ms-auth | 8081 | Usuarios, registro, login, generación y validación de JWT |
| ms-product | 8082 | Catálogo de productos (CRUD + categorías) |
| ms-inventory | 8083 | Stock por producto y bodega, alertas de stock bajo |
| ms-sales | 8084 | Ventas: verifica producto → descuenta stock → notifica |
| ms-notification | 8085 | Registro de alertas (stock bajo, ventas) |

## Cómo levantar

```bash
docker compose up --build
```

Espera ~2 minutos a que todos los servicios arranquen.

---

## Flujo de una venta (el más importante)

```
POST /api/sales  (con token JWT)
        ↓
BFF valida token con ms-auth
        ↓
BFF llama a ms-sales
        ↓
ms-sales verifica producto en ms-product
        ↓
ms-sales descuenta stock en ms-inventory
        ↓
ms-sales guarda la venta en sales_db
        ↓
ms-sales notifica a ms-notification
  - "VENTA_REGISTRADA"
  - "STOCK_BAJO" (si el stock quedó bajo)
        ↓
Respuesta al BFF → al cliente
```

---

## Endpoints completos (todos via Puerto 8080)

### Autenticación (sin token)
```
POST /api/auth/register    Body: {"username":"mario","password":"123456"}
POST /api/auth/login       Body: {"username":"mario","password":"123456"}
```

### Productos (requieren Bearer token)
```
GET    /api/products
GET    /api/products/{id}
GET    /api/products/buscar?nombre=tala
GET    /api/products/categoria/Herramientas
POST   /api/products       Body: {"nombre":"X","precio":9990,"categoria":"Y"}
PUT    /api/products/{id}
DELETE /api/products/{id}
```

### Inventario (requieren Bearer token)
```
POST   /api/inventory                        Body: {"productId":"...","stock":10}
GET    /api/inventory
GET    /api/inventory/stock-bajo
GET    /api/inventory/producto/{productId}
PUT    /api/inventory/producto/{productId}/agregar    Body: {"cantidad":5}
PUT    /api/inventory/producto/{productId}/descontar  Body: {"cantidad":2}
```

### Ventas (requieren Bearer token)
```
POST   /api/sales          Body: {"productId":"...","cantidad":2,"cliente":"Juan"}
GET    /api/sales
GET    /api/sales/{id}
GET    /api/sales/producto/{productId}
GET    /api/sales/resumen
```

### Notificaciones (requieren Bearer token)
```
GET    /api/notifications
GET    /api/notifications/no-leidas
PUT    /api/notifications/{id}/leer
PUT    /api/notifications/leer-todas
```

---

## Orden de uso recomendado en Postman

1. `POST /api/auth/register` → crear usuario
2. `POST /api/auth/login` → obtener token
3. `POST /api/products` → crear producto (ej: Taladro $89990)
4. `POST /api/inventory` → inicializar stock del producto (`{"productId":"...","stock":10}`)
5. `POST /api/sales` → registrar venta (`{"productId":"...","cantidad":2}`)
6. `GET /api/inventory/stock-bajo` → ver si quedó con stock bajo
7. `GET /api/notifications/no-leidas` → ver alertas generadas
