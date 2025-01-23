package GUI;

import Class.Controller;
import java.awt.*;

import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FinestraLogin extends JFrame {

	//private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	

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
		JTextPane txtpnP = new JTextPane();
		txtpnP.setForeground(new Color(0, 153, 255));
		txtpnP.setFont(new Font("FreeSerif", Font.BOLD, 26));
		txtpnP.setBounds(305, 25, 292, 73);
		txtpnP.setBackground(new Color(255, 255, 255));
		txtpnP.setText("            Benvenuto su\n           DietiEstates25");
		contentPane.add(txtpnP);
		
		JLabel imageLabel = new JLabel();
        imageLabel.setBounds(33, 25, 246, 198); 
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/immagini/LOGO.png")); 
        Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image)); 
        contentPane.add(imageLabel);
        
        JButton btnIscriviti = new JButton("Iscriviti");
        btnIscriviti.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnIscriviti.setForeground(new Color(0, 153, 255));
        btnIscriviti.setBounds(420, 203, 117, 25);
        contentPane.add(btnIscriviti);
        
        JButton btnAccedi = new JButton("Accedi");
        btnAccedi.setForeground(new Color(0, 153, 255));
        btnAccedi.setBounds(420, 285, 117, 25);
        contentPane.add(btnAccedi);
        
        JTextPane txtpnSeiGiIscritto = new JTextPane();
        txtpnSeiGiIscritto.setFont(new Font("FreeSerif", Font.BOLD, 16));
        txtpnSeiGiIscritto.setForeground(new Color(0, 153, 255));
        txtpnSeiGiIscritto.setText("Sei già iscritto?");
        txtpnSeiGiIscritto.setBounds(430, 250, 144, 21);
        contentPane.add(txtpnSeiGiIscritto);
        
        JTextPane txtpnLaCasaDei = new JTextPane();
        txtpnLaCasaDei.setFont(new Font("FreeSerif", Font.BOLD, 26));
        txtpnLaCasaDei.setForeground(new Color(0, 153, 255));
        txtpnLaCasaDei.setText("La casa dei tuoi sogni...");
        txtpnLaCasaDei.setBounds(337, 95, 300, 35);
        contentPane.add(txtpnLaCasaDei);
        
        JTextPane txtpnaPortataDi = new JTextPane();
        txtpnaPortataDi.setForeground(new Color(0, 153, 255));
        txtpnaPortataDi.setFont(new Font("FreeSerif", Font.BOLD, 26));
        txtpnaPortataDi.setText("...A portata di mano!");
        txtpnaPortataDi.setBounds(347, 142, 262, 49);
        contentPane.add(txtpnaPortataDi);
        
        ImageIcon iconGoogle = new ImageIcon(getClass().getResource("/immagini/Google_logo.svg.png"));
        Image imgGoogle = iconGoogle.getImage();
        Image imgGoogleScaled = imgGoogle.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalGoogleIcon = new ImageIcon(imgGoogleScaled);
        JButton btnNewButton = new JButton(finalGoogleIcon);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButton.setBounds(123, 237, 89, 73);
        contentPane.add(btnNewButton);
		
	}
}
