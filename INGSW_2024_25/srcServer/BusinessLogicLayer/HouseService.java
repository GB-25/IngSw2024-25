package BusinessLogicLayer;

import DataAccessLayer.DatabaseManager;
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
	
	
	
}
