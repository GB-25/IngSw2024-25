package DataAccessLayer;

import java.sql.*;
import Class.*;

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

        String query = "SELECT * FROM users WHERE mail = "+mail+";";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("mail"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"), rs.getString("numeroTelefono"), rs.getString("dataNascita"), rs.getBoolean("isAgente"));
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
    
    public void updatePassword(String mail, String nuovaPassword) {
    	String query = "UPDATE users SET password = '"+nuovaPassword+"' WHERE mail = '"+mail+"';";
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
               
               stmt.executeUpdate(query);
    		
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    
    public int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
    	String query = "INSERT INTO composizione_immobile (quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo) "
    			+ "VALUES ('"+quadratura+"'.'"+stanze+"','"+piani+"','"+giardino+"','"+condominio+"','"+ascensore+"','"+terrazzo+"') RETURNING id;";
    	int id=0;
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
    			
    		
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return id;
    }
    
    
    public ComposizioneImmobile getComposizioneById(int id) {
    	ComposizioneImmobile composizione = null;
    	
    	String query = "SELECT * FROM composizioneImmobile WHERE id ="+id+";";
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
    			
    		
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			composizione = new ComposizioneImmobile(rs.getInt("id"), rs.getInt("quadratura"), rs.getInt("piani"), rs.getInt("numeroStanze"), 
    					rs.getBoolean("terrazzo"), rs.getBoolean("giardino"), rs.getBoolean("ascensore"), rs.getBoolean("condominio"));
    		}
    	}catch (SQLException e) {
            e.printStackTrace();
    	}
    	
    	return composizione;
    }
  
    
    
    public Immobile getHouseByAddress(String indirizzo) {
    	Immobile immobile = null;
    	
    	String query = "SELECT * FROM immobili WHERE indirizzo = "+indirizzo+";";
    	
    	 try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                	User agente = this.getUserByMail(rs.getString("agente_id"));
                	ComposizioneImmobile composizione = this.getComposizioneById(rs.getInt("idComposizione"));
                	immobile = new Immobile(rs.getDouble("prezzo"), composizione, rs.getString("indirizzo"), rs.getString("annuncio"),
                			rs.getString("tipo"), rs.getString("classe_energetica"), rs.getString("descrizione"), rs.getString("urls"), agente);
                	}
                }catch (SQLException e) {
                    e.printStackTrace();
                }
    	 return immobile;
    	 
    }
    
    public void uploadHouse(double prezzo,int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, 
					String descrizione, String urls, String agente) {
    	String query = "INSERT INTO immobili (prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classe_energetica, descrizione, urls, agente_id)"
    			+ "VALUES ('"+prezzo+"','"+idComposizioneImmobile+"','"+indirizzo+"','"+annuncio+"','"+tipo+"','" +classeEnergetica+"','"+descrizione+"','"+urls+"','" +agente+"');";
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
               
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