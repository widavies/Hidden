package com.cpjd.hidden.entities;

import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public abstract class Enemy extends Sprite{
	
	public static final int NORTH = 0, WEST = 1, EAST = 2, SOUTH = 3;
	
	public static boolean drawLOSOverlay, drawPathFindOverlay;
	
	//broadcast player location
	protected Chapter chapter;
	protected int messageRange;
	
	public Enemy(TileMap tm, Chapter chapter){
		super(tm);
		
		this.chapter = chapter;
	}
	
	public abstract void update(double playerX, double playerY);
	public abstract void drawOverlays(Graphics2D g, double playerX, double playerY);
	public abstract void recievePlayerLocationMessage(double x, double y);
	
}
