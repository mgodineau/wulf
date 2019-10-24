package wulf;

public class wulfMain {

	public static void main(String[] args) {
		
		Wall[][] map = {
			{ Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu },
			{ Wall.MurBleu , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MurBleu },
			{ Wall.MurBleu , Wall.VIDE, Wall.MurBleu , Wall.VIDE , Wall.MurBleu },
			{ Wall.MurBleu , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MurBleu },
			{ Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu }
		};
		
		World world = new World( map );
		Camera cam = new Camera(90);
		cam.setAngleDeg(40);
		cam.setPosX(3);
		cam.setPosY(2);
		
		WulfGameManager gameManager = new WulfGameManager(world, cam, 1000, 600);
		gameManager.startLoop();
		
		/*RendererFast rend = new RendererFast(1000, 600);
		//Renderer rend = new RendererStdDraw(1000, 600);
		
		WulfWindow window = new WulfWindow( rend );
		Camera cam = new Camera(90);
		cam.setAngleDeg(40);
		cam.setPosX(3);
		cam.setPosY(2);
		
		
		//writeImg( window.getPanel().getDisplay() );
		//writeImg( ((RendererFast) rend).getRenderImg() );
		
		
		//boucle de jeu moche
		float deltaTime = 16; //le detaTime en milisecondes. 1/60e de seconde fait 16 ms.
		float spd = 0.3f;
		double timeToRad = Math.PI * 2.0 / 1000.0; //une constante qu'il faut multiplier par le tps pour avoir l'angle
		
		for ( float t=0; true || t<10000; t += deltaTime ) {
			cam.setPosX( 2.5 + Math.cos(t*timeToRad * spd) );
			cam.setPosY( 2.5 + Math.sin(t*timeToRad * spd) );
			
			rend.drawImg(world, cam);
		}*/
		
	}
	
	
	
}
