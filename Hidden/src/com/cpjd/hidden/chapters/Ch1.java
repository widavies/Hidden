package com.cpjd.hidden.chapters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import com.cpjd.hidden.entities.Enemy;
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
		
		enemies = new LinkedList<Enemy>();
	}
	
	public void update() {
		if(finishedGen) super.update();
	}
	
	@Override
	public void drawGUI(Graphics2D g) {
		super.drawGUI(g);
		
		if(finishedGen) return;
		
		g.setColor(Color.WHITE);
		g.drawString("Generating terrain...", Layout.centerString("Generating terrain...", g), Layout.centerStringVert(g));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), 400, 40);
		g.setColor(Color.WHITE);
		g.fillRect(Layout.centerw(400), Layout.aligny(51), (int)(progress * 400), 40);
	}
	
	@Override
	public void worldGenerated() {
		tileMap.setMap(world.getWorld(), 200, 200);	
		player = new Player(tileMap);
		player.setPosition(world.getSpawn());
		finishedGen = true;
	}

	@Override
	public void updateProgress(double progress) {
		this.progress = progress;
	}
}
