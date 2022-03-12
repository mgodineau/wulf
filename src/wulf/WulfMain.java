package wulf;

import java.awt.Color;

import gameLogic.Camera;
import gameLogic.Soldier;
import gameLogic.VisibleGameObject;
import gameLogic.Wall;

public class WulfMain {

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
		
		world.addObj( new VisibleGameObject("assets/textures/objects/barrel/barrel.png", 1.5, 1.5, 0, 0 ) );
		world.addObj( new VisibleGameObject("assets/textures/objects/barrel/barrel.png", 4.4, 4.4, 0, 0 ) );
		world.addObj( new Soldier(2.5, 5.5, 90) );
		
		launchWorld(world);
	}
	
	
	public static void launchWorld ( World world ) {
		Camera cam = new Camera(90);
		cam.setAngleDeg(0);
		cam.setPos(2, 2);
		cam.setRadius(0.1);
		world.addObj(cam);

		WulfGameManager gameManager = new WulfGameManager(world, cam, 1000, 600);
		gameManager.startLoop();
	}

}
