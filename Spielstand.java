import java.io.Serializable;

public class Spielstand implements Serializable {
	public Feld spieler1;
	public Feld spieler2;
	public Feld gegner1;
	public Feld gegner2;
	public int modus;
	public int eigenepunkte;
	public int gegnerischepunkte;
	public boolean anreihe;
	
	public Spielstand(Feld s1, Feld s2, Feld g1, Feld g2, int m, boolean anreihe, int ep, int gp){
		this.spieler1 = s1;
		this.spieler2 = s2;
		this.gegner1 = g1;
		this.gegner2 = g2;
		this.modus = m;
		this.anreihe = anreihe;
		this.eigenepunkte = ep;
		this.gegnerischepunkte = gp;
	}
	
	public Spielstand(String s) {
		String[] arr = s.split(" ");
    	modus = Integer.parseInt(arr[0]);
    	anreihe = Boolean.parseBoolean(arr[1]);
    	int fgr = Integer.parseInt(arr[2]);
    	eigenepunkte = Integer.parseInt(arr[7]);
    	gegnerischepunkte = Integer.parseInt(arr[8]);
    	
    	String[] s1 = arr[3].split(",");
    	String[] s2 = arr[4].split(",");
    	String[] g1 = arr[5].split(",");
    	String[] g2 = arr[6].split(",");
    	
    	spieler1 = new Feld(fgr);
    	spieler2 = new Feld(fgr);
    	gegner1 = new Feld(fgr);
    	gegner2 = new Feld(fgr);
    	
    	int z=0;
    	for(int x=0; x<fgr; x++)
    		for(int y=0; y<fgr; y++) {
    			spieler1.feldAendern(x, y, Integer.parseInt(s1[z++]));
    		}
    	
    	z=0;
    	for(int x=0; x<fgr; x++)
    		for(int y=0; y<fgr; y++) {
    			spieler2.feldAendern(x, y, Integer.parseInt(s2[z++]));
    		}
    	
    	z=0;
    	for(int x=0; x<fgr; x++)
    		for(int y=0; y<fgr; y++) {
    			gegner1.feldAendern(x, y, Integer.parseInt(g1[z++]));
    		}
    	
    	z=0;
    	for(int x=0; x<fgr; x++)
    		for(int y=0; y<fgr; y++) {
    			gegner2.feldAendern(x, y, Integer.parseInt(g2[z++]));
    		}
	}
	
	public String toString() {
		String ausgabe = "";
		
		ausgabe = modus + " " + anreihe + " " + spieler1.getGroesse() + " ";
		for(int x=0; x<spieler1.getGroesse(); x++) {
			for(int y=0; y<spieler1.getGroesse(); y++) {
				ausgabe = ausgabe + spieler1.getFeldInhalt(x, y);
				if(!(x == spieler1.getGroesse()-1 && y == spieler1.getGroesse()-1))
					ausgabe = ausgabe + ",";
			}
		}
		ausgabe = ausgabe + " ";
		for(int x=0; x<spieler2.getGroesse(); x++) {
			for(int y=0; y<spieler2.getGroesse(); y++) {
				ausgabe = ausgabe + spieler2.getFeldInhalt(x, y);
				if(!(x == spieler2.getGroesse()-1 && y == spieler2.getGroesse()-1))
					ausgabe = ausgabe + ",";
			}
		}
		ausgabe = ausgabe + " ";
		for(int x=0; x<gegner1.getGroesse(); x++) {
			for(int y=0; y<gegner1.getGroesse(); y++) {
				ausgabe = ausgabe + gegner1.getFeldInhalt(x, y);
				if(!(x == gegner1.getGroesse()-1 && y == gegner1.getGroesse()-1))
					ausgabe = ausgabe + ",";
			}
		}
		ausgabe = ausgabe + " ";
		for(int x=0; x<gegner2.getGroesse(); x++) {
			for(int y=0; y<gegner2.getGroesse(); y++) {
				ausgabe = ausgabe + gegner2.getFeldInhalt(x, y);
				if(!(x == gegner2.getGroesse()-1 && y == gegner2.getGroesse()-1))
					ausgabe = ausgabe + ",";
			}
		}
		ausgabe = ausgabe + " " + eigenepunkte + " " + gegnerischepunkte + " ";
		
		return ausgabe;
	}

	
}
