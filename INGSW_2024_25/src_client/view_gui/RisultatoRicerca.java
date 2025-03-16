package view_gui;

import java.awt.*;




import java.util.List;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.Immobile;
import classi.User;

import javax.swing.*;
import org.jxmapviewer.JXMapViewer;

import controller.Controller;

public class RisultatoRicerca extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private JXMapViewer mapViewer;
    private JPanel mapPanel;


    public RisultatoRicerca(Controller c, User user, List<Immobile> ricerca, String indirizzo) throws Exception {
        FlatLaf.setup(new FlatLightLaf());

        // Configura il JFrame
        setTitle("Risultato Ricerca - DietiEstates25");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        
      
        c.getCoordinates(c, indirizzo , mapPanel, mapViewer, true, ricerca, user);
        
        // Mostra la finestra
        setVisible(true);
    }

}
