package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.map.Tile;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.tools.Animation;

public class Sprite {

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

	// physics
	protected double moveSpeed; // How fast the object will gain momentum
	protected double maxSpeed; // The max speed the object can go
	protected boolean left, right, up, down;

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
	protected int tileSize;

	// animation
	protected Animation animation;
	
	public Sprite(TileMap tm) {
		this.tm = tm;
		this.tileSize = tm.getTileSize();
	}

	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
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

	private void getNextPosition() {

		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		if(up) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) dy = -maxSpeed;

		}
		if(down) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
		if(!left && !right) {
			dx = 0;
		}
		if(!up && !down) {
			dy = 0;
		}

	}

	public void checkTileMapCollision() {
		currCol = (int) x / tileSize; // Location in tilesize
		currRow = (int) y / tileSize;
		xdest = x + dx; // Destination position
		ydest = y + dy;

		xtemp = x; // Keep track of original x
		ytemp = y;

		calculateCorners(x, ydest); // Four cornered method - in y direction
		if(dy < 0) { // Going upwards
			if(topLeft || topRight) { // Top too corners
				dy = 0; // STop it from moving
				ytemp = currRow * tileSize + cheight / 2; // Set's us right
															// below tile we
															// bumped our head
															// into
			} else {
				ytemp += dy; // If nothing is stopping us, keep going up
			}
		}
		if(dy > 0) { // Landed on a tile
			if(bottomLeft || bottomRight) {
				dy = 0;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			} else {
				ytemp += dy; // Keep falling if there is nothing there
			}

		}

		calculateCorners(xdest, y);
		if(dx < 0) { // We are going left
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			} else {
				xtemp += dx;
			}
		}
		if(dx > 0) { // Moving to the right
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2; // Sets us just
																// to the left
			} else {
				xtemp += dx;
			}
		}
	}

	// draws the sprite
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height, null);
	}
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.fillRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height);
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
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
		return new Rectangle((int) x, (int) y, width, height);
	}

}
