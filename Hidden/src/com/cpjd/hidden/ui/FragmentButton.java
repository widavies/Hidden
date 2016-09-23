package com.cpjd.hidden.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FragmentButton extends Button {
	
	private boolean flip;
	
	public FragmentButton(String text) {
		super(text, null);
	}
	
	@Override
	public void draw(Graphics2D g, int x, int y) {
		FontMetrics fm = g.getFontMetrics();


		width = fm.stringWidth(text);
		this.x = x;
		this.y = y;
		
		if(flip) {
			Font font = g.getFont();
			font = font.deriveFont(30f);
			g.setFont(font);
		}
		
		height = fm.getHeight();
		
		if(fragment != null) fragment.draw(g);

		if(hover) {
			g.setColor(Color.BLACK);
			if(flip) {
				g.setColor(Color.WHITE);
				g.fillRect(x, y - height + PADDING - 20, width + (TOTAL_WIDTH - 20 - width) , height + 20);
			} else g.fillRect(x, y - height + PADDING, width + (TOTAL_WIDTH - width) , height);
		}
	 
		g.setColor(Color.BLACK);
		if(flip) g.setColor(Color.WHITE);
		if(hover) g.setColor(Color.WHITE);
		if(hover && flip) g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}
	
	public void setFlip(boolean b) {
		this.flip = b;
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		if(!flip) {
			super.mouseMoved(x, y);
			return;
		}
		
		Rectangle bounds = new Rectangle(this.x, this.y - height - 20 + PADDING, width + (TOTAL_WIDTH - 20 - width), height + 20);
		if(bounds.contains(x, y)) hover = true; 
		else hover = false;
		
		if(fragment != null) fragment.mouseMoved(x, y);
	}
	
	@Override
	public void mousePressed(int x, int y) {
		if(!flip) {
			super.mousePressed(x, y);
			return;
		}
		
		Rectangle bounds = new Rectangle(this.x, this.y - height - 20 + PADDING, width + (TOTAL_WIDTH - 20 -width), height + 20);
		if(bounds.contains(x, y) && fragment != null && !fragment.isExpanded() && !Fragment.ANY_EXPANDED) {
			fragment.setButtonSize(bounds.x, bounds.y, bounds.width, bounds.height);
			clicked = true;
		} else if(fragment == null && bounds.contains(x, y)) clicked = true;
		
		if(fragment != null) fragment.mousePressed(x, y);
	}
	
}
