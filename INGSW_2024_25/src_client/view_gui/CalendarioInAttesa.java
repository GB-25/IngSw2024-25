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
	public CalendarioInAttesa(Controller c, User user, LocalDate selectedDateGlobal) {
		FlatLaf.setup(new FlatLightLaf());
		frame = new JPanel(new BorderLayout());
		finestraCorrente = this;
		setTitle("Visualizza appuntamenti pendenti");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(new Dimension(400, 400));
		setContentPane(frame);
		JPanel reservationsPanel = new JPanel (new BorderLayout());
		//hello i am john funzionante, please fix finestra, nun s ver nient
		prenotazioni = (ArrayList<Prenotazione>) c.getPrenotazione(user, selectedDateGlobal);
		if (prenotazioni.size() == 0)
			JOptionPane.showMessageDialog(null, "Nessuna prenotazione trovata per questa data.");
		for (int i = 0; i < prenotazioni.size(); i++) {
			prenotazione = prenotazioni.get(i);
			immobile = prenotazione.getImmobile();
			indirizzo = immobile.getIndirizzo();
			reservations.add(i, "Sig.\ra " + prenotazione.getUser().getNome() + " " + prenotazione.getUser().getCognome() + " a " + indirizzo + " alle ore " + prenotazione.getOraPrenotazione());
            }
		JList<String> reservationsList = new JList<>(reservations.toArray(new String[0]));
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(reservationsList);
		reservationsPanel.add(new JLabel("Prenotazioni per il " + prenotazione.getDataPrenotazione()), BorderLayout.NORTH);
		reservationsPanel.add(scrollPane, BorderLayout.CENTER);
		reservationsList.addMouseListener(new MouseAdapter() {
		    @Override
		    
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int selectedPrenotazione = reservationsList.locationToIndex(e.getPoint());
		            if (selectedPrenotazione >= 0 && selectedPrenotazione < prenotazioni.size()) {
		                prenotazione = prenotazioni.get(selectedPrenotazione);
		                c.viewPendingReservation(finestraCorrente, user, prenotazione, c);
		            } else {
		                JOptionPane.showMessageDialog(null, "Nessuna prenotazione selezionata.");
		            }
		        }
		    }
		});
		frame.add(reservationsPanel);
		setVisible(true);
	}
}
