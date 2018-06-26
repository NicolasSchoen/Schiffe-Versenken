import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import javax.swing.JToggleButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * Gui, gibt Auswahl an, ob man ein neues Spiel beginnen moechte,
 * einen Spielstand laden oder einem Spiel beitreten
 *
 * @author Nicolas Schoen
 * @version 1.0
 */

public class Gui extends javax.swing.JFrame{
	/**
     * 
     *
     * 
     */
	
	
	public Gui() {
		getContentPane().setLayout(null);
		
		JButton btnNeuesSpiel = new JButton("Neues Spiel");
		btnNeuesSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button neues Spiel gedrueckt
				/*Gui newgame=new Gui();
				newgame.setTitle("Schiffe versenken-Modus auswaehlen");
				newgame.setSize(1000, 620);
				newgame.setResizable(false);
				newgame.setLocation(50, 50);
				newgame.setVisible(true);*/
				dispose();
				//Guisetmode setmode = new Guisetmode();
				new Guisetmode();
			}
		});
		btnNeuesSpiel.setBounds(116, 52, 188, 59);
		getContentPane().add(btnNeuesSpiel);
		
		JButton btnSpielLaden = new JButton("Spiel laden");
		btnSpielLaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		//Spiel laden button
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
		                String line = reader.readLine();
		                while(line != null) {
		                    sb.append(line);
		                    sb.append(System.lineSeparator());
		                    line = reader.readLine();
		                }
		                spielstand = sb.toString();
		                Spielstand game = new Spielstand(spielstand);
		                
		                if(game.modus == 2) {
		                	dispose();
		                	new Guishot(game);
		                }
		                else {
		                	dispose();
		                	new Guiserver(game, fc.getSelectedFile().getName());
		                }
		            } 
		            catch (FileNotFoundException e1) {
		                e1.printStackTrace();
		            }
		            catch (IOException e1) {
		                e1.printStackTrace();
		            }
		            
		            
		        }
			}
		});
		btnSpielLaden.setBounds(116, 122, 188, 59);
		getContentPane().add(btnSpielLaden);
		
		JButton btnSpielBeitreten = new JButton("Spiel beitreten");
		btnSpielBeitreten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//Button spiel beitreten
				dispose();
				new Guijoin();
			}
		});
		btnSpielBeitreten.setBounds(116, 192, 188, 59);
		getContentPane().add(btnSpielBeitreten);
		
		JLabel lblSchiffeVersenken = new JLabel("Schiffe Versenken");
		lblSchiffeVersenken.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSchiffeVersenken.setBounds(116, 11, 225, 29);
		getContentPane().add(lblSchiffeVersenken);
	}
	
	public static void main(String[] args) {
	    // TODO code application logic here
		Gui frame=new Gui();
	    frame.setTitle("Schiffe versenken");
	    frame.setSize(1000, 620);
	    frame.setResizable(false);
	    frame.setLocation(50, 50);
	    frame.setVisible(true);
	}
}
