package ViewGUI;

import Class.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import Controller.Controller;

public class CaricamentoProprietaNuovo extends JFrame implements MouseListener, MouseMotionListener{

    // Dichiarazione dei campi da controllare
	private File[] files;
	private JXMapViewer mapViewer;
	private Point lastPoint;
    private JPanel photoPanel; // Pannello per le foto
    private JPanel mapPanel;
    private JButton btnCaricaFoto;
    private JComboBox<String> cmbBalcony;
    private JComboBox<String> cmbGarden;
    private JComboBox<String> cmbType;
    private JComboBox<String> cmbAdType;
    private JTextField txtPosition;
    private JTextField txtPrice;
    private JTextField txtFloors;
    private JTextArea txtDescription;
    private JTextField txtWidth;
    private JTextField txtRooms;
    private JComboBox<String> cmbCondo;
    private JComboBox<String> cmbEnergyClass;
    private JComboBox<String> cmbElevator;

    // Lista per memorizzare le immagini caricate
    private List<ImageIcon> immaginiCaricate = new ArrayList<>();

    public CaricamentoProprietaNuovo(Controller c, User user) {
    	int MIN_FOTO = 5;
    	int MAX_FOTO = 10;
        // Configura la finestra
        setTitle("Caricamento Immobile - DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Pannello sinistro
        JPanel leftPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 colonne, spazio 10px
        leftPanel.setPreferredSize(new Dimension (370, 400));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setBackground(Color.WHITE);
        
     // Mappa
        mapPanel = new JPanel(new BorderLayout());
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setPreferredSize(new Dimension(380, 320));
        mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione:"));

        // Pannello per il pulsante "Indietro"
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.setBackground(Color.WHITE);
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); // Dimensioni ridotte
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Imposta un font più piccolo
        indietroButton.addActionListener(e -> {dispose(); new HomeAgente(c, user);});
        indietroPanel.add(indietroButton);
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setPreferredSize(new Dimension (400, 60));

        // Aggiungi il pannello del pulsante "Indietro" al pannello sinistro
        leftPanel.add(indietroPanel);
        leftPanel.add(new JLabel()); // Spazio vuoto per allineamento
        

        // Campi di input
        leftPanel.add(new JLabel("Tipo di immobile:"));
        cmbType = new JComboBox<>(new String[]{"", "Casa", "Appartamento", "Villa"});
        leftPanel.add(cmbType);

        leftPanel.add(new JLabel("Tipo di annuncio:"));
        cmbAdType = new JComboBox<>(new String[]{"", "Vendita", "Affitto"});
        leftPanel.add(cmbAdType);

        leftPanel.add(new JLabel("Prezzo (€):"));
        txtPrice = new JTextField(10);
        leftPanel.add(txtPrice);

        JXMapViewer mapViewer = new JXMapViewer();
        JTextField searchField = new JTextField();
        JList<String> suggestionList = new JList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        suggestionList.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.getViewport().getView().setBackground(new Color(235, 245, 223));
        leftPanel.add(new JLabel("Indirizzo:"));
        leftPanel.add(searchField);
        leftPanel.add(new JLabel(""));
        JButton buttonSearch = new JButton("Mostra sulla mappa");
        buttonSearch.setMaximumSize(new Dimension(100, 25));
        buttonSearch.addActionListener(e -> c.getCoordinates(searchField.getText().trim(), mapPanel, mapViewer));
        leftPanel.add(scrollPane);
        leftPanel.add(new JLabel(""));
        leftPanel.add(buttonSearch);
        
     // Listener per la barra di ricerca
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
        
     // Selezione di un indirizzo dalla lista con il mouse
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Se viene fatto un singolo clic
                    int index = suggestionList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        searchField.setText(suggestionList.getModel().getElementAt(index)); // Copia il valore nel textfield
                        listModel.clear(); // Pulisce la lista dei suggerimenti
                    }
                }
            }
        });

        // Selezione con il tasto Invio
        suggestionList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int index = suggestionList.getSelectedIndex();
                    if (index != -1) {
                        searchField.setText(suggestionList.getModel().getElementAt(index)); // Copia il valore nel textfield
                        listModel.clear(); // Pulisce la lista dei suggerimenti
                    }
                }
            }
        });

        
        leftPanel.add(new JLabel("Descrizione:"));
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        leftPanel.add(new JScrollPane(txtDescription));

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
        
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension (400, 800));

        // Pulsante CARICA
        JButton btnUpload = new JButton("CARICA");
        btnUpload.setPreferredSize(new Dimension(250, 25));
        btnUpload.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnUpload.setBackground(new Color(0, 153, 51));
        btnUpload.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnUpload);
        leftPanel.add(new JLabel()); // Spazio vuoto per allineamento
        leftPanel.add(buttonPanel);

        // Pannello per le foto
        photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Layout dinamico
        photoPanel.setPreferredSize(new Dimension(380, 480)); // Imposta dimensioni fisse
        photoPanel.setBorder(BorderFactory.createTitledBorder("Foto caricate:"));

        btnCaricaFoto = new JButton("Carica Foto");
        btnCaricaFoto.addActionListener(e -> caricaFoto());
        JPanel photoButtonPanel = new JPanel();
        photoButtonPanel.add(btnCaricaFoto, BorderLayout.EAST);
        photoPanel.add(photoButtonPanel, BorderLayout.NORTH);
        rightPanel.add(photoPanel, BorderLayout.NORTH);
        rightPanel.add(mapPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Aggiungere il listener al pulsante "CARICA"
        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Controlla se tutti i campi obbligatori sono compilati
                if (cmbType.getSelectedIndex() == 0 ||
                        cmbAdType.getSelectedIndex() == 0 ||
                        txtPrice.getText().trim().isEmpty() ||
                        txtFloors.getText().trim().isEmpty() ||
                        txtDescription.getText().trim().isEmpty() ||
                        txtWidth.getText().trim().isEmpty() ||
                        txtRooms.getText().trim().isEmpty() ||
                        searchField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Non sono stati riempiti tutti i campi. Controllare che tutti i campi siano stati riempiti, per poi procedere con il caricamento sulla piattaforma.",
                            "Caricamento non effettuato",
                            JOptionPane.ERROR_MESSAGE);
                } else if (immaginiCaricate.size() < MIN_FOTO || immaginiCaricate.size() > MAX_FOTO) {
                    JOptionPane.showMessageDialog(null,
                            "Devi caricare tra " + MIN_FOTO + " e " + MAX_FOTO + " foto.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Mostra il pop-up di conferma
                    int response = JOptionPane.showConfirmDialog(null,
                            "I campi sono stati riempiti ed è possibile caricare l'immobile. Procedere?",
                            "Conferma Caricamento",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        dispose();
                        StringBuilder sb = new StringBuilder();
                        for (File file : files) {
                        	if (sb.length() > 0) {
                                sb.append(",");
                            }
                            sb.append(c.fileUpload(file.getAbsolutePath()));    
                        }
                        String urls = sb.toString();
                        int grandezza = Integer.parseInt(txtWidth.getText());
                        int stanze = Integer.parseInt(txtRooms.getText());
                        int piani = Integer.parseInt(txtFloors.getText());
                        boolean condominio = c.checkComboBox(cmbCondo);
                        boolean giardino = c.checkComboBox(cmbGarden);
                        boolean ascensore = c.checkComboBox(cmbElevator);
                        double prezzo = Double.parseDouble(txtPrice.getText());
                        String indirizzo = txtPosition.getText();
                        String annuncio = (String) cmbAdType.getSelectedItem();
                        String tipo = (String) cmbType.getSelectedItem();
                        String descrizione = txtDescription.getText();
                        String classeEnergetica = (String) cmbEnergyClass.getSelectedItem();
                        boolean terrazzo = c.checkComboBox(cmbBalcony);
                        int idComposizioneImmobile = c.createComposition(grandezza, stanze, piani, condominio, giardino, ascensore, terrazzo);
                        c.uploadHouse(prezzo, idComposizioneImmobile, indirizzo, annuncio, tipo, classeEnergetica, descrizione,
                        urls, user);
                        
                    }
                }
            }
        });

        // Rendi la finestra visibile
        setVisible(true);
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
            files = fileChooser.getSelectedFiles(); // Ottieni tutti i file selezionati

            for (File file : files) {
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
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento dell'immagine.", "Errore", JOptionPane.ERROR_MESSAGE);
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
     

    // Avvio dell'interfaccia
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(CaricamentoProprietaNuovo::new);
    }
	
}
