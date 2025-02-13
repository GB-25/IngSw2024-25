package ViewGUI;

import javax.swing.*;
import java.awt.*;
import Controller.Controller;

public class VisionePrenotazione extends JFrame {
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
    
    public VisionePrenotazione(String cliente, String data, String ora, Controller c) {
        setTitle("Visione Prenotazione");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Usa BorderLayout per il pannello principale
        setContentPane(mainPanel);
        mainPanel.setBackground(Color.WHITE);

        // Creazione dei componenti per i dettagli della prenotazione
        titoloLabel = new JLabel("Dettagli Prenotazione", SwingConstants.CENTER);
        titoloLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        clienteLabel = new JLabel("Cliente: " + cliente, SwingConstants.CENTER);
        clienteLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        dataLabel = new JLabel("Data: " + data, SwingConstants.CENTER);
        dataLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        oraLabel = new JLabel("Ora: " + ora, SwingConstants.CENTER);
        oraLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        posizioneLabel = new JLabel("Posizione: Via Andrea Costa, 13, Roma");
        posizioneLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        idCasaLabel = new JLabel("ID Casa: 19062021");
        idCasaLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        topPanel = createTopPanel();

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

        indietroButton.addActionListener(e -> {dispose();});
        confermaButton.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Prenotazione confermata!"); dispose();});
        rifiutaButton.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Prenotazione annullata!"); dispose();});

        buttonPanel.add(rifiutaButton);
        buttonPanel.add(confermaButton);

        // Aggiungi tutto al frame
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
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

        JMenuItem userInfo = new JMenuItem("Mario Rossi");
        userInfo.setEnabled(false);
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                JOptionPane.showMessageDialog(this, "Logout effettuato!");
                System.exit(0);
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
    
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new Runnable() {
            //@Override
            //public void run() {
                //new VisionePrenotazione("Mario Rossi", "12/02/2025", "15:30").setVisible(true);
            //}
        //});
    }
}
