package PresentationLayer;

import java.io.*;
import java.net.*;
import BusinessLogicLayer.UserService;
import org.json.JSONObject;

public class ClientHandler extends Thread { //implements Runnable???
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
            	System.out.println("Ricevuto: " + message);

            	// Parsing del JSON ricevuto
            	JSONObject request = new JSONObject(message);
            	String action = request.getString("action");

            	// Creazione della risposta JSON
            	JSONObject response = new JSONObject();

            	switch (action) {
                	case "login":
                		response = handleLogin(request);
                		break;
                	case "register":
                		response = handleRegister(request);
                		break;
                	case "addProperty":
                		//response = handleAddProperty(request);
                		break;
                	case "bookProperty":
                		//response = handleBookProperty(request);
                		break;
                	default:
                		response.put("status", "error");
                		response.put("message", "Azione non riconosciuta");
                		break;
            		}

            		// Invia la risposta al client
            		out.println(response.toString());
            	}
            } catch (IOException e) {
        e.printStackTrace();
        }
    }
    
    private JSONObject handleLogin(JSONObject request) {
        String mail = request.getString("mail");
        String password = request.getString("password");
        boolean agente=false;
        userService = new UserService();
        
        boolean isAuthenticated = userService.authenticateUser(mail, password, agente);

        JSONObject response = new JSONObject();
        response.put("status", isAuthenticated ? "success" : "error");
        response.put("message", isAuthenticated ? "Login riuscito" : "Credenziali errate");
        if (isAuthenticated) {
            response.put("isAdmin", agente); // Aggiungi l'attributo booleano
        }
        return response;
    }
    
    private JSONObject handleRegister(JSONObject request) {
    	String nome = request.getString("name");
    	String cognome = request.getString("surname");
    	String data = request.getString("birthdate");
    	String mail = request.getString("mail");
    	String telefono = request.getString("cellphone");
    	String password = request.getString("password");
    	userService = new UserService();
    	
    	boolean notRegistered = userService.registerUser(nome, cognome, data, mail, telefono, password);
    	
    	JSONObject response = new JSONObject();
        response.put("status", notRegistered ? "success" : "error");
        response.put("message", notRegistered ? "Registrazione riuscita" : "Utente esistente");
    	return response;
    }
}