package business_logic_layer;

import data_access_layer.DatabaseManager;
import data_access_layer.interfaces.HouseRepositoryInterface;
import data_access_layer.interfaces.UserRepositoryInterface;

import java.util.ArrayList;

import Class.*;


public class HouseService {

	
	private HouseRepositoryInterface houseRepository;
	public HouseService(HouseRepositoryInterface houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	
	
	public ArrayList<Immobile> retrieveHouse(String query){
		return houseRepository.findHouses(query);
	}
	
	public ComposizioneImmobile getComposizione(int id) {
		return houseRepository.getComposizioneById(id);
	}
	
	public boolean uploadHouse(Immobile immobile, String agente) {
		ComposizioneImmobile composizione = immobile.getComposizione();
		String indirizzo = immobile.getIndirizzo();
		Immobile casa = houseRepository.getHouseByAddress(indirizzo);
		if ((casa == null) || (composizione.isCondominio())) {
			
			int quadratura = composizione.getQuadratura();
			int stanze = composizione.getNumeroStanze();
			int piani = composizione.getPiani();
			boolean giardino = composizione.isGiardino();
			boolean condominio = composizione.isCondominio();
			boolean ascensore = composizione.isAscensore();
			boolean terrazzo = composizione.isTerrazzo();
			int id = houseRepository.uploadComposizione(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
			double prezzo = immobile.getPrezzo();
			String annuncio = immobile.getAnnuncio();
			String tipo = immobile.getTipo();
			String classeEnergetica = immobile.getClasseEnergetica();
			String descrizione = immobile.getDescrizione();
			String urls = immobile.getUrls();
			houseRepository.uploadHouse(prezzo, id, indirizzo, annuncio, tipo, classeEnergetica, 
					descrizione, urls, agente);
			return true;
		}
		return false;
	}
	
	
}
