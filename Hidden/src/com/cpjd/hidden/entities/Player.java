package com.cpjd.hidden.entities;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends Sprite{

	
	//change anything in this class as need be, I dont have any long term plan with it
	
	public final double MOVESPEED = 2;
	
	//key listening
	private boolean wDown, aDown, sDown, dDown;
	
	public Player(double x, double y, int width, int height){
		super.x = x;
		super.y = y;
		super.width = width;
		super.height = height;
	}
	
	@Override
	public void update(){
		if(wDown){
			y -= MOVESPEED;
		}
		if(aDown){
			x -= MOVESPEED;
		}
		if(sDown){
			y += MOVESPEED;
		}
		if(dDown){
			x += MOVESPEED;
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W){
			wDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			aDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			sDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			dDown = true;
		}
	}
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W){
			wDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			aDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			sDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			dDown = false;
		}
	}
	public void mousePressed(MouseEvent e){
	
	}
	public void mouseReleased(MouseEvent e){
	
	}
	
}
