package data_access_layer.interfaces;

import java.util.ArrayList;
import java.util.List;

import classi.Notifica;
import classi.Prenotazione;

public interface ReservationRepositoryInterface {

	boolean alreadyGotAppointment(String mail, boolean isAgente, String data, String ora);
	
	Prenotazione checkReservation(String mailCliente, int idImmobile);
	
	void createReservation(String data, String ora,  String cliente, int idImmobile, String agente);
	
	ArrayList<Prenotazione> getReservationByMail(String mail, boolean isConfirmed, String data, boolean isAgente);
	
	void deleteReservation(int id);
	
	void confirmReservation(int id);
	
	int getReservationId(String mailCliente, String mailAgente, String data, String ora, int idImmobile, boolean confirmed);

	List<Notifica> getNotificheUtente(String mail);

	boolean salvaNotifica(Notifica nuovaNotifica);

	boolean setNotifica(int id);
}
