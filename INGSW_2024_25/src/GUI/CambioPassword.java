package GUI;

import javax.swing.*;
import java.awt.*;

public class CambioPassword extends JFrame {
	// Campi per inserire la password
    private JPasswordField txtPasswordAttuale;
    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    
    public CambioPassword() {
        // Configurazione finestra
        setTitle("Cambio Password - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra
        
     // **Pannello principale con BoxLayout**
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // **Aggiunta dei campi**
        mainPanel.add(createLabelFieldPanel("Password Attuale:", txtPasswordAttuale = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Nuova Password:", txtNuovaPassword = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Conferma Password:", txtConfermaPassword = new JPasswordField(15)));

        // **Bottone di cambio password**
        JButton btnCambiaPassword = new JButton("Cambia Password");
        btnCambiaPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCambiaPassword.addActionListener(e -> cambiaPassword());

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
    private void cambiaPassword() {
        String passwordAttuale = new String(txtPasswordAttuale.getPassword());
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Password attuale (dummy)
        String passwordSalvata = "admin123"; 

        // **Verifica password attuale**
        if (!passwordAttuale.equals(passwordSalvata)) {
            JOptionPane.showMessageDialog(this, "La password attuale non è corretta!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // **Verifica nuova password**
        if (nuovaPassword.length() < 6) {
            JOptionPane.showMessageDialog(this, "La nuova password deve essere lunga almeno 6 caratteri!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this, "Le nuove password non coincidono!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulazione di salvataggio nel database
        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile cambiare la password. Procedere?",
                "Conferma cambio password",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	JOptionPane.showMessageDialog(this, "Password cambiata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomeAgente();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CambioPassword::new);
    }
}
