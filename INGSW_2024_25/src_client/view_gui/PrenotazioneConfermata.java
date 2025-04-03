package view_gui;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.*;
import controller.Controller;

public class PrenotazioneConfermata extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame homeAgente;
	JFrame finestraCorrente = this;
	private String fontScritte = "Microsoft YaHei UI Light";
	/**
	 * Costruttore
	 */
    public PrenotazioneConfermata(Controller c, User user) {
        FlatLaf.setup(new FlatLightLaf());
     
        setTitle("DietiEstates25");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        JButton logoButton = createIconButton("/immagini/logopngwhite.png", 200, 100);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(40, 132, 212));
        logoPanel.add(logoButton, BorderLayout.CENTER);
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel successLabel = new JLabel("La prenotazione è stata effettuata con successo!");
        successLabel.setFont(new Font(fontScritte, Font.BOLD, 18));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel1 = new JLabel("La prenotazione è stata inviata all'agente.");
        infoLabel1.setFont(new Font(fontScritte, Font.ITALIC, 14));
        infoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel infoLabel2 = new JLabel("Controlla le notifiche per sapere se verrà confermata! ;)");
        infoLabel2.setFont(new Font(fontScritte, Font.ITALIC, 14));
        infoLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalStrut(50)); 
        textPanel.add(successLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(infoLabel1);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(infoLabel2);
        textPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(textPanel, BorderLayout.CENTER);
        
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHome.setPreferredSize(new Dimension(100, 40));
        btnHome.addActionListener(e -> {c.createHomeUtente(finestraCorrente, user);
        this.dispose();
        	
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnHome);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    /**
     * Metodo crezione pulsante
     * @param path
     * @param width
     * @param height
     * @return JButton
     */
    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }
}
