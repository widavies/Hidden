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
	
	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
	{
	    // Find the four orientations needed for general and
	    // special cases
	    int o1 = orientation(p1, q1, p2);
	    int o2 = orientation(p1, q1, q2);
	    int o3 = orientation(p2, q2, p1);
	    int o4 = orientation(p2, q2, q1);
	 
	    // General case
	    if (o1 != o2 && o3 != o4)
	        return true;
	 
	    // Special Cases
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	    if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	 
	    // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	    if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	 
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	    if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	 
	     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	    if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	 
	    return false; // Doesn't fall in any of the above cases
	}
	
	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	public static boolean onSegment(Point p, Point q, Point r)
	{
	    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
	        q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
	       return true;
	 
	    return false;
	}
	 
	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	public static int orientation(Point p, Point q, Point r)
	{
	    // See http://www.geeksforgeeks.org/orientation-3-ordered-points/
	    // for details of below formula.
	    int val = (q.y - p.y) * (r.x - q.x) -
	              (q.x - p.x) * (r.y - q.y);
	 
	    if (val == 0) return 0;  // colinear
	 
	    return (val > 0) ? 1: 2; // clock or counterclock wise
	}
}
