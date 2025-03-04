package DataAccessLayer.Interfaces;

import java.util.ArrayList;

import Class.Prenotazione;

public interface ReservationRepositoryInterface {

	boolean alreadyGotAppointment(String mail, boolean isAgente, String data, String ora);
	
	Prenotazione checkReservation(String mailCliente, String indirizzo);
	
	void createReservation(String data, String ora,  String cliente, String indirizzoImmobile, String agente);
	
	ArrayList<Prenotazione> getReservationByMail(String mail, boolean isConfirmed, String data, boolean isAgente);
	
	void deleteReservation(int id);
	
	void confirmReservation(int id);
	
	int getReservationId(String mailCliente, String mailAgente, String data, String ora, String indirizzo, boolean confirmed);
}
