package servicio;

import modelo.Producto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utilidades {

    public static String normalizar(String texto) {
        if (texto == null) return "";
        return texto.toLowerCase().trim();
    }

    public static void ordenarPorNombre(List<Producto> lista) {
        Collections.sort(lista, Comparator.comparing(p -> p.getNombre().toLowerCase()));
        /*
         * Bubble sort (queda acá para mostrar el algoritmo, no se usa):
         *
         * for (int i = 0; i < lista.size() - 1; i++) {
         *     for (int j = 0; j < lista.size() - 1 - i; j++) {
         *         if (lista.get(j).getNombre().compareToIgnoreCase(lista.get(j + 1).getNombre()) > 0) {
         *             Producto aux = lista.get(j);
         *             lista.set(j, lista.get(j + 1));
         *             lista.set(j + 1, aux);
         *         }
         *     }
         * }
         */
    }
}