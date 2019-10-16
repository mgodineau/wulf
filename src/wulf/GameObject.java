package wulf;

public class GameObject {
	
	private double posX;
	private double posY;
	private double angle; //angle en radian
	
	
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPoxY() {
		return posY;
	}
	public void setPosY(double poxY) {
		this.posY = poxY;
	}
	
	
	public GameObject () {
		this(0,0);
	}
	
	public GameObject( double posX , double posY) {
		this( posX , posY , 90 );
	}
	
	public GameObject( double posX , double posY , double angle) {
		setPosX(posX);
		setPosY(posY);
		setAngleDeg(angle);
		
	}
	public double getAngle() {
		return angle;
	}
	public void setAngleDeg(double angle) {
		setAngleRad(Math.toRadians(angle) );
	}
	public void setAngleRad(double angle) {
		this.angle = angle;
	}
	
}
