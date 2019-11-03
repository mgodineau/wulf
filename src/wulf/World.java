package wulf;

import java.awt.Color;
import java.util.ArrayList;

public class World {

	// représentation du monde
	private Wall[][] map;
	private int width;
	private int height;

	// gestion des couleurs
	private Color groundCol;
	private Color roofCol;
	
	//liste de objets de jeu
	private ArrayList<GameObject> gameObjectLst;
	
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
	
	
	//ajout d'objets de jeu
	public void addObj ( GameObject obj ) {
		gameObjectLst.add(obj);
		obj.setWorld(this);
	}
	
	public ArrayList<GameObject> getObjectLst () {
		return gameObjectLst;
	}
	
	public void setInputManager(InputManager inputMng) {
		for ( GameObject obj : gameObjectLst ) {
			obj.setInput(inputMng);
		}
	}
	
	
	public Collision raycast( double xStart, double yStart , double angle) {
		return raycast( xStart, yStart, Math.cos(angle), Math.sin(angle) );
	}
	
	// lance un rayon de (x,y) avec la direction angle (en radian), et retourne la
	// distance du mur le plus proche, positive ou négative en fonction de si la collision est sur un mur vertical ou horizontal
	public Collision raycast(double xStart, double yStart, double cos, double sin) {
		
		//indicateur de verticalité
		boolean verticalCol = false;
		
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
			if ( SimpleMath.sqrDist(interH[0] - xStart, interH[1] - yStart) <= SimpleMath.sqrDist(interV[0] - xStart, interV[1] - yStart)) {
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
		
		double dist = SimpleMath.dist(x - xStart, y - yStart);
		double[] normal = new double[2];
		if ( !verticalCol ) {
			//normal vers la droite ou la gauche
			normal[0] = cos>0 ? -1 : 1;
		} else {
			//normal vers le haut ou le bas
			normal[1] = sin>0 ? -1 : 1;
		}
		double[] pos = { x , y };
		
		return new Collision(dist, map[u][v] , pos, normal);
	}
	
	
	
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
	public boolean pointInMap(double x, double y) {
		return (x >= 0 && x < width) && (y >= 0 && y < height);
	}

	public boolean isWall(int x, int y) {
		return !pointInMap(x, y) || map[x][y] != Wall.VIDE;
	}

	/*private double sqrDist(double x, double y) {
		return x * x + y * y;
	}

	private double dist(double x, double y) {
		return Math.sqrt(sqrDist(x, y));
	}*/
	
	
	//fonction update qui met � jour tous les gameObject
	//deltaTime en secondes
	public void update ( double deltaTime ) {
		for ( GameObject obj : gameObjectLst ) {
			obj.update(deltaTime);
		}
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

	//constructeur principal
	public World(Wall[][] map, Color groundCol, Color roofCol) {
		
		//initialisation des couleurs
		this.groundCol = groundCol;
		this.roofCol = roofCol;
		
		//initialisation des objets de jeu
		this.gameObjectLst = new ArrayList<GameObject>();
		
		//initialisaton de la carte
		this.map = map;
		height = map.length;

		// vérification de la taille des lignes dans la carte
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