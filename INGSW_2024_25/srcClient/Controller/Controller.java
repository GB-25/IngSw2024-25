package Controller;

import ViewGUI.*;
import model.ClientModel;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import Class.User;

public class Controller {

	//frame
	
	FinestraLogin finestraPrincipale;
	ClientModel model;
	FinestraHome homeUtente;
	HomeAgente homeAgente;
	FinestraRegistrazione finestraRegistrazione;
	String ip = "34.78.163.251";
	int porta = 12345;
	
	//costruttore
	public Controller() {
		User user = new User("", "", "", "", "", "", true);
		homeAgente = new HomeAgente(this, user);
		homeAgente.setVisible(true);
		//model = new ClientModel(ip, porta);
		//metodo del model per la connessione, in questo momento sarebbe sendMessage;
	}
	
	
	public void handleLogin (String mail, char[] pass) {
		String password = new String(pass);
		if (mail == null || password == null) {
			JOptionPane.showMessageDialog(null, "Almeno uno dei campi è vuoto", "Errore di Login", JOptionPane.ERROR_MESSAGE);
		}else {
			JSONObject response = model.loginModel(mail, password);
			if (response.getString("status").equals("error")) {
				JOptionPane.showMessageDialog(null, "Credenziali errate!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
			} else {
				
				finestraPrincipale.setVisible(false);
				String nome = response.getString("nome");
				String cognome = response.getString("cognome");
				String dataNascita = response.getString("dataNascita");
				String telefono = response.getString("telefono");
				Boolean isAgente = response.getBoolean("isAgente");
				
				User user = new User(mail, password, nome, cognome, telefono, dataNascita, isAgente );
				if(user.getIsAgente()) {
					
					homeAgente= new HomeAgente(this, user);
					homeAgente.setVisible(true);
				} else {
					homeUtente = new FinestraHome(this);
					homeUtente.setVisible(true);
				}
			}
		}
		
	}
	
	public void handleRegistration (String nome, String cognome, String data, String mail, String telefono, char[] pass, boolean isAgente) {
		String password = new String(pass);
		JSONObject response = model.registerModel(nome, cognome, data, mail, telefono, password, isAgente);
		if (response.getString("status").equals("error")) {
			 JOptionPane.showMessageDialog(null, "Utente già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			finestraRegistrazione.setVisible(false);
			homeUtente = new FinestraHome(this);
			homeUtente.setVisible(true);
		}
	}
	
	public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return ((email != null) && (email.matches(emailRegex)));
	}
	
	public boolean isValidNome(String nome) {
		if(nome.length()<3) {
			return false;
		}
		return true;
	}
	
	public void isValidPassword(char[] pass, boolean[] valori) {
		
		String password = new String(pass);
		
		if (password.length()<6) {
			valori[0] = false;
		} else {
			valori[0] = true;
		}
		if (password.matches("^.*[A-Z].*")) {
			valori[1] = true;
		} else {
			valori[1] = false;
		}
		if (password.matches("^.*[a-z].*")) {
			valori[2] = true;
		} else {
			valori[2] = false;
		}
		if (password.matches("^.*[0-9].*")) {
			valori[3] = true;
		} else {
			valori[3] = false;
		}
	}
	
	public boolean isValidNumero(String numero) {
		if (numero.matches("^\\d{9,10}$")) {
			return true;
		}
		return false;
	}
	
	public boolean verifyPassword(char[] password1, char[] password2) {
		return Arrays.equals(password1, password2);
	}
	
	public boolean checkFields(boolean[] controllo) {
		for (boolean value : controllo) {
	        if (!value) { // Se almeno uno è false, restituisci false
	            return false;
	        }
	    }
	    return true;
	}
	
	
	public void cambiaFinestra(JFrame vecchiaFinestra, JFrame nuovaFinestra) {
		vecchiaFinestra.setVisible(false);
		nuovaFinestra.setVisible(true);
	}
	
	public void updatePassword(User user, char[] pass) {
		String password = new String(pass);
		String mail = user.getMail();
		model.newPasswordModel(mail, password);
		
	}
	
	public String handleFileUpload(String filePath) {
	    JSONObject response = model.uploadFileModel(filePath);

	    if (response.getString("status").equals("success")) {
	        String fileUrl = response.getString("fileUrl"); // URL del file caricato
	        System.out.println("File caricato con successo: " + fileUrl);
	        return fileUrl;  // Restituisce l'URL del file per essere salvato nel database
	    } else {
	        System.err.println("Errore durante l'upload: " + response.getString("message"));
	        return null;
	    }
	}
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
