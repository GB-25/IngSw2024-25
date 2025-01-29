package Class;


import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import GUI.*;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        // Configura il server Jetty sulla porta 8080
        Server server = new Server(8080);

        // Crea un handler per le richieste
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Controller()), "/api");

        server.setHandler(context);
        server.start();
        System.out.println("Server avviato su http://localhost:8080");
        server.join();
    }
}
