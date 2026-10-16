package modelo;

import servicio.BusquedaService;
import servicio.ProductoService;

import java.util.Scanner;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String hash, String rol) {
        super(id, nombre, hash, rol);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== MENÚ ADMINISTRADOR ===");
        System.out.println("1. Gestionar productos y ubicaciones");
        System.out.println("2. Buscar producto");
        System.out.println("3. Listar productos con ubicación");
        System.out.println("4. Generar reporte de búsquedas");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    @Override
    public void iniciar(Scanner sc) {
        ProductoService productoService = new ProductoService();
        BusquedaService busquedaService = new BusquedaService();
        int opcion = -1;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                switch (opcion) {
                    case 1 -> productoService.menuGestion(sc);
                    case 2 -> busquedaService.buscarPorTexto(sc, this);
                    case 3 -> productoService.listarActivos();
                    case 4 -> busquedaService.generarReporte();
                    case 0 -> System.out.println("Cerrando sesión...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Poné un número válido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }
}