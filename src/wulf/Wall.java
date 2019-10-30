package wulf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Wall {
	
	VIDE(),
	MurBleu("assets/textures/walls/blueWall.png", "assets/textures/walls/blueWallDark.png"),
	MurGris("assets/textures/walls/grayWall.png", "assets/textures/walls/grayWallDark.png")
	;
	
	
	private File textureFileX;
	private File textureFileY;
	
	
	/*public BufferedImage getTexture() {
		BufferedImage img = null;
		try {
			return ImageIO.read(textureFile);
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de la texture : " + e.getMessage() + "File : "  + textureFile != null ? textureFile.getPath() : "null" );
		}
		return img;
	}*/
	
	public File getTextureFileX() {
		return textureFileX;
	}
	public File getTextureFileY() {
		return textureFileY;
	}
	
	
	Wall () {
		this((File)null, (File)null );
	}
	
	Wall( String texturePathX, String texturePathY ) {
		this( new File(texturePathX), new File(texturePathY) );
	}
	
	Wall( File textureFileX, File textureFileY ) {
		this.textureFileX = textureFileX;
		this.textureFileY = textureFileY;
	}
	
}
