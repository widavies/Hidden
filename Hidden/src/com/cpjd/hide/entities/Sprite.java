package com.cpjd.hide.entities;

import java.awt.Graphics2D;

public class Sprite {
	
	// location
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	// collision
	protected int cwidth;
	protected int cheight;
	
	// physics
	protected double speed;
	protected double topSpeed;
	
	// health & damage
	protected double damage;
	protected double maxHealth;
	protected double currentHealth;
	protected boolean dead;
	protected double thorn; // the thorn damage
	
	public Sprite() {
		
	}
	
	// draws the sprite
	public void draw(Graphics2D g) {
		
	}
	
	// does the specified amount of damage to the sprite, returns damage if thorn is in place
	public double hit(double damage) {
		currentHealth -= damage;
		
		if(currentHealth < 0) dead = true;
		
		return thorn;
	}
	
}
