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
	
	private final int THRESHOLD = 3; // The time, in ticks * the index of how long to wait for it to appear
	private int current = THRESHOLD;
	
	private boolean completed;
	
	public LevelButton(String text, Fragment fragment, boolean completed) {
		this.text = text; this.fragment = fragment;
		this.completed = completed;
	}
	
	public void update() {
		current--;
		if(current < 0) current = 0;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		if(!fragment.isExpanded() || current > 0) return;
		FontMetrics fm = g.getFontMetrics();
		this.x = x;
		this.y = y;
		
		if(completed) g.setColor(Color.GREEN);
		if(!completed) g.setColor(Color.RED);
		g.fillRect(x, y, 50, 50);
		g.setColor(Color.WHITE);
		if(hover && completed) g.setColor(Color.BLACK);
		g.fillRect(x + 5, y + 5, 42, 40);
		g.setColor(Color.BLACK);
		if(hover && completed) g.setColor(Color.WHITE);
		g.drawString(text, Layout.getStringCenter(x + 5, x + 5 + 40, text, g), y - 7 + fm.getHeight());
	}
	
	public void reset(int index) {
		current = THRESHOLD * index;
	}
	
	public void setIndex(int index) {
		current = current * index;
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
	public void mousePressed(int x, int y) {
		Rectangle bounds = new Rectangle(this.x, this.y, 40, 40);
		if(bounds.contains(x, y)) clicked = true;
	}
}
