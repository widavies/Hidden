package com.cpjd.hide.main;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class Game {
	
	public static JFrame frame;
	
	public static void main(String[] args) {
		frame = new JFrame("Hidden");
		
		GamePanel game = new GamePanel();
		
		frame.setLayout(null);
		frame.setContentPane(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setLocation(new Point(2100, 50));
		
		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				game.resizeGame(frame.getWidth(), frame.getHeight());
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		//frame.setIconImage(new ImageIcon(Game.class.getResource("/CPJD/small-illuminati.png")).getImage());
				
		frame.setVisible(true);
		
	}
	
	
}
