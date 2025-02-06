package PresentationLayer;

import java.io.*;
import java.net.*;
import BusinessLogicLayer.UserService;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.userService = new UserService();
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                String[] parts = message.split(" ");
                if (parts[0].equalsIgnoreCase("LOGIN")) {
                    boolean success = userService.authenticateUser(parts[1], parts[2]);
                    out.println(success ? "LOGIN_OK" : "LOGIN_FAILED");
                } else {
                    out.println("Comando sconosciuto!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                userService.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}