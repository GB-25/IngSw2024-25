package Class;

public class Immobile {

	private int id;
	private double prezzo;
	private ComposizioneImmobile composizione;
	private String indirizzo;
	private String annuncio;
	private String tipo;
	private String classeEnergetica; 
	private String descrizione;
	private User agente;
	
	
	public Immobile(int id, double prezzo, ComposizioneImmobile composizione, String indirizzo, String annuncio,
			String tipo, String classeEnergetica, String descrizione, User agente) {
		
		this.id = id;
		this.prezzo = prezzo;
		this.composizione = composizione;
		this.annuncio = annuncio;
		this.tipo = tipo;
		this.classeEnergetica = classeEnergetica;
		this.descrizione = descrizione;
		this.agente = agente;
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


	public User getAgente() {
		return agente;
	}


	public void setAgente(User agente) {
		this.agente = agente;
	}
	
	
}
