package ViewGUI;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.Base64;
public class FinestraHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCerca;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinestraHome frame = new FinestraHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} **/

	/**
	 * Create the frame.
	 */
	public FinestraHome(Controller c) {
		setBackground(new Color(0, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 422);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
		Image imgLogo = iconLogo.getImage();
		Image imgLogoScaled = imgLogo.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon finalLogoIcon = new ImageIcon(imgLogoScaled);
		JButton btnLogo = new JButton(finalLogoIcon);
		btnLogo.setForeground(new Color(255, 255, 255));
		btnLogo.setBackground(new Color(255, 255, 255));
		btnLogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogo.setBorderPainted(false);   
		btnLogo.setFocusPainted(false);    
		btnLogo.setContentAreaFilled(false);
		contentPane.setLayout(null);
		btnLogo.setBounds(29, 0, 63, 52);
		contentPane.add(btnLogo);
		
		setContentPane(contentPane);
		
		txtCerca = new JTextField();
		txtCerca.setText("Cerca...");
		txtCerca.setBounds(119, 12, 203, 24);
		contentPane.add(txtCerca);
		txtCerca.setColumns(10);
		
		
		ImageIcon iconLente = new ImageIcon(getClass().getResource("/immagini/lente.png"));
		Image imgLente = iconLente.getImage();
		Image imgLenteScaled = imgLente.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		ImageIcon finalLenteIcon = new ImageIcon(imgLenteScaled);
		JButton btnLente = new JButton(finalLenteIcon);
		btnLente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadingDialog(FinestraHome.this).setVisible(true);
                
//              String fileData = c.fileDownload(fileName)
//              byte[] imageBytes = Base64.getDecoder().decode(fileData);
//              ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
//              BufferedImage image = ImageIO.read(bais);
//              JLabel label = new JLabel(new ImageIcon(image));
// questo è il codice che ci permetterà di riprendere le immagini in base 64 che ci manderà il server e
// convertirle per farle apparire nelle label, lascio qui per ora ma dovrà essere gestito a parte
            }
        });
		btnLente.setBounds(323, 12, 33, 24);
		
		contentPane.add(btnLente);
		
		JLabel lblAnnunciRecenti = new JLabel("Annunci Recenti");
		lblAnnunciRecenti.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAnnunciRecenti.setForeground(new Color(0, 153, 255));
		lblAnnunciRecenti.setBounds(29, 120, 251, 39);
		contentPane.add(lblAnnunciRecenti);
		
		JLabel lblAnnunciCheDovranno = new JLabel("Annunci che dovranno uscire tramite query");
		lblAnnunciCheDovranno.setBounds(39, 171, 304, 15);
		contentPane.add(lblAnnunciCheDovranno);
		
		JLabel bellLabel = new JLabel();
		bellLabel.setBounds(411, 12, 23, 24);
		ImageIcon bellIcon = new ImageIcon(getClass().getResource("/immagini/bell.png")); 
        Image bellImage = bellIcon.getImage().getScaledInstance(bellLabel.getWidth(), bellLabel.getHeight(), Image.SCALE_SMOOTH);
        bellLabel.setIcon(new ImageIcon(bellImage));
		contentPane.add(bellLabel);
		
		JLabel userLabel = new JLabel();
		userLabel.setBounds(441, 12, 33, 24);
		ImageIcon userIcon = new ImageIcon(getClass().getResource("/immagini/user.png")); 
        Image userImage = userIcon.getImage().getScaledInstance(userLabel.getWidth(), userLabel.getHeight(), Image.SCALE_SMOOTH);
        userLabel.setIcon(new ImageIcon(userImage));
		
		contentPane.add(userLabel);
		
		
		this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Ottieni la nuova dimensione della finestra
                Dimension frameSize = contentPane.getSize();
                int newWidth = frameSize.width;
                int newHeight = frameSize.height;

                // Modifica le dimensioni e la posizione dei componenti
                //button.setBounds(newWidth / 4, newHeight / 4, newWidth / 2, 40);
            }

			
        });
		
	}
	

class LoadingDialog extends JDialog {
    public LoadingDialog(JFrame owner) {
        super(owner, "Caricamento", true);
        setSize(200, 100);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Caricamento in corso...");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        new Timer(3000, new ActionListener() { // Simula attesa di 3 secondi
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiude la finestra di caricamento
            }
        }).start();
    }
}
}
