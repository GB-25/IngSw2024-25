package data_access_layer.interfaces;

import java.util.ArrayList;

import classi.ComposizioneImmobile;
import classi.Immobile;

public interface HouseRepositoryInterface {

	int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo);
	
	ComposizioneImmobile getComposizioneById(int id);
	
	Immobile getHouseByAddress(String indirizzo);
	
	void uploadHouse(double prezzo,int idComposizioneImmobile, String indirizzo, String annuncio, String tipo, String classeEnergetica, 
			String descrizione, String urls, String agente);
	
	ArrayList<Immobile> findHouses(String query);
	
	
}
