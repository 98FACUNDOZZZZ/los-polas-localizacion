# Sistema de Localización de Mercadería — Materiales Los Polas

Prototipo operacional (consola) del TP3 - Seminario de Práctica Informática.

Permite registrar productos con su ubicación física (pasillo, estantería, nivel, cajón)
y buscarlos por nombre o código. Pensado para el corralón familiar Materiales Los Polas.

## Qué hace

- Login con roles (Administrador / Empleado)
- Alta, modificación y baja lógica de productos
- Administración de ubicaciones
- Búsqueda por texto con normalización
- Visualización de la ubicación completa
- Registro de cada búsqueda
- Listado de productos
- Reporte simple de búsquedas (solo admin)
- Persistencia en MySQL vía JDBC

## Cómo correrlo

1. Tener Java 17+ y MySQL (con XAMPP anda bien).
2. Ejecutar `sql/script.sql` en phpMyAdmin o Workbench.
3. En `src/conexion/ConexionDB.java` poner usuario y contraseña de MySQL
   (en XAMPP suele ser `root` y contraseña vacía).
4. Agregar el MySQL Connector/J al proyecto.
5. Correr `Main`.

### Usuarios de prueba

| Usuario    | Contraseña       | Rol           |
|------------|------------------|---------------|
| admin      | hash_admin_123   | ADMINISTRADOR |
| empleado1  | hash_emp_456     | EMPLEADO      |
| empleado2  | hash_emp_789     | EMPLEADO      |

## Stack

- Java 17
- MySQL 8 (XAMPP)
- JDBC
- Consola (la GUI queda para una próxima etapa)

## Arquitectura

Tres capas: presentación (consola) → lógica de negocio → persistencia (DAO/JDBC).

POO: encapsulamiento, herencia (`Usuario` → `Administrador` / `Empleado`),
polimorfismo (`mostrarMenu` / `iniciar`) y abstracción (clase abstracta + interfaz `Localizable`).