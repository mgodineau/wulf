package wulf;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RendererFast extends Renderer {

	private BufferedImage renderImg;
	
	//private JFrame window;
	

	public BufferedImage getRenderImg() {
		return renderImg;
	}
	
	
	@Override
	public void drawImg(World world, Camera cam) {
		double dst = 0;
		double groundHeightOnScreen;
		double raycastAngle = 0;
		double relativeCastAngle = 0;
		double halfFov = cam.getFov() / 2;
		double screenToWorld = Math.tan(halfFov) * cam.getClipNear() / (width / 2);

		for (int x = 0; x < width; x++) {
			relativeCastAngle = Math.atan2(cam.getClipNear(), screenToWorld * (double) (x - width / 2));
			raycastAngle = cam.getAngle() + relativeCastAngle;
			dst = world.raycast(cam.getPosX(), cam.getPoxY(), raycastAngle) * 3;
			dst *= Math.sin(relativeCastAngle);
			if (dst >= cam.getClipNear()) {
				groundHeightOnScreen = (1.0 - 1.0 / dst) / 2 * height;
				// TODO draw line
				drawLine(x, (int) groundHeightOnScreen, (int) (height / dst + groundHeightOnScreen) );
			}
		}
	}
	
	public BufferedImage getImg () {
		return renderImg;
	}
	

	private void drawLine ( int x, int bot, int top ) {
		bot = bot<0 ? 0 : bot;
		top = top>= height ? height-1 : top;
		int colRoof = Color.blue.getRGB();
		int colWall = Color.red.getRGB();
		int colFloor = Color.gray.getRGB();
		
		for ( int y=0; y<bot; y++ ) {
			renderImg.setRGB(x, y, colFloor);
		}
		for( int y=bot; y<= top; y++ ) {
			renderImg.setRGB(x, y, colWall);
		}
		for ( int y=top+1; y<height; y++ ) {
			renderImg.setRGB(x, y, colRoof);
		}
	}

	public RendererFast(int width, int height) {
		super(width, height);
		renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
	}
	
}
