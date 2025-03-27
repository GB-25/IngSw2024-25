package business_logic_layer;


import java.util.List;

import classi.Notifica;
import classi.Prenotazione;
import data_access_layer.interfaces.ReservationRepositoryInterface;


public class ReservationService {

	private ReservationRepositoryInterface reservationRepository;
	public ReservationService(ReservationRepositoryInterface reservationRepository) {
		
		this.reservationRepository = reservationRepository;
	}
	
	public boolean newReservation(String data, String ora,  String cliente, int idImmobile, String agente) {
		Prenotazione prenotazione = reservationRepository.checkReservation(cliente, idImmobile);
		if(prenotazione !=null) {
			return false;
		}
		if (reservationRepository.alreadyGotAppointment(cliente, false, data, ora)) {
			return false;
		} else {
			reservationRepository.createReservation(data, ora, cliente, idImmobile, agente);
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
	
	public List<Prenotazione> getReservation(String mail, boolean isConfirmed, String data, boolean isAgente){
		
		return reservationRepository.getReservationByMail(mail, isConfirmed, data, isAgente);
	}
	
	public int retrieveId(String mailCliente, String mailAgente, String data, String ora, int idImmobile ) {
		return reservationRepository.getReservationId(mailCliente, mailAgente, data, ora, idImmobile, false);
	}
	
	public boolean aggiungiNotifica(String destinatario, String messaggio) {
	    Notifica nuovaNotifica = new Notifica(destinatario, messaggio);
	    return reservationRepository.salvaNotifica(nuovaNotifica);
	}
	
	public List<Notifica> getNotifiche(String mail) {
	    return reservationRepository.getNotificheUtente(mail);
	  
	}
	
	public boolean setLetta( int id) {
		return reservationRepository.setNotifica(id);
	}
}
