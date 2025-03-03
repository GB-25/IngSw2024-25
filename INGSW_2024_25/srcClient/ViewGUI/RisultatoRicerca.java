package ViewGUI;

import java.awt.*;
import java.util.ArrayList;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import org.jxmapviewer.JXMapViewer;

import Class.Immobile;
import Class.User;
import Controller.Controller;

public class RisultatoRicerca extends JFrame {
    private JPanel mainPanel;
    private JXMapViewer mapViewer;
    private JPanel mapPanel;

    public RisultatoRicerca(Controller c, User user, ArrayList<Immobile> ricerca, String posizione) {
        FlatLightLaf.install();
        // Configura il JFrame
        setTitle("Risultato Ricerca - DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null); // Centra la finestra

        // Crea il pannello principale
        mainPanel = new JPanel(new BorderLayout()); // Usa BorderLayout
        mainPanel.setPreferredSize(new Dimension(600, 600));
        mainPanel.setBackground(Color.WHITE);

        // Crea il pannello per la mappa
        mapPanel = new JPanel(new BorderLayout()); // Usa BorderLayout
        mapPanel.setPreferredSize(new Dimension(600, 500));

        // Crea il JXMapViewer
        mapViewer = new JXMapViewer();
        mapViewer.setPreferredSize(new Dimension(600, 500)); // Imposta le dimensioni

        // Aggiungi il JXMapViewer al mapPanel
        mapPanel.add(mapViewer, BorderLayout.CENTER);

        // Aggiungi il mapPanel al mainPanel
        mainPanel.add(mapPanel, BorderLayout.CENTER);

        // Imposta il contenuto del JFrame
        setContentPane(mainPanel);

        // Ottieni le coordinate e visualizza la mappa
        c.getCoordinates(c, posizione , mapPanel, mapViewer, true);

        // Mostra la finestra
        setVisible(true);
    }

    public static void main(String[] args) {
    }
}
