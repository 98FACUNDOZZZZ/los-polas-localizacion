package servicio;

import dao.UsuarioDAO;
import modelo.Usuario;

public class AuthService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String user, String pass) {
        Usuario u = usuarioDAO.buscarPorNombre(user);
        if (u != null && u.autenticar(user, pass)) {
            return u;
        }
        return null;
    }
}