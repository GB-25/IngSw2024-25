package Class;

//import 
import GUI.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

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
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Imposta il contenuto della risposta come JSON
        resp.setContentType("application/json");

        // Scrivi la risposta (esempio: lista immobili)
        resp.getWriter().println("{\"immobili\": [{\"titolo\": \"Appartamento\", \"prezzo\": 120000}]}");
    }
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
