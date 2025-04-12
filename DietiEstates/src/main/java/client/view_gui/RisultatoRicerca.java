package client.view_gui;

import java.awt.*;
import java.net.URISyntaxException;

import com.formdev.flatlaf.FlatLaf;

import java.util.List;


import com.formdev.flatlaf.FlatLightLaf;

import shared.classi.Immobile;
import shared.classi.User;

import javax.swing.*;
import org.jxmapviewer.JXMapViewer;

import client.controller.Controller;
import client.eccezioni.GeocodingException;

public class RisultatoRicerca extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private JXMapViewer mapViewer;
    private JPanel mapPanel;
    private JFrame finestraCorrente;
    private JLabel lblNewLabel;

    /**
     * Costruttore
     * 
     * @throws GeocodingException
     */
    public RisultatoRicerca(Controller c, User user, List<Immobile> ricerca, String indirizzo) throws GeocodingException  {
        FlatLaf.setup(new FlatLightLaf());

        finestraCorrente = this;
        setTitle("Risultato Ricerca - DietiEstates25");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null); 
        setResizable(false);
        JPanel indietroPanel = new JPanel(new BorderLayout());
        indietroPanel.setBackground(new Color(40, 132, 212));
        JButton indietroButton = new JButton("←");
        indietroButton.setBorderPainted(false);
        indietroButton.setForeground(new Color(255, 255, 255));
        indietroButton.setHorizontalTextPosition(SwingConstants.LEFT);
        indietroButton.setBackground(new Color(40, 132, 212));
        indietroButton.setFont(new Font("Helvetica", Font.BOLD, 13));
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
        	dispose();
            c.findImmobili(finestraCorrente, user);
        });
        indietroPanel.add(indietroButton, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setPreferredSize(new Dimension(600, 600));
        mainPanel.setBackground(Color.WHITE);

        mapPanel = new JPanel(new BorderLayout()); 
        mapPanel.setPreferredSize(new Dimension(600, 500));

        mapViewer = new JXMapViewer();
        mapViewer.setPreferredSize(new Dimension(600, 500)); 

        mapPanel.add(mapViewer, BorderLayout.CENTER);

        mainPanel.add(indietroPanel, BorderLayout.NORTH);
        
        lblNewLabel = new JLabel("                         ");
        indietroPanel.add(lblNewLabel, BorderLayout.EAST);
        mainPanel.add(mapPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        
      try {
        c.getCoordinates(c, indirizzo , mapPanel, mapViewer, ricerca, user, finestraCorrente);
      } catch (URISyntaxException exception) {
    	  JOptionPane.showMessageDialog(null, "Errore nel sistema! Ci scusiamo per il disagio","Errore", JOptionPane.ERROR_MESSAGE);
      }
        
        setVisible(true);
    }
}
