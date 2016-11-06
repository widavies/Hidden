package com.cpjd.hidden.chapters;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Console;

public class Tier1 extends Chapter {

	public Tier1(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		tileMap.loadTiles("/tiles/tileset.png");
	}
	
}
