package wulf;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class WulfWindow extends JFrame {
	
	private WulfRenderPanel panel;
	
	
	public WulfRenderPanel getPanel() {
		return panel;
	}

	public void setPanel(WulfRenderPanel panel) {
		this.panel = panel;
	}
	
	
	
	public WulfWindow( RendererFast rend ) {
		super("Wulfenstein 3D");
		
		panel = new WulfRenderPanel( rend.getImg() );
		setSize(rend.width, rend.height);
		//panel.setBackground(Color.BLUE);
		setContentPane(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
