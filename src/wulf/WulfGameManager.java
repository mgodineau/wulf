package wulf;

import java.awt.Dimension;

public class WulfGameManager {
	
	
	private World world;
	private RendererFast rend;
	private WulfWindow win;
	
	private Camera cam;
	
	
	public void startLoop() {
		double deltaTime = 16; //deltaTime, en miliseconde
		while (true) {
			update( deltaTime );
			try {
				Thread.sleep((long) (deltaTime) );
			} catch (InterruptedException e) {
				System.out.println("Error sleeping : " + e.getMessage() );
			}
		}

	}
	
	//TODO machins crados
	private double tps = 0;
	double timeToRad = Math.PI * 2.0 / 1000.0;
	float spd = 0.1f;
	
	public void update( double deltaTime ) {
		
		//mise à jour de la logique de jeu
		//TODO faire un truc clean pour virer ces trucs dégueu
		tps += deltaTime;
		cam.setPosX( 2.5 + Math.cos(tps*timeToRad * spd) );
		cam.setPosY( 2.5 + Math.sin(tps*timeToRad * spd) );
		
		//mise à jour de la résolution de la fenêtre 
		Dimension dim = win.getSize();
		rend.setHeight(dim.height);
		rend.setWidth(dim.width);
		
		//génération de l'image dans un BufferedImage
		rend.drawImg(world, cam);
		
		//mise à jours de la fenêtre 
		win.repaint();
		
		
		//TODO test
		Wall.MurBleu.getTexture();
	}
	
	
	
	public WulfGameManager ( World world, Camera cam ) {
		this( world, cam, 600, 400 );
	}
	
	public WulfGameManager ( World world, Camera cam , int width, int height ) {
		this.world = world;
		this.cam = cam;
		
		rend = new RendererFast(width, height);
		win = new WulfWindow(rend);
	}

}
