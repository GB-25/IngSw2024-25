package Controller;

import ViewGUI.*;
import model.ClientModel;

public class Controller {

	//frame
	
	FinestraLogin finestraPrincipale;
	ClientModel model;
	//String ip = "ip server vm";
	//int porta = porta server vm
	
	//costruttore
	public Controller() {
		finestraPrincipale = new FinestraLogin(this);
		finestraPrincipale.setVisible(true);
		//model = new ClientModel(ip, porta);
		//metodo del model per la connessione, in questo momento sarebbe sendMessage;
	}
	
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
