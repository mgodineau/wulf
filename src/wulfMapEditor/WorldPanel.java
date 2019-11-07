package wulfMapEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.PaintContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import wulf.Wall;
import wulf.World;

public class WorldPanel extends JPanel {
	
	private World currentWorld;
	private BufferedImage imgTest;//prov
	private HashMap<File, BufferedImage> fileToImg;
	private int tileSize;
	
	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(World currentWorld) {
		this.currentWorld = currentWorld;
	}
	
	private void loadTextures() {
		
		if ( fileToImg == null ) {
			fileToImg = new HashMap<File, BufferedImage>();
		}
		
		for ( Wall wall : Wall.values() ) {
			File file = wall.getTextureFileX();
			if ( file != null ) {
				try {
					BufferedImage img = ImageIO.read( file );
					fileToImg.put( file, img );
				} catch (IOException e) {
					System.out.println("fichier \"" + file.getPath() + "\" non trouvé");
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void paintComponent( Graphics graph ) {
		super.paintComponent( graph );
		//graph.drawImage(imgTest, 10, 100, null);
		int i=0;
		for ( BufferedImage img : fileToImg.values() ) {
			graph.drawImage( img , i , 0 , null );
			i += img.getWidth();
		}
		
	}
	
	
	public WorldPanel() {
		this(null);
	}
	
	public WorldPanel( World currentWorld ) {
		this.setCurrentWorld(currentWorld);
		this.tileSize = 10;
		loadTextures();
	}
	
}
