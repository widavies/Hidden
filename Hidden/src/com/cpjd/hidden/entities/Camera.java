package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public class Camera extends Enemy {

	public static final int NORTH = 0, WEST = 1, EAST = 2, SOUTH = 3;
	
	private int facing;
	private int tileX, tileY;
	private int drawX, drawY;
	
	//draw shape
	private int camWidth = 5, camLength = 20;
	
	public Camera(TileMap tm, int xTile, int yTile, int heading, int fov, int facing, Chapter chapter) {
		super(tm, xTile * TileMap.tileSize, yTile * TileMap.tileSize, fov, chapter);
		
		this.facing = facing;
		this.tileX = xTile;
		this.tileY = xTile;
		super.heading = heading;
		
		switch(facing){
		
		case NORTH:
			drawX = (int) (tileX * tileSize + tileSize / 2 - camWidth / 2);
			drawY = (int) (tileY * tileSize + tileSize - camLength);
			break;
		case SOUTH:
			drawX = (int) (tileX * tileSize + tileSize / 2 - camWidth / 2);
			drawY = (int) (tileY * tileSize);
			break;
		case EAST:
			drawX = (int) (tileX * tileSize);
			drawY = (int) (tileY * tileSize + tileSize / 2 - camWidth / 2);
			break;
		case WEST:
			drawX = (int) (tileX * tileSize + tileSize - camLength);
			drawY = (int) (tileY * tileSize + tileSize / 2 - camWidth / 2);
			System.out.println(drawX + ", " + drawY);
			break;
			
		default:
			System.err.println("Unrecognized camera facing in constructor in Camera");
			break;
		}
	}
	
	@Override
	public void update(){		
		
	}
	
	@Override
	public void draw(Graphics2D g){
		g.setColor(Color.ORANGE);
		
		switch(facing){
		
		case NORTH:
			g.fillRect((int) (drawX + xmap), (int) (drawY + ymap), camWidth, camLength);
			break;
		case SOUTH:
			g.fillRect((int) (drawX + xmap), (int) (drawY + ymap), camWidth, camLength);
			break;
		case EAST:
			g.fillRect((int) (drawX + xmap), (int) (drawY + ymap), camLength, camWidth);
			break;
		case WEST:
			g.fillRect((int) (drawX + xmap), (int) (drawY + ymap), camLength, camWidth);
			break;
			
		default:
			System.err.println("Unrecognized camera facing in draw(Graphics) in Camera");
			break;
		}
	}
	
	@Override
	public void drawSightArc(Graphics2D g){
		g.setColor(new Color(255, 255, 20, 100));
		g.fillArc((int) (drawX + xmap - sightRange), (int) (drawY + ymap - sightRange), sightRange * 2, sightRange * 2, (int) (-heading + 90 - fov / 2), fov);
	}

}
