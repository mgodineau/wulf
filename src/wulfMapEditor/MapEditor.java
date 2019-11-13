package wulfMapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu.Separator;
import javax.swing.UIManager;

import wulf.Camera;
import wulf.Wall;
import wulf.World;
import wulf.WulfGameManager;
import wulf.WulfMain;

public class MapEditor extends JFrame implements ActionListener, KeyListener {

	private World currentWorld;
	private WorldPanel panel;
	
	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(World currentWorld) {
		this.currentWorld = currentWorld;
		panel.setCurrentWorld(currentWorld);
		getJMenuBar().getMenu(0).getItem(2).setEnabled( currentWorld != null );
	}

	@Override
	public void actionPerformed(ActionEvent event) {	
//		System.out.println(event);
		String cmd = event.getActionCommand();

		if (cmd.equals("Open")) {
			MenuOpenWorld();
		} else if (cmd.equals("Save")) {
			MenuSaveWorld();
		} else if ( cmd.equals("New") ) {
			MenuNewWorld();
		} else if ( cmd.equals("Play") ) {
			MenuPlay();
		}

	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		switch ( event.getKeyCode() ) {
		case KeyEvent.VK_I:
			//System.out.println("plus");
			panel.zoomIn();
			break;
		case KeyEvent.VK_O:
			//System.out.println("moins");
			panel.zoomOut();
			break;
		}
		//System.out.println("key pressed");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void MenuPlay() {
		if ( currentWorld == null ) {
			System.out.println("Imossible de lancer le jeu sans monde");
			return;
		}
		
		Thread thread = new Thread() {
			public void run() {
				WulfMain.main(new String[0]);
			}
		};
		thread.start();
	}
	
	public void MenuOpenWorld() {
		JFileChooser fChooser = new JFileChooser();

		int openDialog = fChooser.showOpenDialog(null);
		if (openDialog == JFileChooser.APPROVE_OPTION) {
			openWorld(fChooser.getSelectedFile());

		}
	}

	public void MenuSaveWorld() {
		
		if ( getCurrentWorld() == null ) {
			JOptionPane.showMessageDialog(null, "Impossible de sauvegarder un fichier : Pas de map chargée.");
			return;
		}
		
		JFileChooser fChooser = new JFileChooser();
		
		int saveDialog = fChooser.showSaveDialog(null);
		if ( saveDialog == JFileChooser.APPROVE_OPTION ) {
			saveWorld( fChooser.getSelectedFile() );
		}
		
	}
	
	public void MenuNewWorld() {
		setCurrentWorld(new World());
	}
	
	private void openWorld(File worldFile) {
		try {
			FileInputStream fInput = new FileInputStream(worldFile);
			ObjectInputStream oInput = new ObjectInputStream(fInput);

			setCurrentWorld((World) oInput.readObject());

			oInput.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible de charger le fichier : \"" + worldFile.getPath() + "\"");
		}
	}

	private void saveWorld(File worldFile) {
		
		try {
			FileOutputStream fOutput = new FileOutputStream(worldFile);
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);
			oOutput.writeObject(getCurrentWorld());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible de sauvegarder le fichier : \"" + worldFile.getPath() + "\"");
		}
	}

	private JMenu createJMenu(String menuTitle, String[] items) {
		JMenu menu = new JMenu(menuTitle);
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				menu.add(new Separator());
			} else {
				JMenuItem mItem = new JMenuItem(items[i]);
				mItem.addActionListener(this);
				menu.add(mItem);
			}
		}
		return menu;
	}

	public MapEditor() {

		// définition de base de la JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Wulfenstein map editor");
		setSize(600, 400);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addKeyListener(this);
		
		// sélection du style du système
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// création du menu
		JMenuBar mb = new JMenuBar();

		// le menu file
		String[] cmdLstFile = { "New", "Open", "Save" };
		JMenu menuFile = createJMenu("File", cmdLstFile);
		String[] cmdLstExec = {"Play"};
		JMenu menuExec = createJMenu("Exec", cmdLstExec);
		
		mb.add(menuFile);
		mb.add( menuExec );
		setJMenuBar(mb);

		setVisible(true);
		
		//création de la fenêtre d'édition
		panel = new WorldPanel(currentWorld);
		setContentPane(panel);
		
		setCurrentWorld(null);
	}

	public static void main(String[] args) {
		MapEditor editor = new MapEditor();
	}
	
	
	
	

}
