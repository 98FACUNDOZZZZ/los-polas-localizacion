CREATE DATABASE IF NOT EXISTS los_polas_localizacion CHARACTER SET utf8mb4;
USE los_polas_localizacion;

CREATE TABLE ubicacion (
                           id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
                           pasillo VARCHAR(20) NOT NULL,
                           estanteria VARCHAR(20) NOT NULL,
                           nivel VARCHAR(20) NOT NULL,
                           cajon VARCHAR(20),
                           descripcion VARCHAR(150)
) ENGINE=InnoDB;

CREATE TABLE producto (
                          id_producto INT AUTO_INCREMENT PRIMARY KEY,
                          codigo VARCHAR(30) NOT NULL UNIQUE,
                          nombre VARCHAR(100) NOT NULL,
                          descripcion VARCHAR(200),
                          activo BOOLEAN NOT NULL DEFAULT TRUE,
                          id_ubicacion INT NOT NULL,
                          CONSTRAINT fk_producto_ubicacion FOREIGN KEY (id_ubicacion)
                              REFERENCES ubicacion(id_ubicacion) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
                         password_hash VARCHAR(255) NOT NULL,
                         rol ENUM('ADMINISTRADOR','EMPLEADO') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE busqueda (
                          id_busqueda INT AUTO_INCREMENT PRIMARY KEY,
                          texto_consultado VARCHAR(150) NOT NULL,
                          resultado BOOLEAN NOT NULL,
                          fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          id_usuario INT NOT NULL,
                          id_producto INT NULL,
                          CONSTRAINT fk_busqueda_usuario FOREIGN KEY (id_usuario)
                              REFERENCES usuario(id_usuario) ON DELETE RESTRICT,
                          CONSTRAINT fk_busqueda_producto FOREIGN KEY (id_producto)
                              REFERENCES producto(id_producto) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE INDEX idx_producto_nombre ON producto(nombre);
CREATE INDEX idx_producto_codigo ON producto(codigo);

INSERT INTO ubicacion (pasillo, estanteria, nivel, cajon, descripcion) VALUES
                                                                           ('Pasillo 1', 'Estanteria A', 'Nivel 1', 'Cajon 3', 'Zona de cementos y cales'),
                                                                           ('Pasillo 1', 'Estanteria B', 'Nivel 2', NULL, 'Zona de hierros'),
                                                                           ('Pasillo 2', 'Estanteria A', 'Nivel 1', 'Cajon 1', 'Zona de tornilleria'),
                                                                           ('Pasillo 3', 'Estanteria C', 'Nivel 3', NULL, 'Zona de pinturas');

INSERT INTO usuario (nombre_usuario, password_hash, rol) VALUES
                                                             ('admin', 'hash_admin_123', 'ADMINISTRADOR'),
                                                             ('empleado1', 'hash_emp_456', 'EMPLEADO'),
                                                             ('empleado2', 'hash_emp_789', 'EMPLEADO');

INSERT INTO producto (codigo, nombre, descripcion, activo, id_ubicacion) VALUES
                                                                             ('COD-0001', 'Cemento x25kg', 'Bolsa de cemento comun', TRUE, 1),
                                                                             ('COD-0002', 'Cal x25kg', 'Bolsa de cal para obra', TRUE, 1),
                                                                             ('COD-0003', 'Hierro Aletado 8mm x12m', 'Varilla de hierro para construccion', TRUE, 2),
                                                                             ('COD-0004', 'Tornillo autoperforante 2"', 'Caja x100 unidades', TRUE, 3),
                                                                             ('COD-0005', 'Pintura latex interior-Exterior 20L', 'Balde de pintura blanca', TRUE, 4),
                                                                             ('COD-0006', 'Ladrillo hueco 12x18x33', 'Unidad de ladrillo hueco', FALSE, 2);

INSERT INTO busqueda (texto_consultado, resultado, id_usuario, id_producto) VALUES
                                                                                ('cemento', TRUE, 2, 1),
                                                                                ('cal', TRUE, 2, 2),
                                                                                ('varilla 8mm', TRUE, 3, 3),
                                                                                ('clavo 3 pulgadas', FALSE, 2, NULL),
                                                                                ('pintura blanca', TRUE, 3, 5);