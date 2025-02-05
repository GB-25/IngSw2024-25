package GUI;

import javax.swing.*;
import java.awt.*;
import Class.Controller;

public class HomeAgente extends JFrame {

    public HomeAgente(Controller c) {
        // Imposta il titolo della finestra
        setTitle("DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Pannello superiore con logo e icone
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
        Image imgLogo = iconLogo.getImage();
        Image imgLogoScaled = imgLogo.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        ImageIcon finalLogoIcon = new ImageIcon(imgLogoScaled);
        JButton logoButton = new JButton(finalLogoIcon);
        logoButton.setBackground(new Color(255, 255, 255));
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setContentAreaFilled(false);

        logoButton.addActionListener(e -> {
            dispose(); // Chiude la schermata attuale
            new HomeAgente(c); // Torna alla home
        });

        // Menu a tendina per le notifiche
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Esempio di notifiche
        JMenuItem notifica1 = new JMenuItem("Nuova prenotazione per Appartamento Roma");
        JMenuItem notifica2 = new JMenuItem("Visita per Villa Roma confermata");
        JMenuItem notifica3 = new JMenuItem("Visita per Casa Tivoli confermata");

        popupMenu.add(notifica1);
        popupMenu.add(notifica2);
        popupMenu.add(notifica3);

        JButton bellButton = createIconButton("/immagini/bell.png", 30, 30);
        bellButton.addActionListener(e -> popupMenu.show(bellButton, 0, bellButton.getHeight()));

        // Menu a tendina per lo user
        JPopupMenu popupUser = new JPopupMenu();
        popupUser.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Nome e Cognome dell’utente
        JMenuItem userInfo = new JMenuItem("Mario Rossi"); // Modifica con il nome utente
        userInfo.setEnabled(false); // Non cliccabile

        // Gestione Account
        JMenuItem cambiaPassword = new JMenuItem("Cambia password");
        cambiaPassword.addActionListener(e -> {
        	dispose();
        	new CambioPassword(c);
        });

        JMenuItem creaAccount = new JMenuItem("Crea account da amministratore");
        creaAccount.addActionListener(e -> {
        	dispose();
    	    new CreazioneAccountAdmin(c);
    });

        // Logout
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose(); // Chiude la finestra attuale
                JOptionPane.showMessageDialog(this, "Logout effettuato!");
                System.exit(0);
            }
        });

        popupUser.add(userInfo);
        popupUser.add(new JSeparator()); // Separatore tra identità dell'admin ed opzioni
        popupUser.add(creaAccount);
        popupUser.add(cambiaPassword);
        popupUser.add(logout);

        ImageIcon userIcon = new ImageIcon(getClass().getResource("/immagini/user.png"));
        Image userImage = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon finalUserIcon = new ImageIcon(userImage);
        JButton userButton = new JButton(finalUserIcon);
        userButton.addActionListener(e -> popupUser.show(userButton, 0, userButton.getHeight()));
        userButton.setBackground(new Color(255, 255, 255));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setContentAreaFilled(false);


        // Pannello per icone in alto a destra
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(Color.WHITE);
        topRightPanel.add(bellButton);
        topRightPanel.add(userButton);

        // Aggiunta al topPanel
        topPanel.add(logoButton, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---- PANNELLO CENTRALE CON PULSANTI ----
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JButton addPropertyButton = new JButton("Inserisci un nuovo immobile sulla piattaforma");
        addPropertyButton.addActionListener(e -> {
            dispose(); // Chiude la schermata attuale
            new CaricamentoProprietaNuovo(); // Porta alla schermata di inserimento della proprietà
        });
        JButton viewRequestsButton = new JButton("Visualizza le richieste di appuntamento");
        JButton viewCalendarButton = new JButton("Visualizza il calendario con gli appuntamenti concordati");

        addPropertyButton.setPreferredSize(new Dimension(500, 120));
        viewRequestsButton.setPreferredSize(new Dimension(500, 120));
        viewCalendarButton.setPreferredSize(new Dimension(500, 120));

        // Aggiungi i pulsanti al GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Margini tra i pulsanti
        centerPanel.add(addPropertyButton, gbc);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 1;

        centerPanel.add(viewRequestsButton, gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(viewCalendarButton, gbc2);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Imposta la finestra visibile
        setVisible(true);
    }

    /**
     * Metodo per creare un pulsante con icona personalizzata
     */
    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));

        // Rende il pulsante trasparente
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    // Codice per far partire la finestra senza la necessità del controller
    // Ovviamente metteremo a posto appena abbiamo tutto a disposizione
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(HomeAgente::new);
    }
}
