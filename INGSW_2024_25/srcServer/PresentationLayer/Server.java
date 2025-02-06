package PresentationLayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("✅ Server avviato sulla porta 12345...");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("🔗 Nuovo client connesso!");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

