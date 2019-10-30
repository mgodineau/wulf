package wulf;

import java.awt.Dimension;

public class WulfGameManager {
	
	
	private World world;
	
	private InputManager inputMng;
	private RendererFast rend;
	private WulfWindow win;
	
	private Camera cam;
	
	private Dimension prevDim;
	
	public void startLoop() {
		prevDim = win.getSize();
		
		double deltaTimeTgt = 16; //tps idéal entre chaque frame
		double deltaTime = deltaTimeTgt; //deltaTime, en miliseconde
		double nextDeltaTimeNano = 0;
		
		
		while (true) {
			nextDeltaTimeNano = -System.nanoTime();
			update( Math.max(deltaTime , deltaTimeTgt));
			nextDeltaTimeNano += System.nanoTime();
			deltaTime = nextDeltaTimeNano / 1000000;
			try {
				Thread.sleep((long) ( Math.max(0, deltaTimeTgt - deltaTime ) ) );
			} catch (InterruptedException e) {
				System.out.println("Error sleeping : " + e.getMessage() );
			}
			
			//affichage moche du nb de fps
			//System.out.println( 1.0/(deltaTime/1000.0) + " fps" );
		}

	}
	
	
	public void update( double deltaTime ) {
		
		//mise ï¿½ jour de la logique de jeu
		//TODO faire un truc clean pour virer ces trucs dï¿½gueu
		//cam.update(deltaTime/1000, inputMng);
		world.update(deltaTime/1000);
		
		
		//mise ï¿½ jour de la rï¿½solution de la fenï¿½tre 
		Dimension dim = win.getSize();
		if ( dim.height != prevDim.height || dim.width != prevDim.width ) {
			rend.setHeight( dim.height );
			rend.setWidth(dim.width);
			win.getPanel().setImg( rend.getRenderImg() );
		}
		
		prevDim = dim;
		
		
		
		//gï¿½nï¿½ration de l'image dans un BufferedImage
		synchronized ( rend.getRenderImg() ) {
			rend.drawImg(world, cam);			
		}
		
		
		//mise ï¿½ jours de la fenï¿½tre 
		win.repaint();
		
		
	}
	
	
	
	public WulfGameManager ( World world, Camera cam ) {
		this( world, cam, 600, 400 );
	}
	
	public WulfGameManager ( World world, Camera cam , int width, int height ) {
		this.world = world;
		this.cam = cam;
		
		
		rend = new RendererFast(width, height);
		rend.loadTextures2(world);
		
		inputMng = new InputManager();
		world.setInputManager( inputMng );
		win = new WulfWindow(rend);
		win.addKeyListener(inputMng);
		win.setIgnoreRepaint(true); // ???
	}

}
