package wulf;

public class RendererStdDraw extends Renderer {

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		StdDraw.setCanvasSize(width, height);
	}
	
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		StdDraw.setCanvasSize(width, height);
	}
	
	@Override
	public void drawImg(World world, Camera cam) {
		double dst = 0;
		double groundHeightOnScreen;
		double raycastAngle = 0;
		double relativeCastAngle = 0;
		double halfFov = cam.getFov() / 2;
		double screenToWorld = Math.tan(halfFov) * cam.getClipNear() / (width/2);
		

		for (int x = 0; x < width; x++) {
			relativeCastAngle = Math.atan2( cam.getClipNear() , screenToWorld * (double)(x - width/2) );
			raycastAngle = cam.getAngle() + relativeCastAngle;
			dst = world.raycastDist(cam.getPosX(), cam.getPoxY(), raycastAngle) * 3;
			dst *= Math.sin(relativeCastAngle);
			if (dst >= cam.getClipNear()) {
				groundHeightOnScreen = (1.0 - 1.0 / dst) / 2;
				StdDraw.line((double) x / width, groundHeightOnScreen, (double) x / width,
						(1 / dst + groundHeightOnScreen));
			}
		}
		StdDraw.show();
	}
	
	
	public RendererStdDraw(int width, int height) {
		super(width, height);
		StdDraw.enableDoubleBuffering();
		
	}

}
