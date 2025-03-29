package classi;

public class ComposizioneImmobile {

	private int id;
	private int quadratura;
	private int piani;
	private int numeroStanze;
	private boolean terrazzo;
	private boolean giardino;
	private boolean ascensore;
	private boolean condominio;
	private ComposizioneImmobile composizione;
	
	
	public ComposizioneImmobile(boolean terrazzo, boolean giardino, boolean ascensore, boolean condominio) {
		this.terrazzo = terrazzo;
		this.giardino = giardino;
		this.ascensore = ascensore;
		this.condominio = condominio;
	}

	public ComposizioneImmobile(int id, int quadratura, int piani, int numeroStanze, ComposizioneImmobile composizione) {
		this.id = id;
		this.quadratura = quadratura;
		this.piani = piani;
		this.numeroStanze = numeroStanze;
		this.composizione = composizione;
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

	public ComposizioneImmobile getComposizione() {
		return composizione;
	}

	public void setComposizione(ComposizioneImmobile composizione) {
		this.composizione = composizione;
	}
}
