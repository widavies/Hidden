package com.cpjd.hidden.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.HUD;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.main.GamePanel;
import com.cpjd.hidden.map.TileMap;

public class Chapter extends GameState {

	protected TileMap tileMap;

	protected Player player;
	protected List<Enemy> enemies;
	private HUD hud;
	
	public Chapter(GameStateManager gsm) {
		super(gsm);
		
		tileMap = new TileMap(64);
		tileMap.setTween(0.07);
		
		//TODO hardcoding is temporary
		enemies = new LinkedList<Enemy>();
		enemies.add(new Enemy(tileMap));
		
		hud = new HUD();
	}
	
	@Override
	public void update() {
		player.update();
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getX(), (GamePanel.HEIGHT / 2) - player.getY());
		
		player.setMapPosition();
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(player.getX(), player.getY());
			enemies.get(i).setMapPosition();
		}
		
		hud.update();
	}

	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).drawSightArc(g);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).drawSightLine(g, player.getX(), player.getY());
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g, Color.yellow);
		}
		
		player.draw(g);
		
		hud.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.INTRO);
		}
		
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
		
	}

	@Override
	public void mousePressed(int x, int y) {
		
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		
		
	}

}
