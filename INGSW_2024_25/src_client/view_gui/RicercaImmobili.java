package view_gui;

import javax.swing.*;
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
     * 
     * Costruttore
     */
    public RicercaImmobili(Controller c, User user) {
        setBackground(Color.WHITE);
        FlatLightLaf.setup();
        
        finestraCorrente = this;
        setTitle("Ricerca Immobili - DietiEstates25");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.setBackground(Color.WHITE);

        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(40, 132, 212));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JButton backButton = new JButton("← Indietro");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40, 132, 212));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });

        JLabel titleLabel = new JLabel("RICERCA IMMOBILI");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            logoLabel.setText("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

       
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        
        GridBagConstraints gbcPosLabel = new GridBagConstraints();
        gbcPosLabel.insets = new Insets(10, 10, 10, 10);
        gbcPosLabel.anchor = GridBagConstraints.WEST;
        gbcPosLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPosLabel.gridx = 0;
        gbcPosLabel.gridy = 0;
        gbcPosLabel.weightx = 0.3;
        formPanel.add(new JLabel("Posizione:"), gbcPosLabel);

        GridBagConstraints gbcPosField = new GridBagConstraints();
        gbcPosField.insets = new Insets(10, 10, 10, 10);
        gbcPosField.anchor = GridBagConstraints.WEST;
        gbcPosField.fill = GridBagConstraints.HORIZONTAL;
        gbcPosField.gridx = 1;
        gbcPosField.gridy = 0;
        gbcPosField.weightx = 0.7;
        JTextField posizioneField = new JTextField();
        posizioneField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(posizioneField, gbcPosField);

        GridBagConstraints gbcTipoAnnuncioLabel = new GridBagConstraints();
        gbcTipoAnnuncioLabel.insets = new Insets(10, 10, 10, 10);
        gbcTipoAnnuncioLabel.anchor = GridBagConstraints.WEST;
        gbcTipoAnnuncioLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoAnnuncioLabel.gridx = 0;
        gbcTipoAnnuncioLabel.gridy = 1;
        gbcTipoAnnuncioLabel.weightx = 0.3;
        formPanel.add(new JLabel("Tipo Annuncio:"), gbcTipoAnnuncioLabel);

        GridBagConstraints gbcTipoAnnuncioCombo = new GridBagConstraints();
        gbcTipoAnnuncioCombo.insets = new Insets(10, 10, 10, 10);
        gbcTipoAnnuncioCombo.anchor = GridBagConstraints.WEST;
        gbcTipoAnnuncioCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoAnnuncioCombo.gridx = 1;
        gbcTipoAnnuncioCombo.gridy = 1;
        gbcTipoAnnuncioCombo.weightx = 0.7;
        JComboBox<String> tipoAnnuncio = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        tipoAnnuncio.setBackground(Color.WHITE);
        tipoAnnuncio.setPreferredSize(new Dimension(250, 30));
        formPanel.add(tipoAnnuncio, gbcTipoAnnuncioCombo);

      
        GridBagConstraints gbcTipoCasaLabel = new GridBagConstraints();
        gbcTipoCasaLabel.insets = new Insets(10, 10, 10, 10);
        gbcTipoCasaLabel.anchor = GridBagConstraints.WEST;
        gbcTipoCasaLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoCasaLabel.gridx = 0;
        gbcTipoCasaLabel.gridy = 2;
        gbcTipoCasaLabel.weightx = 0.3;
        formPanel.add(new JLabel("Tipo Casa:"), gbcTipoCasaLabel);

        GridBagConstraints gbcTipoCasaCombo = new GridBagConstraints();
        gbcTipoCasaCombo.insets = new Insets(10, 10, 10, 10);
        gbcTipoCasaCombo.anchor = GridBagConstraints.WEST;
        gbcTipoCasaCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoCasaCombo.gridx = 1;
        gbcTipoCasaCombo.gridy = 2;
        gbcTipoCasaCombo.weightx = 0.7;
        JComboBox<String> tipoCasa = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        tipoCasa.setBackground(Color.WHITE);
        tipoCasa.setPreferredSize(new Dimension(250, 30));
        formPanel.add(tipoCasa, gbcTipoCasaCombo);

        GridBagConstraints gbcTipoClasseLabel = new GridBagConstraints();
        gbcTipoClasseLabel.insets = new Insets(10, 10, 10, 10);
        gbcTipoClasseLabel.anchor = GridBagConstraints.WEST;
        gbcTipoClasseLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoClasseLabel.gridx = 0;
        gbcTipoClasseLabel.gridy = 3;
        gbcTipoClasseLabel.weightx = 0.3;
        formPanel.add(new JLabel("Classe Energetica:"), gbcTipoClasseLabel);

        GridBagConstraints gbcTipoClasseCombo = new GridBagConstraints();
        gbcTipoClasseCombo.insets = new Insets(10, 10, 10, 10);
        gbcTipoClasseCombo.anchor = GridBagConstraints.WEST;
        gbcTipoClasseCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoClasseCombo.gridx = 1;
        gbcTipoClasseCombo.gridy = 3;
        gbcTipoClasseCombo.weightx = 0.7;
        JComboBox<String> tipoClasse = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        tipoClasse.setBackground(Color.WHITE);
        tipoClasse.setPreferredSize(new Dimension(250, 30));
        formPanel.add(tipoClasse, gbcTipoClasseCombo);

       
        GridBagConstraints gbcPrezzoMinLabel = new GridBagConstraints();
        gbcPrezzoMinLabel.insets = new Insets(10, 10, 10, 10);
        gbcPrezzoMinLabel.anchor = GridBagConstraints.WEST;
        gbcPrezzoMinLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMinLabel.gridx = 0;
        gbcPrezzoMinLabel.gridy = 4;
        gbcPrezzoMinLabel.weightx = 0.3;
        formPanel.add(new JLabel("Prezzo Minimo:"), gbcPrezzoMinLabel);

        GridBagConstraints gbcPrezzoMinField = new GridBagConstraints();
        gbcPrezzoMinField.insets = new Insets(10, 10, 10, 10);
        gbcPrezzoMinField.anchor = GridBagConstraints.WEST;
        gbcPrezzoMinField.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMinField.gridx = 1;
        gbcPrezzoMinField.gridy = 4;
        gbcPrezzoMinField.weightx = 0.7;
        JTextField prezzoMinField = new JTextField("500");
        prezzoMinField.setHorizontalAlignment(JTextField.RIGHT);
        prezzoMinField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(prezzoMinField, gbcPrezzoMinField);

     
        GridBagConstraints gbcPrezzoMaxLabel = new GridBagConstraints();
        gbcPrezzoMaxLabel.insets = new Insets(10, 10, 10, 10);
        gbcPrezzoMaxLabel.anchor = GridBagConstraints.WEST;
        gbcPrezzoMaxLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMaxLabel.gridx = 0;
        gbcPrezzoMaxLabel.gridy = 5;
        gbcPrezzoMaxLabel.weightx = 0.3;
        formPanel.add(new JLabel("Prezzo Massimo:"), gbcPrezzoMaxLabel);

        GridBagConstraints gbcPrezzoMaxField = new GridBagConstraints();
        gbcPrezzoMaxField.insets = new Insets(10, 10, 10, 10);
        gbcPrezzoMaxField.anchor = GridBagConstraints.WEST;
        gbcPrezzoMaxField.fill = GridBagConstraints.HORIZONTAL;
        gbcPrezzoMaxField.gridx = 1;
        gbcPrezzoMaxField.gridy = 5;
        gbcPrezzoMaxField.weightx = 0.7;
        JTextField prezzoMaxField = new JTextField("1000000");
        prezzoMaxField.setPreferredSize(new Dimension(250, 30));
        prezzoMaxField.setHorizontalAlignment(JTextField.RIGHT);
        formPanel.add(prezzoMaxField, gbcPrezzoMaxField);

       
        JPanel checkboxPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        checkboxPanel.setBackground(Color.WHITE);
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JCheckBox ascensoreCheckBox = new JCheckBox("Ascensore");
        ascensoreCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox giardinoCheckBox = new JCheckBox("Giardino");
        giardinoCheckBox.setBackground(Color.WHITE);
        JCheckBox terrazzoCheckBox = new JCheckBox("Terrazzo");
        terrazzoCheckBox.setBackground(Color.WHITE);
        JCheckBox condominioCheckBox = new JCheckBox("In Condominio");
        condominioCheckBox.setBackground(Color.WHITE);

        checkboxPanel.add(ascensoreCheckBox);
        checkboxPanel.add(giardinoCheckBox);
        checkboxPanel.add(terrazzoCheckBox);
        checkboxPanel.add(condominioCheckBox);

        GridBagConstraints gbcCheckboxPanel = new GridBagConstraints();
        gbcCheckboxPanel.insets = new Insets(0, 0, 5, 0);
        gbcCheckboxPanel.gridx = 0;
        gbcCheckboxPanel.gridy = 6;
        gbcCheckboxPanel.gridwidth = 2;
        gbcCheckboxPanel.fill = GridBagConstraints.CENTER;
        formPanel.add(checkboxPanel, gbcCheckboxPanel);

        
        GridBagConstraints gbcNoteLabel = new GridBagConstraints();
        gbcNoteLabel.insets = new Insets(0, 0, 5, 0);
        gbcNoteLabel.gridx = 0;
        gbcNoteLabel.gridy = 7;
        gbcNoteLabel.gridwidth = 2;
        gbcNoteLabel.fill = GridBagConstraints.CENTER;
        JLabel noteLabel = new JLabel("Si prega di inserire un prezzo minimo che");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(noteLabel, gbcNoteLabel);

        centerPanel.add(formPanel, BorderLayout.CENTER);
        
        JLabel labelAvvertenza = new JLabel("parta da 500 ed un prezzo massimo non oltre i 1000000.");
        labelAvvertenza.setHorizontalAlignment(SwingConstants.CENTER);
        labelAvvertenza.setFont(new Font("Dialog", Font.ITALIC, 12));
        GridBagConstraints gbcLabelAvvertenza = new GridBagConstraints();
        gbcLabelAvvertenza.gridwidth = 2;
        gbcLabelAvvertenza.insets = new Insets(0, 0, 0, 5);
        gbcLabelAvvertenza.gridx = 0;
        gbcLabelAvvertenza.gridy = 8;
        formPanel.add(labelAvvertenza, gbcLabelAvvertenza);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

       
        JButton cercaButton = new JButton("CERCA IMMOBILI");
        cercaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cercaButton.setBackground(new Color(40, 132, 212));
        cercaButton.setForeground(Color.WHITE);
        cercaButton.setFocusPainted(false);
        cercaButton.setPreferredSize(new Dimension(200, 40));
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
                        ComposizioneImmobile composizione = new ComposizioneImmobile(terrazzo, giardino, ascensore, condominio);
                        Immobile immobile = new Immobile(classe, indirizzo, tipo, annuncio);
                        ArrayList<Immobile> ricerca = (ArrayList<Immobile>) c.ricercaImmobili(prezzoMin, prezzoMax, immobile, composizione);
                        
                        if (!c.checkPrezzi(prezzoMin, prezzoMax)) {
                            JOptionPane.showMessageDialog(null, "Errore durante la ricerca. Prova con altri parametri", ERRORE, JOptionPane.ERROR_MESSAGE);
                        }
                        if (!ricerca.isEmpty()) {
                            c.showResultImmobili(finestraCorrente, user, ricerca, indirizzo);
                        } else {
                            JOptionPane.showMessageDialog(null, "Non sono stati trovati immobili che rispettano questi criteri. Prova con altri parametri", ERRORE, JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    return null;
                }
                
                @Override
                protected void done() {
                    schermataCaricamento.close();
                }
            };
            worker.execute();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(cercaButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setResizable(false);
        setVisible(true);
    }
}
