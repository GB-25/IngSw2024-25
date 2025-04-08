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
    private JLabel lblLunghezza = new JLabel("· essere lunga almeno 6 caratteri");
    private JLabel lblMaiuscola = new JLabel("· contenere almeno una lettera maiuscola");
    private JLabel lblMinuscola = new JLabel("· contenere almeno una lettera minuscola");
    private JLabel lblNumero = new JLabel("· contenere almeno un numero");
    private JLabel lblCheckPassword = new JLabel("");
    private JTextField textFieldNome;
    private JTextField textFieldCognome;
    private JTextField textFieldMail;
    private JTextField textFieldTelefono;
    private JPasswordField passwordField;
    private JPasswordField passwordFieldConferma;
    private JDateChooser dateChooser;
    private JButton btnConferma = new JButton("Crea Account");
    private boolean[] controllo = {false, false, false, false};
    private boolean[] valori = {false, false, false, false};
    private boolean combacia = false;

    /**
     * Controllore
     */
    public FinestraRegistrazione(Controller c) {
        finestraCorrente = this;
        initializeUI();
        initializeComponents(c);
    }

    /**
     * Metodi per la costruzione della finestra
     */
    private void initializeUI() {
        FlatLaf.setup(new FlatLightLaf());
        setTitle("Registrazione - DietiEstates25");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 548);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null); // Centra la finestra
    }

    private void initializeComponents(Controller c) {
        addHeader();
        addLabelsAndFields(c);
        addPasswordRequirements();
        addConfirmPasswordField(c);
        addButtons(c);
    }

    private void addHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBounds(0, 0, 600, 100);
        headerPanel.setBackground(new Color(40, 132, 212));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel titleLabel = new JLabel("Registrati in pochi passi!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Spazio dal bordo
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoLabel, BorderLayout.EAST);
        contentPane.add(headerPanel);
    }

    private void addLabelsAndFields(Controller c) {
        // Etichette
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 120, 80, 15);
        contentPane.add(lblNome);

        JLabel lblCognome = new JLabel("Cognome:");
        lblCognome.setBounds(300, 120, 80, 15);
        contentPane.add(lblCognome);

        JLabel lblDataDiNascita = new JLabel("Data di Nascita:");
        lblDataDiNascita.setBounds(30, 169, 120, 15);
        contentPane.add(lblDataDiNascita);

        JLabel lblIndirizzoMail = new JLabel("Email:");
        lblIndirizzoMail.setBounds(300, 169, 80, 15);
        contentPane.add(lblIndirizzoMail);

        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(30, 216, 80, 15);
        contentPane.add(lblTelefono);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 273, 80, 15);
        contentPane.add(lblPassword);

        JLabel lblConferma = new JLabel("Ripeti Password:");
        lblConferma.setBounds(300, 273, 120, 15);
        contentPane.add(lblConferma);

        // Campi di testo
        textFieldNome = new JTextField();
        textFieldNome.setBounds(120, 118, 150, 20);
        contentPane.add(textFieldNome);
        textFieldNome.setColumns(10);
        textFieldNome.addKeyListener(createNameKeyListener(c));
        labelVerifyNome.setBounds(120, 140, 150, 15);
        contentPane.add(labelVerifyNome);

        textFieldCognome = new JTextField();
        textFieldCognome.setBounds(390, 118, 150, 20);
        contentPane.add(textFieldCognome);
        textFieldCognome.setColumns(10);
        textFieldCognome.addKeyListener(createSurnameKeyListener(c));
        labelVerifyCognome.setBounds(390, 140, 150, 15);
        contentPane.add(labelVerifyCognome);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        ((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);
        dateChooser.setBounds(120, 164, 150, 20);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        Date dataMassima = cal.getTime();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 82);
        Date dataMinima = cal.getTime();
        dateChooser.setSelectableDateRange(dataMinima, dataMassima);
        contentPane.add(dateChooser);

        textFieldMail = new JTextField();
        textFieldMail.setBounds(390, 166, 150, 20);
        contentPane.add(textFieldMail);
        textFieldMail.setColumns(10);
        textFieldMail.addKeyListener(createEmailKeyListener(c));
        validationLabel.setBounds(390, 187, 150, 15);
        contentPane.add(validationLabel);

        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(120, 213, 150, 20);
        contentPane.add(textFieldTelefono);
        textFieldTelefono.setColumns(10);
        textFieldTelefono.addKeyListener(createPhoneKeyListener(c));
        labelConfermaTelefono.setBounds(120, 233, 150, 15);
        contentPane.add(labelConfermaTelefono);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 270, 150, 20);
        contentPane.add(passwordField);
        passwordField.addKeyListener(createPasswordKeyListener(c));

        passwordFieldConferma = new JPasswordField();
        passwordFieldConferma.setBounds(390, 270, 150, 20);
        contentPane.add(passwordFieldConferma);
        passwordFieldConferma.addKeyListener(createConfirmPasswordKeyListener(c));
        lblCheckPassword.setBounds(373, 290, 180, 15);
        contentPane.add(lblCheckPassword);
    }

    private void addPasswordRequirements() {
        JLabel lblRequisiti = new JLabel("La password deve:");
        lblRequisiti.setBounds(32, 317, 150, 15);
        contentPane.add(lblRequisiti);

        lblLunghezza.setBounds(50, 343, 250, 15);
        contentPane.add(lblLunghezza);

        lblMaiuscola.setBounds(50, 369, 300, 15);
        contentPane.add(lblMaiuscola);

        lblMinuscola.setBounds(50, 395, 300, 15);
        contentPane.add(lblMinuscola);

        lblNumero.setBounds(50, 421, 250, 15);
        contentPane.add(lblNumero);
    }

    private void addConfirmPasswordField(Controller c) {
        // Già aggiunto in addLabelsAndFields
    }

    private void addButtons(Controller c) {
        btnConferma.setEnabled(false);
        btnConferma.addActionListener(e -> handleConfirmation(c));
        btnConferma.setBounds(420, 468, 120, 30);
        contentPane.add(btnConferma);

        JButton btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBounds(30, 468, 120, 30);
        btnAnnulla.addActionListener(e -> handleCancellation(c));
        contentPane.add(btnAnnulla);
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
            labelVerifyNome.setText("✔️");
            labelVerifyNome.setForeground(Color.GREEN);
            controllo[0] = true;
        } else {
            labelVerifyNome.setText("❌");
            labelVerifyNome.setForeground(Color.RED);
            controllo[0] = false;
        }
        updateConfirmButtonState(c);
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
            labelVerifyCognome.setText("✔️");
            labelVerifyCognome.setForeground(Color.GREEN);
            controllo[1] = true;
        } else {
            labelVerifyCognome.setText("❌");
            labelVerifyCognome.setForeground(Color.RED);
            controllo[1] = false;
        }
        updateConfirmButtonState(c);
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
            validationLabel.setText("✔️ Email valida");
            validationLabel.setForeground(Color.GREEN);
            controllo[2] = true;
        } else {
            validationLabel.setText("❌ Email non valida");
            validationLabel.setForeground(Color.RED);
            controllo[2] = false;
        }
        updateConfirmButtonState(c);
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
            labelConfermaTelefono.setText("✔️");
            labelConfermaTelefono.setForeground(Color.GREEN);
            controllo[3] = true;
        } else {
            labelConfermaTelefono.setText("❌");
            labelConfermaTelefono.setForeground(Color.RED);
            controllo[3] = false;
        }
        updateConfirmButtonState(c);
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
            lblCheckPassword.setText("✔️ Le password coincidono");
            lblCheckPassword.setForeground(Color.GREEN);
            combacia = true;
        } else {
            lblCheckPassword.setText("❌ Le password non coincidono");
            lblCheckPassword.setForeground(Color.RED);
            combacia = false;
        }
        updateConfirmButtonState(c);
    }

    private void handleConfirmation(Controller c) {
        schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Creazione Account...");
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

    private void handleCancellation(Controller c) {
        int risposta = JOptionPane.showConfirmDialog(
                finestraCorrente,
                "Sei sicuro di voler annullare la registrazione?",
                "Conferma Annullamento",
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