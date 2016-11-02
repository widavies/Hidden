package com.cpjd.hidden.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.Map;
import com.cpjd.smartui.SmartField;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Usage;

public class Console {
	
	private boolean open;
	private GameStateManager gsm;
	private SmartField field;
	
	private String lastCommand = "";
	
	public static boolean showMemory; // A temporary thing
	
	private int x, y;
	
	public Console(GameStateManager gsm) {
		this.gsm = gsm;
		
		Rectangle rect = new Rectangle(0, 0, Layout.alignx(30), 50);
		
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
		
		else if(s.equalsIgnoreCase("reload") || s.equalsIgnoreCase("r")){
			gsm.setState(gsm.getState());
			lastCommand = "reload";
		}
		else if(s.equalsIgnoreCase("menu")){
			gsm.setState(GameStateManager.MENU);
			lastCommand = "menu";
		}
		else if(s.equalsIgnoreCase("scale")){
			Map.SCALE = 4;
			lastCommand = "scale";
		}
		else if(s.equalsIgnoreCase("unscale")){
			Map.SCALE = 1;
			lastCommand = "unscale";
		}
		else if(s.equalsIgnoreCase("mem")){
			showMemory = !showMemory;
			lastCommand = "mem";
		}
		else JOptionPane.showMessageDialog(null, "Command not found.");
		
		open = false;
	}
	
	public void draw(Graphics2D g) {
		if(open) field.draw(g);
		
		if(!GamePanel.DEBUG) return;
		int tempx = (int)Player.LOCATION.x;
		int tempy = (int)Player.LOCATION.y;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(Usage.calcMemory(), 5, Layout.HEIGHT - 50);
		g.drawString("XY: " + "(" + tempx + "," + tempy + ")"+" XY: ("+(tempx / 64)+","+(tempy / 64)+")", 5, Layout.HEIGHT - 15);
	}
	
	public boolean keyPressed(int k) {
		if(k == KeyEvent.VK_BACK_QUOTE) open = !open;
		if(k == KeyEvent.VK_UP){
			for(int i = 0; i < 50; i++) field.delete();
			
			lastCommand.toUpperCase();
			for(int i = 0; i < lastCommand.length(); i++){
				field.add(lastCommand.charAt(i));
			}
			return true;
		}
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
