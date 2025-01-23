package GUI;

import Class.Controller;
import java.awt.*;

import javax.swing.*;
/**import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec; **/
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FinestraLogin extends JFrame {

	//private static final long serialVersionUID = 1L;
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
					FinestraLogin frame = new FinestraLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	onestamente non so se questo sopra ci serve, quindi lo tengo commentato
	/**
	 * Create the frame.
	 */
	public FinestraLogin(Controller c) {
		setBackground(new Color(0, 153, 255));
		setTitle("DietiEstates25");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 527);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel imageLabel = new JLabel();
        imageLabel.setBounds(33, 25, 246, 198); 
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/immagini/LOGO.png")); 
        Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image)); 
        contentPane.add(imageLabel);
        
        JTextPane txtpnSeiGiIscritto = new JTextPane();
        txtpnSeiGiIscritto.setFont(new Font("FreeSerif", Font.BOLD, 16));
        txtpnSeiGiIscritto.setForeground(new Color(0, 153, 255));
        txtpnSeiGiIscritto.setText("Non sei iscritto?");
        txtpnSeiGiIscritto.setBounds(408, 254, 144, 21);
        contentPane.add(txtpnSeiGiIscritto);
        
        ImageIcon iconGoogle = new ImageIcon(getClass().getResource("/immagini/Google_logo.svg.png"));
        Image imgGoogle = iconGoogle.getImage();
        Image imgGoogleScaled = imgGoogle.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon finalGoogleIcon = new ImageIcon(imgGoogleScaled);
        JButton btnGoogle = new JButton(finalGoogleIcon);
        btnGoogle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnGoogle.setBounds(341, 180, 78, 33);
        contentPane.add(btnGoogle);
        
        ImageIcon iconFacebook = new ImageIcon(getClass().getResource("/immagini/facebook-logo.png"));
        Image imgFacebook = iconFacebook.getImage();
        Image imgFacebookScaled = imgFacebook.getScaledInstance(35, 25, Image.SCALE_SMOOTH);
        ImageIcon finalFacebookIcon = new ImageIcon(imgFacebookScaled);
        JButton btnFacebook = new JButton(finalFacebookIcon);
        btnFacebook.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnFacebook.setBounds(506, 180, 78, 33);
        contentPane.add(btnFacebook);
        
        ImageIcon iconGit = new ImageIcon(getClass().getResource("/immagini/gitlogo.png"));
        Image imgGit = iconGit.getImage();
        Image imgGitScaled = imgGit.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon finalGitIcon = new ImageIcon(imgGitScaled);
        JButton btnGit = new JButton(finalGitIcon);
        btnGit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        	
        });
        btnGit.setBounds(418, 180, 89, 33);
        contentPane.add(btnGit);
        
        textField = new JTextField();
        textField.setBounds(341, 83, 243, 20);
        contentPane.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(341, 115, 243, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        JButton btnNewButton_3 = new JButton("Accedi");
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_3.setForeground(new Color(0, 153, 255));
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButton_3.setBounds(418, 146, 89, 23);
        contentPane.add(btnNewButton_3);
        
        JButton btnNewButton = new JButton("ISCRIVITI ORA");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton.setForeground(new Color(0, 153, 255));
        btnNewButton.setBounds(408, 286, 124, 40);
        contentPane.add(btnNewButton);
        
        JTextPane txtpnLaCasaDei = new JTextPane();
        txtpnLaCasaDei.setForeground(new Color(0, 153, 255));
        txtpnLaCasaDei.setFont(new Font("Tahoma", Font.BOLD, 28));
        txtpnLaCasaDei.setText("La casa dei tuoi sogni");
        txtpnLaCasaDei.setBounds(10, 254, 388, 58);
        contentPane.add(txtpnLaCasaDei);
        
        JTextPane txtpnAPortataDi = new JTextPane();
        txtpnAPortataDi.setForeground(new Color(0, 153, 255));
        txtpnAPortataDi.setFont(new Font("Tahoma", Font.BOLD, 28));
        txtpnAPortataDi.setText("A portata di mano");
        txtpnAPortataDi.setBounds(32, 323, 287, 58);
        contentPane.add(txtpnAPortataDi);
		
        
        
	}
}
