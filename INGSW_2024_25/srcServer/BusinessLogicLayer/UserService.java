package BusinessLogicLayer;

import Class.User;
import DataAccessLayer.DatabaseManager;


public class UserService {
    private DatabaseManager dbManager;

    public UserService() {
        dbManager = new DatabaseManager();
    }

    public boolean authenticateUser(String mail, String password, boolean agente) {
    	 User user = dbManager.getUserByMail(mail);
    	 boolean loginSuccess;
         if (user != null) {
             loginSuccess= user.getPassword().equals(password);
             if (loginSuccess) {
            	 agente= user.getIsAgente();
            	 return true;
            }
         }
         return false;
     }
    
    public boolean registerUser(String nome, String cognome, String data, String mail, String telefono, String password) {
    	User user = dbManager.getUserByMail(mail);
    	
    	if (user == null) {
    		dbManager.register(nome, cognome, data, mail, telefono, password);
    		return true;
    	}
    	return false;
    }

    public void close() {
        dbManager.closeConnection();
    }
}
