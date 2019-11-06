package wulfMapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import wulf.World;

public class MapEditor extends JFrame implements ActionListener {

	private World currentWorld;

	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(World currentWorld) {
		this.currentWorld = currentWorld;
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
		}

	}

	public void MenuOpenWorld() {
		JFileChooser fChooser = new JFileChooser();

		int openDialog = fChooser.showOpenDialog(null);
		if (openDialog == JFileChooser.APPROVE_OPTION) {
			// File worldFile = fChooser.getSelectedFile();
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

		// sélection du style du système
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// création du menu
		JMenuBar mb = new JMenuBar();

		// le menu file
		String[] cmdLst = { "New", "Open", "Save" };
		JMenu menuFile = createJMenu("File", cmdLst);

		mb.add(menuFile);
		setJMenuBar(mb);

		setVisible(true);
		
		
		setCurrentWorld(null);
	}

	public static void main(String[] args) {
		new MapEditor();
	}

}
