package ViewGUI;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Calendar;

public class CreazioneAccountAdmin extends JFrame {
    private JTextField txtNome, txtCognome, txtEmail, txtTelefono;
    private JPasswordField txtPassword, txtConfermaPassword;
    private JDateChooser dateChooser;
    private boolean[] valori = {false, false, false, false};

    public CreazioneAccountAdmin(Controller c, String nome, String cognome, String mail) {
        // Configurazione finestra
        setTitle("Creazione Account - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        indietroButton.addActionListener(e -> {dispose(); new HomeAgente(c, nome, cognome, mail);});
        indietroPanel.add(indietroButton);
        mainPanel.add(indietroPanel);
        
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

        // Campi per input
        mainPanel.add(createLabelFieldPanel("Nome:", txtNome = new JTextField(15)));
        mainPanel.add(createLabelFieldPanel("Cognome:", txtCognome = new JTextField(15)));
        mainPanel.add(createDateFieldPanel("Data di nascita:", dateChooser = new JDateChooser()));
        mainPanel.add(createLabelFieldPanel("Telefono:", txtTelefono = new JTextField(15)));        
        mainPanel.add(createLabelFieldPanel("E-mail:", txtEmail = new JTextField(15)));
        mainPanel.add(createLabelFieldPanel("Password:", txtPassword = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Conferma Password:", txtConfermaPassword = new JPasswordField(15)));

        // Pulsante per la creazione account
        JButton btnCreaAccount = new JButton("Crea Account");
        btnCreaAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreaAccount.addActionListener(e -> creaAccount(c, nome, cognome, mail));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio tra i campi
        mainPanel.add(btnCreaAccount);

        // Aggiungi il pannello alla finestra
        add(mainPanel);

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
    private void creaAccount(Controller c, String nomeAgenteChiamante, String cognomeAgenteChiamante, String mailAgenteChiamante) {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        char[] password = txtPassword.getPassword();
        char[] confermaPassword = txtConfermaPassword.getPassword();

        // Verifica che tutti i campi siano compilati
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || telefono.isEmpty() || data.isEmpty() || password.toString().isEmpty()|| confermaPassword.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
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

        // Verifica lunghezza password
        c.isValidPassword(password, valori);
        if (!c.checkFields(valori)){
        	
            JOptionPane.showMessageDialog(this, "La password deve contenere almeno 6 caratteri, una lettera minuscola, una lettera maiuscola e una cifra!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica che le password coincidano
        if (!c.verifyPassword(password, confermaPassword)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile creare l'account. Procedere?",
                "Conferma creazione account amministrativo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	c.handleRegistration(nome, cognome, data, email, telefono, password, true);
        	JOptionPane.showMessageDialog(this, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomeAgente(c, nomeAgenteChiamante, cognomeAgenteChiamante, mailAgenteChiamante);
        }
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(CreazioneAccountAdmin::new);
    }
}
