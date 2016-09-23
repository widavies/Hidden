package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public class BasicEnemy extends MovingEnemy{

	public BasicEnemy(TileMap tm, Chapter ch) {
		super(tm, ch);
	}

	@Override
	public void draw(Graphics2D g){
		super.draw(g, Color.yellow);
	}
}
