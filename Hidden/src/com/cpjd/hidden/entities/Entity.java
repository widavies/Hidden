package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.map.Tile;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.tools.Animation;

public class Entity {

	protected TileMap tm;
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
	
	public Entity(TileMap tm) {
		this.tm = tm;
		Entity.tileSize = tm.getTileSize();
	}

	public void update() {
		System.out.println("Sprite class update called, useless");
	}

	public void calculateCorners(double x, double y) {
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

		if(topTile < 0 || bottomTile >= tm.getNumRows() || leftTile < 0 || rightTile >= tm.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}

		int tl = tm.getType(topTile, leftTile);
		int tr = tm.getType(topTile, rightTile);
		int bl = tm.getType(bottomTile, leftTile);
		int br = tm.getType(bottomTile, rightTile);

		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
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

	public void setMapPosition() {
		xmap = tm.getX(); // Map pos tells us where to draw, actual location! vs
							// global!, only want to draw it if it enter the
							// screen
		ymap = tm.getY();
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
