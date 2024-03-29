package wulf;

import javax.swing.JFrame;

public class WulfWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private WulfRenderPanel panel;
	
	
	public WulfRenderPanel getPanel() {
		return panel;
	}

	public void setPanel(WulfRenderPanel panel) {
		this.panel = panel;
	}
	
	
	public WulfWindow( RendererFast rend ) {
		super("Wulfenstein 3D");
		
		panel = new WulfRenderPanel( rend.getRenderImg() );
		setSize(rend.getWidth(), rend.getHeight() );
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
