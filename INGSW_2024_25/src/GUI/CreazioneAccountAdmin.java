package GUI;

import javax.swing.*;
import java.awt.*;
import Class.Controller;

public class CreazioneAccountAdmin extends JFrame {
    private JTextField txtNome, txtCognome, txtEmail;
    private JPasswordField txtPassword, txtConfermaPassword;

    public CreazioneAccountAdmin(Controller c) {
        // Configurazione finestra
        setTitle("Creazione Account - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campi per input
        mainPanel.add(createLabelFieldPanel("Nome:", txtNome = new JTextField(15)));
        mainPanel.add(createLabelFieldPanel("Cognome:", txtCognome = new JTextField(15)));
        mainPanel.add(createLabelFieldPanel("E-mail:", txtEmail = new JTextField(15)));
        mainPanel.add(createLabelFieldPanel("Password:", txtPassword = new JPasswordField(15)));
        mainPanel.add(createLabelFieldPanel("Conferma Password:", txtConfermaPassword = new JPasswordField(15)));

        // Pulsante per la creazione account
        JButton btnCreaAccount = new JButton("Crea Account");
        btnCreaAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreaAccount.addActionListener(e -> creaAccount(c));

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

    // Metodo per creare l'account con verifica dei campi
    private void creaAccount(Controller c) {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Verifica che tutti i campi siano compilati
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || confermaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica formato email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Inserisci un'email valida!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica lunghezza password
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "La password deve contenere almeno 6 caratteri!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica che le password coincidano
        if (!password.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile creare l'account. Procedere?",
                "Conferma creazione account amministrativo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	JOptionPane.showMessageDialog(this, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomeAgente(c);
        }
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(CreazioneAccountAdmin::new);
    }
}
