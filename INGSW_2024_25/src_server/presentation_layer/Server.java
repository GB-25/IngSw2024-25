package presentation_layer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    //logger
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            logger.log(Level.INFO, "✅ Server avviato sulla porta 12345...");

           //shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> 
                logger.log(Level.INFO, "🛑 Server in arresto...")
            ));

            
            boolean running = true;
            while (running) {
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "🔗 Nuovo client connesso!");
                new ClientHandler(socket).start();

                //end condition
                if (Thread.activeCount() > 10) {
                    logger.log(Level.INFO, "🛑 Limite massimo di client raggiunto. Arresto del server...");
                    running = false;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "❌ Errore nel server: ", e);
        }
    }
}