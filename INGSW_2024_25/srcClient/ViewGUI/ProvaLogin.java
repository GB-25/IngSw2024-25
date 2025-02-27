package ViewGUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Controller.Controller;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.MediaTracker;

public class ProvaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JFrame finestraCorrente;
	private FinestraRegistrazione finestraRegistrazione;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProvaLogin frame = new ProvaLogin();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ProvaLogin(Controller c) {
		finestraCorrente= this;
		this.setResizable(false);
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(40, 132, 212));
		panel.setPreferredSize(new Dimension(800, 150));
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		ImageIcon iconLogo = new ImageIcon(getClass().getResource("/immagini/logopngwhite.png"));
        Image imgLogo = iconLogo.getImage();
        Image imgLogoScaled = imgLogo.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        ImageIcon finalLogoIcon = new ImageIcon(imgLogoScaled);
        JButton logoButton = new JButton(finalLogoIcon);
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoButton.setForeground(new Color(40, 132, 212));
        logoButton.setPreferredSize(new Dimension(200, 120));
		logoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logoButton.setBackground(new Color(40, 132, 212));
		panel.add(logoButton, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setPreferredSize(new Dimension(800, 400));
		contentPane.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{800, 0};
		gbl_panel_1.rowHeights = new int[]{33, 367, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblSottoTitolo = new JLabel("                          La casa dei tuoi sogni a portata di mano!");
		lblSottoTitolo.setBackground(new Color(255, 255, 255));
		lblSottoTitolo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblSottoTitolo.setFont(new Font("Microsoft JhengHei UI Light", Font.ITALIC, 24));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblSottoTitolo, gbc_lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(400, 300));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {250, 67, 120, 130, 30, 30};
		gbl_panel_2.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblMail = new JLabel("E-mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		panel_2.add(lblMail, gbc_lblNewLabel_1);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(20, 19));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panel_2.add(lblPassword, gbc_lblNewLabel_2);
		
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAccedi.setBackground(new Color(166, 204, 238));
		btnAccedi.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD | Font.ITALIC, 13));
		btnAccedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.handleLogin(textField.getText(), passwordField.getPassword());
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(20, 19));
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		panel_2.add(passwordField, gbc_textField_1);
		passwordField.setColumns(10);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 4;
		panel_2.add(btnAccedi, gbc_btnNewButton_1);
		
		JButton btnRegistrati = new JButton("Registrati!");
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraRegistrazione = new FinestraRegistrazione(c);
        		c.cambiaFinestra(finestraCorrente, finestraRegistrazione);
			}
		});
		
		// **API Buttons (Google, Facebook, GitHub)**
        JPanel apiPanel = new JPanel(new FlowLayout());
        GridBagConstraints gbc_apiPanel = new GridBagConstraints();
        gbc_apiPanel.insets = new Insets(0, 0, 5, 5);
        gbc_apiPanel.gridx = 2;
        gbc_apiPanel.gridy = 5;
        panel_2.add(apiPanel, gbc_apiPanel);
        apiPanel.setBackground(new Color(255, 255, 255));
        
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/immagini/Google_logo.svg.png"));
        Image img1 = icon1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/immagini/Facebook-logo.png"));
        Image img2 = icon2.getImage().getScaledInstance(44, 28, Image.SCALE_SMOOTH);
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/immagini/gitlogo.png"));
        Image img3 = icon3.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        
                JButton btnGoogle = new JButton();
                btnGoogle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnGoogle.setBorderPainted(false);
                btnGoogle.setBackground(new Color(255, 255, 255));
                btnGoogle.setIcon(new ImageIcon(img1));
                JButton btnFacebook = new JButton();
                btnFacebook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnFacebook.setBorderPainted(false);
                btnFacebook.setBackground(new Color(255, 255, 255));
                btnFacebook.setIcon(new ImageIcon(img2));
                JButton btnGit = new JButton();
                btnGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnGit.setBorderPainted(false);
                btnGit.setBackground(new Color(255, 255, 255));
                btnGit.setIcon(new ImageIcon(img3));
                
                        apiPanel.add(btnGoogle);
                        apiPanel.add(btnFacebook);
                        apiPanel.add(btnGit);
		
		JLabel label = new JLabel("Non sei ancora iscritto? ");
		label.setFont(new Font("Microsoft YaHei UI Light", Font.ITALIC, 17));
		label.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 7;
		panel_2.add(label, gbc_label);
		btnRegistrati.setBackground(new Color(166, 204, 238));
		btnRegistrati.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD | Font.ITALIC, 13));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 8;
		panel_2.add(btnRegistrati, gbc_btnNewButton);
	}
	
}
