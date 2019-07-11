package com.br.dodgeit.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		
		keyDown[0] = false; // W
		keyDown[1] = false; // S
		keyDown[2] = false; // D
		keyDown[3] = false; // A
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); // Get the INT number for the keys on the keyboard. Like ASCII.
		
		/*
		 * How to know if I'm moving the player object instead of the enemy object?
		 * This for will iterate all the objects in the game in the handler and find the player by its Enum.ID.
		 */
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				//All the key movements for Player 1.
				
				//If the player is Player 1 AND is pressing key W, move him up.
				if (key == KeyEvent.VK_W) { tempObject.setVelY(-5); keyDown[0] = true; };
				if (key == KeyEvent.VK_S) { tempObject.setVelY(5); keyDown[1] = true; };
				if (key == KeyEvent.VK_D) { tempObject.setVelX(5); keyDown[2] = true; };
				if (key == KeyEvent.VK_A) { tempObject.setVelX(-5); keyDown[3] = true; };
			}
			
			if (tempObject.getId() == ID.Player2) {
				//All the key movements for Player 2.
				
				if (key == KeyEvent.VK_UP) tempObject.setVelY(-5);
				if (key == KeyEvent.VK_DOWN) tempObject.setVelY(5);
				if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(5);
				if (key == KeyEvent.VK_LEFT) tempObject.setVelX(-5);
			}
		}
		
		if (key == KeyEvent.VK_ESCAPE) System.exit(1);
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				//All the key movements (when released) for Player 1.
				
				//If the player is Player 1 AND is pressing key W, move him up.
				if (key == KeyEvent.VK_W) keyDown[0] = false;
				if (key == KeyEvent.VK_S) keyDown[1] = false;
				if (key == KeyEvent.VK_D) keyDown[2] = false;
				if (key == KeyEvent.VK_A) keyDown[3] = false;
				
				// Vertical Movement
				if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				
				// Horizontal Movement
				if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
			}
			
			if (tempObject.getId() == ID.Player2) {
				//All the key movements (when released) for Player 2.
				
				if (key == KeyEvent.VK_UP) tempObject.setVelY(0);
				if (key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
				if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
				if (key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
				
			}
		}
	}
}
