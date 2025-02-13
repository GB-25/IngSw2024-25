package DataAccessLayer;

import java.sql.*;
import Class.User;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://35.241.167.132:5432/app-db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "passwordpocosicura"; 

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

    public User getUserByMail(String mail) {
        User user = null;

        String query = "SELECT * FROM users WHERE mail = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("mail"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"), rs.getString("numeroTelefono"), rs.getDate("dataNascita"), rs.getBoolean("isAgente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public void register(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	String query = "INSERT INTO users(mail, password, nome, cognome, numerotelefono, datanascita, isagente)"
    			+ "VALUES('"+ mail +"','"+password+"','"+nome+"','"+cognome+"','"+telefono+"','"+data+"','"+isAgente+"');";
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
               //si deve vedere la compatibilià nel db perché data la passiamo come tipo stringa
    			//nel db è salvato ovviamente come date
               stmt.executeUpdate(query);
    		
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    
    
    
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}