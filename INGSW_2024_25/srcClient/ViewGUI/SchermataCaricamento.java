package ViewGUI;
import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class SchermataCaricamento extends JFrame{
	private static final long serialVersionUID = 1L;
	private JDialog loadingDialog;
    private JLabel gifLabel;
    private JLabel loadingLabel;

    public SchermataCaricamento (JFrame parent, String message) {
        FlatLaf.setup(new FlatLightLaf());
        loadingDialog = new JDialog(parent, "Caricamento", true);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setSize(400, 250);
        loadingDialog.setLocationRelativeTo(parent);
        loadingDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/immagini/loading.gif")); // Assicurati che sia nel classpath
        gifLabel = new JLabel(gifIcon);

        loadingDialog.add(gifLabel, BorderLayout.CENTER);

        // Etichetta con il messaggio di caricamento
        loadingLabel = new JLabel(message, SwingConstants.CENTER);

        loadingDialog.add(loadingLabel, BorderLayout.NORTH);
        loadingDialog.add(gifLabel, BorderLayout.CENTER);
        
        setVisible(true);
    }
}
