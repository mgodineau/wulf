package wulf;

public class Camera extends GameObject {

	private double fov; //fov en rdian
	private double clipNear;
	
	public double getFov() {
		return fov;
	}

	
	public void setFovDeg ( double fov ) {
		setFovRad( Math.toRadians(fov) );
	}
	
	public void setFovRad(double fov) {
		this.fov = fov;
	}
	
	public double getClipNear() {
		return clipNear;
	}

	public void setClipNear(double clipNear) {
		this.clipNear = clipNear;
	}
	
	
	public Camera() {
		this(90);
	}

	public Camera(double fov) {
		this(fov , 0.01);
	}
	
	//fov en degré
	public Camera(double fov , double clipNear) {
		setFovDeg(fov);
		setClipNear(clipNear);
	}
	

}
