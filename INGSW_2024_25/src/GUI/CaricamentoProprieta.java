package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaricamentoProprieta extends JFrame {

    public CaricamentoProprieta() {
        // Configurazione finestra principale
        setTitle("DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello principale
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.WEST);

        // GridBagConstraints per layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 5, 20);

        // Campi del modulo
        JLabel lblTitle = new JLabel("Titolo:");
        JTextField txtTitle = new JTextField(20);

        JLabel lblType = new JLabel("Tipo di immobile:");
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"", "Appartamento", "Casa"});

        JLabel lblAdType = new JLabel("Tipo di annuncio:");
        JComboBox<String> cmbAdType = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});

        JLabel lblPrice = new JLabel("Prezzo:");
        JTextField txtPrice = new JTextField(10);

        JLabel lblDescription = new JLabel("Descrizione:");
        JTextArea txtDescription = new JTextArea(4, 20);

        JLabel lblDimensions = new JLabel("Dimensioni alloggio:");
        JTextField txtWidth = new JTextField(5);
        txtWidth.setSize(new Dimension(1,21)); // Dimensione fissa
        JTextField txtHeight = new JTextField(5);
        txtHeight.setSize(new Dimension(1, 21)); // Dimensione fissa

        JLabel lblRooms = new JLabel("Numero di stanze:");
        JTextField cmbRooms = new JTextField(10);

        JLabel lblCondo = new JLabel("Appartamento in condominio:");
        JComboBox<String> toggleCondo = new JComboBox<>(new String[]{"No", "Sì"});

        JLabel lblEnergyClass = new JLabel("Classe energetica:");
        JComboBox<String> cmbEnergyClass = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});

        JLabel lblElevator = new JLabel("Ascensore:");
        JComboBox<String> toggleElevator = new JComboBox<>(new String[]{"No", "Sì"});

        JButton btnAddService = new JButton("+ Aggiungi servizio");
        JButton btnUpload = new JButton("CARICA");

        // Posizionamento dei campi
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(lblTitle, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtTitle, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblType, gbc);
        gbc.gridx = 1;
        mainPanel.add(cmbType, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblAdType, gbc);
        gbc.gridx = 1;
        mainPanel.add(cmbAdType, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(lblPrice, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(lblDescription, gbc);
        gbc.gridx = 1;
        mainPanel.add(new JScrollPane(txtDescription), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(lblDimensions, gbc);
        gbc.gridx = 1;
        mainPanel.add(txtWidth, gbc);
        gbc.gridx = 2;
        mainPanel.add(new JLabel("X"), gbc);

        gbc.gridx = 3;
        mainPanel.add(txtHeight, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(lblRooms, gbc);
        gbc.gridx = 1;
        mainPanel.add(cmbRooms, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(lblCondo, gbc);
        gbc.gridx = 1;
        mainPanel.add(toggleCondo, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(lblEnergyClass, gbc);
        gbc.gridx = 1;
        mainPanel.add(cmbEnergyClass, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        mainPanel.add(lblElevator, gbc);
        gbc.gridx = 1;
        mainPanel.add(toggleElevator, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        mainPanel.add(btnAddService, gbc);

        gbc.gridx = 1; gbc.gridy = 11;
        gbc.gridwidth = 2;
        btnUpload.setBackground(new Color(0, 153, 51));
        btnUpload.setForeground(Color.WHITE);
        mainPanel.add(btnUpload, gbc);

        // Attivazione pop-up al tasto "CARICA"
        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check per i campi obbligatori se sono vuoti
                if (txtTitle.getText().trim().isEmpty() ||
                        cmbType.getSelectedIndex() == 0 ||
                        cmbAdType.getSelectedIndex() == 0 ||
                        txtPrice.getText().trim().isEmpty() ||
                        txtDescription.getText().trim().isEmpty() ||
                        txtWidth.getText().trim().isEmpty() ||
                        txtHeight.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CaricamentoProprieta.this,
                            "CAUSA: Non sono stati riempiti tutti i campi. Controllare che tutti i campi siano stati riempiti, per poi procedere con il caricamento sulla piattaforma.",
                            "Caricamento non effettuato",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // So che c'è il warning dato che la response non è stata usata ancora
                    // Da associare con il collegamento alla schermata di conferma
                    int response = JOptionPane.showConfirmDialog(CaricamentoProprieta.this,
                            "Sei sicuro di voler caricare l'immobile?",
                            "Conferma Caricamento",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                }
            }
        });

        // Imposta la finestra visibile
        setVisible(true);
    }


    // Codice per far partire la finestra senza la necessità del controller
    // Ovviamente metteremo a posto appena abbiamo tutto a disposizione
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CaricamentoProprieta::new);
    }
}
