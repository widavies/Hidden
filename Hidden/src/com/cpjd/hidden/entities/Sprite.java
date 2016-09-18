package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.map.TileMap;

public class Sprite {
	
	protected TileMap tm;
	protected double xmap;
	protected double ymap;
	
	// location
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	// collision
	protected int cwidth;
	protected int cheight;
	
	// physics
	protected double speed; // The object's current speed
	protected double acceleration; // How fast the object will gain momentum
	protected double topSpeed; // The max speed the object can go
	protected boolean left, right, up, down;
	
	// health & damage
	protected double damage;
	protected double maxHealth;
	protected double currentHealth;
	protected boolean dead;
	protected double thorn; // the thorn damage
	
	public Sprite(TileMap tm) {
		this.tm = tm;
	}
	
	public void update() {
		if(left || right || down || up) speed += acceleration;
		if(!left && !right && !down && !up) speed -= acceleration;
		
		if(speed > topSpeed) speed = topSpeed;
		if(speed < 0) speed = 0;
		
		if(left) x -= speed;
		if(right) x += speed;
		if(up) y -= speed;
		if(down) y += speed;
	}
	
	// draws the sprite
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
				width, height);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// does the specified amount of damage to the sprite, returns damage if thorn is in place
	public double hit(double damage) {
		currentHealth -= damage;
		
		if(currentHealth < 0) dead = true;
		
		return thorn;
	}
	
	public void setMapPosition() {
		xmap = tm.getX(); // Map pos tells us where to draw, actual location! vs global!, only want to draw it if it enter the screen
		ymap = tm.getY();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
