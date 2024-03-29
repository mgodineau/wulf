package wulfMapEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.PaintContext;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameLogic.Wall;
import wulf.World;

public class WorldPanel extends JPanel implements MouseListener {
	
	private World currentWorld;
	private HashMap<Wall, BufferedImage> wallToImg;
	private int tileSize;
	private float zoomSpd;
	
	private Wall[] cycleOrder;
	
	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(World currentWorld) {
		this.currentWorld = currentWorld;
		this.repaint();
	}
	
	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = (tileSize>0) ? tileSize : 0;
	}
	
	public void zoomIn() {
		zoom(true);
	}
	
	public void zoomOut() {
		zoom(false);
	}
	
	public void zoom( boolean in ) {
		setTileSize( (int) (getTileSize() * (in ? zoomSpd : 1.0f/zoomSpd)) );
		this.repaint();
	}
	
	
	private void loadTextures() {
		
		if ( wallToImg == null ) {
			wallToImg = new HashMap<Wall, BufferedImage>();
		}
		
		for ( Wall wall : Wall.values() ) {
			File file = wall.getTextureFileX();
			if ( file != null ) {
				try {
					BufferedImage img = ImageIO.read( file );
					wallToImg.put( wall, img );
				} catch (IOException e) {
					System.out.println("fichier \"" + file.getPath() + "\" non trouvé");
					e.printStackTrace();
				}
			}
		}
		this.repaint();
		
	}

	@Override
	public void paintComponent( Graphics graph ) {
		super.paintComponent( graph );
		
		if ( currentWorld != null ) {
			int originX = (int) (getWidth()/2 - ( (double) currentWorld.getWidth()/2 * getTileSize() ));
			int originY = (int) (getHeight()/2 - ( (double) currentWorld.getHeight()/2 * getTileSize() ));
			
			for ( int i=0; i<currentWorld.getHeight(); i++ ) {
				for ( int j=0; j<currentWorld.getWidth(); j++ ) {
					if ( currentWorld.getWall(i, j) != Wall.VIDE ) {
						graph.drawImage( 
								wallToImg.get(currentWorld.getWall(i, j)), 
								i*getTileSize() + originX, 
								(currentWorld.getHeight()-j-1)*getTileSize()+originY, 
								getTileSize(), 
								getTileSize(), 
								null 
						);//drawImage
					}
				}//for
			}//for
		}//if
		
	}
	

	@Override
	public void mouseClicked(MouseEvent event) {
		int[] tileCoord = getWorldCoord( event.getX() , event.getY() );
		
		if ( tileCoord != null ) {
			Wall prevWall = currentWorld.getWall(tileCoord[0], tileCoord[1]);
			currentWorld.setWall(tileCoord[0], tileCoord[1], cycleWall(prevWall));
			repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
	private int[] getWorldCoord ( double x, double y ) {
		x -= getWidth()/2; 
		x += currentWorld.getWidth()/2 * getTileSize();
		x /= getTileSize();
		
		y -= getHeight()/2; 
		y += currentWorld.getHeight()/2 * getTileSize();
		y /= getTileSize();
		y = currentWorld.getHeight() - y;
		int[] vect2 = { (int) x, (int) y };
		return currentWorld.pointInMap(x, y) ? vect2 : null;
	}
	
	private Wall cycleWall ( Wall wall ) {
		for ( int i=0; i<cycleOrder.length; i++ ) {
			if ( cycleOrder[i] == wall ) {
				return cycleOrder[ (i<cycleOrder.length-1) ? i+1 : 0 ];
			}
		}
		return null;
	}
	
	public WorldPanel() {
		this(null);
	}
	
	public WorldPanel( World currentWorld ) {
		setCurrentWorld(currentWorld);
		setTileSize(50);
		loadTextures();
		zoomSpd = 1.1f;
		cycleOrder = new Wall[3];
		cycleOrder[0] = Wall.VIDE;
		cycleOrder[1] = Wall.MurBleu;
		cycleOrder[2] = Wall.MurGris;
		
		addMouseListener(this);
	}

		
}
