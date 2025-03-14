package ViewGUI;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import Class.Notifica;
import Class.User;
import Controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeAgente extends JFrame {
	
	private JFrame finestraLogin;
	private JFrame finestraCorrente = this;

    public HomeAgente(Controller c, User user) {
    	JButton bellButton;
    	
        FlatLightLaf.setup(new FlatLightLaf());
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
            c.createHomeAgente(finestraCorrente, user);
        });
     // Creazione del bottone della campanella
        JPopupMenu popupMenu = new JPopupMenu();
        bellButton = new JButton();
        bellButton.addActionListener(e -> {
        
           	System.out.println("Inizio creazione popup");
           	popupMenu.removeAll();

            popupMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));
                
            List<Notifica> notifiche = c.getNotificheUtente(user.getMail());
            System.out.println("Numero di notifiche: " + notifiche.size());
                
            if (notifiche.isEmpty()){
                JMenuItem dummy = new JMenuItem("Nessuna notifica");
                popupMenu.add(dummy);
            } else {
                List<Notifica> notificheCopy = new ArrayList<>(notifiche);
                for (Notifica notifica : notificheCopy) {
                    System.out.println("Aggiungo notifica: " + notifica.getMessaggio());
                    JMenuItem menuItem = new JMenuItem(notifica.getMessaggio());
                    menuItem.addActionListener(ae -> {
                        try {
                            System.out.println("Click su notifica: " + notifica.getMessaggio());
                            //c.rimuoviNotifica(user.getMail(), notifica);
                            popupMenu.remove(menuItem);
                            updateBellIcon(c, user, bellButton);
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                	});
                    popupMenu.add(menuItem);
                }
                }
            
        });
        updateBellIcon(c, user, bellButton); // 🔹 Imposta l'icona iniziale correttamente

        bellButton.addActionListener(e -> popupMenu.show(bellButton, 0, bellButton.getHeight()));


        // Menu a tendina per lo user
        JPopupMenu popupUser = new JPopupMenu();
        popupUser.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

        // Nome e Cognome dell’utente
        JMenuItem userInfo = new JMenuItem(user.getNome()+" "+user.getCognome()); // Modifica con il nome utente
        userInfo.setEnabled(false); // Non cliccabile

        JMenuItem creaAccount = new JMenuItem("Crea account da amministratore");
        creaAccount.addActionListener(e -> {
        	c.createAdmin(finestraCorrente, user);
    });

        // Logout
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                
                //JOptionPane.showMessageDialog(this, "Logout effettuato!");
            	c.returnLogin(finestraCorrente);
                
            }
        });

        popupUser.add(userInfo);
        popupUser.add(new JSeparator()); // Separatore tra identità dell'admin ed opzioni
        popupUser.add(creaAccount);
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
            c.createCaricamentoImmobile(finestraCorrente, user);
        });
        JButton changePasswordButton = new JButton("Cambia password di questo account");
        JButton viewCalendarButton = new JButton("Visualizza il calendario con gli appuntamenti concordati");

        addPropertyButton.setPreferredSize(new Dimension(500, 120));
        addPropertyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addPropertyButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        addPropertyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        addPropertyButton.setBackground(new Color(210, 224, 239));
        changePasswordButton.setPreferredSize(new Dimension(500, 120));
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        changePasswordButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        changePasswordButton.setBackground(new Color(210, 224, 239));
        changePasswordButton.addActionListener(e -> {
        	c.changePassword(finestraCorrente, user);
        });
        viewCalendarButton.setPreferredSize(new Dimension(500, 120));
        viewCalendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCalendarButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        viewCalendarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        viewCalendarButton.setBackground(new Color(210, 224, 239));
        viewCalendarButton.addActionListener(e -> {
        	c.viewCalendar(finestraCorrente, user);
    });
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
        
        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	changePasswordButton.setBackground(new Color(180, 224, 239));  // Colore più chiaro al passaggio
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	changePasswordButton.setBackground(new Color(210, 224, 239));  // Colore originale quando il mouse esce
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
        gbc1.gridy = 2;
        gbc1.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(changePasswordButton, gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
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
    
    private void updateBellIcon(Controller c, User user, JButton bellButton) {
        List<Notifica> notifiche = c.getNotificheUtente(user.getMail());
        String iconPath = notifiche.isEmpty() ? "/immagini/bellwhite.png" : "/immagini/whitebellnotifiche.png";

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        bellButton.setIcon(new ImageIcon(img));
    }
    

    // Codice per far partire la finestra senza la necessità del controller
    // Ovviamente metteremo a posto appena abbiamo tutto a disposizione
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(HomeAgente::new);
    }
}
