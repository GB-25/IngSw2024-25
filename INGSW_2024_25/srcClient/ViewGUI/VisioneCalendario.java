package ViewGUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatLightLaf;
import Class.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Controller.Controller;

public class VisioneCalendario extends JFrame {
	private LocalDate selectedDateGlobal = null;
	private JFrame frame;
    private JTable calendarTable;
    private DefaultTableModel tableModel;
    private LocalDate today;
    private LocalDate startDate;
    private Map<LocalDate, List<String>> confirmedAppointments = new HashMap<>();
    private Map<LocalDate, List<String>> pendingAppointments = new HashMap<>();
    private ArrayList<String> prenotazioni;
    
    public VisioneCalendario(Controller c, User user) {
        FlatLightLaf.setup(new FlatLightLaf());
    	frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        today = LocalDate.now();
        startDate = today;
        
        // Modello della tabella con 7 colonne (una per ogni giorno della settimana)
        DayOfWeek dayOfWeek = today.getDayOfWeek(); // Prende il giorno della settimana, dogshit code goes crazy 🔥🔥🔥
        switch(dayOfWeek.toString()) {
        case "MONDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"}, 2);
          break;
        case "TUESDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Mar", "Mer", "Gio", "Ven", "Sab", "Dom", "Lun"}, 2);
          break;
        case "WEDNESDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Mer", "Gio", "Ven", "Sab", "Dom", "Lun", "Mar"}, 2);
            break;
        case "THURSDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Gio", "Ven", "Sab", "Dom", "Lun", "Mar", "Mer"}, 2);
            break;
        case "FRIDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Ven", "Sab", "Dom","Lun", "Mar", "Mer", "Gio"}, 2);
        	break;
        case "SATURDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Sab", "Dom","Lun", "Mar", "Mer", "Gio", "Ven"}, 2);
        	break;
        default:
        	tableModel = new DefaultTableModel(new Object[]{"Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"}, 2);
      }

        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(50);

	calendarTable.setShowGrid(true); // Griglia aggiunta perché FlatLightLaf la faceva sparire prima
        calendarTable.setGridColor(new Color(200, 200, 200));
        
        // Riempie la tabella con le date delle prossime due settimane
        fillCalendarTable();

        // Pulsanti per mostrare le prenotazioni confermate o in attesa
        JButton showConfirmedBtn = new JButton("Prenotazioni Confermate");
        JButton showPendingBtn = new JButton("Prenotazioni in Attesa");
        
        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showConfirmedBtn);
        buttonPanel.add(showPendingBtn);
        
     // Aggiunta di un ListSelectionListener per ottenere il click sulle date
        calendarTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting() && calendarTable.getSelectedRow() != -1 && calendarTable.getSelectedColumn() != -1) {
                Object selectedDate = tableModel.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn());
                if (selectedDate instanceof LocalDate) {
                    selectedDateGlobal = (LocalDate) selectedDate; // Salva la data selezionata
                }
            }
        });

        // Pulsanti che mostrano gli appuntamenti della data selezionata (in attesa o confermati)
        showConfirmedBtn.addActionListener((ActionEvent e) -> {
            if (selectedDateGlobal != null) {
            	prenotazioni = c.showReservation(user, true, selectedDateGlobal.toString());
            	confirmedAppointments.put(selectedDateGlobal, prenotazioni);
                showAppointmentsForDate(confirmedAppointments, selectedDateGlobal, "Prenotazioni Confermate");
            } else {
                JOptionPane.showMessageDialog(frame, "Seleziona prima una data!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        showPendingBtn.addActionListener((ActionEvent e) -> {
            if (selectedDateGlobal != null) {
            	prenotazioni = c.showReservation(user, false, selectedDateGlobal.toString());
            	pendingAppointments.put(selectedDateGlobal, prenotazioni);
                showAppointmentsForDate(pendingAppointments, selectedDateGlobal, "Prenotazioni in Attesa");
            } else {
                JOptionPane.showMessageDialog(frame, "Seleziona prima una data!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aggiunta dei componenti alla finestra
        frame.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }

    // Metodo per riempire il calendario con le date delle prossime due settimane
    private void fillCalendarTable() {
        for (int row = 0; row < 2; row++) {
            Object[] weekRow = new Object[7];
            for (int col = 0; col < 7; col++) {
                weekRow[col] = startDate.plusDays(row * 7 + col);
            }
            tableModel.addRow(weekRow);
        }
    }

    // Metodo per aggiungere appuntamenti di esempio, messo solamente per vedere come vengono mostrate le prenotazioni
    //private void addAppointment(Map<LocalDate, List<String>> map, LocalDate date, String description) {
        //map.putIfAbsent(date, new ArrayList<>());
        //map.get(date).add(description);
    //}
    
 // Nuovo metodo per mostrare gli appuntamenti di una data specifica
    private void showAppointmentsForDate(Map<LocalDate, List<String>> appointments, LocalDate date, String title) {
        
    	
    	List<String> appointmentList = appointments.getOrDefault(date, new ArrayList<>());
        
        String message = appointmentList.isEmpty() ? "Nessun appuntamento per questa data." :
                         String.join("\n", appointmentList);

        JOptionPane.showMessageDialog(frame, message, title + " - " + date.toString(), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> {
           //VisioneCalendario app = new VisioneCalendario();
            
            // Aggiunta di appuntamenti di esempio
            //app.addAppointment(app.confirmedAppointments, LocalDate.now().plusDays(2), "Meeting con cliente");
            //app.addAppointment(app.confirmedAppointments, LocalDate.now().plusDays(5), "Revisione progetto");
            //app.addAppointment(app.pendingAppointments, LocalDate.now().plusDays(8), "Call in attesa di conferma");
        //});
    }// Metodo e appuntamenti aggiunti solo per vedere come venivano mostrati 
}

