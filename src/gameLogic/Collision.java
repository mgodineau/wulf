package gameLogic;

public class Collision {
	
	private double dist;
	private Wall wallType;
	
	//des Vector2 qui représentent la position de la collision et la normal du plan de collision
	private double[] pos;
	private double[] normal;
	
	public double getDistance() {
		return dist;
	}
	
	public Wall getWallType() {
		return wallType;
	}
	
	public double[] getPosition() {
		return pos;
	}
	public double[] getNormal() {
		return normal;
	}
	
	private double[] toVector2 ( double[] vector ) {
		if ( vector.length == 2 ) {
			return vector;
		} else {
			double[] vector2 = new double[2];
			for( int i=0; i<vector.length && i<vector2.length; i++ ) {
				vector2[i] = vector[i];
			}
			return vector2;
		}
	}
	
	private double[] normalizedVector ( double[] vector ) {
		double norm = 0;
		for ( int i=0; i<vector.length; i++ ) {
			norm += vector[i] * vector[i];
		}
		norm = Math.sqrt(norm);
		
		if ( norm != 1 ) {
			for ( int i=0; i<vector.length; i++ ) {
				vector[i] = vector[i] = norm;
			}
		}
		return vector;
	}
	
	public Collision( double dist, Wall wallType, double[] pos, double[] normal ) {
		this.dist = dist;
		this.wallType = wallType!=null ? wallType : Wall.MurBleu;
		
		this.pos = toVector2(pos);
		this.normal = normalizedVector( toVector2(normal) );
	}
}
