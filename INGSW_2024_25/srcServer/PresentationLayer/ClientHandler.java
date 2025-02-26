package PresentationLayer;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;
import Class.*;

import BusinessLogicLayer.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClientHandler extends Thread { //implements Runnable???
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService;
    private GoogleCloudStorageService storageService;
    private HouseService houseService;
    private ReservationService reservationService;

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
                	case "uploadHouse":
                		response = handleUploadHouse(request);
                		break;
                	case "makeNewReservation":
                		response = makeNewReservation(request);
                		break;
                	case "reservationConfirmed":
                		response = reservationConfirmed(request);
                		break;
                	case "reservationDenied":
                		response = reservationDenied(request);
                		break;
                	case "getReservation":
                		response = getMailReservation(request);
                		break;
                	case "findHouse":
                		response = getHouse(request);
                		break;
                	case "findAgente":
                		response = fetchAgente(request);
                		break;
                	case "findComposzione":
                		response = fetchComposizione(request);
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
	        storageService = new GoogleCloudStorageService();
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
           storageService = new GoogleCloudStorageService();
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
		   houseService = new HouseService();
		   int id = houseService.uploadComposizioneImmobile(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		   response.put("status", "success");
           response.put("id", id);
	   } catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il caricamento: " + e.getMessage());
       }
	   return response;
   }

   private JSONObject handleUploadHouse (JSONObject request) {
	   
	   double prezzo = request.getInt("prezzo");
	   int idComposizioneImmobile = request.getInt("idComposizione");
	   String indirizzo = request.getString("indirizzo");
	   String annuncio = request.getString("annuncio");
	   String tipo = request.getString("tipo");
	   String classeEnergetica = request.getString("classeEnergetica");
	   String descrizione = request.getString("descrizione");
       String urls = request.getString("urls");
       String agente = request.getString("agente");
       houseService = new HouseService();
       boolean uploaded = houseService.uploadNewHouse(prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classeEnergetica,
    		   descrizione, urls, agente);
       JSONObject response = new JSONObject();
       response.put("status", uploaded ? "success" : "error");
       response.put("message", uploaded ? "Caricamento riuscito" : "Immobile già presente");
	   return response;
	   }
   
   private JSONObject makeNewReservation(JSONObject request) {
	   
	  JSONObject response = new JSONObject();
	  String data = request.getString("data");
	  String ora = request.getString("ora");
	  String cliente = request.getString("mailCliente");
	  String indirizzoImmobile = request.getString("indirizzo");
	  String agente = request.getString("mailAgente");
	  reservationService = new ReservationService();
	  boolean firstReservation = reservationService.newReservation(data, ora, cliente, indirizzoImmobile, agente);
	  response.put("status", firstReservation ? "success" : "error");
	  response.put("message", firstReservation ? "Prenotazione riuscita" : "Prenotazione già effettuata o già impegnato");
	  return response;
   }
   
   private JSONObject reservationConfirmed(JSONObject request) {
	   JSONObject response = new JSONObject();
	 
	   int id = request.getInt("id");
	   String mail = request.getString("mail");
	   String data = request.getString("data");
	   String ora = request.getString("ora");
	   reservationService = new ReservationService();
	   boolean noProbelmInReservation = reservationService.acceptReservation(id, mail, data, ora);
	   response.put("status", noProbelmInReservation ? "success" : "error");
	   response.put("message", noProbelmInReservation ? "Prenotazione Confermata" : "Sei già impegnato");
	   return response;
	  
   }
   
   private JSONObject reservationDenied(JSONObject request) {
	   JSONObject response = new JSONObject();
	   
	   try {
		   int id = request.getInt("id");
		   reservationService = new ReservationService();
		   reservationService.refusedReservation(id);
		   response.put("status", "success");
	   } catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante la cancellazione: " + e.getMessage());
       }
	   return response;
   }
   

   
   private JSONObject getMailReservation(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   String mail = request.getString("mail");
		   boolean isConfirmed = request.getBoolean("isConfirmed");
		   boolean isAgente = request.getBoolean("isAgente");
		   String data = request.getString("data");
		   reservationService = new ReservationService();
		   List<Prenotazione> lista = reservationService.getReservation(mail, isConfirmed, data, isAgente);
		   response.put("status", "success");
		   JSONArray jsonArray = new JSONArray();
		   for (Prenotazione p : lista) {
			   JSONObject jsonPrenotazione = new JSONObject();
	           jsonPrenotazione.put("id", p.getId());
	           jsonPrenotazione.put("data", p.getDataPrenotazione());
	           jsonPrenotazione.put("ora", p.getOraPrenotazione());
	           jsonPrenotazione.put("Cliente", p.getUser().getNome()+" "+p.getUser().getCognome());
	           jsonPrenotazione.put("indirizzo", p.getImmobile().getIndirizzo());
	           jsonPrenotazione.put("Agente", p.getAgente().getNome()+" "+p.getAgente().getCognome());;
	           jsonPrenotazione.put("confermato", p.isConfirmed());
	           jsonArray.put(jsonPrenotazione);
		   }
		   
	       response.put("prenotazioni", jsonArray); 
	   }catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il recupero delle prenotazioni: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject getHouse(JSONObject request) {
	   JSONObject response = new JSONObject();
	   String query = request.getString("query");
	   houseService = new HouseService();
	   try {
		   List<Immobile> lista = houseService.retrieveHouse(query);
		   response.put("status", "success");
		   JSONArray jsonArray = new JSONArray();
		   for(Immobile i : lista) {
			   JSONObject jsonImmobile = new JSONObject();
			   jsonImmobile.put("indirizzo", i.getIndirizzo());
			   jsonImmobile.put("composizione", i.getComposizione().getId());
			   jsonImmobile.put("agente", i.getAgente().getMail());
			   jsonImmobile.put("prezzo", i.getPrezzo());
			   jsonImmobile.put("annuncio", i.getAnnuncio());
			   jsonImmobile.put("tipo", i.getTipo());
			   jsonImmobile.put("descrizione", i.getDescrizione());
			   jsonImmobile.put("classe", i.getClasseEnergetica());
			   jsonImmobile.put("urls", i.getUrls());
			   jsonArray.put(jsonImmobile);
		   }
		   response.put("immobili", jsonArray); 
	   }catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il recupero degli immobili: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject fetchAgente(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   String mail = request.getString("mail");
		   userService = new UserService();
		   User user = userService.getUser(mail);
		   response.put("status", "success");
		   response.put("mail", user.getMail());
		   response.put("password", user.getPassword());
		   response.put("nome", user.getNome());
		   response.put("cognome", user.getCognome());
		   response.put("telefono", user.getNumeroTelefono());
		   response.put("dataNascita", user.getDataNascita());
		   response.put("isAgente", user.getIsAgente());
		  
	   } catch (Exception e) {
           response.put("status", "error");
           response.put("message", "Errore durante il recupero degli agenti: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject fetchComposizione(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   int id = request.getInt("idComposizione");
		   houseService = new HouseService();
		   ComposizioneImmobile composizione = houseService.getComposizione(id);
		   response.put("status", "success");
		   response.put("idComposizione", id);
		   response.put("quadratura",composizione.getQuadratura());
		   response.put("piani",composizione.getPiani());
		   response.put("stanze",composizione.getNumeroStanze());
		   response.put("terrazzo",composizione.isTerrazzo());
		   response.put("giardino",composizione.isGiardino());
		   response.put("ascensore",composizione.isAscensore());
		   response.put("condominio",composizione.isCondominio());
	   }catch (Exception e) {
		   response.put("status", "error");
		   response.put("message", "Errore durante il recupero delle composizioni degli immobili: " + e.getMessage());
	   }
	   return response;
   }
}

