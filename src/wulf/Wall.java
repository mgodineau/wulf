package wulf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Wall {
	
	VIDE(),
	MurBleu("assets/textures/blueWall.png"),
	MurGris("assets/textures/grayWall.png")
	;
	
	
	private File textureFile;
	
	
	
	public BufferedImage getTexture() {
		BufferedImage img = null;
		try {
			return ImageIO.read(textureFile);
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de la texture : " + e.getMessage() + "File : "  + textureFile != null ? textureFile.getPath() : "null" );
		}
		return img;
	}
	
	public File getTextureFile() {
		return textureFile;
	}
	
	
	Wall () {
		this((File)null);
	}
	
	Wall( String texturePath ) {
		this( new File(texturePath) );
	}
	
	Wall( File textureFile ) {
		this.textureFile = textureFile;
	}
	
}
