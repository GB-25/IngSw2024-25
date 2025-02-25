package model;


import java.io.*;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;

import org.json.JSONObject;

import Class.ComposizioneImmobile;
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
    
    public int uploadComposition(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
    	JSONObject request = new JSONObject();
    	request.put("action", "uploadComposition");
    	request.put("quadratura", quadratura);
    	request.put("stanze", stanze);
    	request.put("piani", piani);
    	request.put("giardino", giardino);
    	request.put("condominio", condominio);
    	request.put("ascensore", ascensore);
    	request.put("terrazzo", terrazzo);
    	int id;
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		id=0;
    	} else {
    		id = response.getInt("id");
    	}
    	return id;
    }
    
    public boolean uploadHouseModel(double prezzo, int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, String descrizione,
            String urls, String agente) {
    	JSONObject request = new JSONObject();
    	request.put("action", "uploadHouse");
    	request.put("prezzo", prezzo);
    	request.put("idComposizione", idComposizioneImmobile);
    	request.put("indirizzo", indirizzo);
    	request.put("annuncio", annuncio);
    	request.put("tipo", tipo);
    	request.put("classeEnergetica", classeEnergetica);
    	request.put("descrizione", descrizione);
    	request.put("urls", urls);
    	request.put("agente", agente);
    	boolean uploaded;
    	JSONObject response = sendRequest(request);
    	if (response.getString("status").equals("error")) {
    		uploaded = false;
    	} else {
    		uploaded = true;
    	}
    	return uploaded;
    }
    
    public JSONObject getReservation(String mail, boolean isConfirmed, boolean isAgente, String data) {
    	JSONObject request = new JSONObject();
    	request.put("action", "getReservation");
    	request.put("mail", mail);
    	request.put("isConfirmed", isConfirmed);
    	request.put("isAgente", isAgente);
    	request.put("data", data);
    	return sendRequest(request);
    }
    
    
    public JSONObject makeReservation(String data, String ora, String mailCliente, String indirizzo, String mailAgente) {
    	JSONObject request = new JSONObject();
    	request.put("action", "makeNewReservation");
    	request.put("data", data);
    	request.put("ora", ora);
    	request.put("mailCliente", mailCliente);
    	request.put("indirizzo", indirizzo);
    	request.put("mailAgente", mailAgente);
    	
    	return sendRequest(request);
    }
    
    public JSONObject confirmReservation(int id, String mail, String data, String ora) {
    	JSONObject request = new JSONObject();
    	request.put("action", "reservationConfirmed");
    	request.put("id", id);
    	request.put("mail", mail);
    	request.put("data", data);
    	request.put("ora", ora);
    	return sendRequest(request);
    }
    
    public JSONObject denyReservation(int id) {
    	JSONObject request = new JSONObject();
    	request.put("action", "reservationDenied");
    	request.put("id", id);
    	
    	return sendRequest(request);
    }
    
    public JSONObject searchHouse(String query) {
    	JSONObject request = new JSONObject();
    	request.put("action", "findHouse");
    	request.put("query", query);
    	
    	return sendRequest(request);
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
    	
    	int id = request.getInt("idComposizione");
    	int quadratura = request.getInt("quadratura");
    	int stanze = request.getInt("stanze"); 
		int piani = request.getInt("piani"); 
		boolean giardino = request.getBoolean("giardino");
		boolean condominio = request.getBoolean("condominio");
		boolean ascensore = request.getBoolean("ascensore"); 
		boolean terrazzo = request.getBoolean("terrazzo");
		
		return new ComposizioneImmobile(id, quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
    	
    	
    }
}
