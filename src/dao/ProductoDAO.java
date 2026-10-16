package dao;

import conexion.ConexionDB;
import modelo.Producto;
import modelo.Ubicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> buscarPorTexto(String texto) {
        List<Producto> lista = new ArrayList<>();
        String sql = """
                SELECT p.id_producto, p.codigo, p.nombre, p.descripcion, p.activo,
                       u.id_ubicacion, u.pasillo, u.estanteria, u.nivel, u.cajon, u.descripcion AS u_desc
                FROM producto p
                JOIN ubicacion u ON p.id_ubicacion = u.id_ubicacion
                WHERE p.activo = TRUE
                  AND (LOWER(p.nombre) LIKE ? OR LOWER(p.codigo) LIKE ?)
                """;
        String like = "%" + texto + "%";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }
        return lista;
    }

    public List<Producto> listarActivos() {
        List<Producto> lista = new ArrayList<>();
        String sql = """
                SELECT p.id_producto, p.codigo, p.nombre, p.descripcion, p.activo,
                       u.id_ubicacion, u.pasillo, u.estanteria, u.nivel, u.cajon, u.descripcion AS u_desc
                FROM producto p
                JOIN ubicacion u ON p.id_ubicacion = u.id_ubicacion
                WHERE p.activo = TRUE
                ORDER BY u.pasillo, u.estanteria, p.nombre
                """;
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeCodigo(String codigo) {
        String sql = "SELECT 1 FROM producto WHERE codigo = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al validar código: " + e.getMessage());
            return true;
        }
    }

    public Producto buscarPorId(int id) {
        String sql = """
                SELECT p.id_producto, p.codigo, p.nombre, p.descripcion, p.activo,
                       u.id_ubicacion, u.pasillo, u.estanteria, u.nivel, u.cajon, u.descripcion AS u_desc
                FROM producto p
                JOIN ubicacion u ON p.id_ubicacion = u.id_ubicacion
                WHERE p.id_producto = ?
                """;
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO producto (codigo, nombre, descripcion, activo, id_ubicacion) VALUES (?,?,?,?,?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setBoolean(4, true);
            ps.setInt(5, p.getUbicacion().getIdUbicacion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de alta el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean modificar(Producto p) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, id_ubicacion = ? WHERE id_producto = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getUbicacion().getIdUbicacion());
            ps.setInt(4, p.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean bajaLogica(int idProducto) {
        String sql = "UPDATE producto SET activo = FALSE WHERE id_producto = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al dar de baja: " + e.getMessage());
            return false;
        }
    }

    private Producto map(ResultSet rs) throws SQLException {
        Ubicacion u = new Ubicacion(
                rs.getInt("id_ubicacion"),
                rs.getString("pasillo"),
                rs.getString("estanteria"),
                rs.getString("nivel"),
                rs.getString("cajon"),
                rs.getString("u_desc")
        );
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("activo"),
                u
        );
    }
}