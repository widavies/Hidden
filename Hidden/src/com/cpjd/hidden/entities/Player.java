package com.cpjd.hidden.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.hidden.map.TileMap;
import com.cpjd.tools.Animation;

public class Player extends Entity {

	// Animation
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private int currentAction;
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 2, 8};
	private BufferedImage rotation;
	private int degrees;
	
	// physics
	private double moveSpeed; // How fast the object will gain momentum
	private double maxSpeed; // The max speed the object can go
	private boolean left, right, up, down;
	
	// global x and y for the console
	public static Point LOCATION = new Point();
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 8;
		cheight = 8;
		maxSpeed =.8;
		
		moveSpeed = .6;
		
		try {
			loadAnimation();
		} catch(Exception e) {
			System.err.println("Couldn't load player animations");
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		rotation = animation.getImage();
	}
	
	private void loadAnimation() throws Exception {
		sprites = new ArrayList<BufferedImage[]>();
		BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player.png"));
		
		for (int i = 0; i < 2; i++) {
			BufferedImage[] bi = new BufferedImage[numFrames[i]];

			for (int j = 0; j < numFrames[i]; j++) {
				bi[j] = spritesheet.getSubimage(j * 32, i * 32, 32, 32);
			}
			sprites.add(bi);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(dy != 0 && dx == 0 || dx != 0 && dy == 0 || (dy == 0 && dx == 0) && (degrees % 90 == 0)) g.drawImage(rotation, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2) ,width, height, null);
		else g.drawImage(rotation, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), (int)(width * 1.25), (int)(height * 1.25), null);
	}
	
	private BufferedImage calculateRotation(BufferedImage toRotate, int degrees) {
		double rotationRequired = Math.toRadians(degrees);
		
		AffineTransform trans = new AffineTransform();
		trans.setTransform(new AffineTransform());
		if(degrees % 45 != 0) trans.setToScale(1.25, 1.25);
		double locX, locY;
		
		locX = toRotate.getWidth() / 2;
		locY = toRotate.getHeight() / 2;
		
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locX, locY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		toRotate = op.filter(toRotate, null);
		return toRotate;
	}

	@Override
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// Manage animation update
		if(left || right || down || up) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(90);
			}
		}
		else if(!left && !right && !down && !up) {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
			}
		}
		animation.update();
		
		// Manage image update
		if(left && up) degrees = 135;
		else if(right && up) degrees = -135;
		else if(left && down) degrees = 45;
		else if(right && down) degrees = -45;
		else if(left) degrees = 90;
		else if(right) degrees = -90;
		else if(up) degrees = 180;
		else if(down) degrees = 0; 	
		rotation = calculateRotation(animation.getImage(), degrees);
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
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		Player.LOCATION.x = (int)x;
		Player.LOCATION.y = (int)y;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_W) up = true;
		if(k == KeyEvent.VK_A) left = true;		
		if(k == KeyEvent.VK_S) down = true;
		if(k == KeyEvent.VK_D) right = true;
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_W) up = false;
		if(k == KeyEvent.VK_A) left = false;
		if(k == KeyEvent.VK_S) down = false;
		if(k == KeyEvent.VK_D) right = false;
	}
}
