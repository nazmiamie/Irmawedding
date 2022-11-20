package Koneksi;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wedding irma
 */
public class koneksi {

    private static Connection connection;

    public static Connection getConnection() {
    
    try {
            String url = "jdbc:mysql://localhost:3306/db_irmawedding";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error to Call Connection", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return connection ;
    }

    public static Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
