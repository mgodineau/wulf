package gameLogic;

import wulf.InputManager;
import wulf.SimpleMath;
import wulf.World;

public class GameObject {

	private double posX;
	private double posY;
	private double height;
	private double angle; // angle en radian
	private double radius;

	private World world;
	protected InputManager input;

	// getter - setter

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = Math.max(0, radius);
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean setPos(double x, double y) {
		if (world != null && !world.pointInMap(x, y)) {
			return false;
		}
		posX = x;
		posY = y;
		return true;
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
		setAngleRad(Math.toRadians(angle));
	}

	public void setAngleRad(double angle) {
		this.angle = angle;
	}

	// fonction update
	public void update(double deltaTime) {

	}

	public void translate(double deltaX, double deltaY) {
		/*
		 * double dst = SimpleMath.dist(deltaX, deltaY); Collision col =
		 * world.raycast(posX, posY, deltaX/dst, deltaY/dst ); if ( radius + dst <
		 * col.getDistance() ) { posX += deltaX; posY += deltaY; }
		 */

		// déplacement en X
		if (deltaX != 0) {
			posX += deltaX;
			int u = (int) posX + ((deltaX > 0) ? 1 : -1);

			if (world.pointInMap(u, (int) posY) && world.getWall(u, (int) posY) != Wall.VIDE
					&& ((deltaX > 0) ? 1 - (posX % 1) : posX % 1) <= radius) {
				posX = (int) posX + (deltaX > 0 ? 1 - radius : radius);
			}
		}
		// déplacement en Y
		if (deltaY != 0) {
			posY += deltaY;
			int v = (int) posY + ((deltaY > 0) ? 1 : -1);

			if (world.pointInMap((int) posX, v) && world.getWall((int) posX, v) != Wall.VIDE
					&& ((deltaY > 0) ? 1 - (posY % 1) : posY % 1) <= radius) {
				posY = (int) posY + (deltaY > 0 ? 1 - radius : radius);
			}
		}

		// tests des collisions dans les coins
		double spd = SimpleMath.dist(deltaX, deltaY);
		if ( world.isWall((int) posX + 1, (int) posY + 1) ) {
			reactFromPoint(spd, (int) posX + 1, (int) posY + 1);
		}
		if ( world.isWall((int) posX - 1, (int) posY + 1) ) {
			reactFromPoint(spd, (int) posX, (int) posY + 1);
		}
		if ( world.isWall((int) posX + 1, (int) posY - 1) ) {
			reactFromPoint(spd, (int) posX + 1, (int) posY);
		}
		if ( world.isWall((int) posX - 1, (int) posY - 1) ) {
			reactFromPoint(spd, (int) posX, (int) posY);
		}
	}

	private void reactFromPoint(double spd, double pointX, double pointY) {
			double reactX = posX - pointX;
			double reactY = posY - pointY;

			double dst = SimpleMath.dist(reactX, reactY);
			if (dst <= radius) {
				posX += reactX * spd / dst;
				posY += reactY * spd / dst;
			}
	}

	public GameObject() {
		this(0, 0);
	}

	public GameObject(double posX, double posY) {
		this(posX, posY, 0, 90, 0.5);
	}

	public GameObject(double posX, double posY, double height, double angle, double radius ) {
		setPos(posX, posY);
		setAngleDeg(angle);
		setRadius(radius);
	}

}
