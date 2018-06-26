import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;


/**
 * Hier kann man die Schiffe platzieren. Möglich ist eine automatische,
 * zufällige Platzierung oder eine Platzierung per Hand, indem man die Schiffe und die Richtung auswählt
 * und dann das Feld an der Stelle anklickt, an der man das Schiff platzieren möchte.
 *
 * @author Nicolas Schoen
 * @version 1.0
 */

public class Guiplace extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private int richtung = 0;
	private String richtungT = "hoch";
	private boolean setzbar5 = true;
	private boolean setzbar4 = true;
	private boolean setzbar3 = true;
	private boolean setzbar2 = true;
	private int groesse;
	private int schiffsPunkte;
	private String schiffauswahl = "Schnellboot";
	private int schiffGroesse = 2;
	private JLabel lblRichtung;
	private JLabel lblVerfuegbareSchiffspunkte;
	private JLabel lblFlugzeugtraeger;
	private JLabel lblSchlachtschiff;
	private JLabel lblKreuzer;
	private JLabel lblSchnellboot;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	JLabel lblAuswahl;
	private Feld spielfeld;
	private boolean alleSchiffegesetzt=false;
	private JButton btnWeiter;
	private JButton btnZufaelligPlatzieren;
	private int modus;
	private boolean reihe;
	private BufferedReader in = null;
	private BufferedReader usr = null;
	private Writer out = null;
	private Socket s = null;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guiplace frame = new Guiplace();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * 
	 * @param g Feldgroesse
	 * @param m Modus
	 */
	public Guiplace(int g, int m) {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {			//gibt position der maus beim klicken aus
				int x=arg0.getX();
			    int y=arg0.getY();
			    int posx = (x-300)/(400/groesse);
			    int posy = (y-70)/(400/groesse);
			    lblNewLabel.setText("PosX:"+x + ", " + posx);
			    lblNewLabel_1.setText("PosY:"+y + ", " + posy);
			    
			    if(schiffsPunkte-schiffGroesse >= 0 && schiffsPunkte-schiffGroesse != 1)
			    {
				    if(spielfeld.schiffPlatzieren(posx, posy, schiffGroesse, richtung))
				    {
				    	schiffsPunkte-=schiffGroesse;
				    	
				    	System.out.println("Schiff platzieren erfolgreich");
				    	repaint();
				    	lblVerfuegbareSchiffspunkte.setText("Verfuegbare Schiffspunkte: " + schiffsPunkte);
				    	if(schiffsPunkte == 0)
				    	{
				    		alleSchiffegesetzt = true;
				    		btnWeiter.setEnabled(true);
				    		JOptionPane.showMessageDialog(null, "Alle Schiffe gesetzt!");
				    		//dispose();
							//Guiplace setship = new Guiplace(wert);
							//new Guishot(spielfeld);
				    	}
				    	
				    }
			    }
			    else
			    {
			    	JOptionPane.showMessageDialog(null, "Nicht genuegend Schiffspunkte, Bitte Schiffstyp aendern!");
			    }
			    
			    	
			    System.out.println("PosX:"+x+",PosY"+y);//these co-ords are relative to the component
			    
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);*/
		
		this.groesse = g;
		this.modus = m;
		this.spielfeld = new Feld(g);
		
		/*//////////testweise feld befuellen (feldgroesse = 10)//////////////////////////////////
		spielfeld.feldAendern(2, 5, 1);
		spielfeld.feldAendern(2, 4, 1);
		spielfeld.feldAendern(2, 3, 1);
		spielfeld.feldAendern(6, 8, 2);
		spielfeld.feldAendern(5, 8, 2);
		spielfeld.feldAendern(1, 7, 3);
		spielfeld.schiffPlatzieren(3, 6, 3, 1);
		spielfeld.schiffPlatzieren(7, 2, 5, 2);
		spielfeld.schiffPlatzieren(5, 3, 4, 1);//duerfte nicht gehen, da schiff kreutzt
		///////////////////////////////////////////////////////////////////////////////////////*/
		
		
		
		
		
		this.setTitle("Schiffe versenken-Schiffe platzieren");
		this.setSize(1000, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		this.schiffsPunkte = (int) (groesse * groesse * 0.3);
		
		lblVerfuegbareSchiffspunkte = new JLabel("Verfuegbare Schiffspunkte:" + schiffsPunkte);
		lblVerfuegbareSchiffspunkte.setBounds(12, 13, 199, 16);
		getContentPane().add(lblVerfuegbareSchiffspunkte);
		
		lblFlugzeugtraeger = new JLabel("Flugzeugtraeger(5):");
		lblFlugzeugtraeger.setOpaque(true);   						//aendert hintergrund
		lblFlugzeugtraeger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(schiffsPunkte-5 >= 0 && schiffsPunkte-5 != 1)	//pruefe ob auswahl moeglich
				{
					schiffauswahl = "Flugzeugtraeger"; 
					lblAuswahl.setText("Auswahl:" + schiffauswahl);
					schiffGroesse = 5;
				}
				
			}
		});
		lblFlugzeugtraeger.setBounds(41, 110, 149, 16);
		getContentPane().add(lblFlugzeugtraeger);
		
		lblSchlachtschiff = new JLabel("Schlachtschiff(4):");
		lblSchlachtschiff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(schiffsPunkte-4 >= 0 && schiffsPunkte-4 != 1)	//pruefe ob auswahl moeglich
				{
					schiffauswahl = "Schlachtschiff"; 
					lblAuswahl.setText("Auswahl:" + schiffauswahl);
					schiffGroesse = 4;
				}
				
			}
		});
		lblSchlachtschiff.setBounds(41, 139, 149, 16);
		getContentPane().add(lblSchlachtschiff);
		
		lblKreuzer = new JLabel("Kreuzer(3):");
		lblKreuzer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(schiffsPunkte-3 >= 0 && schiffsPunkte-3 != 1)	//pruefe ob auswahl moeglich
				{
					schiffauswahl = "Kreuzer"; 
					lblAuswahl.setText("Auswahl:" + schiffauswahl);
					schiffGroesse = 3;
				}
				
			}
		});
		lblKreuzer.setBounds(41, 168, 149, 16);
		getContentPane().add(lblKreuzer);
		
		lblSchnellboot = new JLabel("Schnellboot(2):");
		lblSchnellboot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(schiffsPunkte-2 >= 0 && schiffsPunkte-2 != 1)	//pruefe ob auswahl moeglich
				{
					schiffauswahl = "Schnellboot"; 
					lblAuswahl.setText("Auswahl:" + schiffauswahl);
					schiffGroesse = 2;
				}
				
			}
		});
		lblSchnellboot.setBounds(41, 197, 149, 16);
		getContentPane().add(lblSchnellboot);
		
		JButton btnRichtungAendern = new JButton("Richtung aendern");
		btnRichtungAendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		//Richtung aendern
				if(richtung >= 3)
				{
					richtung = 0;
				}
				else
				{
					richtung++;
				}
				richtungT = getRichtungName();
				
				lblVerfuegbareSchiffspunkte.setText("Verfuegbare Schiffspunkte: " + schiffsPunkte);
				
				System.out.println("Die neue Richtung betraegt:" + richtung + "(" + richtungT + ")");		//Debug
				
				lblRichtung.setText("Richtung: " + richtungT);
				/*lblFlugzeugtraeger.setText("Flugzeugtraeger(5):");		geht noch nicht(paint zeichnet ueber labels)
				lblSchlachtschiff.setText("Schlachtschiff(4):");
				lblKreuzer.setText("Kreuzer(3):");*/
				//lblSchnellboot.setText("Schnellboot(2):"+ " ");
				
				
			}
		});
		btnRichtungAendern.setBounds(34, 226, 134, 25);
		getContentPane().add(btnRichtungAendern);
		
		lblRichtung = new JLabel("Richtung: " + richtungT);
		lblRichtung.setBounds(41, 264, 127, 16);
		getContentPane().add(lblRichtung);
		
		lblAuswahl = new JLabel("Auswahl:" + schiffauswahl);
		lblAuswahl.setBounds(41, 293, 149, 16);
		getContentPane().add(lblAuswahl);
		
		lblNewLabel = new JLabel("PosX:");
		lblNewLabel.setBounds(41, 369, 103, 16);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("PosY:");
		lblNewLabel_1.setBounds(41, 398, 103, 16);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnSchiffeZuruecksetzen = new JButton("Schiffe zuruecksetzen");
		btnSchiffeZuruecksetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Schiffe zuruecksetzen
				spielfeld.feldInitialisieren();
				schiffsPunkte = (int) (groesse * groesse * 0.3);
				lblVerfuegbareSchiffspunkte.setText("Verfuegbare Schiffspunkte: " + schiffsPunkte);
				btnWeiter.setEnabled(false);
				repaint();
			}
		});
		btnSchiffeZuruecksetzen.setBounds(34, 467, 156, 25);
		getContentPane().add(btnSchiffeZuruecksetzen);
		
		btnZufaelligPlatzieren = new JButton("zufaellig platzieren");
		btnZufaelligPlatzieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button zufaelligplatzieren
				spielfeld.feldInitialisieren();
				Ki.alleSchiffeSetzen(spielfeld);
				lblVerfuegbareSchiffspunkte.setText("Verfuegbare Schiffspunkte: " + 0);
				repaint();
				btnWeiter.setEnabled(true);
			}
		});
		btnZufaelligPlatzieren.setBounds(34, 505, 156, 25);
		getContentPane().add(btnZufaelligPlatzieren);
		
		btnWeiter = new JButton("weiter");
		btnWeiter.setEnabled(false);
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				//Button weiter
				dispose();
				if(modus == 2)
					new Guishot(spielfeld, modus);
				else
					new Guishot(spielfeld, modus, s, reihe);
			}
		});
		btnWeiter.setBounds(885, 535, 97, 25);
		getContentPane().add(btnWeiter);
		
		
		
		this.setVisible(true);
	}
	
	
	
	/**
	 * Alternativer Konstruktor fuer Multiplayer
     *
     * @param g übergebene Feldgröße
     * @param m Modus
     * @param s Socket
     * @param reihe Zeigt, Ob Spieler an Reihe ist
     */
	
	public Guiplace(int g, int m, Socket s, boolean reihe) {
		this(g,m);
		this.s = s;
		this.reihe = reihe;
		try {
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
	}
	
	
	/**
     * gibt den Richtungsnamen zurück
     *@return Name der Richtung fuer Menschen lesbar
     */
	
	private String getRichtungName()
	{
		switch(richtung)
		{
		case 0:{return "hoch";}
		case 1:{return "rechts";}
		case 2:{return "runter";}
		case 3:{return "links";}
		}
		return "";
	}
	
	
	/**
     * Zeichnet das Spielfeld auf dem Bildschirm
     *
     * @param g Zeichenobjekt
     */
	
	public void paint(Graphics g){ 
		super.paint(g);
		int feldgroesse = 400;			//breite des Feldes
		//int v = feldgroesse%groesse;
		int xStart = 300;	
		int yStart = 100;//xStart-100;//xStart+feldgroesse;
		//int differenz = xStart-100;
        /*//vertikal/////////////
        for(int i = 0; i<=groesse; i++)
        {
        	g.drawLine(xStart+(i*(feldgroesse/groesse)),xStart-differenz,xStart+(i*(feldgroesse/groesse)),yStart-differenz-v); 
        }
        //horizontal////////
        for(int i = 0; i<=groesse; i++)
        {
        	g.drawLine(xStart,xStart-differenz+(i*(feldgroesse/groesse)),yStart-v,xStart-differenz+(i*(feldgroesse/groesse))); 
        }*/
		
		//alternativ mittels drawRect/ fillRect loesbar(einfacher):
		
		for(int i=0; i<groesse; i++)					//j=posX;  i=posY
		{
			for(int j=0; j<groesse; j++)
			{
				g.setColor(Color.black);
				g.drawRect(xStart+(i*(feldgroesse/groesse)), yStart+(j*(feldgroesse/groesse)), feldgroesse/groesse, feldgroesse/groesse);
				switch(spielfeld.getFeldInhalt(i, j))	//farbe bestimmen
				{
				case 0:g.setColor(Color.cyan);break;		//wasser
				case 1:g.setColor(Color.gray);break;		//schiff
				case 2:g.setColor(Color.red);break;			//getroffenes schiff
				case 3:g.setColor(Color.black);break;		//versenktes schiff
				case 4:g.setColor(Color.white);break;		//unbekannt
				}
				
				g.fillRect(xStart+(i*(feldgroesse/groesse))+1, yStart+(j*(feldgroesse/groesse))+1, feldgroesse/groesse-1, feldgroesse/groesse-1);
				
			}
				
		}
			
		
        
        /*lblFlugzeugtraeger.setText("Flugzeugtraeger(5):"+ setzbar5);
		lblSchlachtschiff.setText("Schlachtschiff(4):"+ setzbar4);
		lblKreuzer.setText("Kreuzer(3):"+ setzbar3);
        lblSchnellboot.setText("Schnellboot(2):"+ setzbar2);
        lblRichtung.setText("Richtung: " + richtung);
        lblAuswahl.setText("Auswahl:" + schiffauswahl);
        lblVerfuegbareSchiffspunkte.setText("Verfuegbare Schiffspunkte: " + schiffsPunkte);*/
    } 
}
