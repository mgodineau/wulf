package wulf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class RendererFast {

	private BufferedImage renderImg;

	// private BufferedImage wallTexture = Wall.MurBleu.getTexture();
	private Wall[] wallIdLst;
	private BufferedImage[] textureLst;

	private HashMap<File, BufferedImage> fileToTex;

	private int width;
	private int height;

	private float[] zBuffer;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		int newWidth = clampPos(width);
		if (this.width < newWidth) {
			renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			zBuffer = new float[newWidth];
		}
		this.width = newWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		int newHeight = clampPos(height);
		if (this.height < newHeight) {
			renderImg = new BufferedImage(width, newHeight, BufferedImage.TYPE_INT_RGB);
		}
		this.height = newHeight;
	}

	private int clampPos(int n) {
		return Math.max(0, n);
	}

	public BufferedImage getRenderImg() {
		return renderImg;
	}

	public void drawImg(World world, Camera cam) {

		// affichage des murs du monde
		double dst = 0;
		Collision col;
		int textureLine = 0;
		BufferedImage currentTexture = null;

		//double groundHeightOnScreen;
		double raycastAngle = 0;
		double relativeCastAngle = 0;
		double halfFov = cam.getFov() / 2;
		//double screenToWorld = Math.tan(halfFov) * cam.getClipNear() / (width / 2);
		double widthInWorld = Math.tan(halfFov);
		
		//balayage des colonnes de pixels de l'�cran
		for (int x = 0; x < width; x++) {
			//relativeCastAngle = Math.atan2(cam.getClipNear(), screenToWorld * (double) (x - width / 2)); //old
			
			//relativeCastAngle = Math.atan2( (width-x)*2*widthInWorld/width - widthInWorld ,  cam.getClipNear() );
			//relativeCastAngle = Math.atan2(1 , +x*2*widthInWorld/width - widthInWorld );
			relativeCastAngle = Math.atan2( (width-x)*2*widthInWorld/width - widthInWorld , 1 );
			raycastAngle = cam.getAngleRad() + relativeCastAngle;

			col = world.raycast(cam.getPosX(), cam.getPosY(), raycastAngle);
			dst = col.getDistance();
			zBuffer[x] = (float) dst;
			dst *= Math.cos(relativeCastAngle);

			if (dst >= cam.getClipNear()) {
				//groundHeightOnScreen = (1.0 - 1.0 / dst) / 2 * height;

				currentTexture = getTexture(col.getWallType());
				if (currentTexture == null) {
					loadTextures(world);
					currentTexture = getTexture(col.getWallType());
				}
				textureLine = (int) (currentTexture.getWidth()
						* ((col.getNormal()[0] == 0 ? col.getPosition()[0] : col.getPosition()[1]) % 1));

//				drawLine(x, (int) groundHeightOnScreen, (int) (height / dst + groundHeightOnScreen), world,
//						currentTexture, textureLine);
				drawLine(x, (int) (height / dst), world, currentTexture, textureLine);
			}
		}
		
		//affichage des sprites
		drawImgSpritesOnly(world, cam);
	}

	public void drawImgSpritesOnly(World world, Camera cam) {

		float dst = 0;
		float dstDisp = 0;
		double relativeAngle = 0;
		int centerOnScreen = 0;
		int scaleOnScreen = 0;

		BufferedImage currentTex = null;
		int left = 0;
		int right = 0;
		
		double radToScreen = width/2 / Math.tan( cam.getFov()/2 );
		double tanHalfFov = Math.tan( cam.getFov() / 2 );
		
		for (GameObject obj : world.getObjectLst()) {
			if (obj instanceof VisibleGameObject) {

				dst = (float) SimpleMath.dist(obj, cam);
				
				relativeAngle = SimpleMath.relativeAngle(cam, obj) - cam.getAngleRad();
				dstDisp = (float) (dst * Math.cos(relativeAngle));
				
				centerOnScreen = (int) (( ( tanHalfFov - Math.tan(relativeAngle)) / tanHalfFov) * width / 2);
				scaleOnScreen = (int) (height / dstDisp);
				currentTex = fileToTex.get(((VisibleGameObject) obj).getCurrentTexFile());
				left = centerOnScreen - scaleOnScreen / 2;
				right = centerOnScreen + scaleOnScreen / 2;

				// affichage du sprite
				for (int x = left; x < right; x++) {
					if ( x >= 0 && x < width && dst <= zBuffer[x]) {
						drawLine(x, (int) (height / dstDisp), null, currentTex,
								currentTex.getWidth() * (x - left) / (right - left));
					}
				}

			}
		}

	}

	public BufferedImage getImg() {
		return renderImg;
	}

	private void drawLine(int x, int heightOnScreen, World world, BufferedImage texture, int textureLine) {
		drawLine(x, (height - heightOnScreen) / 2, (height + heightOnScreen) / 2, world, texture, textureLine);
	}

	private void drawLine(int x, int top, int bot, World world, BufferedImage texture, int textureLine) {
		int topClp = top < 0 ? 0 : top;
		int botClp = bot >= height ? height - 1 : bot;

		//rendu des murs
		if (world != null) {
			int colRoof = world.getRoofCol().getRGB();
			int colGround = world.getGroundCol().getRGB();

			for (int y = 0; y < topClp; y++) {
				renderImg.setRGB(x, y, colRoof);
			}
			for (int y = botClp + 1; y < height; y++) {
				renderImg.setRGB(x, y, colGround);
			}
		}

		for (int y = topClp; y <= botClp; y++) {
			int yTex = mapClamp(y, top, bot, 0, texture.getHeight() - 1);
			int col = texture.getRGB(textureLine, yTex);
			if ( (new Color(col, true)).getAlpha() != 0 ) {
			renderImg.setRGB(x, y, col );
			}
		}

	}
	
	//TODO passer dans les maths
	private int mapClamp(int val, int min1, int max1, int min2, int max2) {
		val = (val - min1) * (max2 - min2) / (max1 - min1);
		if (val < min2) {
			val = min2;
		} else if (val > max2) {
			val = max2;
		}
		return val;
	}

	private BufferedImage getTexture(Wall wall) {
		for (int i = 0; i < wallIdLst.length; i++) {
			if (wallIdLst[i].equals(wall)) {
				return textureLst[i];
			}
		}
		return null;
	}

	public void loadTextures(World world) {
		// remplissage d'une arrayList avec les différents murs présents
		ArrayList<Wall> wallArrayLst = new ArrayList<Wall>();
		for (int i = 0; i < world.getHeight(); i++) {
			for (int j = 0; j < world.getWidth(); j++) {

				boolean wallInLst = world.getWall(i, j) == Wall.VIDE || wallArrayLst.contains(world.getWall(i, j));
				if (!wallInLst) {
					wallArrayLst.add(world.getWall(i, j));
				}
			}
		}

		// chargeement des textures nécessaires dans une tableau
		wallIdLst = new Wall[wallArrayLst.size()];
		textureLst = new BufferedImage[wallIdLst.length];
		for (int i = 0; i < wallIdLst.length; i++) {
			wallIdLst[i] = wallArrayLst.get(i);
			textureLst[i] = wallIdLst[i].getTexture();
		}

	}

	public void loadObjTextures(World world) {
		VisibleGameObject vObj = null;
		for (GameObject obj : world.getObjectLst()) {
			if (obj instanceof VisibleGameObject) {
				vObj = (VisibleGameObject) obj;
				try {
					fileToTex.putIfAbsent(vObj.getCurrentTexFile(), ImageIO.read(vObj.getCurrentTexFile()));
				} catch (IOException e) {
					System.out.println("file not found : " + e.getMessage());
				}
			}
		}

	}

	public RendererFast(int width, int height) {
		this.height = height;
		this.width = width;
		zBuffer = new float[this.width];

		renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		wallIdLst = new Wall[0];
		textureLst = new BufferedImage[0];
		fileToTex = new HashMap<File, BufferedImage>();
	}

}
