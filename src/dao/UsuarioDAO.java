package dao;

import conexion.ConexionDB;
import modelo.Administrador;
import modelo.Empleado;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario buscarPorNombre(String nombreUsuario) {
        String sql = "SELECT id_usuario, nombre_usuario, password_hash, rol FROM usuario WHERE nombre_usuario = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    String nombre = rs.getString("nombre_usuario");
                    String hash = rs.getString("password_hash");
                    String rol = rs.getString("rol");
                    if ("ADMINISTRADOR".equals(rol)) {
                        return new Administrador(id, nombre, hash, rol);
                    }
                    return new Empleado(id, nombre, hash, rol);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
}