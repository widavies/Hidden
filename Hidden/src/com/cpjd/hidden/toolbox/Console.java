package com.cpjd.hidden.toolbox;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.smartui.SmartField;

public class Console {
	
	private boolean open;
	private GameStateManager gsm;
	private SmartField field;
	
	public static boolean showMemory; // A temporary thing
	
	public Console(GameStateManager gsm) {
		this.gsm = gsm;
		
		Rectangle rect = new Rectangle(0, 0, GamePanel.WIDTH, 50);
		
		field = new SmartField(new Font("Arial", Font.PLAIN, 15), rect, 100);
		
	}
	
	public void update() {
		if(!open) return;
		
		field.onFocus();

		field.update();
		
		if(field.isEnterPressed()) {
			processCommand(field.getText());
			field.setEnterPressed(false);
		}
	}
	
	public void processCommand(String s) {
		if(s.equalsIgnoreCase("stop")) System.exit(0);
		else if(s.equalsIgnoreCase("reload") || s.equalsIgnoreCase("r")) gsm.setState(gsm.getState());
		else if(s.equalsIgnoreCase("los overlay")) Enemy.drawLOSOverlay = !Enemy.drawLOSOverlay;
		else if(s.equalsIgnoreCase("pathfind overlay")) Enemy.drawPathFindOverlay = !Enemy.drawPathFindOverlay;
		else if(s.equalsIgnoreCase("menu")) gsm.setState(GameStateManager.MENU);
		else if(s.equalsIgnoreCase("mem")) showMemory = !showMemory;
		else JOptionPane.showMessageDialog(null, "Command not found.");
		
		open = false;
	}
	
	public void draw(Graphics2D g) {
		if(!open) return;
		
		field.draw(g);
	}
	
	public boolean keyPressed(int k) {
		if(k == 192) open = !open;
		if(!open) return false;
		field.keyPressed(k);
		return true;
		//returns whether key was used
	}
	
	public boolean isOpen() {
		return open;
	}
}
