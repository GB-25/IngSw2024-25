package ViewGUI;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import Class.Immobile;
import Class.User;
import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class RicercaImmobili extends JFrame {

	private JFrame finestraCorrente;
	
    public RicercaImmobili(Controller c, User user) {
    	FlatLightLaf.setup(new FlatLightLaf());
    	finestraCorrente = this;
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
        
        JLabel lblParametri = new JLabel("Scegli i parametri della ricerca");
        lblParametri.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        GridBagConstraints gbc_lblParametri = new GridBagConstraints();
        gbc_lblParametri.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblParametri.insets = new Insets(0, 0, 5, 5);
        gbc_lblParametri.gridx = 4;
        gbc_lblParametri.gridy = 0;
        mainPanel.add(lblParametri, gbc_lblParametri);
        
        // Posizione
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel lblPosizione = new JLabel("Posizione:");
        mainPanel.add(lblPosizione, gbc);
        JTextField posizioneField = new JTextField();
        GridBagConstraints gbc_posizioneField = new GridBagConstraints();
        gbc_posizioneField.fill = GridBagConstraints.HORIZONTAL;
        gbc_posizioneField.insets = new Insets(0, 0, 5, 5);
        gbc_posizioneField.gridx = 5;
        gbc_posizioneField.gridy = 2;
        mainPanel.add(posizioneField, gbc_posizioneField);
        	
        // Tipo di annuncio
        GridBagConstraints gbc_lblAnnuncio = new GridBagConstraints();
        gbc_lblAnnuncio.fill = GridBagConstraints.BOTH;
        gbc_lblAnnuncio.insets = new Insets(0, 0, 5, 5);
        gbc_lblAnnuncio.gridx = 1;
        gbc_lblAnnuncio.gridy = 3;
        JLabel lblAnnuncio = new JLabel("Tipo di annuncio:");
        mainPanel.add(lblAnnuncio, gbc_lblAnnuncio);
        JComboBox<String> tipoAnnuncio = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        GridBagConstraints gbc_tipoAnnuncio = new GridBagConstraints();
        gbc_tipoAnnuncio.insets = new Insets(0, 0, 5, 5);
        gbc_tipoAnnuncio.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipoAnnuncio.gridx = 5;
        gbc_tipoAnnuncio.gridy = 3;
        mainPanel.add(tipoAnnuncio, gbc_tipoAnnuncio);
        
        
        // Tipo di casa
        GridBagConstraints gbc_lblCasa = new GridBagConstraints();
        gbc_lblCasa.fill = GridBagConstraints.BOTH;
        gbc_lblCasa.insets = new Insets(0, 0, 5, 5);
        gbc_lblCasa.gridx = 1;
        gbc_lblCasa.gridy = 4;
        JLabel lblCasa = new JLabel("Tipo di casa:");
        mainPanel.add(lblCasa, gbc_lblCasa);
        JComboBox<String> tipoCasa = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        GridBagConstraints gbc_tipoCasa = new GridBagConstraints();
        gbc_tipoCasa.insets = new Insets(0, 0, 5, 5);
        gbc_tipoCasa.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipoCasa.gridx = 5;
        gbc_tipoCasa.gridy = 4;
        mainPanel.add(tipoCasa, gbc_tipoCasa);
        
        // Classe energetica
        GridBagConstraints gbc_lblClasse = new GridBagConstraints();
        gbc_lblClasse.fill = GridBagConstraints.BOTH;
        gbc_lblClasse.insets = new Insets(0, 0, 5, 5);
        gbc_lblClasse.gridx = 1;
        gbc_lblClasse.gridy = 5;
        JLabel lblClasse = new JLabel("Classe energetica:");
        mainPanel.add(lblClasse, gbc_lblClasse);
        JComboBox<String> tipoClasse = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        GridBagConstraints gbc_tipoClasse = new GridBagConstraints();
        gbc_tipoClasse.insets = new Insets(0, 0, 5, 5);
        gbc_tipoClasse.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipoClasse.gridx = 5;
        gbc_tipoClasse.gridy = 5;
        mainPanel.add(tipoClasse, gbc_tipoClasse);
        
        // Prezzo minimo e massimo
        GridBagConstraints gbc_prezzoMinimo = new GridBagConstraints();
        gbc_prezzoMinimo.fill = GridBagConstraints.BOTH;
        gbc_prezzoMinimo.insets = new Insets(0, 0, 5, 5);
        gbc_prezzoMinimo.gridx = 1;
        gbc_prezzoMinimo.gridy = 6;
        JLabel lblPrezzoMinimo = new JLabel("Prezzo minimo:");
        mainPanel.add(lblPrezzoMinimo, gbc_prezzoMinimo);
        
        GridBagConstraints gbc_lblPrezzoMassimo = new GridBagConstraints();
        gbc_lblPrezzoMassimo.fill = GridBagConstraints.BOTH;
        gbc_lblPrezzoMassimo.insets = new Insets(0, 0, 5, 5);
        gbc_lblPrezzoMassimo.gridx = 5;
        gbc_lblPrezzoMassimo.gridy = 6;
        JLabel lblPrezzoMassimo = new JLabel("Prezzo massimo:");
        mainPanel.add(lblPrezzoMassimo, gbc_lblPrezzoMassimo);
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
        GridBagConstraints gbc_lblAscensore = new GridBagConstraints();
        gbc_lblAscensore.fill = GridBagConstraints.VERTICAL;
        gbc_lblAscensore.insets = new Insets(0, 0, 5, 5);
        gbc_lblAscensore.gridx = 1;
        gbc_lblAscensore.gridy = 9;
        JLabel lblAscensore = new JLabel("AscmbTypecensore");
        mainPanel.add(lblAscensore, gbc_lblAscensore);
        
        GridBagConstraints gbc_lblGiardino = new GridBagConstraints();
        gbc_lblGiardino.fill = GridBagConstraints.VERTICAL;
        gbc_lblGiardino.insets = new Insets(0, 0, 5, 5);
        gbc_lblGiardino.gridx = 5;
        gbc_lblGiardino.gridy = 9;
        JLabel lblGiardino = new JLabel("Giardino");
        mainPanel.add(lblGiardino, gbc_lblGiardino);
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
        
        GridBagConstraints gbc_lblTerrazzo = new GridBagConstraints();
        gbc_lblTerrazzo.fill = GridBagConstraints.VERTICAL;
        gbc_lblTerrazzo.insets = new Insets(0, 0, 5, 5);
        gbc_lblTerrazzo.gridx = 1;
        gbc_lblTerrazzo.gridy = 12;
        JLabel lblTerrazzo = new JLabel("Terrazzo");
        mainPanel.add(lblTerrazzo, gbc_lblTerrazzo);
        
		GridBagConstraints gbc_lblCondominio = new GridBagConstraints();
		gbc_lblCondominio.fill = GridBagConstraints.VERTICAL;
		gbc_lblCondominio.insets = new Insets(0, 0, 5, 5);
		gbc_lblCondominio.gridx = 5;
		gbc_lblCondominio.gridy = 12;
		JLabel lblCondominio = new JLabel("In condominio");
		mainPanel.add(lblCondominio, gbc_lblCondominio);
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
		prezzoMinField.setText("0");
		prezzoMaxField.setText("0");
		// Bottone di ricerca
		JButton cercaButton = new JButton("Cerca");
		cercaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			c.createSchermataCaricamento(finestraCorrente, "Caricamento");
    		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
				String indirizzo = posizioneField.getText();
				if (indirizzo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Devi inserire almeno una città dove cercare", "Errore", JOptionPane.ERROR_MESSAGE);
				} else {
					double prezzoMin = Double.parseDouble(prezzoMinField.getText());
					double prezzoMax = Double.parseDouble(prezzoMaxField.getText());
					String classe = (String) tipoClasse.getSelectedItem();
					String tipo = (String) tipoCasa.getSelectedItem();
					String annuncio = (String) tipoAnnuncio.getSelectedItem();
					boolean giardino = c.controlCheckBox(giardinoCheckBox);
					boolean terrazzo = c.controlCheckBox(terrazzoCheckBox);
					boolean condominio = c.controlCheckBox(condominioCheckBox);
					boolean ascensore = c.controlCheckBox(ascensoreCheckBox);
					ArrayList<Immobile> ricerca = c.ricercaImmobili(prezzoMin, prezzoMax, classe, indirizzo, tipo, annuncio, ascensore, condominio, terrazzo, giardino);
					if (!ricerca.isEmpty()) {
						c.showResultImmobili(finestraCorrente, user, ricerca, indirizzo);
					}}
					return null;}
				 @Override
                 protected void done() {
                     dispose();
                 }
			};
			worker.execute();}});
		
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
