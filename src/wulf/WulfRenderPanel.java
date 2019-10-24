package wulf;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class WulfRenderPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private BufferedImage display;
	
	@Override
	public void paintComponent( Graphics graph ) {
		//super.paintComponents(graph);
		graph.drawImage( getDisplay() , 0, 0, null); //truc qui doit marcher
		//graph.fillRect(10, 20, 30, 40);
	}
	
	public WulfRenderPanel( BufferedImage display ) {
		this.setDisplay(display);
	}

	public BufferedImage getDisplay() {
		return display;
	}

	public void setDisplay(BufferedImage display) {
		this.display = display;
	}
}
