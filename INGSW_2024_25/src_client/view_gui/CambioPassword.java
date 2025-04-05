package view_gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.User;
import controller.Controller;

import java.awt.*;

public class CambioPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JFrame finestraCorrente;
    private JPasswordField txtPasswordAttuale;
    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private static final String ERRORE = "Errore";
    private boolean[] valori = {false, false, false, false};
    
    /**
     * 
     * Costruttore
     */
    public CambioPassword(Controller c, User user) {
        setResizable(false);
    	FlatLaf.setup(new FlatLightLaf());
    	finestraCorrente = this;
        setTitle("Cambio Password - Admin");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(600, 640);
        setLocationRelativeTo(null); 
        
     
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); 
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> {dispose(); new HomeGenerale(c, user);});
        indietroPanel.add(indietroButton);
        mainPanel.add(indietroPanel);

        
        txtPasswordAttuale = new JPasswordField(15);
        txtNuovaPassword = new JPasswordField(15);
        txtConfermaPassword = new JPasswordField(15);
        mainPanel.add(createLabelFieldPanel("Password Attuale:", txtPasswordAttuale));
        mainPanel.add(createLabelFieldPanel("Nuova Password:", txtNuovaPassword));
        mainPanel.add(createLabelFieldPanel("Conferma Password:", txtConfermaPassword));

    
        JButton btnCambiaPassword = new JButton("Cambia Password");
        btnCambiaPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCambiaPassword.addActionListener(e -> cambiaPassword(c, user));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        mainPanel.add(btnCambiaPassword);

      
        add(mainPanel);

        
        setVisible(true);
    }

    /**
     * 
     * @param labelText
     * @param textField
     * @return Panel per poter inserire la password
     */
    private JPanel createLabelFieldPanel(String labelText, JPasswordField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 150)); 
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    /**
     * 
     * @param c
     * @param user
     *  aggiorna la password, eventuali errori sono gestiti tramite creazione di specifiche JOptionPane
     */
    private void cambiaPassword(Controller c, User user) {
        String passwordAttuale = new String(txtPasswordAttuale.getPassword());


        
        String passwordSalvata = user.getPassword();
     
        if (!passwordAttuale.equals(passwordSalvata)) {
            JOptionPane.showMessageDialog(this, "La password attuale non è corretta!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        c.isValidPassword(txtNuovaPassword.getPassword(), valori);
        if (!c.checkFields(valori)){
        	
            JOptionPane.showMessageDialog(this, "La password deve contenere almeno 6 caratteri, una lettera minuscola, una lettera maiuscola e una cifra!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        if (passwordSalvata.equals(nuovaPassword)) {
        	 JOptionPane.showMessageDialog(this, "La nuova password non può essere uguale alla precedente", ERRORE, JOptionPane.ERROR_MESSAGE);
             return;
        }

        if (!c.verifyPassword(txtNuovaPassword.getPassword(),txtConfermaPassword.getPassword())) {
            JOptionPane.showMessageDialog(this, "Le nuove password non coincidono!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        int response = JOptionPane.showConfirmDialog(null,
                "I campi sono stati riempiti ed è possibile cambiare la password. Procedere?",
                "Conferma cambio password",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
        	c.updatePassword(user,txtNuovaPassword.getPassword());
            c.createHomeAgente(finestraCorrente, user);
        }
    }

    
}