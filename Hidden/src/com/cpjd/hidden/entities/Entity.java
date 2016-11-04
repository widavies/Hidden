package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.cpjd.hidden.map.Map;
import com.cpjd.tools.Animation;

public class Entity {

	protected Map tm;
	protected double xmap;
	protected double ymap;

	// location
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int width;
	protected int height;

	// health & damage
	protected double damage;
	protected double maxHealth;
	protected double currentHealth;
	protected boolean dead;
	protected double thorn; // the thorn damage

	// collision
	protected int cwidth;
	protected int cheight;
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean bottomLeft, bottomRight, topLeft, topRight;
	protected static int tileSize;

	// animation
	protected Animation animation;
	
	public Entity(Map tm) {
		this.tm = tm;
		Entity.tileSize = tm.getTileSize();
	}

	public void update() {
		System.out.println("Sprite class update called, useless");
	}

	public void calculateCorners(double x, double y) {
		
	}

	// draws the sprite
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height, null);
	}
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.fillRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height);
	}

	// does the specified amount of damage to the sprite, returns damage if
	// thorn is in place
	public double hit(double damage) {
		currentHealth -= damage;

		if(currentHealth < 0) dead = true;

		return thorn;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	public Rectangle getCollisionBox(){
		return new Rectangle((int) x, (int) y, cwidth, cheight);
	}

}
