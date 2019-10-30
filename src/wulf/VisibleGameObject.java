package wulf;

import java.io.File;

//gameObject qui peut s'afficher, donc qui contient des sprites
public class VisibleGameObject extends GameObject {
	
	private File currentTexFile;
	private File[] texFileLst;
	
	
	
	public File getCurrentTexFile() {
		return currentTexFile;
	}
	
	public void setCurrentTexFile( int texId ) {
		try {
		setCurrentTexFile( texFileLst[texId] );
		} catch ( IndexOutOfBoundsException e ) {
			System.out.println("[VisibleGameObject] wrong texture id - " + e.getMessage() );
		}
	}
	public void setCurrentTexFile(File currentTexFile) {
		this.currentTexFile = currentTexFile;
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
	

	public VisibleGameObject(String string, double posX, double posY, double angle) {
		this( new String[]{string} , posX, posY, angle );
	}
	
	public VisibleGameObject( String[] paths, double posX, double posY, double angle ) {
		super(posX, posY, angle);
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
	
	public VisibleGameObject( File[] texFileLst, double posX, double posY, double angle) {
		
		super(posX, posY, angle);
		setTexFiles(texFileLst);
	}

	
	
}
