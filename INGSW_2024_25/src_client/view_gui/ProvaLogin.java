package view_gui;

import java.awt.Image;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import controller.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ProvaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JFrame finestraCorrente;
	private String fontScritte = "Microsoft YaHei UI Light";

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
        FlatLaf.setup(new FlatLightLaf());
		finestraCorrente= this;
		this.setResizable(false);
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		logoButton.setBackground(new Color(40, 132, 212));
		panel.add(logoButton, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255, 255, 255));
		panel1.setPreferredSize(new Dimension(800, 400));
		contentPane.add(panel1);
		GridBagLayout gblPanel1 = new GridBagLayout();
		gblPanel1.columnWidths = new int[]{800, 0};
		gblPanel1.rowHeights = new int[]{33, 367, 0};
		gblPanel1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gblPanel1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel1.setLayout(gblPanel1);
		
		JLabel lblSottoTitolo = new JLabel("                           La casa dei tuoi sogni a portata di mano!");
		lblSottoTitolo.setBackground(new Color(255, 255, 255));
		lblSottoTitolo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblSottoTitolo.setFont(new Font("Microsoft JhengHei UI Light", Font.ITALIC, 24));
		GridBagConstraints gbcLblSottoTitolo = new GridBagConstraints();
		gbcLblSottoTitolo.anchor = GridBagConstraints.NORTH;
		gbcLblSottoTitolo.fill = GridBagConstraints.HORIZONTAL;
		gbcLblSottoTitolo.insets = new Insets(0, 0, 5, 0);
		gbcLblSottoTitolo.gridx = 0;
		gbcLblSottoTitolo.gridy = 0;
		panel1.add(lblSottoTitolo, gbcLblSottoTitolo);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 255, 255));
		panel2.setPreferredSize(new Dimension(400, 300));
		GridBagConstraints gbcPanel2 = new GridBagConstraints();
		gbcPanel2.fill = GridBagConstraints.BOTH;
		gbcPanel2.gridx = 0;
		gbcPanel2.gridy = 1;
		panel1.add(panel2, gbcPanel2);
		GridBagLayout gblPanel2 = new GridBagLayout();
		gblPanel2.columnWidths = new int[] {250, 67, 120, 130, 30, 30};
		gblPanel2.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gblPanel2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblPanel2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel2.setLayout(gblPanel2);
		
		JLabel lblMail = new JLabel("E-mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbcLblMail = new GridBagConstraints();
		gbcLblMail.anchor = GridBagConstraints.WEST;
		gbcLblMail.insets = new Insets(0, 0, 5, 5);
		gbcLblMail.gridx = 1;
		gbcLblMail.gridy = 2;
		panel2.add(lblMail, gbcLblMail);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(20, 19));
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.fill = GridBagConstraints.HORIZONTAL;
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		gbcTextField.gridx = 2;
		gbcTextField.gridy = 2;
		panel2.add(textField, gbcTextField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbcLblPassword = new GridBagConstraints();
		gbcLblPassword.anchor = GridBagConstraints.WEST;
		gbcLblPassword.insets = new Insets(0, 0, 5, 5);
		gbcLblPassword.gridx = 1;
		gbcLblPassword.gridy = 3;
		panel2.add(lblPassword, gbcLblPassword);
		
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAccedi.setBackground(new Color(166, 204, 238));
		btnAccedi.setFont(new Font(fontScritte, Font.BOLD | Font.ITALIC, 13));
		btnAccedi.addActionListener(e -> {
				c.createSchermataCaricamento(finestraCorrente, "Caricamento");
				 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                     @Override
                     protected Void doInBackground() throws Exception {
				c.handleLogin(textField.getText(), passwordField.getPassword());
				return null;}
                     
                     @Override
                     protected void done() {
                         dispose();
                     }
			};
			worker.execute();
		});
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(20, 19));
		GridBagConstraints gbcPasswordField = new GridBagConstraints();
		gbcPasswordField.insets = new Insets(0, 0, 5, 5);
		gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbcPasswordField.gridx = 2;
		gbcPasswordField.gridy = 3;
		panel2.add(passwordField, gbcPasswordField);
		passwordField.setColumns(10);
		GridBagConstraints gbcBtnAccedi = new GridBagConstraints();
		gbcBtnAccedi.fill = GridBagConstraints.BOTH;
		gbcBtnAccedi.insets = new Insets(0, 0, 5, 5);
		gbcBtnAccedi.gridx = 2;
		gbcBtnAccedi.gridy = 4;
		panel2.add(btnAccedi, gbcBtnAccedi);
		
		JButton btnRegistrati = new JButton("Registrati!");
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrati.addActionListener(e -> c.registerUser(finestraCorrente));
        
		JLabel label = new JLabel("Non sei ancora iscritto? ");
		label.setFont(new Font(fontScritte, Font.ITALIC, 17));
		label.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbclabel = new GridBagConstraints();
		gbclabel.fill = GridBagConstraints.VERTICAL;
		gbclabel.insets = new Insets(0, 0, 5, 5);
		gbclabel.gridx = 2;
		gbclabel.gridy = 7;
		panel2.add(label, gbclabel);
		btnRegistrati.setBackground(new Color(166, 204, 238));
		btnRegistrati.setFont(new Font(fontScritte, Font.BOLD | Font.ITALIC, 13));
		GridBagConstraints gbcBtnRegistrati = new GridBagConstraints();
		gbcBtnRegistrati.insets = new Insets(0, 0, 5, 5);
		gbcBtnRegistrati.gridx = 2;
		gbcBtnRegistrati.gridy = 8;
		panel2.add(btnRegistrati, gbcBtnRegistrati);
	}
	
}
