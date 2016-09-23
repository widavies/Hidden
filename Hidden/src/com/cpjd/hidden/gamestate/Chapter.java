package com.cpjd.hidden.gamestate;

import java.awt.Graphics2D;
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
		
		hud = new HUD();
	}
	
	@Override
	public void update() {

		hud.update();
		
		if(hud.isInventoryOpen()) return;
		
		player.update();
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getX(), (GamePanel.HEIGHT / 2) - player.getY());
		
		player.setMapPosition();
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(player.getX(), player.getY());
			enemies.get(i).setMapPosition();
		}
		hud.update();
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getCollisionBox().intersects(player.getCollisionBox())){
				gsm.setState(gsm.getState());
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		
		tileMap.draw(g);
		
		
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).drawSightArc(g);
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		player.draw(g);
		
		
		hud.draw(g);
		
		if(Enemy.drawLOSOverlay){
			enemies.get(0).drawLOSOverlay(g, player.getX(), player.getY());
		}
		
		
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		hud.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
		
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

	public void sendSightMessage(double x, double y, int messageRange) {
		
		double distance;
		double changeX;
		double changeY;
		
		for(int i = 0; i < enemies.size(); i++){
			changeX = enemies.get(i).getX() - x;
			changeY = enemies.get(i).getY() - y;
			distance = changeX * changeX + changeY * changeY;
			
			
			if(distance <= messageRange * messageRange){
				enemies.get(i).recievePlayerLocationMessage(player.getX(), player.getY());
			}
		}
	}

}