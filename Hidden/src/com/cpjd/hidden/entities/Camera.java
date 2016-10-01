package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public class Camera extends Enemy {

	public static final int NORTH = 0, WEST = 1, EAST = 2, SOUTH = 3;
	
	private int facing;
	private int tileX, tileY;
	
	//draw shape
	private int camWidth = 5, camLength = 20;
	
	public Camera(TileMap tm, double xPos, double yPos, int fov, int facing, Chapter chapter) {
		super(tm, xPos, yPos, fov, chapter);
		
		this.facing = facing;
		tileX = (int) (xPos / tileSize);
		tileY = (int) (yPos / tileSize);
	}
	
	@Override
	public void update(){		
		super.update();
	}
	
	@Override
	public void draw(Graphics2D g){
		g.setColor(Color.ORANGE);
		
		switch(facing){
		
		case NORTH:
			g.fillRect((int) (tileX * tileSize + tileSize / 2 - camWidth / 2 + xmap), (int) (tileY * tileSize + tileSize - camLength + ymap), camWidth, camLength);
			break;
		case SOUTH:
			System.out.println((int) (tileX * tileSize + tileSize / 2 - camWidth / 2 + xmap) + ". " +  (int) (tileY * tileSize + ymap));
			g.fillRect((int) (tileX * tileSize + tileSize / 2 - camWidth / 2 + xmap), (int) (tileY * tileSize + ymap), camWidth, camLength);
			break;
		case EAST:
			g.fillRect((int) (tileX * tileSize + xmap), (int) (tileY * tileSize + tileSize / 2 - camWidth / 2 + ymap), camLength, camWidth);
			break;
		case WEST:
			g.fillRect((int) (tileX * tileSize + tileSize - camLength + xmap), (int) (tileY * tileSize + tileSize / 2 - camWidth / 2 + ymap), camLength, camWidth);
			break;
			
		default:
			System.err.println("Unrecognized camera facing");
			break;
		}
	}

}
