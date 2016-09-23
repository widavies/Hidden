package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.Tile;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.toolbox.MathTools;
import com.cpjd.hidden.toolbox.Vector;

public class Enemy extends Sprite{

	//position and heading variables (for the current position/heading of the enemy)
	private double heading;
		
	//sight constants	
	private final int sightRange;
	private final int fov;
		
	//move/rotate to
	private double finalRotation;
	private int moveToX, moveToY;
	private Vector vecToTarget;
	private long delayStartTime;
	private boolean delayStarted = false;
		
	//constants for speed and rotation
	private final int MAXROTATION = 3;
	private final int SPEED = 1;
		
	//waypoint storage
	private List<Integer> waypoints;//stored as x, y, delay, x, y, delay, etc...
	private int currentWaypoint = 0;
	private final int WAYPOINT_SIZE = 3;	
	private boolean newWaypoint = true;	
	
	//obstacles
	private List<Point> obstacles;
	public static boolean drawLOSOverlay;
	
	//AI
	private boolean tracking = false;
	private List<Point> pathToReturn;
	private boolean returning = false;
	private int ticksReturning = 0;
	
	public Enemy(TileMap tm) {
		super(tm);
		
		obstacles = new LinkedList<Point>();
		pathToReturn = new LinkedList<Point>();
		
		for(int x = 0; x < tm.getNumRows(); x++){
			for(int y = 0;  y < tm.getNumCols(); y++){
				
				int type = tm.getType(x, y);
				if(type == Tile.BLOCKED || type == Tile.FATAL){
					
					obstacles.add(new Point(y * tileSize, x * tileSize));
				}
			}
		}
		
		waypoints = new LinkedList<Integer>();
		waypoints.add(250);
		waypoints.add(250);
		waypoints.add(0);
		waypoints.add(450);
		waypoints.add(450);
		waypoints.add(2 * 1000);
		
		int numWaypoints = waypoints.size() / WAYPOINT_SIZE;
		
		if(numWaypoints != 0){
			moveToX = waypoints.get(0);
			moveToY = waypoints.get(1);
		}
		
		sightRange = 500;
		fov = 100;
		
		x = 250;
		y = 250;
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
	
	public void drawLOSOverlay(Graphics2D g, double targetX, double targetY){
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
	}
	
	@Override
	public void update(){}
	
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
					
					moveToX = (int) targetX;
					moveToY = (int) targetY;
					
					pathToReturn.add(new Point((int) x, (int) y));
					
					newWaypoint = true;
					tracking = true;
					returning = false;
					sighted = true;
					
				}
				
			}//isBetweenAngles
		}//sight range
		
		if(returning)
			ticksReturning++;
		else
			ticksReturning = 0;
		
		if(tracking && !sighted){
			tracking = false;
			returning = true;
			
			//setting these equal triggers atTarget() to be true, so the enemy will continue to next waypoint
			moveToX = (int) x;
			moveToY = (int) y;
		}
		
		int numWaypoints = waypoints.size() / 3;
		
		if(numWaypoints == 0)
			return;
		
		if(atTarget()){
			
			if(!pathToReturn.isEmpty()){
				Point last = pathToReturn.get(pathToReturn.size() - 1);
				
				newWaypoint = true;
				moveToX = last.x;
				moveToY = last.y;
				
				pathToReturn.remove(pathToReturn.size() - 1);
				
			}else{
				returning = false;
				
				if(!delayStarted){
					if(waypoints.get(currentWaypoint * WAYPOINT_SIZE + 2) != 0){
						delayStartTime = System.nanoTime();
						delayStarted = true;
						return;
					}
				}else{
					if(System.nanoTime() - delayStartTime < waypoints.get(currentWaypoint * WAYPOINT_SIZE + 2) * 1000000d){
						return;
					}else{
						//delay finished
						delayStarted = false;
					}
					
				}		
				
				//at waypoint, delay then move to next one
				if(numWaypoints > 1){
					
					newWaypoint = true;
					currentWaypoint++;
					
					if(currentWaypoint >= numWaypoints)
						currentWaypoint = 0;
					moveToX = waypoints.get(currentWaypoint * WAYPOINT_SIZE);
					moveToY = waypoints.get(currentWaypoint * WAYPOINT_SIZE + 1);
				}else{
					return;
				}
			}
		}
		
		if(newWaypoint){
			vecToTarget = new Vector(x, y, moveToX, moveToY);
			if(!returning || (ticksReturning % (GamePanel.FPS / 4) == 0)){
				finalRotation = (int) Math.toDegrees(Math.atan2(moveToY - y, moveToX - x)) + 90;
			}
			newWaypoint = false;
			
			if(finalRotation < 0)
				finalRotation += 360;
			if(finalRotation > 360)
				finalRotation -= 360;
		}
		
		x += vecToTarget.getNormalizedX() * SPEED;
		y += vecToTarget.getNormalizedY() * SPEED;
		
		
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
		
	}

	private boolean atTarget() {
		
		final int MARGIN = 5;
		
		if(x > moveToX - MARGIN && x < moveToX + MARGIN && y > moveToY - MARGIN && y < moveToY + MARGIN)
			return true;
		return false;
	}

}