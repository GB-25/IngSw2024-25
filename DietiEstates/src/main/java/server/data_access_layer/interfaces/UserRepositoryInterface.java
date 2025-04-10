package server.data_access_layer.interfaces;

import shared.classi.User;

public interface UserRepositoryInterface {

	User getUserByMail(String mail);
	
	void register(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente);
	
	boolean updatePassword(String mail, String nuovaPassword);
	
	
	
}
