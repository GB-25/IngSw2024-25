package Controller;

import ViewGUI.*;

import model.ClientModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;


import Class.Immobile;
import Class.Notifica;
import Class.Prenotazione;
import Class.User;

public class Controller {

	private Point lastPoint;
	//frame
	JFrame finestraCorrente;
	JFrame finestraPrincipale;
	ClientModel model;
	JFrame schermataCaricamento;
	JFrame homeUtente;
	JFrame homeAgente;
	JFrame finestraRegistrazione;
	JFrame caricamento;
	JFrame caricamentoConfermato;
	JFrame creazioneAdmin;
	JFrame cambioPassword;
	JFrame visioneCalendario;
	JFrame ricerca;
	JFrame risultato;
	JFrame prenota;
	JFrame visioneImmobile;
	JFrame prenotazioneConfermata;
	String ip = "34.76.2.59";
	int porta = 12345;
	private Map<String, List<Runnable>> notificheUtenti = new HashMap<>();
	
	//costruttore
	public Controller() {

		finestraPrincipale = new ProvaLogin(this);
		finestraPrincipale.setVisible(true);

		model = new ClientModel(ip, porta);
		
	}
	
	
	public void handleLogin (String mail, char[] pass) {
		String password = new String(pass);
		
		User user = model.loginModel(mail, password);
		if (user == null) {
			JOptionPane.showMessageDialog(null, "Credenziali errate!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
		} else {
				
			finestraPrincipale.setVisible(false);
			
			if(user.getIsAgente()) {
					
				homeAgente= new HomeAgente(this, user);
				homeAgente.setVisible(true);
			} else {
				homeUtente = new HomeCliente(this, user);
				homeUtente.setVisible(true);
				
			}
		}
		
	}
	
	public void handleRegistration (String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
		
		User user = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (user == null) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			finestraRegistrazione.setVisible(false);
			homeUtente = new HomeCliente(this, user);
			homeUtente.setVisible(true);
		}
	}
	
	public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return ((email != null) && (email.matches(emailRegex)));
	}
	
	public boolean isValidNome(String nome) {
		if(nome.length()<2) {
			return false;
		}
		return true;
	}
	
	public void isValidPassword(char[] pass, boolean[] valori) {
		
		String password = new String(pass);
		
		if (password.length()<6) {
			valori[0] = false;
		} else {
			valori[0] = true;
		}
		if (password.matches("^.*[A-Z].*")) {
			valori[1] = true;
		} else {
			valori[1] = false;
		}
		if (password.matches("^.*[a-z].*")) {
			valori[2] = true;
		} else {
			valori[2] = false;
		}
		if (password.matches("^.*[0-9].*")) {
			valori[3] = true;
		} else {
			valori[3] = false;
		}
	}
	
	public boolean isValidNumero(String numero) {
		if (numero.matches("^\\d{9,10}$")) {
			return true;
		}
		return false;
	}
	
	public boolean verifyPassword(char[] password1, char[] password2) {
		return Arrays.equals(password1, password2);
	}
	
	public boolean checkFields(boolean[] controllo) {
		for (boolean value : controllo) {
	        if (!value) { // Se almeno uno è false, restituisci false
	            return false;
	        }
	    }
	    return true;
	}
	
	
	public void cambiaFinestra(JFrame vecchiaFinestra, JFrame nuovaFinestra) {
		vecchiaFinestra.setVisible(false);
		nuovaFinestra.setVisible(true);
	}
	
	public void updatePassword(User user, char[] pass) {
		String password = new String(pass);
		String mail = user.getMail();
		model.newPasswordModel(mail, password);
		
	}
	
	public String fileUpload(String filePath) {
	    String fileUrl = model.uploadFileModel(filePath);
	    if (!fileUrl.isEmpty()) {
	        return fileUrl;  //Restituisce l'URL del file per essere salvato nel database
	    } else {	        
	        return null;
	    }
	}
	
	public String fileDownload(String fileName) {
		String fileData = model.downloadFileModel(fileName);
        if (!fileData.isEmpty()) {
            return fileData;  // Questa stringa Base64 potrà essere decodificata dal client per visualizzare l'immagine
        } else {
            return null;
        }
    }
	
	
	public boolean checkComboBox(JComboBox comboBox) {
		String valore = (String) comboBox.getSelectedItem();
        if (valore.matches("No")){
        	return false;
        } else {
        	return true;
        }
	}

	private double calculateDistance(GeoPosition pos1, GeoPosition pos2) {
	    double lat1 = pos1.getLatitude();
	    double lon1 = pos1.getLongitude();
	    double lat2 = pos2.getLatitude();
	    double lon2 = pos2.getLongitude();

	    // Formula di Haversine per calcolare la distanza tra due coordinate
	    double R = 6371; // Raggio della Terra in km
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return R * c;
	}
	
	public void getCoordinates(Controller c, String address, JPanel mapPanel, JXMapViewer mapViewer, 
            boolean isSearchMode, ArrayList<Immobile> immobili, User user) {
				try {
						String apiKey = "7a5d95a05b0245eb865812ff441e5e43";
						String encodedAddress = URLEncoder.encode(address, "UTF-8");

						double lat = 0;
						double lon = 0;

						// Richiesta API per ottenere le coordinate dell'indirizzo principale	
						String url = "https://api.geoapify.com/v1/geocode/search?text=" + encodedAddress + "&apiKey=" + apiKey;
						HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
						conn.setRequestMethod("GET");

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						StringBuilder response = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
								response.append(line);
						}
						reader.close();

						JSONObject jsonResponse = new JSONObject(response.toString());
						if (jsonResponse.getJSONArray("features").length() > 0) {
							JSONObject location = jsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry");
							lat = location.getJSONArray("coordinates").getDouble(1);
							lon = location.getJSONArray("coordinates").getDouble(0);
						} else {
							JOptionPane.showMessageDialog(null, "Indirizzo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
							return;
						}

						// Rimuovi la mappa precedente se esiste
						mapPanel.removeAll();

						// Configura la mappa
						TileFactoryInfo info = new OSMTileFactoryInfo();
						DefaultTileFactory tileFactory = new DefaultTileFactory(info);
						mapViewer.setTileFactory(tileFactory);

						GeoPosition centerPosition = new GeoPosition(lat, lon);
						mapViewer.setAddressLocation(centerPosition);
						mapViewer.setZoom(14);

						// Manteniamo una mappa waypoint -> immobile per i click
						Map<DefaultWaypoint, Immobile> waypointMap = new HashMap<>();
						Set<DefaultWaypoint> waypoints = new HashSet<>();

						if (isSearchMode) {
							for (Immobile immobile : immobili) {
								try {
									String immobileAddress = URLEncoder.encode(immobile.getIndirizzo(), "UTF-8");
									String immobileUrl = "https://api.geoapify.com/v1/geocode/search?text=" + immobileAddress + "&apiKey=" + apiKey;
									HttpURLConnection immobileConn = (HttpURLConnection) new URL(immobileUrl).openConnection();
									immobileConn.setRequestMethod("GET");

									BufferedReader immobileReader = new BufferedReader(new InputStreamReader(immobileConn.getInputStream()));
									StringBuilder immobileResponse = new StringBuilder();
									String immobileLine;
									while ((immobileLine = immobileReader.readLine()) != null) {
										immobileResponse.append(immobileLine);
									}
									immobileReader.close();

									JSONObject immobileJsonResponse = new JSONObject(immobileResponse.toString());
									if (immobileJsonResponse.getJSONArray("features").length() > 0) {
										JSONObject immobileLocation = immobileJsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry");
										double immobileLat = immobileLocation.getJSONArray("coordinates").getDouble(1);
										double immobileLon = immobileLocation.getJSONArray("coordinates").getDouble(0);

										GeoPosition immobilePosition = new GeoPosition(immobileLat, immobileLon);
										DefaultWaypoint waypoint = new DefaultWaypoint(immobilePosition);
										waypoints.add(waypoint);
										waypointMap.put(waypoint, immobile);
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						} else {
							DefaultWaypoint waypoint = new DefaultWaypoint(centerPosition);
							waypoints.add(waypoint);
						}

						// Configura il painter dei waypoint
						WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
						waypointPainter.setWaypoints(waypoints);
						mapViewer.setOverlayPainter(waypointPainter);

						// Listener per i click sui waypoint
						if (isSearchMode) {
						    mapViewer.addMouseListener(new MouseAdapter() {
						        @Override
						        public void mouseClicked(MouseEvent e) {
						            Point clickPoint = e.getPoint();
						            System.out.println("Click point: " + clickPoint);

						            TileFactory tileFactory = mapViewer.getTileFactory();
						            int currentZoom = mapViewer.getZoom();
						            // Ottieni l'offset della viewport
						            Rectangle viewportBounds = mapViewer.getViewportBounds();
						            
						            for (DefaultWaypoint waypoint : waypointMap.keySet()) {
						                // Coordinate "world" del waypoint
						                Point2D worldPoint = tileFactory.geoToPixel(waypoint.getPosition(), currentZoom);
						                // Convertilo in coordinate della vista sottraendo l'offset della viewport
						                Point2D viewPoint = new Point2D.Double(
						                        worldPoint.getX() - viewportBounds.getX(),
						                        worldPoint.getY() - viewportBounds.getY()
						                );
						                double pixelDistance = clickPoint.distance(viewPoint);
						                System.out.println("Waypoint view point: " + viewPoint + " distanza: " + pixelDistance);
						                
						                if (pixelDistance < 20) { // Aumenta la soglia per testare
						                	c.createSchermataCaricamento(finestraCorrente, "Caricamento");
						                	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						                        @Override
						                        protected Void doInBackground() throws Exception {
						                    Immobile immobileSelezionato = waypointMap.get(waypoint);
						                    System.out.println("Waypoint cliccato: " + immobileSelezionato); 
						                    new VisioneImmobile(c, immobileSelezionato, user);
						                    return null;}
						                        @Override
						                        protected void done() {
						                        	if (c.schermataCaricamento != null) {
						                                c.schermataCaricamento.dispose(); // Chiude solo la schermata di caricamento
						                            }
						                        }
						       			};
						       			worker.execute();
						                }
						            }
						        }
						    });
						}
						// Implementazione del drag della mappa
						mapViewer.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								lastPoint = e.getPoint();
								}

							@Override
							public void mouseReleased(MouseEvent e) {
								lastPoint = null;
							}
						});

						mapViewer.addMouseMotionListener(new MouseMotionAdapter() {
							@Override
							public void mouseDragged(MouseEvent e) {
								if (lastPoint != null) {
									Point newPoint = e.getPoint();
									int dx = newPoint.x - lastPoint.x;
									int dy = newPoint.y - lastPoint.y;
     
									GeoPosition center = mapViewer.getCenterPosition();
									TileFactory tileFactory = mapViewer.getTileFactory();
									Point2D centerPoint = tileFactory.geoToPixel(center, mapViewer.getZoom());

									Point2D newCenterPoint = new Point2D.Double(centerPoint.getX() - dx, centerPoint.getY() - dy);
									GeoPosition newCenter = tileFactory.pixelToGeo(newCenterPoint, mapViewer.getZoom());

									mapViewer.setCenterPosition(newCenter);
									lastPoint = newPoint;
								}
							}
						});

						// Zoom con rotella del mouse
						mapViewer.addMouseWheelListener(e -> {
							int zoom = mapViewer.getZoom();
							if (e.getWheelRotation() < 0) {
								mapViewer.setZoom(Math.max(zoom - 1, 0));
							} else {
								mapViewer.setZoom(Math.min(zoom + 1, 15));
							}
						});

						// Aggiungi la mappa al pannello
						mapViewer.setVisible(true);
						mapPanel.add(mapViewer);
						mapViewer.revalidate();
						mapViewer.repaint();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Errore nel recupero della posizione!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
	}

	public void fetchAddressSuggestions(String query, DefaultListModel<String> listModel) {
    	String API_KEY = "7a5d95a05b0245eb865812ff441e5e43";
    	String API_URL = "https://api.geoapify.com/v1/geocode/autocomplete?apiKey=" + API_KEY;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addQueryParameter("text", query);
        urlBuilder.addQueryParameter("lang", "it"); // Lingua italiana
        urlBuilder.addQueryParameter("limit", "5"); // Massimo 5 suggerimenti

        Request request = new Request.Builder().url(urlBuilder.build().toString()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("Errore nella richiesta: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.err.println("Errore nella risposta: " + response.code());
                    return;
                }

                String responseData = response.body().string();
                try {
                    JSONObject json = new JSONObject(responseData);
                    JSONArray features = json.getJSONArray("features");

                    ArrayList<String> addresses = new ArrayList<>();
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject feature = features.getJSONObject(i);
                        String address = feature.getJSONObject("properties").getString("formatted");
                        addresses.add(address);
                    }

                    SwingUtilities.invokeLater(() -> {
                        listModel.clear();
                        addresses.forEach(listModel::addElement);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	
	
	
	public ArrayList<String> showReservation(User user, boolean isConfirmed, String data) {
		
		String mail = user.getMail();
		boolean isAgente = user.getIsAgente();
		ArrayList<String> prenotazioni = model.getReservation(mail, isConfirmed, data, isAgente);
		if(prenotazioni.isEmpty()) {
			//JOptionPane.showMessageDialog(null, "Non sono presenti prenotazioni", "Errore", JOptionPane.ERROR_MESSAGE);
		} 
		return prenotazioni;
	}
	
	public void createReservation(User user, Immobile immobile, String data, String ora) {
		int id=0;
		String mailCliente=user.getMail();
		String indirizzo=immobile.getIndirizzo();
		String mailAgente=immobile.getAgente().getMail();
		User agente= immobile.getAgente();
		boolean reservation = model.makeReservation(data, ora, mailCliente, indirizzo, mailAgente, id);
		if (!reservation) {
			 JOptionPane.showMessageDialog(null, "Prenotazione già effettuata per l'immobile o sei già impegnato quel giorno", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			//metodo per mostrare "bravo hai prenotato"
			Prenotazione prenotazione = new Prenotazione(id, data, ora, user, immobile, agente, false);
			this.notifyAgente(prenotazione);
			
		}
	}
	

	public boolean reservationConfirm(int id, String mail, String data, String ora) {
		boolean confirmed = model.confirmReservation(id, mail, data, ora);
		if (!confirmed) {
			 JOptionPane.showMessageDialog(null, "Errore durante la conferma: Sei già impeganto quel giorno", "Errore", JOptionPane.ERROR_MESSAGE);
			 return false;
		} else {
			JOptionPane.showMessageDialog(null, "Prenotazione confermata! Sarà visualizzabile nel calendario", "Nuova visita!", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
	}
	
	
	public void reservationDeny(int id) {
		boolean deleted = model.denyReservation(id);
		if (!deleted) {
			 JOptionPane.showMessageDialog(null, "Errore durante la cancellazione", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Prenotazione rifiutata! Avviseremo il cliente per te ;)", "Rifiutato", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public String[] getUrls(Immobile immobile) {// o passiamo direttamente le stringhe
		
		String urls = immobile.getUrls();
		String[] urlArray = urls.split(",");

        // Stampare gli URL uno per uno
//        } ha senso che poi io gli passi direttamente l'array e poi dalla view un metodo che associa una label con un elemento dell'array, per avere l'immagine
		return urlArray;
	}
	//boh cosa gli deve tornare, le istanze singole, una lista?
	public ArrayList<Immobile> ricercaImmobili(double prezzoMin, double prezzoMax, String classeEnergetica, String posizione, String tipoImmobile, String annuncio, boolean ascensore, boolean condominio, boolean terrazzo, boolean giardino){
		StringBuilder sql = new StringBuilder("SELECT * FROM immobili WHERE 1=1");
		if (prezzoMin > 0 ) {
			sql.append(" AND prezzo >= "+prezzoMin);
		}
		if (prezzoMax > 0 && prezzoMax>=prezzoMin) {
			sql.append(" AND prezzo <= "+prezzoMin);
		}
		if (posizione != null) {
			sql.append(" AND TRIM(SPLIT_PART(indirizzo, ',', 2)) ILIKE '%"+posizione+"%'");
		}
		if (!classeEnergetica.isBlank()) {
			sql.append(" AND classe_energetica = '"+classeEnergetica+"'");
		}
		if (!tipoImmobile.isBlank()) {
			sql.append(" AND tipo = '"+tipoImmobile+"'");
		}
		if (!annuncio.isBlank()) {
			sql.append(" AND annuncio = '"+annuncio+"'");
		}
		if (ascensore) {
			sql.append(" AND ascensore = 'TRUE'");
		}
		if (condominio) {
			sql.append(" AND condominio = 'TRUE'");
		}
		if (terrazzo) {
			sql.append(" AND terrazzo = 'TRUE'");
		}
		if (giardino) {
			sql.append(" AND giardino = 'TRUE'");
		}
		sql.append(";");
		String query = sql.toString();
		
		ArrayList<Immobile> immobili = model.searchHouse(query);
	
		if (immobili.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Errore durante la ricerca. Prova con altri parametri", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return immobili;
	}
	
	public void notifyAgente(Prenotazione prenotazione) {
	    User agente = prenotazione.getAgente();
	    
	    if (agente == null || prenotazione == null) {
	        JOptionPane.showMessageDialog(null, "Errore: dati della prenotazione non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    String messaggioNotifica = "Nuova prenotazione con ID: " + prenotazione.getId();

	    // (In precedenza veniva definito un Runnable per l'azione,
	    // ora la logica d'azione potrebbe essere gestita in altro modo o via interfaccia utente.)
	    
	    // Qui viene chiamato il metodo che crea e invia la notifica
	    aggiungiNotifica(agente.getMail(), messaggioNotifica);
	}

	
	private void aggiungiNotifica(String mail, String messaggio) {
	    Notifica nuovaNotifica = new Notifica(mail, messaggio);
	    // Invia la richiesta al ClientModel per salvare la notifica nel DB
	    boolean success = model.inviaRichiestaNotifica(nuovaNotifica);
	    if (!success) {
	        // Gestisci l'errore (ad es. log o messaggio di errore)
	    }
	}

    // Metodo per recuperare le notifiche di un utente
	public List<Notifica> getNotificheUtente(String mail) {
	    return model.richiestaNotificheUtente(mail);
	}
	
	public void notifyCliente(Prenotazione prenotazione, boolean confirmed) {
	    User cliente = prenotazione.getUser();
	    String messaggioNotifica;
	    // Anche qui si verificano i dati
	    if (cliente == null || prenotazione == null) {
	        JOptionPane.showMessageDialog(null, "Errore: dati della prenotazione non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    if (confirmed) {
	        messaggioNotifica = "La tua prenotazione è stata confermata! Puoi vederla nel calendario";
	    } else {
	        messaggioNotifica = "La tua prenotazione è stata rifiutata... Prova a chiedere un nuovo appuntamento";
	    }
	    
	    // Chiamata che invia la notifica al client
	    aggiungiNotifica(cliente.getMail(), messaggioNotifica);
	}
	
    public boolean controlCheckBox(JCheckBox box) {
    	if(box.isSelected()) {
    		return true;
    	}
    	return false;
    }
    
    public void createHomeAgente(JFrame finestraCorrente, User user) {
    	homeAgente = new HomeAgente(this, user);
    	finestraCorrente.setVisible(false);
    	homeAgente.setVisible(true);
    	}

    public void createHomeUtente(JFrame finestraCorrente, User user) {
    	homeUtente = new HomeCliente(this, user);
    	finestraCorrente.setVisible(false);
    	homeUtente.setVisible(true);
    	}

    public void reservationConfirmed(JFrame finestraCorrente, User user) {
    	prenotazioneConfermata = new PrenotazioneConfermata(this, user);
    	finestraCorrente.setVisible(false);
    	prenotazioneConfermata.setVisible(true);
        }
    
    public void confermaCaricamento(JFrame finestraCorrente, User user) {
    	caricamentoConfermato = new CaricamentoConfermato (this, user);
    	finestraCorrente.setVisible(false);
    	caricamentoConfermato.setVisible(true);
    }
    
    public void returnLogin(JFrame finestraCorrente) {
    	finestraPrincipale = new ProvaLogin(this);
    	finestraCorrente.setVisible(false);
    	finestraPrincipale.setVisible(true);
    }
    
    public void createAdmin(JFrame finestraCorrente, User user) {
    	creazioneAdmin = new CreazioneAccountAdmin(this, user);
    	finestraCorrente.setVisible(false);
    	creazioneAdmin.setVisible(true);
    }
    
    public void createCaricamentoImmobile(JFrame finestraCorrente, User user) {
    	caricamento = new CaricamentoProprietaNuovo(this, user);
    	finestraCorrente.setVisible(false);
    	caricamento.setVisible(true);
    }
    
    public void changePassword(JFrame finestraCorrente, User user) {
    	cambioPassword = new CambioPassword(this, user);
    	finestraCorrente.setVisible(false);
    	cambioPassword.setVisible(true);
    }
    
    public void viewCalendar(JFrame finestraCorrente, User user) {
    	visioneCalendario = new VisioneCalendario(this, user);
    	finestraCorrente.setVisible(false);
    	visioneCalendario.setVisible(true);
    }
    
    public void findImmobili(JFrame finestraCorrente, User user) {
    	ricerca = new RicercaImmobili(this, user);
    	finestraCorrente.setVisible(false);
    	ricerca.setVisible(true);
    }
    
    public void registerUser(JFrame finestraCorrente) {
    	finestraRegistrazione = new FinestraRegistrazione(this);
    	finestraCorrente.setVisible(false);
    	finestraRegistrazione.setVisible(true);
    }
    
    public void showResultImmobili(JFrame finestraCorrente, User user, ArrayList<Immobile> ricerca, String indirizzo) {
    	risultato = new RisultatoRicerca(this, user, ricerca, indirizzo);
    	finestraCorrente.setVisible(false);
    	risultato.setVisible(true);
    }
    
    public void makeReservationClient(JFrame finestraCorrente, Immobile immobile, User user) {
    	prenota = new PrenotazioneCliente(this, immobile, user);
    	finestraCorrente.setVisible(false);
    	prenota.setVisible(true);
    }
    
    public void showImmobile(JFrame finestraCorrente, Immobile immobile, User user) {
    	visioneImmobile = new VisioneImmobile(this, immobile, user);
    	finestraCorrente.setVisible(false);
    	visioneImmobile.setVisible(true);
    }
    
    public void uploadNewHouse(Immobile immobile) {
    	
    	boolean confermato = model.uploadNewHouseModel(immobile);
    	if (!confermato) {
			 JOptionPane.showMessageDialog(null, "Immobile già presente", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			new CaricamentoConfermato(this, immobile.getAgente());
		}
    }
    
    
    public void createAdmin(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	User user = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (user == null) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
		} 
    }

	public void createSchermataCaricamento(JFrame finestraCorrente, String message) {
    	schermataCaricamento = new SchermataCaricamento(finestraCorrente, message);
    	schermataCaricamento.setVisible(true);
    }

	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
