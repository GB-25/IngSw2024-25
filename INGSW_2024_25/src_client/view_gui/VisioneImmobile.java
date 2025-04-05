package view_gui;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Logger;

import org.jxmapviewer.JXMapViewer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.User;
import controller.Controller;
import eccezioni.GeocodingException;

public class VisioneImmobile extends JFrame {

    private static final long serialVersionUID = 1L;
	private JPanel imagePanel; 
    private CardLayout cardLayout; 
    private JButton prevButton; 
    private JButton nextButton;
    private JFrame finestraCorrente;
    private String fontScritte = "Helvetica";
    transient Logger logger = Logger.getLogger(getClass().getName());

    public VisioneImmobile(Controller c, Immobile immobile, User user, JFrame finestra) throws GeocodingException, URISyntaxException {
    	finestraCorrente=this;

    	FlatLaf.setup(new FlatLightLaf());
        setTitle("Visualizzazione Immobile - DietiEstates25");
        setSize(600, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setAlwaysOnTop(true);
        setResizable(false);
 
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        JPanel topPanel = createTopPanel(c, user);
        mainPanel.add(topPanel, BorderLayout.NORTH);
 
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setBackground(Color.WHITE);

        imagePanel = new JPanel();
        cardLayout = new CardLayout();
        imagePanel.setLayout(cardLayout);
        imagePanel.setBackground(Color.WHITE);

        String[] urlArray = c.getUrls(immobile);
        for(String url : urlArray) {
        	addImageToCarousel(c.fileDownload(url));
        }

        JPanel carouselControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        prevButton = new JButton("←");
        nextButton = new JButton("→");

        prevButton.addActionListener(e -> cardLayout.previous(imagePanel));
        nextButton.addActionListener(e -> cardLayout.next(imagePanel));

        carouselControlPanel.add(prevButton);
        carouselControlPanel.add(nextButton);

        JPanel carouselPanel = new JPanel(new BorderLayout());
        carouselPanel.setBackground(Color.WHITE);
        carouselPanel.add(imagePanel, BorderLayout.NORTH);
        carouselPanel.add(carouselControlPanel, BorderLayout.CENTER);


        JPanel detailsPanel = createDetailsPanel(immobile);


        centerPanel.add(detailsPanel, BorderLayout.WEST);
        centerPanel.add(carouselPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        JPanel buttonPanel = createButtonPanel(c, immobile, user, finestra);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
       JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setPreferredSize(new Dimension (300, 320));
        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione"));
        mapPanel.setPreferredSize(new Dimension(200, 200));
        ArrayList<Immobile> immobileList = new ArrayList<>();
        immobileList.add(immobile);
        c.getCoordinates(c, immobile.getImmobileDettagli().getIndirizzo(), mapPanel, mapViewer, immobileList, user, null);
        mapPanel.setEnabled(false); 
        carouselPanel.add(mapPanel, BorderLayout.SOUTH);

        

        setVisible(true);
    }

    /**
     * Creazione del JPanel, così come i metodi successivi
     * @param c
     * @param user
     * @return JPanel
     */
    private JPanel createTopPanel(Controller c, User user) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(40, 132, 212));
        topPanel.setPreferredSize(new Dimension(600, 120));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.BLACK);
        separator.setPreferredSize(new Dimension(600, 2));

        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
        Image imgLogo = iconLogo.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        JButton logoButton = new JButton(new ImageIcon(imgLogo));
        logoButton.setBackground(Color.WHITE);
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoButton.addActionListener(e -> { dispose(); new HomeGenerale(c, user);});

        topPanel.add(logoButton, BorderLayout.WEST);
        topPanel.add(separator, BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel createDetailsPanel(Immobile immobile) {
    	ComposizioneImmobile composizione = immobile.getComposizione();
    	ComposizioneImmobile composizioneBoolean = composizione.getComposizione();
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel priceLabel = new JLabel("Prezzo: "+immobile.getPrezzo());
        priceLabel.setFont(new Font(fontScritte, Font.BOLD, 20));
        priceLabel.setForeground(Color.BLACK);

        JLabel adLabel = new JLabel("Annuncio: "+immobile.getImmobileDettagli().getAnnuncio());
        JLabel surfaceLabel = new JLabel("Superficie: "+composizione.getQuadratura()+" m²");
        JLabel roomsLabel = new JLabel("Stanze: "+composizione.getNumeroStanze());
        JLabel energyLabel = new JLabel("Classe Energetica: "+immobile.getImmobileDettagli().getClasseEnergetica());
        JLabel condoLabel = new JLabel(immobile.getImmobileDettagli().getTipo());
        JLabel elevatorLabel = new JLabel("Ascensore: "+(composizioneBoolean.isAscensore() ? "✓" : "✗"));
        JLabel gardenLabel = new JLabel("Giardino: "+(composizioneBoolean.isGiardino() ? "✓" : "✗"));
        JLabel balconyLabel = new JLabel("Terrazzo: "+(composizioneBoolean.isTerrazzo() ? "✓" : "✗"));
        JLabel complexLabel = new JLabel("Condominio: "+(composizioneBoolean.isCondominio() ? "✓" : "✗"));
        JLabel descriptionLabel = new JLabel("Descrizione: ");

        JTextArea descriptionArea = new JTextArea(immobile.getDescrizione());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setFont(new Font(fontScritte, Font.PLAIN, 14));
        descriptionArea.setBorder(null);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(null);
        descriptionScroll.setPreferredSize(new Dimension(200, 100));

        detailsPanel.add(priceLabel, BorderLayout.WEST);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(adLabel);
        detailsPanel.add(surfaceLabel);
        detailsPanel.add(roomsLabel);
        detailsPanel.add(energyLabel);
        detailsPanel.add(condoLabel);
        detailsPanel.add(elevatorLabel);
        detailsPanel.add(gardenLabel);
        detailsPanel.add(balconyLabel);
        detailsPanel.add(complexLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(descriptionScroll);

        return detailsPanel;
    }

    private JPanel createButtonPanel(Controller c, Immobile immobile, User user, JFrame finestra) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton prenotaButton = new JButton("Prenota visita");
        prenotaButton.setBackground(new Color(0, 153, 76));
        prenotaButton.setForeground(Color.WHITE);
        prenotaButton.setFont(new Font(fontScritte, Font.BOLD, 13));
        prenotaButton.setFocusPainted(false);
        prenotaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prenotaButton.addActionListener(e -> {
        	if (finestra != null) {
        	finestra.dispose();}
        	c.makeReservationClient(finestraCorrente, immobile, user);});

        JButton indietroButton = new JButton("Indietro");
        indietroButton.setBackground(Color.GRAY);
        indietroButton.setForeground(Color.WHITE);
        indietroButton.setFont(new Font(fontScritte, Font.BOLD, 13));
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
        	dispose();
        	if (finestra == null) {
            c.findImmobili(finestraCorrente, user);}
        });

        buttonPanel.add(indietroButton);
        buttonPanel.add(prenotaButton);

        return buttonPanel;
    }
    /**
     * Metodo per aggiungere le immaggini al carosello
     * @param imagePath
     */
    private void addImageToCarousel(String imagePath) {
    	 try {
    	        byte[] imageBytes = Base64.getDecoder().decode(imagePath);

    	        ImageIcon icon = new ImageIcon(imageBytes);

    	        Image image = icon.getImage().getScaledInstance(290, 200, Image.SCALE_SMOOTH);

    	        JLabel imageLabel = new JLabel(new ImageIcon(image));
    	        imagePanel.add(imageLabel);

    	        imagePanel.revalidate();
    	        imagePanel.repaint();
    	    } catch (IllegalArgumentException e) {
    	        logger.info("Errore nella decodifica dell'immagine Base64: " + e.getMessage());
    	    }
    }   
}
