package BusinessLogicLayer;

import Class.User;
import DataAccessLayer.DatabaseManager;


public class UserService {
    private DatabaseManager dbManager;

    public UserService() {
        dbManager = new DatabaseManager();
    }

    public boolean authenticateUser(String mail, String password, boolean agente, String nome, String cognome) {
    	 User user = dbManager.getUserByMail(mail);
    	 boolean loginSuccess;
         if (user != null) {
             loginSuccess= user.getPassword().equals(password);
             if (loginSuccess) {
            	 nome= user.getNome();
            	 cognome= user.getCognome();
            	 agente= user.getIsAgente();
            	 return true;
            }
         }
         return false;
     }
    
    public boolean registerUser(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	User user = dbManager.getUserByMail(mail);
    	
    	if (user == null) {
    		dbManager.register(nome, cognome, data, mail, telefono, password, isAgente);
    		return true;
    	}
    	return false;
    }

    public void close() {
        dbManager.closeConnection();
    }
}
