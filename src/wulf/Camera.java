package wulf;

import java.awt.event.KeyEvent;

public class Camera extends GameObject {

	private double fov; //fov en radian
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
	
	
	private float spd = 2;
	private float angularSpd = 90;
	private double halfPi = Math.PI / 2;
	
	//TODO fct provisoire
	public void update ( double deltaTimeSec , InputManager inputMng ) {
		
		//dpl en translation
		double deltaX = 0;
		double deltaY = 0;
		if ( inputMng.isKeyPressed( KeyEvent.VK_Z ) ) {
			deltaX += Math.cos(getAngleRad()) * spd;
			deltaY += Math.sin(getAngleRad()) * spd;
		}
		if ( inputMng.isKeyPressed( KeyEvent.VK_S ) ) {
			deltaX -= Math.cos(getAngleRad()) * spd;
			deltaY -= Math.sin(getAngleRad()) * spd;
		}
		if ( inputMng.isKeyPressed( KeyEvent.VK_Q ) ) {
			deltaX += Math.cos(getAngleRad() + halfPi) * spd;
			deltaY += Math.sin(getAngleRad() + halfPi) * spd;
		}
		if ( inputMng.isKeyPressed( KeyEvent.VK_D ) ) {
			deltaX += Math.cos(getAngleRad() - halfPi) * spd;
			deltaY += Math.sin(getAngleRad() - halfPi) * spd;
		}
		setPosX(getPosX() + deltaX*deltaTimeSec);
		setPosY(getPosY() + deltaY*deltaTimeSec);
		//rotation de la cam
		if ( inputMng.isKeyPressed(KeyEvent.VK_LEFT) ) {
			setAngleDeg(getAngleDeg() + angularSpd * deltaTimeSec);
		}
		if ( inputMng.isKeyPressed(KeyEvent.VK_RIGHT) ) {
			setAngleDeg(getAngleDeg() - angularSpd * deltaTimeSec);
		}
	}
	
	
	public Camera() {
		this(90);
	}

	public Camera(double fov) {
		this(fov , 0.01);
	}
	
	//fov en degr�
	public Camera(double fov , double clipNear) {
		setFovDeg(fov);
		setClipNear(clipNear);
	}
	

}
