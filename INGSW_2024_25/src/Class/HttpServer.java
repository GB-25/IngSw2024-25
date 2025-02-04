package Class;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        // Configura il server Jetty sulla porta 8080
        Server server = new Server(8080);

        // Crea un handler per le richieste
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Collega il controller esistente (come servlet)
        context.addServlet(new ServletHolder(new Controller()), "/");

        // Avvia il server
        server.start();
        System.out.println("Server avviato su http://localhost:8080");
        server.join();
    }
}
