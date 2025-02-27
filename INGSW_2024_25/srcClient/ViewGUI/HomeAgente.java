package ViewGUI;

import javax.swing.*;

import Class.User;
import Controller.Controller;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeAgente extends JFrame {
	
	private FinestraLogin finestraLogin;
	private JFrame finestraCorrente = this;

    public HomeAgente(Controller c, User user) {
    	List<Runnable> notifiche = c.getNotificheUtente(user.getMail());

        // Imposta il titolo della finestra
        setTitle("DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 212, 255));
        setContentPane(mainPanel);    
        
     // Creazione di un separatore orizzontale
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.BLACK);  // Imposta il colore della linea
        separator.setPreferredSize(new Dimension(600, 2));  // Lunghezza e spessore della linea


        // Pannello superiore con logo e icone
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(600, 120));
        topPanel.setBackground(new Color(40, 132, 212));

        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
        Image imgLogo = iconLogo.getImage();
        Image imgLogoScaled = imgLogo.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        ImageIcon finalLogoIcon = new ImageIcon(imgLogoScaled);
        JButton logoButton = new JButton(finalLogoIcon);
        logoButton.setBackground(new Color(255, 255, 255));
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logoButton.addActionListener(e -> {
            dispose(); // Chiude la schermata attuale
            new HomeAgente(c, user); // Torna alla home
        });

        // Menu a tendina per le notifiche
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

        // Esempio di notifiche
        for (Runnable notifica : notifiche) {
            JMenuItem menuItem = new JMenuItem("Notifica");
            menuItem.addActionListener(e -> {
            	notifica.run(); 
            	popupMenu.remove(this);
            	notifiche.remove(notifica);
            }); // Esegui l'azione associata alla notifica
            popupMenu.add(menuItem);
        }

        JButton bellButton = createIconButton("/immagini/bellwhite.png", 30, 30);
        bellButton.addActionListener(e -> popupMenu.show(bellButton, 0, bellButton.getHeight()));

        // Menu a tendina per lo user
        JPopupMenu popupUser = new JPopupMenu();
        popupUser.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

        // Nome e Cognome dell’utente
        JMenuItem userInfo = new JMenuItem(user.getNome()+user.getCognome()); // Modifica con il nome utente
        userInfo.setEnabled(false); // Non cliccabile

        // Gestione Account
        JMenuItem cambiaPassword = new JMenuItem("Cambia password");
        cambiaPassword.addActionListener(e -> {
        	dispose();
        	new CambioPassword(c, user);
        });

        JMenuItem creaAccount = new JMenuItem("Crea account da amministratore");
        creaAccount.addActionListener(e -> {
        	dispose();
    	    new CreazioneAccountAdmin(c,user);
    });

        // Logout
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose(); // Chiude la finestra attuale
                JOptionPane.showMessageDialog(this, "Logout effettuato!");
                finestraLogin= new FinestraLogin(c);
                c.cambiaFinestra(finestraCorrente, finestraLogin);
                
            }
        });

        popupUser.add(userInfo);
        popupUser.add(new JSeparator()); // Separatore tra identità dell'admin ed opzioni
        popupUser.add(creaAccount);
        popupUser.add(cambiaPassword);
        popupUser.add(logout);

        ImageIcon userIcon = new ImageIcon(getClass().getResource("/immagini/userwhite.png"));
        Image userImage = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon finalUserIcon = new ImageIcon(userImage);
        JButton userButton = new JButton(finalUserIcon);
        userButton.addActionListener(e -> popupUser.show(userButton, 0, userButton.getHeight()));
        userButton.setBackground(new Color(40, 132, 212));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setContentAreaFilled(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        // Pannello per icone in alto a destra
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        topRightPanel.setBackground(new Color(40, 132, 212));
        topRightPanel.add(bellButton);
        topRightPanel.add(userButton);

        // Aggiunta al topPanel
        topPanel.add(logoButton, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);
        topPanel.add(separator, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---- PANNELLO CENTRALE CON PULSANTI ----
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JButton addPropertyButton = new JButton("Inserisci un nuovo immobile sulla piattaforma");
        addPropertyButton.addActionListener(e -> {
            dispose(); // Chiude la schermata attuale
            new CaricamentoProprietaNuovo(c, user); // Porta alla schermata di inserimento della proprietà
        });
        JButton viewRequestsButton = new JButton("Visualizza le richieste di appuntamento");
        JButton viewCalendarButton = new JButton("Visualizza il calendario con gli appuntamenti concordati");

        addPropertyButton.setPreferredSize(new Dimension(500, 120));
        addPropertyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addPropertyButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        addPropertyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        addPropertyButton.setBackground(new Color(210, 224, 239));
        viewRequestsButton.setPreferredSize(new Dimension(500, 120));
        viewRequestsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewRequestsButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        viewRequestsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        viewRequestsButton.setBackground(new Color(210, 224, 239));
        viewCalendarButton.setPreferredSize(new Dimension(500, 120));
        viewCalendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCalendarButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        viewCalendarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        viewCalendarButton.setBackground(new Color(210, 224, 239));
        
        addPropertyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addPropertyButton.setBackground(new Color(180, 224, 239));  // Colore più chiaro al passaggio
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addPropertyButton.setBackground(new Color(210, 224, 239));  // Colore originale quando il mouse esce
            }
        });
        
        viewRequestsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	viewRequestsButton.setBackground(new Color(180, 224, 239));  // Colore più chiaro al passaggio
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	viewRequestsButton.setBackground(new Color(210, 224, 239));  // Colore originale quando il mouse esce
            }
        });
        
        viewCalendarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	viewCalendarButton.setBackground(new Color(180, 224, 239));  // Colore più chiaro al passaggio
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	viewCalendarButton.setBackground(new Color(210, 224, 239));  // Colore originale quando il mouse esce
            }
        });


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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
        
        
    }
    
    

    // Codice per far partire la finestra senza la necessità del controller
    // Ovviamente metteremo a posto appena abbiamo tutto a disposizione
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(HomeAgente::new);
    }
}
