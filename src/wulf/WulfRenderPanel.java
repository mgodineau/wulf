package wulf;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class WulfRenderPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	
	
	public BufferedImage getimg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	
	@Override
	public void paintComponent( Graphics graph ) {
		//super.paintComponents(graph);
		synchronized (img) {
			graph.drawImage( img , 0, 0, null);
		}
	}
	
	
	public WulfRenderPanel( BufferedImage img ) {
		this.setImg(img);
	}

	
}
