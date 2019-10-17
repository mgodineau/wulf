package wulf;

public class wulfMain {

	public static void main(String[] args) {
		
		Wall[][] map = {
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.MUR , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR }
		};
		
		World world = new World( map );
		Renderer rend = new RendererStdDraw(1000, 600);
		
		Camera cam = new Camera(90);
		cam.setAngleDeg(40);
		cam.setPosX(3);
		cam.setPosY(2);
		
		float deltaTime = 16; //le detaTime en milisecondes. 1/60e de seconde fait 16 ms.
		float spd = 0.3f;
		double timeToRad = Math.PI * 2.0 / 1000.0; //une constante qu'il faut multiplier par le tps pour avoir l'angle
		
		for ( float t=0; true || t<10000; t += deltaTime ) {
			cam.setPosX( 2.5 + Math.cos(t*timeToRad * spd) );
			cam.setPosY( 2.5 + Math.sin(t*timeToRad * spd) );
			rend.drawImg(world, cam);
			StdDraw.clear();
			StdDraw.pause((int) deltaTime);
		}
		
	}
}
