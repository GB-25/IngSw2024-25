package server.data_access_layer.interfaces;

import java.util.ArrayList;

import shared.classi.*;


public interface HouseRepositoryInterface {

	int uploadComposizione(int quadratura, int stanze, int piani, boolean giardino, boolean condominio, boolean ascensore, boolean terrazzo);
	
	ComposizioneImmobile getComposizioneById(int id);
	
	Immobile getHouseByAddress(String indirizzo);
	
	int uploadHouse(double prezzo,int idComposizioneImmobile, Immobile immobileDettagli, String descrizione, String urls, String agente);
	
	ArrayList<Immobile> findHouses(String query);
	
	Immobile getHouseById(int id);
	
	boolean updateUrls(String urls, int id);
}
