package Controller;

import ViewGUI.*;
import model.ClientModel;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.json.JSONObject;

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
		finestraPrincipale = new FinestraLogin(this);
		finestraPrincipale.setVisible(true);
		//model = new ClientModel(ip, porta);
		//metodo del model per la connessione, in questo momento sarebbe sendMessage;
	}
	
	
	public void handleLogin (String mail, String password) {
		JSONObject response = model.loginModel(mail, password);
		if (response.getString("status").equals("error")) {
			 JOptionPane.showMessageDialog(null, "Credenziali errate!", "Errore di Login", JOptionPane.ERROR_MESSAGE);
		} else {
			
			finestraPrincipale.setVisible(false);
			
			if(response.getBoolean("agente")) {
				homeAgente= new HomeAgente(this);
				homeAgente.setVisible(true);
			} else {
				homeUtente = new FinestraHome(this);
				homeUtente.setVisible(true);
			}
		}
		
		
	}
	
	public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
	}
	
	public boolean isValidNome(String nome) {
		if(nome.length()<3) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
