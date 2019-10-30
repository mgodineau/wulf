package wulf;

//ensemble de fonctions de math basiques
public class SimpleMath {
	
	
	//calculs de distance
	public static double dist ( GameObject obj1, GameObject obj2 ) {
		return dist( obj1.getPosX() - obj2.getPosX() , obj1.getPosY() - obj2.getPosY() );
	}
	
	public static double dist ( double x , double y ) {
		return Math.sqrt( sqrDist(x,y) );
	}
	
	public static double sqrDist(double x, double y) {
		return x * x + y * y;
	}
	
	
	
	//retourne l'angle en radian de 'to' depuis 'from'
	public static double relativeAngle ( GameObject from , GameObject to ) {
		
		double deltaX = to.getPosX() - from.getPosX();
		double deltaY = to.getPosY() - from.getPosY();
		return Math.atan2(deltaY, deltaX);
	}
	
}
