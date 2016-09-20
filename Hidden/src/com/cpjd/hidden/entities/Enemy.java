package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.toolbox.MathTools;
import com.cpjd.hidden.toolbox.Vector;

public class Enemy extends Sprite{

	//position and heading variables (for the current position/heading of the enemy)
	private double x, y;
	private double heading;
		
	//sight constants	
	private final int sightRange;
	private final int fov;
		
	//move/rotate to
	private double finalRotation;
	private int moveToX, moveToY;
	private Vector vecToTarget;
		
	//constants for speed and rotation
	private final int MAXROTATION = 5;
	private final int SPEED = 2;
		
	//waypoint storage
	private List<Integer> waypoints;//stored as x, y, delay, x, y, delay, etc...
	private int currentWaypoint = 0;
	private final int WAYPOINT_SIZE = 3;	
	private boolean newWaypoint = true;	
	
	public Enemy(TileMap tm) {
		super(tm);
		
		waypoints = new LinkedList<Integer>();
		waypoints.add(500);
		waypoints.add(500);
		waypoints.add(0);
		waypoints.add(50);
		waypoints.add(50);
		waypoints.add(0);
		
		
		int numWaypoints = waypoints.size() / WAYPOINT_SIZE;
		
		if(numWaypoints != 0){
			moveToX = waypoints.get(0);
			moveToY = waypoints.get(1);
		}
		
		sightRange = 100;
		fov = 100;
		
		width = 50;
		height = 50;
		cwidth = 50;
		cheight = 50;
		maxSpeed = 10;
		
		moveSpeed = 0.4;
	}
	
	public void drawSightArc(Graphics g){
		g.setColor(new Color(255, 255, 20, 100));
		g.fillArc((int) (x - sightRange), (int) (y - sightRange), sightRange * 2, sightRange * 2, (int) (-heading + 90 - fov / 2), fov);
	}
	
	public void drawSightLine(Graphics g, int targetX, int targetY){
		
		double changeX = Math.abs(targetX - x);
		double changeY = Math.abs(targetY - y);
		
		double distance = Math.sqrt((changeX * changeX) + (changeY * changeY));
		
		if(distance <= sightRange){
			
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
				g.setColor(Color.blue);
				
				List<Point> line = MathTools.BresenhamLine((int) x, (int) y, targetX, targetY);
				/*
				for(Obstacle o : obstacles){
					for(int i = line.size() - 1; i >= 0; i--){
						Point p = line.get(i);
						
						if(o.contains(p)){
							g.setColor(Color.red);
						}
						
						g.drawLine(p.x, p.y, p.x, p.y);
						
					}
				}
				*/
			}
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.yellow);
		g.fillRect((int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
				width, height);
	}
	
	@Override
	public void update(){
		
		int numWaypoints = waypoints.size() / 3;
		
		if(numWaypoints == 0)
			return;
		
		if(atTarget()){
			
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
		
		if(newWaypoint){
			vecToTarget = new Vector(x, y, moveToX, moveToY);
			finalRotation = (int) Math.toDegrees(Math.atan2(moveToY - y, moveToX - x)) + 90;
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
