package wulf;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter {
	
	
	private int[] trackedKeys;
	private boolean[] keyPressed;
	
	@Override
	public void keyPressed( KeyEvent event ) {
		setKeyState(event, true);
	}
	
	@Override
	public void keyReleased ( KeyEvent event ) {
		setKeyState(event, false);
	}
	
	private void setKeyState ( KeyEvent event, boolean pressed ) {
		for ( int i=0; i<trackedKeys.length; i++ ) {
			if ( trackedKeys[i] == event.getKeyCode() ) {
				keyPressed[i] = pressed;
				return;
			}
		}
	}
	
	
	public boolean isKeyPressed ( int keyCode ) {
		
		for ( int i=0; i<trackedKeys.length; i++ ) {
			if ( trackedKeys[i] == keyCode ) {
				return keyPressed[i];
			}
		}
		return false;
	}
	
	
	public InputManager() {
		int[] trackedKeys = {
				KeyEvent.VK_Z,
				KeyEvent.VK_S,
				KeyEvent.VK_Q,
				KeyEvent.VK_D,
				KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT,
		};
		this.trackedKeys = trackedKeys;
		
		keyPressed = new boolean[ trackedKeys.length ];
		for ( int i=0; i<keyPressed.length; i++ ) {
			keyPressed[i] = false;
		}
	}
	
}
