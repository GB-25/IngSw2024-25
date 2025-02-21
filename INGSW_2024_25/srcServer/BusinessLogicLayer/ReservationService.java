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
		
		} else {
			dbManager.createReservation(data, ora, cliente, indirizzoImmobile, agente);
			return true;
		}
	}
	
	public void refusedReservation(int id) {
		dbManager.deleteReservation(id);
	}
	
	public void acceptReservation(int id) {
		dbManager.confirmReservation(id);
	}
	
	public ArrayList<Prenotazione> getReservation(String mail, boolean isConfirmed, boolean isAgente){
		return dbManager.getReservationByMail(mail, isConfirmed, isAgente);
	}
	
}
