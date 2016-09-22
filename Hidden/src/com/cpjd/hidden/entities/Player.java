package com.cpjd.hidden.entities;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import com.cpjd.hidden.map.TileMap;

public class Player extends Sprite {

	public Player(TileMap tm) {
		super(tm);
		
		width = 50;
		height = 50;
		cwidth = 50;
		cheight = 50;
		maxSpeed = 3;
		
		moveSpeed = 0.9;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_W) up = true;
		if(k == KeyEvent.VK_A) left = true;		
		if(k == KeyEvent.VK_S) down = true;
		if(k == KeyEvent.VK_D) right = true;
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_W) up = false;
		if(k == KeyEvent.VK_A) left = false;		
		if(k == KeyEvent.VK_S) down = false;
		if(k == KeyEvent.VK_D) right = false;
	}
	
	public void programmingClub() {
		for(int i = 0; i < 420; i++) {
			System.out.println("Will you join the Schaeffer programming club?");
		}
		
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		switch(result) {
		case "yes":
			System.out.println(": )");
		case "no":
			System.err.println(":(");
		}
	}
}
