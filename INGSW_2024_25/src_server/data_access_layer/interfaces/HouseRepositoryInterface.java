package data_access_layer.interfaces;

import java.util.ArrayList;

import classi.ComposizioneImmobile;
import classi.Immobile;

public interface HouseRepositoryInterface {

	int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo);
	
	ComposizioneImmobile getComposizioneById(int id);
	
	Immobile getHouseByAddress(String indirizzo);
	
	int uploadHouse(double prezzo,int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, 
			String descrizione, String urls, String agente);
	
	ArrayList<Immobile> findHouses(String query);
	
	Immobile getHouseById(int id);
	
	boolean updateUrls(String urls, int id);
}
