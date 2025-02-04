package GUI;

import javax.swing.*;
import java.awt.*;

public class HomeAgente extends JFrame {

    public HomeAgente() {
        // Imposta il titolo della finestra
        setTitle("DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Pannello superiore con logo e icone
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JButton logoButton = createIconButton("/immagini/LOGO.png", 200, 120);
        logoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "click logo, fare in modo che venga evidenziato con il mouse. redirect alla home"));

        JButton bellButton = createIconButton("/immagini/bell.png", 30, 30);
        bellButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "click campanella, fare in modo che venga evidenziato con il mouse. tendina notifiche"));

        JButton userButton = createIconButton("/immagini/user.png", 30, 30);
        userButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "click utente, fare in modo che venga evidenziato con il mouse. tendina gestione account"));

        // Pannello per icone in alto a destra
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(Color.WHITE);
        topRightPanel.add(bellButton);
        topRightPanel.add(userButton);

        // Aggiunta al topPanel
        topPanel.add(logoButton, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---- PANNELLO CENTRALE CON PULSANTI ----
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JButton addPropertyButton = new JButton("Inserisci un nuovo immobile sulla piattaforma");
        JButton viewRequestsButton = new JButton("Visualizza le richieste di appuntamento");
        JButton viewCalendarButton = new JButton("Visualizza il calendario con gli appuntamenti concordati");

        addPropertyButton.setPreferredSize(new Dimension(500, 120));
        viewRequestsButton.setPreferredSize(new Dimension(500, 120));
        viewCalendarButton.setPreferredSize(new Dimension(500, 120));

        // Aggiungi i pulsanti al GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Margini tra i pulsanti
        centerPanel.add(addPropertyButton, gbc);

        gbc.gridy = 1;
        centerPanel.add(viewRequestsButton, gbc);

        gbc.gridy = 2;
        centerPanel.add(viewCalendarButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Imposta la finestra visibile
        setVisible(true);
    }

    /**
     * Metodo per creare un pulsante con icona personalizzata
     */
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

    // Codice per far partire la finestra senza la necessità del controller
    // Ovviamente metteremo a posto appena abbiamo tutto a disposizione
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeAgente::new);
    }
}
