package wulf;

public class World {

	private Wall[][] map;
	private int width;
	private int height;

	public Wall getWall(int x, int y) {
		return map[x][y];
	}

	// lance un rayon de (x,y) avec la direction angle (en radian), et retourne la
	// distance du mur le plus proche
	public double raycast(double xStart, double yStart, double angle) {

		// cos et sin de l'angle
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		// coordonnées courrantes
		double x = xStart;
		double y = yStart;

		// coordonnées du mur à tester
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
			if (sqrDist(interH) <= sqrDist(interV)) {
				// intersection avec une ligne horizontale
				x = interH[0];
				y = interH[1];

				u = (int) (x + (cos > 0 ? 1 : 0));
				v = (int) y;
			} else {
				// intersection avec une ligne verticale
				x = interV[0];
				y = interV[1];

				u = (int) x;
				v = (int) y + (sin > 0 ? 1 : 0);
			}

		}

		return dist(x - xStart, y - yStart);

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

	// détermine si un point est dans la carte
	private boolean pointInMap(double x, double y) {
		return (x >= 0 && x < width) && (y >= 0 && y < height);
	}

	private boolean isWall(int x, int y) {
		return !pointInMap(x, y) || map[x][y] == Wall.MUR;
	}

	private double sqrDist(double[] coord) {
		try {
			return sqrDist(coord[0], coord[1]);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

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
		map = new Wall[Math.max(0, height)][Math.max(0, width)];
	}

	public World(Wall[][] map) {
		// on met la map corrigÃ©e dans la variable
		this.map = map;
		height = map.length;

		// vÃ©rification de la taille des lignes
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