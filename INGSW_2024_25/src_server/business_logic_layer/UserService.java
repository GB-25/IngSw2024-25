package business_logic_layer;

import java.util.Date;

import Class.User;
import data_access_layer.DatabaseManager;
import data_access_layer.interfaces.UserRepositoryInterface;


public class UserService {
  
    private UserRepositoryInterface userRepository;

    public UserService(UserRepositoryInterface userRepository) {

        this.userRepository = userRepository;
    }

    public User authenticateUser(String mail, String password) {
    	 User user = userRepository.getUserByMail(mail);
    	 boolean loginSuccess;
         if (user != null) {
             loginSuccess = user.getPassword().equals(password);
             if (loginSuccess) {
            	 return user;
            }
         }
         return null;
     }
    
    public boolean registerUser(String nome, String cognome, String data, String mail, String telefono, String password, boolean isAgente) {
    	User user = userRepository.getUserByMail(mail);
    	
    	if (user == null) {
    		userRepository.register(nome, cognome, data, mail, telefono, password, isAgente);
    		return true;
    	}
    	return false;
    }

    public void updatePassword(String mail, String nuovaPassword) {
    	userRepository.updatePassword(mail, nuovaPassword);
    }
    
    public User getUser(String mail) {
    	return userRepository.getUserByMail(mail);
    }
    
//    public void close() {
//        userRepository.closeConnection();
//    }
}
