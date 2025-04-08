package view_gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.re2j.Pattern;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.User;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;


public class CaricamentoProprietaNuovo extends JFrame implements MouseListener, MouseMotionListener{


    
	private static final long serialVersionUID = 1L;
	private static final String ERRORE = "Errore";
	private transient Logger logger = Logger.getLogger(getClass().getName());

	private List<File> files = new ArrayList<>();
	private JXMapViewer mapViewer;
	private Point lastPoint;
    private JPanel photoPanel; 
    private JPanel mapPanel;
    private SchermataCaricamento schermataCaricamento;
    private JFrame finestraCorrente;
    
    private JComboBox<String> cmbBalcony;
    private JComboBox<String> cmbGarden;
    private JComboBox<String> cmbType;
    private JComboBox<String> cmbAdType;
    private JTextField txtPrice;
    private JTextField txtFloors;
    private JTextArea txtDescription;
    private JTextField txtWidth;
    private JTextField txtRooms;
    private JComboBox<String> cmbCondo;
    private JComboBox<String> cmbEnergyClass;
    private JComboBox<String> cmbElevator;



    JTextField searchField;
    int minFoto = 5;
	int maxFoto = 10;
    
    private List<ImageIcon> immaginiCaricate = new ArrayList<>();
    private JPanel leftPanel;
    /**
     * 
     * Costruttore
     */
    public CaricamentoProprietaNuovo(Controller c, User user) {
        finestraCorrente = this;
        FlatLaf.setup(new FlatLightLaf());
        
        setupWindow();
        JPanel mainPanel = createMainPanel();
        leftPanel = createLeftPanel(c, user);
        JPanel rightPanel = createRightPanel();
        setupUploadButton(c, user, leftPanel);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        setVisible(true);
    }
    /**
     * Metodi che permettono la creazione della finestra e dei suoi componenti
     */
    private void setupWindow() {
        setTitle("Caricamento Immobile - DietiEstates25");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);
        return mainPanel;
    }

    private JPanel createLeftPanel(Controller c, User user) {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(370, 400));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setBackground(Color.WHITE);

        addBackButton(leftPanel, c, user);
        addInputFields(leftPanel);
        addSearchComponents(leftPanel, c, user);
        addDescriptionField(leftPanel);
        addAdditionalFields(leftPanel);
        addUploadButtonPanel(leftPanel);

        return leftPanel;
    }

    private void addBackButton(JPanel leftPanel, Controller c, User user) {
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.setBounds(20, 22, 160, 35);
        indietroPanel.setBackground(Color.WHITE);
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25));
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });
        leftPanel.setLayout(null);
        indietroPanel.add(indietroButton);
        leftPanel.add(indietroPanel);
    }

    private void addInputFields(JPanel leftPanel) {
        JLabel label = new JLabel("Tipo di immobile:");
        label.setBounds(20, 67, 160, 35);
        leftPanel.add(label);
        cmbType = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        cmbType.setBackground(new Color(255, 255, 255));
        cmbType.setBounds(190, 67, 160, 35);
        leftPanel.add(cmbType);

        JLabel adLabel = new JLabel("Tipo di annuncio:");
        adLabel.setBounds(20, 112, 160, 35);
        leftPanel.add(adLabel);
        cmbAdType = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        cmbAdType.setBackground(new Color(255, 255, 255));
        cmbAdType.setBounds(190, 112, 160, 35);
        leftPanel.add(cmbAdType);

        JLabel priceLabel = new JLabel("Prezzo (€):");
        priceLabel.setBounds(20, 157, 160, 35);
        leftPanel.add(priceLabel);
        txtPrice = new JTextField(10);
        txtPrice.setBounds(190, 157, 160, 35);
        leftPanel.add(txtPrice);
    }


    private void addSearchComponents(JPanel leftPanel, Controller c, User user) {
        searchField = new JTextField();
        searchField.setBounds(190, 202, 160, 35);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> suggestionList = new JList<>(listModel);
        suggestionList.setSelectionBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setBounds(190, 247, 160, 80);
        scrollPane.getViewport().getView().setBackground(new Color(255, 255, 255));

        JLabel addressLabel = new JLabel("Indirizzo:");
        addressLabel.setBounds(20, 202, 160, 35);
        leftPanel.add(addressLabel);
        leftPanel.add(searchField);
        JButton buttonSearch = new JButton("<html><center>Mostra sulla <br>mappa</center></html>");
        buttonSearch.setBounds(20, 270, 160, 35);
        buttonSearch.setMaximumSize(new Dimension(100, 25));

        buttonSearch.addActionListener(e -> {
            try {
            	mapViewer = new JXMapViewer();
                c.getCoordinates(c, searchField.getText().trim(), mapPanel, mapViewer, null, user, finestraCorrente);
            } catch (Exception e1) {
            	logger.severe("Errore nel recupero delle coordinate");
            }
        });

        leftPanel.add(scrollPane);
        leftPanel.add(buttonSearch);

        setupSearchListeners(searchField, listModel, suggestionList, c);
    }

    private void setupSearchListeners(JTextField searchField, DefaultListModel<String> listModel, JList<String> suggestionList, Controller c) {
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String query = searchField.getText();
                if (!query.isEmpty()) {
                    c.fetchAddressSuggestions(query, listModel);
                } else {
                    listModel.clear();
                }
            }
        });

        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = suggestionList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        searchField.setText(suggestionList.getModel().getElementAt(index));
                        listModel.clear();
                    }
                }
            }
        });

        suggestionList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int index = suggestionList.getSelectedIndex();
                    if (index != -1) {
                        searchField.setText(suggestionList.getModel().getElementAt(index));
                        listModel.clear();
                    }
                }
            }
        });
    }

    private void addDescriptionField(JPanel leftPanel) {
        JLabel descriptionLabel = new JLabel("Descrizione:");
        descriptionLabel.setBounds(20, 337, 160, 35);
        leftPanel.add(descriptionLabel);
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setBounds(190, 337, 160, 35);
        leftPanel.add(scrollPane);
    }

    private void addAdditionalFields(JPanel leftPanel) {
        JLabel widthLabel = new JLabel("<html><center>Dimensioni alloggio (m²) :</center></html>");
        widthLabel.setBounds(20, 382, 160, 35);
        leftPanel.add(widthLabel);
        txtWidth = new JTextField(10);
        txtWidth.setBounds(190, 382, 160, 35);
        leftPanel.add(txtWidth);

        JLabel roomsLabel = new JLabel("Numero di stanze:");
        roomsLabel.setBounds(20, 427, 160, 35);
        leftPanel.add(roomsLabel);
        txtRooms = new JTextField(10);
        txtRooms.setBounds(190, 427, 160, 35);
        leftPanel.add(txtRooms);

        JLabel floorsLabel = new JLabel("Piani:");
        floorsLabel.setBounds(20, 472, 160, 35);
        leftPanel.add(floorsLabel);
        txtFloors = new JTextField(10);
        txtFloors.setBounds(190, 472, 160, 35);
        leftPanel.add(txtFloors);

        JLabel complexLabel = new JLabel("Condominiale:");
        complexLabel.setBounds(20, 517, 160, 35);
        leftPanel.add(complexLabel);
        cmbCondo = new JComboBox<>(new String[]{"No", "Sì"});
        cmbCondo.setBackground(new Color(255, 255, 255));
        cmbCondo.setBounds(190, 517, 160, 35);
        leftPanel.add(cmbCondo);

        JLabel energyClassLabel = new JLabel("Classe energetica:");
        energyClassLabel.setBounds(20, 562, 160, 35);
        leftPanel.add(energyClassLabel);
        cmbEnergyClass = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        cmbEnergyClass.setBackground(new Color(255, 255, 255));
        cmbEnergyClass.setBounds(190, 562, 160, 35);
        leftPanel.add(cmbEnergyClass);

        JLabel gardenLabel = new JLabel("Giardino:");
        gardenLabel.setBounds(20, 607, 160, 35);
        leftPanel.add(gardenLabel);
        cmbGarden = new JComboBox<>(new String[]{"No", "Sì"});
        cmbGarden.setBackground(new Color(255, 255, 255));
        cmbGarden.setBounds(190, 607, 160, 35);
        leftPanel.add(cmbGarden);

        JLabel balconyLabel = new JLabel("Terrazzo:");
        balconyLabel.setBounds(20, 652, 160, 35);
        leftPanel.add(balconyLabel);
        cmbBalcony = new JComboBox<>(new String[]{"No", "Sì"});
        cmbBalcony.setBackground(new Color(255, 255, 255));
        cmbBalcony.setBounds(190, 652, 160, 35);
        leftPanel.add(cmbBalcony);

        JLabel elevatorLabel = new JLabel("Ascensore:");
        elevatorLabel.setBounds(20, 697, 160, 35);
        leftPanel.add(elevatorLabel);
        cmbElevator = new JComboBox<>(new String[]{"No", "Sì"});
        cmbElevator.setBackground(new Color(255, 255, 255));
        cmbElevator.setBounds(190, 697, 160, 35);
        leftPanel.add(cmbElevator);
    }

    private void addUploadButtonPanel(JPanel leftPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(190, 732, 160, 35);
        buttonPanel.setBackground(Color.WHITE);
        JButton btnUpload = new JButton("CARICA");
        btnUpload.setPreferredSize(new Dimension(250, 25));
        btnUpload.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnUpload.setBackground(new Color(0, 153, 51));
        btnUpload.setForeground(Color.WHITE);
        buttonPanel.add(btnUpload);
        leftPanel.add(buttonPanel);
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout()); 
        rightPanel.setPreferredSize(new Dimension(400, 800));
        rightPanel.setBackground(new Color(40, 132, 212));

        photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        photoPanel.setPreferredSize(new Dimension(380, 480));
        TitledBorder title = BorderFactory.createTitledBorder("Foto caricate:");
        title.setTitleColor(Color.WHITE);
        photoPanel.setBorder(title);
        
        photoPanel.setBackground(new Color(40, 132, 212));
        
        JButton btnCaricaFoto = new JButton("Carica Foto");
        btnCaricaFoto.addActionListener(e -> caricaFoto());

        JPanel photoButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        photoButtonPanel.add(btnCaricaFoto);
        photoButtonPanel.setBackground(new Color(40, 132, 212));
        
        photoPanel.add(photoButtonPanel);

        rightPanel.add(photoPanel, BorderLayout.NORTH);

        if (mapPanel == null) {
            mapPanel = new JPanel(new BorderLayout());
            mapPanel.setBackground(Color.LIGHT_GRAY);
            mapPanel.setPreferredSize(new Dimension(380, 320));
            mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione:"));
        }

        rightPanel.add(mapPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    private void setupUploadButton(Controller c, User user, JPanel leftPanel) {
        JButton btnUpload = (JButton) ((JPanel) leftPanel.getComponent(leftPanel.getComponentCount() - 1)).getComponent(0);
        btnUpload.addActionListener(e -> handleUpload(c, user));
    }
    /**
     * 
     * @param c
     * @param user
     * Metodo che controlla che i valori nei campi siano giusti
     */
    private void handleUpload(Controller c, User user) {
    	String checkPrice = txtPrice.getText().trim();
    	String checkAddress = searchField.getText().trim();
    	String quadratura = txtWidth.getText();
     	String rooms = txtRooms.getText();
     	String floors = txtFloors.getText();
        if (!areAllFieldsFilled()) {
            JOptionPane.showMessageDialog(null,
                    "Non sono stati riempiti tutti i campi. Controllare che tutti i campi siano stati riempiti, per poi procedere con il caricamento sulla piattaforma.",
                    "Caricamento non effettuato",
                    JOptionPane.ERROR_MESSAGE);
        } else if (immaginiCaricate.size() < minFoto || immaginiCaricate.size() > maxFoto) {
            JOptionPane.showMessageDialog(null,
                    "Devi caricare tra " + minFoto + " e " + maxFoto + " foto.",
                    ERRORE,
                    JOptionPane.ERROR_MESSAGE);
        } else if (!Controller.isNumeric(checkPrice)) {
            JOptionPane.showMessageDialog(null,
                    "Sono presenti caratteri all'interno del campo del prezzi. Inserire un prezzo valido.",
                    ERRORE,
                    JOptionPane.ERROR_MESSAGE);
        } else if (!Pattern.matches("^[A-Za-zÀ-ÿ]+(?:[\\s-][A-Za-zÀ-ÿ]+)*,\\s*\\d+,\\s*\\d{5}\\s+[A-Za-zÀ-ÿ]+(?:\\s+[A-Za-zÀ-ÿ]+)*(?:\\s+[A-Z]{2})?,\\s*[A-Za-zÀ-ÿ]+$"
        		, checkAddress)) {
        	JOptionPane.showMessageDialog(null,
                    "Indirizzo non valido. È necessario inserire l'indirizzo suggerito dal sistema, oltre a fare attenzione ad inserire il numero civico!",
                    ERRORE,
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(!c.checkDettagliInserzione(quadratura, floors, rooms)) {
         	JOptionPane.showMessageDialog(null,
                     "Qualcosa non va! Prova a ricontrollare i campi della dimensione, stanze e piani!",
                     ERRORE,
                     JOptionPane.ERROR_MESSAGE);
         }
        
        else {
            int response = JOptionPane.showConfirmDialog(null,
                    "I campi sono stati riempiti ed è possibile caricare l'immobile. Procedere?",
                    "Conferma Caricamento",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
            	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
        		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                uploadProperty(c, user);
                return null;}
                    
                    @Override
                    protected void done() {
                    	schermataCaricamento.close();
                    }
            }; worker.execute();
        }
    }}

    /**
     * 
     * @return true se i campi sono riempiti
     */
    private boolean areAllFieldsFilled() {
        return cmbType.getSelectedIndex() != 0 &&
                cmbAdType.getSelectedIndex() != 0 &&
                !txtPrice.getText().trim().isEmpty() &&
                !txtFloors.getText().trim().isEmpty() &&
                !txtDescription.getText().trim().isEmpty() &&
                !txtWidth.getText().trim().isEmpty() &&
                !txtRooms.getText().trim().isEmpty() &&
                !searchField.getText().trim().isEmpty();
    }
    /**
     * 
     * @param c
     * @param user
     * Metodo per caricare un immobile sul DataBase
     */
    private void uploadProperty(Controller c, User user) {
    	
        int grandezza = Integer.parseInt(txtWidth.getText());
        int stanze = Integer.parseInt(txtRooms.getText());
        int piani = Integer.parseInt(txtFloors.getText());
        boolean condominio = c.checkComboBox(cmbCondo);
        boolean giardino = c.checkComboBox(cmbGarden);
        boolean ascensore = c.checkComboBox(cmbElevator);
        double prezzo = Double.parseDouble(txtPrice.getText());
        String indirizzo = searchField.getText();
        ArrayList<String> foto = new ArrayList<>();
        for (File file : files) {
        	foto.add(file.getAbsolutePath());
        	}
        String annuncio = (String) cmbAdType.getSelectedItem();
        String tipo = (String) cmbType.getSelectedItem();
        String descrizione = txtDescription.getText();
        String classeEnergetica = (String) cmbEnergyClass.getSelectedItem();
        boolean terrazzo = c.checkComboBox(cmbBalcony); 
        ComposizioneImmobile composizioneBoolean = new ComposizioneImmobile(terrazzo, giardino, ascensore, condominio);
        ComposizioneImmobile composizione = new ComposizioneImmobile(0, grandezza, piani, stanze, composizioneBoolean);
        Immobile immobileDettagli = new Immobile(classeEnergetica, indirizzo, tipo, annuncio);
        Immobile immobile = new Immobile(0, prezzo, composizione, descrizione, "", user, immobileDettagli);
        c.uploadNewHouse(immobile, foto);
        dispose();
    }

    /**
     * Metodo per gestire il caricamento delle foto e farle visualizzare nella finestra
     */
    public void caricaFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleziona un'immagine");
        fileChooser.setMultiSelectionEnabled(true); 
        
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Immagini (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
        	if (files == null) { 
                files = new ArrayList<>();
            }
            
            files.addAll(Arrays.asList(fileChooser.getSelectedFiles()));

            for (File file : fileChooser.getSelectedFiles()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    Image image = imageIcon.getImage();

                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                    immaginiCaricate.add(new ImageIcon(scaledImage));

                    JLabel lblFoto = new JLabel(new ImageIcon(scaledImage));
                    photoPanel.add(lblFoto);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento dell'immagine.", ERRORE, JOptionPane.ERROR_MESSAGE);
                }
            }

            photoPanel.revalidate();
            photoPanel.repaint();
        }}
    


    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint != null) {
            Point newPoint = e.getPoint();

            int dx = newPoint.x - lastPoint.x;
            int dy = newPoint.y - lastPoint.y;

            GeoPosition newCenter = mapViewer.convertPointToGeoPosition(new Point(
                mapViewer.getWidth() / 2 - dx,  
                mapViewer.getHeight() / 2 - dy  
            ));

            
            mapViewer.setCenterPosition(newCenter);

   
            lastPoint = newPoint;
        }
    }
    
    

    @Override
    public void mouseReleased(MouseEvent e) {
        lastPoint = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            mapViewer.setZoom(mapViewer.getZoom() - 1); 
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // gestione eventi di movimento del mouse 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Metodo vuoto
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Metodo vuoto
    }   
     

    
	
}
