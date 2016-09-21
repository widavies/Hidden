package com.cpjd.hidden.main;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Game {
	
	public static JFrame frame;
	
	public static void main(String[] args) {
		
		//mac stuff, makes it look pretty
		if(System.getProperty("os.name").contains("OS X")){
			System.setProperty("com.apple.mrj.application.apple.menu.about.name","Hidden");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		frame = new JFrame("Hidden");
		
		final GamePanel game = new GamePanel();
		
		frame.setLayout(null);
		frame.setContentPane(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setLocation(new Point(2100, 50));
		
		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				game.resizeGame(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
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
