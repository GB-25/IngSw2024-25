package ViewGUI;

import java.awt.*;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import Controller.Controller;

import java.util.Date;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class FinestraRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame finestraCorrente;
	private JFrame finestraLogin;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldMail;
	private JTextField textFieldTelefono;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConferma;
	private JDateChooser dateChooser;
	private boolean[] controllo = {false, false, false, false};
	private boolean[] valori = {false, false, false, false};
	private boolean enable = false;
	private boolean combacia = false;

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
		finestraCorrente = this;
		setBackground(new Color(0, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 635);
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
		
		JButton btnConferma = new JButton("Conferma");
        btnConferma.setEnabled(false);
        btnConferma.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        		char[] pass = passwordField.getPassword();
        		String password = new String(pass);
        		c.handleRegistration(textFieldNome.getText(), textFieldCognome.getText(), data, textFieldMail.getText(), textFieldTelefono.getText(), password, false);
        	}
        });
        btnConferma.setBounds(408, 561, 117, 25);
        contentPane.add(btnConferma);
		
        dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);

		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
        dateChooser.setBounds(152, 128, 200, 30);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18); 
        Date dataMassima = cal.getTime();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 82); 
        Date dataMinima = cal.getTime(); 
        dateChooser.setSelectableDateRange(dataMinima, dataMassima);
        contentPane.add(dateChooser);
        
        
		
		JLabel lblRegistratiInPochi = new JLabel("Registrati in pochi passi!");
		lblRegistratiInPochi.setFont(new Font("Dialog", Font.BOLD, 26));
		lblRegistratiInPochi.setForeground(new Color(0, 153, 255));
		lblRegistratiInPochi.setBounds(148, 12, 377, 40);
		contentPane.add(lblRegistratiInPochi);
		
		JLabel labelVerifyNome = new JLabel("");
	    labelVerifyNome.setBounds(77, 96, 114, 25);
	    contentPane.add(labelVerifyNome);
		
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(32, 81, 70, 15);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(77, 79, 114, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textFieldNome.getText();
                if (c.isValidNome(text)) {
                    labelVerifyNome.setText("Nome valido ✔️");
                    labelVerifyNome.setForeground(Color.GREEN);
                    controllo[0] = true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                    
                } else {
                    labelVerifyNome.setText("Nome non valido ❌");
                    labelVerifyNome.setForeground(Color.RED);
                    controllo[0] = false;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                }
            }
        });
		
		JLabel labelVerifyCognome = new JLabel("");
        labelVerifyCognome.setBounds(295, 96, 114, 25);
        contentPane.add(labelVerifyCognome);
		
		JLabel lblCognome = new JLabel("Cognome\n");
		lblCognome.setBounds(226, 81, 70, 15);
		contentPane.add(lblCognome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(295, 79, 114, 19);
		contentPane.add(textFieldCognome);
		textFieldCognome.setColumns(10);
		textFieldCognome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textFieldCognome.getText();
                if (c.isValidNome(text)) {
                    labelVerifyCognome.setText("Cognome valido ✔️");
                    labelVerifyCognome.setForeground(Color.GREEN);
                    controllo[1] = true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                    
                } else {
                    labelVerifyCognome.setText("Cognome non valido ❌");
                    labelVerifyCognome.setForeground(Color.RED);
                    controllo[1] = false;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                }
            }
        });
		
		JLabel lblDataDiNascita = new JLabel("Data di Nascita");
		lblDataDiNascita.setBounds(32, 133, 159, 15);
		contentPane.add(lblDataDiNascita);
		
		
        
        JLabel lblIndirizzoMail = new JLabel("Indirizzo Mail");
        lblIndirizzoMail.setBounds(32, 199, 119, 15);
        contentPane.add(lblIndirizzoMail);
        
        
        JLabel validationLabel = new JLabel("");
        validationLabel.setBounds(135, 225, 161, 14);
        contentPane.add(validationLabel);
        
        
        textFieldMail = new JTextField();
        textFieldMail.setBounds(135, 197, 114, 19);
        contentPane.add(textFieldMail);
        textFieldMail.setColumns(10);
        textFieldMail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textFieldMail.getText();
                if (c.isValidEmail(text)) {
                    validationLabel.setText("Email valida ✔️");
                    validationLabel.setForeground(Color.GREEN);
                    controllo[2] = true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                    
                } else {
                    validationLabel.setText("Email non valida ❌");
                    validationLabel.setForeground(Color.RED);
                    controllo[2] = false;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                }
            }
        });
        
        JLabel lblTelefono = new JLabel("Telefono");
        lblTelefono.setBounds(310, 199, 70, 15);
        contentPane.add(lblTelefono);
        JLabel labelConfermaTelefono = new JLabel("");
        labelConfermaTelefono.setBounds(391, 224, 104, 19);
        contentPane.add(labelConfermaTelefono);
        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(381, 197, 114, 19);
        contentPane.add(textFieldTelefono);
        textFieldTelefono.setColumns(10);
        textFieldTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	String text = textFieldTelefono.getText();
            	if (c.isValidNumero(text)) {
                    labelConfermaTelefono.setText("Numero valida ✔️");
                    labelConfermaTelefono.setForeground(Color.GREEN);
                    controllo[3]= true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                    
                } else {
                	labelConfermaTelefono.setText("Numero non valido ❌");
                	labelConfermaTelefono.setForeground(Color.RED);
                	controllo[3] = false;
                	enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
                }
            }
        });
        
        
        JButton btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBounds(22, 561, 117, 25);
        btnAnnulla.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int risposta = JOptionPane.showConfirmDialog(
        		        finestraCorrente, 
        		        "Sei sicuro di non voler continuare la registrazione?", 
        		        "Attenzione", 
        		        JOptionPane.YES_NO_OPTION
        		);

        		if (risposta == JOptionPane.YES_OPTION) {
        		    c.returnLogin(finestraCorrente);
        		}

        	}});
        contentPane.add(btnAnnulla);
        
        
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(32, 257, 70, 15);
        contentPane.add(lblPassword);
        
        JLabel lblRequisiti = new JLabel("La password deve:");
        lblRequisiti.setBounds(276, 284, 159, 15);
        contentPane.add(lblRequisiti);
        
        JLabel lblLunghezza = new JLabel("· essere lunga almeno 6 caratteri");
        lblLunghezza.setBounds(225, 311, 258, 15);
        contentPane.add(lblLunghezza);
        
        JLabel lblMaiuscola = new JLabel("· contenere almeno una lettera maiuscola");
        lblMaiuscola.setBounds(213, 338, 329, 15);
        contentPane.add(lblMaiuscola);
        
        JLabel lblMinuscola = new JLabel("· contenere almeno una lettera minuscola");
        lblMinuscola.setBounds(211, 366, 319, 15);
        contentPane.add(lblMinuscola);
        
        JLabel lblNumero = new JLabel("· contenere almeno un numero");
        lblNumero.setBounds(238, 394, 257, 15);
        contentPane.add(lblNumero);
        
        JLabel lblConferma = new JLabel("Conferma Password");
        lblConferma.setBounds(22, 469, 169, 15);
        contentPane.add(lblConferma);
        
        JLabel lblCheckPassword = new JLabel("");
        lblCheckPassword.setBounds(266, 498, 229, 15);
        contentPane.add(lblCheckPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(266, 255, 183, 19);
        contentPane.add(passwordField);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char[] text = passwordField.getPassword();
                c.isValidPassword(text, valori);
                if (valori[0]) {
                    lblLunghezza.setForeground(Color.GREEN);
                } else {
                    lblLunghezza.setForeground(Color.RED);
                }
                if (valori[1]) {
                	lblMaiuscola.setForeground(Color.GREEN);
                } else {
                	lblMaiuscola.setForeground(Color.RED);
                }
                if (valori[2]) {
                	lblMinuscola.setForeground(Color.GREEN);
                } else {
                	lblMinuscola.setForeground(Color.RED);
                }
                if (valori[3]) {
                	lblNumero.setForeground(Color.GREEN);
                } else {
                	lblNumero.setForeground(Color.RED);
                }
                if (c.verifyPassword(passwordField.getPassword(), passwordFieldConferma.getPassword())) {
            		lblCheckPassword.setText("La password combacia ✔️");
                    lblCheckPassword.setForeground(Color.GREEN);
                    combacia = true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
            	} else {
            		lblCheckPassword.setText("La password non combacia ❌");
                    lblCheckPassword.setForeground(Color.RED);
                    combacia = false;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
            	}
            }
        });
       
        
        passwordFieldConferma = new JPasswordField();
        passwordFieldConferma.setBounds(266, 467, 183, 19);
        contentPane.add(passwordFieldConferma);
        passwordFieldConferma.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	if (c.verifyPassword(passwordField.getPassword(), passwordFieldConferma.getPassword())) {
            		lblCheckPassword.setText("La password combacia ✔️");
                    lblCheckPassword.setForeground(Color.GREEN);
                    combacia = true;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
            	} else {
            		lblCheckPassword.setText("La password non combacia ❌");
                    lblCheckPassword.setForeground(Color.RED);
                    combacia = false;
                    enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
                    btnConferma.setEnabled(enable);
            	}
            }
        });
        
//        DocumentListener documentListener = new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
//                btnConferma.setEnabled(enable);
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//            	enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
//                btnConferma.setEnabled(enable);
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//            	enable = (c.checkFields(controllo))&&(combacia)&&(c.checkFields(valori))&&(!(dateChooser.getDate()== null));
//                btnConferma.setEnabled(enable);
//            }
//        };
//        
//        textFieldNome.getDocument().addDocumentListener(documentListener);
//        textFieldCognome.getDocument().addDocumentListener(documentListener);
//        textFieldMail.getDocument().addDocumentListener(documentListener);
//        textFieldTelefono.getDocument().addDocumentListener(documentListener);
//        passwordField.getDocument().addDocumentListener(documentListener);
//        passwordFieldConferma.getDocument().addDocumentListener(documentListener);
//        ((JTextField) dateChooser.getDateEditor().getUiComponent()).getDocument().addDocumentListener(documentListener);
//        

		};

}
	
	
	

