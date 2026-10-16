package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            "jdbc:mysql://localhost:3306/los_polas_localizacion"
                    + "?useSSL=false&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=America/Argentina/Buenos_Aires";
    private static final String USER = "root";
    private static final String PASS = ""; // más adelante ponemos la contraseña de MySQL

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Falta agregar el connector.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}