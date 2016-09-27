package com.cpjd.hidden.ui.options;

import java.awt.Graphics2D;

import com.cpjd.hidden.ui.toolbox.Slider;

public class GraphicsTab {
	
	private Slider brightness;
	
	public GraphicsTab() {
		brightness = new Slider(0);
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g, int optionsx, int optionsy) {
		brightness.draw(g, optionsx + 200, optionsy + 40, 200, 20);
	}
	
	public void mouseMoved(int x, int y) {
		brightness.mouseMoved(x, y);
	}
	
	public void mousePressed(int x, int y) {
		brightness.mousePressed(x, y);
	}
	
	public void mouseReleased(int x, int y) {
		brightness.mouseReleased(x, y);
	}
	
}
