# 📚 Ebook — Plataforma de Compraventa e Intercambio de Libros

Plataforma de e-commerce orientada a la compraventa e intercambio de libros entre usuarios, construida con una arquitectura de microservicios en Java y Spring Boot.

---

## 🧩 Microservicios

| Servicio | Puerto | Descripción |
|---|---|---|
| `usuario-service` | `9091` | Registro, autenticación y gestión de usuarios |
| `carrito-service` | `8081` | Catálogo de libros, transacciones e intercambios |
| `pago-service` | `9093` | Procesamiento y reversión de pagos |

---

## ⚙️ Tecnologías

- **Java 17** + **Spring Boot 3.5**
- **Spring Data JPA** + **PostgreSQL**
- **Spring Security**
- **Lombok**
- **Maven**

---

## 🚀 Requisitos previos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+ corriendo localmente
- Base de datos creada: `biblioteca`

---

## 🛠️ Configuración

Cada microservicio tiene su propio `application.properties` en `src/main/resources/`. Ajusta las credenciales según tu entorno:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

---

## ▶️ Ejecución

Levanta cada microservicio de forma independiente desde su directorio raíz:

```bash
# Servicio de usuarios
cd Usuario
./mvnw spring-boot:run

# Servicio de libros y transacciones
cd "Libros - copia"
./mvnw spring-boot:run

# Servicio de pagos
cd pago
./mvnw spring-boot:run
```

---

## 📡 Endpoints principales

### Usuarios — `localhost:9091`
| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/ecommerce/usuario/save` | Registrar usuario |
| `POST` | `/api/ecommerce/usuario/login` | Iniciar sesión |
| `GET` | `/api/ecommerce/usuario/{id}` | Consultar usuario |
| `PUT` | `/api/ecommerce/usuario/update` | Actualizar usuario |
| `DELETE` | `/api/ecommerce/usuario/{id}` | Eliminar usuario |

### Libros — `localhost:8081`
| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/ecommerce/libros/crear` | Publicar un libro |
| `GET` | `/api/ecommerce/libros/publicos` | Listar libros disponibles |
| `GET` | `/api/ecommerce/libros/buscar/titulo?titulo=` | Buscar por título |
| `GET` | `/api/ecommerce/libros/buscar/autor?autor=` | Buscar por autor |
| `GET` | `/api/ecommerce/libros/usuario/{usuarioId}` | Mis libros |
| `PUT` | `/api/ecommerce/libros/publicar/{id}` | Publicar / hacer visible |
| `DELETE` | `/api/ecommerce/libros/eliminar/{id}` | Eliminar libro |

### Transacciones — `localhost:8081`
| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/ecommerce/transacciones/comprar` | Iniciar compra |
| `PUT` | `/api/ecommerce/transacciones/confirmar/{id}` | Confirmar transacción |
| `PUT` | `/api/ecommerce/transacciones/cancelar/{id}` | Cancelar transacción |

### Intercambios — `localhost:8081`
| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/ecommerce/intercambios/publicaciones/crear` | Publicar libro para intercambio |
| `GET` | `/api/ecommerce/intercambios/publicaciones/activas` | Ver publicaciones activas |
| `POST` | `/api/ecommerce/intercambios/ofertas/crear` | Hacer una oferta |
| `PUT` | `/api/ecommerce/intercambios/ofertas/{id}/aceptar` | Aceptar oferta |
| `PUT` | `/api/ecommerce/intercambios/ofertas/{id}/rechazar` | Rechazar oferta |

### Pagos — `localhost:9093`
| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/ecommerce/pagos/procesar` | Procesar un pago |
| `PUT` | `/api/ecommerce/pagos/revertir/{id}` | Revertir un pago |
| `GET` | `/api/ecommerce/pagos/{id}` | Consultar pago |

---

## 🏛️ Arquitectura

El proyecto aplica **Clean Architecture** (Arquitectura Hexagonal) en cada microservicio:

```
src/
├── domain/
│   ├── model/          # Entidades del dominio
│   ├── usecase/        # Casos de uso (lógica de negocio)
│   └── model/gateway/  # Interfaces de puertos de salida
├── infraestructure/
│   ├── entry_points/   # Controladores REST
│   ├── driver_adapters/ # Repositorios JPA
│   └── mapper/         # Conversión Data ↔ Dominio
└── application/
    └── config/         # Inyección de dependencias
```

La comunicación entre microservicios se realiza vía HTTP usando `RestTemplate`.

---

## 📁 Estructura del repositorio

```
Ebook/
├── Usuario/            # Microservicio de usuarios
├── Libros - copia/     # Microservicio de catálogo y transacciones
└── pago/               # Microservicio de pagos
```
