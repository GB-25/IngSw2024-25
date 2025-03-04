package BusinessLogicLayer;

import java.util.Date;

import Class.User;
import DataAccessLayer.DatabaseManager;
import DataAccessLayer.Interfaces.UserRepositoryInterface;


public class UserService {
  
    private UserRepositoryInterface userRepository;

    public UserService(UserRepositoryInterface userRepository) {

        this.userRepository = userRepository;
    }

    public boolean authenticateUser(String mail, String password, boolean agente, String nome, String cognome, String telefono, String dataNascita) {
    	 User user = userRepository.getUserByMail(mail);
    	 boolean loginSuccess;
         if (user != null) {
             loginSuccess= user.getPassword().equals(password);
             if (loginSuccess) {
            	 nome= user.getNome();
            	 cognome= user.getCognome();
            	 agente= user.getIsAgente();
            	 telefono = user.getNumeroTelefono();
            	 dataNascita = user.getDataNascita();
            	 return true;
            }
         }
         return false;
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
