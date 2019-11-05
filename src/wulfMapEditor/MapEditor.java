package wulfMapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu.Separator;
import javax.swing.UIManager;

public class MapEditor extends JFrame implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event ) {
		System.out.println(event);
	}
	
	
	
	
	
	private JMenu createJMenu ( String menuTitle, String[] items ) {
		JMenu menu = new JMenu(menuTitle);
		for ( int i=0; i<items.length; i++ ) {
			if (items[i]==null) {
				menu.add( new Separator() );
			} else {
				JMenuItem mItem = new JMenuItem(items[i]);
				mItem.addActionListener(this);
				menu.add( mItem );
			}
		}
		return menu;
	}
	
	public MapEditor() {
		
		//définition de base de la JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Wulfenstein map editor");
		setSize(600, 400);
		setExtendedState( JFrame.MAXIMIZED_BOTH );
		
		//sélection du style du système
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//création du menu
		JMenuBar mb = new JMenuBar();
		
		//le menu file
		String[] cmdLst = {"New" , "Open", null , "Save"};
		JMenu menuFile = createJMenu( "File" , cmdLst );
		
		mb.add(menuFile);
		setJMenuBar(mb);
		
		
		setVisible(true);
		
	}
	

	public static void main(String[] args) {
		new MapEditor();
	}

}
