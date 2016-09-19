package com.cpjd.hidden.toolbox;

public class Vector {

	private double normalizedX, normalizedY;
	
	public Vector(double playerX, double playerY, int x, int y){
		
		double relativeX, relativeY;
		
		relativeX = x - playerX;
		relativeY = y - playerY;
		
		double distance = Math.sqrt((Math.abs(relativeX) * Math.abs(relativeX)) + (Math.abs(relativeY) * Math.abs(relativeY)));
		
		normalizedX = relativeX / distance;
		normalizedY = relativeY / distance;
		
	}
	
	public double getNormalizedX(){
		
		return normalizedX;
		
	}
	public double getNormalizedY(){
		
		return normalizedY;
		
	}
}
