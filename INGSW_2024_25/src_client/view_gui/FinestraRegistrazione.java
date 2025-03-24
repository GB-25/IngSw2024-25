
package view_gui;

import java.awt.*;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import controller.Controller;

import java.util.Date;
import java.util.Calendar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class FinestraRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame finestraCorrente;
	private SchermataCaricamento schermataCaricamento;
	private JLabel labelVerifyNome = new JLabel();
	private JLabel labelVerifyCognome = new JLabel();
	private JLabel validationLabel = new JLabel();
	private JLabel labelConfermaTelefono = new JLabel();
	private JLabel lblLunghezza = new JLabel();
	private JLabel lblMaiuscola = new JLabel();
	private JLabel lblMinuscola = new JLabel();
	private JLabel lblNumero = new JLabel();
	private JLabel lblCheckPassword = new JLabel();
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldMail;
	private JTextField textFieldTelefono;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConferma;
	private JDateChooser dateChooser;
	private JButton btnConferma = new JButton("Conferma");
	private boolean[] controllo = {false, false, false, false};
	private boolean[] valori = {false, false, false, false};

	private boolean combacia = false;

	

	/**
	 * Create the frame.
	 */
	public FinestraRegistrazione(Controller c) {
	    finestraCorrente = this;
	    initializeUI();
	    initializeComponents(c);
	}

	private void initializeUI() {
		FlatLaf.setup(new FlatLightLaf());
	    setBackground(new Color(0, 153, 255));
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    setBounds(100, 100, 542, 635);
	    contentPane = new JPanel();
	    contentPane.setBackground(new Color(255, 255, 255));
	    contentPane.setBorder(null);
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    setResizable(false);
	}

	private void initializeComponents(Controller c) {
	    addLogo();
	    addRegistrationLabel();
	    addNameFields(c);
	    addSurnameFields(c);
	    addBirthDateField();
	    addEmailField(c);
	    addPhoneField(c);
	    addPasswordFields(c);
	    addConfirmButton(c);
	    addCancelButton(c);
	}

	private void addLogo() {
	    JLabel logoLabel = new JLabel();
	    logoLabel.setBounds(22, 0, 103, 67);
	    ImageIcon logo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
	    Image imageLogo = logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
	    logoLabel.setIcon(new ImageIcon(imageLogo));
	    contentPane.add(logoLabel);
	}

	private void addRegistrationLabel() {
	    JLabel lblRegistratiInPochi = new JLabel("Registrati in pochi passi!");
	    lblRegistratiInPochi.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD | Font.ITALIC, 26));
	    lblRegistratiInPochi.setForeground(new Color(0, 153, 255));
	    lblRegistratiInPochi.setBounds(148, 12, 377, 40);
	    contentPane.add(lblRegistratiInPochi);
	}

	private void addNameFields(Controller c) {
	    JLabel lblNome = new JLabel("Nome");
	    lblNome.setBounds(32, 81, 70, 15);
	    contentPane.add(lblNome);

	    textFieldNome = new JTextField();
	    textFieldNome.setBounds(77, 79, 114, 19);
	    contentPane.add(textFieldNome);
	    textFieldNome.setColumns(10);

	    labelVerifyNome = new JLabel("");
	    labelVerifyNome.setBounds(77, 96, 114, 25);
	    contentPane.add(labelVerifyNome); // Add label to contentPane

	    textFieldNome.addKeyListener(createNameKeyListener(c));
	}

	private KeyAdapter createNameKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validateName(c);
	        }
	    };
	}

	private void validateName(Controller c) {
	    String text = textFieldNome.getText();
	    if (c.isValidNome(text)) {
	        labelVerifyNome.setText("Nome valido ✔️");
	        labelVerifyNome.setForeground(Color.GREEN);
	        controllo[0] = true;
	    } else {
	        labelVerifyNome.setText("Nome non valido ❌");
	        labelVerifyNome.setForeground(Color.RED);
	        controllo[0] = false;
	    }
	    updateConfirmButtonState(c);
	}

	private void addSurnameFields(Controller c) {
	    JLabel lblCognome = new JLabel("Cognome\n");
	    lblCognome.setBounds(226, 81, 70, 15);
	    contentPane.add(lblCognome);

	    textFieldCognome = new JTextField();
	    textFieldCognome.setBounds(295, 79, 114, 19);
	    contentPane.add(textFieldCognome);
	    textFieldCognome.setColumns(10);

	    labelVerifyCognome = new JLabel("");
	    labelVerifyCognome.setBounds(295, 96, 114, 25);
	    contentPane.add(labelVerifyCognome); // Add label to contentPane

	    textFieldCognome.addKeyListener(createSurnameKeyListener(c));
	}

	private KeyAdapter createSurnameKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validateSurname(c);
	        }
	    };
	}

	private void validateSurname(Controller c) {
	    String text = textFieldCognome.getText();
	    if (c.isValidNome(text)) {
	        labelVerifyCognome.setText("Cognome valido ✔️");
	        labelVerifyCognome.setForeground(Color.GREEN);
	        controllo[1] = true;
	    } else {
	        labelVerifyCognome.setText("Cognome non valido ❌");
	        labelVerifyCognome.setForeground(Color.RED);
	        controllo[1] = false;
	    }
	    updateConfirmButtonState(c);
	}

	private void addBirthDateField() {
	    JLabel lblDataDiNascita = new JLabel("Data di Nascita");
	    lblDataDiNascita.setBounds(32, 133, 159, 15);
	    contentPane.add(lblDataDiNascita);

	    dateChooser = new JDateChooser();
	    dateChooser.setDateFormatString("yyyy-MM-dd");
	    ((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);
	    dateChooser.setBounds(152, 128, 200, 30);

	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.YEAR, -18); // Maximum date (18 years ago)
	    Date dataMassima = cal.getTime();

	    cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 82); // Minimum date (82 years ago)
	    Date dataMinima = cal.getTime();

	    dateChooser.setSelectableDateRange(dataMinima, dataMassima);
	    contentPane.add(dateChooser);
	}

	private void addEmailField(Controller c) {
	    JLabel lblIndirizzoMail = new JLabel("Indirizzo Mail");
	    lblIndirizzoMail.setBounds(32, 199, 119, 15);
	    contentPane.add(lblIndirizzoMail);

	    textFieldMail = new JTextField();
	    textFieldMail.setBounds(135, 197, 114, 19);
	    contentPane.add(textFieldMail);
	    textFieldMail.setColumns(10);

	    validationLabel = new JLabel("");
	    validationLabel.setBounds(135, 225, 161, 14);
	    contentPane.add(validationLabel); // Add label to contentPane

	    textFieldMail.addKeyListener(createEmailKeyListener(c));
	}

	private KeyAdapter createEmailKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validateEmail(c);
	        }
	    };
	}

	private void validateEmail(Controller c) {
	    String text = textFieldMail.getText();
	    if (c.isValidEmail(text)) {
	        validationLabel.setText("Email valida ✔️");
	        validationLabel.setForeground(Color.GREEN);
	        controllo[2] = true;
	    } else {
	        validationLabel.setText("Email non valida ❌");
	        validationLabel.setForeground(Color.RED);
	        controllo[2] = false;
	    }
	    updateConfirmButtonState(c);
	}

	private void addPhoneField(Controller c) {
	    JLabel lblTelefono = new JLabel("Telefono");
	    lblTelefono.setBounds(310, 199, 70, 15);
	    contentPane.add(lblTelefono);

	    textFieldTelefono = new JTextField();
	    textFieldTelefono.setBounds(381, 197, 114, 19);
	    contentPane.add(textFieldTelefono);
	    textFieldTelefono.setColumns(10);

	    labelConfermaTelefono = new JLabel("");
	    labelConfermaTelefono.setBounds(391, 224, 104, 19);
	    contentPane.add(labelConfermaTelefono); // Add label to contentPane

	    textFieldTelefono.addKeyListener(createPhoneKeyListener(c));
	}

	private KeyAdapter createPhoneKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validatePhone(c);
	        }
	    };
	}

	private void validatePhone(Controller c) {
	    String text = textFieldTelefono.getText();
	    if (c.isValidNumero(text)) {
	        labelConfermaTelefono.setText("Numero valido ✔️");
	        labelConfermaTelefono.setForeground(Color.GREEN);
	        controllo[3] = true;
	    } else {
	        labelConfermaTelefono.setText("Numero non valido ❌");
	        labelConfermaTelefono.setForeground(Color.RED);
	        controllo[3] = false;
	    }
	    updateConfirmButtonState(c);
	}

	private void addPasswordFields(Controller c) {
	    JLabel lblPassword = new JLabel("Password");
	    lblPassword.setBounds(32, 257, 70, 15);
	    contentPane.add(lblPassword);

	    passwordField = new JPasswordField();
	    passwordField.setBounds(135, 255, 183, 19);
	    contentPane.add(passwordField);

	    JLabel lblRequisiti = new JLabel("La password deve:");
	    lblRequisiti.setBounds(175, 284, 159, 15);
	    contentPane.add(lblRequisiti);

	    lblLunghezza = new JLabel("· essere lunga almeno 6 caratteri");
	    lblLunghezza.setBounds(135, 311, 258, 15);
	    contentPane.add(lblLunghezza); // Add label to contentPane

	    lblMaiuscola = new JLabel("· contenere almeno una lettera maiuscola");
	    lblMaiuscola.setBounds(118, 338, 329, 15);
	    contentPane.add(lblMaiuscola); // Add label to contentPane

	    lblMinuscola = new JLabel("· contenere almeno una lettera minuscola");
	    lblMinuscola.setBounds(118, 366, 319, 15);
	    contentPane.add(lblMinuscola); // Add label to contentPane

	    lblNumero = new JLabel("· contenere almeno un numero");
	    lblNumero.setBounds(143, 394, 257, 15);
	    contentPane.add(lblNumero); // Add label to contentPane

	    JLabel lblConferma = new JLabel("Ripeti Password");
	    lblConferma.setBounds(32, 439, 169, 15);
	    contentPane.add(lblConferma);

	    passwordFieldConferma = new JPasswordField();
	    passwordFieldConferma.setBounds(135, 437, 183, 19);
	    contentPane.add(passwordFieldConferma);

	    lblCheckPassword = new JLabel("");
	    lblCheckPassword.setBounds(135, 464, 229, 15);
	    contentPane.add(lblCheckPassword); // Add label to contentPane

	    passwordField.addKeyListener(createPasswordKeyListener(c));
	    passwordFieldConferma.addKeyListener(createConfirmPasswordKeyListener(c));
	}

	private KeyAdapter createPasswordKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validatePassword(c);
	        }
	    };
	}

	private void validatePassword(Controller c) {
	    char[] text = passwordField.getPassword();
	    c.isValidPassword(text, valori);
	    updatePasswordLabels();
	    updateConfirmButtonState(c);
	}

	private void updatePasswordLabels() {
	    lblLunghezza.setForeground(valori[0] ? Color.GREEN : Color.RED);
	    lblMaiuscola.setForeground(valori[1] ? Color.GREEN : Color.RED);
	    lblMinuscola.setForeground(valori[2] ? Color.GREEN : Color.RED);
	    lblNumero.setForeground(valori[3] ? Color.GREEN : Color.RED);
	}

	private KeyAdapter createConfirmPasswordKeyListener(Controller c) {
	    return new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            validateConfirmPassword(c);
	        }
	    };
	}

	private void validateConfirmPassword(Controller c) {
	    if (c.verifyPassword(passwordField.getPassword(), passwordFieldConferma.getPassword())) {
	        lblCheckPassword.setText("La password combacia ✔️");
	        lblCheckPassword.setForeground(Color.GREEN);
	        combacia = true;
	    } else {
	        lblCheckPassword.setText("La password non combacia ❌");
	        lblCheckPassword.setForeground(Color.RED);
	        combacia = false;
	    }
	    updateConfirmButtonState(c);
	}

	private void addConfirmButton(Controller c) {
	
	    btnConferma.setEnabled(false);
	    btnConferma.addActionListener(e -> handleConfirmation(c));
	    btnConferma.setBounds(391, 561, 117, 25);
	    contentPane.add(btnConferma);
	}

	private void handleConfirmation(Controller c) {
	    schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
	        @Override
	        protected Void doInBackground() throws Exception {
	            String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
	            char[] pass = passwordField.getPassword();
	            String password = new String(pass);
	            c.handleRegistration(textFieldNome.getText(), textFieldCognome.getText(), data, textFieldMail.getText(), textFieldTelefono.getText(), password, false);
	            return null;
	        }

	        @Override
	        protected void done() {
	            schermataCaricamento.close();
	        }
	    };
	    worker.execute();
	}

	private void addCancelButton(Controller c) {
	    JButton btnAnnulla = new JButton("Annulla");
	    btnAnnulla.setBounds(22, 561, 117, 25);
	    btnAnnulla.addActionListener(e -> handleCancellation(c));
	    contentPane.add(btnAnnulla);
	}

	private void handleCancellation(Controller c) {
	    int risposta = JOptionPane.showConfirmDialog(
	            finestraCorrente,
	            "Sei sicuro di non voler continuare la registrazione?",
	            "Attenzione",
	            JOptionPane.YES_NO_OPTION
	    );

	    if (risposta == JOptionPane.YES_OPTION) {
	        c.returnLogin(finestraCorrente);
	    }
	}

	private void updateConfirmButtonState(Controller c) {
	    boolean enable = (c.checkFields(controllo)) && (combacia) && (c.checkFields(valori)) && (dateChooser.getDate() != null);
	    btnConferma.setEnabled(enable);
	}
}
