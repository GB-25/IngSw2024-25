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
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;
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
	private JXMapViewer mapViewer;
	private Point lastPoint;
    private JPanel photoPanel; // Pannello per le foto
    private JPanel mapPanel;
    private JButton btnCaricaFoto;
    private JComboBox<String> cmbGarden;
    private JComboBox<String> cmbType;
    private JComboBox<String> cmbAdType;
    private JTextField txtPosition;
    private JTextField txtPrice;
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
        leftPanel.setPreferredSize(new Dimension (400, 400));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setBackground(Color.WHITE);

        // Pannello per il pulsante "Indietro"
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.setBackground(Color.WHITE);
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25)); // Dimensioni ridotte
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Imposta un font più piccolo
        indietroButton.addActionListener(e -> {dispose(); new HomeAgente(c, user);});
        indietroPanel.add(indietroButton);
        JButton buttonSearch = new JButton("↻");
        buttonSearch.setPreferredSize(new Dimension(100, 25));
        buttonSearch.addActionListener(e -> getCoordinates(txtPosition.getText().trim()));
        indietroPanel.add(buttonSearch);

        // Aggiungi il pannello del pulsante "Indietro" al pannello sinistro
        leftPanel.add(indietroPanel);
        leftPanel.add(new JLabel()); // Spazio vuoto per allineamento
        
     // Mappa
        mapPanel = new JPanel(new BorderLayout());
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setPreferredSize(new Dimension(380, 320));
        mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione:"));

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

        leftPanel.add(new JLabel("Posizione:"));
        txtPosition = new JTextField(10);
        leftPanel.add(txtPosition);

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

        leftPanel.add(new JLabel("Appartamento in condominio:"));
        cmbCondo = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbCondo);

        leftPanel.add(new JLabel("Classe energetica:"));
        cmbEnergyClass = new JComboBox<>(new String[]{"", "A", "B", "C", "D", "E", "F", "G"});
        leftPanel.add(cmbEnergyClass);

        leftPanel.add(new JLabel("Giardino:"));
        cmbGarden = new JComboBox<>(new String[]{"No", "Sì"});
        leftPanel.add(cmbGarden);

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
                        txtPosition.getText().trim().isEmpty() ||
                        txtDescription.getText().trim().isEmpty() ||
                        txtWidth.getText().trim().isEmpty() ||
                        txtRooms.getText().trim().isEmpty()) {

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
                        //new CaricamentoConfermato(c);
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
            File[] files = fileChooser.getSelectedFiles(); // Ottieni tutti i file selezionati

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
    
    private void aggiornaMappa(double lat, double lon) {
        // Rimuove la vecchia mappa se esiste
        if (mapViewer != null) {
            mapPanel.remove(mapViewer);
        }

        // Creazione della nuova mappa
        mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Imposta la posizione iniziale e lo zoom
        GeoPosition position = new GeoPosition(lat, lon);
        mapViewer.setAddressLocation(position);
        mapViewer.setZoom(34);
        
        Set<DefaultWaypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(position));
     // Create a waypoint painter
        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        // Set the overlay painter
        mapViewer.setOverlayPainter(waypointPainter);

        // Aggiunge i listener per interattività
        mapViewer.addMouseListener(this);
        mapViewer.addMouseMotionListener(this);
        mapViewer.addMouseWheelListener(e -> {
            int zoom = mapViewer.getZoom();
            if (e.getWheelRotation() < 0) {
                mapViewer.setZoom(Math.max(zoom - 1, 0)); // Zoom in
            } else {
                mapViewer.setZoom(Math.min(zoom + 1, 15)); // Zoom out
            }
        });

        // Aggiunge la mappa al pannello
        mapViewer.setVisible(true);
        mapPanel.add(mapViewer, BorderLayout.CENTER);
    }


        
    public void getCoordinates(String address) {
        try {
            // Rimossa chiave API giusto per sicurezza, dovresti averla
            String apiKey = "API_KEY";  
            String encodedAddress = URLEncoder.encode(address, "UTF-8");

            // Costruzione dell'URL per la richiesta di geocoding
            String url = "https://api.geoapify.com/v1/geocode/search?text=" + encodedAddress + "&apiKey=" + apiKey;

            // Connessione alla API
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            // Lettura della risposta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Analisi della risposta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getJSONArray("features").length() > 0) {
                // Estrazione delle coordinate (latitudine e longitudine)
                JSONObject location = jsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry");
                double lat = location.getJSONArray("coordinates").getDouble(1);
                double lon = location.getJSONArray("coordinates").getDouble(0);

                // Aggiorna la mappa con le nuove coordinate
                aggiornaMappa(lat, lon);
            } else {
                JOptionPane.showMessageDialog(null, "Indirizzo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel recupero della posizione!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


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
