package com.br.dodgeit.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5956531451738685003L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	
	private Handler handler; // Instance of Handler
	Random r;
	private HUD hud; // Instance of HUD
	private Spawn spawner; // Instance of Spawner
	private Menu menu; // Instance of Menu
	
	public enum STATE {
		Menu,
		Game,
		Help,
		End
	};
		
	public STATE gameState = STATE.Menu;
	
	public Game(){
		
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler));
		r = new Random();		
		spawner = new Spawn(handler, hud);
		
		new Window(WIDTH, HEIGHT, "DODGE IT", this);
		
		if(gameState == STATE.Game) {
			handler.addObject(new Player(WIDTH/2 - 32, HEIGHT /2 - 32, ID.Player, handler));
			//handler.addObject(new Player(WIDTH/2, HEIGHT /2 - 32, ID.Player2));
			handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(WIDTH), ID.BasicEnemy, handler));
		} else {
			for (int i = 0; i < 18; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
		
	}

	public synchronized void start(){
		//Window.game.start() start this method as a thread 
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join(); //Killing all the threads
			running = false;
		} catch (Exception e) {
			e.printStackTrace(); //Print the error on console
		}
	}
	
	public void run(){
		/*
		 * Game loop - same as used by Notch
		 */
		this.requestFocus(); //So you don't have to click the screen every time. Game if 1st plan.
		
		 long lastTime = System.nanoTime(); //Get current time to the nanosecond
		 double amountOfTicks = 60.0; //Set the number of ticks
		 double ns = 1000000000 / amountOfTicks; //This determines how many times we can divide 60 into 1^9 of nanoseconds or about 1 second
		 double delta = 0; //Change in time (delta always means a change like delta v is change in velocity)
		 long timer = System.currentTimeMillis(); // get current time 
		 int frames = 0; // set frame variable
		 
		 while(running){ 

			 long now = System.nanoTime(); // get current time in nanoseconds during current loop 
			 delta += (now - lastTime) / ns; // add the amount of change since the last loop
			 lastTime = now; // set lastTime to now to prepare for next loop

			 while(delta >= 1){
				 /*
				  * One tick of time has passed in the game. This ensures that we have a steady pause in our game loop so we don't have a game that runs way too fast.
				  * Basically we are waiting for enough time to pass so we have about 60 frames per one second interval determined to the nanosecond (accuracy)
				  * once this pause is done we render the next frame
				  */
				 tick(); 
				 delta--; // lower our delta back to 0 to start our next frame wait
			 }

			 if(running) { 
				 render(); // render the visuals of the game

				 frames++; // note that a frame has passed

				 if(System.currentTimeMillis() - timer > 1000 ) { // if one second has passed
					 timer+= 1000; // add a thousand to our timer for next time
					 System.out.println("FPS: " + frames); // print out how many frames have happened in the last second
					 frames = 0; // reset the frame count for the next second
				 }
			 }	else {
				 stop(); // no longer running stop the thread
			 }
		 }
	}

	private void tick() {
		handler.tick();
		
		if(gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			
			if(HUD.HEALTH <= 0){
				HUD.HEALTH = 100;
				gameState = STATE.End;
				handler.clearPlayer();
				handler.clearEnemy();
				
				// Create the MenuParticles again
				for (int i = 0; i < 18; i++) {
					handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
				}							
				
			}	
			
		} else if (gameState == STATE.Menu || gameState == STATE.End){
			menu.tick();			
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3); // Creating 3 buffers for the game
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End){
			menu.render(g);
		}		
		
		g.dispose();
		bs.show();	
	}
	
	//Clamp to make the player stay inbounds.
	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		}
		
		return var;
	}

	public static void main(String args[]) {
		new Game();
	}
	
}