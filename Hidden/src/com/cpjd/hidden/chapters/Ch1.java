package com.cpjd.hidden.chapters;

import java.util.LinkedList;

import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.map.OpenWorld;

public class Ch1 extends Chapter {

	public Ch1(GameStateManager gsm) {
		super(gsm);
		
		tileMap.loadTiles("/tiles/tileset.png");
		//tileMap.loadTiledMap("/chapter_maps/Lv1_1.txt");
		int[][] map = new OpenWorld().generateWorld(100, 100);
		tileMap.setMap(map, 100, 100);
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		enemies = new LinkedList<Enemy>();
	}
}
