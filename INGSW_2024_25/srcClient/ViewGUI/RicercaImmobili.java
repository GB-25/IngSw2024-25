package ViewGUI;
import javax.swing.*;

import Class.User;
import Controller.Controller;

import java.awt.*;

public class RicercaImmobili extends JFrame {

    public RicercaImmobili(Controller c, User user) {
        setTitle("Ricerca immobili - DietiEstates25");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWidths = new int[] {100, 80, 30, 30, 40, 100, 100};
        gbl_mainPanel.rowHeights = new int[]{33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 0, 0, 33, 0, 0, 0, 0, 0};
        gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0};
        gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        mainPanel.setLayout(gbl_mainPanel);

        // Aggiungi il pannello principale al JFrame
        getContentPane().add(mainPanel);
                                                                        
                                                                                // Pannello principale
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); // Dimensioni ridotte
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Imposta un font più piccolo
        indietroButton.addActionListener(e -> {dispose(); new HomeCliente(c, user);});
        GridBagConstraints gbc_indietroButton = new GridBagConstraints();
        gbc_indietroButton.insets = new Insets(0, 0, 5, 5);
        gbc_indietroButton.gridx = 1;
        gbc_indietroButton.gridy = 0;
        mainPanel.add(indietroButton, gbc_indietroButton);
        
        JLabel lblNewLabel = new JLabel("Scegli i parametri della ricerca");
        lblNewLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 4;
        gbc_lblNewLabel.gridy = 0;
        mainPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        // Posizione
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel label_9 = new JLabel("Posizione:");
        mainPanel.add(label_9, gbc);
        JTextField cittaField = new JTextField();
        GridBagConstraints gbc_cittaField = new GridBagConstraints();
        gbc_cittaField.fill = GridBagConstraints.HORIZONTAL;
        gbc_cittaField.insets = new Insets(0, 0, 5, 5);
        gbc_cittaField.gridx = 5;
        gbc_cittaField.gridy = 2;
        mainPanel.add(cittaField, gbc_cittaField);
        	
        // Tipo di annuncio
        GridBagConstraints gbc_1 = new GridBagConstraints();
        gbc_1.fill = GridBagConstraints.BOTH;
        gbc_1.insets = new Insets(0, 0, 5, 5);
        gbc_1.gridx = 1;
        gbc_1.gridy = 3;
        JLabel label_2 = new JLabel("Tipo di annuncio:");
        mainPanel.add(label_2, gbc_1);
        JComboBox<String> tipoAnnuncio = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        GridBagConstraints gbc_annuncioField = new GridBagConstraints();
        gbc_annuncioField.insets = new Insets(0, 0, 5, 5);
        gbc_annuncioField.fill = GridBagConstraints.HORIZONTAL;
        gbc_annuncioField.gridx = 5;
        gbc_annuncioField.gridy = 3;
        mainPanel.add(tipoAnnuncio, gbc_annuncioField);
        
        
        // Tipo di casa
        GridBagConstraints gbc_2 = new GridBagConstraints();
        gbc_2.fill = GridBagConstraints.BOTH;
        gbc_2.insets = new Insets(0, 0, 5, 5);
        gbc_2.gridx = 1;
        gbc_2.gridy = 4;
        JLabel label_6 = new JLabel("Tipo di casa:");
        mainPanel.add(label_6, gbc_2);
        JComboBox<String> tipoCasa = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        GridBagConstraints gbc_casaField = new GridBagConstraints();
        gbc_casaField.insets = new Insets(0, 0, 5, 5);
        gbc_casaField.fill = GridBagConstraints.HORIZONTAL;
        gbc_casaField.gridx = 5;
        gbc_casaField.gridy = 4;
        mainPanel.add(tipoCasa, gbc_casaField);
        
        // Classe energetica
        GridBagConstraints gbc_3 = new GridBagConstraints();
        gbc_3.fill = GridBagConstraints.BOTH;
        gbc_3.insets = new Insets(0, 0, 5, 5);
        gbc_3.gridx = 1;
        gbc_3.gridy = 5;
        JLabel label_4 = new JLabel("Classe energetica:");
        mainPanel.add(label_4, gbc_3);
        JComboBox<String> tipoClasse = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        GridBagConstraints gbc_classeField = new GridBagConstraints();
        gbc_classeField.insets = new Insets(0, 0, 5, 5);
        gbc_classeField.fill = GridBagConstraints.HORIZONTAL;
        gbc_classeField.gridx = 5;
        gbc_classeField.gridy = 5;
        mainPanel.add(tipoClasse, gbc_classeField);
        
        // Prezzo minimo e massimo
        GridBagConstraints gbc_4 = new GridBagConstraints();
        gbc_4.fill = GridBagConstraints.BOTH;
        gbc_4.insets = new Insets(0, 0, 5, 5);
        gbc_4.gridx = 1;
        gbc_4.gridy = 6;
        JLabel label_1 = new JLabel("Prezzo minimo:");
        mainPanel.add(label_1, gbc_4);
        
        GridBagConstraints gbc_5 = new GridBagConstraints();
        gbc_5.fill = GridBagConstraints.BOTH;
        gbc_5.insets = new Insets(0, 0, 5, 5);
        gbc_5.gridx = 5;
        gbc_5.gridy = 6;
        JLabel label_3 = new JLabel("Prezzo massimo:");
        mainPanel.add(label_3, gbc_5);
        JTextField prezzoMinField = new JTextField();
        GridBagConstraints gbc_prezzoMinField = new GridBagConstraints();
        gbc_prezzoMinField.fill = GridBagConstraints.HORIZONTAL;
        gbc_prezzoMinField.insets = new Insets(0, 0, 5, 5);
        gbc_prezzoMinField.gridx = 1;
        gbc_prezzoMinField.gridy = 7;
        mainPanel.add(prezzoMinField, gbc_prezzoMinField);
        JTextField prezzoMaxField = new JTextField();
        GridBagConstraints gbc_prezzoMaxField = new GridBagConstraints();
        gbc_prezzoMaxField.fill = GridBagConstraints.HORIZONTAL;
        gbc_prezzoMaxField.insets = new Insets(0, 0, 5, 5);
        gbc_prezzoMaxField.gridx = 5;
        gbc_prezzoMaxField.gridy = 7;
        mainPanel.add(prezzoMaxField, gbc_prezzoMaxField);
        
        // Ascensore, Giardino, Terrazzo, In condominio
        GridBagConstraints gbc_6 = new GridBagConstraints();
        gbc_6.fill = GridBagConstraints.VERTICAL;
        gbc_6.insets = new Insets(0, 0, 5, 5);
        gbc_6.gridx = 1;
        gbc_6.gridy = 9;
        JLabel label_7 = new JLabel("Ascensore");
        mainPanel.add(label_7, gbc_6);
        
        GridBagConstraints gbc_7 = new GridBagConstraints();
        gbc_7.fill = GridBagConstraints.VERTICAL;
        gbc_7.insets = new Insets(0, 0, 5, 5);
        gbc_7.gridx = 5;
        gbc_7.gridy = 9;
        JLabel label_10 = new JLabel("Giardino");
        mainPanel.add(label_10, gbc_7);
        JCheckBox ascensoreCheckBox = new JCheckBox();
        ascensoreCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_ascensoreCheckBox = new GridBagConstraints();
        gbc_ascensoreCheckBox.fill = GridBagConstraints.VERTICAL;
        gbc_ascensoreCheckBox.insets = new Insets(0, 0, 5, 5);
        gbc_ascensoreCheckBox.gridx = 1;
        gbc_ascensoreCheckBox.gridy = 10;
        mainPanel.add(ascensoreCheckBox, gbc_ascensoreCheckBox);
        JCheckBox giardinoCheckBox = new JCheckBox();
        giardinoCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_giardinoCheckBox = new GridBagConstraints();
        gbc_giardinoCheckBox.fill = GridBagConstraints.VERTICAL;
        gbc_giardinoCheckBox.insets = new Insets(0, 0, 5, 5);
        gbc_giardinoCheckBox.gridx = 5;
        gbc_giardinoCheckBox.gridy = 10;
        mainPanel.add(giardinoCheckBox, gbc_giardinoCheckBox);
        
        GridBagConstraints gbc_8 = new GridBagConstraints();
        gbc_8.fill = GridBagConstraints.VERTICAL;
        gbc_8.insets = new Insets(0, 0, 5, 5);
        gbc_8.gridx = 1;
        gbc_8.gridy = 12;
        JLabel label_5 = new JLabel("Terrazzo");
        mainPanel.add(label_5, gbc_8);
        
		GridBagConstraints gbc_9 = new GridBagConstraints();
		gbc_9.fill = GridBagConstraints.VERTICAL;
		gbc_9.insets = new Insets(0, 0, 5, 5);
		gbc_9.gridx = 5;
		gbc_9.gridy = 12;
		JLabel label_8 = new JLabel("In condominio");
		mainPanel.add(label_8, gbc_9);
		JCheckBox terrazzoCheckBox = new JCheckBox();
		terrazzoCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_terrazzoCheckBox = new GridBagConstraints();
		gbc_terrazzoCheckBox.fill = GridBagConstraints.VERTICAL;
		gbc_terrazzoCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_terrazzoCheckBox.gridx = 1;
		gbc_terrazzoCheckBox.gridy = 13;
		mainPanel.add(terrazzoCheckBox, gbc_terrazzoCheckBox);
		JCheckBox condominioCheckBox = new JCheckBox();
		condominioCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_condominioCheckBox = new GridBagConstraints();
		gbc_condominioCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_condominioCheckBox.gridx = 5;
		gbc_condominioCheckBox.gridy = 13;
		mainPanel.add(condominioCheckBox, gbc_condominioCheckBox);
		
		// Bottone di ricerca
		JButton cercaButton = new JButton("Cerca");
		cercaButton.setForeground(new Color(255, 255, 255));
		cercaButton.setBackground(new Color(40, 132, 212));
		cercaButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		cercaButton.setPreferredSize(new Dimension(150, 50));
		GridBagConstraints gbc_cercaButton = new GridBagConstraints();
		gbc_cercaButton.insets = new Insets(0, 0, 5, 5);
		gbc_cercaButton.fill = GridBagConstraints.VERTICAL;
		gbc_cercaButton.gridx = 4;
		gbc_cercaButton.gridy = 15;
		mainPanel.add(cercaButton, gbc_cercaButton);
		
        // Mostra la finestra
        setVisible(true);
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new RicercaImmobili());
    }
}