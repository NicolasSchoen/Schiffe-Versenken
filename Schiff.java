
public class Schiff {						//evtl. ist klasse schiff unnötig, da man schiffe direkt im feld schreiben kann
	int groesse;							//logik dazu dann in der klasse spiel
	int posX,posY;
	String name;
	int richtung;							//0=fehler, 1=oben, 2=rechts, 3=unten, 4=links
	
	public Schiff(int g, int x, int y, int r){
		if(g<2 || g>5)
		{
			System.out.println("Bitte ein Schiff der Groesse zwischen 2 und 5 angeben!");
			return;
		}
		switch(g)
		{
		case 2: this.name = "Schnellboot";
				break;
		case 3: this.name = "Kreuzer";
				break;
		case 4: this.name = "Schlachtschiff";
				break;
		case 5: this.name = "Flugzeugtraeger";
				break;
		}
			
		this.groesse = g;
		this.posX = x;
		this.posY = y;
		this.richtung = r;
	}

}
