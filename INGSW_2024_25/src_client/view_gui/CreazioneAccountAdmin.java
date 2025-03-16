package view_gui;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import Class.User;
import controller.Controller;

import java.awt.*;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Calendar;

public class CreazioneAccountAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
    private JTextField  txtCognome;
    private JTextField  txtEmail;
    private JTextField  txtTelefono;
    private JTextField txtPassword;
    private JDateChooser dateChooser;
    private JFrame finestraCorrente;
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS;
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public CreazioneAccountAdmin(Controller c, User user) {
    	FlatLaf.setup(new FlatLightLaf());
        // Configurazione finestra
    	finestraCorrente=this;
        setTitle("Creazione Account Admin - DietiEstates25");



        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); // Dimensioni ridotte
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Imposta un font più piccolo
        indietroButton.addActionListener(e -> {dispose(); new HomeAgente(c, user);});
        indietroPanel.add(indietroButton, BorderLayout.NORTH);
        mainPanel.add(indietroPanel);
        
        dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);

		dateChooser.getCalendarButton().addActionListener(e -> { });

		dateChooser.setBounds(152, 128, 200, 30);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18); 
        Date dataMassima = cal.getTime();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 82); 
        Date dataMinima = cal.getTime(); 
        dateChooser.setSelectableDateRange(dataMinima, dataMassima);
        
        String password = generateRandomString(); 

        // Campi per input
        txtNome = new JTextField(15);
        txtCognome = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtEmail = new JTextField(15);
        txtPassword = new JTextField(password, 15); // Campo password non editabile
        mainPanel.add(createLabelFieldPanel("Nome:", txtNome));
        mainPanel.add(createLabelFieldPanel("Cognome:", txtCognome));
        mainPanel.add(createDateFieldPanel("Data di nascita:", dateChooser));
        mainPanel.add(createLabelFieldPanel("Telefono:", txtTelefono));        
        mainPanel.add(createLabelFieldPanel("E-mail:", txtEmail));
        mainPanel.add(createLabelFieldPanel("Password:", txtPassword)); 
        txtPassword.setEditable(false);
        JLabel label = new JLabel("Sarà possibile modificare la password in futuro. Ricordati di segnarla da qualche parte! ;)");
        label.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 11));
        mainPanel.add(label);
        

        // Pulsante per la creazione account
        JButton btnCreaAccount = new JButton("Crea Account");
        btnCreaAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreaAccount.addActionListener(e -> {
        	c.createSchermataCaricamento(finestraCorrente, "Caricamento");
        	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
        	creaAccount(c, user);
                return null;}
            @Override
            protected void done() {
                dispose();
            }
	};
	worker.execute();});

     // Panel per  il pulsante
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCreaAccount);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio tra i campi
        mainPanel.add(buttonPanel); 

        // Aggiungi il pannello alla finestra
        getContentPane().add(mainPanel);

        // Mostra la finestra
        setVisible(true);
    }

    // Metodo per creare una riga con etichetta e campo di testo
     
    private JPanel createLabelFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25)); // Imposta larghezza fissa per allineare
        panel.add(label);
        panel.add(textField);
        return panel;
    }
    
    private JPanel createDateFieldPanel(String labelText, JDateChooser dateChooser) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25)); // Imposta larghezza fissa per allineare
        dateChooser.setPreferredSize(new Dimension(176, 20));
        panel.add(label);
        panel.add(dateChooser);
        return panel;
    }

    // Metodo per creare l'account con verifica dei campi
    private void creaAccount(Controller c, User agenteChiamante) {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String password = txtPassword.getText().trim();
        // Verifica che tutti i campi siano compilati
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || telefono.isEmpty() || data.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!c.isValidNome(nome)|| !c.isValidNome(cognome)) {
        	JOptionPane.showMessageDialog(this, "Nome e cognome devono essere almeno di due lettere", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Verifica formato email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Inserisci un'email valida!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verifica numero di telefono
        if(!c.isValidNumero(telefono)){
        	JOptionPane.showMessageDialog(this, "Inserisci un numero di telefono valido!", "Errore", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile creare l'account. Procedere?",
                "Conferma creazione account amministrativo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	c.createAdmin(nome, cognome, data, email, telefono, password, true);
        	// Sono stato costretto a commentare questa sezione altrimenti non partiva, bisogna fare in modo che password venga poi restituita in versione JPasswordField
        	JOptionPane.showMessageDialog(this, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            c.createHomeAgente(finestraCorrente, agenteChiamante);
        }
    }
    
    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);

        // Inclusione di almeno un carattere di ogni tipo
        sb.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        sb.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // Riempimento con caratteri casuali
        for (int i = 3; i < LENGTH; i++) {
            sb.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // Mescolamento caratteri poi inseriti in un array
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
