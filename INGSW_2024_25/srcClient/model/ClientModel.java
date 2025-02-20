package model;


import java.io.*;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;

import org.json.JSONObject;

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
    
    
    public JSONObject loginModel(String mail, String password) {
    	JSONObject request = new JSONObject();
        request.put("action", "login");
        request.put("mail", mail);
        request.put("password", password);
        return sendRequest(request);
    }
    
    public JSONObject registerModel (String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	JSONObject request = new JSONObject();
    	request.put("action", "register");
    	request.put("name", nome);
    	request.put("surname", cognome);
    	request.put("birthdate", data);
    	request.put("mail", mail);
    	request.put("cellphone", telefono);
    	request.put("password", password);
    	request.put("isAgente", isAgente);
    	return sendRequest(request);
    }
    
    public void newPasswordModel(String mail, String nuovaPassword) {
    	JSONObject request = new JSONObject();
    	request.put("action", "updatePassword");
    	request.put("mail", mail);
    	request.put("newPassword", nuovaPassword);
    	sendRequest(request);
    	
    }
 
    public JSONObject uploadFileModel(String filePath) {
        JSONObject request = new JSONObject();
        request.put("action", "uploadFile");
        
        try {
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String base64Data = Base64.getEncoder().encodeToString(fileBytes);
            
            // Inviamo sia il nome del file che i dati in Base64
            request.put("fileName", path.getFileName().toString());
            request.put("fileData", base64Data);
        } catch (IOException e) {
            e.printStackTrace();
            // Puoi decidere come gestire l'errore (ad es. inserendo un campo "error" nel JSON)
        }
        return sendRequest(request);
    }
    
    public JSONObject downloadFileModel(String fileName) {
        JSONObject request = new JSONObject();
        request.put("action", "downloadFile");
        request.put("fileName", fileName);
        
        // Invio della richiesta al server.
       
        return sendRequest(request);
    }
    
    public JSONObject uploadComposition(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
    	JSONObject request = new JSONObject();
    	request.put("action", "uploadComposition");
    	request.put("quadratura", quadratura);
    	request.put("stanze", stanze);
    	request.put("piani", piani);
    	request.put("giardino", giardino);
    	request.put("condominio", condominio);
    	request.put("ascensore", ascensore);
    	request.put("terrazzo", terrazzo);
    	
    	return sendRequest(request);
    }
    
    public JSONObject uploadHouseModel(double prezzo, int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, String descrizione,
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
    	
    	return sendRequest(request);
    }
}
