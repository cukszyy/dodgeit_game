// This class will handle the game window.

package com.br.dodgeit.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6864732444752235971L;
	
	public Window(int width, int height, String title, Game game) {
		
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stop the game operation on X
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //Start the game in the middle of the screen
		frame.add(game); //Instance of the game itself
		frame.setVisible(true); //So we can actually see it
		game.start();
	}
	
}
