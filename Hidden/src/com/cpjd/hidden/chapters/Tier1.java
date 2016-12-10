package com.cpjd.hidden.chapters;

import java.awt.Graphics2D;

import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Console;

public class Tier1 extends Chapter {

	public Tier1(GameStateManager gsm, Console console) {
		super(gsm, console);
		
		tileMap.loadTiles("/tiles/tileset.png");
		
		int mapSize = 25;
		
		byte[][][] map = new byte[mapSize][mapSize][2];
		
		for(int x = 0; x < mapSize; x++)
			for(int y = 0; y < mapSize; y++)
				map[x][y] = new byte[] {7, 0};
		
		tileMap.setMap(map);
		player = new Player(tileMap);
		player.setPosition(0, 0);
		//tileMap.loadTiledMap("/chapter_maps/Lvl_1.txt");
		finishedGen = true;
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



