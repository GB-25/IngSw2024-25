package GUI;

import java.awt.*;

import javax.swing.*;


import Class.Controller;


public class FinestraRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

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
	} */

	/**
	 * Create the frame.
	 */
	public FinestraRegistrazione() {
		setBackground(new Color(0, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logoLabel = new JLabel();
		logoLabel.setBounds(33, 0, 103, 67);
		ImageIcon logo = new ImageIcon(getClass().getResource("/immagini/LOGO.png"));
		Image imageLogo = logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(imageLogo)); 
		contentPane.add(logoLabel);
		
		JLabel lblRegistratiInPochi = new JLabel("Registrati in pochi passi!");
		lblRegistratiInPochi.setFont(new Font("Dialog", Font.BOLD, 26));
		lblRegistratiInPochi.setForeground(new Color(0, 153, 255));
		lblRegistratiInPochi.setBounds(187, 12, 377, 40);
		contentPane.add(lblRegistratiInPochi);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(33, 101, 70, 15);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(78, 99, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cognome\n");
		lblNewLabel.setBounds(249, 101, 70, 15);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(319, 99, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}
