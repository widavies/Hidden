package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.map.Map;
import com.cpjd.hidden.map.OpenWorld;
import com.cpjd.hidden.ui.hud.HUD;

public class Chapter extends GameState {

	protected Map tileMap;
	protected boolean finishedGen;
	
	protected Player player;
	private HUD hud;
	
	

	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new Map(16);
		
		hud = new HUD();
	}
	
	@Override
	public void update() {
		if(player == null || !finishedGen) return;
		
		hud.update();
		
		if(hud.isInventoryOpen()) return;
		player.update();
		//tileMap.setPosition((Layout.WIDTH / 2) - player.getX(), (Layout.HEIGHT / 2) - player.getY());
		player.setMapPosition();
		
		hud.update();
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(!finishedGen) return;
		
		
		
	}
	@Override
	public void drawGUI(Graphics2D g) {
		if(!gsm.isPaused() && finishedGen) hud.draw(g);
		
		if(!finishedGen) return;
		tileMap.draw(g);
		player.draw(g);		
	}
	@Override
	public void keyPressed(int k) {
		if(player != null) player.keyPressed(k);
		hud.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		if(player != null) player.keyReleased(k);
		
	}
	
	@Override
	public void mousePressed(int x, int y) {
		hud.mousePressed(x, y);
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		hud.mouseMoved(x, y);
	}
	
	@Override
	public void mouseWheelMoved(int k) {
		hud.mouseWheelMoved(k);
	}
}