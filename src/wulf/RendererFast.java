package wulf;

import java.awt.Color;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RendererFast extends Renderer {

	private BufferedImage renderImg;
	
	//private BufferedImage wallTexture = Wall.MurBleu.getTexture();
	private Wall[] wallIdLst;
	private BufferedImage textureLst;
	
	public BufferedImage getRenderImg() {
		return renderImg;
	}

	@Override
	public void drawImg(World world, Camera cam) {
		double dst = 0;
		Collision col;
		int textureLine = 0;
		double groundHeightOnScreen;
		double raycastAngle = 0;
		double relativeCastAngle = 0;
		double halfFov = cam.getFov() / 2;
		double screenToWorld = Math.tan(halfFov) * cam.getClipNear() / (width / 2);

		for (int x = 0; x < width; x++) {
			relativeCastAngle = Math.atan2(cam.getClipNear(), screenToWorld * (double) (x - width / 2));
			raycastAngle = cam.getAngle() + relativeCastAngle;
			// dst = world.raycastDist(cam.getPosX(), cam.getPoxY(), raycastAngle) * 3;
			col = world.raycastCol(cam.getPosX(), cam.getPoxY(), raycastAngle);
			dst = col.getDistance();
			dst *= Math.sin(relativeCastAngle);
			if (dst >= cam.getClipNear()) {
				textureLine = (int) (wallTexture.getWidth() * ((col.getNormal()[0] == 0 ? col.getPosition()[0] : col.getPosition()[1])%1));
				groundHeightOnScreen = (1.0 - 1.0 / dst) / 2 * height;
				drawLine(x, (int) groundHeightOnScreen, (int) (height / dst + groundHeightOnScreen), world, wallTexture , textureLine);
			}
		}
	}

	public BufferedImage getImg() {
		return renderImg;
	}

	private void drawLine(int x, int top, int bot, World world, BufferedImage texture, int textureLine) {
		int topClp = top < 0 ? 0 : top;
		int botClp = bot >= height ? height - 1 : bot;

		int colRoof = world.getRoofCol().getRGB();
		// int colWall = Color.blue.getRGB();
		int colGround = world.getGroundCol().getRGB();

		for (int y = 0; y < topClp; y++) {
			renderImg.setRGB(x, y, colRoof);
		}
		for (int y = topClp; y <= botClp; y++) {
			int yTex = mapClamp(y, top, bot, 0, texture.getHeight()-1);
			renderImg.setRGB(x, y, texture.getRGB(textureLine, yTex) );
		}
		for (int y = botClp + 1; y < height; y++) {
			renderImg.setRGB(x, y, colGround);
		}
	}
	
	private int mapClamp( int val , int min1, int max1, int min2, int max2) {
		val = (val - min1) * (max2-min2) / (max1-min1);
		if ( val < min2 ) {
			val = min2;
		} else if ( val > max2 ) {
			val = max2;
		}
		return val;
	}
	
	public void loadTextures ( World world ) {
		//remplissage d'une arrayList avec les différents murs présents
		ArrayList<Wall> wallArrayLst = new ArrayList<Wall>();
		for( int i=0; i<world.getHeight(); i++ ) {
			for( int j=0; j<world.getWidth(); j++ ) {
				
				boolean wallInLst = world.getWall(i, j) == Wall.VIDE || wallArrayLst.contains( world.getWall(i, j) );
				if ( !wallInLst ) {
					wallArrayLst.add( world.getWall(i, j) );
				}
			}
		}
		
		//chargeement des textures nécessaires dans une tableau
		wallIdLst = new Wall[ wallArrayLst.size() ];
		
	}
	
	
	public RendererFast(int width, int height) {
		super(width, height);
		renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	}

}
