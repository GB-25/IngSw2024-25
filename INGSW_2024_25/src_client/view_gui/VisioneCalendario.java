package view_gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.User;
import controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisioneCalendario extends JFrame {
	private static final long serialVersionUID = 1L;
	private LocalDate selectedDateGlobal = null;
	private JFrame frame;
	private SchermataCaricamento schermataCaricamento;
    private JTable calendarTable;
    private DefaultTableModel tableModel;
    private LocalDate today;
    private LocalDate startDate;
    private Map<LocalDate, List<String>> confirmedAppointments = new HashMap<>();
    private ArrayList<String> prenotazioni;
    /**
     * Costruttore
     * 
     */
    public VisioneCalendario(Controller c, User user) {
    	FlatLaf.setup(new FlatLightLaf());
    	frame = this;
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setLayout(new BorderLayout());
        setResizable(false);
        JPanel indietroPanel = new JPanel(new BorderLayout());
        indietroPanel.setBackground(new Color(40, 132, 212));
        JButton indietroButton = new JButton("←");
        indietroButton.setHorizontalAlignment(SwingConstants.LEFT);
        indietroButton.setPreferredSize(new Dimension(60, 50));
        indietroButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        
        indietroButton.setFocusPainted(false);
        indietroButton.setForeground(Color.WHITE);
        indietroButton.setBackground(new Color(40, 132, 212));
        indietroButton.setBorderPainted(false);
        indietroButton.setContentAreaFilled(false);
        indietroButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });
        indietroButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });
        indietroPanel.add(indietroButton, BorderLayout.WEST);
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledImage));
        indietroPanel.add(logoLabel, BorderLayout.EAST);
        today = LocalDate.now();
        startDate = today;
        
        DayOfWeek dayOfWeek = today.getDayOfWeek(); 
        switch(dayOfWeek.toString()) {
        case "MONDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
          break;
        case "TUESDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Mar", "Mer", "Gio", "Ven", "Sab", "Dom", "Lun"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
          break;
        case "WEDNESDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Mer", "Gio", "Ven", "Sab", "Dom", "Lun", "Mar"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
            break;
        case "THURSDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Gio", "Ven", "Sab", "Dom", "Lun", "Mar", "Mer"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
            break;
        case "FRIDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Ven", "Sab", "Dom","Lun", "Mar", "Mer", "Gio"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
        	break;
        case "SATURDAY":
        	tableModel = new DefaultTableModel(new Object[]{"Sab", "Dom","Lun", "Mar", "Mer", "Gio", "Ven"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
        	break;
        default:
        	tableModel = new DefaultTableModel(new Object[]{"Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"}, 2) {
        		private static final long serialVersionUID = 1L;

				@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
        		}
        	};
      }

        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(50);
        calendarTable.setShowGrid(true); 
        calendarTable.setGridColor(new Color(200, 200, 200));
        calendarTable.setRowSelectionAllowed(false);
        calendarTable.setColumnSelectionAllowed(false);
        calendarTable.setCellSelectionEnabled(true);
        fillCalendarTable();

        JButton showConfirmedBtn = new JButton("Prenotazioni Confermate");
        JButton showPendingBtn = new JButton("Prenotazioni in Attesa");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showConfirmedBtn);
        if(user.getIsAgente()) {
        buttonPanel.add(showPendingBtn);}
  
        calendarTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());
                
                if (row >= 0 && col >= 0) {
                    Object selectedDate = tableModel.getValueAt(row, col);
                    if (selectedDate instanceof LocalDate localDate) {
                        selectedDateGlobal = localDate;
                    }
                }
            }
        });


        showConfirmedBtn.addActionListener((ActionEvent e) -> {
            if (selectedDateGlobal != null) {
            	schermataCaricamento = c.createSchermataCaricamento(frame, "Caricamento");
   			 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                   @Override
                   protected Void doInBackground() throws Exception {
            	prenotazioni = (ArrayList<String>) c.showReservation(user, true, selectedDateGlobal.toString());
            	confirmedAppointments.put(selectedDateGlobal, prenotazioni);
                showAppointmentsForDate(confirmedAppointments, selectedDateGlobal, "Prenotazioni Confermate");
                return null;}
                   @Override
                   protected void done() {
                   	schermataCaricamento.close();
                   }}; worker.execute();
   			 
            } else {
                JOptionPane.showMessageDialog(frame, "Seleziona prima una data!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        showPendingBtn.addActionListener(e -> { 
        	schermataCaricamento = c.createSchermataCaricamento(frame, "Caricamento");
			 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
        	c.viewReservationsScreen(c, user, selectedDateGlobal, frame);
        	return null;}
                
                @Override
                protected void done() {
                	schermataCaricamento.close();
                }}; worker.execute();
			 });

        frame.getContentPane().add(indietroPanel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    /**
     * Metodo per generazione calendario per le prossime due settimane
     */
    private void fillCalendarTable() {
        for (int row = 0; row < 3; row++) {
            Object[] weekRow = new Object[7];
            if (row == 2) {
            	weekRow[0] = startDate.plusDays(row * 7L);
            }
            else {
            for (int col = 0; col < 7; col++) {
                weekRow[col] = startDate.plusDays(row * 7L + col);
            }}
            tableModel.addRow(weekRow);
        }
    }
    
    /**
     * Visualizzazione appuntamenti per la data selezionata
     * @param appointments
     * @param date
     * @param title
     */
    private void showAppointmentsForDate(Map<LocalDate, List<String>> appointments, LocalDate date, String title) {
        
    	
    	List<String> appointmentList = appointments.getOrDefault(date, new ArrayList<>());
    	
        	String message = appointmentList.isEmpty() ? "Nessun appuntamento per questa data." :
        		String.join("\n", appointmentList);

        	JOptionPane.showMessageDialog(frame, message, title + " - " + date.toString(), JOptionPane.INFORMATION_MESSAGE);
        
    } 
}

