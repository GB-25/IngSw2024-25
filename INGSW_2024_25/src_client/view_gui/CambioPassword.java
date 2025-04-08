package view_gui;

import javax.swing.*;
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
    private static final String FONT = "Segoe UI";
    private boolean[] valori = {false, false, false, false};

    public CambioPassword(Controller c, User user) {
    	setBackground(Color.WHITE);
        FlatLightLaf.setup(); 

        finestraCorrente = this;
        setTitle("Cambio Password - Admin");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(40, 132, 212));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JButton backButton = new JButton("← Indietro");
        backButton.setFont(new Font(FONT, Font.PLAIN, 14));
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
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font(FONT, Font.BOLD, 20));
        }

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(logoLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setOpaque(false);

        GridBagConstraints gbcLabel1 = new GridBagConstraints();
        GridBagConstraints gbcField1 = new GridBagConstraints();

        GridBagConstraints gbcLabel2 = new GridBagConstraints();
        GridBagConstraints gbcField2 = new GridBagConstraints();

        GridBagConstraints gbcLabel3 = new GridBagConstraints();
        GridBagConstraints gbcField3 = new GridBagConstraints();

        Insets labelInsets = new Insets(15, 15, 15, 15);
        gbcLabel1.insets = labelInsets;
        gbcLabel2.insets = labelInsets;
        gbcLabel3.insets = labelInsets;

        Insets fieldInsets = new Insets(15, 15, 15, 15);
        gbcField1.insets = fieldInsets;
        gbcField2.insets = fieldInsets;
        gbcField3.insets = fieldInsets;

        gbcLabel1.anchor = GridBagConstraints.WEST;
        gbcLabel2.anchor = GridBagConstraints.WEST;
        gbcLabel3.anchor = GridBagConstraints.WEST;

        gbcField1.anchor = GridBagConstraints.WEST;
        gbcField2.anchor = GridBagConstraints.WEST;
        gbcField3.anchor = GridBagConstraints.WEST;

        gbcLabel1.fill = GridBagConstraints.HORIZONTAL;
        gbcLabel2.fill = GridBagConstraints.HORIZONTAL;
        gbcLabel3.fill = GridBagConstraints.HORIZONTAL;

        gbcField1.fill = GridBagConstraints.HORIZONTAL;
        gbcField2.fill = GridBagConstraints.HORIZONTAL;
        gbcField3.fill = GridBagConstraints.HORIZONTAL;

        txtPasswordAttuale = new JPasswordField();
        txtNuovaPassword = new JPasswordField();
        txtConfermaPassword = new JPasswordField();
     
        Dimension fieldSize = new Dimension(300, 30);
        txtPasswordAttuale.setPreferredSize(fieldSize);
        txtNuovaPassword.setPreferredSize(fieldSize);
        txtConfermaPassword.setPreferredSize(fieldSize);

        gbcLabel1.gridx = 0;
        gbcLabel1.gridy = 0;
        gbcLabel1.weightx = 0.3;
        formPanel.add(new JLabel("Password Attuale:"), gbcLabel1);

        gbcField1.gridx = 1;
        gbcField1.weightx = 0.7;
        formPanel.add(txtPasswordAttuale, gbcField1);

        gbcLabel2.gridx = 0;
        gbcLabel2.gridy = 1;
        gbcLabel2.weightx = 0.3;
        formPanel.add(new JLabel("Nuova Password:"), gbcLabel2);

        gbcField2.gridx = 1;
        gbcField2.weightx = 0.7;
        formPanel.add(txtNuovaPassword, gbcField2);

        gbcLabel3.gridx = 0;
        gbcLabel3.gridy = 2;
        gbcLabel3.weightx = 0.3;
        formPanel.add(new JLabel("Conferma Password:"), gbcLabel3);

        gbcField3.gridx = 1;
        gbcField3.weightx = 0.7;
        formPanel.add(txtConfermaPassword, gbcField3);

        centerPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JButton btnCambiaPassword = new JButton("Cambia Password");
        btnCambiaPassword.setFont(new Font(FONT, Font.BOLD, 14));
        btnCambiaPassword.setBackground(new Color(33, 150, 243));
        btnCambiaPassword.setForeground(Color.WHITE);
        btnCambiaPassword.setFocusPainted(false);
        btnCambiaPassword.setPreferredSize(new Dimension(200, 40));
        btnCambiaPassword.addActionListener(e -> cambiaPassword(c, user));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.add(btnCambiaPassword);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setResizable(false);
        setVisible(true);
    }
    /**
     * Metodo per verificare e cambiare password
     * @param c
     * @param user
     */
    private void cambiaPassword(Controller c, User user) {
        String passwordAttuale = new String(txtPasswordAttuale.getPassword());
        String passwordSalvata = user.getPassword();

        if (!passwordAttuale.equals(passwordSalvata)) {
            JOptionPane.showMessageDialog(this, "La password attuale non è corretta!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        c.isValidPassword(txtNuovaPassword.getPassword(), valori);
        if (!c.checkFields(valori)) {
            JOptionPane.showMessageDialog(this, "La password deve contenere almeno 6 caratteri, una minuscola, una maiuscola e una cifra!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        if (passwordSalvata.equals(nuovaPassword)) {
            JOptionPane.showMessageDialog(this, "La nuova password non può essere uguale alla precedente", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!c.verifyPassword(txtNuovaPassword.getPassword(), txtConfermaPassword.getPassword())) {
            JOptionPane.showMessageDialog(this, "Le nuove password non coincidono!", ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(this,
                "Procedere con il cambio password?",
                "Conferma",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            c.updatePassword(user, txtNuovaPassword.getPassword());
            c.createHomeAgente(finestraCorrente, user);
        }
    }
}



