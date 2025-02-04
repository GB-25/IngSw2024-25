package Class;

//import 
import GUI.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

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
	
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        resp.setContentType("application/json");
	        resp.setStatus(HttpServletResponse.SC_OK);

	        JSONObject json = new JSONObject();
	        json.put("message", "Ciao dal server Java!");
	        
	        PrintWriter out = resp.getWriter();
	        out.print(json.toString());
	        out.flush();
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String body = req.getReader().lines().collect(Collectors.joining());
	        JSONObject jsonRequest = new JSONObject(body);

	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("received", jsonRequest);

	        resp.setContentType("application/json");
	        resp.setStatus(HttpServletResponse.SC_OK);
	        PrintWriter out = resp.getWriter();
	        out.print(jsonResponse.toString());
	        out.flush();
	    }
	
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		
	}
}
