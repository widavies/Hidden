package com.cpjd.hidden.ui.toolbox;

import java.awt.Color;
import java.awt.Graphics2D;

import javafx.scene.shape.Ellipse;

public class Slider {
	
	private int current;
	private boolean hover;
	private int x, y, width;
	private boolean drag;
	private int dragx;
	
	public Slider(int percent) {
		
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		this.x = x; this.y = y; this.width = width;
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(x, y, width, height, 30, 30);
		if(!hover) g.setColor(new Color(24, 105, 133));
		else g.setColor(Color.MAGENTA);
		g.fillOval(x + (current / 100) * width, y - 5, 30, 30);
	}
	
	public void mouseMoved(int x, int y) {
		Ellipse el = new Ellipse(this.x + (current / 100) * width + 2, this.y + 2, 28, 28);
		if(el.contains(x, y)) hover = true;
		else hover = false;
		
		// Calculate drag
		if(drag) {
			int motion = Math.abs(dragx - x);
			current = (width - (width - motion)) / width;
		}
		
	}
	
	public void mousePressed(int x, int y) {
		Ellipse el = new Ellipse(this.x + (current / 100) * width + 2, this.y + 2, 28, 28);
		if(el.contains(x, y)) {
			drag = true;
			this.dragx = x;
		}
	}
	
	public void mouseReleased(int x, int y) {
		drag = false;
	}
	
}
