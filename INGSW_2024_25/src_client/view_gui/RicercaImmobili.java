package view_gui;
import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.User;
import controller.Controller;

import java.awt.*;
import java.util.ArrayList;

public class RicercaImmobili extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame finestraCorrente;
	private SchermataCaricamento schermataCaricamento;
	private static final String ERRORE = "Errore";
	/**
	 * Costruttore
	 */
    public RicercaImmobili(Controller c, User user) {
    	FlatLaf.setup(new FlatLightLaf());
    	finestraCorrente = this;
        setTitle("Ricerca immobili - DietiEstates25");
        setSize(700, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        GridBagLayout gblMainPanel = new GridBagLayout();
        gblMainPanel.columnWidths = new int[] {100, 0, 80, 30, 30, 40, 100, 100};
        gblMainPanel.rowHeights = new int[]{33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 0, 0, 33, 0, 0, 0, 0, 0, 0};
        gblMainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
        gblMainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        mainPanel.setLayout(gblMainPanel);

        getContentPane().add(mainPanel);
        

        JButton indietroButton = new JButton("Torna alla Home");
        indietroButton.setPreferredSize(new Dimension(60, 25)); 
        indietroButton.setForeground(new Color(255, 255, 255));
        indietroButton.setBackground(new Color(40, 132, 212));
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> {dispose(); new HomeGenerale(c, user);});
        GridBagConstraints gbcIndietroButton = new GridBagConstraints();
        gbcIndietroButton.fill = GridBagConstraints.HORIZONTAL;
        gbcIndietroButton.insets = new Insets(0, 0, 5, 5);
        gbcIndietroButton.gridx = 2;
        gbcIndietroButton.gridy = 0;
        mainPanel.add(indietroButton, gbcIndietroButton);
        
        JLabel lblParametri = new JLabel("Scegli i parametri della ricerca");
        lblParametri.setHorizontalAlignment(SwingConstants.CENTER);
        lblParametri.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        GridBagConstraints gbcLblParametri = new GridBagConstraints();
        gbcLblParametri.fill = GridBagConstraints.HORIZONTAL;
        gbcLblParametri.insets = new Insets(0, 0, 5, 5);
        gbcLblParametri.gridx = 5;
        gbcLblParametri.gridy = 0;
        mainPanel.add(lblParametri, gbcLblParametri);
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel lblPosizione = new JLabel("Posizione:");
        mainPanel.add(lblPosizione, gbc);
        JTextField posizioneField = new JTextField();
        GridBagConstraints gbcPosizioneField = new GridBagConstraints();
        gbcPosizioneField.fill = GridBagConstraints.HORIZONTAL;
        gbcPosizioneField.insets = new Insets(0, 0, 5, 5);
        gbcPosizioneField.gridx = 6;
        gbcPosizioneField.gridy = 2;
        mainPanel.add(posizioneField, gbcPosizioneField);
        	

        GridBagConstraints gbcLblAnnuncio = new GridBagConstraints();
        gbcLblAnnuncio.fill = GridBagConstraints.BOTH;
        gbcLblAnnuncio.insets = new Insets(0, 0, 5, 5);
        gbcLblAnnuncio.gridx = 2;
        gbcLblAnnuncio.gridy = 3;
        JLabel lblAnnuncio = new JLabel("Tipo di annuncio:");
        mainPanel.add(lblAnnuncio, gbcLblAnnuncio);
        JComboBox<String> tipoAnnuncio = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        GridBagConstraints gbcTipoAnnuncio = new GridBagConstraints();
        gbcTipoAnnuncio.insets = new Insets(0, 0, 5, 5);
        gbcTipoAnnuncio.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoAnnuncio.gridx = 6;
        gbcTipoAnnuncio.gridy = 3;
        mainPanel.add(tipoAnnuncio, gbcTipoAnnuncio);

        GridBagConstraints gbcLblCasa = new GridBagConstraints();
        gbcLblCasa.fill = GridBagConstraints.BOTH;
        gbcLblCasa.insets = new Insets(0, 0, 5, 5);
        gbcLblCasa.gridx = 2;
        gbcLblCasa.gridy = 4;
        JLabel lblCasa = new JLabel("Tipo di casa:");
        mainPanel.add(lblCasa, gbcLblCasa);
        JComboBox<String> tipoCasa = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        GridBagConstraints gbcTipoCasa = new GridBagConstraints();
        gbcTipoCasa.insets = new Insets(0, 0, 5, 5);
        gbcTipoCasa.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoCasa.gridx = 6;
        gbcTipoCasa.gridy = 4;
        mainPanel.add(tipoCasa, gbcTipoCasa);

        GridBagConstraints gbcLblClasse = new GridBagConstraints();
        gbcLblClasse.fill = GridBagConstraints.BOTH;
        gbcLblClasse.insets = new Insets(0, 0, 5, 5);
        gbcLblClasse.gridx = 2;
        gbcLblClasse.gridy = 5;
        JLabel lblClasse = new JLabel("Classe energetica:");
        mainPanel.add(lblClasse, gbcLblClasse);
        JComboBox<String> tipoClasse = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        GridBagConstraints gbcTipoClasse = new GridBagConstraints();
        gbcTipoClasse.insets = new Insets(0, 0, 5, 5);
        gbcTipoClasse.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoClasse.gridx = 6;
        gbcTipoClasse.gridy = 5;
        mainPanel.add(tipoClasse, gbcTipoClasse);

        GridBagConstraints gbcPrezzoMinimo = new GridBagConstraints();
        gbcPrezzoMinimo.fill = GridBagConstraints.BOTH;
        gbcPrezzoMinimo.insets = new Insets(0, 0, 5, 5);
        gbcPrezzoMinimo.gridx = 2;
        gbcPrezzoMinimo.gridy = 6;
        JLabel lblPrezzoMinimo = new JLabel("Prezzo minimo:");
        mainPanel.add(lblPrezzoMinimo, gbcPrezzoMinimo);
        
        GridBagConstraints gbcLblPrezzoMassimo = new GridBagConstraints();
        gbcLblPrezzoMassimo.fill = GridBagConstraints.BOTH;
        gbcLblPrezzoMassimo.insets = new Insets(0, 0, 5, 5);
        gbcLblPrezzoMassimo.gridx = 6;
        gbcLblPrezzoMassimo.gridy = 6;
        JLabel lblPrezzoMassimo = new JLabel("Prezzo massimo:");
        mainPanel.add(lblPrezzoMassimo, gbcLblPrezzoMassimo);
        JTextField prezzoMinField = new JTextField();
        GridBagConstraints gbcPrezzoMinField = new GridBagConstraints();
        gbcPrezzoMinField.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMinField.insets = new Insets(0, 0, 5, 5);
        gbcPrezzoMinField.gridx = 2;
        gbcPrezzoMinField.gridy = 7;
        mainPanel.add(prezzoMinField, gbcPrezzoMinField);
        JTextField prezzoMaxField = new JTextField();
        GridBagConstraints gbcPrezzoMaxField = new GridBagConstraints();
        gbcPrezzoMaxField.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMaxField.insets = new Insets(0, 0, 5, 5);
        gbcPrezzoMaxField.gridx = 6;
        gbcPrezzoMaxField.gridy = 7;
        mainPanel.add(prezzoMaxField, gbcPrezzoMaxField);

        GridBagConstraints gbcLblAscensore = new GridBagConstraints();
        gbcLblAscensore.fill = GridBagConstraints.VERTICAL;
        gbcLblAscensore.insets = new Insets(0, 0, 5, 5);
        gbcLblAscensore.gridx = 2;
        gbcLblAscensore.gridy = 9;
        JLabel lblAscensore = new JLabel("Ascensore");
        mainPanel.add(lblAscensore, gbcLblAscensore);
        
        GridBagConstraints gbcLblGiardino = new GridBagConstraints();
        gbcLblGiardino.fill = GridBagConstraints.VERTICAL;
        gbcLblGiardino.insets = new Insets(0, 0, 5, 5);
        gbcLblGiardino.gridx = 6;
        gbcLblGiardino.gridy = 9;
        JLabel lblGiardino = new JLabel("Giardino");
        mainPanel.add(lblGiardino, gbcLblGiardino);
        JCheckBox ascensoreCheckBox = new JCheckBox();
        ascensoreCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbcAscensoreCheckBox = new GridBagConstraints();
        gbcAscensoreCheckBox.fill = GridBagConstraints.VERTICAL;
        gbcAscensoreCheckBox.insets = new Insets(0, 0, 5, 5);
        gbcAscensoreCheckBox.gridx = 2;
        gbcAscensoreCheckBox.gridy = 10;
        mainPanel.add(ascensoreCheckBox, gbcAscensoreCheckBox);
        JCheckBox giardinoCheckBox = new JCheckBox();
        giardinoCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbcGiardinoCheckBox = new GridBagConstraints();
        gbcGiardinoCheckBox.fill = GridBagConstraints.VERTICAL;
        gbcGiardinoCheckBox.insets = new Insets(0, 0, 5, 5);
        gbcGiardinoCheckBox.gridx = 6;
        gbcGiardinoCheckBox.gridy = 10;
        mainPanel.add(giardinoCheckBox, gbcGiardinoCheckBox);
        
        GridBagConstraints gbcLblTerrazzo = new GridBagConstraints();
        gbcLblTerrazzo.fill = GridBagConstraints.VERTICAL;
        gbcLblTerrazzo.insets = new Insets(0, 0, 5, 5);
        gbcLblTerrazzo.gridx = 2;
        gbcLblTerrazzo.gridy = 12;
        JLabel lblTerrazzo = new JLabel("Terrazzo");
        mainPanel.add(lblTerrazzo, gbcLblTerrazzo);
        
		GridBagConstraints gbcLblCondominio = new GridBagConstraints();
		gbcLblCondominio.fill = GridBagConstraints.VERTICAL;
		gbcLblCondominio.insets = new Insets(0, 0, 5, 5);
		gbcLblCondominio.gridx = 6;
		gbcLblCondominio.gridy = 12;
		JLabel lblCondominio = new JLabel("In condominio");
		mainPanel.add(lblCondominio, gbcLblCondominio);
		JCheckBox terrazzoCheckBox = new JCheckBox();
		terrazzoCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbcTerrazzoCheckBox = new GridBagConstraints();
		gbcTerrazzoCheckBox.fill = GridBagConstraints.VERTICAL;
		gbcTerrazzoCheckBox.insets = new Insets(0, 0, 5, 5);
		gbcTerrazzoCheckBox.gridx = 2;
		gbcTerrazzoCheckBox.gridy = 13;
		mainPanel.add(terrazzoCheckBox, gbcTerrazzoCheckBox);
		JCheckBox condominioCheckBox = new JCheckBox();
		condominioCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbcCondominioCheckBox = new GridBagConstraints();
		gbcCondominioCheckBox.insets = new Insets(0, 0, 5, 5);
		gbcCondominioCheckBox.gridx = 6;
		gbcCondominioCheckBox.gridy = 13;
		mainPanel.add(condominioCheckBox, gbcCondominioCheckBox);
		prezzoMinField.setText("0");
		prezzoMaxField.setText("0");
				
						JButton cercaButton = new JButton("Cerca");
						cercaButton.addActionListener(e -> {
							schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
    		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
								String indirizzo = posizioneField.getText();
								boolean prezzoMinimo = Controller.isNumeric(prezzoMinField.getText());
								boolean prezzoMassimo = Controller.isNumeric(prezzoMaxField.getText());
								
								if (indirizzo.isEmpty()) {
									JOptionPane.showMessageDialog(null, "Devi inserire almeno una città dove cercare", ERRORE, JOptionPane.ERROR_MESSAGE);
								} else if (!prezzoMinimo || !prezzoMassimo) {
									JOptionPane.showMessageDialog(null, "Occhio al prezzo! Ci sono dei caratteri non numerici", ERRORE, JOptionPane.ERROR_MESSAGE);
								}else {
									double prezzoMin = Double.parseDouble(prezzoMinField.getText());
									double prezzoMax = Double.parseDouble(prezzoMaxField.getText());
									String classe = (String) tipoClasse.getSelectedItem();
									String tipo = (String) tipoCasa.getSelectedItem();
									String annuncio = (String) tipoAnnuncio.getSelectedItem();
									boolean giardino = c.controlCheckBox(giardinoCheckBox);
									boolean terrazzo = c.controlCheckBox(terrazzoCheckBox);
									boolean condominio = c.controlCheckBox(condominioCheckBox);
									boolean ascensore = c.controlCheckBox(ascensoreCheckBox);
									ComposizioneImmobile composizione = new ComposizioneImmobile(terrazzo, giardino, ascensore, condominio);
									Immobile immobile = new Immobile(classe, indirizzo, tipo, annuncio);
									ArrayList<Immobile> ricerca = (ArrayList<Immobile>) c.ricercaImmobili(prezzoMin, prezzoMax, immobile, composizione);
									if (!c.checkPrezzi(prezzoMin, prezzoMax)) {		
										JOptionPane.showMessageDialog(null, "Errore durante la ricerca. Prova con altri parametri", ERRORE , JOptionPane.ERROR_MESSAGE);
									}
									if (!ricerca.isEmpty()) {
										c.showResultImmobili(finestraCorrente, user, ricerca, indirizzo);
									}
									else {
										JOptionPane.showMessageDialog(null, "Non sono stati trovati immobili che rispettano questi criteri. Prova con altri parametri", ERRORE , JOptionPane.ERROR_MESSAGE);
									}
								}
									return null;}
								 @Override
                 protected void done() {
                     schermataCaricamento.close();
                 }
							};
							worker.execute();});
						
						JLabel lblMinimo = new JLabel("Si prega di inserire un prezzo minimo che parta ");
						GridBagConstraints gbcLblMinimo = new GridBagConstraints();
						gbcLblMinimo.insets = new Insets(0, 0, 5, 5);
						gbcLblMinimo.gridx = 5;
						gbcLblMinimo.gridy = 15;
						mainPanel.add(lblMinimo, gbcLblMinimo);
						
						JLabel lblMassimo = new JLabel("da 500 ed un prezzo massimo non oltre i 1000000.");
						GridBagConstraints gbcLblMassimo = new GridBagConstraints();
						gbcLblMassimo.insets = new Insets(0, 0, 5, 5);
						gbcLblMassimo.gridx = 5;
						gbcLblMassimo.gridy = 16;
						mainPanel.add(lblMassimo, gbcLblMassimo);
						
						cercaButton.setForeground(new Color(255, 255, 255));
						cercaButton.setBackground(new Color(40, 132, 212));
						cercaButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
						cercaButton.setPreferredSize(new Dimension(150, 50));
						GridBagConstraints gbcCercaButton = new GridBagConstraints();
						gbcCercaButton.insets = new Insets(0, 0, 0, 5);
						gbcCercaButton.fill = GridBagConstraints.VERTICAL;
						gbcCercaButton.gridx = 5;
						gbcCercaButton.gridy = 17;
						mainPanel.add(cercaButton, gbcCercaButton);

        setVisible(true);
    }
}
