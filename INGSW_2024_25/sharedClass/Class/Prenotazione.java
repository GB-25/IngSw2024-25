package Class;

import java.util.Date;

public class Prenotazione {
	
	private int id;
	private Date dataPrenotazione;
	private User user;
	private Immobile immobile;
	private User agente;
	
	public Prenotazione(int id, Date dataPrenotazione, User user, Immobile immobile, User agente) {
		super();
		this.id = id;
		this.dataPrenotazione = dataPrenotazione;
		this.user = user;
		this.immobile = immobile;
		this.agente = agente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(Date dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
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
}
