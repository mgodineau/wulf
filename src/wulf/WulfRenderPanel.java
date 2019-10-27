package wulf;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class WulfRenderPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	
	@Override
	public void paintComponent( Graphics graph ) {
		//super.paintComponents(graph);
		graph.drawImage( getimg() , 0, 0, null); //truc qui doit marcher
		//graph.fillRect(10, 20, 30, 40);
	}
	
	public WulfRenderPanel( BufferedImage img ) {
		this.setimg(img);
	}

	public BufferedImage getimg() {
		return img;
	}

	public void setimg(BufferedImage img) {
		this.img = img;
	}
}
