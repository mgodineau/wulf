package wulf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RendererFast extends Renderer {

	private BufferedImage renderImg;
	private WritableRaster raster;
	
	private JFrame window;
	
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
				groundHeightOnScreen = (1.0 - 1.0 / dst) / 2;
				// TODO draw line
				drawLine(x, (int) groundHeightOnScreen, (int) (1.0 / dst + groundHeightOnScreen));
			}
		}
		// TODO draw image

	}
	
	public BufferedImage getImg () {
		return renderImg;
	}
	

	private void drawLine ( int x, int bot, int top ) {
		float[] colRoof = {10, 10 , 10};
		float[] colWall = {200, 200 , 200};
		float[] colFloor = {30, 30 , 30};
		for ( int y=0; y<bot; y++ ) {
			raster.setPixel(x, y, colFloor);
		}
		for( int y=bot; y<= top; y++ ) {
			raster.setPixel(x, y, colWall );
		}
		for ( int y=top+1; y<height; y++ ) {
			raster.setPixel(x, y, colRoof );
		}
	}

	public RendererFast(int width, int height) {
		super(width, height);
		renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		raster = renderImg.getRaster();
		
	}

}
