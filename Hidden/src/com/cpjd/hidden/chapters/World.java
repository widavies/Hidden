package com.cpjd.hidden.chapters;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.map.GenWorld;
import com.cpjd.hidden.map.WorldListener;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.tools.Layout;

public class World extends Chapter implements WorldListener {

	private double progress;
	
	protected GenWorld world;
	
	public World(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		finishedGen = false;
		
		tileMap.loadTiles("/tiles/tileset.png");
		world = new GenWorld();
		world.addWorldListener(this);
		world.generate();
	}
	
	public void update() {
		if(finishedGen) super.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if(finishedGen) return;
		
		g.setColor(Color.WHITE);
		g.setFont(GameStateManager.font.deriveFont(35f));
		g.drawString("Generating map ("+GenWorld.WIDTH+","+GenWorld.HEIGHT+")", Layout.centerString("Generating map: ("+GenWorld.WIDTH+","+GenWorld.HEIGHT+")", g), Layout.centerStringVert(g));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), 400, 40);
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), (int)(progress * 400), 40);
		
	}
	
	@Override
	public void worldGenerated() {
		tileMap.setMap(world.getWorld());
		player = new Player(tileMap);
		//player.setPosition(world.getSpawn().x * tileMap.getScaledTileSize(), world.getSpawn().y * tileMap.getScaledTileSize());
		player.setPosition(5977, 735);
		tileMap.initCamera(player.getX(), player.getY());
		console.setPlayer(player);
		finishedGen = true;
		
	}

	@Override
	public void updateProgress(double progress) {
		this.progress = progress;
	}
}
