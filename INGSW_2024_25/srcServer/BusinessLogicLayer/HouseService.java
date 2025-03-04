package BusinessLogicLayer;

import DataAccessLayer.DatabaseManager;
import DataAccessLayer.Interfaces.HouseRepositoryInterface;
import DataAccessLayer.Interfaces.UserRepositoryInterface;

import java.util.ArrayList;

import Class.*;


public class HouseService {

	
	private HouseRepositoryInterface houseRepository;
	public HouseService(HouseRepositoryInterface houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	public int uploadComposizioneImmobile(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
		int id = houseRepository.uploadComposizione(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		return id;
	}
	
	
	
	public boolean uploadNewHouse(double prezzo, int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica,
        		   String descrizione,String urls, String agente) {
		
		Immobile immobile = houseRepository.getHouseByAddress(indirizzo);
		ComposizioneImmobile composizione = houseRepository.getComposizioneById(idComposizioneImmobile);
		if ((immobile == null) || (composizione.isCondominio())) {
			houseRepository.uploadHouse(prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classeEnergetica, 
					descrizione, urls, agente);
			return true;
			
		}
		return false;
		
	}
	
	public ArrayList<Immobile> retrieveHouse(String query){
		return houseRepository.findHouses(query);
	}
	
	public ComposizioneImmobile getComposizione(int id) {
		return houseRepository.getComposizioneById(id);
	}
	
	
	
}
