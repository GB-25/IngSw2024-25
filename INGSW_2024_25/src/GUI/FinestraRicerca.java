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
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class FinestraRicerca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCerca;
	private JTextField txtBarra;

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
	public FinestraRicerca() {
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
		btnLente.setBounds(323, 12, 33, 24);
		
		contentPane.add(btnLente);
		
		JLabel lblAnnunciTrovati = new JLabel("Annunci Trovati");
		lblAnnunciTrovati.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAnnunciTrovati.setForeground(new Color(0, 153, 255));
		lblAnnunciTrovati.setBounds(29, 120, 251, 39);
		contentPane.add(lblAnnunciTrovati);
		
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
		
	}
	
		
		
		

		
	}
	
