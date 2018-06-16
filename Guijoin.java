import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.awt.event.ActionEvent;
import java.net.*;

public class Guijoin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private int port;
	private String ipaddr;

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
		
		
		this.setTitle("Schiffe versenken-Spiel beitreten");
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
				
				if(!textField.getText().equals("") && !textField_1.getText().equals(""))
				{
					port = Integer.parseInt(textField.getText());
					ipaddr = textField_1.getText();
					BufferedReader in = null;
					BufferedReader usr = null;
					Writer out = null;
					Socket s = null;
					try {
						s = new Socket(ipaddr, port);
						System.out.println("Connection established.");
						
						// Ein- und Ausgabestrom des Sockets ermitteln
						// und als BufferedReader bzw. Writer verpacken
						// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
						in = new BufferedReader(new InputStreamReader(s.getInputStream()));
						out = new OutputStreamWriter(s.getOutputStream());
						
						// Standardeingabestrom ebenfalls als BufferedReader verpacken.
						usr = new BufferedReader(new InputStreamReader(System.in));
						
						
					} catch(IOException e) {
						JOptionPane.showMessageDialog(null, "Daten stimmen nicht!");
						e.printStackTrace();
					}
					
					
					try {
						String line = in.readLine();
					    System.out.println(line);
					    
					    if (line.contains("Feld"))
					    {
					    	String[] arr = line.split(" ");
					    	dispose();
					    	new Guiplace(Integer.valueOf(arr[1]), 1, s, false);
					    }
					    
					}catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Bitte Adresse und Port angeben!");
				}
				
				
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
		
		JButton btnAlsKiBeitreten = new JButton("Als Ki beitreten");
		btnAlsKiBeitreten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {									//Button als Ki beitreten
				
				if(!textField.getText().equals("") && !textField_1.getText().equals(""))
				{
					port = Integer.parseInt(textField.getText());
					ipaddr = textField_1.getText();
					BufferedReader in = null;
					BufferedReader usr = null;
					Writer out = null;
					Socket s = null;
					try {
						s = new Socket(ipaddr, port);
						System.out.println("Connection established.");
						
						// Ein- und Ausgabestrom des Sockets ermitteln
						// und als BufferedReader bzw. Writer verpacken
						// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
						in = new BufferedReader(new InputStreamReader(s.getInputStream()));
						out = new OutputStreamWriter(s.getOutputStream());
						
						// Standardeingabestrom ebenfalls als BufferedReader verpacken.
						usr = new BufferedReader(new InputStreamReader(System.in));
						
						
					} catch(IOException e) {
						JOptionPane.showMessageDialog(null, "Daten stimmen nicht!");
						e.printStackTrace();
					}
					
					
					try {
						String line = in.readLine();
					    System.out.println(line);
					    
					    if (line.contains("Feld"))
					    {
					    	String[] arr = line.split(" ");
					    	dispose();
					    	new Guiplace(Integer.valueOf(arr[1]), 3, s, false);
					    }
					    
					    if(line.contains("Laden")) {
					    	String[] arr = line.split(" ");
					    	System.out.println("ladenxxx");
					    	JOptionPane.showMessageDialog(null, "Laden von: " + arr[1]);
					    	
					    	JFileChooser fc = new JFileChooser();
							BufferedReader reader;
							String spielstand;
					        StringBuilder sb = new StringBuilder();
							
							
							
							int rueckgabeWert = fc.showOpenDialog(null);
							
							System.out.println(rueckgabeWert);
					        
					        /* Abfrage, ob auf "Öffnen" geklickt wurde */
					        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
					        {
					             // Ausgabe der ausgewaehlten Datei
					            System.out.println("Die zu öffnende Datei ist: " +
					                  fc.getSelectedFile().getName());
					            
					            
					            try {
					                reader = new BufferedReader(new FileReader(fc.getSelectedFile()));
					                String lne = reader.readLine();
					                while(lne != null) {
					                    sb.append(lne);
					                    sb.append(System.lineSeparator());
					                    lne = reader.readLine();
					                }
					                spielstand = sb.toString();
					                Spielstand game = new Spielstand(spielstand);
					                
					                dispose();
					                new Guishot(game, s);
					                
					            } 
					            catch (FileNotFoundException e1) {
					                e1.printStackTrace();
					            }
					            catch (IOException e1) {
					                e1.printStackTrace();
					            }
					            
					            
					        }
					    	
					    }
					    
					}catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Bitte Adresse und Port angeben!");
				}
				
				
			}
		});
		btnAlsKiBeitreten.setBounds(163, 124, 150, 23);
		getContentPane().add(btnAlsKiBeitreten);
		this.setVisible(true);
	}
}
