package servicio;

import dao.ProductoDAO;
import dao.UbicacionDAO;
import modelo.Producto;
import modelo.Ubicacion;

import java.util.List;
import java.util.Scanner;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final UbicacionDAO ubicacionDAO = new UbicacionDAO();

    public void listarActivos() {
        List<Producto> lista = productoDAO.listarActivos();
        Utilidades.ordenarPorNombre(lista);
        if (lista.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }
        System.out.println("\n--- Productos ---");
        for (Producto p : lista) {
            System.out.println(p);
        }
    }

    public void menuGestion(Scanner sc) {
        int op = -1;
        do {
            System.out.println("\n--- Gestión de catálogo ---");
            System.out.println("1. Alta de producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Baja lógica de producto");
            System.out.println("4. Alta de ubicación");
            System.out.println("5. Listar ubicaciones");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                op = Integer.parseInt(sc.nextLine().trim());
                switch (op) {
                    case 1 -> altaProducto(sc);
                    case 2 -> modificarProducto(sc);
                    case 3 -> bajaProducto(sc);
                    case 4 -> altaUbicacion(sc);
                    case 5 -> listarUbicaciones();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Poné un número válido.");
            }
        } while (op != 0);
    }

    private void altaProducto(Scanner sc) {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim();
        if (productoDAO.existeCodigo(codigo)) {
            System.out.println("Ese código ya existe (restricción UNIQUE).");
            return;
        }
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Descripción: ");
        String desc = sc.nextLine().trim();

        listarUbicaciones();
        System.out.print("ID de ubicación (o 0 para crear una nueva): ");
        int idUbi;
        try {
            idUbi = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Ubicacion ubi;
        if (idUbi == 0) {
            ubi = altaUbicacion(sc);
            if (ubi == null) return;
        } else {
            ubi = ubicacionDAO.buscarPorId(idUbi);
            if (ubi == null) {
                System.out.println("Esa ubicación no existe. ¿La creamos ahora? (s/n)");
                String r = sc.nextLine().trim();
                if (r.equalsIgnoreCase("s")) {
                    ubi = altaUbicacion(sc);
                    if (ubi == null) return;
                } else {
                    return;
                }
            }
        }

        Producto p = new Producto(0, codigo, nombre, desc, true, ubi);
        if (productoDAO.insertar(p)) {
            System.out.println("Producto creado.");
        }
    }

    private void modificarProducto(Scanner sc) {
        listarActivos();
        System.out.print("ID de producto a modificar: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
        Producto p = productoDAO.buscarPorId(id);
        if (p == null) {
            System.out.println("No existe ese producto.");
            return;
        }
        System.out.print("Nuevo nombre (enter = dejar igual): ");
        String nombre = sc.nextLine().trim();
        if (!nombre.isEmpty()) p.setNombre(nombre);

        System.out.print("Nueva descripción (enter = dejar igual): ");
        String desc = sc.nextLine().trim();
        if (!desc.isEmpty()) p.setDescripcion(desc);

        listarUbicaciones();
        System.out.print("Nuevo ID de ubicación (enter = dejar igual): ");
        String idUbiTxt = sc.nextLine().trim();
        if (!idUbiTxt.isEmpty()) {
            try {
                Ubicacion ubi = ubicacionDAO.buscarPorId(Integer.parseInt(idUbiTxt));
                if (ubi == null) {
                    System.out.println("Ubicación inexistente.");
                    return;
                }
                p.setUbicacion(ubi);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                return;
            }
        }
        if (productoDAO.modificar(p)) {
            System.out.println("Producto actualizado.");
        }
    }

    private void bajaProducto(Scanner sc) {
        listarActivos();
        System.out.print("ID de producto a dar de baja: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            if (productoDAO.bajaLogica(id)) {
                System.out.println("Baja lógica ok. Deja de aparecer en listados y búsquedas.");
            } else {
                System.out.println("No se pudo dar de baja.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private Ubicacion altaUbicacion(Scanner sc) {
        System.out.print("Pasillo: ");
        String pasillo = sc.nextLine().trim();
        System.out.print("Estantería: ");
        String est = sc.nextLine().trim();
        System.out.print("Nivel: ");
        String nivel = sc.nextLine().trim();
        System.out.print("Cajón (puede quedar vacío): ");
        String cajon = sc.nextLine().trim();
        if (cajon.isEmpty()) cajon = null;
        System.out.print("Descripción: ");
        String desc = sc.nextLine().trim();

        Ubicacion u = new Ubicacion(0, pasillo, est, nivel, cajon, desc);
        int id = ubicacionDAO.insertar(u);
        if (id > 0) {
            System.out.println("Ubicación creada con ID " + id);
            return u;
        }
        return null;
    }

    private void listarUbicaciones() {
        List<Ubicacion> lista = ubicacionDAO.listar();
        System.out.println("\n--- Ubicaciones ---");
        for (Ubicacion u : lista) {
            System.out.println(u);
        }
    }
}