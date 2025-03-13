package Class;

import java.util.Date;

public class Prenotazione {
	
	private int id;
	private String dataPrenotazione;
	private String oraPrenotazione;
	private User user;
	private Immobile immobile;
	private User agente;
	private boolean isConfirmed;
	
	public Prenotazione(int id, String dataPrenotazione, String oraPrenotazione, User user, Immobile immobile, User agente, boolean isConfirmed) {
		this.id = id;
		this.dataPrenotazione = dataPrenotazione;
		this.oraPrenotazione = oraPrenotazione;
		this.user = user;
		this.immobile = immobile;
		this.agente = agente;
		this.isConfirmed = isConfirmed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(String dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}

	public String getOraPrenotazione() {
		return oraPrenotazione;
	}

	public void setOraPrenotazione(String oraPrenotazione) {
		this.oraPrenotazione = oraPrenotazione;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Immobile getImmobile() {
		return immobile;
	}

	public void setImmobile(Immobile immobile) {
		this.immobile = immobile;
	}

	public User getAgente() {
		return agente;
	}

	public void setAgente(User agente) {
		this.agente = agente;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}
