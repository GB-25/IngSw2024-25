package Controller;

import ViewGUI.*;

import model.ClientModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import javax.swing.JComboBox;
import okhttp3.Request;
import okhttp3.Response;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

import Class.ComposizioneImmobile;
import Class.Immobile;
import Class.Prenotazione;
import Class.User;

public class Controller {

	private Point lastPoint;
	//frame
	ProvaLogin finestra;
	FinestraLogin finestraPrincipale;
	ClientModel model;
	FinestraHome homeUtente;
	HomeAgente homeAgente;
	FinestraRegistrazione finestraRegistrazione;
	String ip = "34.78.163.251";
	int porta = 12345;
	private Map<String, List<Runnable>> notificheUtenti = new HashMap<>();
	
	//costruttore
	public Controller() {
		User user = new User("", "", "", "", "", "", true);
		homeAgente = new HomeAgente(this, user);
		homeAgente.setVisible(true);
//		finestra = new ProvaLogin(this);
//		finestra.setVisible(true);
		
		//model = new ClientModel(ip, porta);
		//metodo del model per la connessione, in questo momento sarebbe sendMessage;
	}
	
	
	public void handleLogin (String mail, char[] pass) {
		String password = new String(pass);
		if (mail == null || password == null) {
			JOptionPane.showMessageDialog(null, "Almeno uno dei campi è vuoto", "Errore di Login", JOptionPane.ERROR_MESSAGE);
		}else {
			User user = model.loginModel(mail, password);
			if (user == null) {
				JOptionPane.showMessageDialog(null, "Credenziali errate!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
			} else {
				
				finestraPrincipale.setVisible(false);
				
				if(user.getIsAgente()) {
					
					homeAgente= new HomeAgente(this, user);
					homeAgente.setVisible(true);
				} else {
					homeUtente = new FinestraHome(this, user);
					homeUtente.setVisible(true);
				}
			}
		}
		
	}
	
	public void handleRegistration (String nome, String cognome, String data, String mail, String telefono, char[] pass, boolean isAgente) {
		String password = new String(pass);
		User user = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (user == null) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			finestraRegistrazione.setVisible(false);
			homeUtente = new FinestraHome(this, user);
			homeUtente.setVisible(true);
		}
	}
	
	public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return ((email != null) && (email.matches(emailRegex)));
	}
	
	public boolean isValidNome(String nome) {
		if(nome.length()<3) {
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
	        return fileUrl;  // Restituisce l'URL del file per essere salvato nel database
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
	
	public int createComposition(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
		int id = model.uploadComposition(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		if (id==0) {
			JOptionPane.showMessageDialog(null, "Errore nel caricamento dell'immobile", "Errore", JOptionPane.ERROR_MESSAGE);	
		}
		return id; //da verificare se da problemi il ritorno
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
	
	public void getCoordinates(Controller c, String address, JPanel mapPanel, JXMapViewer mapViewer, boolean isSearchMode) {
	    try {
	        String apiKey = "7a5d95a05b0245eb865812ff441e5e43";
	        String encodedAddress = URLEncoder.encode(address, "UTF-8");
	        double lat = 0;
	        double lon = 0;

	        // Richiesta API per ottenere le coordinate
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
	        }

	        // Rimuovi la mappa precedente se esiste
	        if (mapViewer != null) {
	            mapPanel.remove(mapViewer);
	            System.out.println("Mappa precedente rimossa.");
	        }

	        // Configura la mappa
	        TileFactoryInfo info = new OSMTileFactoryInfo();
	        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
	        mapViewer.setTileFactory(tileFactory);

	        GeoPosition position = new GeoPosition(lat, lon);
	        mapViewer.setAddressLocation(position);
	        mapViewer.setZoom(34);

	        // Waypoint inseriti in base al boolean
	        Set<DefaultWaypoint> waypoints = new HashSet<>();
	        if (isSearchMode) {
	            // Modalità ricerca, quindi più waypoint
	            waypoints.add(new DefaultWaypoint(position));
	            // Qui bisogna poi aggiungere tutti i marker degli immobili nella zona
	            // Sono stati aggiunti esempi per vedere come fungeva il tutto
	            waypoints.add(new DefaultWaypoint(new GeoPosition(lat + 0.01, lon + 0.01)));
	            waypoints.add(new DefaultWaypoint(new GeoPosition(lat - 0.01, lon - 0.01)));
	        } else {
	            // Modalità visualizzazione, solo il waypoint inserito
	            waypoints.add(new DefaultWaypoint(position));
	        }

	        // Configura il painter dei waypoint
	        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
	        waypointPainter.setWaypoints(waypoints);
	        mapViewer.setOverlayPainter(waypointPainter);

	        // Listener per i click solo in modalità ricerca
	        if (isSearchMode) {
	        	mapViewer.addMouseListener(new MouseAdapter() {
	        	    @Override
	        	    public void mouseClicked(MouseEvent e) {
	        	        Point clickPoint = e.getPoint();
	        	        GeoPosition clickGeoPosition = mapViewer.convertPointToGeoPosition(clickPoint);

	        	        for (DefaultWaypoint waypoint : waypoints) {
	        	            GeoPosition waypointGeoPosition = waypoint.getPosition();
	        	            double distance = calculateDistance(clickGeoPosition, waypointGeoPosition);

	        	            if (distance < 0.006) { // Soglia di distanza tra click e waypoint
	        	                new VisioneImmobile(c); // ho aggiunto la schermata base così com'è
	        	                break;
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

	                       // Differenza in pixel tra la posizione corrente e quella precedente
	                       int dx = newPoint.x - lastPoint.x;
	                       int dy = newPoint.y - lastPoint.y;

	                       // Ottieni la posizione geografica corrente del centro della mappa
	                       GeoPosition center = mapViewer.getCenterPosition();

	                       // Usa TileFactory per convertire GeoPosition in Point
	                       TileFactory tileFactory = mapViewer.getTileFactory();
	                       Point2D centerPoint = tileFactory.geoToPixel(center, mapViewer.getZoom());

	                       // Calcola il nuovo centro in base al trascinamento
	                       Point2D newCenterPoint = new Point2D.Double(centerPoint.getX() - dx, centerPoint.getY() - dy);
	                       GeoPosition newCenter = tileFactory.pixelToGeo(newCenterPoint, mapViewer.getZoom());

	                       // Imposta il nuovo centro della mappa
	                       mapViewer.setCenterPosition(newCenter);

	                       // Aggiorna lastPoint per il prossimo evento di trascinamento
	                       lastPoint = newPoint;
	                   }
	               }
	           });
	           mapViewer.addMouseWheelListener(e -> {
	               int zoom = mapViewer.getZoom();
	               if (e.getWheelRotation() < 0) {
	                   mapViewer.setZoom(Math.max(zoom - 1, 0)); // Zoom in
	               } else {
	                   mapViewer.setZoom(Math.min(zoom + 1, 15)); // Zoom out
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
	
	public void uploadHouse(double prezzo, int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, String descrizione,
            String urls, User user) {
		String mail = user.getMail();
		boolean confermato = model.uploadHouseModel(prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classeEnergetica, descrizione, urls, mail);
		if (!confermato) {
			 JOptionPane.showMessageDialog(null, "Immobile già presente", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			new CaricamentoConfermato(this, user);
		}
	}
	
	
	public ArrayList<String> showReservation(User user, boolean isConfirmed, String data) {
		
		String mail = user.getMail();
		boolean isAgente = user.getIsAgente();
		ArrayList<String> prenotazioni = model.getReservation(mail, isConfirmed, data, isAgente);
		if(prenotazioni.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Non sono presenti prenotazioni", "Errore", JOptionPane.ERROR_MESSAGE);
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
			this.notifyAgente( prenotazione);
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
//        for (String url : urlArray) {
//           this.fileDownload(urls);
//        } ha senso che poi io gli passi direttamente l'array e poi dalla view un metodo che associa una label con un elemento dell'array, per avere l'immagine
		return urlArray;
	}
	//boh cosa gli deve tornare, le istanze singole, una lista?
	public ArrayList<Immobile> ricercaImmobili(int prezzoMin, int prezzoMax, String classeEnergetica, String posizione, String tipoImmobile, String annuncio){
		StringBuilder sql = new StringBuilder("SELECT * FROM immobili");
		if (prezzoMin > 0) {
			sql.append(" AND prezzo >= "+prezzoMin);
		}
		if (prezzoMax > 0 && prezzoMax>=prezzoMin) {
			sql.append(" AND prezzo <= "+prezzoMin);
		}
		if (posizione != null) {
			sql.append(" AND TRIM(SPLIT_PART(indirizzo, ',', 2)) LIKE '%"+posizione+"%'");
		}
		if (classeEnergetica != null) {
			sql.append(" AND classe_energetica = "+classeEnergetica);
		}
		if (tipoImmobile != null) {
			sql.append(" AND tipo = "+tipoImmobile);
		}
		if (annuncio != null) {
			sql.append(" AND annuncio = "+annuncio);
		}
		sql.append(";");
		String query = sql.toString();
		
		ArrayList<Immobile> immobili = model.searchHouse(query);
		Immobile casa;
		if (immobili.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Errore durante la ricerca. Prova con altri parametri", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return immobili;
	}
	
	public void notifyAgente(Prenotazione prenotazione) {
		User agente = prenotazione.getAgente(); // Supponendo che tu abbia accesso all'oggetto Immobile
	   

	    // Verifica che i dati siano validi
	    if (agente == null || prenotazione == null) {
	        JOptionPane.showMessageDialog(null, "Errore: dati della prenotazione non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Crea il messaggio della notifica
	    String messaggioNotifica = "Nuova prenotazione con ID: " + prenotazione.getId();

	    // Definisci l'azione da eseguire quando la notifica viene cliccata
	    Runnable azioneNotifica = () -> {
	        // Apri una nuova finestra passando i parametri richiesti
	        VisionePrenotazione visione = new VisionePrenotazione(agente, prenotazione, this);
	        visione.setVisible(true);
	    };

	    // Aggiungi la notifica all'agente
	    aggiungiNotifica(agente.getMail(), messaggioNotifica, azioneNotifica);
	}
	
	private void aggiungiNotifica(String mail, String messaggio, Runnable azione) {
        // Recupera la lista di notifiche dell'utente (o crea una nuova lista se non esiste)
        List<Runnable> notifiche = notificheUtenti.getOrDefault(mail, new ArrayList<>());

        // Aggiungi la notifica come un oggetto Runnable
        notifiche.add(() -> {
            System.out.println("Notifica per " + mail + ": " + messaggio);
            azione.run(); // Esegui l'azione associata alla notifica
        });

        // Aggiorna la mappa delle notifiche
        notificheUtenti.put(mail, notifiche);
    }

    // Metodo per recuperare le notifiche di un utente
    public List<Runnable> getNotificheUtente(String mail) {
        return notificheUtenti.getOrDefault(mail, new ArrayList<>());
    }
	
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
