package com.cpjd.hidden.entities;

import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public class Tripwire extends Enemy{

	private int tileX, tileY;
	private int drawX, drawY;
	private int drawXEnd, drawYEnd;
	private int facing;
	private int range;//in tiles

	public Tripwire(TileMap tm, Chapter chapter, int tileX, int tileY, int facing, int range) {

		super(tm, chapter);
		
		this.tileX = tileX;
		this.tileY = tileY;
		
		this.facing = facing;
		this.range = range;

		width = 10;
		height = 10;

		switch (facing) {

		case EnemyWithSight.NORTH:
			drawX = tileX * tileSize + tileSize / 2 - width / 2;
			drawY = tileY * tileSize + tileSize - height;
			drawXEnd = tileX * tileSize + tileSize / 2 - width / 2;
			drawYEnd = (tileY - range) * tileSize;
			break;
		case EnemyWithSight.SOUTH:
			drawX = tileX * tileSize + tileSize / 2 - width / 2;
			drawY = tileY * tileSize;
			drawXEnd = tileX * tileSize + tileSize / 2 - width / 2;
			drawYEnd = (tileY + range) * tileSize + tileSize - height;
			break;
		case EnemyWithSight.EAST:
			drawX = tileX * tileSize;
			drawY = tileY * tileSize + tileSize / 2 - height / 2;
			drawXEnd = (tileX + range) * tileSize + tileSize - width;
			drawYEnd = tileY * tileSize + tileSize / 2 - height / 2;
			break;
		case EnemyWithSight.WEST:
			drawX = tileX * tileSize + tileSize - width;
			drawY = tileY * tileSize + tileSize / 2 - height / 2;
			drawXEnd = (tileX - range) * tileSize;
			drawYEnd = tileY * tileSize + tileSize / 2 - height / 2;
			break;
		default:
			System.err.println("invalid facing for tripwire");
			break;

		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect((int) (drawX + xmap), (int) (drawY + ymap), width, height);
		g.drawLine((int) (drawX + width / 2 + xmap), (int) (drawY + height / 2 + ymap), (int) (drawXEnd + width / 2 + xmap), (int) (drawYEnd + height / 2 + ymap));
		g.fillRect((int) (drawXEnd + xmap), (int) (drawYEnd + ymap), width, height);
	}

	@Override
	public void update(double playerX, double playerY) {}

	@Override
	public void drawOverlays(Graphics2D g, double playerX, double playerY) {}

	@Override
	public void recievePlayerLocationMessage(double x, double y) {}
}
