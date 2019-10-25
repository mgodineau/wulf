package wulf;

import java.awt.Color;

public class wulfMain {

	public static void main(String[] args) {

		/*
		 * Wall[][] map = { { Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu
		 * , Wall.MurBleu }, { Wall.MurBleu , Wall.VIDE, Wall.VIDE , Wall.VIDE ,
		 * Wall.MurBleu }, { Wall.MurBleu , Wall.VIDE, Wall.MurBleu , Wall.VIDE ,
		 * Wall.MurBleu }, { Wall.MurBleu , Wall.VIDE, Wall.VIDE , Wall.VIDE ,
		 * Wall.MurBleu }, { Wall.MurBleu , Wall.MurBleu , Wall.MurBleu , Wall.MurBleu ,
		 * Wall.MurBleu } };
		 */

		Wall[][] map = { 
			{ Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu , Wall.MurBleu, Wall.MurBleu, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.MurBleu, Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.VIDE, Wall.MurBleu },
			{ Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu, Wall.MurBleu , Wall.MurBleu, Wall.MurBleu, Wall.MurBleu },
		};

		World world = new World(map , Color.gray, Color.darkGray);
		Camera cam = new Camera(90);
		cam.setAngleDeg(40);
		cam.setPosX(3);
		cam.setPosY(2);

		WulfGameManager gameManager = new WulfGameManager(world, cam, 1000, 600);
		gameManager.startLoop();

	}

}
