package model;


import java.io.*;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.Notifica;
import classi.Prenotazione;
import classi.User;

public class ClientModel {
    private String serverIP;
    private int port;
    Logger logger = Logger.getLogger(getClass().getName());
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String ACTION = "action";
    private static final String PASSWORDSTRING = "password";
    private static final String SUCCESS = "success";
    private static final String ISAGENTESTRING = "isAgente";
    private static final String INDIRIZZOSTRING = "indirizzo";
    private static final String IMMOBILESTRING = "idImmobile";
    /**
     * 
     * Costruttore
     * 
     */
    public ClientModel(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
    }

    /**
     * 
     * @param request
     * @return JSONObject che i vari metodi che chiamano sendRequest provvedono a "spacchettare"
     * permette la comunicazione col Server
     */
    public JSONObject sendRequest(JSONObject request) {
        try (Socket socket = new Socket(serverIP, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request.toString());

            String response = in.readLine();
            return new JSONObject(response);

        } catch (IOException e) {
        	logger.info("Errore nella comunicazione col Server");
            JSONObject errorResponse = new JSONObject();
            errorResponse.put(STATUS, ERROR);
            errorResponse.put(MESSAGE, "Connessione al server fallita");
            return errorResponse;
        }
    }

    public User loginModel(String mail, String password) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "login");
        request.put("mail", mail);
        request.put(PASSWORDSTRING, password);
        User user;
        JSONObject response = sendRequest(request);
        if (response.getString(STATUS).equals(SUCCESS)) {
            String nome = response.getString("nome");
            String cognome = response.getString("cognome");
            String dataNascita = response.getString("dataNascita");
            String telefono = response.getString("telefono");
            boolean isAgente = response.getBoolean(ISAGENTESTRING);

            user = new User(mail, password, nome, cognome, telefono, dataNascita, isAgente);
        } else {
            user = null;
        }
        return user;
    }

    public User registerModel (String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
        JSONObject request = new JSONObject();
        User user;
        request.put(ACTION, "register");
        request.put("name", nome);
        request.put("surname", cognome);
        request.put("birthdate", data);
        request.put("mail", mail);
        request.put("cellphone", telefono);
        request.put(PASSWORDSTRING, password);
        request.put(ISAGENTESTRING, isAgente);
        JSONObject response = sendRequest(request);
        if (response.getString(STATUS).equals(SUCCESS)) {
            user = new User(mail, password, nome, cognome, telefono, data, isAgente );
        } else {
            user = null;
        }
        return user;
    }

    public boolean newPasswordModel(String mail, String nuovaPassword) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "updatePassword");
        request.put("mail", mail);
        request.put("newPassword", nuovaPassword);
        JSONObject response = sendRequest(request);
     	return (response.getString(STATUS).equals(SUCCESS));
    }
    /**
     * 
     * @param filePath
     * @param id
     * @return Stringa dell'url per recuperare la foto dell'immobile
     */
    public String uploadFileModel(String filePath, int id) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "uploadFile");
        String fileUrl = null;
        try {
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String base64Data = Base64.getEncoder().encodeToString(fileBytes);

            request.put("fileName", path.getFileName().toString());
            request.put("fileData", base64Data);
            request.put("id", id);
            JSONObject response = sendRequest(request);
            if (response.getString(STATUS).equals(SUCCESS)) {
                fileUrl = response.getString("fileUrl"); 
            } else {
                logger.log(Level.INFO, () -> "Errore durante l'upload: " + response.getString(MESSAGE)); 
            }
        } catch (IOException e) {
        	logger.info("Errore nel caricamento delle immagini");
            
        }
        return fileUrl;
    }
    /**
     * 
     * @param fileName
     * @return stringa in base 64 dell'immagine
     */
    public String downloadFileModel(String fileName) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "downloadFile");
        request.put("fileName", fileName);
        String fileData = null;

        JSONObject response = sendRequest(request);
        if (response.getString(STATUS).equals(SUCCESS)) {
            fileData = response.getString("fileData");
        } else {
            logger.log(Level.INFO, () -> "Errore durante il download: " + response.getString(MESSAGE));
        }
        return fileData;
    }

    public List<String> getReservation(String mail, boolean isConfirmed, String data, boolean isAgente) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "getReservation");
        request.put("mail", mail);
        request.put("isConfirmed", isConfirmed);
        request.put("data", data);
        request.put(ISAGENTESTRING, isAgente);
        JSONObject response = sendRequest(request);
        ArrayList<String> prenotazioni = new ArrayList<>(); 
        if (response.getString(STATUS).equals(SUCCESS)) {
            JSONArray jsonArray = response.getJSONArray("prenotazioni");
            
            for (int i = 0; i < jsonArray.length(); i++) {
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String indirizzo = jsonObject.getString(INDIRIZZOSTRING);
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
    
    public List<Prenotazione> getWholeReservationAgent (User agente, String data) {
    	JSONObject request = new JSONObject();
    	User cliente;
    	String mailCliente;
        request.put(ACTION, "getReservation");
        request.put("mail", agente.getMail());
        request.put("isConfirmed", false);
        request.put("data", data);
        request.put(ISAGENTESTRING, true);
        JSONObject response = sendRequest(request);
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        List <Immobile> immobili;
        if (response.getString(STATUS).equals(SUCCESS)) {
            JSONArray jsonArray = response.getJSONArray("prenotazioni");
            for (int i = 0; i < jsonArray.length(); i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	int idImmobile = jsonObject.getInt(IMMOBILESTRING);
            	String query = "SELECT * FROM immobili WHERE id ='"+idImmobile+"';";
            	immobili = searchHouse(query);
            	mailCliente = jsonObject.getString("mailCliente");
            	cliente = getAgente(mailCliente);
            	Prenotazione prenotazione = new Prenotazione(jsonObject.getInt("id"), jsonObject.getString("data"),
            			jsonObject.getString("ora"), cliente, immobili.get(0), agente, false);
            	prenotazioni.add(prenotazione);
              }
            }
    	return prenotazioni;
    }

    public int makeReservation(String data, String ora, String mailCliente, int idImmobile, String mailAgente) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "makeNewReservation");
        request.put("data", data);
        request.put("ora", ora);
        request.put("mailCliente", mailCliente);
        request.put(IMMOBILESTRING, idImmobile);
        request.put("mailAgente", mailAgente);

        JSONObject response = sendRequest(request);
        if (response.getString(STATUS).equals(ERROR)) {
            return 0;
        } else {
            return response.getInt("id");
        }
    }

    public boolean confirmReservation(int id, String mail, String data, String ora) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "reservationConfirmed");
        request.put("id", id);
        request.put("mail", mail);
        request.put("data", data);
        request.put("ora", ora);
        JSONObject response = sendRequest(request);
        return !(response.getString(STATUS).equals(ERROR));
    }

    public boolean denyReservation(int id) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "reservationDenied");
        request.put("id", id);

        JSONObject response = sendRequest(request);
        return !(response.getString(STATUS).equals(ERROR));
    }

    public List<Immobile> searchHouse(String query) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "findHouse");
        request.put("query", query);

        JSONObject response = sendRequest(request);

        ArrayList<Immobile> immobili = new ArrayList<>();
        Immobile casa;
        Immobile immobileDettagli;
        if (response.getString(STATUS).equals(SUCCESS)) {
            JSONArray jsonArray = response.getJSONArray("immobili");
            for (int i = 0; i < jsonArray.length(); i++) {
            	
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String indirizzo = jsonObject.getString(INDIRIZZOSTRING);
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
                immobileDettagli = new Immobile(classe, indirizzo, tipo, tipoAnnuncio);
                casa = new Immobile(id, prezzo, composizione, descrizione, urls, agente, immobileDettagli);
                immobili.add(casa);
                
            }
        } else {
            immobili = null;
        }
        return immobili;
    }

    public User getAgente(String mailAgente) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "findAgente");
        request.put("mail", mailAgente);

        JSONObject response = sendRequest(request);

        String mail = response.getString("mail");
        String password = response.getString(PASSWORDSTRING);
        String nome = response.getString("nome");
        String cognome = response.getString("cognome");
        String dataNascita = response.getString("dataNascita");
        String telefono = response.getString("telefono");
        Boolean isAgente = response.getBoolean(ISAGENTESTRING);

        return new User(mail, password, nome, cognome, telefono, dataNascita, isAgente);
    }

    public ComposizioneImmobile getComposizione(int idComposizione) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "findComposizione");
        request.put("idComposizione", idComposizione);

        JSONObject response = sendRequest(request);

        int id = response.getInt("idComposizione");
        int quadratura = response.getInt("quadratura");
        int stanze = response.getInt("stanze"); 
        int piani = response.getInt("piani"); 
        boolean giardino = response.getBoolean("giardino");
        boolean condominio = response.getBoolean("condominio");
        boolean ascensore = response.getBoolean("ascensore"); 
        boolean terrazzo = response.getBoolean("terrazzo");
        ComposizioneImmobile composizioneBoolean = new ComposizioneImmobile(terrazzo, giardino, ascensore, condominio);
        return new ComposizioneImmobile(id, quadratura, stanze, piani, composizioneBoolean);
    }

    public boolean uploadNewHouseModel(Immobile immobile, List<String> foto) {
    	ComposizioneImmobile composizione = immobile.getComposizione();
    	ComposizioneImmobile composizioneBoolean = composizione.getComposizione();
    	Immobile immobileDettagli = immobile.getImmobileDettagli();
        JSONObject request = new JSONObject();
        request.put(ACTION, "uploadNewHouse");
        request.put("quadratura", composizione.getQuadratura());
        request.put("stanze", composizione.getNumeroStanze());
        request.put("piani", composizione.getPiani());
        request.put("giardino", composizioneBoolean.isGiardino());
        request.put("condominio", composizioneBoolean.isCondominio());
        request.put("ascensore", composizioneBoolean.isAscensore());
        request.put("terrazzo", composizioneBoolean.isTerrazzo());
        request.put("prezzo", immobile.getPrezzo());
        request.put(INDIRIZZOSTRING, immobileDettagli.getIndirizzo());
        request.put("annuncio", immobileDettagli.getAnnuncio());
        request.put("tipo", immobileDettagli.getTipo());
        request.put("classeEnergetica", immobileDettagli.getClasseEnergetica());
        request.put("descrizione", immobile.getDescrizione());
        
        request.put("agente", immobile.getAgente().getMail());
        
        JSONObject response = sendRequest(request);
        if (response.getString(STATUS).equals(SUCCESS)) {
        	int id = response.getInt(IMMOBILESTRING);
        	StringBuilder sb = new StringBuilder();
        	for (String filename: foto) {
        		if (sb.length() > 0) {
                    sb.append(",");
                }
        		
        		sb.append(this.uploadFileModel(filename, id));
        	}
        	String urls = sb.toString();
        	return updateUrls(urls, id);
        	
        }
        else {
        	return false;
        }
    }

    public boolean inviaRichiestaNotifica(Notifica notifica) {
        try {
            JSONObject request = new JSONObject();
            request.put(ACTION, "aggiungiNotifica");
            request.put("destinatario", notifica.getDestinatarioEmail());
            request.put("messaggio", notifica.getMessaggio());

            JSONObject response = sendRequest(request);
            return response.getString(STATUS).equalsIgnoreCase(SUCCESS);
        } catch (Exception e) {
        	logger.info("Errore nell'invio di notifiche");
            return false;
        }
    }

    public List<Notifica> richiestaNotificheUtente(String mail) {
        List<Notifica> notifiche = new ArrayList<>();
        try {
            JSONObject richiesta = new JSONObject();
            richiesta.put(ACTION, "getNotifiche");
            richiesta.put("mail", mail);

            JSONObject risposta = sendRequest(richiesta);

            if (risposta.getString(STATUS).equalsIgnoreCase(SUCCESS)) {
                JSONArray notificheArray = risposta.getJSONArray("notifiche");

                for (int i = 0; i < notificheArray.length(); i++) {
                    JSONObject obj = notificheArray.getJSONObject(i);
                    Notifica n = new Notifica(
                            obj.getInt("id"),
                            mail,
                            obj.getString("messaggio"),
                            obj.getBoolean("letta")
                    );
                    notifiche.add(n);
                }
            }
        } catch (Exception e) {
        	logger.info("Errore nella ricevuta di notifiche");
        }
        return notifiche;
    }

    public boolean notificaLetta(Notifica notifica) {
        JSONObject request = new JSONObject();
        request.put(ACTION, "notificaLetta");
        request.put("id", notifica.getId());
        JSONObject response = sendRequest(request);
        return !(response.getString(STATUS).equals(ERROR)); 
    }
    /**
     * 
     * @param urls
     * @param id
     * @return true se l'aggiornamento degli urls è andato a buon fine
     */
    public boolean updateUrls(String urls, int id) {
    	JSONObject request = new JSONObject();
    	request.put(ACTION, "updateUrls");
    	request.put("urls", urls);
    	request.put("id", id);
    	JSONObject response = sendRequest(request);
        return (response.getString(STATUS).equals(SUCCESS)); 
    }
    
    public Prenotazione retrievePrenotazione(int id) {
    	JSONObject request = new JSONObject();
    	request.put(ACTION, "retrievePrenotazione");
    	request.put("id", id);
    	JSONObject response = sendRequest(request);
    	if (response.getString(STATUS).equals(SUCCESS)) {
    		System.out.println("sono nell'if del model");
    		String dataPrenotazione = response.getString("data");
    		String oraPrenotazione = response.getString("ora");
    		User user = getAgente(response.getString("mailUtente"));
    		int idImmobile = response.getInt("immobileId");
    		String query = "SELECT * FROM immobili WHERE id = '"+idImmobile+"';";
    		List <Immobile> immobili = searchHouse(query);
    		Immobile immobile = immobili.get(0);
    		User agente = getAgente(response.getString("mailAgente"));
    		boolean isConfirmed = response.getBoolean("confermata");
    		return new Prenotazione(id, dataPrenotazione, oraPrenotazione, user, immobile, agente, isConfirmed);
    	} else {
    		System.out.println("sono nell'else del model");
    		return null;
    	}
    }
   }
