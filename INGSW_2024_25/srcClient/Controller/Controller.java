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
import java.util.HashSet;
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

import Class.User;

public class Controller {

	private Point lastPoint;
	//frame
	
	FinestraLogin finestraPrincipale;
	ClientModel model;
	FinestraHome homeUtente;
	HomeAgente homeAgente;
	FinestraRegistrazione finestraRegistrazione;
	String ip = "34.78.163.251";
	int porta = 12345;
	
	//costruttore
	public Controller() {
		User user = new User("", "", "", "", "", "", true);
		homeAgente = new HomeAgente(this, user);
		homeAgente.setVisible(true);
		//model = new ClientModel(ip, porta);
		//metodo del model per la connessione, in questo momento sarebbe sendMessage;
	}
	
	
	public void handleLogin (String mail, char[] pass) {
		String password = new String(pass);
		if (mail == null || password == null) {
			JOptionPane.showMessageDialog(null, "Almeno uno dei campi è vuoto", "Errore di Login", JOptionPane.ERROR_MESSAGE);
		}else {
			JSONObject response = model.loginModel(mail, password);
			if (response.getString("status").equals("error")) {
				JOptionPane.showMessageDialog(null, "Credenziali errate!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
			} else {
				
				finestraPrincipale.setVisible(false);
				String nome = response.getString("nome");
				String cognome = response.getString("cognome");
				String dataNascita = response.getString("dataNascita");
				String telefono = response.getString("telefono");
				Boolean isAgente = response.getBoolean("isAgente");
				
				User user = new User(mail, password, nome, cognome, telefono, dataNascita, isAgente );
				if(user.getIsAgente()) {
					
					homeAgente= new HomeAgente(this, user);
					homeAgente.setVisible(true);
				} else {
					homeUtente = new FinestraHome(this);
					homeUtente.setVisible(true);
				}
			}
		}
		
	}
	
	public void handleRegistration (String nome, String cognome, String data, String mail, String telefono, char[] pass, boolean isAgente) {
		String password = new String(pass);
		JSONObject response = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (response.getString("status").equals("error")) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			finestraRegistrazione.setVisible(false);
			homeUtente = new FinestraHome(this);
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
	    JSONObject response = model.uploadFileModel(filePath);

	    if (response.getString("status").equals("success")) {
	        String fileUrl = response.getString("fileUrl"); // URL del file caricato
	        System.out.println("File caricato con successo: " + fileUrl);
	        return fileUrl;  // Restituisce l'URL del file per essere salvato nel database
	    } else {
	        System.err.println("Errore durante l'upload: " + response.getString("message"));
	        return null;
	    }
	}
	
	public String fileDownload(String fileName) {
        JSONObject response = model.downloadFileModel(fileName);
        if (response.getString("status").equals("success")) {
            String fileData = response.getString("fileData");
            System.out.println("File scaricato con successo.");
            return fileData;  // Questa stringa Base64 potrà essere decodificata dal client per visualizzare l'immagine
        } else {
            System.err.println("Errore durante il download: " + response.getString("message"));
            return null;
        }
    }
	
	public int createComposition(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
		int id = 0;
		JSONObject response = model.uploadComposition(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		if (response.getString("status").equals("error")) {
			JOptionPane.showMessageDialog(null, "Errore nel caricamento dell'immobile", "Errore", JOptionPane.ERROR_MESSAGE);	
		}
		else {
			id = response.getInt("id");
		}
		return id;
	}
	
	public boolean checkComboBox(JComboBox comboBox) {
		String valore = (String) comboBox.getSelectedItem();
        if (valore.matches("No")){
        	return false;
        } else {
        	return true;
        }
	}

	public void getCoordinates(String address, JPanel mapPanel, JXMapViewer mapViewer) {
        try {
            String apiKey = "7a5d95a05b0245eb865812ff441e5e43";  
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            double lat = 0;
            double lon = 0;

            // Costruzione dell'URL per la richiesta di geocoding
            String url = "https://api.geoapify.com/v1/geocode/search?text=" + encodedAddress + "&apiKey=" + apiKey;

            // Connessione alla API
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            // Lettura della risposta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Analisi della risposta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getJSONArray("features").length() > 0) {
                // Estrazione delle coordinate (latitudine e longitudine)
                JSONObject location = jsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry");
                lat = location.getJSONArray("coordinates").getDouble(1);
                lon = location.getJSONArray("coordinates").getDouble(0);
            } else {
                JOptionPane.showMessageDialog(null, "Indirizzo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
            } 
	// Creazione della nuova mappa
    if (mapViewer != null) {
        mapPanel.remove(mapViewer);
        System.out.println("Mappa precedente rimossa.");
    }


    TileFactoryInfo info = new OSMTileFactoryInfo();
    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
    mapViewer.setTileFactory(tileFactory);

    // Imposta la posizione iniziale e lo zoom
    GeoPosition position = new GeoPosition(lat, lon);
    mapViewer.setAddressLocation(position);
    mapViewer.setZoom(34);
    
    Set<DefaultWaypoint> waypoints = new HashSet<>();
    waypoints.add(new DefaultWaypoint(position));
 // Create a waypoint painter
    WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
    waypointPainter.setWaypoints(waypoints);

    // Set the overlay painter
    mapViewer.setOverlayPainter(waypointPainter);

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

    // Aggiunge la mappa al pannello
    mapViewer.setVisible(true);
    mapPanel.add(mapViewer);
    mapViewer.revalidate(); 
    mapViewer.repaint();
}catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Errore nel recupero della posizione!", "Errore", JOptionPane.ERROR_MESSAGE);
}}
	
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
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
