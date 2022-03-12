package gameLogic;

import java.io.File;

public class DirectionalVisibleGO extends VisibleGameObject {
	
	
	protected DirectionalImage currentDirImg;
	
	
	@Override
	public File getCurrentTexFile() {
		return getCurrentTexFile(0);
	}
	
	@Override
	public File getCurrentTexFile( double viewAngle ) {
		return currentDirImg.getVisibleTexture( viewAngle );
	}
	
	
	
	public DirectionalVisibleGO(File[] texFileLst, double posX, double posY, double angle, double radius) {
		super(texFileLst, posX, posY, angle, radius);
		currentDirImg = new DirectionalImage(texFileLst);
		
		
	}

	

}
