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
		double halfFOV = cam.getFov() / 2;

		for (int x = 0; x < width; x++) {
			raycastAngle = cam.getAngle() - cam.getFov() * ((double) x / width) - halfFOV;
			dst = world.raycast(cam.getPosX(), cam.getPoxY(), raycastAngle) * 3;
			//dst *= Math.abs(Math.sin( cam.getAngle() - raycastAngle ));
			if (dst >= cam.getClipNear()) {
				groundHeightOnScreen = (1.0 - 1.0 / dst) / 2;
				StdDraw.line((double) x / width, groundHeightOnScreen, (double) x / width,
						(1 / dst + groundHeightOnScreen));
				// StdDraw.line((double)x/width, 10, (double)x/width, 50);
			}
			//StdDraw.line((double)x/width, 0, (double)x/width, 0.5);
		}
		StdDraw.show();
	}
	
	
	public RendererStdDraw(int width, int height) {
		super(width, height);
		StdDraw.enableDoubleBuffering();
		
	}

}
