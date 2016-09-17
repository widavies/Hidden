package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.toolbox.Layout;

public class LevelButton {
	
	private String text;
	private int x;
	private int y;
	private boolean hover;
	private boolean clicked;
	private Fragment fragment;
	
	public LevelButton(String text, Fragment fragment) {
		this.text = text; this.fragment = fragment;
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g, int x, int y) {
		if(!fragment.isExpanded()) return;
		FontMetrics fm = g.getFontMetrics();
		this.x = x;
		this.y = y;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 50, 50);
		g.setColor(Color.WHITE);
		if(hover) g.setColor(Color.BLACK);
		g.fillRect(x + 5, y + 5, 40, 40);
		g.setColor(Color.BLACK);
		if(hover) g.setColor(Color.WHITE);
		g.drawString(text, Layout.getStringCenter(x + 5, x + 5 + 40, text, g), y - 7 + fm.getHeight());
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public void mouseMoved(int x, int y) {
		Rectangle bounds = new Rectangle(this.x, this.y, 40, 40);
		if(bounds.contains(x, y)) hover = true;
		else hover = false;
	}
}
