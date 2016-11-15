package com.cpjd.hidden.chapters;

import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Console;

public class Tier1 extends Chapter {

	public Tier1(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		tileMap.loadTiles("/tiles/tileset.png");
		tileMap.loadTiledMap("/chapter_maps/Lvl_1.txt");
	}
	
	@Override
	public void update(){
		super.update();
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
	}
}
