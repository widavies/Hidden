package com.cpjd.hidden.chapters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.files.GameSave;
import com.cpjd.hidden.files.IO;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.genworld.GenWorld;
import com.cpjd.hidden.genworld.WorldListener;
import com.cpjd.hidden.prisons.PrisonID;
import com.cpjd.hidden.toolbox.Console;
import com.cpjd.hidden.ui.hud.PUD;
import com.cpjd.tools.Layout;

public class World extends Chapter implements WorldListener {

	private double progress;
	
	protected GenWorld world;
	
	private PUD pud;
	
	public World(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		finishedGen = false;
		
		tileMap.loadTiles("/tiles/tileset.png");
		
		pud = new PUD();
		
		if(gsm.getGameSave() == null || gsm.getGameSave().getMap() == null) {
			world = new GenWorld();
			world.addWorldListener(this);
			world.generate();
		} else {
			tileMap.setMap(gsm.getGameSave().getMap());
			player = new Player(tileMap);
			player.setPosition(gsm.getGameSave().getPlayerLocation().x, gsm.getGameSave().getPlayerLocation().y);
			tileMap.initCamera(player.getX(), player.getY());
			console.setPlayer(player);
			finishedGen = true;
		}
		
	}
	
	public void update() {
		if(finishedGen){
			super.update();
			pud.update(player.getX(), player.getY());
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		pud.draw(g, tileMap.getXOffset(), tileMap.getYOffset());
		
		if(finishedGen) return;
		
		g.setColor(Color.WHITE);
		g.setFont(GameStateManager.font.deriveFont(35f));
		//g.drawString(world.getStatus(), Layout.centerString(world.getStatus(), g), Layout.centerStringVert(g));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), 400, 40);
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), (int)(progress * 400), 40);
		
	}
	
	@Override
	public void worldGenerated() {
		tileMap.setMap(world.getWorld());
		player = new Player(tileMap);
		player.setPosition(world.getSpawn().x * tileMap.getScaledTileSize(), world.getSpawn().y * tileMap.getScaledTileSize());
		tileMap.initCamera(player.getX(), player.getY());
		console.setPlayer(player);
		finishedGen = true;
		
		GameSave save = new GameSave();
		save.setMap(world.getWorld());
		save.setPrisonLocations(world.getPrisonLocations());
		save.setPlayerLocation(new Point((int)player.getX(), (int)player.getY()));
		
		for(int i = 0; i < world.getPrisonLocations().size(); i++){
			
			for(int j = 0; j < world.getPrisonLocations().get(i).size(); j++){
				
				PrisonID id = new PrisonID(world.getPrisonLocations().get(i).get(j).x * tileMap.getScaledTileSize(), world.getPrisonLocations().get(i).get(j).y * tileMap.getScaledTileSize(), PrisonID.RANDOM_NAME, Integer.toString(i + 1), "Daniel Peterson", 500, 0);
				pud.addPrisonID(id);
				
			}
		}
		
		IO.serializeGameSave(save);
	}

	@Override
	public void updateProgress(double progress) {
		this.progress = progress;
	}
}
