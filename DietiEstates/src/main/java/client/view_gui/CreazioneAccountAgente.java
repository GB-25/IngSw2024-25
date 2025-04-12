package client.view_gui;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import shared.classi.User;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import client.controller.Controller;

import java.awt.*;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Calendar;

public class CreazioneAccountAgente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
    private JTextField  txtCognome;
    private JTextField  txtEmail;
    private JTextField  txtTelefono;
    private JTextField txtPassword;
    private JDateChooser dateChooser;
    private JFrame finestraCorrente;
    private SchermataCaricamento schermataCaricamento;
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ERROR = "error";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS;
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();
    
    public CreazioneAccountAgente(Controller c, User user) {
    	FlatLaf.setup(new FlatLightLaf());
        
    	finestraCorrente=this;
        setTitle("Creazione Account Agente - DietiEstates25");

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setSize(600, 461);
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 600, 100);
        headerPanel.setBackground(new Color(40, 132, 212));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JButton backButton = new JButton("← Indietro");
        backButton.setBounds(0, 0, 98, 100);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40, 132, 212));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });

 
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(453, 0, 120, 100);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logopngwhite.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        mainPanel.setLayout(null);
        headerPanel.setLayout(null);

        headerPanel.add(backButton);
        headerPanel.add(logoLabel);
        mainPanel.add(headerPanel);
        
        dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);

		dateChooser.getCalendarButton().addActionListener(e -> { });

		dateChooser.setBounds(110, 192, 126, 19);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18); 
        Date dataMassima = cal.getTime();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 82); 
        Date dataMinima = cal.getTime(); 
        dateChooser.setSelectableDateRange(dataMinima, dataMassima);
        
        String password = generateRandomString(); 

        txtNome = new JTextField(15);
        txtNome.setBounds(110, 112, 126, 19);
        txtCognome = new JTextField(15);
        txtCognome.setBounds(444, 112, 126, 19);
        txtTelefono = new JTextField(15);
        txtTelefono.setBounds(444, 190, 126, 19);
        txtEmail = new JTextField(15);
        txtEmail.setBounds(110, 269, 126, 19);
        txtPassword = new JTextField(password, 15);
        txtPassword.setBounds(444, 271, 126, 19);
        Label lblNome = new Label("Nome:");
        lblNome.setBounds(44, 110, 44, 21);
        Label lblCognome = new Label("Cognome:");
        lblCognome.setBounds(352, 110, 61, 21);
        Label lblData = new Label("Data di nascita:");
        lblData.setBounds(10, 190, 100, 21);
        Label lblTelefono = new Label("Telefono:");
        lblTelefono.setBounds(357, 190, 56, 21);
        Label lblEmail = new Label("Email:");
        lblEmail.setBounds(44, 269, 44, 21);
        Label lblPassword = new Label("Password:");
        lblPassword.setBounds(350, 269, 63, 21);
        mainPanel.add(lblNome);
        mainPanel.add(lblCognome);
        mainPanel.add(lblData);
        mainPanel.add(lblTelefono);        
        mainPanel.add(lblEmail);
        mainPanel.add(lblPassword);
        mainPanel.add(txtNome);
        mainPanel.add(txtCognome);
        mainPanel.add(dateChooser);
        mainPanel.add(txtTelefono);
        mainPanel.add(txtEmail);
        mainPanel.add(txtPassword);        
     
        txtPassword.setEditable(false);
        
        JButton btnCreaAccount = new JButton("<html><center>Crea<br> Account</center></html>");
        btnCreaAccount.setBounds(250, 367, 93, 31);
        btnCreaAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreaAccount.addActionListener(e -> creaAccount(c, user));

        mainPanel.add(btnCreaAccount);

        Component rigidArea = Box.createRigidArea(new Dimension(0, 10));
        rigidArea.setBounds(742, 115, 12, 10);
        mainPanel.add(rigidArea);  
        JLabel label = new JLabel("<html><center>Sarà possibile modificare la password in futuro.<br> Ricordati di segnarla da qualche parte! ;)</center></html>");
        label.setBounds(170, 321, 285, 34);
        mainPanel.add(label);
        label.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 11));

        getContentPane().add(mainPanel);

        setVisible(true);
    }
    
    private void creaAccount(Controller c, User agenteChiamante) {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String password = txtPassword.getText().trim();
       
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || telefono.isEmpty() || data.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori!", ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!c.isValidNome(nome)|| !c.isValidNome(cognome)) {
        	JOptionPane.showMessageDialog(this, "Nome e cognome devono essere almeno di due lettere", ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
      
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Inserisci un'email valida!", ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        if(!c.isValidNumero(telefono)){
        	JOptionPane.showMessageDialog(this, "Inserisci un numero di telefono valido!", ERROR, JOptionPane.ERROR_MESSAGE);
        	return;
        }
        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile creare l'account. Procedere?",
                "Conferma creazione account amministrativo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
        	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                	c.createAdmin(nome, cognome, data, email, telefono, password, true);
                	return null;}
                
                @Override
                protected void done() {
        	
                	schermataCaricamento.close();
                	c.createHomeAgente(finestraCorrente, agenteChiamante);}};
                worker.execute();
               	JOptionPane.showMessageDialog(this, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
        	}
    	}
    /**
     * 
     * @return Stringa generata randomicamente per la password di default
     */
    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);

        sb.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        sb.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        for (int i = 3; i < LENGTH; i++) {
            sb.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        char[] charArray = sb.toString().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int randomIndex = random.nextInt(charArray.length);
            char temp = charArray[i];
            charArray[i] = charArray[randomIndex];
            charArray[randomIndex] = temp;
        }

        return new String(charArray);
    }
}
