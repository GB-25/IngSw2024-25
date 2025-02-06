package DataAccessLayer;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://34.125.200.50:3306/miodb";
    private static final String USER = "root";
    private static final String PASSWORD = "password"; 

    private Connection conn;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connessione a Cloud SQL riuscita!");
        } catch (SQLException e) {
            System.err.println("❌ Errore nella connessione a Cloud SQL!");
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}