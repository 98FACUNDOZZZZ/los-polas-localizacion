# Sistema de Localización de Mercadería — Materiales Los Polas

Prototipo de consola del TP3 (Seminario de Práctica Informática).

La idea es simple: en el corralón nadie tiene un registro de dónde está cada cosa.
Este prototipo deja cargar productos con su ubicación (pasillo, estantería, nivel, cajón)
y buscarlos por nombre o código. Si el empleado no se acuerda, lo escribe y el sistema le dice dónde ir.

## Qué se puede hacer

- Login con rol (Administrador o Empleado)
- Alta, modificación y baja lógica de productos
- Cargar ubicaciones
- Buscar por texto (pasa todo a minúsculas y saca espacios)
- Ver la ubicación completa
- Que quede registrada cada búsqueda
- Listar productos
- Un reporte simple de búsquedas (solo el admin)
- Todo se guarda en MySQL

## Cómo correrlo

1. Java 17 o más, y MySQL (con XAMPP anda bien).
2. Ejecutar `sql/script.sql` en phpMyAdmin.
3. En `src/conexion/ConexionDB.java` poner el usuario y la contraseña de MySQL.
   En XAMPP casi siempre es `root` y la contraseña vacía.
4. Agregar el MySQL Connector/J al proyecto.
5. Correr `Main`.

### Usuarios de prueba

| Usuario    | Contraseña       | Rol           |
|------------|------------------|---------------|
| admin      | hash_admin_123   | ADMINISTRADOR |
| empleado1  | hash_emp_456     | EMPLEADO      |
| empleado2  | hash_emp_789     | EMPLEADO      |

## De qué está hecho

- Java 17
- MySQL (XAMPP)
- JDBC
- Consola (la GUI queda para después, como se había planteado en el TP2)

## Cómo está armado

Tres capas: la consola, la lógica de negocio y los DAO que hablan con la base.

De POO está el encapsulamiento, herencia (`Usuario` → `Administrador` y `Empleado`),
polimorfismo (`mostrarMenu` / `iniciar`) y abstracción (clase abstracta + interfaz `Localizable`).
