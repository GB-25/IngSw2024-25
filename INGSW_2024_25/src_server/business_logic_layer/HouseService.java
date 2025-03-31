package business_logic_layer;


import data_access_layer.interfaces.HouseRepositoryInterface;


import java.util.List;

import classi.*;


public class HouseService {

	private HouseRepositoryInterface houseRepository;
	/**
     * 
     * Costruttore
     */
	public HouseService(HouseRepositoryInterface houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	public Immobile getImmobile(int id) {
		return houseRepository.getHouseById(id);
	}
	
	public List<Immobile> retrieveHouse(String query){
		return houseRepository.findHouses(query);
	}
	
	public ComposizioneImmobile getComposizione(int id) {
		return houseRepository.getComposizioneById(id);
	}
	
	public int uploadHouse(Immobile immobile, String agente) {
		ComposizioneImmobile composizione = immobile.getComposizione();
		ComposizioneImmobile composizioneBoolean = composizione.getComposizione();
		Immobile immobileDettagli = immobile.getImmobileDettagli();
		Immobile casa = houseRepository.getHouseByAddress(immobileDettagli.getIndirizzo());
		if ((casa == null) || (composizioneBoolean.isCondominio())) {
			int quadratura = composizione.getQuadratura();
			int stanze = composizione.getNumeroStanze();
			int piani = composizione.getPiani();
			boolean giardino = composizioneBoolean.isGiardino();
			boolean condominio = composizioneBoolean.isCondominio();
			boolean ascensore = composizioneBoolean.isAscensore();
			boolean terrazzo = composizioneBoolean.isTerrazzo();
			int id = houseRepository.uploadComposizione(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
			double prezzo = immobile.getPrezzo();
			String descrizione = immobile.getDescrizione();
			String urls = immobile.getUrls();
			return houseRepository.uploadHouse(prezzo, id, immobileDettagli, descrizione, urls, agente);
		}
		return 0;
		
	}
	
	public boolean newUrls(String urls, int id) {
		return houseRepository.updateUrls(urls, id);
	}
}
