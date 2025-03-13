package ViewGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Base64;
import org.jxmapviewer.JXMapViewer;
import com.formdev.flatlaf.FlatLightLaf;
import Class.ComposizioneImmobile;
import Class.Immobile;
import Class.User;
import Controller.Controller;

public class VisioneImmobile extends JFrame {

    private JPanel imagePanel; // Pannello per l'immagine
    private CardLayout cardLayout; // Layout per il carosello
    private JButton prevButton, nextButton; // Navigation buttons
    private JFrame finestraLogin;
    private JFrame finestraCorrente;

    public VisioneImmobile(Controller c, Immobile immobile, User user) {
    	finestraCorrente=this;
        // Configurazione della finestra
    	FlatLightLaf.setup(new FlatLightLaf());
        setTitle("Visualizzazione Immobile - DietiEstates25");
        setSize(600, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrare la finestra
        setAlwaysOnTop(true);

        // **Pannello principale**
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // **Pannello superiore con titolo e pulsante "Indietro"**
        JPanel topPanel = createTopPanel(c, user);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // **Pannello centrale con immagini e dettagli**
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setBackground(Color.WHITE);

        // ---- PANNELLO SINISTRO: Immagine dell'immobile ----
        imagePanel = new JPanel();
        cardLayout = new CardLayout();
        imagePanel.setLayout(cardLayout);
        imagePanel.setBackground(Color.WHITE);

        // Immagini per il carosello
//        addImageToCarousel("/immagini/casa1.jpeg");
//        addImageToCarousel("/immagini/casa2.jpeg");
//        addImageToCarousel("/immagini/casa3.jpeg");
        String[] urlArray = c.getUrls(immobile);
        for(String url : urlArray) {
        	addImageToCarousel(c.fileDownload(url));
        }
        // Tasti per navigare tra le foto
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

        // ---- PANNELLO DESTRO: Dettagli dell'immobile ----
        JPanel detailsPanel = createDetailsPanel(c, immobile);

        // **Aggiunta dei pannelli principali**
        centerPanel.add(detailsPanel, BorderLayout.WEST);
        centerPanel.add(carouselPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // **Pulsanti Prenota Visita e Indietro**
        JPanel buttonPanel = createButtonPanel(c, immobile, user);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
       JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setPreferredSize(new Dimension (300, 320));
        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione"));
        mapPanel.setPreferredSize(new Dimension(200, 200));
        ArrayList<Immobile> immobileList = new ArrayList<>();
        immobileList.add(immobile);
        c.getCoordinates(c, immobile.getIndirizzo(), mapPanel, mapViewer, false, immobileList, user);
        carouselPanel.add(mapPanel, BorderLayout.SOUTH);
        


        setVisible(true);
    }

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
        logoButton.addActionListener(e -> dispose());

        JPopupMenu popupUser = new JPopupMenu();
        popupUser.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

        JMenuItem userInfo = new JMenuItem(user.getNome()+" "+user.getCognome());
        userInfo.setEnabled(false);
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
            	c.returnLogin(finestraCorrente);
            }
        });

        popupUser.add(userInfo);
        popupUser.add(new JSeparator());
        popupUser.add(logout);

        ImageIcon userIcon = new ImageIcon(getClass().getResource("/immagini/userwhite.png"));
        Image userImage = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton userButton = new JButton(new ImageIcon(userImage));
        userButton.addActionListener(e -> popupUser.show(userButton, 0, userButton.getHeight()));
        userButton.setBackground(new Color(40, 132, 212));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setContentAreaFilled(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

        JMenuItem notifica1 = new JMenuItem("Prenotazione confermata per Appartamento Roma");
        JMenuItem notifica2 = new JMenuItem("Prenotazione rifiutata per Casa Tivoli");
        JMenuItem notifica3 = new JMenuItem("Messaggio da Mario Rossi");

        popupMenu.add(notifica1);
        popupMenu.add(notifica2);
        popupMenu.add(notifica3);

        ImageIcon bellIcon = new ImageIcon(getClass().getResource("/immagini/bellwhite.png"));
        Image bellImage = bellIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton bellButton = new JButton(new ImageIcon(bellImage));
        bellButton.addActionListener(e -> popupMenu.show(bellButton, 0, bellButton.getHeight()));
        bellButton.setBackground(new Color(40, 132, 212));
        bellButton.setBorderPainted(false);
        bellButton.setFocusPainted(false);
        bellButton.setContentAreaFilled(false);
        bellButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        topRightPanel.setBackground(new Color(40, 132, 212));
        topRightPanel.add(bellButton);
        topRightPanel.add(userButton);

        topPanel.add(logoButton, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);
        topPanel.add(separator, BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel createDetailsPanel(Controller c, Immobile immobile) {
    	ComposizioneImmobile composizione = immobile.getComposizione();
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel priceLabel = new JLabel("Prezzo: "+immobile.getPrezzo());
        priceLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        priceLabel.setForeground(Color.BLACK);

        JLabel adLabel = new JLabel("Annuncio: "+immobile.getAnnuncio());
        JLabel surfaceLabel = new JLabel("Superficie: "+composizione.getQuadratura()+" m²");
        JLabel roomsLabel = new JLabel("Stanze: "+composizione.getNumeroStanze());
        JLabel energyLabel = new JLabel("Classe Energetica: "+immobile.getClasseEnergetica());
        JLabel condoLabel = new JLabel(immobile.getTipo());
        JLabel elevatorLabel = new JLabel("Ascensore: "+(composizione.isAscensore() ? "✓" : "✗"));
        JLabel gardenLabel = new JLabel("Giardino: "+(composizione.isGiardino() ? "✓" : "✗"));
        JLabel descriptionLabel = new JLabel("Descrizione: ");

        JTextArea descriptionArea = new JTextArea(immobile.getDescrizione());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
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
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(descriptionScroll);

        return detailsPanel;
    }

    private JPanel createButtonPanel(Controller c, Immobile immobile, User user) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton prenotaButton = new JButton("Prenota visita");
        prenotaButton.setBackground(new Color(0, 153, 76));
        prenotaButton.setForeground(Color.WHITE);
        prenotaButton.setFont(new Font("Helvetica", Font.BOLD, 13));
        prenotaButton.setFocusPainted(false);
        prenotaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prenotaButton.addActionListener(e -> {
        	c.makeReservationClient(finestraCorrente, immobile, user);
        });

        JButton indietroButton = new JButton("Indietro");
        indietroButton.setBackground(Color.GRAY);
        indietroButton.setForeground(Color.WHITE);
        indietroButton.setFont(new Font("Helvetica", Font.BOLD, 13));
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> dispose());

        buttonPanel.add(indietroButton);
        buttonPanel.add(prenotaButton);

        return buttonPanel;
    }

    private void addImageToCarousel(String imagePath) {
    	 try {
    	        // Decodifica la stringa Base64 in un array di byte
    	        byte[] imageBytes = Base64.getDecoder().decode(imagePath);

    	        // Crea un'icona direttamente dai byte
    	        ImageIcon icon = new ImageIcon(imageBytes);

    	        // Scala l'immagine per adattarla alle dimensioni desiderate
    	        Image image = icon.getImage().getScaledInstance(290, 200, Image.SCALE_SMOOTH);

    	        // Crea un JLabel con l'immagine e aggiungilo al pannello
    	        JLabel imageLabel = new JLabel(new ImageIcon(image));
    	        imagePanel.add(imageLabel);

    	        // Aggiorna la GUI per mostrare la nuova immagine
    	        imagePanel.revalidate();
    	        imagePanel.repaint();
    	    } catch (IllegalArgumentException e) {
    	        System.err.println("Errore nella decodifica dell'immagine Base64: " + e.getMessage());
    	    }
    }
    

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(VisioneImmobile::new);
    }
}
