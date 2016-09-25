package com.cpjd.hidden.ui.mainmenu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Button {
	
 final int PADDING = 12;
	protected final int TOTAL_WIDTH = 400;
	
	protected String text;
	protected int x, y, width, height;
	protected boolean hover;
	protected boolean clicked;
	protected Fragment fragment;
	
	public Button(String text, Fragment fragment) {
		this.text = text; this.fragment = fragment;
	}
	
	public void update() {
		if(fragment != null) fragment.update();
		
		if(clicked) {
			if(fragment != null) fragment.expand();
			hover = false;
		} 
		
		if(fragment != null && fragment.isExiting()) {
			fragment.close();
			if(!fragment.isExpanded()) {
				fragment.setExiting(false);
				fragment.setButtonSize(0, 0, 0, 0);
			} else {
				clicked = false;
			}
		}
	}
	
	public void draw(Graphics2D g, int x, int y) {
		FontMetrics fm = g.getFontMetrics();

		height = fm.getHeight();
		width = fm.stringWidth(text);
		this.x = x;
		this.y = y;
		
		if(fragment != null) fragment.draw(g);

		if(hover && !Fragment.ANY_EXPANDED) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y - height + PADDING, width + (TOTAL_WIDTH - width) , height);
		}
	 
		g.setColor(Color.WHITE);
		if(hover && !Fragment.ANY_EXPANDED) {
			g.setColor(Color.BLACK);
		}
		
		if(!Fragment.ANY_EXPANDED) g.drawString(text, x, y);
	}
	
	public void mouseMoved(int x, int y) {
		Rectangle bounds = new Rectangle(this.x, this.y - height + PADDING, width + (TOTAL_WIDTH - width), height);
		if(bounds.contains(x, y)) hover = true; 
		else hover = false;
		
		if(fragment != null) fragment.mouseMoved(x, y);
	}
	
	public void mousePressed(int x, int y) {
		Rectangle bounds = new Rectangle(this.x, this.y - height + PADDING, width + (TOTAL_WIDTH - width), height);
		if(bounds.contains(x, y) && fragment != null && !fragment.isExpanded() && !Fragment.ANY_EXPANDED) {
			fragment.setButtonSize(bounds.x, bounds.y, bounds.width, bounds.height);
			clicked = true;
		} else if(fragment == null && bounds.contains(x, y)) clicked = true;
		
		if(fragment != null) fragment.mousePressed(x, y);
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
}
