package com.cpjd.cascade.engine;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.tools.Animation;

public class Tile {
	public static final int NO_COLLISION = 0;
	public static final int COLLISION = 1;
	
	private BufferedImage image;
	private int id;
	
	private Animation animation;
	
	public Tile(BufferedImage image, int id) {
		this.image = image;
		this.id = id;
	}
	
	public void setAnimation(String path, short frameSize, int delay) {
		animation = new Animation();
		
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			
			BufferedImage[] images = new BufferedImage[image.getWidth() / frameSize];
			
			for(int i = 0; i < images.length; i++) {
				images[i] = image.getSubimage(frameSize * i, 0, frameSize, frameSize);
			}
			
			animation.setFrames(images);
			animation.setDelay(delay);
		} catch(Exception e) {
			System.err.println("Couldn't load animation for a tile");
		}	
	}
	
	public void update() {
		if(animation != null) animation.update();
	}
	
	public BufferedImage getImage() {
		if(animation != null) return animation.getImage();
		return image;
	}
	
	public int getId() {
		return id;
	}
	
	public void release() {
		this.image = null;
		animation.setFrames(null);
		this.animation = null;
	}
}
