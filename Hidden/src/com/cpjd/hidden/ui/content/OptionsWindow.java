package com.cpjd.hidden.ui.content;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.main.Game;
import com.cpjd.hidden.ui.elements.UICheckbox;
import com.cpjd.hidden.ui.windows.UIWindow;
import com.cpjd.tools.Layout;

public class OptionsWindow extends UIWindow {
	
	private final static String[] TABS = {"General","Sound","Graphics","Keybinds"};
	private boolean[] ACTIVE_TAB = new boolean[TABS.length];
	private boolean[] HOVER_TAB = new boolean[TABS.length]; 
	
	private int tabWidth;
	
	// General elements
	private UICheckbox fullscreen;
	
	public OptionsWindow() {
		super();
		
		ACTIVE_TAB[0] = true;
		
		fullscreen = new UICheckbox("Fullscreen");
		fullscreen.addUIListener(this);
	}
	
	@Override
	public void update() {
		super.update();
		
		fullscreen.setLocation(Layout.alignPoints(1, x + ext, x + ext + width), Layout.alignPoints(15, y + ext, y + ext + height));
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		drawTabs(g);
		drawGeneral(g);
	}
	
	private void drawGeneral(Graphics2D g) {
		if(!ACTIVE_TAB[2]) return;
		
		fullscreen.draw(g);
	}
	
	private void drawTabs(Graphics2D g) {
		g.setFont(font.deriveFont(35f));
		metrics = g.getFontMetrics();
		
		tabWidth = 0;
		
		for(int i = 0; i < TABS.length; i++) {
			if(ACTIVE_TAB[i] || HOVER_TAB[i]) g.setColor(new Color(38, 79, 183));
			else g.setColor(Color.BLACK);
			
			if(ACTIVE_TAB[i]) g.fillRect(x + ext + 5 + tabWidth + (i * 50), Layout.alignPoints(11, y + ext, y + ext + height), metrics.stringWidth(TABS[i]), 7);
			
			g.drawString(TABS[i], x + ext + 5 + tabWidth + (i * 50), Layout.alignPoints(10, y + ext, y + ext + height));
			tabWidth += metrics.stringWidth(TABS[i]);	
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		int tabWidth = 0;
		for(int i = 0; i < TABS.length; i++) {
			if(intersects(i, x, y, tabWidth)) {
				HOVER_TAB[i] = true;
			} else HOVER_TAB[i] = false;
			if(metrics != null) tabWidth += metrics.stringWidth(TABS[i]);	
		}
		
		fullscreen.mouseMoved(x, y);
	}
	
	@Override
	public void mousePressed(int x, int y) {
		super.mousePressed(x, y);
		int tabWidth = 0;
		for(int i = 0; i < TABS.length; i++) {
			if(intersects(i, x, y, tabWidth)) {
				
				for(int j = 0; j < ACTIVE_TAB.length; j++) ACTIVE_TAB[j] = false;
				
				ACTIVE_TAB[i] = true;
			}
			if(metrics != null) tabWidth += metrics.stringWidth(TABS[i]);	 
		}
		
		fullscreen.mousePressed(x, y);
	}
	
	private boolean intersects(int index, int x, int y, int tabWidth) {
		if(metrics == null) return false;
		return x >= this.x + ext + 5 + tabWidth + (index * 50) && x <= this.x + ext + 5 + tabWidth + (index * 50) + metrics.stringWidth(TABS[index])
		&& y >= Layout.alignPoints(10, this.y + ext, this.y + ext + height) - metrics.getHeight() && y <= Layout.alignPoints(10, this.y + ext, this.y + ext + height);
	}
	
	
	@Override
	public void checkBoxPressed(UICheckbox checkBox, boolean checked) {
		Game.setFullscreen(checked);
	}
	
}
