package com.cpjd.hidden.map;

import java.awt.image.BufferedImage;

public class Tile {

	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	public static final int FATAL = 2;
	public static final int ITEM = 3;
	
	private BufferedImage image;
	private int type;
	private int id;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
}
