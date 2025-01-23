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
		setBounds(100, 100, 621, 390);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JTextPane txtpnP = new JTextPane();
		txtpnP.setForeground(new Color(0, 153, 255));
		txtpnP.setFont(new Font("FreeSerif", Font.BOLD, 26));
		txtpnP.setBounds(305, 25, 292, 198);
		txtpnP.setBackground(new Color(255, 255, 255));
		txtpnP.setText("            Benvenuto su\n           DietiEstates25\n\n        La casa dei tuoi\n                  sogni...\n      A portata di mano!");
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
        btnIscriviti.setBounds(102, 274, 117, 25);
        contentPane.add(btnIscriviti);
        
        JButton btnAccedi = new JButton("Accedi");
        btnAccedi.setForeground(new Color(0, 153, 255));
        btnAccedi.setBounds(393, 274, 117, 25);
        contentPane.add(btnAccedi);
		
	}
}
