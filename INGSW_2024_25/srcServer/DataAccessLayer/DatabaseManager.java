package DataAccessLayer;

import java.sql.*;
import Class.User;

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

    public  User getUserByUsername(String username) {
        User user = null;

        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}