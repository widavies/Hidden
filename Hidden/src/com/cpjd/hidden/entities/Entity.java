package com.cpjd.hidden.entities;

import java.awt.Graphics2D;

import com.cpjd.hidden.map.Map;
import com.cpjd.tools.Animation;

public abstract class Entity {

	protected Map tm;

	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int width;
	protected int height;

	protected double damage;
	protected double maxHealth;
	protected double currentHealth;
	protected boolean dead;
	protected double thorn;

	protected int cwidth;
	protected int cheight;
	protected int currRow;
	protected int currCol;
	protected double xtemp;
	protected double ytemp;

	protected Animation animation;
	
	public Entity(Map tm) {
		this.tm = tm;
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	
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
}
