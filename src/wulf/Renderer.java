package wulf;

public class Renderer {
	
	
	private int width;
	private int height;
	
	
	public float getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = clampPos(width);
		StdDraw.setCanvasSize(width, height);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = clampPos(height);
		StdDraw.setCanvasSize(width, height);
	}
	
	private int clampPos ( int n ) {
		return Math.max(0, n);
	}
	
	
	
	public void drawImg ( World world , Camera cam ) {
		double dst = 0;
		double groundHeightOnScreen;
		double raycastAngle = 0;
		double halfFOV = cam.getFov()/2;
		
		for ( int x=0; x<width; x++ ) {
			raycastAngle = cam.getAngle() - cam.getFov() * ((double)x/width) - halfFOV;
			dst = world.raycast(cam.getPosX(), cam.getPoxY(), raycastAngle );
			if ( dst >= cam.getClipNear() ) {
				groundHeightOnScreen = (1 - 1/dst)/2; 
				StdDraw.line((double)x/width, groundHeightOnScreen, (double)x/width, 1/dst + groundHeightOnScreen );
			}
		}
	}
	
	
	
	public Renderer ( int width , int height ) {
		this.width = 512;
		this.height = 512;
		setWidth(width);
		setHeight(height);
	}
	
	
	
}
