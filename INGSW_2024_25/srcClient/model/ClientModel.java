package model;


import java.io.*;
import java.net.Socket;
import java.util.Date;

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
 
}
