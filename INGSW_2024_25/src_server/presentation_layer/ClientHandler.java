package presentation_layer;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import business_logic_layer.*;
import classi.*;
import data_access_layer.DatabaseManager;
import data_access_layer.GoogleCloudStorageManager;
import data_access_layer.interfaces.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClientHandler extends Thread { //implements Runnable???
    private Socket socket;
    
    
    private UserService userService;
    private GoogleCloudStorageService storageService;
    private HouseService houseService;
    private ReservationService reservationService;
    private UserRepositoryInterface userRepository;
    private HouseRepositoryInterface houseRepository;
    private ReservationRepositoryInterface reservationRepository;
    private StorageManagerInterface storageManager;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.userRepository = new DatabaseManager();
        this.houseRepository = new DatabaseManager();
        this.reservationRepository = new DatabaseManager();
        this.storageManager = new GoogleCloudStorageManager();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                logger.log(Level.INFO, "Ricevuto: {0}", message);
                JSONObject response = handleRequest(message);
                out.println(response.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Errore durante la comunicazione con il client: ", e);
        }
    }

    private JSONObject handleRequest(String message) {
        JSONObject request = new JSONObject(message);
        String action = request.getString("action");

        
        Map<String, UnaryOperator<JSONObject>> actionHandlers = new HashMap<>();
        actionHandlers.put("login", this::handleLogin);
        actionHandlers.put("register", this::handleRegister);
        actionHandlers.put("updatePassword", this::handleNewPassword);
        actionHandlers.put("uploadFile", this::handleUploadRequest);
        actionHandlers.put("downloadFile", this::handleDownloadRequest);
        actionHandlers.put("makeNewReservation", this::makeNewReservation);
        actionHandlers.put("reservationConfirmed", this::reservationConfirmed);
        actionHandlers.put("reservationDenied", this::reservationDenied);
        actionHandlers.put("getReservation", this::getMailReservation);
        actionHandlers.put("findHouse", this::getHouse);
        actionHandlers.put("findAgente", this::fetchAgente);
        actionHandlers.put("findComposizione", this::fetchComposizione);
        actionHandlers.put("uploadNewHouse", this::uploadNewHouse);
        actionHandlers.put("aggiungiNotifica", this::addNotifica);
        actionHandlers.put("getNotifiche", this::getNotifiche);
        actionHandlers.put("notifica letta", this::setNotifiche);

    
        UnaryOperator<JSONObject> handler = actionHandlers.getOrDefault(action, req -> {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Azione non riconosciuta");
            return errorResponse;
        });

        return handler.apply(request);
    }
    private JSONObject handleLogin(JSONObject request) {
        String mail = request.getString("mail");
        String password = request.getString("password");
        userService = new UserService(userRepository);
        
        User isAuthenticated = userService.authenticateUser(mail, password);

        JSONObject response = new JSONObject();
        response.put("status", isAuthenticated !=null ? "success" : "error");
        response.put("message", isAuthenticated != null ? "Login riuscito" : "Credenziali errate");
        if (isAuthenticated != null) {
            response.put("isAgente", isAuthenticated.getIsAgente());
            response.put("nome",isAuthenticated.getNome());
            response.put("cognome", isAuthenticated.getCognome());
            response.put("telefono", isAuthenticated.getNumeroTelefono());
            response.put("dataNascita", isAuthenticated.getDataNascita());
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
    	userService = new UserService(userRepository);
    	
    	boolean notRegistered = userService.registerUser(nome, cognome, data, mail, telefono, password, isAgente);
    	
    	JSONObject response = new JSONObject();
        response.put("status", notRegistered ? "success" : "error");
        response.put("message", notRegistered ? "Registrazione riuscita" : "Utente esistente");
    	return response;
    }
    
   private JSONObject handleNewPassword(JSONObject request) {
	   
	   String mail = request.getString("mail");
	   String nuovaPassword = request.getString("newPassword");
	   userService = new UserService(userRepository);
	   
	   JSONObject response = new JSONObject();
	   boolean ok = userService.updatePassword(mail, nuovaPassword);
	   response.put("status", ok ? "success" : "error");
       response.put("message", ok ? "pasword aggiornata" : "errore nell'aggiornamento");
       return response;
   }
   
   private JSONObject handleUploadRequest(JSONObject request) {
	    JSONObject response = new JSONObject();
	    try {
	        // Estrai dal JSON il nome del file e i dati in Base64
	        String fileName = request.getString("fileName");
	        String base64Data = request.getString("fileData");
	        storageService = new GoogleCloudStorageService(storageManager);
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
           storageService = new GoogleCloudStorageService(storageManager);
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
   


  
   
   private JSONObject makeNewReservation(JSONObject request) {
	   
	  JSONObject response = new JSONObject();
	  String data = request.getString("data");
	  String ora = request.getString("ora");
	  String cliente = request.getString("mailCliente");
	  String indirizzoImmobile = request.getString("indirizzo");
	  String agente = request.getString("mailAgente");
	  reservationService = new ReservationService(reservationRepository);
	  boolean firstReservation = reservationService.newReservation(data, ora, cliente, indirizzoImmobile, agente);
	  if (firstReservation) {
		  response.put("status", "success");
		  response.put("message", "Prenotazione riuscita");
		  response.put("id", reservationService.retrieveId(cliente, agente, data, ora, indirizzoImmobile) );
		  
	  } else {
		  response.put("status", "error");
		  response.put("message", "Prenotazione già effettuata o già impegnato");
	  }
	  
	  return response;
   }
   
   private JSONObject reservationConfirmed(JSONObject request) {
	   JSONObject response = new JSONObject();
	 
	   int id = request.getInt("id");
	   String mail = request.getString("mail");
	   String data = request.getString("data");
	   String ora = request.getString("ora");
	   reservationService = new ReservationService(reservationRepository);
	   boolean noProbelmInReservation = reservationService.acceptReservation(id, mail, data, ora);
	   response.put("status", noProbelmInReservation ? "success" : "error");
	   response.put("message", noProbelmInReservation ? "Prenotazione Confermata" : "Sei già impegnato");
	   return response;
	  
   }
   
   private JSONObject reservationDenied(JSONObject request) {
	   JSONObject response = new JSONObject();
	   
	   try {
		   int id = request.getInt("id");
		   reservationService = new ReservationService(reservationRepository);
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
		   reservationService = new ReservationService(reservationRepository);
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
	           jsonPrenotazione.put("Agente", p.getAgente().getNome()+" "+p.getAgente().getCognome());
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
	   houseService = new HouseService(houseRepository);
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
		   userService = new UserService(userRepository);
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
		   houseService = new HouseService(houseRepository);
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
   
   public JSONObject uploadNewHouse(JSONObject request) {
	   JSONObject response = new JSONObject();
	   
	   int quadratura = request.getInt("quadratura");
	   int stanze = request.getInt("stanze"); 
	   int piani = request.getInt("piani"); 
	   boolean giardino = request.getBoolean("giardino");
	   boolean condominio = request.getBoolean("condominio");
	   boolean ascensore = request.getBoolean("ascensore"); 
	   boolean terrazzo = request.getBoolean("terrazzo");
	   double prezzo = request.getInt("prezzo");
	   String indirizzo = request.getString("indirizzo");
	   String annuncio = request.getString("annuncio");
	   String tipo = request.getString("tipo");
	   String classeEnergetica = request.getString("classeEnergetica");
	   String descrizione = request.getString("descrizione");
	   String urls = request.getString("urls");
       String agente = request.getString("agente");
       ComposizioneImmobile composizione = new ComposizioneImmobile(0,quadratura, piani, stanze, terrazzo, giardino, ascensore, condominio);
       Immobile immobile = new Immobile (prezzo, composizione, indirizzo, annuncio, tipo, classeEnergetica, descrizione, urls, null);
       houseService = new HouseService(houseRepository);
       boolean caricato = houseService.uploadHouse(immobile, agente);
       response.put("status", caricato ? "success" : "error");
	   response.put("message", caricato ? "Immobile caricato!" : "Già presente nel db");
	   return response;
	  
   	}
   
   public JSONObject addNotifica(JSONObject request) {
	   String destinatario = request.getString("destinatario");
	   String messaggio = request.getString("messaggio");
	    // Chiamata al servizio di Business Logic per salvare la notifica
	   reservationService = new ReservationService(reservationRepository);
	   boolean ok = reservationService.aggiungiNotifica(destinatario, messaggio);
	   JSONObject response = new JSONObject();
	   response.put("status", ok ? "success" : "error");
	   return response;
   }
   
   public JSONObject getNotifiche(JSONObject request) {
	   String mail = request.getString("mail");
	   reservationService = new ReservationService(reservationRepository);
	    List<Notifica> notifiche = reservationService.getNotifiche(mail);
	   
	    JSONArray notificheArray = new JSONArray();
	    for (Notifica notifica : notifiche) {
	        JSONObject obj = new JSONObject();
	        obj.put("id", notifica.getId());
	        obj.put("destinatario", notifica.getDestinatarioEmail());
	        obj.put("messaggio", notifica.getMessaggio());
	        obj.put("letta", notifica.isLetta());
	        notificheArray.put(obj);
	    }
	    JSONObject response = new JSONObject();
	    response.put("status", "success");
	    response.put("notifiche", notificheArray);
	    return response;
   }
   
   public JSONObject setNotifiche(JSONObject request) {
	  
	   int id = request.getInt("id");
	   reservationService = new ReservationService(reservationRepository);
	   boolean confermato = reservationService.setLetta(id);
	   JSONObject response = new JSONObject();
	   response.put("status", confermato ? "success" : "error");
	   response.put("message", confermato ? "notifica cancellata" : "errore");
	   return response;
	   
   }
}

