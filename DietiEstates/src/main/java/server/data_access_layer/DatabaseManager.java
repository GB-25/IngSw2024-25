package server.data_access_layer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.classi.*;
import server.data_access_layer.interfaces.*;

public class DatabaseManager implements UserRepositoryInterface, HouseRepositoryInterface, ReservationRepositoryInterface{
    private static final String URL = System.getenv("URL_DB"); //"jdbc:postgresql://35.241.167.132:5432/app-db"
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String VALUES = "VALUES";
    private static final String AGENTEIDSTRING = "agente_id";
    private static final String PREZZOSTRING = "prezzo";
    private static final String INDIRIZZOSTRING = "indirizzo";
    private static final String ANNUNCIOSTRING = "annuncio";
    private static final String CLASSEENERGITICASTRING = "classe_energetica";
    private static final String DESCRIZIONESTRING = "descrizione";
    private static final String IMMOBILEID = "immobile_id";
    private static final String ORA ="ora_prenotazione";
    private static final String DATA = "data_prenotazione";
    private static final String USERID = "user_id";
    private Logger logger = Logger.getLogger(getClass().getName());

    private Connection conn;

	/**
     * 
     * Costruttore
     */
    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("✅ Connessione a Cloud SQL riuscita!");
            
        } catch (SQLException e) {
            logger.info("❌ Errore nella connessione a Cloud SQL!");
            logger.severe("");
        }
    }
    
    @Override
    public User getUserByMail(String mail) {
        User user = null;
        String query = "SELECT mail, password, nome, cognome, numeroTelefono, dataNascita, isAgente FROM users WHERE mail = ?"; 

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, mail); 

            ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                    user = new User(rs.getString("mail"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"),
                        rs.getString("numeroTelefono"), rs.getString("dataNascita"), rs.getBoolean("isAgente") );
                }
            
        } catch (SQLException e) {
            logger.severe("Errore in getUserByMail, DB");
        }
        return user;
    }

    
    @Override
    public void register(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
        String query = "INSERT INTO users (mail, password, nome, cognome, numerotelefono, datanascita, isagente) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
        	
        	stmt.setString(1, mail);
            stmt.setString(2, password);
            stmt.setString(3, nome);
            stmt.setString(4, cognome);
            stmt.setString(5, telefono);
            stmt.setString(6, data);
            stmt.setBoolean(7, isAgente);

            stmt.executeUpdate();
           
        } catch (SQLException e) {
            logger.severe("Errore in register, DB");
        }
    }
    
    
    @Override
    public boolean updatePassword(String mail, String nuovaPassword) {
    	String query = "UPDATE users SET password = ? WHERE mail = ?;";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
    		   stmt.setString(1, nuovaPassword);
    		   stmt.setString(2, mail);
               stmt.executeUpdate();
               return true;
           } catch (SQLException e) {
               logger.severe("Errore updatePassword, DB");
               return false;
           }
    }
    
    
    @Override
    public int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
    	String query = "INSERT INTO composizione_immobile (quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo) "
    			+ VALUES + " (?, ?, ?, ?, ?, ?, ?) RETURNING id;";
    	int id=0;
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    			

    		stmt.setInt(1, quadratura);
    		stmt.setInt(2, stanze);
    		stmt.setInt(3, piani);
    		stmt.setBoolean(4, giardino);
    		stmt.setBoolean(5, condominio);
    		stmt.setBoolean(6, ascensore);
    		stmt.setBoolean(7, terrazzo);
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
    	} catch (SQLException e) {
            logger.severe("Errore uploadComposizione, DB");
        }
    	return id;
    }
    
    
    
    @Override
    public ComposizioneImmobile getComposizioneById(int id) {
    	ComposizioneImmobile composizione = null;
    	ComposizioneImmobile composizioneBoolean = null;
    	String query = "SELECT id, quadratura, piani, stanze, terrazzo, giardino, ascensore, condominio FROM composizione_immobile WHERE id = ?;";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    			
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			composizioneBoolean = new ComposizioneImmobile(rs.getBoolean("terrazzo"), rs.getBoolean("giardino"), rs.getBoolean("ascensore"), rs.getBoolean("condominio"));
    			composizione = new ComposizioneImmobile(rs.getInt("id"), rs.getInt("quadratura"), rs.getInt("piani"), rs.getInt("stanze"), composizioneBoolean);
    		}
    	}catch (SQLException e) {
            logger.severe("Errore getComposizioneById, DB");
    	}
    	
    	return composizione;
    }
  
    
    @Override
    public Immobile getHouseByAddress(String indirizzo) {
    	Immobile immobile = null;
    	Immobile immobileDettagli = null;
    	String query = "SELECT id, prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classe_energetica, descrizione, urls, agente_id FROM immobili WHERE indirizzo = ?;";
    	
    
    	 try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, indirizzo);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                	User agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                	ComposizioneImmobile composizione = this.getComposizioneById(rs.getInt("idComposizioneImmobile"));
                	immobileDettagli = new Immobile(rs.getString(CLASSEENERGITICASTRING), rs.getString(INDIRIZZOSTRING), rs.getString("tipo"),rs.getString(ANNUNCIOSTRING));
                	immobile = new Immobile(rs.getInt("id"), rs.getDouble(PREZZOSTRING), composizione, rs.getString(DESCRIZIONESTRING), rs.getString("urls"), agente, immobileDettagli);
                	}
                }catch (SQLException e) {
                    logger.severe("Errore getHouseByAddress, DB");
                }
    	 return immobile;
    	 
    }
    
    
    @Override
    public int uploadHouse(double prezzo, int idComposizioneImmobile, Immobile immobileDettagli, String descrizione, String urls, String agente) {
    	String indirizzo = immobileDettagli.getIndirizzo();
    	String annuncio = immobileDettagli.getAnnuncio();
    	String tipo = immobileDettagli.getTipo();
    	String classeEnergetica = immobileDettagli.getClasseEnergetica();
    	String query = "INSERT INTO immobili ("+ PREZZOSTRING + ", idComposizioneImmobile, "+ INDIRIZZOSTRING +", "+ ANNUNCIOSTRING + ", tipo, "+ CLASSEENERGITICASTRING +", "+ DESCRIZIONESTRING +", urls, " + AGENTEIDSTRING + ")"
    			+ VALUES + " (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";

    	int id=0;
    	
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
    		stmt.setDouble(1, prezzo);
    		stmt.setInt(2, idComposizioneImmobile);
    		stmt.setString(3, indirizzo);
    		stmt.setString(4, annuncio);
    		stmt.setString(5, tipo);
    		stmt.setString(6, classeEnergetica);
    		stmt.setString(7, descrizione);
    		stmt.setString(8, urls);
    		stmt.setString(9, agente);
               ResultSet rs = stmt.executeQuery();
               if (rs.next()) {
                   id = rs.getInt("id");
         
                   }
           } catch (SQLException e) {
               logger.severe("Errore uploadHouse, DB");
           }
    	return id;
    }
    
    @Override
    public boolean alreadyGotAppointment(String mail, boolean isAgente, String data, String ora) {
        String query;
     
        if (isAgente) {
            query = "SELECT * FROM prenotazioni WHERE agente_id = ? AND data_prenotazione = ? AND ora_prenotazione = ? AND is_confirmed = TRUE;";
        } else {
            query = "SELECT * FROM prenotazioni WHERE user_id = ? AND data_prenotazione = ? AND ora_prenotazione = ? AND is_confirmed = TRUE;";
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

        	stmt.setString(1, mail);
            stmt.setString(2, data);
            stmt.setString(3, ora);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true; 
            }
        } catch (SQLException e) {
            logger.severe("Errore alreadyGotAppointment, DB");
        }

        return false; 
    }
    
    @Override
    public Prenotazione checkReservation(String mailCliente, int idImmobile) {
        Prenotazione prenotazione = null;
        String query = "SELECT id, data_prenotazione, ora_prenotazione, user_id, immobile_id, agente_id, is_confirmed FROM prenotazioni WHERE user_id = ? AND immobile_id = ? AND is_Confirmed = TRUE";
       
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {


            stmt.setString(1, mailCliente);
            stmt.setInt(2, idImmobile);

            ResultSet rs = stmt.executeQuery();
               if (rs.next()) {
                   
                   User user = this.getUserByMail(rs.getString(USERID));
                   Immobile immobile = this.getHouseByAddress(rs.getString(IMMOBILEID));
                   User agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                   prenotazione = new Prenotazione( rs.getInt("id"), rs.getString(DATA), rs.getString(ORA), user,
                       immobile, agente, rs.getBoolean("is_Confirmed")
                   );
               }
            
        } catch (SQLException e) {
            logger.severe("Errore checkReservation, DB");
        }

        return prenotazione;
    }


    
    
    @Override
    public void createReservation(String data, String ora, String cliente, int idImmobile, String agente) {
        String query = "INSERT INTO prenotazioni (data_prenotazione, ora_prenotazione, user_id, immobile_id, agente_id, is_confirmed) "
                     + "VALUES (?, ?, ?, ?, ?, FALSE)";  

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, data);
            stmt.setString(2, ora);
            stmt.setString(3, cliente);
            stmt.setInt(4, idImmobile);
            stmt.setString(5, agente);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Errore createReservation, DB"); 
        }
    }

    
    
    @Override
    public ArrayList<Prenotazione> getReservationByMail(String mail, boolean isConfirmed, String data, boolean isAgente) {
        User agente;
        Immobile immobile;
        User cliente;
        String query;
        ArrayList<Prenotazione> lista = new ArrayList<>();

        if (isAgente) {
            query = "SELECT * FROM prenotazioni WHERE agente_id = ? AND is_confirmed = ? AND data_prenotazione = ?";
        } else {
            query = "SELECT * FROM prenotazioni WHERE user_id = ? AND is_confirmed = ? AND data_prenotazione = ?";
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, mail);
            stmt.setBoolean(2, isConfirmed);
            stmt.setString(3, data);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                immobile = this.getHouseById(rs.getInt(IMMOBILEID));
                cliente = this.getUserByMail(rs.getString(USERID));

                Prenotazione prenotazione = new Prenotazione(rs.getInt("id"), rs.getString(DATA),  rs.getString(ORA), cliente,
                    immobile, agente, isConfirmed);
                lista.add(prenotazione);
            }

        } catch (SQLException e) {
            logger.severe("Errore getReservationByMail, DB");
        }

        return lista;
    }

    
    
    @Override
    public void deleteReservation(int id) {
    	String query = "DELETE FROM prenotazioni WHERE id = ?;";
    	
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.setInt(1, id);
    		stmt.executeUpdate();
    		
        } catch (SQLException e) {
            logger.severe("Errore deleteReservation, DB");
        }
    	
    }
    
    
    @Override
    public void confirmReservation(int id) {
    	String query = "UPDATE prenotazioni SET is_confirmed = TRUE WHERE id = ?;";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.setInt(1, id);
    		stmt.executeUpdate();
    		
        } catch (SQLException e) {
            logger.severe("Errore confirmReservation, DB");
        }
    }
    
    
    
    @Override
    public ArrayList<Immobile> findHouses(String query){
    	ComposizioneImmobile composizione;
    	User agente;
    	ArrayList<Immobile> lista = new ArrayList<>();
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
               PreparedStatement stmt = connection.prepareStatement(query)) {
               
               ResultSet rs = stmt.executeQuery();
               
               while (rs.next()) {
            	   agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
            	   composizione = this.getComposizioneById(rs.getInt("idcomposizioneimmobile"));
            	   Immobile immobileDettagli = new Immobile(rs.getString(CLASSEENERGITICASTRING), rs.getString(INDIRIZZOSTRING), rs.getString("tipo"),rs.getString(ANNUNCIOSTRING));
            	   Immobile immobile = new Immobile(rs.getInt("id"), rs.getDouble(PREZZOSTRING), composizione, rs.getString(DESCRIZIONESTRING),rs.getString("urls"), agente, immobileDettagli);
            	   lista.add(immobile);
               }
    	} catch (SQLException e) {
            logger.severe("Errore findHouses, DB");
        }
    	return lista;
    }
    
    
    @Override
    public int getReservationId(String mailCliente, String mailAgente, String data, String ora, int idImmobile, boolean confirmed) {
        String query = "SELECT id FROM prenotazioni WHERE data_prenotazione = ? AND user_id = ? AND ora_prenotazione = ? AND immobile_id = ? AND agente_id = ? AND is_confirmed = ?;";
        int id = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, data);
            stmt.setString(2, mailCliente);
            stmt.setString(3, ora);
            stmt.setInt(4, idImmobile);
            stmt.setString(5, mailAgente);
            stmt.setBoolean(6, confirmed);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            logger.severe("Errore getReservationId, DB"); 
        }

        return id;
    }

    
    public boolean salvaNotifica(Notifica notifica) {
        String sql = "INSERT INTO notifiche (destinatario_email, messaggio, letta) VALUES (?, ?, false)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, notifica.getDestinatarioEmail());
            stmt.setString(2, notifica.getMessaggio());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.severe("Errore salvaNotifica, DB");
            return false;
        }
    }
    
    
    public List<Notifica> getNotificheUtente(String mail) {
        List<Notifica> notifiche = new ArrayList<>();
        String query = "SELECT id, messaggio, letta FROM notifiche WHERE destinatario_email = ? and letta = false;";
        try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String messaggio = rs.getString("messaggio");
                boolean letta = rs.getBoolean("letta");
                notifiche.add(new Notifica(id, mail, messaggio, letta));
            }
        } catch (SQLException e) {
            logger.severe("Errore getNotificheUtente, DB");
        }
        return notifiche;
    }


	@Override
	public boolean setNotifica(int id) {
		String query = "UPDATE notifiche SET letta = TRUE WHERE id = ?;";
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) {
            logger.severe("Errore setNotifica, DB");
            return false;
		}
	}
    
    @Override
	public Immobile getHouseById(int id) {
		ComposizioneImmobile composizione;
    	User agente;
    	Immobile immobileDettagli;
		String query = "SELECT id, prezzo, idcomposizioneimmobile, indirizzo, annuncio, tipo, classe_energetica, descrizione, urls, agente_id FROM immobili WHERE id = ?;";
	
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
			agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
         	   composizione = this.getComposizioneById(rs.getInt("idcomposizioneimmobile"));
         	   immobileDettagli = new Immobile(rs.getString(CLASSEENERGITICASTRING), rs.getString(INDIRIZZOSTRING), rs.getString("tipo"),rs.getString(ANNUNCIOSTRING));
         	   return new Immobile(id, rs.getDouble(PREZZOSTRING), composizione, rs.getString(DESCRIZIONESTRING),rs.getString("urls"), agente, immobileDettagli);
			}
		} catch (SQLException e) {
            logger.severe("Errore getHouseById, DB");
		}
		return null;
	}
	
    @Override
	public boolean updateUrls(String urls, int id) {
		String query = "UPDATE immobili SET urls = ? WHERE id= ?;";
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, urls);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) {
            logger.severe("Errore updateUrls, DB");
            return false;
		}
	}
	
    @Override 
    public Prenotazione getReservationById(int id) {
    	String query = "SELECT id, data_prenotazione, ora_prenotazione, user_id, immobile_id, agente_id, is_confirmed FROM prenotazioni WHERE id = ?;";
    	try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			User cliente = getUserByMail(rs.getString(USERID));
    			User agente = getUserByMail(rs.getString(AGENTEIDSTRING));
    			Immobile immobile = getHouseById(rs.getInt(IMMOBILEID));
    		
    			return new Prenotazione(id, rs.getString(DATA), rs.getString(ORA), cliente, immobile, agente, rs.getBoolean("is_confirmed")); 
    		}
    	}catch (SQLException e) {
            logger.severe("Errore getReservationById, DB");
          
    	}
		return null;
    }
    
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            logger.severe("Errore chiusura DB");
        }
    }	
}
