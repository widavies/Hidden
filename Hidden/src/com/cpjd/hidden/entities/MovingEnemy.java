package com.cpjd.hidden.entities;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.toolbox.MathTools;
import com.cpjd.hidden.toolbox.Vector;

public class MovingEnemy extends Enemy{
	
	//move
	protected int moveToX, moveToY;
	protected Vector vecToTarget;
	protected long delayStartTime;
	protected boolean delayStarted = false;	
	protected final int SPEED = 1;
	
	//waypoint storage
	protected List<Integer> waypoints;//stored as x, y, delay, x, y, delay, etc...
	protected int currentWaypoint = 0;
	protected final int WAYPOINT_SIZE = 3;	
	protected boolean newWaypoint = true;	

	//AI
	protected boolean tracking = false;
	
	public MovingEnemy(TileMap tm, Chapter ch) {
		super(tm, ch);
		
		
		waypoints = new LinkedList<Integer>();
		waypoints.add(10 * tileSize);
		waypoints.add(10 * tileSize);
		waypoints.add(0 * 1000);
		waypoints.add(8 * tileSize);
		waypoints.add(13 * tileSize);
		waypoints.add(0 * 1000);
		waypoints.add(10 * tileSize);
		waypoints.add(10 * tileSize);
		waypoints.add(0 * 1000);
		waypoints.add(19 * tileSize);
		waypoints.add(18 * tileSize);
		waypoints.add(0 * 1000);
		
		int numWaypoints = waypoints.size() / WAYPOINT_SIZE;
		
		if(numWaypoints != 0){
			moveToX = waypoints.get(0);
			moveToY = waypoints.get(1);
		}
	}
	
	@Override
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
					
					
					newWaypoint = true;
					tracking = true;
					sighted = true;
					
					super.chapter.sendSightMessage(x, y, messageRange, this);
					pathfindRoute.clear();
				}
				
			}//isBetweenAngles
		}//sight range
		
		if(tracking && !sighted){
			tracking = false;
			
			//setting these equal triggers atTarget() to be true, so the enemy will continue to next waypoint
			moveToX = (int) x;
			moveToY = (int) y;
		}
		
		int numWaypoints = waypoints.size() / 3;
		
		if(numWaypoints == 0){
			return;
		}
		
		if(atTarget()){
			if(!pathfindRoute.isEmpty()){
				while(true){
					
					Point moveToTile = pathfindRoute.get(0);
					
					if(moveToTile.x == x / tileSize && moveToTile.y == y / tileSize){
						//on this tile already
						pathfindRoute.remove(0);
						continue;
					}
					
					moveToX = moveToTile.x * tileSize + tileSize / 2;
					moveToY = moveToTile.y * tileSize + tileSize / 2;
					newWaypoint = true;
					
					delayStarted = false;
					
					pathfindRoute.remove(0);
					break;
				}
			}else{
				
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
					
					pathfindRoute = tm.pathfind(x, y, waypoints.get(currentWaypoint * WAYPOINT_SIZE), waypoints.get(currentWaypoint * WAYPOINT_SIZE + 1));
					moveToX = pathfindRoute.get(0).x * tileSize + tileSize / 2;
					moveToY = pathfindRoute.get(0).y * tileSize + tileSize / 2;
				}
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
		
		//move
		x += vecToTarget.getNormalizedX() * SPEED;
		y += vecToTarget.getNormalizedY() * SPEED;
		
		//change heading		
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

	@Override
	public void recievePlayerLocationMessage(double playerX, double playerY) {
		
		pathfindRoute = tm.pathfind(x, y, playerX, playerY);
		
	}
	
	private boolean atTarget() {
		
		final int MARGIN = 5;
		
		if(x > moveToX - MARGIN && x < moveToX + MARGIN && y > moveToY - MARGIN && y < moveToY + MARGIN)
			return true;
		return false;
	}

}
