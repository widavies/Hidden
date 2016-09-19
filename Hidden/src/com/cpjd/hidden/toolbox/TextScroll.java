package com.cpjd.hidden.toolbox;

import java.awt.Graphics2D;

public class TextScroll {
	
	// Display strings
	private String display = "";
	private char[] chars;
	
	// Timing
	private long start;
	private long elapsed;
	private int delay = 75; // in ms
	private int index;
	
	// Mode
	private boolean right;
	private boolean complete;
	private boolean backwards;
	
	public TextScroll(String text, boolean backwards) {
		this.backwards = backwards;
		
		chars = text.toCharArray();
		
		right = true;
		
		start = System.nanoTime();
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void update() {
		if(complete) return;
		
		elapsed = (System.nanoTime() - start) / 1000000;
		
		if(elapsed >= delay) {
			if(right) {
				display += chars[index];
				index++;	
			} else {
				display = "";
				for (int i = 0; i < index; i++) {
					display += chars[i];
				}
				index--;

			}
			if(index > chars.length - 1) {
				if(!backwards) complete = true;
				index = chars.length - 1;
				right = false;
				delay *= 2;
			} else if(index < 0) {
				index = 0;
				complete = true;
			}
			start = System.nanoTime();
		}	
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		g.drawString(display, x, y);
	}
	
}
