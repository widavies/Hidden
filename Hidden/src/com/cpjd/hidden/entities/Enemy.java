package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.Tile;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.toolbox.MathTools;

public class Enemy extends Sprite{

	public static final int NORTH = 0, WEST = 1, EAST = 2, SOUTH = 3;
	
	//position and heading variables (for the current position/heading of the enemy)
	protected double heading;
		
	//sight constants	
	protected final int sightRange;
	protected final int fov;
		
	//move/rotate to
	protected double finalRotation;
		
	//constants for speed and rotation
	protected final int MAXROTATION = 3;
	
	//obstacles
	protected List<Point> obstacles;
	public static boolean drawLOSOverlay, drawPathFindOverlay;
	
	//broadcast player location
	protected Chapter chapter;
	protected int messageRange;
	
	protected List<Point> pathfindRoute;//in this class instead of MovingEnemy so that the drawOverlays() method can use it
	
	public Enemy(TileMap tm, double xPos, double yPos, int fov, Chapter chapter) {
		super(tm);
		
		this.chapter = chapter;
		messageRange = 200000;
		
		pathfindRoute = new LinkedList<Point>();
		obstacles = new LinkedList<Point>();
		
		for(int x = 0; x < tm.getNumRows(); x++){
			for(int y = 0;  y < tm.getNumCols(); y++){
				
				int type = tm.getType(x, y);
				if(type == Tile.BLOCKED || type == Tile.FATAL){
					
					obstacles.add(new Point(y * tileSize, x * tileSize));
				}
			}
		}
		
		
		
		sightRange = 500;
		this.fov = fov;
		
		this.x = xPos;
		this.y = yPos;
		heading = 135;
		
		width = 50;
		height = 50;
		cwidth = 50;
		cheight = 50;
		maxSpeed = 10;
		
		moveSpeed = 0.4;
	}
	
	public Enemy(TileMap tm, Chapter chapter) {
		super(tm);
		
		this.chapter = chapter;
		messageRange = 200000;
		
		pathfindRoute = new LinkedList<Point>();
		obstacles = new LinkedList<Point>();
		
		for(int x = 0; x < tm.getNumRows(); x++){
			for(int y = 0;  y < tm.getNumCols(); y++){
				
				int type = tm.getType(x, y);
				if(type == Tile.BLOCKED || type == Tile.FATAL){
					
					obstacles.add(new Point(y * tileSize, x * tileSize));
				}
			}
		}
		
		sightRange = 500;
		fov = 100;
		
		x = 19 * tileSize;
		y = 18 * tileSize;
		heading = 135;
		
		width = 50;
		height = 50;
		cwidth = 50;
		cheight = 50;
		maxSpeed = 10;
		
		moveSpeed = 0.4;
		
	}
	
	public void drawSightArc(Graphics2D g){
		g.setColor(new Color(255, 255, 20, 100));
		g.fillArc((int) (x + xmap - sightRange), (int) (y + ymap - sightRange), sightRange * 2, sightRange * 2, (int) (-heading + 90 - fov / 2), fov);
	}
	
	public void drawOverlays(Graphics2D g, double targetX, double targetY){
		
		g.setColor(Color.white);
		
		if(drawLOSOverlay){
			
			for(Point p : obstacles){
				
				Point line1_1 = new Point(p.x, p.y);
				Point line1_2 = new Point(p.x + tileSize, p.y + tileSize);
				
				g.drawLine(line1_1.x + (int) xmap, line1_1.y + (int) ymap, line1_2.x + (int) xmap, line1_2.y + (int) ymap);
				
				Point line2_1 = new Point(p.x, p.y + tileSize);
				Point line2_2 = new Point(p.x + tileSize, p.y);
				
				g.drawLine(line2_1.x + (int) xmap, line2_1.y + (int) ymap, line2_2.x + (int) xmap, line2_2.y + (int) ymap);
				
				Point sightLine_1 = new Point((int) x, (int) y);
				Point sightLine_2 = new Point((int) targetX, (int) targetY);
				
				g.drawLine(sightLine_1.x + (int) xmap, sightLine_1.y + (int) ymap, sightLine_2.x + (int) xmap, sightLine_2.y + (int) ymap);
			}
		}
		
		if(drawPathFindOverlay){
			if(pathfindRoute != null && pathfindRoute.size() > 0){
				for(int i = 0; i < pathfindRoute.size() - 1; i++)
					g.drawLine(pathfindRoute.get(i).x * tileSize + (int) xmap + tileSize / 2, pathfindRoute.get(i).y * tileSize + (int) ymap + tileSize / 2, pathfindRoute.get(i + 1).x * tileSize + (int) xmap + tileSize / 2, pathfindRoute.get(i + 1).y * tileSize + (int) ymap + tileSize / 2);
			}
		}
	}
	
	@Override
	public void update(){System.err.println("Wrong enemy update method called");}
	
	public void update(double targetX, double targetY){
		
		//AI
		boolean sighted = false;
		
		double changeX = Math.abs(targetX - x);
		double changeY = Math.abs(targetY - y);
		
		double distance = (changeX * changeX) + (changeY * changeY);
		
		if(distance <= sightRange * sightRange){
			
			double angle = Math.toDegrees(Math.atan2(Math.toRadians(x - targetX), Math.toRadians(targetY - y))) + 180;
			
			int leftMargin = (int) (heading + fov / 2);
			int rightMargin = (int) (heading - fov / 2);
			
			if(leftMargin > rightMargin){
				int temp = leftMargin;
				leftMargin = rightMargin;
				rightMargin = temp;
			}
			
			if(leftMargin < 0)
				leftMargin += 360;
			if(leftMargin > 360)
				leftMargin -= 360;
			if(rightMargin < 0)
				rightMargin += 360;
			if(rightMargin > 360)
				rightMargin -= 360;
			
			
			if(MathTools.isBetweenAngles(angle, leftMargin, rightMargin)){
		
				boolean losBlocked = false;
				
				for(Point p : obstacles){
					
					Point line1_1 = new Point(p.x, p.y);
					Point line1_2 = new Point(p.x + tileSize, p.y + tileSize);
					
					
					Point line2_1 = new Point(p.x, p.y + tileSize);
					Point line2_2 = new Point(p.x + tileSize, p.y);
					
					
					Point sightLine_1 = new Point((int) x, (int) y);
					Point sightLine_2 = new Point((int) targetX, (int) targetY);
					
					
					if(MathTools.doIntersect(sightLine_1, sightLine_2, line1_1, line1_2) || MathTools.doIntersect(sightLine_1, sightLine_2, line2_1, line2_2)){
						losBlocked = true;
						break;
					}
				}
				
				if(!losBlocked){
					sighted = true;
					chapter.sendSightMessage(x, y, messageRange, this);
				}
				
			}//isBetweenAngles
		}//sight range
		/*
		if(!sighted)
			finalRotation++;
		else{
			finalRotation = (int) Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;
		}
		
		if(finalRotation < 0)
			finalRotation += 360;
		if(finalRotation > 360)
			finalRotation -= 360;
		
		if(heading != finalRotation){
			if(heading < finalRotation) {
			    if(Math.abs(heading - finalRotation) < 180){
			    	if(Math.abs(heading - finalRotation) > MAXROTATION)
			    		heading += MAXROTATION;
			    	else
			    		heading = finalRotation;
			    }
			    else {
			    	if(Math.abs(heading - finalRotation) > MAXROTATION)
			    		heading -= MAXROTATION;
			    	else
			    		heading = finalRotation;
			    }
			}else {
			    if(Math.abs(heading - finalRotation) < 180){
			    	if(Math.abs(heading - finalRotation) > MAXROTATION)
			    		heading -= MAXROTATION;
			    	else
			    		heading = finalRotation;
			    }
			    else{
			    	if(Math.abs(heading - finalRotation) > MAXROTATION)
			    		heading += MAXROTATION;
			    	else
			    		heading = finalRotation;
			    }
			}
		}
		if(heading < 0)
			heading += 360;
		if(heading > 360)
			heading -= 360;
		*/
	}

	public void recievePlayerLocationMessage(double playerX, double playerY) {
		double changeX = Math.abs(playerX - x);
		double changeY = Math.abs(playerY - y);
		
		double distance = (changeX * changeX) + (changeY * changeY);
		
		if(distance < sightRange * sightRange){
			finalRotation = (int) Math.toDegrees(Math.atan2(playerY - y, playerX - x)) + 90;
		}
	}
}