package PresentationLayer;

import java.io.*;
import java.net.*;
import java.util.Date;

import BusinessLogicLayer.*;
import org.json.JSONObject;

public class ClientHandler extends Thread { //implements Runnable???
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService;
    private GoogleCloudStorageService storageService;
    private HouseService houseService;

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
                	case "updatePassword":
                		this.handleNewPassword(request);
                		break;
                	case "uploadFile":  
                        response = handleUploadRequest(request);
                        break;
                	case "downloadFile":
                		response = handleDownloadRequest(request);
                	case "uploadComposition":
                		response = handleUploadComposition(request);
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
        String nome="";
        String cognome="";
        String telefono="";
        String dataNascita = "";
        userService = new UserService();
        
        boolean isAuthenticated = userService.authenticateUser(mail, password, agente, nome, cognome, telefono, dataNascita);

        JSONObject response = new JSONObject();
        response.put("status", isAuthenticated ? "success" : "error");
        response.put("message", isAuthenticated ? "Login riuscito" : "Credenziali errate");
        if (isAuthenticated) {
            response.put("isAgente", agente);
            response.put("nome",nome);
            response.put("cognome", cognome);
            response.put("telefono", telefono);
            response.put("dataNascita", dataNascita);
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
    	Boolean isAgente = request.getBoolean("isAgente");
    	userService = new UserService();
    	
    	boolean notRegistered = userService.registerUser(nome, cognome, data, mail, telefono, password, isAgente);
    	
    	JSONObject response = new JSONObject();
        response.put("status", notRegistered ? "success" : "error");
        response.put("message", notRegistered ? "Registrazione riuscita" : "Utente esistente");
    	return response;
    }
    
   private void handleNewPassword(JSONObject request) {
	   
	   String mail = request.getString("mail");
	   String nuovaPassword = request.getString("newPassword");
	   userService = new UserService();
	   
	   userService.updatePassword(mail, nuovaPassword);
   }
   
   private JSONObject handleUploadRequest(JSONObject request) {
	    JSONObject response = new JSONObject();
	    try {
	        // Estrai dal JSON il nome del file e i dati in Base64
	        String fileName = request.getString("fileName");
	        String base64Data = request.getString("fileData");
	        
	        // Carica l'immagine su Cloud Storage tramite il service
	        String fileUrl = storageService.uploadHouseImage(fileName, base64Data);
	        
	        response.put("status", "success");
	        response.put("fileUrl", fileUrl);
	    } catch (IOException e) {
	        response.put("status", "error");
	        response.put("message", "Errore durante l'upload: " + e.getMessage());
	    }
	    return response;
	}
   
   private JSONObject handleDownloadRequest(JSONObject request) {
       JSONObject response = new JSONObject();
       try {
           // Estrai il nome del file dal JSON
           String fileName = request.getString("fileName");
           // Richiama il service per scaricare l'immagine in Base64
           String base64Image = storageService.downloadHouseImage(fileName);
           response.put("status", "success");
           response.put("fileData", base64Image);
       } catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il download: " + e.getMessage());
       }
       return response;
   }
   
   private JSONObject handleUploadComposition(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   int quadratura = request.getInt("quadratura");
		   int stanze = request.getInt("stanze"); 
		   int piani = request.getInt("piani"); 
		   boolean giardino = request.getBoolean("giardino");
		   boolean condominio = request.getBoolean("condominio");
		   boolean ascensore = request.getBoolean("ascensore"); 
		   boolean terrazzo = request.getBoolean("terrazzo");
		   int id = houseService.uploadComposizioneImmobile(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		   response.put("status", "success");
           response.put("id", id);
	   } catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il caricamento: " + e.getMessage());
       }
	   return response;
   }


}