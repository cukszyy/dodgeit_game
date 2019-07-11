/*
 * Maintain and update/render all objects. 
 * It will loop into all objects in the screen and render them individually.
 */

package com.br.dodgeit.main;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i); 
			tempObject.tick();
		}
		
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i); 
			tempObject.render(g);
		}
	}
	
	/*
	 * This method add and hook from the List
	 */
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	
	/*
	 * Personal Code - By myself
	 * LinkedList has an iterator, so I attributed it to an Iterator with same type.
	 * Then I iterate the LinkedList using the tempObject removing all objects whose ID were not Player.
	 */
	public void clearEnemy() {
		Iterator<GameObject> i = object.iterator();
		while(i.hasNext()){
			GameObject tempObject = i.next();
			if(tempObject.getId() != ID.Player){
				i.remove();
			}
		}
	}
	
	public void clearPlayer(){
		Iterator<GameObject> i = object.iterator();
		while(i.hasNext()){
			GameObject tempObject = i.next();
			if(tempObject.getId() == ID.Player){
				i.remove();
			}
		}
	}
	
}
