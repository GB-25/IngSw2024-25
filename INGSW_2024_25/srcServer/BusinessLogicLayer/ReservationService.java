package BusinessLogicLayer;

import java.util.ArrayList;
import java.util.List;

import Class.Notifica;
import Class.Prenotazione;
import DataAccessLayer.DatabaseManager;
import DataAccessLayer.Interfaces.ReservationRepositoryInterface;


public class ReservationService {

	private ReservationRepositoryInterface reservationRepository;
	public ReservationService(ReservationRepositoryInterface reservationRepository) {
		
		this.reservationRepository = reservationRepository;
	}
	
	public boolean newReservation(String data, String ora,  String cliente, String indirizzoImmobile, String agente) {
		Prenotazione prenotazione = reservationRepository.checkReservation(cliente, indirizzoImmobile);
		if(prenotazione !=null) {
			return false;
		}
		if (reservationRepository.alreadyGotAppointment(cliente, false, data, ora)) {
			return false;
		} else {
			reservationRepository.createReservation(data, ora, cliente, indirizzoImmobile, agente);
			return true;
		}
	}
	
	public void refusedReservation(int id) {
		reservationRepository.deleteReservation(id);
	}
	
	public boolean acceptReservation(int id, String mail, String data, String ora) {
		if(!reservationRepository.alreadyGotAppointment(mail, true, data, ora)) {
			reservationRepository.confirmReservation(id);
			return true;
		} else {
			return false;
		}
		
	}
	
	public ArrayList<Prenotazione> getReservation(String mail, boolean isConfirmed, String data, boolean isAgente){
		return reservationRepository.getReservationByMail(mail, isConfirmed, data, isAgente);
	}
	
	public int retrieveId(String mailCliente, String mailAgente, String data, String ora, String indirizzo ) {
		return reservationRepository.getReservationId(mailCliente, mailAgente, data, ora, indirizzo, false);
	}
	
	public boolean aggiungiNotifica(String destinatario, String messaggio) {
	    Notifica nuovaNotifica = new Notifica(destinatario, messaggio);
	    return reservationRepository.salvaNotifica(nuovaNotifica);
	}
	
	public List<Notifica> getNotifiche(String mail) {
	    List<Notifica> prova = reservationRepository.getNotificheUtente(mail);
	   
	    return prova;
	}
	
	public boolean setLetta( int id) {
		return reservationRepository.setNotifica(id);
	}
}
