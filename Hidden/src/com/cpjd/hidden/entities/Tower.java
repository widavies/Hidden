package com.cpjd.hidden.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cpjd.hidden.map.TileMap;

public class Tower extends Enemy{

	public Tower(TileMap tm) {
		super(tm, 500, 800);
	}

	@Override
	public void draw(Graphics2D g){
		super.draw(g, Color.blue);
	}
	
}
