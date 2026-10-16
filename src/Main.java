import modelo.Usuario;
import servicio.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        Usuario usuarioLogueado = null;

        System.out.println("=== Materiales Los Polas — Localización de mercadería ===");

        int intentos = 0;
        while (usuarioLogueado == null && intentos < 3) {
            System.out.print("Usuario: ");
            String user = sc.nextLine().trim();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine().trim();
            usuarioLogueado = auth.login(user, pass);
            if (usuarioLogueado == null) {
                intentos++;
                System.out.println("Credenciales incorrectas. Reintentá. (" + intentos + "/3)");
            }
        }

        if (usuarioLogueado == null) {
            System.out.println("Se acabaron los intentos. Chau.");
            return;
        }

        System.out.println("Hola " + usuarioLogueado.getNombreUsuario()
                + " (" + usuarioLogueado.getRol() + ")");
        usuarioLogueado.iniciar(sc);
        System.out.println("Sesión cerrada. Chau.");
        sc.close();
    }
}