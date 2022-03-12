package gameLogic;

import java.io.File;

public class Soldier extends DirectionalVisibleGO {

	private final static File[] soldierTextures = { 
		new File("assets/textures/objects/soldier/soldier_e.png"),
		new File("assets/textures/objects/soldier/soldier_ne.png"),
		new File("assets/textures/objects/soldier/soldier_n.png"),
		new File("assets/textures/objects/soldier/soldier_nw.png"),
		new File("assets/textures/objects/soldier/soldier_w.png"),
		new File("assets/textures/objects/soldier/soldier_sw.png"),
		new File("assets/textures/objects/soldier/soldier_s.png"),
		new File("assets/textures/objects/soldier/soldier_se.png"),
	};
	
	
	public Soldier( double posX, double posY, double angle) {
		super(soldierTextures, posX, posY, angle, 0.5);
		// TODO Auto-generated constructor stub
	}

}
