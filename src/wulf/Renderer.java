package wulf;

public abstract class Renderer {
	
	
	protected int width;
	protected int height;
	
	
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
	
	
	
	public abstract void drawImg ( World world , Camera cam );
	
	
	
	public Renderer( int width , int height ) {
		this.height = 1;
		this.width = 1;
		
		setWidth(width);
		setHeight(height);
		
		
	}
	
	
	
}
