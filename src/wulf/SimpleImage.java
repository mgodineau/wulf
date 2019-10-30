package wulf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class SimpleImage {

	private int height;
	private int width;

	private int[] raster;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getColor(int x, int y) {
		int id = x + width * y;
		try {
			return raster[id];
		} catch (IndexOutOfBoundsException e) {
			System.out.println( "[SimpleImage] coordonnées (" +x + ", " + y + ") hors de la texture" );
			return -1;
		}
	}

	public SimpleImage(BufferedImage img) {
		// changement du type d'image pour avoir du TYPE_INT_RGB
		if (img.getType() != BufferedImage.TYPE_INT_RGB) {
			BufferedImage imgInt = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graph = imgInt.createGraphics();
			graph.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
			graph.dispose();
			img = imgInt;
		}
		raster = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		height = img.getHeight();
		width = img.getWidth();
	}

}
