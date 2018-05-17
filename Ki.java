import java.util.Random;

public class Ki {
	
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
		Random rand = new Random();
		int posx = rand.nextInt(f2.getGroesse());
		int posy = rand.nextInt(f2.getGroesse());
		int punkte = f2.getFeldpunkte();
		while(punkte > 0)
		{
			posx = rand.nextInt(f2.getGroesse());
			posy = rand.nextInt(f2.getGroesse());//posx posy an letztem treffer anpassen
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
					f2.feldAendern(posx, posy, wert+1);
				}
					
			}
		}
		return true;
	}
}
