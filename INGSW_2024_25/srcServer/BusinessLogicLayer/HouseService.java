package BusinessLogicLayer;

import DataAccessLayer.DatabaseManager;

import java.util.ArrayList;

import Class.*;


public class HouseService {

	private DatabaseManager dbManager;
	
	public HouseService() {
		dbManager = new DatabaseManager();
	}
	
	public int uploadComposizioneImmobile(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo) {
		int id = dbManager.uploadComposizione(quadratura, stanze, piani, giardino, condominio, ascensore, terrazzo);
		return id;
	}
	
	
	
	public boolean uploadNewHouse(double prezzo, int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica,
        		   String descrizione,String urls, String agente) {
		
		Immobile immobile = dbManager.getHouseByAddress(indirizzo);
		
		if (immobile == null) {
			dbManager.uploadHouse(prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classeEnergetica, 
					descrizione, urls, agente);
			return true;
			
		}
		return false;
		
	}
	
	public ArrayList<Immobile> retrieveHouse(String query){
		return dbManager.findHouses(query);
	}
	
	public ComposizioneImmobile getComposizione(int id) {
		return dbManager.getComposizioneById(id);
	}
	
	
	
}
