package wulf;

import java.awt.Color;

public class World {

	// représentation du monde
	private Wall[][] map;
	private int width;
	private int height;

	// gestion des couleurs
	private Color groundCol;
	private Color roofCol;

	public Wall getWall(int x, int y) {
		return map[x][y];
	}
	
	
	public Color getGroundCol() {
		return groundCol;
	}

	public void setGroundCol(Color groundCol) {
		this.groundCol = groundCol;
	}

	public Color getRoofCol() {
		return roofCol;
	}

	public void setRoofCol(Color roofCol) {
		this.roofCol = roofCol;
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth () {
		return width;
	}
	
	public Collision raycastCol ( double xStart, double yStart, double angle ) {
		double dist = raycastDist(xStart, yStart, angle);
		double[] normal = new double[2];
		if ( dist <= 0 ) {
			//normal vers la droite ou la gauche
			normal[0] = Math.cos(angle)>0 ? -1 : 1;
		} else {
			//normal vers le haut ou le bas
			normal[1] = Math.sin(angle)>0 ? -1 : 1;
		}
		dist = Math.abs(dist);
		double[] pos = { xStart + Math.cos(angle) * dist, yStart + Math.sin(angle) * dist };
		
		return new Collision(dist, Wall.MurBleu, pos, normal);
	}
	
	// lance un rayon de (x,y) avec la direction angle (en radian), et retourne la
	// distance du mur le plus proche, positive ou négative en fonction de si la collision est sur un mur vertical ou horizontal
	public double raycastDist(double xStart, double yStart, double angle) {
		
		//indicateur de verticalité
		boolean verticalCol = false;
		
		// cos et sin de l'angle
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		// coordonn�es courrantes
		double x = xStart;
		double y = yStart;

		// coordonn�es du mur � tester
		int u = (int) x;
		int v = (int) y;

		double[] interH;
		double[] interV;

		while (!isWall(u, v)) {
			int lineIdH = (int) (x + ((cos > 0) ? 1 : 0));
			if (lineIdH == x) {
				lineIdH--;
			}
			int lineIdV = (int) (y + ((sin > 0) ? 1 : 0));
			if (lineIdV == y) {
				lineIdV--;
			}
			interH = intersectLine(true, lineIdH, x, y, cos, sin);
			interV = intersectLine(false, lineIdV, x, y, cos, sin);
			if (sqrDist(interH[0] - xStart, interH[1] - yStart) <= sqrDist(interV[0] - xStart, interV[1] - yStart)) {
				// intersection avec une ligne horizontale
				verticalCol =false;
				x = interH[0];
				y = interH[1];

				u = ((int) x) + (cos > 0 ? 0 : -1);
				v = (int) y;
			} else {
				// intersection avec une ligne verticale
				verticalCol =true;
				x = interV[0];
				y = interV[1];

				u = (int) x;
				v = ((int) y) + (sin > 0 ? 0 : -1);
			}

		}

		return dist(x - xStart, y - yStart) * (verticalCol ? 1 : -1);

	}

	/*
	 * private double[] intersectLine(boolean interWithH, int lineId, double x,
	 * double y, double angle) { return intersectLine(interWithH, lineId, x, y,
	 * Math.cos(angle), Math.sin(angle)); }
	 */
	// calcul l'intersection avec une ligne de la grille du monde, verticale ou
	// horizontale
	private double[] intersectLine(boolean interWithH, int lineId, double x, double y, double cos, double sin) {

		if (interWithH) {
			double deltaX = lineId - x;
			x = lineId;
			if (cos != 0) {
				y += sin * deltaX / cos;
			}
		} else {
			double deltaY = lineId - y;
			y = lineId;
			if (sin != 0) {
				x += cos * deltaY / sin;
			}
		}

		double[] out = { x, y };
		return out;
	}

	// d�termine si un point est dans la carte
	private boolean pointInMap(double x, double y) {
		return (x >= 0 && x < width) && (y >= 0 && y < height);
	}

	private boolean isWall(int x, int y) {
		return !pointInMap(x, y) || map[x][y] == Wall.MurBleu;
	}

	/*
	 * private double sqrDist(double[] coord) { try { return sqrDist(coord[0],
	 * coord[1]); } catch (IndexOutOfBoundsException e) {
	 * System.out.println(e.getMessage()); return -1; } }
	 */

	private double sqrDist(double x, double y) {
		return x * x + y * y;
	}

	private double dist(double x, double y) {
		return Math.sqrt(sqrDist(x, y));
	}

	public World() {
		this(0, 0);
	}

	public World(int height, int width) {
		this(new Wall[Math.max(0, height)][Math.max(0, width)]);
	}
	
	public World(Wall[][] map) {
		this(map, Color.DARK_GRAY, Color.gray);
	}

	public World(Wall[][] map, Color groundCol, Color roofCol) {
		
		//initialisation des couleurs
		this.groundCol = groundCol;
		this.roofCol = roofCol;
		
		//initialisaton de la carte
		this.map = map;
		height = map.length;

		// vérification de la taille des lignes
		if (this.map.length > 0) {
			width = this.map[0].length;
			for (int i = 0; i < this.map.length; i++) {
				if (this.map[i].length != width) {
					this.map[i] = new Wall[width];
					for (int j = 0; j < width; j++) {
						this.map[i][j] = (j < width) ? map[i][j] : Wall.VIDE;
					}
				}
			}
		} else {
			width = 0;
		}

	}

	

}