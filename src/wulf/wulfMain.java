package wulf;

public class wulfMain {

	public static void main(String[] args) {
		
		Wall[][] map = {
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR }
		};
		
		World world = new World( map );
		Renderer rend = new Renderer(1500, 400);
		
		Camera cam = new Camera(360);
		cam.setAngleDeg(45);
		cam.setPosX(1.5);
		cam.setPosY(1.5);
		
		rend.drawImg(world, cam);
		
		//StdDraw.line(0, 0, 1, 1);
		
	}
}
