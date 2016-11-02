package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.Map;
import com.cpjd.hidden.map.Tile;
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
	
	// Physics
	private double moveSpeed; // How fast the object will gain momentum
	private double maxSpeed; // The max speed the object can go
	private boolean left, right, up, down;
	private double borderLeftx, borderRightx, borderUpy, borderDowny, adjustx, adjusty;
	
	// global x and y for the console
	public static Point LOCATION = new Point();
	
	public Player(Map tm) {
		super(tm);
		
		width = 128;
		height = 128;
		cwidth = 128;
		cheight = 32;
		maxSpeed = 3.8;
		
		moveSpeed = 2.4;
		
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
		if(dy != 0 && dx == 0 || dx != 0 && dy == 0 || (dy == 0 && dx == 0) && (degrees % 90 == 0)) g.drawImage(rotation, (int)((GamePanel.WIDTH / 2) - width / 2 - adjustx), (int)((GamePanel.HEIGHT  / 2) - height / 2 - adjusty),width, height, null);
		else g.drawImage(rotation, (int)(((GamePanel.WIDTH / 2) - width / 2) - adjustx), (int)((GamePanel.HEIGHT  / 2) - height / 2 -adjusty),(int)(width * 1.25), (int)(height * 1.25), null);
		
		// Draw the collision bounds
		if(GamePanel.DEBUG) {
			g.setColor(Color.RED);
			
			if(dy != 0 && dx == 0 || dx != 0 && dy == 0 || (dy == 0 && dx == 0) && (degrees % 90 == 0)) g.drawRect((int)((GamePanel.WIDTH / 2) - width / 2 - adjustx), (int)((GamePanel.HEIGHT  / 2) - height / 2 - adjusty),cwidth, cheight);
			else g.drawRect((int)(((GamePanel.WIDTH / 2) - width / 2) - adjustx), (int)((GamePanel.HEIGHT  / 2) - height / 2 -adjusty),cwidth, cheight);
			
		}
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
		manageMovement();
		
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
		
		resizeCollisionBox();
		
		//System.out.println("X: "+x+" Y: "+y);
	}
	
	private void resizeCollisionBox() {
		cwidth = 32;
		cheight = 32;
	}
	
	private void manageMovement() {
		double oldMoveSpeed = moveSpeed;
		
		if((left && up) || (left && down) || (right && up) || (right && down)){
			moveSpeed *= .7;
		}
		
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
		
		moveSpeed = oldMoveSpeed;
		
		xtemp = x;
		ytemp = y;
		
		if(dy < 0) {
			ytemp += dy;
		}
		if(dy > 0) {
			ytemp += dy;
		}
		if(dx < 0) {
			xtemp += dx;
		}
		if(dx > 0) {
			xtemp += dx;
		}
		
		// Manage collision
		if(tm.getTileType(xtemp, ytemp) == Tile.COLLISION || tm.getTileType(xtemp + cwidth, ytemp) == Tile.COLLISION
				|| tm.getTileType(xtemp +cwidth, ytemp + cheight) == Tile.COLLISION || tm.getTileType(xtemp, ytemp + cheight) == Tile.COLLISION ) {
			System.out.println("L,U");
			if(dx != 0) xtemp = x;
			if(dy != 0) ytemp = y;
		}

		setPosition(xtemp, ytemp);
		
		// X borders
		borderLeftx = (GamePanel.WIDTH / 2) - x;
		borderRightx = (tm.getWidth() - (GamePanel.WIDTH / 2) - x);
		if(borderLeftx >= 0) adjustx = borderLeftx;
		else if(borderRightx <= 0) adjustx = borderRightx;
		else adjustx = 0;
		
		// Y borders		
		borderUpy = (GamePanel.HEIGHT / 2) - y;
		borderDowny = (tm.getHeight() - (GamePanel.HEIGHT / 2) - y);
		if(borderUpy >= 0) adjusty = borderUpy;
		else if(borderDowny <= 0) adjusty = borderDowny;
		else adjusty = 0;
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
