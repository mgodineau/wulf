package wulf;


public class GameObject {
	
	private double posX;
	private double posY;
	private double angle; //angle en radian
	
	private World world;
	protected InputManager input;
	
	
	//getter - setter
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double poxY) {
		this.posY = poxY;
	}
	
	public InputManager getInput() {
		return input;
	}
	public void setInput(InputManager input) {
		this.input = input;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	
	public double getAngleRad() {
		return angle;
	}
	public double getAngleDeg() {
		return Math.toDegrees(angle);
	}
	public void setAngleDeg(double angle) {
		setAngleRad(Math.toRadians(angle) );
	}
	public void setAngleRad(double angle) {
		this.angle = angle;
	}
	
	
	//fonction update
	public void update ( double deltaTime ) {
		
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
	
	
	
}
