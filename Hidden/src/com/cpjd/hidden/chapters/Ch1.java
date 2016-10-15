package com.cpjd.hidden.chapters;

import java.util.LinkedList;

import com.cpjd.hidden.entities.BasicEnemy;
import com.cpjd.hidden.entities.Camera;
import com.cpjd.hidden.entities.Enemy;
import com.cpjd.hidden.entities.Player;
import com.cpjd.hidden.entities.Tower;
import com.cpjd.hidden.entities.Tripwire;
import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.gamestate.GameStateManager;

public class Ch1 extends Chapter {

	public Ch1(GameStateManager gsm) {
		super(gsm);
		
		tileMap.loadTiles("/tiles/tileset.png");
		tileMap.loadTiledMap("/chapter_maps/Lv1_1.txt");
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		enemies = new LinkedList<Enemy>();
		
	}
}
