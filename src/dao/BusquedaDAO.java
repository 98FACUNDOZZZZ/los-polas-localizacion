package dao;

import conexion.ConexionDB;
import modelo.Busqueda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusquedaDAO {

    public void registrar(Busqueda b) {
        String sql = "INSERT INTO busqueda (texto_consultado, resultado, id_usuario, id_producto) VALUES (?,?,?,?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, b.getTextoConsultado());
            ps.setBoolean(2, b.isResultado());
            ps.setInt(3, b.getIdUsuario());
            if (b.getIdProducto() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, b.getIdProducto());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo registrar la búsqueda: " + e.getMessage());
        }
    }

    public void imprimirReporte() {
        System.out.println("\n--- Productos más buscados ---");
        String sql1 = """
                SELECT p.nombre, COUNT(*) AS cantidad
                FROM busqueda b
                JOIN producto p ON b.id_producto = p.id_producto
                GROUP BY p.nombre
                ORDER BY cantidad DESC
                """;
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql1);
             ResultSet rs = ps.executeQuery()) {
            boolean hay = false;
            while (rs.next()) {
                hay = true;
                System.out.println(rs.getString("nombre") + " → " + rs.getInt("cantidad") + " búsquedas");
            }
            if (!hay) System.out.println("(sin datos)");
        } catch (SQLException e) {
            System.out.println("Error en reporte: " + e.getMessage());
        }

        System.out.println("\n--- Búsquedas sin resultado ---");
        String sql2 = """
                SELECT b.texto_consultado, b.fecha_hora, u.nombre_usuario
                FROM busqueda b
                JOIN usuario u ON b.id_usuario = u.id_usuario
                WHERE b.resultado = FALSE
                ORDER BY b.fecha_hora DESC
                """;
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql2);
             ResultSet rs = ps.executeQuery()) {
            boolean hay = false;
            while (rs.next()) {
                hay = true;
                System.out.println(rs.getTimestamp("fecha_hora") + " | "
                        + rs.getString("nombre_usuario") + " | "
                        + rs.getString("texto_consultado"));
            }
            if (!hay) System.out.println("(ninguna)");
        } catch (SQLException e) {
            System.out.println("Error en reporte: " + e.getMessage());
        }
    }
}