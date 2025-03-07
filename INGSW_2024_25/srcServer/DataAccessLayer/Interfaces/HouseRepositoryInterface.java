package DataAccessLayer.Interfaces;

import java.util.ArrayList;

import Class.ComposizioneImmobile;
import Class.Immobile;

public interface HouseRepositoryInterface {

	int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo);
	
	ComposizioneImmobile getComposizioneById(int id);
	
	Immobile getHouseByAddress(String indirizzo);
	
	void uploadHouse(double prezzo,int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, 
			String descrizione, String urls, String agente);
	
	ArrayList<Immobile> findHouses(String query);
	
	
}
