package wulf;

public class World {

	private int[][] map;

	public int getWall(int x, int y) {
		return map[x][y];
	}

	
	
	//lance un rayon de (x,y) avec la direction angle (en radian), et retourne la distance du mur le plus proche
	public double raycast ( int x , int y , double angle ) {
		
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		
	}
	
	
	
	
	
	
	
	public World() {
		this(0, 0);
	}

	public World(int height, int width) {
		map = new int[Math.max(0, height)][Math.max(0, width)];
	}

	public World(int[][] map) {
		// on met la map corrigée dans la variable

		// vérification de la taille des lignes
		if (this.map.length > 0) {
			int width = this.map[0].length;
			for (int i = 0; i < this.map.length; i++) {
				if (this.map[i].length != width) {
					this.map[i] = new int[width];
					for (int j = 0; j < width; j++) {
						this.map[i][j] = (j < width) ? map[i][j] : 0;
					}
				}
			}
		}

		this.map = map;
	}

}