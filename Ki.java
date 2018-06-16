import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ki {
	
	static int punkte = 0;
	static boolean oben=false;
	static boolean rechts=false;
	static boolean unten=false;
	static boolean links=false;
	
	static boolean trefa=false;
	static int trefx;
	static int trefy;
		
	static int tref2x;
	static int tref2y;
	
	public static void setztePunkte(int p) {
		punkte = p;
	}
	
	public static int getPunkte() {
		return punkte;
	}

	public static boolean alleSchiffeSetzen(Feld f)
	{
		int fpunkte = f.getFeldpunkte();
		Random rand = new Random();
		int schiffg = rand.nextInt(4) + 2;
		int posx = rand.nextInt(f.getGroesse());
		int posy = rand.nextInt(f.getGroesse());
		int richtung = rand.nextInt(4);
		int fehler = 0;
		
		while(fpunkte > 0)
		{
			/*for(int i=0; i<10; i++)
			{
				
			}*/
			schiffg = rand.nextInt(4) + 2;
			posx = rand.nextInt(f.getGroesse());
			posy = rand.nextInt(f.getGroesse());
			richtung = rand.nextInt(4);
			
			if(fpunkte == 3)
				schiffg = 3;
			if(fpunkte == 2)
				schiffg = 2;
			if(fpunkte == 4)
				schiffg = (rand.nextInt(2) + 1) * 2;
			if(fpunkte == 5)
				if(schiffg == 4)
				{
					schiffg = (rand.nextInt(3) + 2);
					if(schiffg == 4)
						schiffg = 5;
				}
					
			if(fpunkte == 6 && schiffg == 5)
				schiffg = (rand.nextInt(3) + 2);
				
			if(!f.schiffPlatzieren(posx, posy, schiffg, richtung))
				fehler++;
			else
			{
				fehler=0;
				fpunkte-=schiffg;
			}
				
			
			if(fehler > 10000)			//soll verhindern, dass ki zu oft durchlaeuft
			{
				f.feldInitialisieren();
				fpunkte = f.getFeldpunkte();
				//return false;
				System.out.println("Viele Fehler!");
			}
			
			
		}
		if(fpunkte == 0)
			return true;
		return false;
	}
	
	public static boolean schiesse(Feld s, Feld f2)
	{
		
		
		
		
		System.out.println("trefa = " + trefa);
		
		Random rand = new Random();
		int posx = rand.nextInt(f2.getGroesse());
		int posy = rand.nextInt(f2.getGroesse());
		if(punkte < (int)(f2.getGroesse() * f2.getGroesse() * 0.3))
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
					if(f2.inFeld(tref2x, tref2y-1) && !f2.bereitsBeschossen(tref2x, tref2y-1))
					{
						tref2y--;
						int wert = s.schiessen(tref2x, tref2y);
						if(wert == 1)
						{
							punkte++;
							rechts = links = false;
							System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
							f2.feldAendern(tref2x, tref2y, wert+1);
							return true;
						}
						if(wert == 2)
						{
							punkte++;
							System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
							f2.feldAendern(tref2x, tref2y, wert+1);
							f2.schiffVersenkenv2(tref2x, tref2y);
							trefa = false;
							return true;
						}
						if(wert == 0)
						{
							f2.feldAendern(tref2x, tref2y, wert);
							tref2x = trefx;
							tref2y = trefy;
							oben = false;
							return false;
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
					if(f2.inFeld(tref2x+1, tref2y) && !f2.bereitsBeschossen(tref2x+1, tref2y))
					{
						tref2x++;
						int wert = s.schiessen(tref2x, tref2y);
						if(wert == 1)
						{
							punkte++;
							oben = unten = false;
							System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
							f2.feldAendern(tref2x, tref2y, wert+1);
							return true;
						}
						if(wert == 2)
						{
							punkte++;
							System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
							f2.feldAendern(tref2x, tref2y, wert+1);
							f2.schiffVersenkenv2(tref2x, tref2y);
							trefa = false;
							return true;
						}
						if(wert == 0)
						{
							f2.feldAendern(tref2x, tref2y, wert);
							tref2x = trefx;
							tref2y = trefy;
							rechts = false;
							return false;
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
					if(f2.inFeld(tref2x, tref2y+1) && !f2.bereitsBeschossen(tref2x, tref2y+1))
					{
						tref2y++;
						int wert = s.schiessen(tref2x, tref2y);
						if(wert == 1)
						{
							punkte++;
							rechts = links = false;
							System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
							f2.feldAendern(tref2x, tref2y, wert+1);
							return true;
						}
						if(wert == 2)
						{
							punkte++;
							System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
							f2.feldAendern(tref2x, tref2y, wert+1);
							f2.schiffVersenkenv2(tref2x, tref2y);
							trefa = false;
							return true;
						}
						if(wert == 0)
						{
							f2.feldAendern(tref2x, tref2y, wert);
							tref2x = trefx;
							tref2y = trefy;
							unten = false;
							return false;
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
					if(f2.inFeld(tref2x-1, tref2y) && !f2.bereitsBeschossen(tref2x-1, tref2y))
					{
						tref2x--;
						int wert = s.schiessen(tref2x, tref2y);
						if(wert == 1)
						{
							punkte++;
							oben = unten = false;
							System.out.println("KI fPunkte: " + punkte + "Schiff getroffen");
							f2.feldAendern(tref2x, tref2y, wert+1);
							return true;
							//continue;
						}
						if(wert == 2)
						{
							punkte++;
							System.out.println("KI fPunkte: " + punkte + "Schiff versenkt");
							f2.feldAendern(tref2x, tref2y, wert+1);
							f2.schiffVersenkenv2(tref2x, tref2y);
							trefa = false;
							return true;
						}
						if(wert == 0)
						{
							f2.feldAendern(tref2x, tref2y, wert);
							tref2x = trefx;
							tref2y = trefy;
							links = false;
							return false;
						}	
					}
					else
					{
						tref2x = trefx;
						tref2y = trefy;
						links = false;
					}
				}
				return false;
			}
			
			
			
			
			
			
			
			
			
			
			
			//Wenn nicht getroffen
			
			posx = rand.nextInt(f2.getGroesse());
			posy = rand.nextInt(f2.getGroesse());//posx posy an letztem treffer anpassen
			
			while(f2.bereitsBeschossen(posx, posy))
			{
				posx = rand.nextInt(f2.getGroesse());
				posy = rand.nextInt(f2.getGroesse());//posx posy an letztem treffer anpassen
			}
			if(!f2.bereitsBeschossen(posx, posy))
			{
				int wert = s.schiessen(posx, posy);
				if(wert == 0)
				{
					f2.feldAendern(posx, posy, wert);
					return false;
				}
				else
				{
					punkte++;
					System.out.println("KI fPunkte: " + punkte);
					f2.feldAendern(posx, posy, wert+1);
					trefa = true;
					trefx = tref2x = posx;
					trefy = tref2y= posy;
					oben = rechts = unten = links = true;
					if(wert == 2)
						f2.schiffVersenkenv2(posx, posy);
					return true;
				}
					
			}
		}
		return false;
		
	}
}
