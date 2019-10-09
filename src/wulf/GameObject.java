package wulf;

public class GameObject {
	
	private float posX;
	private float posY;
	private double angle;
	
	
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPoxY() {
		return posY;
	}
	public void setPoxY(float poxY) {
		this.posY = poxY;
	}
	
	
	public GameObject () {
		this(0,0);
	}
	
	public GameObject( float posX , float posY) {
		this.posX = posX;
		this.posY = posY;
		
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
}
