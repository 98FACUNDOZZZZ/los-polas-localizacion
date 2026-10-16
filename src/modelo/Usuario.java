package modelo;

import java.util.Scanner;

public abstract class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String passwordHash;
    private String rol;

    public Usuario(int idUsuario, String nombreUsuario, String passwordHash, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getPasswordHash() { return passwordHash; }
    public String getRol() { return rol; }

    public boolean autenticar(String user, String pass) {
        return this.nombreUsuario.equals(user) && this.passwordHash.equals(pass);
    }

    public abstract void mostrarMenu();
    public abstract void iniciar(Scanner sc);
}