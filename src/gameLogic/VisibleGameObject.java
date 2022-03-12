package gameLogic;

import java.io.File;

//gameObject qui peut s'afficher, donc qui contient des sprites
public class VisibleGameObject extends GameObject {
	
	protected File currentTexFile;
	protected File[] texFileLst;
	
	
	
	public File getCurrentTexFile() {
		return currentTexFile;
	}
	
	public File getCurrentTexFile( double viewAngle ) {
		return getCurrentTexFile();
	}
	
	public File[] getTexFileLst() {
		return texFileLst;
	}
	
	
	
	private void setTexFiles (File[] texFileLst ) {
		this.texFileLst = texFileLst;
		if ( texFileLst.length != 0 ) {
			currentTexFile = texFileLst[0];
		}
	}
	
	
	public VisibleGameObject(String string, double posX, double posY, double angle, double radius) {
		this( new String[]{string} , posX, posY, angle, radius );
	}
	
	public VisibleGameObject( String[] paths, double posX, double posY, double angle, double radius ) {
		super(posX, posY,0 , angle, radius);
		File[] texFileLst = new File[paths.length];
		for ( int i=0; i<texFileLst.length; i++ ) {
			texFileLst[i] = new File( paths[i] );
		}
		setTexFiles(texFileLst);
	}
	
	public VisibleGameObject( File[] texFileLst ) {
		super();
		setTexFiles(texFileLst);
		
	}
	
	public VisibleGameObject( File[] texFileLst, double posX, double posY, double angle, double radius) {
		
		super(posX, posY,0, angle, radius);
		setTexFiles(texFileLst);
	}

	
	
}
