package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.map.Map;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.hidden.ui.hud.HUD;

public class Chapter extends GameState {

	protected Map tileMap;
	protected boolean finishedGen;
	
	protected Player player;
	private HUD hud;
	
	public Chapter(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		tileMap = new Map(16);
		
		hud = new HUD();
	}
	
	@Override
	public void update() {
		if(player == null || !finishedGen) return;
		
		player.update();
		tileMap.setCameraPosition(player.getX(),player.getY());
		
		hud.update();
	}

	@Override
	public void draw(Graphics2D g) {
		if(!finishedGen) return;
	
		tileMap.draw(g);
		player.draw(g);
		
		//if(!gsm.isPaused()) hud.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		if(player != null) player.keyPressed(k);
		hud.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		if(player != null) player.keyReleased(k);
		
		if(k == KeyEvent.VK_F5) {
			GameSave save = gsm.getGameSave();
			save.setMap(tileMap.getMap());
			save.setPlayerLocation(new Point((int)player.getX(), (int)player.getY()));
			gsm.saveGame(save);
			System.out.println("Game saved!");
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		hud.mousePressed(x, y);
	}

	@Override
	public void mouseReleased(int x, int y) {}

	@Override
	public void mouseMoved(int x, int y) {
		hud.mouseMoved(x, y);
	}
	
	@Override
	public void mouseWheelMoved(int k) {
		hud.mouseWheelMoved(k);
	}
}