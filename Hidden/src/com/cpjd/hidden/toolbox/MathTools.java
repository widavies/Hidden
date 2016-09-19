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
	public static List<Point> BresenhamLine(int x0, int y0, int x1, int y1) {
	    // Optimization: it would be preferable to calculate in
	    // advance the size of "result" and to use a fixed-size array
	    // instead of a list.
	    List<Point> result = new LinkedList<Point>();

	    boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
	    
	    if (steep) {
	    	int temp = x0;
	    	x0 = y0;
	    	y0 = temp;
	    	
	    	temp = x1;
	    	x1 = y1;
	    	y1 = temp;
	    }
	    
	    if (x0 > x1) {
	    	int temp = x0;
	    	x0 = x1;
	    	x1 = temp;
	    	
	    	temp = y0;
	    	y0 = y1;
	    	y1 = temp;
	    }

	    int deltax = x1 - x0;
	    int deltay = Math.abs(y1 - y0);
	    int error = 0;
	    int ystep;
	    int y = y0;
	    
	    if (y0 < y1) ystep = 1;
	    else ystep = -1;
	    
	    for (int x = x0; x <= x1; x++) {
	    	
	        if (steep) result.add(new Point(y, x));
	        else result.add(new Point(x, y));
	        
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
