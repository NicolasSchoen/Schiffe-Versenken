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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class Guishot extends JFrame {

	private JPanel contentPane;
	private Feld spieler;
	private Feld spieler2;
	private Feld gegner;
	private Feld gegner2;
	private int fgroesse;
	private boolean schiesse = true;
	private boolean multiplayer = false;
	JButton btnSchiessen;
	JLabel lblGegnerpunkte;
	JLabel lblEigenePunkte;
	private int eigenePunkte, gegnerischepunkte;
	Cursor c;
	private int modus;
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
	public Guishot(Feld f, int m) {
		
		
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {			//gibt position der maus beim klicken aus
				int x=arg0.getX();
			    int y=arg0.getY();
			    int posx = (x-500)/(400/fgroesse);
			    int posy = (y-70)/(400/fgroesse);
			    
			    if(multiplayer == false)
			    {
			    	schiesseSingleplayer(posx, posy);
			    }
			    else
			    {
			    	if(schiesse)
			    		schiesseMultiplayer(posx, posy);
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
		this.modus = m;
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
		gegner2 = new Feld(fgroesse);
		gegner2.gegnerInitialisieren();
		
		this.setTitle("Schiffe versenken schiessen");
		this.setSize(1500, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		
		JButton btnBeenden = new JButton("Beenden");
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		//beenden button
				
				try {
					s.close();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
				System.exit(0);
			}
		});
		btnBeenden.setBounds(10, 11, 89, 23);
		getContentPane().add(btnBeenden);
		
		JButton btnSpeichernBeenden = new JButton("Speichern  und beenden");
		btnSpeichernBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		//speichern und beenden button
				
				int anreihe;
				if(schiesse)
				{
					anreihe = 1;
				}
				else
				{
					anreihe = 0;
				}
				
				Spielstand spielstand = new Spielstand(spieler, spieler2, gegner, gegner2, modus, anreihe);
				
				
				////speichern
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(null);
				
				////
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	
	public Guishot(Feld f, int m, Socket s, boolean reihe) {
		this(f,m);
		multiplayer = true;
		this.s = s;
		schiesse = reihe;
		
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
		
		if(modus == 1) {
			while(schiesse == false)
				waitforShot();
		}
		else
		{
			if(modus == 3) {
				schiesseKIMult();
			}
		}
		
		
		
	}
	
	private void schiesseKIMult() {
		int punkte = 0;
		boolean oben=false;
		boolean rechts=false;
		boolean unten=false;
		boolean links=false;
		
		boolean trefa=false;
		int trefx = 0;
		int trefy = 0;
			
		int tref2x = 0;
		int tref2y = 0;
		
		
		
		while(true)
		{
				
			
			Random rand = new Random();
			int posx = rand.nextInt(gegner.getGroesse());
			int posy = rand.nextInt(gegner.getGroesse());
			if(punkte < (int)(gegner.getGroesse() * gegner.getGroesse() * 0.3) && schiesse)
			{
				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				System.out.println("Ki tut was");
				
				
				
				//Wenn getroffen
				if(trefa == true) {
					if(oben == true) {
						System.out.println("oben");
						if(gegner.inFeld(tref2x, tref2y-1) && !gegner.bereitsBeschossen(tref2x, tref2y-1))
						{
							tref2y--;
							int wert = multShot(tref2x, tref2y);
							if(wert == 1)
							{
								punkte++;
								rechts = links = false;
								System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								schiesse = true;
								continue;
							}
							if(wert == 2)
							{
								punkte++;
								System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								gegner.schiffVersenkenv2(tref2x, tref2y);
								trefa = false;
								schiesse = true;
								continue;
							}
							if(wert == 0)
							{
								gegner.feldAendern(tref2x, tref2y, wert);
								tref2x = trefx;
								tref2y = trefy;
								oben = false;
								schiesse = false;
								continue;
							}	
						}
						else
						{
							tref2x = trefx;
							tref2y = trefy;
							oben = false;
						}
					}
					
					if(rechts == true) {
						System.out.println("rechts");
						if(gegner.inFeld(tref2x+1, tref2y) && !gegner.bereitsBeschossen(tref2x+1, tref2y))
						{
							tref2x++;
							int wert = multShot(tref2x, tref2y);
							if(wert == 1)
							{
								punkte++;
								oben = unten = false;
								System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								schiesse = true;
								continue;
							}
							if(wert == 2)
							{
								punkte++;
								System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								gegner.schiffVersenkenv2(tref2x, tref2y);
								trefa = false;
								schiesse = true;
								continue;
							}
							if(wert == 0)
							{
								gegner.feldAendern(tref2x, tref2y, wert);
								tref2x = trefx;
								tref2y = trefy;
								rechts = false;
								schiesse = false;
								continue;
							}	
						}
						else
						{
							tref2x = trefx;
							tref2y = trefy;
							rechts = false;
						}
					}
					
					if(unten == true) {
						System.out.println("unten");
						if(gegner.inFeld(tref2x, tref2y+1) && !gegner.bereitsBeschossen(tref2x, tref2y+1))
						{
							tref2y++;
							int wert = multShot(tref2x, tref2y);
							if(wert == 1)
							{
								punkte++;
								rechts = links = false;
								System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								schiesse = true;
								continue;
							}
							if(wert == 2)
							{
								punkte++;
								System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								gegner.schiffVersenkenv2(tref2x, tref2y);
								trefa = false;
								schiesse = true;
								continue;
							}
							if(wert == 0)
							{
								gegner.feldAendern(tref2x, tref2y, wert);
								tref2x = trefx;
								tref2y = trefy;
								unten = false;
								schiesse = false;
								continue;
							}	
						}
						else
						{
							tref2x = trefx;
							tref2y = trefy;
							unten = false;
						}
					}
					
					if(links == true) {
						System.out.println("links");
						if(gegner.inFeld(tref2x-1, tref2y) && !gegner.bereitsBeschossen(tref2x-1, tref2y))
						{
							tref2x--;
							int wert = multShot(tref2x, tref2y);
							if(wert == 1)
							{
								punkte++;
								oben = unten = false;
								System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								schiesse = true;
								continue;
							}
							if(wert == 2)
							{
								punkte++;
								System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
								gegner.feldAendern(tref2x, tref2y, wert+1);
								gegner.schiffVersenkenv2(tref2x, tref2y);
								trefa = false;
								schiesse = true;
								continue;
							}
							if(wert == 0)
							{
								gegner.feldAendern(tref2x, tref2y, wert);
								tref2x = trefx;
								tref2y = trefy;
								links = false;
								schiesse = false;
								continue;
							}	
						}
						else
						{
							tref2x = trefx;
							tref2y = trefy;
							links = false;
						}
					}
					schiesse = false;
					continue;
				}
				
				
				
				
				
				
				
				
				
				
				
				//Wenn nicht getroffen
				
				posx = rand.nextInt(gegner.getGroesse());
				posy = rand.nextInt(gegner.getGroesse());
				
				while(gegner.bereitsBeschossen(posx, posy))
				{
					posx = rand.nextInt(gegner.getGroesse());
					posy = rand.nextInt(gegner.getGroesse());
				}
				if(!gegner.bereitsBeschossen(posx, posy))
				{
					int wert = multShot(posx, posy);
					if(wert == 0)
					{
						gegner.feldAendern(posx, posy, wert);
						schiesse = false;
						continue;
					}
					else
					{
						punkte++;
						System.out.println("KI fPunkte: " + punkte);
						gegner.feldAendern(posx, posy, wert+1);
						trefa = true;
						trefx = tref2x = posx;
						trefy = tref2y= posy;
						oben = rechts = unten = links = true;
						if(wert == 2)
							gegner.schiffVersenkenv2(posx, posy);
						schiesse = true;
						continue;
					}
						
				}
			}
			else
			{
				if(punkte >= (int)(gegner.getGroesse() * gegner.getGroesse() * 0.3))
				{
					JOptionPane.showMessageDialog(null, "Gewonnen!");
					System.exit(0);
				}
				
			}
			zeichneFeldNeu();
			
			while(schiesse == false)
				waitforShot();
		}
			

	}
	
	private int multShot(int x, int y) {
		
		int wert;
		try {
			out.write(String.format("%s%n", "Schuss " + x + "," + y));
		    out.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//while(true)//warte auf antwort
		//{
			try {
				String line = in.readLine();
			    System.out.println(line);
			    
			    if (line.contains("Antwort"))
			    {
			    	String[] arr = line.split(" ");
			    	//dispose();
			    	wert = Integer.valueOf(arr[1]);
			    	return wert;
			    }
			}catch(IOException e) {
				e.printStackTrace();
			}
		
		return -1;
	}
	
	private void schiesseSingleplayer(int posx, int posy) {
		if(schiesse == true)
	    {
	    	btnSchiessen.setBackground(Color.orange);
	    	if(posx >= 0 && posx < fgroesse && posy >= 0 && posy < fgroesse)
	    	{
	    		if(!gegner.bereitsBeschossen(posx, posy))	//schiesse nur wenn noch nicht geschossen
		    	{
		    		System.out.println("X:" + posx + ",Y:" + posy);
		    		int gegnerwert = spieler2.schiessen(posx, posy);
		    		
		    		
		    		
		    		if(gegnerwert == 1 || gegnerwert == 2)
		    		{
		    			gegner.feldAendern(posx, posy, gegnerwert+1);
		    			gegnerischepunkte-=1;
		    			if(gegnerwert == 2)
		    			{
		    				gegner.schiffVersenkenv2(posx, posy);
		    				//gegner.schiffVersenkenv2(posx, posy);	//zeigt bei versenktem schiff umliegende wasserfelder an
		    				
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
		    				System.exit(0);
		    			}
		    		}
		    		else
		    		{
		    			gegner.feldAendern(posx, posy, gegnerwert);
		    			
		    			try {
		    		        Clip clip = AudioSystem.getClip();
		    		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		    		          Main.class.getResourceAsStream("/media/wasser.wav"));
		    		        clip.open(inputStream);
		    		        clip.start(); 
		    		      } catch (Exception e) {
		    		        System.err.println(e.getMessage());
		    		      }
		    			
		    			schiesse = false;
		    			repaint();
		    			//c.getDefaultCursor();
		    			btnSchiessen.setEnabled(false);
		    			while(schiesse == false)
		    			{
		    				
		    				
		    				eigenePunkte = spieler.getFeldpunkte();
			    			lblEigenePunkte.setText("eigene punkte: " + eigenePunkte);
			    			repaint();
			    			if(!Ki.schiesse(spieler, gegner2))
			    				schiesse = true;
		    			}
		    			zeichneFeldNeu();
		    			//do {
		    				
			    			
			    			
			    			/*try {										//wartete nach jedem schiessen 1 sekunde, zum nachvollziehen
			    				TimeUnit.SECONDS.sleep(1);
			    			} catch (InterruptedException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}*/
			    			
		    			//}while();
		    			//Ki.schiesse(spieler, gegner2);		//ki schiesst
		    			//eigenePunkte = spieler.getFeldpunkte();
		    			//lblEigenePunkte.setText("eigene punkte: " + eigenePunkte);
		    			//repaint();
		    			if(eigenePunkte == 0)
		    			{
		    				//spieler hat verloren
		    				JOptionPane.showMessageDialog(null, "Verloren!");
		    				System.exit(0);
		    			}
		    			btnSchiessen.setBackground(Color.white);
		    			btnSchiessen.setEnabled(true);
		    			repaint();
		    			
		    			
		    			
		    		}
		    		
		    		
		    	}
	    	
	    	}
	    	
	    }
	    else				//wird nie aufgerufen
	    {
	    	//if(!Ki.schiesse(spieler, gegner2))
	    	//	schiesse = true;
	    	//JOptionPane.showMessageDialog(null, "Gegner an Reihe!");
	    }
	}
	
	private void schiesseMultiplayer(int posx, int posy) {
		if(schiesse) 
		{
			if(gegner.inFeld(posx, posy) && !gegner.bereitsBeschossen(posx, posy))
			{
				int wert;
				try {
					out.write(String.format("%s%n", "Schuss " + posx + "," + posy));
				    out.flush();
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				//while(true)//warte auf antwort
				//{
					try {
						String line = in.readLine();
					    System.out.println(line);
					    
					    if (line.contains("Antwort"))
					    {
					    	String[] arr = line.split(" ");
					    	//dispose();
					    	wert = Integer.valueOf(arr[1]);
					    	
					    	if(wert == 0)
					    	{
					    		gegner.feldAendern(posx, posy, wert);
				    			
				    			try {
				    		        Clip clip = AudioSystem.getClip();
				    		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
				    		          Main.class.getResourceAsStream("/media/wasser.wav"));
				    		        clip.open(inputStream);
				    		        clip.start(); 
				    		      } catch (Exception e) {
				    		        System.err.println(e.getMessage());
				    		      }
				    			
				    			schiesse = false;
				    			//c.getDefaultCursor();
				    			
				    			/*try        
				    			{
				    			    Thread.sleep(500);
				    			} 
				    			catch(InterruptedException ex) 
				    			{
				    			    Thread.currentThread().interrupt();
				    			}*/
				    			
				    			btnSchiessen.setBackground(Color.white);
				    			//repaint();
				    			//break;
					    	}
					    	else
					    	{
					    		if(wert == 1 || wert == 2)
					    		{
					    			gegner.feldAendern(posx, posy, wert+1);
					    			gegnerischepunkte-=1;
					    			if(wert == 2)
					    			{
					    				gegner.schiffVersenkenv2(posx, posy);
					    				//gegner.schiffVersenkenv2(posx, posy);	//zeigt bei versenktem schiff umliegende wasserfelder an
					    				
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
					    				
					    			//repaint();
					    			lblGegnerpunkte.setText("Gegnerpunkte: " + gegnerischepunkte);
					    			if(gegnerischepunkte <=0)
					    			{
					    				//spieler hat gewonnen
					    				JOptionPane.showMessageDialog(null, "Gewonnen!");
					    				System.exit(0);
					    			}
					    			//break;
					    		}
					    	}
					    }
					    
					}catch (IOException e) {
						e.printStackTrace();
					}
				//}
				zeichneFeldNeu();
				//update(this.getGraphics());
				//repaint();
				while(schiesse == false)
					waitforShot();
				
			}
		}
		/*else
		{
			
			while(true) //Warte auf schuss
			{
				try {
					String line = in.readLine();
				    System.out.println(line);
				    
				    if (line.contains("Schuss"))
				    {
				    	String[] arr = line.split(" ");
				    	String[] pos = arr[1].split(",");
				    	int positx = Integer.valueOf(pos[0]);
				    	int posity = Integer.valueOf(pos[1]);
				    	
				    	int wert = spieler.schiessen(positx, posity);
				    	
				    	
				    	try {
							out.write(String.format("%s%n", "Antwort " + wert));
						    out.flush();
						}catch (IOException e) {
							e.printStackTrace();
						}
				    	
				    	if(wert == 0)
				    	{
				    		schiesse = true;
				    		break;
				    	}
				    	else
				    	{
				    		eigenePunkte = spieler.getFeldpunkte();
			    			lblEigenePunkte.setText("eigene punkte: " + eigenePunkte);
			    			
			    			
			    			
			    			if(eigenePunkte == 0)
			    			{
			    				//spieler hat verloren
			    				JOptionPane.showMessageDialog(null, "Verloren!");
			    				System.exit(0);
			    			}
			    			
				    	}
				    	repaint();
				    	//break;
				    }
				    
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
		repaint();
	}
	
	private void zeichneFeldNeu()
	{
		repaint();
		revalidate();
		update(this.getGraphics());
	}
	
	public void waitforShot()
	{
		//while(true) //Warte auf schuss
		//{
			try {
				String line = in.readLine();
			    System.out.println(line);
			    
			    if (line.contains("Schuss"))
			    {
			    	String[] arr = line.split(" ");
			    	String[] pos = arr[1].split(",");
			    	int positx = Integer.valueOf(pos[0]);
			    	int posity = Integer.valueOf(pos[1]);
			    	
			    	int wert = spieler.schiessen(positx, posity);
			    	
			    	
			    	try {
						out.write(String.format("%s%n", "Antwort " + wert));
					    out.flush();
					}catch (IOException e) {
						e.printStackTrace();
					}
			    	
			    	if(wert == 0)
			    	{
			    		schiesse = true;
			    		JOptionPane.showMessageDialog(null, "Gegner hat geschossen, du bist an der Reihe!");
			    		//break;
			    	}
			    	else
			    	{
			    		eigenePunkte = spieler.getFeldpunkte();
		    			lblEigenePunkte.setText("eigene punkte: " + eigenePunkte);
		    			
		    			repaint();
		    			
		    			if(eigenePunkte == 0)
		    			{
		    				//spieler hat verloren
		    				JOptionPane.showMessageDialog(null, "Verloren!");
		    				System.exit(0);
		    			}
		    			
			    	}
			    	//repaint();
			    	zeichneFeldNeu();
			    	//break;
			    }
			    
			}catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}
	
	public void paint(Graphics g){ 
		super.paint(g);
		int feldgroesse = 400;			//breite des gezeichneten Feldes
		int xStart = 50;
		int xgStart = xStart+feldgroesse+50;
		int xgvStart = xgStart+feldgroesse+50;
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
		
		//gegnerische Sicht:
		
				for(int i=0; i<fgroesse; i++)					//j=posX;  i=posY
				{
					for(int j=0; j<fgroesse; j++)
					{
						g.setColor(Color.black);
						g.drawRect(xgvStart+(i*(feldgroesse/fgroesse)), yStart+(j*(feldgroesse/fgroesse)), feldgroesse/fgroesse, feldgroesse/fgroesse);
						switch(gegner2.getFeldInhalt(i, j))	//farbe bestimmen//spieler
						{
						case 0:g.setColor(Color.cyan);break;		//wasser
						case 1:g.setColor(Color.gray);break;		//schiff
						case 2:g.setColor(Color.red);break;			//getroffenes schiff
						case 3:g.setColor(Color.black);break;		//versenktes schiff
						case 4:g.setColor(Color.white);break;		//unbekannt
						}
						
						g.fillRect(xgvStart+(i*(feldgroesse/fgroesse))+1, yStart+(j*(feldgroesse/fgroesse))+1, feldgroesse/fgroesse-1, feldgroesse/fgroesse-1);
						
					}
						
				}
    } 
}
