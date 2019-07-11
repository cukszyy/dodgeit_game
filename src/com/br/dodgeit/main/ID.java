/*
 * How can the game know what GameObject is an enemy and what is a player?
 * They're all GameObjects. Enum is the solution to this so you can ID an GameObject whatever you want.
 */

package com.br.dodgeit.main;

public enum ID {
	
	Player(),
	Player2(),
	BasicEnemy(),
	Trail(),
	FastEnemy(),
	SmartEnemy(),
	BossEnemy(),
	MenuParticle();

}
