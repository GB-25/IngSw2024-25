package ViewGUI;

import Class.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import Controller.Controller;

public class CaricamentoProprietaNuovo extends JFrame {

    // Dichiarazione dei campi da controllare
    private JPanel photoPanel; // Pannello per le foto
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
        setSize(1400, 800);
        setLocationRelativeTo(null); // Centra la finestra

        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Pannello sinistro
        JPanel leftPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 colonne, spazio 10px
        leftPanel.setPreferredSize(new Dimension (700, 800));
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
        rightPanel.setPreferredSize(new Dimension (700, 800));

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

        // Mappa
        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setPreferredSize(new Dimension(670, 400));
        mapPanel.setBorder(BorderFactory.createTitledBorder("Posizione:"));

        JLabel mapPlaceholder = new JLabel("Mappa non disponibile", SwingConstants.CENTER);
        mapPlaceholder.setForeground(Color.DARK_GRAY);
        mapPlaceholder.setFont(new Font("Arial", Font.ITALIC, 14));
        mapPanel.add(mapPlaceholder, BorderLayout.WEST);

        // Pannello per le foto
        photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Layout dinamico
        photoPanel.setPreferredSize(new Dimension(670, 400)); // Imposta dimensioni fisse
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
        }
    }

    // Avvio dell'interfaccia
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(CaricamentoProprietaNuovo::new);
    }
}
