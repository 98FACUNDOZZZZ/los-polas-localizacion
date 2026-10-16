package dao;

import conexion.ConexionDB;
import modelo.Ubicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {

    public Ubicacion buscarPorId(int id) {
        String sql = "SELECT * FROM ubicacion WHERE id_ubicacion = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar ubicación: " + e.getMessage());
        }
        return null;
    }

    public List<Ubicacion> listar() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion ORDER BY pasillo, estanteria";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ubicaciones: " + e.getMessage());
        }
        return lista;
    }

    public int insertar(Ubicacion u) {
        String sql = "INSERT INTO ubicacion (pasillo, estanteria, nivel, cajon, descripcion) VALUES (?,?,?,?,?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getPasillo());
            ps.setString(2, u.getEstanteria());
            ps.setString(3, u.getNivel());
            ps.setString(4, u.getCajon());
            ps.setString(5, u.getDescripcion());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    u.setIdUbicacion(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al crear ubicación: " + e.getMessage());
        }
        return -1;
    }

    private Ubicacion map(ResultSet rs) throws SQLException {
        return new Ubicacion(
                rs.getInt("id_ubicacion"),
                rs.getString("pasillo"),
                rs.getString("estanteria"),
                rs.getString("nivel"),
                rs.getString("cajon"),
                rs.getString("descripcion")
        );
    }
}