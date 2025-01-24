package GUI;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

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
	public FinestraHome() {
		setBackground(new Color(0, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 374);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
		Image imgLogo = iconLogo.getImage();
		Image imgLogoScaled = imgLogo.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
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
		btnLogo.setBounds(12, 0, 78, 63);
		contentPane.add(btnLogo);
		
		setContentPane(contentPane);
		
		txtCerca = new JTextField();
		txtCerca.setText("Cerca...");
		txtCerca.setBounds(119, 12, 203, 51);
		contentPane.add(txtCerca);
		txtCerca.setColumns(10);
		
		
		ImageIcon iconLente = new ImageIcon(getClass().getResource("/immagini/lente.png"));
		Image imgLente = iconLente.getImage();
		Image imgLenteScaled = imgLente.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
		ImageIcon finalLenteIcon = new ImageIcon(imgLenteScaled);
		JButton btnLente = new JButton(finalLenteIcon);
		btnLente.setBounds(323, 12, 65, 51);
		
		contentPane.add(btnLente);
	}
	
	}

