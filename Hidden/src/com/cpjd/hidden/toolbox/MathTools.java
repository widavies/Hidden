package com.cpjd.hidden.toolbox;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class MathTools {

	// Returns the list of points from p0 to p1 
	public static List<Point> BresenhamLine(Point p0, Point p1) {
	    return BresenhamLine(p0.x, p0.y, p1.x, p1.y);
	}

	// Returns the list of points from (x0, y0) to (x1, y1)
	public static List<Point> BresenhamLine(double x2, double y2, double targetX, double targetY) {
	    // Optimization: it would be preferable to calculate in
	    // advance the size of "result" and to use a fixed-size array
	    // instead of a list.
	    List<Point> result = new LinkedList<Point>();

	    boolean steep = Math.abs(targetY - y2) > Math.abs(targetX - x2);
	    
	    if (steep) {
	    	double temp = x2;
	    	x2 = y2;
	    	y2 = temp;
	    	
	    	temp = targetX;
	    	targetX = targetY;
	    	targetY = temp;
	    }
	    
	    if (x2 > targetX) {
	    	double temp = x2;
	    	x2 = targetX;
	    	targetX = temp;
	    	
	    	temp = y2;
	    	y2 = targetY;
	    	targetY = temp;
	    }

	    double deltax = targetX - x2;
	    double deltay = Math.abs(targetY - y2);
	    int error = 0;
	    int ystep;
	    double y = y2;
	    
	    if (y2 < targetY) ystep = 1;
	    else ystep = -1;
	    
	    for (double x = x2; x <= targetX; x++) {
	    	
	        if (steep) result.add(new Point((int) y, (int) x));
	        else result.add(new Point((int) x, (int) y));
	        
	        error += deltay;
	        
	        if (2 * error >= deltax) {
	            y += ystep;
	            error -= deltax;
	        }
	    }

	    return result;
	}
	
	public static boolean isBetweenAngles(double angle, int leftMargin, int rightMargin) {
		if(Math.abs(leftMargin - rightMargin) < 180){
			return angle > leftMargin && angle < rightMargin;
		}else{
			return (angle > leftMargin && angle < 360) || (angle > 0 && angle < rightMargin);
		}
	}
}
