package BusinessLogicLayer;

import java.util.ArrayList;

import Class.Prenotazione;
import DataAccessLayer.DatabaseManager;


public class ReservationService {

	private DatabaseManager dbManager;
	
	public ReservationService() {
		dbManager = new DatabaseManager();
	}
	
	public boolean newReservation(String data, String ora,  String cliente, String indirizzoImmobile, String agente) {
		Prenotazione prenotazione = dbManager.checkReservation(cliente, indirizzoImmobile);
		if(prenotazione !=null) {
			return false;
		}
		if (dbManager.alreadyGotAppointment(cliente, false, data, ora)) {
			return false;
		} else {
			dbManager.createReservation(data, ora, cliente, indirizzoImmobile, agente);
			return true;
		}
	}
	
	public void refusedReservation(int id) {
		dbManager.deleteReservation(id);
	}
	
	public boolean acceptReservation(int id, String mail, String data, String ora) {
		if(!dbManager.alreadyGotAppointment(mail, true, data, ora)) {
			dbManager.confirmReservation(id);
			return true;
		} else {
			return false;
		}
		
	}
	
	public ArrayList<Prenotazione> getReservation(String mail, boolean isConfirmed, String data,  boolean isAgente){
		return dbManager.getReservationByMail(mail, isConfirmed, data, isAgente);
	}
}
