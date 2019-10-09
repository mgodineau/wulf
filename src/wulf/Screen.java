package wulf;

public class Screen {
	private int width;
	private int height;
	
	public float getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = clampPos(width);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = clampPos(height);
	}
	
	private int clampPos ( int n ) {
		return Math.max(0, n);
	}
	
	
	public Screen ( int width , int height ) {
		setWidth(width);
		setHeight(height);
	}
}
