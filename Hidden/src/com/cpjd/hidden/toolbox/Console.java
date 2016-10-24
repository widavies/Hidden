package com.cpjd.hidden.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.smartui.SmartField;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Usage;

public class Console {
	
	private boolean open;
	private GameStateManager gsm;
	private SmartField field;
	
	public static boolean showMemory; // A temporary thing
	
	private int x, y;
	
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
		GamePanel.DEBUG = open;
	}
	
	public void draw(Graphics2D g) {
		if(!open) return;
		
		field.draw(g);
		int tempx = (int)Math.abs(Player.LOCATION.x) + (x / 4) - 4;
		int tempy = (int)Math.abs(Player.LOCATION.y) + (y / 4) - 4;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(Usage.calcMemory(), 5, Layout.HEIGHT - 50);
		g.drawString("XY: " + "(" + tempx + "," + tempy + ")"+" XY: ("+(tempx / 16)+","+(tempy / 16)+")", 5, Layout.HEIGHT - 15);
	}
	
	public boolean keyPressed(int k) {
		if(k == 192) open = !open;
		GamePanel.DEBUG = open;
		if(!open) return false;
		field.keyPressed(k);
		return true;
		//returns whether key was used
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void mouseMoved(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
