package ViewGUI;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import Class.User;
import Controller.Controller;

import java.awt.*;

public class CambioPassword extends JFrame {
	// Campi per inserire la password
    private JPasswordField txtPasswordAttuale;
    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private boolean[] valori = {false, false, false, false};
    
    public CambioPassword(Controller c, User user) {
        // Configurazione finestra
	FlatLightLaf.setup(new FlatLightLaf());
        setTitle("Cambio Password - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra
        
     // **Pannello principale con BoxLayout**
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); // Dimensioni ridotte
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Imposta un font più piccolo
        indietroButton.addActionListener(e -> {dispose(); new HomeAgente(c, user);});
        indietroPanel.add(indietroButton);
        mainPanel.add(indietroPanel);

        // **Aggiunta dei campi**
        mainPanel.add(createLabelFieldPanel("Password Attuale:", txtPasswordAttuale = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Nuova Password:", txtNuovaPassword = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Conferma Password:", txtConfermaPassword = new JPasswordField(15)));

        // **Bottone di cambio password**
        JButton btnCambiaPassword = new JButton("Cambia Password");
        btnCambiaPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCambiaPassword.addActionListener(e -> cambiaPassword(c, user));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio
        mainPanel.add(btnCambiaPassword);

        // Aggiungi il pannello alla finestra
        add(mainPanel);

        // Mostra la finestra
        setVisible(true);
    }

    /**
     * Metodo per creare una riga con etichetta e campo di testo
     */
    private JPanel createLabelFieldPanel(String labelText, JPasswordField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 150)); // Imposta larghezza fissa per allineare
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    /**
     * Metodo per cambiare la password con verifica
     */
    private void cambiaPassword(Controller c, User user) {
        String passwordAttuale = new String(txtPasswordAttuale.getPassword());
        //String nuovaPassword = new String(txtNuovaPassword.getPassword());
        //String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Simuliamo la password attuale (da sostituire con query al database)
        String passwordSalvata = user.getPassword();
        // **Verifica password attuale**
        if (!passwordAttuale.equals(passwordSalvata)) {
            JOptionPane.showMessageDialog(this, "La password attuale non è corretta!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // **Verifica nuova password**
        c.isValidPassword(txtNuovaPassword.getPassword(), valori);
        if (!c.checkFields(valori)){
        	
            JOptionPane.showMessageDialog(this, "La password deve contenere almeno 6 caratteri, una lettera minuscola, una lettera maiuscola e una cifra!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        if (passwordSalvata.equals(nuovaPassword)) {
        	 JOptionPane.showMessageDialog(this, "La nuova password non può essere uguale alla precedente", "Errore", JOptionPane.ERROR_MESSAGE);
             return;
        }

        if (!c.verifyPassword(txtNuovaPassword.getPassword(),txtConfermaPassword.getPassword())) {
            JOptionPane.showMessageDialog(this, "Le nuove password non coincidono!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulazione salvataggio nel database
        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile cambiare la password. Procedere?",
                "Conferma cambio password",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	c.updatePassword(user,txtNuovaPassword.getPassword() );
        	JOptionPane.showMessageDialog(this, "Password cambiata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomeAgente(c, user);
        }
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(CambioPassword::new);
    }
}
