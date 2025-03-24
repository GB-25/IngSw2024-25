package controller;

import model.ClientModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import view_gui.*;

import java.util.List;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.Notifica;
import classi.Prenotazione;
import classi.User;
import eccezioni.*;

public class Controller {

	private Point lastPoint;
	private static final String APIKEYSTRING = "7a5d95a05b0245eb865812ff441e5e43";
	private static final String ERRORE = "Errore";
	private static final String FEATURES = "features";
	private static final String COORDINATESSTRING = "coordinates";
	//frame
	JFrame finestraCorrente;
	JFrame finestraPrincipale;
	ClientModel model;
	SchermataCaricamento schermataCaricamento;
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
	JFrame visionePrenotazione;
	JFrame calendarioAttesa;
	String ip = "34.76.2.59";
	int porta = 12345;
	Logger logger = Logger.getLogger(getClass().getName());
	private static final double MIN = 500;
	private static final double MAX = 1000000;

	
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
			
				homeUtente = new HomeGenerale(this, user);
				homeUtente.setVisible(true);
		}
		
	}
	
	public void handleRegistration (String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
		
		User user = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (user == null) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", ERRORE, JOptionPane.ERROR_MESSAGE);
		} else {
			finestraRegistrazione.setVisible(false);
			homeUtente = new HomeGenerale(this, user);
			homeUtente.setVisible(true);
		}
	}
	
	public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return ((email != null) && (email.matches(emailRegex)));
	}
	
	public boolean isValidNome(String nome) {
		return (nome.length()>2); 
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
		if (password.matches("^.*\\d.*")) {
			valori[3] = true;
		} else {
			valori[3] = false;
		}
	}
	
	public boolean isValidNumero(String numero) {
		return (numero.matches("^\\d{9,10}$"));
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
		boolean ok = model.newPasswordModel(mail, password);
		if (ok) {
			JOptionPane.showMessageDialog(null, "Password aggiornata con successo!", "Operazione Eseguita", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Errore nel sistema! Ci scusiamo per il disagio", ERRORE, JOptionPane.ERROR_MESSAGE);
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
	
	
	public boolean checkComboBox(JComboBox<?> comboBox) {
		String valore = (String) comboBox.getSelectedItem();
        return !(valore.matches("No"));
	}

	public static boolean isNumeric(String prezzo) {
	    return prezzo != null && prezzo.matches("\\d+");
	}
	
	public void getCoordinates(Controller c, String address, JPanel mapPanel, JXMapViewer mapViewer, 
	        boolean isSearchMode, List<Immobile> immobili, User user) throws GeocodingException, URISyntaxException  {
	    try {
	        double[] coordinates = getCoordinatesFromAPI(address);
	        //era == null ma boh
	        if (coordinates.length==0) {
	            JOptionPane.showMessageDialog(null, "Indirizzo non trovato!", ERRORE, JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        configureMap(mapPanel, mapViewer, coordinates[0], coordinates[1]);

	        Map<DefaultWaypoint, Immobile> waypointMap = new HashMap<>();
	        Set<DefaultWaypoint> waypoints = new HashSet<>();

	        if (isSearchMode) {
	            addImmobileWaypoints(immobili, waypointMap, waypoints);
	        } else {
	            waypoints.add(new DefaultWaypoint(new GeoPosition(coordinates[0], coordinates[1])));
	        }

	        configureWaypoints(mapViewer, waypoints);

	        if (isSearchMode) {
	            addWaypointClickListener(c, mapViewer, waypointMap, user);
	        }

	        addMapInteractionListeners(mapViewer);

	        mapViewer.setVisible(true);
	        mapPanel.add(mapViewer);
	        mapViewer.revalidate();
	        mapViewer.repaint();
	    } catch (GeocodingException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, e.getMessage(), ERRORE, JOptionPane.ERROR_MESSAGE);
	    }
	}

	private double[] getCoordinatesFromAPI(String address) throws GeocodingException, URISyntaxException {
	    String apiKey = APIKEYSTRING;
	    String encodedAddress;
	    try {
	        encodedAddress = URLEncoder.encode(address, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        throw new GeocodingException("Failed to encode address", e);
	    }

	    String url = "https://api.geoapify.com/v1/geocode/search?text=" + encodedAddress + "&apiKey=" + apiKey;
	    HttpURLConnection conn;
	    try {
	    	URI uri = new URI(url);
	        conn = (HttpURLConnection) uri.toURL().openConnection();
	        conn.setRequestMethod("GET");
	    } catch (IOException e) {
	        throw new GeocodingException("Failed to establish connection to the API", e);
	    }

	    StringBuilder response = new StringBuilder();
	    try {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            response.append(line);
	        }
	        reader.close();
	    } catch (IOException e) {
	        throw new GeocodingException("Failed to read API response", e);
	    }

	    JSONObject jsonResponse;
	    try {
	        jsonResponse = new JSONObject(response.toString());
	    } catch (JSONException e) {
	        throw new GeocodingException("Failed to parse API response", e);
	    }

	    if (jsonResponse.getJSONArray(FEATURES).length() > 0) {
	        try {
	            JSONObject location = jsonResponse.getJSONArray(FEATURES).getJSONObject(0).getJSONObject("geometry");
	            double lat = location.getJSONArray(COORDINATESSTRING).getDouble(1);
	            double lon = location.getJSONArray(COORDINATESSTRING).getDouble(0);
	            return new double[]{lat, lon};
	        } catch (JSONException e) {
	            throw new GeocodingException("Failed to extract coordinates from API response", e);
	        }
	    } else {
	        throw new GeocodingException("No coordinates found for the given address");
	    }
	}

	private void configureMap(JPanel mapPanel, JXMapViewer mapViewer, double lat, double lon) {
	    mapPanel.removeAll();
	    TileFactoryInfo info = new OSMTileFactoryInfo();
	    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
	    mapViewer.setTileFactory(tileFactory);
	    GeoPosition centerPosition = new GeoPosition(lat, lon);
	    mapViewer.setAddressLocation(centerPosition);
	    mapViewer.setZoom(14);
	}

	private void addImmobileWaypoints(List<Immobile> immobili, Map<DefaultWaypoint, Immobile> waypointMap, Set<DefaultWaypoint> waypoints) {
	    try {
	    		String apiKey = APIKEYSTRING;
	    		
	    		for (Immobile immobile : immobili) {
	    			String immobileAddress = URLEncoder.encode(immobile.getIndirizzo(), "UTF-8");
	    			String immobileUrl = "https://api.geoapify.com/v1/geocode/search?text=" + immobileAddress + "&apiKey=" + apiKey;
	    			URI uri = new URI(immobileUrl);
	    			HttpURLConnection immobileConn = (HttpURLConnection) uri.toURL().openConnection();
	    			immobileConn.setRequestMethod("GET");

	    			BufferedReader immobileReader = new BufferedReader(new InputStreamReader(immobileConn.getInputStream()));
	    			StringBuilder immobileResponse = new StringBuilder();
	    			String immobileLine;
	    			while ((immobileLine = immobileReader.readLine()) != null) {
	    				immobileResponse.append(immobileLine);
	    			}
	    			immobileReader.close();

	    			JSONObject immobileJsonResponse = new JSONObject(immobileResponse.toString());
	    			if (immobileJsonResponse.getJSONArray(FEATURES).length() > 0) {
	    				JSONObject immobileLocation = immobileJsonResponse.getJSONArray(FEATURES).getJSONObject(0).getJSONObject("geometry");
	    				double immobileLat = immobileLocation.getJSONArray(COORDINATESSTRING).getDouble(1);
	    				double immobileLon = immobileLocation.getJSONArray(COORDINATESSTRING).getDouble(0);

	    				GeoPosition immobilePosition = new GeoPosition(immobileLat, immobileLon);
	    				DefaultWaypoint waypoint = new DefaultWaypoint(immobilePosition);
	    				waypoints.add(waypoint);
	    				waypointMap.put(waypoint, immobile);
	    			}
	    		}
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, e.getMessage(), ERRORE, JOptionPane.ERROR_MESSAGE);
	    	}
	    }

	private void configureWaypoints(JXMapViewer mapViewer, Set<DefaultWaypoint> waypoints) {
	    WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
	    waypointPainter.setWaypoints(waypoints);
	    mapViewer.setOverlayPainter(waypointPainter);
	}

	private void addWaypointClickListener(Controller c, JXMapViewer mapViewer, Map<DefaultWaypoint, Immobile> waypointMap, User user) {
	    mapViewer.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            Point clickPoint = e.getPoint();
	            TileFactory tileFactory = mapViewer.getTileFactory();
	            int currentZoom = mapViewer.getZoom();
	            Rectangle viewportBounds = mapViewer.getViewportBounds();

	            // Iterate over the entrySet instead of the keySet
	            for (Map.Entry<DefaultWaypoint, Immobile> entry : waypointMap.entrySet()) {
	                DefaultWaypoint waypoint = entry.getKey();
	                Immobile immobile = entry.getValue();

	                Point2D worldPoint = tileFactory.geoToPixel(waypoint.getPosition(), currentZoom);
	                Point2D viewPoint = new Point2D.Double(worldPoint.getX() - viewportBounds.getX(), worldPoint.getY() - viewportBounds.getY());
	                double pixelDistance = clickPoint.distance(viewPoint);

	                if (pixelDistance < 20) {
	                    schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
	                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
	                        @Override
	                        protected Void doInBackground() throws Exception {
	                            new VisioneImmobile(c, immobile, user);
	                            return null;
	                        }

	                        @Override
	                        protected void done() {  
	                                schermataCaricamento.close();
	                        }
	                    };
	                    worker.execute();
	                }
	            }
	        }
	    });
	}

	private void addMapInteractionListeners(JXMapViewer mapViewer) {
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

	    mapViewer.addMouseWheelListener(e -> {
	        int zoom = mapViewer.getZoom();
	        if (e.getWheelRotation() < 0) {
	            mapViewer.setZoom(Math.max(zoom - 1, 0));
	        } else {
	            mapViewer.setZoom(Math.min(zoom + 1, 15));
	        }
	    });
	}

	public void fetchAddressSuggestions(String query, DefaultListModel<String> listModel) {
    	String apiKey = APIKEYSTRING;
    	String apiUrl = "https://api.geoapify.com/v1/geocode/autocomplete?apiKey=" + apiKey;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(apiUrl).newBuilder();
        urlBuilder.addQueryParameter("text", query);
        urlBuilder.addQueryParameter("lang", "it"); // Lingua italiana
        urlBuilder.addQueryParameter("limit", "5"); // Massimo 5 suggerimenti

        Request request = new Request.Builder().url(urlBuilder.build().toString()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            	logger.info("Errore nella richiesta: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                	logger.info("Errore nella risposta: " + response.code());
                    return;
                }

                String responseData = response.body().string();
                try {
                    JSONObject json = new JSONObject(responseData);
                    JSONArray features = json.getJSONArray(FEATURES);

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
	
	
	
	
	public List<String> showReservation(User user, boolean isConfirmed, String data) {
		
		String mail = user.getMail();
		boolean isAgente = user.getIsAgente();
		return model.getReservation(mail, isConfirmed, data, isAgente);
		
	}
	
	public void createReservation(User user, Immobile immobile, String data, String ora) {
		
		String mailCliente=user.getMail();
		
		int idImmobile = immobile.getId();
		String mailAgente=immobile.getAgente().getMail();
		User agente= immobile.getAgente();
		int id = model.makeReservation(data, ora, mailCliente, idImmobile, mailAgente);
		if (id == 0) {
			 JOptionPane.showMessageDialog(null, "Prenotazione già effettuata per l'immobile o sei già impegnato quel giorno", ERRORE, JOptionPane.ERROR_MESSAGE);
		} else {
			//metodo per mostrare "bravo hai prenotato"
			Prenotazione prenotazione = new Prenotazione(id, data, ora, user, immobile, agente, false);
			this.notifyAgente(prenotazione);
			
		}
	}
	
	public List<Prenotazione> getPrenotazione(User user, LocalDate selectedDateGlobal) {		
		return model.getWholeReservationAgent(user, selectedDateGlobal.toString());
	}
	
	public void viewPendingReservation(JFrame finestraCorrente, User user, Prenotazione prenotazione, Controller c) {
		visionePrenotazione = new VisionePrenotazione (user, prenotazione, c);
		finestraCorrente.setVisible(false);
		visionePrenotazione.setVisible(true);
	}
	
	public void viewReservationsScreen(Controller c, User user, LocalDate selectedDateGlobal) {
		calendarioAttesa = new CalendarioInAttesa(c, user, selectedDateGlobal);
		calendarioAttesa.setVisible(true);
	}

	public boolean reservationConfirm(int id, String mail, String data, String ora) {
		boolean confirmed = model.confirmReservation(id, mail, data, ora);
		if (!confirmed) {
			 JOptionPane.showMessageDialog(null, "Errore durante la conferma: Sei già impeganto quel giorno", ERRORE, JOptionPane.ERROR_MESSAGE);
			 return false;
		} else {
			JOptionPane.showMessageDialog(null, "Prenotazione confermata! Sarà visualizzabile nel calendario", "Nuova visita!", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
	}
	
	
	public void reservationDeny(int id) {
		boolean deleted = model.denyReservation(id);
		if (!deleted) {
			 JOptionPane.showMessageDialog(null, "Errore durante la cancellazione", ERRORE, JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Prenotazione rifiutata! Avviseremo il cliente per te ;)", "Rifiutato", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public String[] getUrls(Immobile immobile) {// o passiamo direttamente le stringhe
		
		String urls = immobile.getUrls();
		return urls.split(",");
	}
	//boh cosa gli deve tornare, le istanze singole, una lista?
	public List<Immobile> ricercaImmobili(double prezzoMin, double prezzoMax, Immobile immobile, ComposizioneImmobile composizione ){
		StringBuilder sql = new StringBuilder("SELECT * FROM immobili WHERE 1=1");
		if(!checkPrezzi(prezzoMin, prezzoMax)) {
			JOptionPane.showMessageDialog(null, "Inserisci un intervallo di prezzo giusto (tra i 500 e 1000000€). Occhio a non mettere il prezzo massimo minore di quello minimo", ERRORE, JOptionPane.ERROR_MESSAGE);
			
		}else {
			sql.append(" AND prezzo >= "+prezzoMin+" AND prezzo <= "+prezzoMax);
			sql.append(" AND TRIM(SPLIT_PART(indirizzo, ',', 2)) ILIKE '%"+immobile.getIndirizzo()+"%'");
			sql.append(checkDettagliImmobile(immobile.getClasseEnergetica(), immobile.getTipo(), immobile.getAnnuncio()));
			sql.append(checkDettagliComposizione(composizione.isAscensore(), composizione.isCondominio(), composizione.isTerrazzo(), composizione.isGiardino()));
		}
		sql.append(";");
		String query = sql.toString();
		
		return model.searchHouse(query);
	}
	
	public void notifyAgente(Prenotazione prenotazione) {
	    User agente = prenotazione.getAgente();
	    
	    if (agente == null) {
	        JOptionPane.showMessageDialog(null, "Errore: dati della prenotazione non validi.", ERRORE, JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    String messaggioNotifica = "Nuova prenotazione per il giorno " + prenotazione.getDataPrenotazione()+ ". Controlla il calendario";

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
	    if (cliente == null ) {
	        JOptionPane.showMessageDialog(null, "Errore: dati della prenotazione non validi.", ERRORE, JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    if (confirmed) {
	        messaggioNotifica = "Prenotazione per l'immobile in "+prenotazione.getImmobile().getIndirizzo()+" confermata!";
	    } else {
	        messaggioNotifica = "La tua prenotazione per l'immobile in "+prenotazione.getImmobile().getIndirizzo()+" è stata rifiutata... ";
	    }
	    
	    // Chiamata che invia la notifica al client
	    aggiungiNotifica(cliente.getMail(), messaggioNotifica);
	}
	
	
	public void setNotificaLetta(Notifica notifica) {
		boolean cancellato = model.notificaLetta(notifica);
		if (!cancellato) {
			JOptionPane.showMessageDialog(null, "Problemi nel recupero delle notifiche. Riprova più tardi", ERRORE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
    public boolean controlCheckBox(JCheckBox box) {
    	return box.isSelected();
    }
    
    public void createHomeAgente(JFrame finestraCorrente, User user) {
    	homeAgente = new HomeGenerale(this, user);
    	finestraCorrente.setVisible(false);
    	homeAgente.setVisible(true);
    	}

    public void createHomeUtente(JFrame finestraCorrente, User user) {
    	homeUtente = new HomeGenerale(this, user);
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
    
    public void showResultImmobili(JFrame finestraCorrente, User user, List<Immobile> ricerca, String indirizzo) throws GeocodingException, URISyntaxException {
    	risultato = new RisultatoRicerca(this, user, ricerca, indirizzo);
    	finestraCorrente.setVisible(false);
    	risultato.setVisible(true);
    }
    
    public void makeReservationClient(JFrame finestraCorrente, Immobile immobile, User user) {
    	prenota = new PrenotazioneCliente(this, immobile, user);
    	finestraCorrente.setVisible(false);
    	prenota.setVisible(true);
    }
    
    public void showImmobile(JFrame finestraCorrente, Immobile immobile, User user) throws GeocodingException, URISyntaxException  {
    	visioneImmobile = new VisioneImmobile(this, immobile, user);
    	finestraCorrente.setVisible(false);
    	visioneImmobile.setVisible(true);
    }
    
    public void uploadNewHouse(Immobile immobile, List<String> foto) {
    	
    	boolean confermato = model.uploadNewHouseModel(immobile, foto);
    	if (!confermato) {
			 JOptionPane.showMessageDialog(null, "Immobile già presente", ERRORE, JOptionPane.ERROR_MESSAGE);
		} else {
			new CaricamentoConfermato(this, immobile.getAgente());
		}
    }
    
    
    public void createAdmin(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	User user = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (user == null) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", ERRORE, JOptionPane.ERROR_MESSAGE);
		} 
    }

	public SchermataCaricamento createSchermataCaricamento(JFrame finestraCorrente, String message) {
    	schermataCaricamento = new SchermataCaricamento(finestraCorrente, message);
    	return schermataCaricamento;
    }

	public boolean checkPrezzi(double prezzoMin, double prezzoMax) {
		 return (prezzoMin >= MIN && prezzoMin <= MAX) && (prezzoMax >= MIN && prezzoMax <= MAX) && (prezzoMax >= prezzoMin);
	}
	
	public String checkDettagliComposizione(boolean ascensore, boolean condominio, boolean terrazzo, boolean giardino) {
		
		StringBuilder string = new StringBuilder();
		if (ascensore) {
			string.append(" AND ascensore = 'TRUE'");
		}
		if (condominio) {
			string.append(" AND condominio = 'TRUE'");
		}
		if (terrazzo) {
			string.append(" AND terrazzo = 'TRUE'");
		}
		if (giardino) {
			string.append(" AND giardino = 'TRUE'");
		}
		
		return string.toString();
	}
	
	public String checkDettagliImmobile(String classe, String tipo, String annuncio) {
		StringBuilder string = new StringBuilder();
		
		if (!classe.isBlank()) {
			string.append(" AND classe_energetica = '"+classe+"'");
		}
		if (!tipo.isBlank()) {
			string.append(" AND tipo = '"+tipo+"'");
		}
		if (!annuncio.isBlank()) {
			string.append(" AND annuncio = '"+annuncio+"'");
		}
		return string.toString();
	}
	
	public static void main(String[] args)
	{
		new Controller();
		
	}
}
