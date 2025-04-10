package client.view_gui;
import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class SchermataCaricamento extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame loadingFrame;
    private JLabel gifLabel;
    private JLabel loadingLabel;
    /**
     * Costruttore
     * 
     */
    public SchermataCaricamento (JFrame parent, String message) {
        FlatLaf.setup(new FlatLightLaf());
        loadingFrame = new JFrame("Caricamento");
        loadingFrame.setLayout(new BorderLayout());
        loadingFrame.setSize(400, 250);
        loadingFrame.setLocationRelativeTo(parent);
        loadingFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/loading.gif")); 
        gifLabel = new JLabel(gifIcon);

        loadingFrame.add(gifLabel, BorderLayout.CENTER);

        setResizable(false);
        loadingLabel = new JLabel(message, SwingConstants.CENTER);

        loadingFrame.add(loadingLabel, BorderLayout.NORTH);
        loadingFrame.add(gifLabel, BorderLayout.CENTER);
        loadingFrame.setVisible(true);
    }
    
    public void close() {
        loadingFrame.dispose();
    }
}
