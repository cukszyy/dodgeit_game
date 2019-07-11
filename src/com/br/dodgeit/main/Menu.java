package com.br.dodgeit.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.br.dodgeit.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Game game;
	private Handler handler;
	Random r = new Random();
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud){
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}

	public void mousePressed(MouseEvent e){
		int mx = e.getX(); // Mouse position on X axis.
		int my = e.getY(); // Mouse position on Y axis.
		
		// Play button
		if(mouseOver(mx, my, 210, 150, 200, 64) && game.gameState == STATE.Menu){
			game.gameState = STATE.Game;
			handler.clearEnemy(); // Clean the MenuParticles
			handler.addObject(new Player(Game.WIDTH/2 - 32, Game.HEIGHT /2 - 32, ID.Player, handler));			
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.WIDTH), ID.BasicEnemy, handler));
		}
		
		// Help button
		if(mouseOver(mx, my, 210, 250, 200, 64) && game.gameState == STATE.Menu){
			game.gameState = STATE.Help;
		}
		
		// Back button
		if(game.gameState == STATE.Help && mouseOver(mx, my, 210, 350, 200, 64)){
			game.gameState = STATE.Menu;
			return;
		}
		
		// Quit button
		if(mouseOver(mx, my, 210, 350, 200, 64) && game.gameState != STATE.End){
			System.exit(1);
		}
		
		// Play again button		
		if(mouseOver(mx, my, 210, 350, 200, 64) && game.gameState == STATE.End){
			game.gameState = STATE.Menu;
			hud.setLevel(1);
			hud.setScore(0);
			
			// Clear the old player and sets up a new one.
			handler.clearPlayer(); 
		}
		
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
		if(game.gameState == STATE.Menu){
			Font f = new Font("arial", 1, 50);
			Font f2 = new Font("arial", 1, 30);
			
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Dodge it", 210, 70);
			
			g.setFont(f2);
			g.setColor(Color.WHITE);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 275, 190);
			
			g.setFont(f2);
			g.setColor(Color.WHITE);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 275, 290);
			
			g.setFont(f2);
			g.setColor(Color.WHITE);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 275, 390);
		} else if(game.gameState == STATE.Help){
			Font f = new Font("arial", 1, 50);
			
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Is this helping?", 140, 70);
			
			Font f2 = new Font("arial", 1, 30);
			g.setFont(f2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 275, 390);
		} else if(game.gameState == STATE.End){
			Font f = new Font("arial", 1, 50);
			Font f2 = new Font("arial", 1, 30);
			Font f3 = new Font("arial", 1, 20);
			
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("You lost", 210, 70);
			
			g.setFont(f3);
			g.drawString("Score: " + hud.getScore(), Game.WIDTH/2 - 70, 290);
			
			g.setFont(f2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Try Again", 245, 390);
		}			
	}
}
