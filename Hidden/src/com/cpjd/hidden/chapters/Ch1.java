package com.cpjd.hidden.chapters;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.map.OpenWorld;
import com.cpjd.hidden.map.WorldListener;
import com.cpjd.tools.Layout;

public class Ch1 extends Chapter implements WorldListener {

	private double progress;
	
	public Ch1(GameStateManager gsm) {
		super(gsm);
		
		finishedGen = false;
		
		tileMap.loadTiles("/tiles/tileset.png");
		world = new OpenWorld();
		world.addWorldListener(this);
		world.generate();
	}
	
	public void update() {
		if(finishedGen) super.update();
	}
	
	@Override
	public void drawGUI(Graphics2D g) {
		super.drawGUI(g);
		
		if(finishedGen) return;
		
		g.setColor(Color.WHITE);
		g.setFont(GameStateManager.font.deriveFont(35f));
		g.drawString("Generating map ("+OpenWorld.WIDTH+","+OpenWorld.HEIGHT+")", Layout.centerString("Generating map: ("+OpenWorld.WIDTH+","+OpenWorld.HEIGHT+")", g), Layout.centerStringVert(g));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), 400, 40);
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), (int)(progress * 400), 40);
	}
	
	@Override
	public void worldGenerated() {
		tileMap.setMap(world.getWorld());
		//player = new Player(tileMap);
		//player.setPosition(world.getSpawn());
		finishedGen = true;
	}

	@Override
	public void updateProgress(double progress) {
		this.progress = progress;
	}
}
