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
	private ArrayList<Prenotazione> prenotazioni;
	private ArrayList<String> reservations = new ArrayList<>();
	private JPanel frame;
	private Prenotazione prenotazione;
	private String indirizzo; 
	private Immobile immobile;
	private JFrame finestraCorrente;
	public CalendarioInAttesa(Controller c, User user, LocalDate selectedDateGlobal) {
		FlatLaf.setup(new FlatLightLaf());
		frame = new JPanel();
		finestraCorrente = this;
		setTitle("Visualizza appuntamenti pendenti");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(550, 550));
		setContentPane(frame);
		System.out.println("la data è: "+selectedDateGlobal);
		//hello i am john funzionante, please fix finestra, nun s ver nient
		prenotazioni = (ArrayList<Prenotazione>) c.getPrenotazione(user, selectedDateGlobal);
		for (int i = 0; i < prenotazioni.size(); i++) {
			prenotazione = prenotazioni.get(i);
			immobile = prenotazione.getImmobile();
			indirizzo = immobile.getIndirizzo();
			reservations.add(i, prenotazione.getUser() + " a " + indirizzo + " alle ore " + prenotazione.getOraPrenotazione());
            }
		JList<String> reservationsList = new JList<>(reservations.toArray(new String[0]));
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(reservationsList);
		frame.add(scrollPane, BorderLayout.CENTER);
		System.out.println("la dimensione è: "+prenotazioni.size());
		reservationsList.addMouseListener(new MouseAdapter() {
		    @Override
		    
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int selectedPrenotazione = reservationsList.locationToIndex(e.getPoint());
		            if (selectedPrenotazione >= 0 && selectedPrenotazione < prenotazioni.size()) {
		                prenotazione = prenotazioni.get(selectedPrenotazione);
		                c.viewPendingReservation(finestraCorrente, user, prenotazione, c);
		            } else {
		                // Puoi gestire il caso di indice non valido, ad esempio:
		                JOptionPane.showMessageDialog(null, "Nessuna prenotazione selezionata.");
		            }
		        }
		    }
		});

		frame.setVisible(true);
	}
}
