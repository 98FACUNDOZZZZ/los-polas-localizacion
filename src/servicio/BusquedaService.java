package servicio;

import dao.BusquedaDAO;
import dao.ProductoDAO;
import excepciones.ProductoNoEncontradoException;
import modelo.Busqueda;
import modelo.Producto;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class BusquedaService {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final BusquedaDAO busquedaDAO = new BusquedaDAO();

    public void buscarPorTexto(Scanner sc, Usuario usuario) {
        System.out.print("\nNombre o código a buscar: ");
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("No escribiste nada.");
            return;
        }
        String normalizado = Utilidades.normalizar(texto);
        try {
            List<Producto> resultados = productoDAO.buscarPorTexto(normalizado);
            Utilidades.ordenarPorNombre(resultados);
            if (resultados.isEmpty()) {
                throw new ProductoNoEncontradoException("No se encontraron coincidencias para: " + texto);
            }
            System.out.println("\nResultados:");
            for (Producto p : resultados) {
                System.out.println("• " + p.getCodigo() + " - " + p.getNombre());
                System.out.println("  " + p.getUbicacionCompleta());
            }
            Integer idProd = resultados.get(0).getIdProducto();
            busquedaDAO.registrar(new Busqueda(texto, true, usuario.getIdUsuario(), idProd));
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
            busquedaDAO.registrar(new Busqueda(texto, false, usuario.getIdUsuario(), null));
        } catch (Exception e) {
            System.out.println("Error de base de datos. Probá más tarde.");
        }
    }

    public void generarReporte() {
        busquedaDAO.imprimirReporte();
    }
}