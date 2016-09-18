package com.cpjd.hidden.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.smartui.SmartField;

public class Console {
	
	private boolean open;
	private GameStateManager gsm;
	private SmartField field;
	
	public Console(GameStateManager gsm) {
		this.gsm = gsm;
		
		Rectangle rect = new Rectangle(0, 50, GamePanel.WIDTH, 50);
		
		field = new SmartField(new Font("Arial", Font.PLAIN, 15), rect, 100);
		
	}
	
	public void update() {
		if(!open) return;
		
		field.onFocus();

		field.update();
		
	}
	
	public void draw(Graphics2D g) {
		if(!open) return;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, 100);
		g.setColor(Color.BLACK);
		g.drawString("Console", 0, 35);
		field.draw(g);
	}
	
	public void keyPressed(int k) {
		if(k == 192) open = !open;
		field.keyPressed(k);
	}
}
