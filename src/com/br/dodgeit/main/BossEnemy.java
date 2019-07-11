package com.br.dodgeit.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject {
	
	private Handler handler;
	private int timer = 75;
	private int timer2 = 50;
	Random r = new Random();

	public BossEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velY = 2;
		velX = 0;

	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(timer < 0) velY = 0;
		else timer--;
		
		if(timer <= 0) timer2--;
		
		if(timer2 <= 0){
			
			if(velX == 0) velX = 2;
			
			if(velX > 0) velX += 0.01f;
			else if (velX < 0) velX -= 0.001f;
			
			velX = Game.clamp(velX, -10, 10); // Clamp sets limits.
			
			int spawn = r.nextInt(10);
			
			if(spawn == 0) {
				handler.addObject(new BossEnemyBullet((int)x + 38, (int)y + 48, ID.BasicEnemy, handler));
			}
		}
		
		//if (y < 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if (x < 0 || x >= Game.WIDTH - 96) velX *= -1;
		
		//handler.addObject(new Trail(x, y, ID.Trail, Color.orange, 56, 56, 0.008f, handler));		
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);
	}

}
