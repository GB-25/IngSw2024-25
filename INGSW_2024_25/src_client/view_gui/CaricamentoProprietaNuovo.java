package view_gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import classi.ComposizioneImmobile;
import classi.Immobile;
import classi.User;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;


public class CaricamentoProprietaNuovo extends JFrame implements MouseListener, MouseMotionListener{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERRORE = "Errore";

	// Dichiarazione dei campi da controllare
	private List<File> files = new ArrayList<>();
	private JXMapViewer mapViewer;
	private Point lastPoint;
    private JPanel photoPanel; // Pannello per le foto
    private JPanel mapPanel;
    
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
    // Lista per memorizzare le immagini caricate
    private List<ImageIcon> immaginiCaricate = new ArrayList<>();

    public CaricamentoProprietaNuovo(Controller c, User user) {

        FlatLaf.setup(new FlatLightLaf());
        setupWindow();
        JPanel mainPanel = createMainPanel();
        JPanel leftPanel = createLeftPanel(c, user);
        JPanel rightPanel = createRightPanel();
        setupUploadButton(c, user, leftPanel);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        setVisible(true);
    }
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
        JPanel leftPanel = new JPanel(new GridLayout(0, 2, 10, 10));
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
        indietroPanel.setBackground(Color.WHITE);
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25));
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> {
            dispose();
            new HomeGenerale(c, user);
        });
        indietroPanel.add(indietroButton);
        leftPanel.add(indietroPanel);
        leftPanel.add(new JLabel());
    }

    private void addInputFields(JPanel leftPanel) {
        leftPanel.add(new JLabel("Tipo di immobile:"));
        cmbType = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        leftPanel.add(cmbType);

        leftPanel.add(new JLabel("Tipo di annuncio:"));
        cmbAdType = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        leftPanel.add(cmbAdType);

        leftPanel.add(new JLabel("Prezzo (€):"));
        txtPrice = new JTextField(10);
        leftPanel.add(txtPrice);
    }


    private void addSearchComponents(JPanel leftPanel, Controller c, User user) {
        searchField = new JTextField();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> suggestionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.getViewport().getView().setBackground(new Color(64, 168, 211));

        leftPanel.add(new JLabel("Indirizzo:"));
        leftPanel.add(searchField);
        leftPanel.add(new JLabel(""));
        JButton buttonSearch = new JButton("Mostra sulla mappa");
        buttonSearch.setMaximumSize(new Dimension(100, 25));

        buttonSearch.addActionListener(e -> {
            try {
                c.getCoordinates(c, searchField.getText().trim(), mapPanel, mapViewer, false, null, user);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        leftPanel.add(scrollPane);
        leftPanel.add(new JLabel(""));
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
        leftPanel.add(new JLabel("Descrizione:"));
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        leftPanel.add(new JScrollPane(txtDescription));
    }

    private void addAdditionalFields(JPanel leftPanel) {
        leftPanel.add(new JLabel("Dimensioni alloggio:"));
        txtWidth = new JTextField(10);
        leftPanel.add(txtWidth);

        leftPanel.add(new JLabel("Numero di stanze:"));
        txtRooms = new JTextField(10);
        leftPanel.add(txtRooms);

        leftPanel.add(new JLabel("Piani:"));
        txtFloors = new JTextField(10);
        leftPanel.add(txtFloors);

        leftPanel.add(new JLabel("Condominiale:"));
        cmbCondo = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbCondo);

        leftPanel.add(new JLabel("Classe energetica:"));
        cmbEnergyClass = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        leftPanel.add(cmbEnergyClass);

        leftPanel.add(new JLabel("Giardino:"));
        cmbGarden = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbGarden);

        leftPanel.add(new JLabel("Terrazzo:"));
        cmbBalcony = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbBalcony);

        leftPanel.add(new JLabel("Ascensore:"));
        cmbElevator = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbElevator);
    }

    private void addUploadButtonPanel(JPanel leftPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton btnUpload = new JButton("CARICA");
        btnUpload.setPreferredSize(new Dimension(250, 25));
        btnUpload.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnUpload.setBackground(new Color(0, 153, 51));
        btnUpload.setForeground(Color.WHITE);
        buttonPanel.add(btnUpload);
        leftPanel.add(new JLabel());
        leftPanel.add(buttonPanel);
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout()); // Use BorderLayout for better organization
        rightPanel.setPreferredSize(new Dimension(400, 800));

        // Initialize photoPanel
        photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        photoPanel.setPreferredSize(new Dimension(380, 480));
        photoPanel.setBorder(BorderFactory.createTitledBorder("Foto caricate:"));

        // Initialize btnCaricaFoto
        JButton btnCaricaFoto = new JButton("Carica Foto");
        btnCaricaFoto.addActionListener(e -> caricaFoto());

        // Add btnCaricaFoto to a panel
        JPanel photoButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        photoButtonPanel.add(btnCaricaFoto);

        // Add photoButtonPanel to photoPanel
        photoPanel.add(photoButtonPanel);

        // Add photoPanel to rightPanel
        rightPanel.add(photoPanel, BorderLayout.NORTH);

        // Initialize mapPanel if it's null
        if (mapPanel == null) {
            mapPanel = new JPanel(new BorderLayout());
            mapPanel.setBackground(Color.LIGHT_GRAY);
            mapPanel.setPreferredSize(new Dimension(380, 320));
            mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione:"));
        }


        // Add mapPanel to rightPanel
        rightPanel.add(mapPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    private void setupUploadButton(Controller c, User user, JPanel leftPanel) {
        JButton btnUpload = (JButton) ((JPanel) leftPanel.getComponent(leftPanel.getComponentCount() - 1)).getComponent(0);
        btnUpload.addActionListener(e -> handleUpload(c, user));
    }

    private void handleUpload(Controller c, User user) {
    	String check = txtPrice.getText().trim();
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
        } else if (!Controller.isNumeric(check)) {
            JOptionPane.showMessageDialog(null,
                    "Sono presenti caratteri all'interno del campo del prezzi. Inserire un prezzo valido.",
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
                uploadProperty(c, user);
            }
        }
    }


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

        ComposizioneImmobile composizione = new ComposizioneImmobile(0, grandezza, piani, stanze, terrazzo, giardino, ascensore, condominio);
        Immobile immobile = new Immobile(0, prezzo, composizione, indirizzo, annuncio, tipo, classeEnergetica, descrizione, "", user);
        c.uploadNewHouse(immobile, foto);
        dispose();
    }


    public void caricaFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleziona un'immagine");
        fileChooser.setMultiSelectionEnabled(true); // Abilita selezione multipla

        // Filtro per file di tipo immagine
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
        	if (files == null) {  // Se è null inizializzalo
                files = new ArrayList<>();
            }
            
            // Aggiungi i nuovi file selezionati senza sovrascrivere quelli precedenti
            files.addAll(Arrays.asList(fileChooser.getSelectedFiles()));

            for (File file : fileChooser.getSelectedFiles()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    Image image = imageIcon.getImage();

                    // Ridimensiona l'immagine
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                    // Aggiungi l'immagine alla lista
                    immaginiCaricate.add(new ImageIcon(scaledImage));

                    // Aggiungi l'immagine al pannello
                    JLabel lblFoto = new JLabel(new ImageIcon(scaledImage));
                    photoPanel.add(lblFoto);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento dell'immagine.", ERRORE, JOptionPane.ERROR_MESSAGE);
                }
            }

            // Aggiorna il pannello
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

            // Calcola la differenza in pixel tra la posizione corrente e quella precedente
            int dx = newPoint.x - lastPoint.x;
            int dy = newPoint.y - lastPoint.y;

            // Converti il movimento del mouse in un cambiamento di posizione geografica
            GeoPosition newCenter = mapViewer.convertPointToGeoPosition(new Point(
                mapViewer.getWidth() / 2 - dx,  // Calcola la nuova posizione X
                mapViewer.getHeight() / 2 - dy  // Calcola la nuova posizione Y
            ));

            // Aggiorna il centro della mappa
            mapViewer.setCenterPosition(newCenter);

            // Aggiorna lastPoint per il prossimo evento di trascinamento
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
            mapViewer.setZoom(mapViewer.getZoom() - 1); // Doppio clic per zoom in
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
