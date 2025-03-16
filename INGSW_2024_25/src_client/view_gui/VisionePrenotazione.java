package view_gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.Notifica;
import classi.Prenotazione;
import classi.User;
import controller.Controller;

import java.awt.*;
import java.util.List;

public class VisionePrenotazione extends JFrame {
    private static final long serialVersionUID = 1L;
	private JLabel titoloLabel;
    private JLabel clienteLabel;
    private JLabel dataLabel;
    private JLabel oraLabel;
    private JLabel posizioneLabel;
    private JLabel idCasaLabel;
    private JButton confermaButton;
    private JButton rifiutaButton;
    private JButton indietroButton;
    private JPanel topPanel;
    private JFrame finestraLogin;
	private JFrame finestraCorrente = this;
	private String fontScritte = "Microsoft YaHei UI Light";
    
    public VisionePrenotazione(User user, Prenotazione prenotazione, Controller c) {
        FlatLaf.setup(new FlatLightLaf());
        setTitle("Visione Prenotazione");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Usa BorderLayout per il pannello principale
        setContentPane(mainPanel);
        mainPanel.setBackground(Color.WHITE);

        // Creazione dei componenti per i dettagli della prenotazione
        titoloLabel = new JLabel("Dettagli Prenotazione", SwingConstants.CENTER);
        titoloLabel.setFont(new Font(fontScritte, Font.BOLD, 20));
        clienteLabel = new JLabel("Cliente: " + prenotazione.getUser().getNome()+prenotazione.getUser().getCognome(), SwingConstants.CENTER);
        clienteLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
        dataLabel = new JLabel("Data: " + prenotazione.getDataPrenotazione(), SwingConstants.CENTER);
        dataLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
        oraLabel = new JLabel("Ora: " + prenotazione.getOraPrenotazione(), SwingConstants.CENTER);
        oraLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
        posizioneLabel = new JLabel("Posizione: "+prenotazione.getImmobile().getIndirizzo());
        posizioneLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
        idCasaLabel = new JLabel("ID Prenotazione: "+prenotazione.getId());
        idCasaLabel.setFont(new Font(fontScritte, Font.BOLD, 20));
        
        topPanel = createTopPanel(user, c);

        // Pannello per i dettagli dell'immobile
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra i componenti
        
        indietroButton = new JButton("←");
        detailsPanel.add(indietroButton);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(titoloLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(idCasaLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(clienteLabel);
        detailsPanel.add(dataLabel);
        detailsPanel.add(oraLabel);
        detailsPanel.add(posizioneLabel);

        // Pannello per i pulsanti "Conferma" e "Annulla"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        rifiutaButton = new JButton("Rifiuta");
        rifiutaButton.setBackground(Color.RED);
        rifiutaButton.setForeground(Color.WHITE);
        confermaButton = new JButton("Conferma");
        confermaButton.setBackground(new Color(0, 153, 51));
        confermaButton.setForeground(Color.WHITE);

        indietroButton.addActionListener(e -> dispose());
        
        confermaButton.addActionListener(e -> {
            	int id = prenotazione.getId();
            	String mail = prenotazione.getAgente().getMail();
            	String data = prenotazione.getDataPrenotazione();
            	String ora= prenotazione.getOraPrenotazione();
            	if(c.reservationConfirm(id, mail, data, ora)) {
            		c.notifyCliente(prenotazione, true);
            		c.createHomeAgente(finestraCorrente, user);
            	}
            });
        
        
        rifiutaButton.addActionListener(e -> {
            	int id = prenotazione.getId();
            	c.reservationDeny(id);
            	c.notifyCliente(prenotazione, false);
            	c.createHomeAgente(finestraCorrente, user);});

        buttonPanel.add(rifiutaButton);
        buttonPanel.add(confermaButton);

        // Aggiungi tutto al frame
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel(User user, Controller c) {
        topPanel = new JPanel(new BorderLayout());
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
            	c.cambiaFinestra(finestraCorrente, finestraLogin);
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
        JButton bellButton = new JButton();
        bellButton.addActionListener(e -> bellButtonVisible(c, popupMenu, user, bellButton));
        updateBellIcon(c, user, bellButton);
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
    
    private void updateBellIcon(Controller c, User user, JButton bellButton) {
	    List<Notifica> notifiche = c.getNotificheUtente(user.getMail());
	    String iconPath = notifiche.isEmpty() ? "/immagini/bellwhite.png" : "/immagini/whitenotifiche.png";

	    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
	    Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	    bellButton.setIcon(new ImageIcon(img));
	}
    
    private void bellButtonVisible (Controller c, JPopupMenu popupMenu, User user, JButton bellButton){
		 if (popupMenu.isVisible()) {
            // 🔹 Se il popup è già visibile, chiudilo e aggiorna lo stato delle notifiche
            popupMenu.setVisible(false);
        } else {
            // 🔹 Se il popup non è visibile, aggiornalo e mostralo
            popupMenu.removeAll();
            popupMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

            List<Notifica> notifiche = c.getNotificheUtente(user.getMail());

            if (notifiche.isEmpty()) {
                JMenuItem dummy = new JMenuItem("Nessuna notifica");
                popupMenu.add(dummy);
            } else {
                for (Notifica notifica : notifiche) {
                    JMenuItem menuItem = new JMenuItem(notifica.getMessaggio());
                    menuItem.addActionListener(ae -> {
                        try {
                            c.setNotificaLetta(notifica);
                            popupMenu.remove(menuItem);
                            updateBellIcon(c, user, bellButton);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    popupMenu.add(menuItem);
                }
            }
            popupMenu.show(bellButton, 0, bellButton.getHeight());
        }
	 }
}