package wulf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class RendererFast {

	private BufferedImage renderImg;
	private int[] rgbRaster;

	//textures chargées dans une HashMap, pour les retrouver à partir des fichiers
	private HashMap<File ,SimpleImage> fileToRaster;

	private int width;
	private int height;

	private float[] zBuffer;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		int newWidth = clampPos(width);
		setRenderImg( new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB) );
		zBuffer = new float[newWidth];
		this.width = newWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		int newHeight = clampPos(height);
		setRenderImg( new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB) );
		this.height = newHeight;
	}

	private int clampPos(int n) {
		return Math.max(0, n);
	}

	public BufferedImage getRenderImg() {
		return renderImg;
	}

	public void setRenderImg(BufferedImage renderImg) {
		this.renderImg = renderImg;
		rgbRaster = ((DataBufferInt) renderImg.getRaster().getDataBuffer()).getData();
	}

	public void drawImg(World world, Camera cam) {

		// affichage des murs du monde
		double dst = 0;
		Collision col;
		int textureLine = 0;
		SimpleImage currentTexture = null;

		// double groundHeightOnScreen;
		double raycastAngle = 0;
		double relativeCastAngle = 0;
		double halfFov = cam.getFov() / 2;
		double widthInWorld = Math.tan(halfFov);

		// balayage des colonnes de pixels de l'écran
		for (int x = 0; x < width; x++) {
			relativeCastAngle = Math.atan2((width - x) * 2 * widthInWorld / width - widthInWorld, 1);
			raycastAngle = cam.getAngleRad() + relativeCastAngle;

			col = world.raycast(cam.getPosX(), cam.getPosY(), raycastAngle);
			dst = col.getDistance();
			zBuffer[x] = (float) dst;
			dst *= Math.cos(relativeCastAngle);

			if (dst >= cam.getClipNear()) {
				
				currentTexture = fileToRaster.get( col.getNormal()[0] == 0 ? col.getWallType().getTextureFileX() : col.getWallType().getTextureFileY() );
				textureLine = (int) (currentTexture.getWidth()
						* ((col.getNormal()[0] == 0 ? col.getPosition()[0] : col.getPosition()[1]) % 1) );
				if ( col.getNormal()[0] == -1 || col.getNormal()[1] == 1 ) {
					textureLine = currentTexture.getWidth() - textureLine -1;
				}
				drawLine(x, (int) (height / dst), world, currentTexture, textureLine);
			}
		}

		// affichage des sprites
		drawImgSpritesOnly(world, cam);
	}

	public void drawImgSpritesOnly(World world, Camera cam) {

		float dst = 0;
		float dstDisp = 0;
		double relativeAngle = 0;
		int centerOnScreen = 0;
		int scaleOnScreen = 0;

		SimpleImage currentTex = null;
		int left = 0;
		int right = 0;

		double radToScreen = width / 2 / Math.tan(cam.getFov() / 2);
		double tanHalfFov = Math.tan(cam.getFov() / 2);

		for (GameObject obj : world.getObjectLst()) {
			if (obj instanceof VisibleGameObject) {

				dst = (float) SimpleMath.dist(obj, cam);

				relativeAngle = SimpleMath.relativeAngle(cam, obj) - cam.getAngleRad();
				dstDisp = (float) (dst * Math.cos(relativeAngle));

				centerOnScreen = (int) (((tanHalfFov - Math.tan(relativeAngle)) / tanHalfFov) * width / 2);
				scaleOnScreen = (int) (height / dstDisp);
				currentTex = fileToRaster.get(((VisibleGameObject) obj).getCurrentTexFile());
				left = centerOnScreen - scaleOnScreen / 2;
				right = centerOnScreen + scaleOnScreen / 2;

				// affichage du sprite
				for (int x = left; x < right; x++) {
					if (x >= 0 && x < width && dst <= zBuffer[x]) {
						drawLine(x, (int) (height / dstDisp), null, currentTex,
								currentTex.getWidth() * (x - left) / (right - left));
					}
				}

			}
		}

	}

	private void drawLine(int x, int heightOnScreen, World world, SimpleImage texture, int textureLine) {
		drawLine(x, (height - heightOnScreen) / 2, (height + heightOnScreen) / 2, world, texture, textureLine);
	}

	private void drawLine(int x, int top, int bot, World world, SimpleImage texture, int textureLine) {
		int topClp = top < 0 ? 0 : top;
		int botClp = bot >= height ? height - 1 : bot;

		// rendu des murs
		if (world != null) {
			int colRoof = world.getRoofCol().getRGB();
			int colGround = world.getGroundCol().getRGB();;

			for (int y = 0; y < topClp; y++) {
				setPixelInRaster(x ,y ,colRoof );
			}
			for (int y = botClp + 1; y < height; y++) {
				setPixelInRaster(x ,y ,colGround );
			}
		}
		
		for (int y = topClp; y <= botClp; y++) {
			int yTex = mapClamp(y, top, bot, 0, texture.getHeight() - 1);
			int col = texture.getColor(textureLine, yTex);
			if ((new Color(col, true)).getAlpha() != 0) {
				setPixelInRaster(x ,y ,col );
			}
		}

	}

	private void setPixelInRaster(int x, int y, int color) {
		int id = x + width*y;
		if ( id < rgbRaster.length && id >= 0) {
			rgbRaster[id] = color;
		}
	}

	// TODO passer dans les maths
	private int mapClamp(int val, int min1, int max1, int min2, int max2) {
		val = (val - min1) * (max2 - min2) / (max1 - min1);
		if (val < min2) {
			val = min2;
		} else if (val > max2) {
			val = max2;
		}
		return val;
	}
	
	
	public void loadTextures2( World world ) {
		if ( fileToRaster == null ) {
			fileToRaster = new HashMap<File, SimpleImage>();
		}
		ArrayList<File> fileLst = new ArrayList<File>();
		
		//ajout des fichiers de textures des murs
		for (int i = 0; i < world.getHeight(); i++) {
			for (int j = 0; j < world.getWidth(); j++) {
				File file = world.getWall(i, j).getTextureFileX();
				if ( file != null ) {
					fileLst.add(file );
				}
				file = world.getWall(i, j).getTextureFileY();
				if ( file != null ) {
					fileLst.add(file );
				}
			}
		}
		
		//ajouts des fichiers de texture des objets
		for ( GameObject obj : world.getObjectLst() ) {
			if ( obj instanceof VisibleGameObject ) {
				for ( File file : ((VisibleGameObject)obj).getTexFileLst() ) {
					if ( file != null ) {
						fileLst.add(file );
					}
				}
			}//if
		}//for
		
		//chargement des rasters des textures et stockage dans la HashMap fileToRaster
		for ( File file : fileLst ) {
			try {
				BufferedImage tex = ImageIO.read( file );
				fileToRaster.putIfAbsent(file, new SimpleImage(tex) );
			} catch (IOException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public RendererFast(int width, int height) {
		this.height = height;
		this.width = width;
		zBuffer = new float[this.width];

		setRenderImg(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB) );
		fileToRaster = new HashMap<File, SimpleImage>();
	}

}
