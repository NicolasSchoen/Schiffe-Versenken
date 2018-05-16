import java.io.Serializable;

public class Spielstand implements Serializable {
	public Feld spieler1;
	public Feld spieler2;
	public Feld gegner1;
	public Feld gegner2;
	public int modus;
	public int anreihe;
	
	public Spielstand(Feld s1, Feld s2, Feld g1, Feld g2, int m, int anreihe){
		this.spieler1 = s1;
		this.spieler2 = s2;
		this.gegner1 = g1;
		this.gegner2 = g2;
		this.modus = m;
		this.anreihe = anreihe;
	}

	
}
