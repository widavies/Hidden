package com.cpjd.hidden.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.smartui.SmartField;
import com.cpjd.tools.Layout;
import com.cpjd.tools.Usage;

public class Console {
	
	// Passed in stuff
	private GameStateManager gsm;
	private Player player;
	
	private boolean open;
	private SmartField field;
	
	private String lastCommand = "";
	
	private ArrayList<String> output;
	
	private final String[] HELP = {
		"Command, Description, Usage",
		"stop - force close the program",
		"reload - reload the current gamestate",
		"clear - remove all messages from the console"
	};
	
	public Console(GameStateManager gsm) {
		this.gsm = gsm;
		
		Rectangle rect = new Rectangle(0, Layout.aligny(20), 450, 30);
		
		field = new SmartField(new Font("Arial", Font.PLAIN, 15), rect, 100);
		field.setBlinkSpeed(40);
		
		output = new ArrayList<String>();
	}
	
	public void update() {
		if(!open) return;
		
		field.onFocus();

		field.update();
		
		if(field.isEnterPressed()) {
			try {
				processCommand(field.getText());
			} catch(Exception e) {
				output.add("Incorrect command syntax. Use <command> help for usage");
			}
			field.setEnterPressed(false);
		}
	}
	
	public void processCommand(String s) throws Exception {
		String[] tokens = s.split("\\s+");
		
		// Basic commands
		if (tokens[0].toLowerCase().equals("stop")){
			output.add("Force stopping game");
			System.exit(0);
			return;
		}
		else if (tokens[0].toLowerCase().equals("r")){
			gsm.setState(gsm.getState());
			output.add("Gamestate reloaded.");
			open = false;
			return;
		}
		else if (tokens[0].toLowerCase().equals("clear")){
			output.clear();
			return;
		}
		else if (tokens[0].toLowerCase().equals("help")){
			for(int i = 0; i < HELP.length; i++) {
				output.add(HELP[i]);
			}
			return;
		}
		else{
			output.add("Unrecognized command. Type help for list of commands");
		}
	}
	
	public void draw(Graphics2D g) {
		if(open) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 452, Layout.aligny(20));
			
			field.draw(g);
			
			// Draw items
			g.setColor(Color.BLACK);
			for(int i = output.size() - 1, j = 0; i >= 0; i--, j++) {
				g.drawString(output.get(i), 5, Layout.aligny(19) - (j * 20));
			}
			
			
			
		}
		
		if(!GamePanel.DEBUG) return;
		int tempx = (int)player.getX();
		int tempy = (int)player.getY();
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
	
	public void mousePressed(int x, int y) {
		//if(GamePanel.DEBUG && player != null) player.setPosition(player.getX() - (GamePanel.WIDTH / 2), player.getY() - (GamePanel.HEIGHT / 2) - y);
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
}
