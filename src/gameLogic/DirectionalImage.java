package gameLogic;

import java.io.File;

public class DirectionalImage {
	
	private static final double angleStep = Math.PI / 8;
	
	private File[] fileLst;
	
	
	public File getVisibleTexture ( double relativeRadAngle ) {
		
		if ( relativeRadAngle < 0 ) {
			relativeRadAngle += Math.PI*2;
		}
		
		for ( int i=0; i<fileLst.length; i++ ) {
			if ( relativeRadAngle < angleStep * (1 + i*2) ) {
				return fileLst[i];
			}
		}
		return fileLst[0];
		
	}
	
	
	public DirectionalImage ( File[] fileLst ) {
		this.fileLst = new File[8];
		for ( int i=0; i<Math.min( this.fileLst.length , fileLst.length); i++ ) {
			this.fileLst[i] = fileLst[i];
		}
	}
	
}
