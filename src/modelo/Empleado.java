package modelo;

import servicio.BusquedaService;
import servicio.ProductoService;

import java.util.Scanner;

public class Empleado extends Usuario {

    public Empleado(int id, String nombre, String hash, String rol) {
        super(id, nombre, hash, rol);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== MENÚ EMPLEADO ===");
        System.out.println("1. Buscar producto");
        System.out.println("2. Listar productos con ubicación");
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
                    case 1 -> busquedaService.buscarPorTexto(sc, this);
                    case 2 -> productoService.listarActivos();
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