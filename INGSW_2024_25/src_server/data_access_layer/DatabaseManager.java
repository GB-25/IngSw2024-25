package data_access_layer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import classi.*;
import data_access_layer.interfaces.*;

public class DatabaseManager implements UserRepositoryInterface, HouseRepositoryInterface, ReservationRepositoryInterface{
    private static final String URL = "jdbc:postgresql://35.241.167.132:5432/app-db";
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String SELECTUSERSTRING = "SELECT * FROM prenotazioni WHERE user_id = '";
    private static final String ANDORASTRING = "' AND ora_prenotazione = '";
    private static final String VALUES = "VALUES";
    private static final String AGENTEIDSTRING = "agente_id";
    private static final String PREZZOSTRING = "prezzo";
    private static final String INDIRIZZOSTRING = "indirizzo";
    private static final String ANNUNCIOSTRING = "annuncio";
    private static final String CLASSEENERGITICASTRING = "classe_energetica";
    private static final String DESCRIZIONESTRING = "descrizione";
    Logger logger = Logger.getLogger(getClass().getName());

    private Connection conn;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("✅ Connessione a Cloud SQL riuscita!");
            
        } catch (SQLException e) {
            logger.info("❌ Errore nella connessione a Cloud SQL!");
            e.printStackTrace();
        }
    }
    
    @Override
    public User getUserByMail(String mail) {
        User user = null;

        String query = "SELECT * FROM users WHERE mail = '"+mail+"';";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("mail"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"), rs.getString("numeroTelefono"), rs.getString("dataNascita"), rs.getBoolean("isAgente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    @Override
    public void register(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	String query = "INSERT INTO users(mail, password, nome, cognome, numerotelefono, datanascita, isagente)"
    			+ VALUES + "('"+ mail +"','"+password+"','"+nome+"','"+cognome+"','"+telefono+"','"+data+"','"+isAgente+"');";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               //si deve vedere la compatibilià nel db perché data la passiamo come tipo stringa
    			//nel db è salvato ovviamente come date
               stmt.executeUpdate();
    		
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    
    
    @Override
    public boolean updatePassword(String mail, String nuovaPassword) {
    	String query = "UPDATE users SET password = '"+nuovaPassword+"' WHERE mail = '"+mail+"';";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
               stmt.executeUpdate();
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
    }
    
    
    @Override
    public int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
    	String query = "INSERT INTO composizione_immobile (quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo) "
    			+ VALUES + " ('"+quadratura+"','"+stanze+"','"+piani+"','"+giardino+"','"+condominio+"','"+ascensore+"','"+terrazzo+"') RETURNING id;";
    	int id=0;
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    			
    		
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return id;
    }
    
    
    
    @Override
    public ComposizioneImmobile getComposizioneById(int id) {
    	ComposizioneImmobile composizione = null;
    	
    	String query = "SELECT * FROM composizione_immobile WHERE id ="+id+";";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    			
    		
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			composizione = new ComposizioneImmobile(rs.getInt("id"), rs.getInt("quadratura"), rs.getInt("piani"), rs.getInt("stanze"), 
    					rs.getBoolean("terrazzo"), rs.getBoolean("giardino"), rs.getBoolean("ascensore"), rs.getBoolean("condominio"));
    		}
    	}catch (SQLException e) {
            e.printStackTrace();
    	}
    	
    	return composizione;
    }
  
    
    @Override
    public Immobile getHouseByAddress(String indirizzo) {
    	Immobile immobile = null;
    	
    	String query = "SELECT * FROM immobili WHERE indirizzo = '"+indirizzo+"';";
    	
    	 try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                	User agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                	ComposizioneImmobile composizione = this.getComposizioneById(rs.getInt("idComposizioneImmobile"));
                	immobile = new Immobile(rs.getInt("id"), rs.getDouble(PREZZOSTRING), composizione, rs.getString(INDIRIZZOSTRING), rs.getString(ANNUNCIOSTRING),
                			rs.getString("tipo"), rs.getString(CLASSEENERGITICASTRING), rs.getString(DESCRIZIONESTRING), rs.getString("urls"), agente);
                	}
                }catch (SQLException e) {
                    e.printStackTrace();
                }
    	 return immobile;
    	 
    }
    
    
    @Override
    public int uploadHouse(double prezzo,int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, 
					String descrizione, String urls, String agente) {
    	String query = "INSERT INTO immobili ("+ PREZZOSTRING + ", idComposizioneImmobile, "+ INDIRIZZOSTRING +", "+ ANNUNCIOSTRING + ", tipo, "+ CLASSEENERGITICASTRING +", "+ DESCRIZIONESTRING +", urls, " + AGENTEIDSTRING + ")"
    			+ VALUES + " ('"+prezzo+"','"+idComposizioneImmobile+"','"+indirizzo+"','"+annuncio+"','"+tipo+"','" +classeEnergetica+"','"+descrizione+"','"+urls+"','" +agente+"') RETURNING id;";
    	int id=0;
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
               ResultSet rs = stmt.executeQuery();
               if (rs.next()) {
                   id = rs.getInt("id");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
    	return id;
    }
    
    @Override
    public boolean alreadyGotAppointment(String mail, boolean isAgente, String data, String ora) {
    	
    	String query;
    	if(isAgente) {
    		query = "SELECT * FROM prenotazioni WHERE agente_id = '"+mail+"' AND data_prenotazione = '"+data+ ANDORASTRING +ora+"';";
    	} else {
    		query = SELECTUSERSTRING +mail+"' AND data_prenotazione = '"+data+ANDORASTRING+ora+"';";
    	}
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
               ResultSet rs = stmt.executeQuery();

               if (rs.next()) {
            	   return true;
               }
    	} catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    @Override
    public Prenotazione checkReservation(String mailCliente, int idImmobile) {
    	Prenotazione prenotazione = null;
    	String query = SELECTUSERSTRING +mailCliente+"' AND immobile_id = '"+idImmobile+"' and is_Confirmed = TRUE;";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
               ResultSet rs = stmt.executeQuery();

               if (rs.next()) {
            	   User user = this.getUserByMail(rs.getString("user_id"));
            	   Immobile immobile = this.getHouseByAddress(rs.getString("immobile_id"));
            	   User agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                   prenotazione = new Prenotazione(rs.getInt("id"), rs.getString("data_prenotazione"), rs.getString("ora_prenotazione"), user, immobile , agente, rs.getBoolean("is_Confirmed"));
               	}
           } catch (SQLException e) {
               e.printStackTrace();
           }

           return prenotazione;
       }

    
    
    @Override
    public void createReservation(String data, String ora,  String cliente, int idImmobile, String agente) {
    	String query = "INSERT INTO prenotazioni (data_prenotazione, ora_prenotazione, user_id, immobile_id, agente_id, is_confirmed)"
    			+ VALUES + " ('"+data+"','"+ ora+"','"+cliente+"','"+ idImmobile+"','"+agente+"', FALSE);";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
               
               stmt.executeUpdate();
    		
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    
    
    @Override
    public ArrayList<Prenotazione> getReservationByMail(String mail, boolean isConfirmed, String data, boolean isAgente) {
    	User agente;
    	Immobile immobile;
    	User cliente;
    	String query;
    	ArrayList<Prenotazione> lista = new ArrayList<>();
    	System.out.println("ciao sono il db");
    	if(isAgente) {
    		query = "SELECT * FROM prenotazioni WHERE agente_id = '"+mail+"' AND is_confirmed = "+isConfirmed+" AND data_prenotazione = '"+data+"';";
    	} else {
    		query = SELECTUSERSTRING+mail+"' AND is_confirmed = "+isConfirmed+" AND data_prenotazione = '"+data+"';";
    	}
    	 try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                	agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
                	immobile = this.getHouseById(rs.getInt("immobile_id"));
                	cliente = this.getUserByMail(rs.getString("user_id"));
                	
                	Prenotazione prenotazione = new Prenotazione(rs.getInt("id"), rs.getString("data_prenotazione"), rs.getString("ora_prenotazione"), 
                			cliente, immobile, agente, isConfirmed);
                	lista.add(prenotazione);
                }
                
                
            }catch (SQLException e) {
            	System.out.println("hey sono nel catch del db aiuto");
                e.printStackTrace();
            }
    	 System.out.println("tutto apposto");
    	 return lista;
    }
    
    
    @Override
    public void deleteReservation(int id) {
    	String query = "DELETE FROM prenotazioni WHERE id = "+id+";";
    	
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.executeUpdate();
    		
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    
    @Override
    public void confirmReservation(int id) {
    	String query = "UPDATE prenotazioni SET isConfirmed = TRUE WHERE id = '"+id+"';";
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.executeUpdate();
    		
        } catch (SQLException e) {
            e.printStackTrace();
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
            	   Immobile immobile = new Immobile(rs.getInt("id"), rs.getDouble(PREZZOSTRING), composizione, rs.getString(INDIRIZZOSTRING), rs.getString(ANNUNCIOSTRING),
            			   rs.getString("tipo"), rs.getString(CLASSEENERGITICASTRING), rs.getString(DESCRIZIONESTRING),rs.getString("urls"), agente);
            	   lista.add(immobile);
               }
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return lista;
    }
    
    
    @Override
    public int getReservationId(String mailCliente, String mailAgente, String data, String ora, int idImmobile, boolean confirmed) {
    	String query = "SELECT * FROM prenotazioni WHERE data_prenotazione = '"+data+"' AND user_id = '"+mailCliente+ANDORASTRING+ora+"' AND immobile_id ='"+idImmobile+"' AND agente_id = '"+mailAgente+"' AND is_Confirmed = '"+confirmed+"';";
    	int id=0;
    	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(query)) {
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                	id=rs.getInt("id");
                	}
    		}catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<Notifica> getNotificheUtente(String mail) {
        List<Notifica> notifiche = new ArrayList<>();
        String query = "SELECT id, messaggio, letta FROM notifiche WHERE destinatario_email = '"+mail+"' and letta = false;";
        try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
           
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String messaggio = rs.getString("messaggio");
                boolean letta = rs.getBoolean("letta");
                notifiche.add(new Notifica(id, mail, messaggio, letta));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifiche;
    }


	@Override
	public boolean setNotifica(int id) {
		String query = "UPDATE notifiche SET letta = TRUE WHERE id ='"+id+"';";
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
		}
	}
    
    @Override
	public Immobile getHouseById(int id) {
		ComposizioneImmobile composizione;
    	User agente;
		String query = "SELECT * FROM immobili WHERE id ='"+id+"';";
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
			agente = this.getUserByMail(rs.getString(AGENTEIDSTRING));
         	   composizione = this.getComposizioneById(rs.getInt("idcomposizioneimmobile"));
         	   return new Immobile(id, rs.getDouble(PREZZOSTRING), composizione, rs.getString(INDIRIZZOSTRING), rs.getString(ANNUNCIOSTRING),
         			   rs.getString("tipo"), rs.getString(CLASSEENERGITICASTRING), rs.getString(DESCRIZIONESTRING),rs.getString("urls"), agente);
			}
		} catch (SQLException e) {
            e.printStackTrace();
		}
		return null;
	}
	
    @Override
	public boolean updateUrls(String urls, int id) {
		String query = "UPDATE immobili SET urls ='"+urls+"' WHERE id='"+id+"';";
		try (Connection connection =  DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.executeUpdate(query);
			return true;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
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
