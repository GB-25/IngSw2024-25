package model;


import java.io.*;
import java.net.Socket;
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
    
    
    public JSONObject loginModel(String username, String password) {
    	JSONObject request = new JSONObject();
        request.put("action", "login");
        request.put("username", username);
        request.put("password", password);
        return sendRequest(request);
    }
    
    
 
}
