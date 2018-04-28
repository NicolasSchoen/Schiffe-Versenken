import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Guijoin extends JFrame {

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
					Guisetmode frame = new Guisetmode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Guijoin() {
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);*/
		
		
		this.setTitle("Schiffe versenken-Server konfigurieren");
		this.setSize(1000, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(75, 62, 64, 14);
		getContentPane().add(lblPort);
		
		textField = new JTextField();
		textField.setBounds(163, 59, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnServerBeitreten = new JButton("Server beitreten");
		btnServerBeitreten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button server beitreten
				
			}
		});
		btnServerBeitreten.setBounds(163, 90, 150, 23);
		getContentPane().add(btnServerBeitreten);
		
		JLabel lblIpadresse = new JLabel("IP-Adresse: ");
		lblIpadresse.setBounds(75, 37, 86, 14);
		getContentPane().add(lblIpadresse);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(163, 34, 150, 20);
		getContentPane().add(textField_1);
		this.setVisible(true);
	}
}
