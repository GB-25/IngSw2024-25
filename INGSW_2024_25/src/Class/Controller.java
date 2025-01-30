package Class;

import GUI.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller {

    FinestraLogin finestraPrincipale;

    public Controller() {
        finestraPrincipale = new FinestraLogin(this);
        finestraPrincipale.setVisible(true);
    }

    public String chiamaServer(String endpoint) {
        try {
            URL url = new URL("http://localhost:8080/api/" + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore nella connessione al server!";
        }
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }
}

