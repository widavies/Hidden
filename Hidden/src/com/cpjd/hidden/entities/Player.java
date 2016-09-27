package com.cpjd.hidden.entities;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 32;
		cheight = 32;
		maxSpeed = 3;
		
		moveSpeed = 0.9;
		
		try {
			loadAnimation();
		} catch(Exception e) {
			System.err.println("Couldn't load player animations");
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(500);
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
	public void update() {
		super.update();
		
		// Manage animation update
		if(left || right || down || up) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
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
