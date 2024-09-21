
package Controlador.Dao.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Victor
 */
public class ConexionBD {
    
    private Connection connection;
    
    public String driver = "oracle.jdbc.OracleDriver";
    public String database = "XE";
    public String hostname = "localhost";
    public String port = "1521";
    public String url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + database;

    public String username = "PIS";
    public String password = "PIS";

    private Connection conectar() throws SQLException {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado!");
        } 
        catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver: " + e.getMessage());
        } 
        catch (SQLException e) {
            throw new SQLException("Error al establecer la conexión: " + e.getMessage());
        }


        return conn;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = conectar();
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
