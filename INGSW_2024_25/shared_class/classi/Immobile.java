package classi;

public class Immobile {

	private int id;
	private double prezzo;
	private ComposizioneImmobile composizione;
	private String indirizzo;
	private String annuncio;
	private String tipo;
	private String classeEnergetica; 
	private String descrizione;
	private String urls;
	private User agente;
	private Immobile immobileDettagli;
	


	public Immobile(String classeEnergetica, String posizione, String tipoImmobile, String annuncio) {
		this.classeEnergetica = classeEnergetica;
		this.indirizzo = posizione;
		this.tipo = tipoImmobile;
		this.annuncio = annuncio;
	}
	
	public Immobile(int id, double prezzo, ComposizioneImmobile composizione, String descrizione, String urls, User agente, Immobile immobileDettagli) {
		this.id = id;
		this.prezzo = prezzo;
		this.composizione = composizione;
		this.descrizione = descrizione;
		this.urls = urls;
		this.agente = agente;
		this.immobileDettagli = immobileDettagli;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	public ComposizioneImmobile getComposizione() {
		return composizione;
	}


	public void setComposizione(ComposizioneImmobile composizione) {
		this.composizione = composizione;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getAnnuncio() {
		return annuncio;
	}


	public void setAnnuncio(String annuncio) {
		this.annuncio = annuncio;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getClasseEnergetica() {
		return classeEnergetica;
	}


	public void setClasseEnergetica(String classeEnergetica) {
		this.classeEnergetica = classeEnergetica;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getUrls() {
		return urls;
	}


	public void setUrls(String urls) {
		this.urls = urls;
	}


	public User getAgente() {
		return agente;
	}


	public void setAgente(User agente) {
		this.agente = agente;
	}

	public Immobile getImmobileDettagli() {
		return immobileDettagli;
	}

	public void setImmobileDettagli(Immobile immobileDettagli) {
		this.immobileDettagli = immobileDettagli;
	}
	
	
}
