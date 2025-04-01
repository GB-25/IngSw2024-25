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

public class ClientHandler extends Thread { 
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
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String ISAGENTE = "isAgente";
    private static final String PASSWORD = "password";
    private static final String INDIRIZZO = "indirizzo";
    private static final String IMMOBILEID = "idImmobile";
    /**
     * 
     * Costruttore
     * @throws IOException
     */
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
	/**
	 * Metodo per gestire le richieste del client, chiama dei metodi specializzati in un
	 * tipo di richiesta
	 * @param message
	 * @return JSONObject da mandare al client
	 */
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
        actionHandlers.put("notificaLetta", this::setNotifiche);
        actionHandlers.put("updateUrls", this:: setUrls);
        actionHandlers.put("retrievePrenotazione", this::getPrenotazione);

    
        UnaryOperator<JSONObject> handler = actionHandlers.getOrDefault(action, req -> {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put(STATUS, ERROR);
            errorResponse.put(MESSAGE, "Azione non riconosciuta");
            return errorResponse;
        });

        return handler.apply(request);
    }
    private JSONObject handleLogin(JSONObject request) {
        String mail = request.getString("mail");
        String password = request.getString(PASSWORD);
        userService = new UserService(userRepository);
        
        User isAuthenticated = userService.authenticateUser(mail, password);

        JSONObject response = new JSONObject();
        response.put(STATUS, isAuthenticated !=null ? SUCCESS : ERROR);
        response.put(MESSAGE, isAuthenticated != null ? "Login riuscito" : "Credenziali errate");
        if (isAuthenticated != null) {
            response.put(ISAGENTE, isAuthenticated.getIsAgente());
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
    	String password = request.getString(PASSWORD);
    	Boolean isAgente = request.getBoolean(ISAGENTE);
    	userService = new UserService(userRepository);
    	
    	boolean notRegistered = userService.registerUser(nome, cognome, data, mail, telefono, password, isAgente);
    	
    	JSONObject response = new JSONObject();
        response.put(STATUS, notRegistered ? SUCCESS : ERROR);
        response.put(MESSAGE, notRegistered ? "Registrazione riuscita" : "Utente esistente");
    	return response;
    }
    
   private JSONObject handleNewPassword(JSONObject request) {
	   
	   String mail = request.getString("mail");
	   String nuovaPassword = request.getString("newPassword");
	   userService = new UserService(userRepository);
	   
	   JSONObject response = new JSONObject();
	   boolean ok = userService.updatePassword(mail, nuovaPassword);
	   response.put(STATUS, ok ? SUCCESS : ERROR);
       response.put(MESSAGE, ok ? "pasword aggiornata" : "errore nell'aggiornamento");
       return response;
   }
   
   private JSONObject handleUploadRequest(JSONObject request) {
	    JSONObject response = new JSONObject();
	    try {
	        String fileName = request.getString("fileName");
	        String base64Data = request.getString("fileData");
	        int idCartella = request.getInt("id");
	        storageService = new GoogleCloudStorageService(storageManager);
	        String fileUrl = storageService.uploadHouseImage(fileName, base64Data, idCartella);
	        
	        response.put(STATUS, SUCCESS);
	        response.put("fileUrl", fileUrl);
	    } catch (IOException e) {
	        response.put(STATUS, ERROR);
	        response.put(MESSAGE, "Errore durante l'upload: " + e.getMessage());
	    }
	    return response;
	}
   
   private JSONObject handleDownloadRequest(JSONObject request) {
       JSONObject response = new JSONObject();
       try {

           String fileName = request.getString("fileName");
           storageService = new GoogleCloudStorageService(storageManager);

           String base64Image = storageService.downloadHouseImage(fileName);
           response.put(STATUS, SUCCESS);
           response.put("fileData", base64Image);
       } catch (Exception e) {
           response.put(STATUS, ERROR);
           response.put(MESSAGE, "Errore durante il download: " + e.getMessage());
       }
       return response;
   }
   


  
   
   private JSONObject makeNewReservation(JSONObject request) {
	   
	  JSONObject response = new JSONObject();
	  String data = request.getString("data");
	  String ora = request.getString("ora");
	  String cliente = request.getString("mailCliente");
	  int idImmobile = request.getInt(IMMOBILEID);
	  String agente = request.getString("mailAgente");
	  reservationService = new ReservationService(reservationRepository);
	  boolean firstReservation = reservationService.newReservation(data, ora, cliente, idImmobile, agente);
	  if (firstReservation) {
		  response.put(STATUS, SUCCESS);
		  response.put(MESSAGE, "Prenotazione riuscita");
		  response.put("id", reservationService.retrieveId(cliente, agente, data, ora, idImmobile) );
		  
	  } else {
		  response.put(STATUS, ERROR);
		  response.put(MESSAGE, "Risulta precedente prenotazione");
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
	   response.put(STATUS, noProbelmInReservation ? SUCCESS : ERROR);
	   response.put(MESSAGE, noProbelmInReservation ? "Prenotazione Confermata" : "Presente una precedente Prenotazione");
	   return response;
	  
   }
   
   private JSONObject reservationDenied(JSONObject request) {
	   JSONObject response = new JSONObject();
	   
	   try {
		   int id = request.getInt("id");
		   reservationService = new ReservationService(reservationRepository);
		   reservationService.refusedReservation(id);
		   response.put(STATUS, SUCCESS);
	   } catch (Exception e) {
           response.put(STATUS, ERROR);
           response.put(MESSAGE, "Errore durante la cancellazione: " + e.getMessage());
       }
	   return response;
   }
   

   
   private JSONObject getMailReservation(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   String mail = request.getString("mail");
		   boolean isConfirmed = request.getBoolean("isConfirmed");
		   boolean isAgente = request.getBoolean(ISAGENTE);
		   String data = request.getString("data");
		   reservationService = new ReservationService(reservationRepository);
		 
		   List<Prenotazione> lista = reservationService.getReservation(mail, isConfirmed, data, isAgente);
		 
		   response.put(STATUS, SUCCESS);
		   JSONArray jsonArray = new JSONArray();
		   for (Prenotazione p : lista) {
			
			   JSONObject jsonPrenotazione = new JSONObject();
	           jsonPrenotazione.put("id", p.getId());

	           jsonPrenotazione.put("data", p.getDataPrenotazione());
	    
	           jsonPrenotazione.put("ora", p.getOraPrenotazione());
	     
	           jsonPrenotazione.put("Cliente", p.getUser().getNome()+" "+p.getUser().getCognome());
	           jsonPrenotazione.put("mailCliente", p.getUser().getMail());
	
	           jsonPrenotazione.put(INDIRIZZO, p.getImmobile().getImmobileDettagli().getIndirizzo());
	           jsonPrenotazione.put(IMMOBILEID, p.getImmobile().getId());

	           jsonPrenotazione.put("Agente", p.getAgente().getNome()+" "+p.getAgente().getCognome());

	           jsonPrenotazione.put("confermato", p.isConfirmed());

	           jsonArray.put(jsonPrenotazione);
	 
		   }
		   
	       response.put("prenotazioni", jsonArray); 
	   }catch (Exception e) {
		
           response.put(STATUS, ERROR);
           response.put(MESSAGE, "Errore durante il recupero delle prenotazioni: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject getHouse(JSONObject request) {
	   JSONObject response = new JSONObject();
	   String query = request.getString("query");
	   houseService = new HouseService(houseRepository);
	   try {
		   List<Immobile> lista = houseService.retrieveHouse(query);
		   response.put(STATUS, SUCCESS);
		   JSONArray jsonArray = new JSONArray();
		   for(Immobile i : lista) {
			   JSONObject jsonImmobile = new JSONObject();
			   jsonImmobile.put("id", i.getId());
			   jsonImmobile.put(INDIRIZZO, i.getImmobileDettagli().getIndirizzo());
			   jsonImmobile.put("composizione", i.getComposizione().getId());
			   jsonImmobile.put("agente", i.getAgente().getMail());
			   jsonImmobile.put("prezzo", i.getPrezzo());
			   jsonImmobile.put("annuncio", i.getImmobileDettagli().getAnnuncio());
			   jsonImmobile.put("tipo", i.getImmobileDettagli().getTipo());
			   jsonImmobile.put("descrizione", i.getDescrizione());
			   jsonImmobile.put("classe", i.getImmobileDettagli().getClasseEnergetica());
			   jsonImmobile.put("urls", i.getUrls());
			   jsonArray.put(jsonImmobile);
		   }
		   response.put("immobili", jsonArray); 
	   }catch (Exception e) {
           response.put(STATUS, ERROR);
           response.put(MESSAGE, "Errore durante il recupero degli immobili: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject fetchAgente(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   String mail = request.getString("mail");
		   userService = new UserService(userRepository);
		   User user = userService.getUser(mail);
		   response.put(STATUS, SUCCESS);
		   response.put("mail", user.getMail());
		   response.put(PASSWORD, user.getPassword());
		   response.put("nome", user.getNome());
		   response.put("cognome", user.getCognome());
		   response.put("telefono", user.getNumeroTelefono());
		   response.put("dataNascita", user.getDataNascita());
		   response.put(ISAGENTE, user.getIsAgente());
		  
	   } catch (Exception e) {
           response.put(STATUS, ERROR);
           response.put(MESSAGE, "Errore durante il recupero degli agenti: " + e.getMessage());
       }
	   return response;
   }
   
   public JSONObject fetchComposizione(JSONObject request) {
	   JSONObject response = new JSONObject();
	   try {
		   int id = request.getInt("idComposizione");
		   houseService = new HouseService(houseRepository);
		   ComposizioneImmobile composizione = houseService.getComposizione(id);
		   ComposizioneImmobile composizioneBoolean = composizione.getComposizione();
		   response.put(STATUS, SUCCESS);
		   response.put("idComposizione", id);
		   response.put("quadratura",composizione.getQuadratura());
		   response.put("piani",composizione.getPiani());
		   response.put("stanze",composizione.getNumeroStanze());
		   response.put("terrazzo",composizioneBoolean.isTerrazzo());
		   response.put("giardino",composizioneBoolean.isGiardino());
		   response.put("ascensore",composizioneBoolean.isAscensore());
		   response.put("condominio",composizioneBoolean.isCondominio());
	   }catch (Exception e) {
		   response.put(STATUS, ERROR);
		   response.put(MESSAGE, "Errore durante il recupero delle composizioni degli immobili: " + e.getMessage());
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
	   String indirizzo = request.getString(INDIRIZZO);
	   String annuncio = request.getString("annuncio");
	   String tipo = request.getString("tipo");
	   String classeEnergetica = request.getString("classeEnergetica");
	   String descrizione = request.getString("descrizione");
	   
       String agente = request.getString("agente");
       ComposizioneImmobile composizioneBoolean = new ComposizioneImmobile(terrazzo, giardino, ascensore, condominio);
       ComposizioneImmobile composizione = new ComposizioneImmobile(0,quadratura, piani, stanze, composizioneBoolean);
       Immobile immobileDettagli = new Immobile(classeEnergetica, indirizzo, tipo, annuncio);
       Immobile immobile = new Immobile (0, prezzo, composizione, descrizione, "", null, immobileDettagli);
       houseService = new HouseService(houseRepository);
       int id = houseService.uploadHouse(immobile, agente);
       if(id!=0) {
    	   response.put(STATUS, SUCCESS);
    	   response.put(IMMOBILEID, id);
       } else {
    	   response.put(STATUS, ERROR);
    	   response.put(MESSAGE, "Risulta presente nel db");
       }
      
	   return response;
	  
   	}
   
   public JSONObject addNotifica(JSONObject request) {
	   String destinatario = request.getString("destinatario");
	   String messaggio = request.getString("messaggio");
	   reservationService = new ReservationService(reservationRepository);
	   boolean ok = reservationService.aggiungiNotifica(destinatario, messaggio);
	   JSONObject response = new JSONObject();
	   response.put(STATUS, ok ? SUCCESS : ERROR);
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
	    response.put(STATUS, SUCCESS);
	    response.put("notifiche", notificheArray);
	    return response;
   }
   
   public JSONObject setNotifiche(JSONObject request) {
	  
	   int id = request.getInt("id");
	   reservationService = new ReservationService(reservationRepository);
	   boolean confermato = reservationService.setLetta(id);
	   JSONObject response = new JSONObject();
	   response.put(STATUS, confermato ? SUCCESS : ERROR);
	   response.put(MESSAGE, confermato ? "notifica cancellata" : "errore");
	   return response;
	   
   }
   
   public JSONObject setUrls(JSONObject request) {
	   int id = request.getInt("id");
	   String urls = request.getString("urls");
	   houseService = new HouseService(houseRepository);
	   boolean updated = houseService.newUrls(urls, id);
	   JSONObject response = new JSONObject();
	   response.put(STATUS, updated ? SUCCESS : ERROR);
	   response.put(MESSAGE, updated ? "notifica cancellata" : "errore");
	   return response;
   }
   
   public JSONObject getPrenotazione(JSONObject request) {
	   int id = request.getInt("id");
	   reservationService = new ReservationService(reservationRepository);
	   Prenotazione prenotazione = reservationService.getPrenotazione(id);
	   JSONObject response = new JSONObject();
	   if(prenotazione!=null) {
		   System.out.println("sono nell'if dell'handler");
		   response.put(STATUS, SUCCESS );
		   response.put("data", prenotazione.getDataPrenotazione());
		   response.put("ora", prenotazione.getOraPrenotazione());
		   response.put("mailUtente", prenotazione.getUser().getMail());
		   response.put("immobileId", prenotazione.getImmobile().getId());
		   response.put("mailAgente", prenotazione.getAgente().getMail());
		   response.put("confermata", prenotazione.isConfirmed());
	   } else {
		   System.out.println("sono nell'else dell'handler");
		   response.put(STATUS,ERROR);
		   response.put(MESSAGE, "Prenotazione non trovata");
	   }
	   return response;
   }
}

