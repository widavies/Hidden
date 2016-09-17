package com.cpjd.hidden.toolbox;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/** An utility class for positioning GUI or other elements on the screen
 * Cats PJs Dev
 * 
 */
public class Layout {

	// The dimensions of the screen size, must be set at application startup to use
	public static int WIDTH;
	public static int HEIGHT;
	
	/**
	 * Returns the x-value required to center the object horizontally
	 * based off the inputed width.
	 * 
	 * @return
	 */
	public static int centerw(int width) {
		return (WIDTH / 2) - (width / 2);
	}
	
	/**
	 * Returns the y-value required to center the object vertically
	 * based off the inputed height.
	 * 
	 * @param height
	 * @return
	 */
	public static int centerh(int height) {
		return (HEIGHT / 2) - (height / 2);
	}
	
	/**
	 * Returns the x-value that is the midpoint
	 * between the two inputed points. 
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int getCenter(int p1, int p2) {
		return p1 + (p2 - p1) / 2;
	}
	
	/**
	 * Returns the center of an object (with width) between two points.
	 * 
	 * @param p1
	 * @param p2
	 * @param width
	 * @return
	 */
	public static int getObjectCenter(int p1, int p2, int width) {
		return (p1 + (p2 - p1) / 2) - (width / 2);
	}
	
	/**
	 * Returns the x-value of the specified screen percentage. Provides
	 * a consistent experience across platforms
	 * 
	 * @param percent
	 * @return
	 */
	public static int alignx(double percent) {
		percent = percent * 0.01;
		return (int)(WIDTH * percent);
	}
	
	/**
	 * Returns the y-value of the specified screen percentage. Provides
	 * a consistent experience across platforms
	 * 
	 * @param percent
	 * @return
	 */
	public static int aligny(double percent) {
		percent = percent * 0.01;
		return(int)(HEIGHT * percent);
	}
	
	/**
	 * Returns the x-value of the specified percentage between two points. Provides
	 * a consistent experience across platforms
	 * 
	 * @param percent
	 * @return
	 */
	public static int alignPoints(double percent, int p1, int p2) {
		percent = percent * 0.01;
		double result = (p2 - p1) * percent;
		return (int)(result + p1);
	}
	
	/** 
	 * Creates a rectangle based off the four standard traits.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static Rectangle getHitBox(int x, int y, int width, int height) {
		return new Rectangle(x,y,width,height);
	}
	
	/**
	 * Returns the pixel width of the inputed string.
	 * 
	 * @param g
	 * @param s
	 * @return
	 */
	public static int getStringWidth(Graphics2D g, String s) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		return metrics.stringWidth(s);
	}
	
	/**
	 * Returns the pixel height of the current active font.
	 * 
	 * @param g
	 * @return
	 */
	public static int getStringHeight(Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics();
		return metrics.getHeight();
	}
	
	/**
	 * Centers a string between two points.
	 * 
	 * @param p1
	 * @param p2
	 * @param str
	 * @param g
	 * @return
	 */
	public static int getStringCenter(int p1, int p2, String str, Graphics2D g) {
		int center = p1 + ((p2 - p1) / 2);
		FontMetrics metrics = g.getFontMetrics();
		return center - (metrics.stringWidth(str) / 2);
	}
	
	/**
	 * Centers the string horizontally on the screen.
	 * 
	 * @param text
	 * @param g
	 * @return
	 */
	public static int centerString(String text, Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int center = WIDTH - (metrics.stringWidth(text) / 2);
		return center;
	}
	
	/**
	 * Centers the string vertically on the screen.
	 * @param g
	 * @return
	 */
	public static int centerStringVert(Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int center = HEIGHT - (metrics.getHeight() / 2);
		return center;
	}
}
