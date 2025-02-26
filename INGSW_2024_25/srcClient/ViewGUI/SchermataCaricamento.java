package ViewGUI;
import javax.swing.*;
import java.awt.*;

public class SchermataCaricamento {
    private JDialog loadingDialog;
    private JLabel gifLabel;
    private JLabel loadingLabel;

    public SchermataCaricamento (JFrame parent, String message) {
        loadingDialog = new JDialog(parent, "Caricamento", true);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setSize(400, 250);
        loadingDialog.setLocationRelativeTo(parent);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/immagini/loading.gif")); // Assicurati che sia nel classpath
        gifLabel = new JLabel(gifIcon);

        loadingDialog.add(gifLabel, BorderLayout.CENTER);

        // Etichetta con il messaggio di caricamento
        loadingLabel = new JLabel(message, SwingConstants.CENTER);

        loadingDialog.add(loadingLabel, BorderLayout.NORTH);
        loadingDialog.add(gifLabel, BorderLayout.CENTER);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));
    }

    public void hide() {
        SwingUtilities.invokeLater(() -> loadingDialog.dispose());
    }
}
