package Controller;

import ViewGUI.*;
import model.ClientModel;

public class Controller {

	//frame
	
	FinestraLogin finestraPrincipale;
	ClientModel model;
	
	//costruttore
	public Controller() {
		finestraPrincipale = new FinestraLogin(this);
		finestraPrincipale.setVisible(true);
		//model = new ClietModel(ip, porta);
	}
	
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
