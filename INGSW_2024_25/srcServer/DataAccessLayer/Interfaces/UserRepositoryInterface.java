package DataAccessLayer.Interfaces;

import Class.User;

public interface UserRepositoryInterface {

	User getUserByMail(String mail);
	
	void register(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente);
	
	void updatePassword(String mail, String nuovaPassword);
	
	
	
}
