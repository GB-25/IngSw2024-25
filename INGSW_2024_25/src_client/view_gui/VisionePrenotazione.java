package view_gui;

import javax.swing.*;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;


import classi.Prenotazione;
import classi.User;
import controller.Controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import java.util.logging.Logger;


public class VisionePrenotazione extends JFrame implements MouseListener, MouseMotionListener{
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
    private JXMapViewer mapViewer;
	private JFrame finestraCorrente = this;
	private String fontScritte = "Microsoft YaHei UI Light";
	private transient Logger logger = Logger.getLogger(getClass().getName());
	private JPanel panel;
	private Point lastPoint;
	private static final String HTMLFINE = "</html>";
	/**
	 * @wbp.nonvisual location=152,17
	 */
	

	/**
	 * 
	 * Costruttore
	 */
	public VisionePrenotazione(User user, Prenotazione prenotazione, Controller c, JFrame finestraPrecedente) {
	    FlatLaf.setup(new FlatLightLaf());
	    setTitle("Visione Prenotazione");
	    setSize(800, 500);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    getContentPane().setLayout(new BorderLayout());
	    
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    setContentPane(mainPanel);
	    mainPanel.setBackground(Color.WHITE);

	    // Componenti con i font originali
	    titoloLabel = new JLabel("<html>Dettagli Prenotazione</html>");
	    titoloLabel.setFont(new Font(fontScritte, Font.BOLD, 20));
	    clienteLabel = new JLabel("<html>Cliente: " + prenotazione.getUser().getNome() + " " + prenotazione.getUser().getCognome() + HTMLFINE);
	    clienteLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
	    dataLabel = new JLabel("<html>Data: " + prenotazione.getDataPrenotazione()+HTMLFINE);
	    dataLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
	    oraLabel = new JLabel("<html>Ora: " + prenotazione.getOraPrenotazione()+HTMLFINE);
	    oraLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
	    posizioneLabel = new JLabel("<html>Posizione: "+prenotazione.getImmobile().getImmobileDettagli().getIndirizzo()+HTMLFINE);
	    posizioneLabel.setFont(new Font(fontScritte, Font.PLAIN, 20));
	    idCasaLabel = new JLabel("<html>ID Prenotazione: "+prenotazione.getId()+HTMLFINE);
	    idCasaLabel.setFont(new Font(fontScritte, Font.BOLD, 20));
	    
	    topPanel = createTopPanel();

	    // Pannello centrale personalizzato
	    JPanel centerPanel = new JPanel();
	    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
	    centerPanel.setBackground(Color.WHITE);
	    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    // Pannello dettagli (sinistra)
	    JPanel detailsPanel = new JPanel();
	    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
	    detailsPanel.setBackground(Color.WHITE);
	    detailsPanel.setPreferredSize(new Dimension(400, 400));
	    
	    indietroButton = new JButton("←");
	    indietroButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	    detailsPanel.add(indietroButton);
	    detailsPanel.add(Box.createVerticalStrut(20));
	    
	    // Allineamento a sinistra per tutti i componenti
	    titoloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    idCasaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    clienteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    dataLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    oraLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    posizioneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    detailsPanel.add(titoloLabel);
	    detailsPanel.add(Box.createVerticalStrut(10));
	    detailsPanel.add(idCasaLabel);
	    detailsPanel.add(Box.createVerticalStrut(10));
	    detailsPanel.add(clienteLabel);
	    detailsPanel.add(Box.createVerticalStrut(10));
	    detailsPanel.add(dataLabel);
	    detailsPanel.add(Box.createVerticalStrut(10));
	    detailsPanel.add(oraLabel);
	    detailsPanel.add(Box.createVerticalStrut(10));
	    detailsPanel.add(posizioneLabel);
	    detailsPanel.add(Box.createVerticalGlue());

	    // Pannello mappa (destra)
	    panel = new JPanel(new BorderLayout());
	    panel.setBackground(Color.WHITE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	    panel.setPreferredSize(new Dimension(400, 400));

	    try {
	        mapViewer = new JXMapViewer();
	        c.getCoordinates(c, prenotazione.getImmobile().getImmobileDettagli().getIndirizzo(), panel, mapViewer, null, user, finestraCorrente);
	        
	        mapViewer.setPreferredSize(new Dimension(350, 350));
	        mapViewer.setMinimumSize(new Dimension(350, 350));
	        mapViewer.setMaximumSize(new Dimension(350, 350));
	        
	        panel.add(mapViewer, BorderLayout.CENTER);
	    } catch (Exception e1) {
	        logger.severe("Errore nel recupero delle coordinate");
	    }

	    // Aggiungo i pannelli al centro
	    centerPanel.add(detailsPanel);
	    centerPanel.add(Box.createHorizontalStrut(20));
	    centerPanel.add(panel);

	    // Pulsanti esattamente come nell'originale
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    buttonPanel.setBackground(Color.WHITE);
	    
	    rifiutaButton = new JButton("Rifiuta");
	    rifiutaButton.setBackground(Color.RED);
	    rifiutaButton.setForeground(Color.WHITE);
	    
	    confermaButton = new JButton("Conferma");
	    confermaButton.setBackground(new Color(0, 153, 51));
	    confermaButton.setForeground(Color.WHITE);

	    // Action listener originali
	    indietroButton.addActionListener(e -> {
	    	if (finestraPrecedente instanceof CalendarioInAttesa) {
	    		String data = prenotazione.getDataPrenotazione();
	    		LocalDate date = LocalDate.parse(data);
	    		c.viewReservationsScreen(c, user, date, finestraCorrente);
	    	} else {
	    		c.createHomeAgente(finestraCorrente, user);
	    	}
	    });
	    
	    confermaButton.addActionListener(e -> {
	        int response = JOptionPane.showConfirmDialog(null,
	                "Vuoi confermare la prenotazione?",
	                "Conferma prenotazione",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE);

	        if (response == JOptionPane.YES_OPTION) {
	            int id = prenotazione.getId();
	            String mail = prenotazione.getAgente().getMail();
	            String data = prenotazione.getDataPrenotazione();
	            String ora= prenotazione.getOraPrenotazione();
	            if(c.reservationConfirm(id, mail, data, ora)) {
	                c.notifyCliente(prenotazione, true);
	                c.createHomeAgente(finestraCorrente, user);
	            }
	        }
	    });
	    
	    rifiutaButton.addActionListener(e -> {
	        int response = JOptionPane.showConfirmDialog(null,
	                "Vuoi rifiutare la prenotazione?",
	                "Conferma prenotazione",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE);

	        if (response == JOptionPane.YES_OPTION) {
	            int id = prenotazione.getId();
	            c.reservationDeny(id);
	            c.notifyCliente(prenotazione, false);
	          	c.createHomeAgente(finestraCorrente, user);
	        }
	    });

	    buttonPanel.add(rifiutaButton);
	    buttonPanel.add(confermaButton);

	    // Layout finale
	    mainPanel.add(topPanel, BorderLayout.NORTH);
	    mainPanel.add(centerPanel, BorderLayout.CENTER);
	    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
    
    /**
     * Creazione top Panel finestra
     * @param user
     * @param c
     * @return JPanel
     */
    private JPanel createTopPanel() {
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
       

        topPanel.add(logoButton, BorderLayout.CENTER);
        topPanel.add(separator, BorderLayout.SOUTH);

        return topPanel;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint != null) {
            Point newPoint = e.getPoint();

            int dx = newPoint.x - lastPoint.x;
            int dy = newPoint.y - lastPoint.y;

            GeoPosition newCenter = mapViewer.convertPointToGeoPosition(new Point(
                mapViewer.getWidth() / 2 - dx,  
                mapViewer.getHeight() / 2 - dy  
            ));

            
            mapViewer.setCenterPosition(newCenter);

   
            lastPoint = newPoint;
        }
    }
    
    

    @Override
    public void mouseReleased(MouseEvent e) {
        lastPoint = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            mapViewer.setZoom(mapViewer.getZoom() - 1); 
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // gestione eventi di movimento del mouse 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Metodo vuoto
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Metodo vuoto
    }   
     

    
}