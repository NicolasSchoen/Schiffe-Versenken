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
import java.net.*;
import java.io.*;


/**
 * Hier kann man einen Server starten, wobei man den verwendeten Port selbst bestimmen kann.
 *
 * @author Nicolas Schoen
 * @version 1.0
 */


public class Guiserver extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblIpadresse;
	private JLabel lblStatusMitspielerNicht;
	private int feldgroesse;
	private int modus;
	private int port=12345;
	private boolean laden = false;
	private String fn="";
	private Spielstand game;

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
		textField.setText("12345");
		textField.setBounds(127, 59, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnServerStarten = new JButton("Server starten");
		btnServerStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button starte server
				
				port = Integer.parseInt(textField.getText());
				BufferedReader in = null;
				BufferedReader usr = null;
				Writer out = null;
				
				
				ServerSocket servSocket = null;
				try {
				    servSocket = new ServerSocket(port);
				    System.out.println("IP-Adresse:" + InetAddress.getLocalHost());
				    //lblIpadresse.setText("IP-Adresse: " + InetAddress.getLocalHost());
				    repaint();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Port bereits belegt!");
				    e.printStackTrace();
				}
				System.out.println("Server gestartet, warte auf Client zum joinen");
				//while(true){
				Socket s = null;
				
				try {
					s = servSocket.accept();
					
					
					// Ein- und Ausgabestrom des Sockets ermitteln
					// und als BufferedReader bzw. Writer verpacken
					// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
					in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					out = new OutputStreamWriter(s.getOutputStream());
					
					// Standardeingabestrom ebenfalls als BufferedReader verpacken.
					usr = new BufferedReader(new InputStreamReader(System.in));
				} catch (IOException e) {
					e.printStackTrace();
				}
				lblStatusMitspielerNicht.setText("Status: Spieler beigetreten");
				
				
				if(laden) {
					try {
						out.write("Laden " + fn + " ");
					    out.flush();
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					dispose();
					new Guishot(game, s);
				}
				else {
					try {
						out.write(String.format("%s%n", "Feld " + feldgroesse));
					    out.flush();
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					dispose();
					new Guiplace(feldgroesse, modus, s, true);
				}
				
				
					
					
				
					

				//}
				
			}
		});
		btnServerStarten.setBounds(93, 87, 120, 23);
		getContentPane().add(btnServerStarten);
		
		lblStatusMitspielerNicht = new JLabel("Status Mitspieler: nicht beigetreten");
		lblStatusMitspielerNicht.setBounds(93, 121, 225, 14);
		getContentPane().add(lblStatusMitspielerNicht);
		
		lblIpadresse = new JLabel("IP-Adresse: ");
		lblIpadresse.setBounds(93, 37, 184, 14);
		getContentPane().add(lblIpadresse);
		this.setVisible(true);
		try {
			lblIpadresse.setText("IP-Adresse: " + Inet4Address.getLocalHost());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Guiserver(Spielstand game, String filename) {
		this(game.spieler1.getGroesse(), game.modus);
		this.game = game;
		laden = true;
		fn = filename;
	}
}
