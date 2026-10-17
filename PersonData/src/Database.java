import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Mengatur koneksi ke database MySQL.
 * Sesuaikan USER dan PASSWORD dengan konfigurasi MySQL lokal.
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/db_people";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
