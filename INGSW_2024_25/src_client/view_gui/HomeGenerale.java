package view_gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.Notifica;
import classi.User;
import controller.Controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;

public class HomeGenerale extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JFrame finestraCorrente;
	private SchermataCaricamento schermataCaricamento;
	private String fontScritte = "Microsoft YaHei UI Light";
	private static final String CARICAMENTO = "Caricamento";
	ImageIcon bellIcon;
	private transient Logger logger = Logger.getLogger(getClass().getName());
	/**
	 * 
	 * Costruttore
	 */
	public HomeGenerale(Controller c, User user) {
		
		finestraCorrente = this;
		FlatLaf.setup(new FlatLightLaf());
		setTitle("Home Cliente - DietiEstates25");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); 
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);
        
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        topPanel = createTopPanel(c, user);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JButton searchButton;
        if (!user.getIsAgente()) {
            searchButton = new JButton("Ricerca immobile");
            searchButton.addActionListener(e -> {
        		schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, CARICAMENTO);
        	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        	        @Override
        	        protected Void doInBackground() throws Exception {
        	        	c.findImmobili(finestraCorrente, user);
        	           return null;}
        	        @Override
        	        protected void done() {
        	        	schermataCaricamento.close();}
        	        };
        	           worker.execute();});}
        else {
        	searchButton = new JButton("Inserisci un nuovo immobile sulla piattaforma");
        	searchButton.addActionListener(e -> {
        		schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, CARICAMENTO);
        	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        	        @Override
        	        protected Void doInBackground() throws Exception {
        	           c.createCaricamentoImmobile(finestraCorrente, user);
        	           return null;}
        	        @Override
        	        protected void done() {
        	        	schermataCaricamento.close();}
        	        };
        	           worker.execute();});}
        JButton viewCalendarButton = new JButton("Visualizza il calendario con gli appuntamenti");
        JButton changePasswordButton = new JButton("Cambia password di questo account");

        searchButton.setPreferredSize(new Dimension(500, 120));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setFont(new Font(fontScritte, Font.BOLD, 18));
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        searchButton.setBackground(new Color(210, 224, 239));
        viewCalendarButton.setPreferredSize(new Dimension(500, 120));
        viewCalendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCalendarButton.setFont(new Font(fontScritte, Font.BOLD, 18));
        viewCalendarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        viewCalendarButton.setBackground(new Color(210, 224, 239));
        changePasswordButton.setPreferredSize(new Dimension(500, 120));
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordButton.setFont(new Font(fontScritte, Font.BOLD, 18));
        changePasswordButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        changePasswordButton.setBackground(new Color(210, 224, 239));
        changePasswordButton.addActionListener(e -> c.changePassword(finestraCorrente, user));
        
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	searchButton.setBackground(new Color(180, 224, 239));  // Colore più chiaro al passaggio
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	searchButton.setBackground(new Color(210, 224, 239));  // Colore originale quando il mouse esce
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
        viewCalendarButton.addActionListener(e -> c.viewCalendar(finestraCorrente, user));
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(searchButton, gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(viewCalendarButton, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 3;
        gbc3.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(changePasswordButton, gbc3);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
	}
	/**
	 * 
	 * @param c
	 * @param user
	 * @return JPanel da caricare sulla finestra
	 */
	 private JPanel createTopPanel(Controller c, User user) {
		 	
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
	        logoButton.addActionListener(e -> { dispose(); new HomeGenerale(c, user);});

	        JPopupMenu popupUser = new JPopupMenu();
	        popupUser.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

	        JMenuItem userInfo = new JMenuItem(user.getNome()+" "+user.getCognome());
	        userInfo.setEnabled(false);
	        JMenuItem logout = new JMenuItem("Logout");
	        logout.addActionListener(e -> {
	            int response = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
	            if (response == JOptionPane.YES_OPTION)      
	                c.returnLogin(finestraCorrente);
	        });

	        popupUser.add(userInfo);
	        if(user.getIsAgente()) {
	        	JMenuItem creaAccount = new JMenuItem("Crea account da amministratore");
	            creaAccount.addActionListener(e -> c.createAdmin(finestraCorrente, user));
	            popupUser.add(creaAccount);
	        }
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
	        
	        finestraCorrente.addComponentListener(new java.awt.event.ComponentAdapter() {
	            @Override
	            public void componentMoved(java.awt.event.ComponentEvent e) {
	                if (popupMenu.isVisible()) {
	                    popupMenu.setVisible(false); 
	                    popupMenu.show(bellButton, 0, bellButton.getHeight());
			        
	                }
	                if (popupUser.isVisible()) {
	                	popupUser.setVisible(false);
	                    popupUser.show(userButton, 0, userButton.getHeight());
	                }
	                
	            }
	            
	            @Override
	            public void componentResized(java.awt.event.ComponentEvent e) {
	                if (popupMenu.isVisible()) {
	              
	                    popupMenu.show(bellButton, 0, bellButton.getHeight());
	                }
	                if (popupUser.isVisible()) {
	                	popupUser.setVisible(false);
	                    popupUser.show(userButton, 0, userButton.getHeight());
	                }
	                
	            }
	            
	        });

	        return topPanel;
	    }
	 /**
	  * 
	  * @param c
	  * @param popupMenu
	  * @param user
	  * @param bellButton
	  * Metodo per il recupero delle notifiche
	  */ 
	 private void bellButtonVisible(Controller c, JPopupMenu popupMenu, User user, JButton bellButton) {
		    popupMenu.removeAll();
		    popupMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 132, 212)));

		    List<Notifica> notifiche = c.getNotificheUtente(user.getMail());
		    
		    if (notifiche.isEmpty()) {
		        nessunaNotifica(popupMenu);
		    } else {
		        notifiche.forEach(notifica -> nuovaNotifica(c, popupMenu, user, bellButton, notifica));
		    }

		    popupMenu.pack();
		    popupMenu.revalidate();
		    popupMenu.repaint();

		    SwingUtilities.invokeLater(() -> popupMenu.show(bellButton, 0, bellButton.getHeight()));
		}

		private void nessunaNotifica(JPopupMenu popupMenu) {
		    JMenuItem dummy = new JMenuItem("Nessuna notifica");
		    dummy.setEnabled(false);
		    popupMenu.add(dummy);
		}

		private void nuovaNotifica(Controller c, JPopupMenu popupMenu, User user, JButton bellButton, Notifica notifica) {
		    JMenuItem menuItem = new JMenuItem(notifica.getMessaggio());
		    menuItem.addActionListener(ae -> {
		    	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, CARICAMENTO);
				 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
		    	handleNotificationClick(c, popupMenu, user, bellButton, menuItem, notifica);
		    	return null;}
                    
                    @Override
                    protected void done() {
                    	schermataCaricamento.close();
                    }}; worker.execute();});
		    popupMenu.add(menuItem);
		}
		/**
		 * metodo aggiornamento status notifiche
		 * @param c
		 * @param popupMenu
		 * @param user
		 * @param bellButton
		 * @param menuItem
		 * @param notifica
		 */
		private void handleNotificationClick(Controller c, JPopupMenu popupMenu, User user, JButton bellButton, JMenuItem menuItem, Notifica notifica) {
		    try {
		        c.setNotificaLetta(notifica);
		        popupMenu.remove(menuItem);
		        String[] parts;
		        int numero;
		        String numeroStringa;
		        if (notifica.getMessaggio().startsWith("Rifiutata la prenotazione dell'immobile con id:")) {
		        	 parts = notifica.getMessaggio().split(":"); 
		        	 numeroStringa = parts[1].trim().split("\\.")[0]; 
		             numero = Integer.parseInt(numeroStringa);
		             c.recreatePrenotazione(user, numero, finestraCorrente);
		        }else if(notifica.getMessaggio().startsWith("Nuova prenotazione con id:")) {
		        	 parts = notifica.getMessaggio().split(":"); 
		             numeroStringa = parts[parts.length - 1].trim(); 
		             numero = Integer.parseInt(numeroStringa);
		             c.checkPrenotazione(finestraCorrente, numero, user);
		             
		        } else {
		            c.viewCalendar(finestraCorrente, user);
		        }

		        updateBellIcon(c, user, bellButton);

		        if (popupMenu.getComponentCount() == 0) {
		            popupMenu.setVisible(false);
		        }
		    } catch (Exception ex) {
		    	logger.severe("Errore nella ricezione di notifiche");
		    }
		}
		/**
		 * aggiornamento icona notifiche
		 * 
		 * @param c
		 * @param user
		 * @param bellButton
		 */
	    private void updateBellIcon(Controller c, User user, JButton bellButton) {
	        List<Notifica> notifiche = c.getNotificheUtente(user.getMail());
	        String iconPath = notifiche.isEmpty() ? "/immagini/bellwhite.png" : "/immagini/whitebellnotifiche.png";

	        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
	        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
	          
	            return;
	        }

	        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        bellButton.setIcon(new ImageIcon(img));
	        bellButton.revalidate();
	        bellButton.repaint();
	    }
}