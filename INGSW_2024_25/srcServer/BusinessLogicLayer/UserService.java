package BusinessLogicLayer;

import Class.User;
import DataAccessLayer.DatabaseManager;


public class UserService {
    private DatabaseManager dbManager;

    public UserService() {
        dbManager = new DatabaseManager();
    }

    public boolean authenticateUser(String username, String password) {
    	 User user = dbManager.getUserByUsername(username);

         if (user != null) {
             return user.getPassword().equals(password);
         }
         return false;
     }

    public void close() {
        dbManager.closeConnection();
    }
}
