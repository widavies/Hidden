package com.cpjd.hidden.chapters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.map.OpenWorld;

public class Ch1 extends Chapter {

	private boolean finishedGen;
	
	public Ch1(GameStateManager gsm) {
		super(gsm);
		
		finishedGen = false;
		
		tileMap.loadTiles("/tiles/tileset.png");
		//tileMap.loadTiledMap("/chapter_maps/Lv1_1.txt");
		world = new OpenWorld();

		enemies = new LinkedList<Enemy>();
	}
	
	public void update() {
		if(world.isFinishedGeneration() && !finishedGen) {
			tileMap.setMap(world.getWorld(), 200, 200);	
			player = new Player(tileMap);
			player.setPosition(world.getSpawn());
			finishedGen = true;
		}
		if(finishedGen) super.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(finishedGen) super.draw(g);
		else {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			if(!world.isFinishedGeneration()) g.drawString("Generating terrain: "+world.getGenerationProgress(), 5, 10);
		}
		
	}
}
