package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientModel {
    private String serverIP;
    private int port;

    public ClientModel(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
    }

    public String sendMessage(String message) {
        try (Socket socket = new Socket(serverIP, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(message);
            return in.readLine(); // Riceve risposta dal server

        } catch (Exception e) {
            e.printStackTrace();
            return "Errore di connessione!";
        }
    }
}
