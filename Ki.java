import java.util.Random;

public class Ki {

	public static void main(String[] args) {

		int[][] feld = new int[10][10];
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				feld[x][y] = 0;
			}

		}
		schiffesetzen(feld, 5, 10);	
		

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(feld[i][j] + "  ");
			}
			System.out.println();
		}

	}

	public static void schiffesetzen(int[][] feld, int schiffgroesse, int feldgroesse) {
		Random rand = new Random();
		int random4 = rand.nextInt(feldgroesse);

		Random rand1 = new Random();
		int random3 = rand1.nextInt(feldgroesse);

		Random rand2 = new Random();
		int random5 = rand2.nextInt(2);

		

		int erfüllt = 0;
		
		int erneut = 1;
	

		if (random5 == 1) { // nach rechts addieren
			for (int i = 0; i < erneut; i++)
				if (random4 + schiffgroesse <= feldgroesse && feld[random3][random4] == 0) {
					if (random3 == 0) {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3][random4 + w] == 0 && feld[random3 + 1][random4 + w] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}

						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3][random4 + z] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}

					} else if (random3 == feldgroesse - 1) {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3][random4 + w] == 0 && feld[random3 - 1][random4 + w] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}

						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3][random4 + z] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}
					}

					else {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3][random4 + w] == 0 && feld[random3 + 1][random4 + w] == 0
									&& feld[random3 - 1][random4 + w] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}
						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3][random4 + z] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}
					}
				} else {
					random3 = (int) ((Math.random() * feldgroesse));
					random4 = (int) ((Math.random() * feldgroesse));
					erneut++;
				}
		} else {// nach unten addieren
			for (int i = 0; i < erneut; i++) {
				if (random3 + schiffgroesse <= feldgroesse && feld[random3][random4] == 0) {
					
					if (random4 == 0) {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3 + w][random4 + 1] == 0 && feld[random3 + w][random4] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}

						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3 + z][random4] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}
					} else if (random4 == feldgroesse - 1) {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3+w][random4 ] == 0 && feld[random3][random4 - 1] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}

						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3 + z][random4] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}
					} else {
						for (int w = 0; w < schiffgroesse; w++) {
							if (feld[random3 + w][random4] == 0 && feld[random3 + w][random4 - 1] == 0
									&& feld[random3 + w][random4 + 1] == 0) {
								erfüllt++;
							} else {
								w = schiffgroesse;

							}
						}
						if (erfüllt == schiffgroesse) {
							for (int z = 0; z < schiffgroesse; z++) {
								feld[random3 + z][random4] = 1;

							}
						}

						else {
							random3 = (int) ((Math.random() * feldgroesse));
							random4 = (int) ((Math.random() * feldgroesse));
							erneut++;
						}
					}
				} else {
					random3 = (int) ((Math.random() * feldgroesse));
					random4 = (int) ((Math.random() * feldgroesse));
					erneut++;
				}
			}
		}
		System.out.println(random3 + " " + random4);

	}

	public static void schiesen(int[][] feld, int g) {
		Random rand = new Random();
		int random1 = rand.nextInt(g);

		Random rand1 = new Random();
		int random2 = rand1.nextInt(g);

		if (feld[random2][random1] == 0) {
			feld[random2][random1] = 3;
		} else if (feld[random2][random1] == 1) {
			feld[random2][random1] = 2;

		} else if (feld[random2][random1] == 2) {
			schiesen(feld, g);
		} else if (feld[random2][random1] == 3) {
			schiesen(feld, g);
		}

	}

}
