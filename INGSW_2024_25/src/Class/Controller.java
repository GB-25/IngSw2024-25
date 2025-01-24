package Class;

//import 
import GUI.*;

public class Controller {

	//frame
	
	FinestraLogin finestraPrincipale;
	//connessione al db
	//private final static String url 
	//private final static String user
	//private final static String password
	
	//costruttore
	public Controller() {
		finestraPrincipale = new FinestraLogin(this);
		finestraPrincipale.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
