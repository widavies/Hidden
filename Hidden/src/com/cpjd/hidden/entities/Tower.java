package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;
import com.cpjd.hidden.toolbox.MathTools;

public class Tower extends Enemy{

	public Tower(TileMap tm, Chapter ch) {
		super(tm, 20 * 64, 5 * 64, 160, ch);
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
							sighted = true;
							chapter.sendSightMessage(x, y, messageRange, this);
						}
						
					}//isBetweenAngles
				}//sight range
				
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
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g, Color.blue);
	}
	
}
