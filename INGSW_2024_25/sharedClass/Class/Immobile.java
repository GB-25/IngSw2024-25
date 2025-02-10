package Class;

public class Immobile {

	private int id;
	private int quadratura;
	private int piani;
	private int numeroStanze;
	private boolean terrazzo;
	private boolean giardino;
	private boolean ascensore;
	private boolean condominio;
	private double prezzo;
	private String indirizzo;
	private String annuncio;
	private String tipo;
	private String classeEnergetica; 
	private String descrizione;
	private User agente;
	
	
	public Immobile(int id, int quadratura, int piani, int numeroStanze, boolean terrazzo, boolean giardino,
			boolean ascensore, boolean condominio, double prezzo, String indirizzo, String annuncio,
			String tipo, String classeEnergetica, String descrizione, User agente) {
		
		this.id = id;
		this.quadratura = quadratura;
		this.piani = piani;
		this.numeroStanze = numeroStanze;
		this.terrazzo = terrazzo;
		this.giardino = giardino;
		this.ascensore = ascensore;
		this.condominio = condominio;
		this.prezzo = prezzo;
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


	public int getQuadratura() {
		return quadratura;
	}


	public void setQuadratura(int quadratura) {
		this.quadratura = quadratura;
	}


	public int getPiani() {
		return piani;
	}


	public void setPiani(int piani) {
		this.piani = piani;
	}


	public int getNumeroStanze() {
		return numeroStanze;
	}


	public void setNumeroStanze(int numeroStanze) {
		this.numeroStanze = numeroStanze;
	}


	public boolean isTerrazzo() {
		return terrazzo;
	}


	public void setTerrazzo(boolean terrazzo) {
		this.terrazzo = terrazzo;
	}


	public boolean isGiardino() {
		return giardino;
	}


	public void setGiardino(boolean giardino) {
		this.giardino = giardino;
	}


	public boolean isAscensore() {
		return ascensore;
	}


	public void setAscensore(boolean ascensore) {
		this.ascensore = ascensore;
	}


	public boolean isCondominio() {
		return condominio;
	}


	public void setCondominio(boolean condominio) {
		this.condominio = condominio;
	}


	public double getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
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
