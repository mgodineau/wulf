package wulf;

import java.awt.Color;

public class wulfMain {

	public static void main(String[] args) {

		
		Wall[][] map = { 
			{ Wall.MurGris, Wall.MurBleu, Wall.MurBleu, Wall.MurGris, Wall.MurBleu , Wall.MurBleu, Wall.MurBleu, Wall.MurBleu },
			{ Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.MurGris, Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurGris, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu , Wall.MurBleu, Wall.MurBleu, Wall.MurBleu },
		};
		
		
		World world = new World(map , Color.gray, Color.darkGray);
		
		world.addObj( new VisibleGameObject("assets/textures/barrel.png", 1.5, 1.5, 0 ) );
		world.addObj( new VisibleGameObject("assets/textures/barrel.png", 4.4, 4.4, 0 ) );
		Camera cam = new Camera(90);
		cam.setAngleDeg(0);
		cam.setPosX(2);
		cam.setPosY(2);

		WulfGameManager gameManager = new WulfGameManager(world, cam, 1000, 600);
		gameManager.startLoop();

	}

}
