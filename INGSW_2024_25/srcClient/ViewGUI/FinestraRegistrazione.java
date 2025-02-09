package ViewGUI;

import java.awt.*;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import Controller.Controller;

import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;


public class FinestraRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinestraHome frame = new FinestraHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} */

	/**
	 * Create the frame.
	 */
	public FinestraRegistrazione(Controller c) {
		setBackground(new Color(0, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logoLabel = new JLabel();
		logoLabel.setBounds(22, 0, 103, 67);
		ImageIcon logo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
		Image imageLogo = logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(imageLogo)); 
		contentPane.add(logoLabel);
		
		JLabel lblRegistratiInPochi = new JLabel("Registrati in pochi passi!");
		lblRegistratiInPochi.setFont(new Font("Dialog", Font.BOLD, 26));
		lblRegistratiInPochi.setForeground(new Color(0, 153, 255));
		lblRegistratiInPochi.setBounds(148, 12, 377, 40);
		contentPane.add(lblRegistratiInPochi);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(33, 101, 70, 15);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(78, 99, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cognome\n");
		lblNewLabel.setBounds(227, 101, 70, 15);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(296, 99, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDataDiNascita = new JLabel("Data di Nascita");
		lblDataDiNascita.setBounds(33, 153, 159, 15);
		contentPane.add(lblDataDiNascita);
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
        dateChooser.setBounds(153, 148, 200, 30);
        dateChooser.setSelectableDateRange(new Date(), new Date(System.currentTimeMillis()));
        contentPane.add(dateChooser);
        
        JLabel lblIndirizzoMail = new JLabel("Indirizzo Mail");
        lblIndirizzoMail.setBounds(33, 219, 119, 15);
        contentPane.add(lblIndirizzoMail);
        
        
        JLabel validationLabel = new JLabel("");
        validationLabel.setBounds(136, 245, 114, 14);
        contentPane.add(validationLabel);
        
        
        textField_2 = new JTextField();
        textField_2.setBounds(136, 217, 114, 19);
        contentPane.add(textField_2);
        textField_2.setColumns(10);
        textField_2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();
                if (c.isValidEmail(text)) {
                    validationLabel.setText("Email valida ✔️");
                    validationLabel.setForeground(Color.GREEN);
                    
                } else {
                    validationLabel.setText("Email non valida ❌");
                    validationLabel.setForeground(Color.RED);
                }
            }
        });
        
        JLabel lblTelefono = new JLabel("Telefono");
        lblTelefono.setBounds(311, 219, 70, 15);
        contentPane.add(lblTelefono);
        
        textField_3 = new JTextField();
        textField_3.setBounds(382, 217, 114, 19);
        contentPane.add(textField_3);
        textField_3.setColumns(10);
        
        JButton btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBounds(54, 276, 117, 25);
        contentPane.add(btnAnnulla);
        
        JButton btnNewButtocon = new JButton("Conferma");
        btnNewButtocon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButtocon.setBounds(342, 276, 117, 25);
        contentPane.add(btnNewButtocon);
        
		
	}
}
