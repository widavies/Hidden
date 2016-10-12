package com.cpjd.hidden.entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.hidden.map.TileMap;
import com.cpjd.tools.Animation;

public class Player extends Sprite {

	// Animation
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private int currentAction;
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 2, 8};
	private BufferedImage rotation;

	public Player(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 16;
		cheight = 16;
		maxSpeed = 0.8;
		
		moveSpeed = 0.3;
		
		try {
			loadAnimation();
		} catch(Exception e) {
			System.err.println("Couldn't load player animations");
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(500);
		
		rotation = animation.getImage();
	}
	
	private void loadAnimation() throws Exception {
		sprites = new ArrayList<BufferedImage[]>();
		BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player.png"));
		
		for (int i = 0; i < 2; i++) {
			BufferedImage[] bi = new BufferedImage[numFrames[i]];

			for (int j = 0; j < numFrames[i]; j++) {
				bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
			}
			sprites.add(bi);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(rotation, (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
	}
	
	private BufferedImage calculateRotation(BufferedImage toRotate, int degrees) {
		double rotationRequired = Math.toRadians (degrees);
		
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
		super.update();
		
		// Manage animation update
		if(left || right || down || up) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
			}
		}
		else if(!left && !right && !down && !up) {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(500);
			}
		}
		animation.update();
		
		// Manage image update
		if(left && up) rotation = calculateRotation(animation.getImage(), 135);
		else if(right && up) rotation = calculateRotation(animation.getImage(), -135);
		else if(left && down) rotation = calculateRotation(animation.getImage(), 45);
		else if(right && down) rotation = calculateRotation(animation.getImage(), -45);
		else if(left) rotation = calculateRotation(animation.getImage(), 90);
		else if(right) rotation = calculateRotation(animation.getImage(), -90);
		else if(up) rotation = calculateRotation(animation.getImage(), 180);
		else if(down) rotation = calculateRotation(animation.getImage(), 0);	
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
