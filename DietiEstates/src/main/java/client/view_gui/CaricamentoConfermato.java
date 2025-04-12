package client.view_gui;

import javax.swing.*;
import java.awt.*;


import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import shared.classi.*;
import client.controller.Controller;

public class CaricamentoConfermato extends JFrame {


	private static final long serialVersionUID = 1L;
	JFrame homeAgente;
	JFrame finestraCorrente = this;
	/**
	 * 
	 * Costruttire
	 */
    public CaricamentoConfermato(Controller c, User user) {
        FlatLaf.setup(new FlatLightLaf());
        setResizable(false);
        setTitle("DietiEstates25");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(600, 440);
        setLocationRelativeTo(null);

      
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        
        JButton logoButton = createIconButton("/logopngwhite.png", 200, 100);
        

       
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(600, 120));
        logoPanel.setBackground(new Color (40, 132, 212));
        logoPanel.setLayout(null);
        logoPanel.add(logoButton);
        mainPanel.add(logoPanel, BorderLayout.NORTH);

      
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel successLabel = new JLabel("L'immobile è stato inserito con successo!");
        successLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel1 = new JLabel("L'immobile è ora inserito sulla piattaforma. Preparati alle offerte dei clienti! ;)");
        infoLabel1.setFont(new Font("Microsoft YaHei UI Light", Font.ITALIC, 14));
        infoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalStrut(50)); 
        textPanel.add(successLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(infoLabel1);
        textPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(textPanel, BorderLayout.CENTER);

       
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHome.setPreferredSize(new Dimension(100, 40));
        btnHome.addActionListener(e -> c.createHomeAgente(finestraCorrente, user));

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnHome);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    /**
     * 
     * @param path
     * @param width
     * @param height
     * @return JButton da visualizzare nella finestra
     */
    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));
        button.setBounds(184, 10, 232, 108);

 
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

}
