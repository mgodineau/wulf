package wulf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class wulfMain {

	public static void main(String[] args) {
		
		Wall[][] map = {
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.MUR , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.VIDE, Wall.VIDE , Wall.VIDE , Wall.MUR },
			{ Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR , Wall.MUR }
		};
		
		World world = new World( map );
		Renderer rend = new RendererFast(1000, 600);
		//Renderer rend = new RendererStdDraw(1000, 600);
		
		WulfWindow window = null;
		if ( rend instanceof RendererFast ) {
			window = new WulfWindow((RendererFast) rend );
		}
		Camera cam = new Camera(90);
		cam.setAngleDeg(40);
		cam.setPosX(3);
		cam.setPosY(2);
		
		
		//trucs de test
		rend.drawImg(world, cam);
		//window.getPanel().setDisplay( ((RendererFast) rend).getImg() );
		
		//writeImg( window.getPanel().getDisplay() );
		//writeImg( ((RendererFast) rend).getRenderImg() );
		
		
		//boucle de jeu moche
		float deltaTime = 16; //le detaTime en milisecondes. 1/60e de seconde fait 16 ms.
		float spd = 0.3f;
		double timeToRad = Math.PI * 2.0 / 1000.0; //une constante qu'il faut multiplier par le tps pour avoir l'angle
		
		for ( float t=0; true || t<10000; t += deltaTime ) {
			cam.setPosX( 2.5 + Math.cos(t*timeToRad * spd) );
			cam.setPosY( 2.5 + Math.sin(t*timeToRad * spd) );
			rend.drawImg(world, cam);
			
			if( rend instanceof RendererStdDraw ) {
				StdDraw.clear();
				StdDraw.pause( (int)deltaTime );
			}
		}
		
	}
	
	
	public static void writeImg ( BufferedImage img ) {
		File outputFile = new File("test.jpg");
		try {
			ImageIO.write(img, "jpg", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
