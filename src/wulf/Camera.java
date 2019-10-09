package wulf;

public class Camera extends GameObject {
	
	private float fov;
	
	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}
	
	private 
	
	public Camera () {
		this(90);
	}
	
	public Camera( float fov ) {
		this.setFov(fov);
	}
	
}
