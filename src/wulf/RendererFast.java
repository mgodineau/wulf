package wulf;

public class RendererFast extends Renderer {
	
	
	
	
	@Override
	public void drawImg ( World world , Camera cam ) {
		double dst = 0;
		double groundHeightOnScreen;
		double raycastAngle = 0;
		double halfFOV = cam.getFov()/2;
		
		for ( int x=0; x<width; x++ ) {
			raycastAngle = cam.getAngle() - cam.getFov() * ((double)x/width) - halfFOV;
			dst = world.raycast(cam.getPosX(), cam.getPoxY(), raycastAngle );
			if ( dst >= cam.getClipNear() ) {
				groundHeightOnScreen = (1 - 1/dst)/2;
				
				
				
			}
		}
	}
	
	
	
	public RendererFast ( int width , int height ) {
		super(width,height);
	}
	
	
	
}
