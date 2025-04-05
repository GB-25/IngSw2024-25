package view_gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.User;
import classi.Immobile;
import classi.Prenotazione;
import controller.Controller;

public class CalendarioInAttesa extends JFrame{
	private static final long serialVersionUID = 1L;
	private transient ArrayList<Prenotazione> prenotazioni;
	private ArrayList<String> reservations = new ArrayList<>();
	private JPanel frame;
	private transient Prenotazione prenotazione;
	private String indirizzo; 
	private transient Immobile immobile;
	private JFrame finestraCorrente;
	private SchermataCaricamento schermataCaricamento;
	/**
	 * 
	 * Costruttore
	 */
	public CalendarioInAttesa(Controller c, User user, LocalDate selectedDateGlobal) {
		setResizable(false);
		FlatLaf.setup(new FlatLightLaf());
		frame = new JPanel();
		finestraCorrente = this;
		setTitle("Visualizza appuntamenti pendenti");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(new Dimension(500, 450));
		setContentPane(frame);
		setResizable(false);
		JButton logoButton = createIconButton("/immagini/logopngwhite.png", 200, 100);

        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 486, 82);
        logoPanel.setBackground(new Color(40, 132, 212));
        logoPanel.setLayout(null);
        logoPanel.add(logoButton);
        frame.add(logoPanel, BorderLayout.NORTH);
		
		JPanel indietroPanel = new JPanel();
		indietroPanel.setBounds(0, 80, 486, 32);
        JButton indietroButton = new JButton("←");
        indietroButton.setBounds(0, 0, 44, 26);
        indietroButton.setHorizontalTextPosition(SwingConstants.LEFT);
        indietroButton.setBackground(Color.WHITE);
        indietroButton.setFont(new Font("Helvetica", Font.BOLD, 13));
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
        	dispose();
        	c.viewCalendar(finestraCorrente, user);
        });
        indietroPanel.setLayout(null);
        indietroPanel.add(indietroButton);

		JPanel reservationsPanel = new JPanel ();
		reservationsPanel.setBounds(0, 111, 486, 302);
		

		prenotazioni = (ArrayList<Prenotazione>) c.getPrenotazione(user, selectedDateGlobal);
		if (prenotazioni.isEmpty())
			JOptionPane.showMessageDialog(null, "Nessuna prenotazione trovata per questa data.");
		for (int i = 0; i < prenotazioni.size(); i++) {
			prenotazione = prenotazioni.get(i);
			immobile = prenotazione.getImmobile();
			indirizzo = immobile.getImmobileDettagli().getIndirizzo();
			reservations.add(i, "<html><div align ='center'>Sig/ra " + prenotazione.getUser().getNome() + " " + prenotazione.getUser().getCognome() + " a " + indirizzo + " alle ore " + prenotazione.getOraPrenotazione()+"</div></html>");
            }
		
		JList<String> reservationsList = new JList<>(reservations.toArray(new String[0]));
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(reservationsList);
		scrollPane.setBounds(0, 13, 486, 374);
		reservationsPanel.setLayout(null);
		JLabel label = new JLabel("Prenotazioni per il " + prenotazione.getDataPrenotazione());
		label.setBounds(0, 0, 486, 13);
		reservationsPanel.add(label);
		reservationsPanel.add(scrollPane);
		
		reservationsList.addMouseListener(new MouseAdapter() {
		    
			@Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
		   			 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		                   @Override
		                   protected Void doInBackground() throws Exception {
		                	   int selectedPrenotazione = reservationsList.locationToIndex(e.getPoint());
		                	   if (selectedPrenotazione >= 0 && selectedPrenotazione < prenotazioni.size()) {
				                prenotazione = prenotazioni.get(selectedPrenotazione);
				                c.viewPendingReservation(finestraCorrente, user, prenotazione, c);
		                	   } else {
		   		                JOptionPane.showMessageDialog(null, "Nessuna prenotazione selezionata.");
		   		            }
		                return null;}
		                   @Override
		                   protected void done() {
		                   	schermataCaricamento.close();
		                   }}; worker.execute();
		   			 
		           
		        }
		    }
		});
		frame.setLayout(null);
		frame.add(indietroPanel);
		frame.add(reservationsPanel);
		setVisible(true);
	}
	
	private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));
        button.setBounds(129, -10, 232, 108);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }
}
