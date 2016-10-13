package com.cpjd.hidden.ui.elements;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.cpjd.hidden.ui.View;

public class UIButton extends View {
	
	// Constants
	private int HOVER_WIDTH; // The width of the hovering highlights
	public static final int WHITE_TEXT = 0;
	public static final int BLACK_TEXT = 1;
	
	// Attributes
	private String text;
	private int colorMode;
	
	// Technical
	private boolean hover;
	private Point original;
	private boolean visible;
	
	public UIButton(String text) {
		super();
		
		this.text = text;
		
		colorMode = WHITE_TEXT;
		HOVER_WIDTH = 400;
		
		visible = true;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public void setHoverWidth(int width) {
		this.HOVER_WIDTH = width;
	}
	public int getHoverWidth() {
		return HOVER_WIDTH;
	}
	public void draw(Graphics2D g) {
		if(!visible) return;
		
		g.setFont(font);
		metrics = g.getFontMetrics();
		if(colorMode == UIButton.WHITE_TEXT) g.setColor(Color.WHITE);
		else g.setColor(Color.BLACK);
		if(hover) {
			g.fillRect(x, y - (int)(metrics.getHeight() * .75), HOVER_WIDTH, metrics.getHeight());
			if(colorMode == UIButton.WHITE_TEXT) g.setColor(Color.BLACK);
			else g.setColor(Color.WHITE);
		}
		g.drawString(text, x, y);
	}
	public void setColorMode(int mode) {
		this.colorMode = mode;
	}
	public void mouseMoved(int x, int y) {
		hover = intersects(x, y) && focus; 
	}
	public void setOriginalLocation(int x, int y) {
		original = new Point(x, y);
	}
	public int getOriginalX() {
		return original.x;
	}
	public int getOriginalY() {
		return original.y;
	}
	public void mousePressed(int x, int y) {
		if(intersects(x, y) && focus) listener.buttonPressed(this);
	}
	public String getText() {
		return text;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = HOVER_WIDTH;
		this.height = new Canvas().getFontMetrics(font).getHeight();
	}
	
	private boolean intersects(int mousex, int mousey) {
		if(metrics == null) return false;
		return mousex >= this.x && mousex <= this.x + this.width && mousey >= this.y - (int)(metrics.getHeight() * .75) && mousey <= this.y - (int)(metrics.getHeight() * .75) + this.height;
	}

}
