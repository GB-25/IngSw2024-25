package model;


import java.io.*;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import Class.ComposizioneImmobile;
import Class.Immobile;
import Class.User;

public class ClientModel {
    private String serverIP; //34.78.163.251
    private int port;

    public ClientModel(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
    }

    
    public JSONObject sendRequest(JSONObject request) {
        try (Socket socket = new Socket(serverIP, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Invia richiesta al server
            out.println(request.toString());

            // Riceve la risposta dal server
            String response = in.readLine();
            return new JSONObject(response);

        } catch (IOException e) {
            e.printStackTrace();
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Connessione al server fallita");
            return errorResponse;
        }
    }
    
    
    public User loginModel(String mail, String password) {
    	JSONObject request = new JSONObject();
        request.put("action", "login");
        request.put("mail", mail);
        request.put("password", password);
        User user;
        JSONObject response = sendRequest(request);
        if (response.getString("status").equals("success")) {
        	String nome = response.getString("nome");
        	String cognome = response.getString("cognome");
        	String dataNascita = response.getString("dataNascita");
        	String telefono = response.getString("telefono");
        	boolean isAgente = response.getBoolean("isAgente");
		
        	user = new User(mail, password, nome, cognome, telefono, dataNascita, isAgente);
        } else {
        	user = null;
        }
        return user;
    }
    
    public User registerModel (String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	JSONObject request = new JSONObject();
    	User user;
    	request.put("action", "register");
    	request.put("name", nome);
    	request.put("surname", cognome);
    	request.put("birthdate", data);
    	request.put("mail", mail);
    	request.put("cellphone", telefono);
    	request.put("password", password);
    	request.put("isAgente", isAgente);
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("success")) {
    		user = new User(mail, password, nome, cognome, telefono, data, isAgente );
    	} else {
    		user = null;
    	}
    	return user;
    }
    
    public void newPasswordModel(String mail, String nuovaPassword) {
    	JSONObject request = new JSONObject();
    	request.put("action", "updatePassword");
    	request.put("mail", mail);
    	request.put("newPassword", nuovaPassword);
    	sendRequest(request);
    	
    }
 
    public String uploadFileModel(String filePath) {
        JSONObject request = new JSONObject();
        request.put("action", "uploadFile");
        String fileUrl = null;
        try {
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String base64Data = Base64.getEncoder().encodeToString(fileBytes);
            
            // Inviamo sia il nome del file che i dati in Base64
            request.put("fileName", path.getFileName().toString());
            request.put("fileData", base64Data);
            JSONObject response = sendRequest(request);
            if (response.getString("status").equals("success")) {
            	fileUrl = response.getString("fileUrl"); 
            } else {
            	System.err.println("Errore durante l'upload: " + response.getString("message"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Puoi decidere come gestire l'errore (ad es. inserendo un campo "error" nel JSON)
        }
        return fileUrl;
    }
    
    public String downloadFileModel(String fileName) {
        JSONObject request = new JSONObject();
        request.put("action", "downloadFile");
        request.put("fileName", fileName);
        String fileData = null;
       
        JSONObject response = sendRequest(request);
        if (response.getString("status").equals("success")) {
        	fileData = response.getString("fileData");
        } else {
            System.err.println("Errore durante il download: " + response.getString("message"));
        }
        return fileData;
    }
    
   
    public ArrayList<String> getReservation(String mail, boolean isConfirmed, String data, boolean isAgente) {
    	JSONObject request = new JSONObject();
    	request.put("action", "getReservation");
    	request.put("mail", mail);
    	request.put("isConfirmed", isConfirmed);
    	request.put("data", data);
    	request.put("isAgente", isAgente);
    	JSONObject response = sendRequest(request);
    	ArrayList<String> prenotazioni = new ArrayList<String>(); 
    	if (response.getString("status").equals("success")) {
    		JSONArray jsonArray = response.getJSONArray("prenotazioni");
			for (int i = 0; i < jsonArray.length(); i++) {
				StringBuilder sb = new StringBuilder();
			    JSONObject jsonObject = jsonArray.getJSONObject(i);
			    int id = jsonObject.getInt("id");
			    String indirizzo = jsonObject.getString("indirizzo");
			    String ora = jsonObject.getString("ora");
			    if(isAgente) {
			    	String cliente = jsonObject.getString("Cliente");
			    	sb.append("prenotazione "+id+", Sig/ra "+cliente+", "+indirizzo+", alle ore "+ora);
			    } else {
			    	String agente = jsonObject.getString("Agente");
			    	sb.append("prenotazione "+id+", Agente "+agente+", "+indirizzo+", alle ore "+ora);
			    }
			    String prenotazione = sb.toString();
			    prenotazioni.add(prenotazione);
			}
    	} else {
    		prenotazioni = null;
    	}
    	return prenotazioni;
    }
    
    
    public boolean makeReservation(String data, String ora, String mailCliente, String indirizzo, String mailAgente, int id) {
    	JSONObject request = new JSONObject();
    	request.put("action", "makeNewReservation");
    	request.put("data", data);
    	request.put("ora", ora);
    	request.put("mailCliente", mailCliente);
    	request.put("indirizzo", indirizzo);
    	request.put("mailAgente", mailAgente);
    	
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		return false;
    	} else {
    		id = response.getInt("id");
    		return true;
    	}
    }
    
    public boolean confirmReservation(int id, String mail, String data, String ora) {
    	JSONObject request = new JSONObject();
    	request.put("action", "reservationConfirmed");
    	request.put("id", id);
    	request.put("mail", mail);
    	request.put("data", data);
    	request.put("ora", ora);
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public boolean denyReservation(int id) {
    	JSONObject request = new JSONObject();
    	request.put("action", "reservationDenied");
    	request.put("id", id);
    	
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public ArrayList<Immobile> searchHouse(String query) {
    	JSONObject request = new JSONObject();
    	request.put("action", "findHouse");
    	request.put("query", query);
    	
    	JSONObject response = sendRequest(request);
    	
    	ArrayList<Immobile> immobili = new ArrayList<Immobile>();
		Immobile casa;
		if (response.getString("status").equals("success")) {
			
			JSONArray jsonArray = response.getJSONArray("immobili");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String indirizzo = jsonObject.getString("indirizzo");
				int idComposizione = jsonObject.getInt("composizione");
				String mailAgente = jsonObject.getString("agente");
				double prezzo = jsonObject.getDouble("prezzo");
				String tipoAnnuncio = jsonObject.getString("annuncio");
				String tipo = jsonObject.getString("tipo");
				String descrizione = jsonObject.getString("descrizione");
				String classe = jsonObject.getString("classe");
			   	String urls = jsonObject.getString("urls");
			   	User agente = this.getAgente(mailAgente);
			   	ComposizioneImmobile composizione = this.getComposizione(idComposizione);
			   	casa = new Immobile(prezzo, composizione,indirizzo, tipoAnnuncio, tipo,
			   			classe, descrizione, urls, agente);
			   	immobili.add(casa);
			   	//da capire come visualizzare le foto, però immagino sarà a parte dalla gui
			}
		} else {
			immobili = null;
		}
		return immobili;
    }
    
    public User getAgente(String mailAgente) {
    	JSONObject request = new JSONObject();
    	request.put("action", "findAgente");
    	request.put("mail", mailAgente);
    	
    	JSONObject response = sendRequest(request);
    	
    	String mail = response.getString("mail");
    	String password = response.getString("password");
    	String nome = response.getString("nome");
		String cognome = response.getString("cognome");
		String dataNascita = response.getString("dataNascita");
		String telefono = response.getString("telefono");
		Boolean isAgente = response.getBoolean("isAgente");
		
		return new User(mail, password, nome, cognome, telefono, dataNascita, isAgente);
    }
    
    public ComposizioneImmobile getComposizione(int idComposizione) {
    	JSONObject request = new JSONObject();
    	request.put("action", "findComposzione");
    	request.put("id", idComposizione);
    	
    	JSONObject response = sendRequest(request);
    	
    	int id = response.getInt("idComposizione");
    	int quadratura = response.getInt("quadratura");
    	int stanze = response.getInt("stanze"); 
		int piani = response.getInt("piani"); 
		boolean giardino = response.getBoolean("giardino");
		boolean condominio = response.getBoolean("condominio");
		boolean ascensore = response.getBoolean("ascensore"); 
		boolean terrazzo = response.getBoolean("terrazzo");
		
		return new ComposizioneImmobile(id, quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
    	
    	
    }
    
    public boolean uploadNewHouseModel(Immobile immobile) {
    	ComposizioneImmobile composizione = immobile.getComposizione();
    	JSONObject request = new JSONObject();
    	request.put("action", "uploadNewHouse");
    	request.put("quadratura", composizione.getQuadratura());
    	request.put("stanze", composizione.getNumeroStanze());
    	request.put("piani", composizione.getPiani());
    	request.put("giardino", composizione.isGiardino());
    	request.put("condominio", composizione.isCondominio());
    	request.put("ascensore", composizione.isAscensore());
    	request.put("terrazzo", composizione.isTerrazzo());
    	request.put("prezzo", immobile.getPrezzo());
    	request.put("indirizzo", immobile.getIndirizzo());
    	request.put("annuncio", immobile.getAnnuncio());
    	request.put("tipo", immobile.getTipo());
    	request.put("classeEnergetica", immobile.getClasseEnergetica());
    	request.put("descrizione", immobile.getDescrizione());
    	request.put("urls", immobile.getUrls());
    	request.put("agente", immobile.getAgente().getMail());
    	
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		return false;
    	} else {
    		return true;
    	}
    }
}
