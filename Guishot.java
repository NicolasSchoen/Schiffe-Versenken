import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sun.applet.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class Guishot extends JFrame {

	private JPanel contentPane;
	private Feld spieler;
	private Feld spieler2;
	private Feld gegner;
	private int fgroesse;
	private boolean schiesse = false;
	JButton btnSchiessen;
	JLabel lblGegnerpunkte;
	JLabel lblEigenePunkte;
	private int eigenePunkte, gegnerischepunkte;
	Cursor c;

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
	public Guishot(Feld f) {
		
		
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {			//gibt position der maus beim klicken aus
				int x=arg0.getX();
			    int y=arg0.getY();
			    int posx = (x-500)/(400/fgroesse);
			    int posy = (y-70)/(400/fgroesse);
			    
			    if(schiesse == true)
			    {
			    	if(posx >= 0 && posx < fgroesse && posy >= 0 && posy < fgroesse)
			    	{
			    		if(!gegner.bereitsBeschossen(posx, posy))	//schiesse nur wenn noch nicht geschossen
				    	{
				    		System.out.println("X:" + posx + ",Y:" + posy);
				    		int gegnerwert = spieler2.schiessen(posx, posy);
				    		gegner.feldAendern(posx, posy, gegnerwert);
				    		
				    		
				    		if(gegnerwert == 2 || gegnerwert == 3)
				    		{
				    			gegnerischepunkte-=1;
				    			if(gegnerwert == 3)
				    			{
				    				gegner.schiffVersenken(posx, posy);
				    				
				    				try {
					    		        Clip clip = AudioSystem.getClip();
					    		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
					    		          Main.class.getResourceAsStream("/media/explosion.wav"));
					    		        clip.open(inputStream);
					    		        clip.start(); 
					    		      } catch (Exception e) {
					    		        System.err.println(e.getMessage());
					    		      }
				    			}
				    			else
				    			{
				    				try {
					    		        Clip clip = AudioSystem.getClip();
					    		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
					    		          Main.class.getResourceAsStream("/media/treffer.wav"));
					    		        clip.open(inputStream);
					    		        clip.start(); 
					    		      } catch (Exception e) {
					    		        System.err.println(e.getMessage());
					    		      }
				    			}
				    				
				    			repaint();
				    			//System.out.println("gegnerische Punkte:" + gegnerischepunkte);
				    			lblGegnerpunkte.setText("Gegnerpunkte: " + gegnerischepunkte);
				    			if(gegnerischepunkte <=0)
				    			{
				    				//spieler hat gewonnen
				    				JOptionPane.showMessageDialog(null, "Gewonnen!");
				    			}
				    		}
				    		else
				    		{
				    			schiesse = false;
				    			//c.getDefaultCursor();
				    			btnSchiessen.setBackground(Color.white);
				    			btnSchiessen.setEnabled(true);
				    			
				    			try {
				    		        Clip clip = AudioSystem.getClip();
				    		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
				    		          Main.class.getResourceAsStream("/media/wasser.wav"));
				    		        clip.open(inputStream);
				    		        clip.start(); 
				    		      } catch (Exception e) {
				    		        System.err.println(e.getMessage());
				    		      }
				    			repaint();
				    		}
				    		
				    		
				    	}
			    	
			    	}
			    	
			    }
			    
			}
		});
		
		
		
		////Ende mouse listener
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);*/
		
		this.spieler = f;
		fgroesse = spieler.getGroesse();
		//noch zum test
		//spieler2 = new Feld(fgroesse);	//Feld des Gegners(KI) KI rudimentär implementiert, noch testen!
		spieler2 = new Feld(fgroesse);
		if(Ki.alleSchiffeSetzen(spieler2))
			System.out.println("Ki hat alle Schiffe gesetzt!");
		else
			System.out.println("Zu viele Fehler!");
		//spieler2 = spieler;
		//spieler2.schiffPlatzieren(2, 4, 4, 1);
		//
		eigenePunkte = gegnerischepunkte = (int) (fgroesse * fgroesse * 0.3);
		gegner= new Feld(fgroesse);
		gegner.gegnerInitialisieren();	//setze alle felder auf unbekannt
		
		this.setTitle("Schiffe versenken schiessen");
		this.setSize(1000, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		
		JButton btnBeenden = new JButton("Beenden");
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		//beenden button
				System.exit(0);
			}
		});
		btnBeenden.setBounds(10, 11, 89, 23);
		getContentPane().add(btnBeenden);
		
		JButton btnSpeichernBeenden = new JButton("Speichern  und beenden");
		btnSpeichernBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		//speichern und beenden button
				////speichern
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(null);
				
				////
				System.exit(0);
			}
		});
		btnSpeichernBeenden.setBounds(109, 11, 171, 23);
		getContentPane().add(btnSpeichernBeenden);
		
		JLabel lblEigenesFeld = new JLabel("eigenes Feld");
		lblEigenesFeld.setBounds(202, 47, 89, 16);
		getContentPane().add(lblEigenesFeld);
		
		JLabel lblGegnerischesFeld = new JLabel("gegnerisches Feld");
		lblGegnerischesFeld.setBounds(654, 47, 122, 16);
		getContentPane().add(lblGegnerischesFeld);
		
		btnSchiessen = new JButton("schiessen");
		btnSchiessen.setBackground(Color.white);
		btnSchiessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		//schiessen button
				schiesse = true;
				btnSchiessen.setBackground(Color.orange);
				btnSchiessen.setEnabled(false);
				//c = getToolkit().createCustomCursor(new ImageIcon("img.png").getImage(), new Point(10,10), "Cursor");
				//c.getSystemCustomCursor();
			}
		});
		btnSchiessen.setBounds(560, 532, 97, 25);
		getContentPane().add(btnSchiessen);
		
		lblGegnerpunkte = new JLabel("Gegnerpunkte:");
		lblGegnerpunkte.setBounds(773, 47, 130, 16);
		getContentPane().add(lblGegnerpunkte);
		
		lblEigenePunkte = new JLabel("eigene Punkte:");
		lblEigenePunkte.setBounds(291, 47, 130, 16);
		getContentPane().add(lblEigenePunkte);
		
		/*System.out.println("Rahmen" + getInsets());
		System.out.println("Breite" + getWidth());
		System.out.println("Hoehe" + getHeight());*/
		
		lblGegnerpunkte.setText("Gegnerpunkte: " + gegnerischepunkte);
		lblEigenePunkte.setText("eigene Punkte: " + eigenePunkte);
		this.setVisible(true);
	}
	
	public void paint(Graphics g){ 
		super.paint(g);
		int feldgroesse = 400;			//breite des gezeichneten Feldes
		int xStart = 50;
		int xgStart = xStart+feldgroesse+50;
		int yStart = 100;//xStart-100;//xStart+feldgroesse;
		
		//Spielerfeld:
		
		for(int i=0; i<fgroesse; i++)					//j=posX;  i=posY
		{
			for(int j=0; j<fgroesse; j++)
			{
				g.setColor(Color.black);
				g.drawRect(xStart+(i*(feldgroesse/fgroesse)), yStart+(j*(feldgroesse/fgroesse)), feldgroesse/fgroesse, feldgroesse/fgroesse);
				switch(spieler.getFeldInhalt(i, j))	//farbe bestimmen
				{
				case 0:g.setColor(Color.cyan);break;		//wasser
				case 1:g.setColor(Color.gray);break;		//schiff
				case 2:g.setColor(Color.red);break;			//getroffenes schiff
				case 3:g.setColor(Color.black);break;		//versenktes schiff
				case 4:g.setColor(Color.white);break;		//unbekannt
				}
				
				g.fillRect(xStart+(i*(feldgroesse/fgroesse))+1, yStart+(j*(feldgroesse/fgroesse))+1, feldgroesse/fgroesse-1, feldgroesse/fgroesse-1);
				
			}
				
		}
			
		//Zeichne gegner. feld:
		for(int i=0; i<fgroesse; i++)					//j=posX;  i=posY
		{
			for(int j=0; j<fgroesse; j++)
			{
				g.setColor(Color.black);
				g.drawRect(xgStart+(i*(feldgroesse/fgroesse)), yStart+(j*(feldgroesse/fgroesse)), feldgroesse/fgroesse, feldgroesse/fgroesse);
				switch(gegner.getFeldInhalt(i, j))	//farbe bestimmen
				{
				case 0:g.setColor(Color.cyan);break;		//wasser
				case 1:g.setColor(Color.gray);break;		//schiff
				case 2:g.setColor(Color.red);break;			//getroffenes schiff
				case 3:g.setColor(Color.black);break;		//versenktes schiff
				case 4:g.setColor(Color.white);break;		//unbekannt
				}
				
				g.fillRect(xgStart+(i*(feldgroesse/fgroesse))+1, yStart+(j*(feldgroesse/fgroesse))+1, feldgroesse/fgroesse-1, feldgroesse/fgroesse-1);
				
			}
				
		}
    } 
}
