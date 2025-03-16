package view_gui;

import javax.swing.*;
import java.awt.*;

<<<<<<< Updated upstream:INGSW_2024_25/srcClient/ViewGUI/CaricamentoConfermato.java
=======

>>>>>>> Stashed changes:INGSW_2024_25/src_client/view_gui/CaricamentoConfermato.java
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import Class.*;
import controller.Controller;

public class CaricamentoConfermato extends JFrame {

<<<<<<< Updated upstream:INGSW_2024_25/srcClient/ViewGUI/CaricamentoConfermato.java
=======
	/**
	 * 
	 */
>>>>>>> Stashed changes:INGSW_2024_25/src_client/view_gui/CaricamentoConfermato.java
	private static final long serialVersionUID = 1L;
	JFrame homeAgente;
	JFrame finestraCorrente = this;
	
    public CaricamentoConfermato(Controller c, User user) {
        FlatLaf.setup(new FlatLightLaf());
        // Imposta la finestra
        setTitle("DietiEstates25");
<<<<<<< Updated upstream:INGSW_2024_25/srcClient/ViewGUI/CaricamentoConfermato.java
        setDefaultCloseOperation(EXIT_ON_CLOSE);
=======
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
>>>>>>> Stashed changes:INGSW_2024_25/src_client/view_gui/CaricamentoConfermato.java
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Tasto del logo
        JButton logoButton = createIconButton("/immagini/LOGO.png", 200, 100);
        

        // Pannello per il logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoButton);
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        // Pannello per il testo miao miao
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel successLabel = new JLabel("L'immobile è stato inserito con successo!");
        successLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel1 = new JLabel("L'immobile è ora inserito sulla piattaforma. Preparati alle offerte dei clienti! ;)");
        infoLabel1.setFont(new Font("Microsoft YaHei UI Light", Font.ITALIC, 14));
        infoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalStrut(50)); // Spazio sopra il testo
        textPanel.add(successLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(infoLabel1);
        textPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(textPanel, BorderLayout.CENTER);

        // Tasto home
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHome.setPreferredSize(new Dimension(100, 40));
        btnHome.addActionListener(e -> c.createHomeAgente(finestraCorrente, user));

        // Pannello per il pulsante
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnHome);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Mostra la finestra
        setVisible(true);
    }

    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));

        // Rende il pulsante trasparente
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

}
