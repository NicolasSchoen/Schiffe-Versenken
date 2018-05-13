
public class Feld {
	
	//Belegung: 0:Wasser,   1:Schiff,   2:getroffenes Schiff,    3:versenktes Schiff		4:unbekannt(bei gegner)
	//--------------------------------------------------------------------------------
	
	
	private int x,y;
	private int feld[][];
	private int schiffsfelder, belegteSchiffsfelder;
	
	public Feld(int g)
	{
		if(g >= 5 && g<=30)
		{
			this.x = g;
			this.y = g;
			feld = new int[g][g];
			schiffsfelder = (int) (g*g*0.3); 
			belegteSchiffsfelder = schiffsfelder;
			feldInitialisieren();
		}
		else
		{
			System.out.println("Bitte Werte zwischen 5 und 30 eingeben!");
		}
		
	}
	
	public void feldInitialisieren()
	{
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<x; j++)
			{
				feld[i][j] = 0;										//Feld mit Wasser initialisieren
			}
		}
	}
	public void gegnerInitialisieren()
	{
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<x; j++)
			{
				feld[i][j] = 4;										//Feld mit Unbekannt initialisieren
			}
		}
	}
	
	public int getGroesse() {
		return x;
	}
	
	public void feldAusgeben()
	{
		System.out.println("Feld: ");
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<x; j++)
			{
				System.out.print("[" + feld[j][i] + "]");	
			}
			System.out.println("");
		}
		System.out.println("Schiffsfelder: " + schiffsfelder + " ,davon belegt: " + (schiffsfelder-belegteSchiffsfelder));
	}
	
	public int getFeldInhalt(int x, int y) 								//gibt Inhalt an feld x y zurueck
	{
		if(x > this.x || y > this.y || x < 0 || y < 0)
			return -1;
		return feld[x][y];
	}
	
	public boolean schiffPlatzieren(int x, int y, int g, int r)			//platziere Schiff der laenge g und richtung r an x,y
	{
		if(x >= 0 && x < this.x && y >= 0 && y < this.y)//pos x,y in feld
		{
			if(r>=0 && r<4)//richtung gueltig
			{
				switch(r)
				{
				case 0:	//hoch
				{
					if(y-g+1 >= 0)//schiff liegt in feld
					{
						for(int z=0; z<g; z++)
						{
							if(getFeldInhalt(x, y-z) != 0)//feld bereits belegt
							{
								return false;
							}
						}
						///////pruefen ob Felder drumherum leer
						if(x-1 >= 0)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x-1, y-l) != 0)
									return false;
							}
						}
						if(x+1 < this.x)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x+1, y-l) != 0)
									return false;
							}
						}
						
						if(y+1 < this.x)
						{
							if(getFeldInhalt(x, y+1) != 0)
								return false;
							if(x-1 >= 0)
								if(getFeldInhalt(x-1, y+1) != 0)
									return false;
							if(x+1 < this.x)
								if(getFeldInhalt(x+1, y+1) != 0)
									return false;
						}
						if(y-g >= 0)
						{
							if(getFeldInhalt(x, y-g) != 0)
								return false;
							if(x-1 >= 0)
								if(getFeldInhalt(x-1, y-g) != 0)
									return false;
							if(x+1 < this.x)
								if(getFeldInhalt(x+1, y-g) != 0)
									return false;
						}
						//////////////////////////////////////////////
						for(int z=0; z<g; z++)
						{
							feldAendern(x, y-z, 1);//Schiff platzieren
						}
						return true;
					}
					return false;
				}
				case 1:	//rechts
				{
					if(x+g-1 < this.x)//schiff liegt in feld
					{
						for(int z=0; z<g; z++)
						{
							if(getFeldInhalt(x+z, y) != 0)//feld bereits belegt
							{
								return false;
							}
						}
						///////pruefen ob Felder drumherum leer
						if(y-1 >= 0)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x+l, y-1) != 0)
									return false;
							}
						}
						if(y+1 < this.x)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x+l, y+1) != 0)
									return false;
							}
						}
						
						if(x+g < this.x)
						{
							if(getFeldInhalt(x+g, y) != 0)
								return false;
							if(y-1 >= 0)
								if(getFeldInhalt(x+g, y-1) != 0)
									return false;
							if(y+1 < this.x)
								if(getFeldInhalt(x+g, y+1) != 0)
									return false;
						}
						if(x-1 >= 0)
						{
							if(getFeldInhalt(x-1, y) != 0)
								return false;
							if(y-1 >= 0)
								if(getFeldInhalt(x-1, y-1) != 0)
									return false;
							if(y+1 < this.x)
								if(getFeldInhalt(x-1, y+1) != 0)
									return false;
						}
						//////////////////////////////////////////////
						for(int z=0; z<g; z++)
						{
							feldAendern(x+z, y, 1);//Schiff platzieren
						}
						return true;
					}
					return false;
				}
				case 2:	//runter
				{
					if(y+g-1 < this.x)//schiff liegt in feld
					{
						for(int z=0; z<g; z++)
						{
							if(getFeldInhalt(x, y+z) != 0)//feld bereits belegt
							{
								return false;
							}
						}
						///////pruefen ob Felder drumherum leer
						if(x-1 >= 0)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x-1, y+l) != 0)
									return false;
							}
						}
						if(x+1 < this.x)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x+1, y+l) != 0)
									return false;
							}
						}
						
						if(y+g < this.x)
						{
							if(getFeldInhalt(x, y+g) != 0)
								return false;
							if(x-1 >= 0)
								if(getFeldInhalt(x-1, y+g) != 0)
									return false;
							if(x+1 < this.x)
								if(getFeldInhalt(x+1, y+g) != 0)
									return false;
						}
						if(y-1 >= 0)
						{
							if(getFeldInhalt(x, y-1) != 0)
								return false;
							if(x-1 >= 0)
								if(getFeldInhalt(x-1, y-1) != 0)
									return false;
							if(x+1 < this.x)
								if(getFeldInhalt(x+1, y-1) != 0)
									return false;
						}
						//////////////////////////////////////////////
						for(int z=0; z<g; z++)
						{
							feldAendern(x, y+z, 1);//Schiff platzieren
						}
						return true;
					}
					return false;
				}
				case 3:	//links
				{
					if(x-g+1 >= 0)//schiff liegt in feld
					{
						for(int z=0; z<g; z++)
						{
							if(getFeldInhalt(x-z, y) != 0)//feld bereits belegt
							{
								return false;
							}
						}
						/////// pruefen ob Felder drumherum leer
						if(y-1 >= 0)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x-l, y-1) != 0)
									return false;
							}
						}
						if(y+1 < this.x)
						{
							for(int l=0; l<g; l++)
							{
								if(getFeldInhalt(x-l, y+1) != 0)
									return false;
							}
						}
						
						if(x+1 < this.x)
						{
							if(getFeldInhalt(x+1, y) != 0)
								return false;
							if(y-1 >= 0)
								if(getFeldInhalt(x+1, y-1) != 0)
									return false;
							if(y+1 < this.x)
								if(getFeldInhalt(x+1, y+1) != 0)
									return false;
						}
						if(x-g >= 0)
						{
							if(getFeldInhalt(x-g, y) != 0)
								return false;
							if(y-1 >= 0)
								if(getFeldInhalt(x-g, y-1) != 0)
									return false;
							if(y+1 < this.x)
								if(getFeldInhalt(x-g, y+1) != 0)
									return false;
						}
						//////////////////////////////////////////////
						for(int z=0; z<g; z++)
						{
							feldAendern(x-z, y, 1);//Schiff platzieren
						}
						return true;
					}
					return false;
				}
				}
			}
			
		}
		return false;
	}
	
	public boolean feldAendern(int x, int y, int wert)					//Aendere feldwert an pos x y zu wert
	{
		if(x >= 0 && x < this.x && y >= 0 && y < this.y)					//pruefe ob pos in feld
		{
			if(wert >= 0 && wert <= 4)									//pruefe ob wert gueltig
			{
				feld[x][y] = wert;
				if(wert == 2)
					belegteSchiffsfelder--;
				//System.out.println("Noch " + belegteSchiffsfelder + " Schiffspunkte verfuegbar");
				return true;
			}
			System.out.println("Feldaendern fehlgeschlagen");
		}
		
		return false; 													//fehler
	}
	
	public boolean istSchiff(int x, int y)								//prueft ob an pos schiff ist
	{
		if(feld[x][y] == 1 || feld[x][y] == 2)
			return true;
		return false;
	}
	public int schiessen(int x, int y)
	{
		if(x>= 0 && x < this.x && y>= 0 && y< this.x)	//koordinaten in feld
		{
			if(this.getFeldInhalt(x, y) == 0)
				return 0;
			if(this.getFeldInhalt(x, y) == 1)
				this.feldAendern(x, y, 2);
				//pruefen ob schiff versenkt, dann return 3
				if(schiffVersenkt(x,y))
				{
					schiffVersenken(x, y);
					return 3;
				}
					
				return 2;
		}
		return -1;
	}
	
	private boolean schiffVersenkt(int x, int y)
	{
		for(int i=1; i<5; i++)//laufe nach rechts			//noch pruefen
		{
			if(x+i >= getGroesse() || getFeldInhalt(x+i, y) == 0)	//wasser
			{
				break;
			}
			if(x+i < getGroesse() && getFeldInhalt(x+i, y) == 1)
				return false;
		}
		for(int i=1; i<5; i++)//laufe nach links
		{
			if(x-i < 0 || getFeldInhalt(x-i, y) == 0)	//wasser
			{
				break;
			}
			if(x-i >= 0 && getFeldInhalt(x-i, y) == 1)
				return false;
		}
		for(int i=1; i<5; i++)//laufe nach oben
		{
			if(y-i < 0 || getFeldInhalt(x, y-i) == 0)	//wasser
			{
				break;
			}
			if(y-i >= 0 && getFeldInhalt(x, y-i) == 1)
				return false;
		}
		for(int i=1; i<5; i++)//laufe nach unten
		{
			if(y+i >= getGroesse() || getFeldInhalt(x, y+i) == 0)	//wasser
			{
				break;
			}
			if(y+i < getGroesse() && getFeldInhalt(x, y+i) == 1)
				return false;
		}
		return true;
	}
	public void schiffVersenken(int x, int y)						//Setzt getroffene felder rekursiv auf versenkt
	{
		feldAendern(x, y, 3);
		if(y-1 >= 0 && getFeldInhalt(x, y-1) == 2)
			schiffVersenken(x,y-1);
		if(x+1 < getGroesse() && getFeldInhalt(x+1, y) == 2)
			schiffVersenken(x+1, y);
		if(y+1 < getGroesse() && getFeldInhalt(x, y+1) == 2)
			schiffVersenken(x, y+1);
		if(x-1 >= 0 && getFeldInhalt(x-1, y) == 2)
			schiffVersenken(x-1, y);
	}
	public boolean bereitsBeschossen(int x, int y)						//prueft ob position schon beschossen wurde
	{
		if(x>=0 && x<this.x && y>= 0 && y<this.x)
			if(feld[x][y] == 0 || feld[x][y] == 2 || feld[x][y] == 3)
				return true;
		return false;
	}
	public int getFeldpunkte()
	{
		return belegteSchiffsfelder;
	}
}
