package com.cpjd.hidden.chapters;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Console;

public class Ch1 extends Chapter {

	public Ch1(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		tileMap.loadTiles("/tiles/tileset.png");
		
		loadMap();
	}

	private void loadMap(){
		//TODO load the tiled map
	}
	
	public void update() {
		
		if(player != null) {
			//System.out.println(player.getX());
			tileMap.setCameraPosition(player.getX(),player.getY());
		}
	}
	
}
