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

public class Guiserver extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private int feldgroesse;
	private int modus;

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
	public Guiserver(int g, int m) {
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);*/
		this.feldgroesse = g;
		this.modus = m;
		
		
		this.setTitle("Schiffe versenken-Server konfigurieren");
		this.setSize(1000, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(93, 62, 46, 14);
		getContentPane().add(lblPort);
		
		textField = new JTextField();
		textField.setBounds(127, 59, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnServerStarten = new JButton("Server starten");
		btnServerStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button starte server
				
			}
		});
		btnServerStarten.setBounds(93, 87, 120, 23);
		getContentPane().add(btnServerStarten);
		
		JLabel lblStatusMitspielerNicht = new JLabel("Status Mitspieler: nicht beigetreten");
		lblStatusMitspielerNicht.setBounds(93, 121, 225, 14);
		getContentPane().add(lblStatusMitspielerNicht);
		
		JLabel lblIpadresse = new JLabel("IP-Adresse: ");
		lblIpadresse.setBounds(93, 37, 184, 14);
		getContentPane().add(lblIpadresse);
		this.setVisible(true);
	}
}
