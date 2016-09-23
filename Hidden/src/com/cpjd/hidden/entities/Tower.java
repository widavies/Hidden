package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.Chapter;
import com.cpjd.hidden.map.TileMap;

public class Tower extends Enemy{

	public Tower(TileMap tm, Chapter ch) {
		super(tm, 500, 800, 160, ch);
	}

	@Override
	public void draw(Graphics2D g){
		super.draw(g, Color.blue);
	}
	
}
