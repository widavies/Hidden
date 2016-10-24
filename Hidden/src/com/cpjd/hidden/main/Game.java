package com.cpjd.hidden.main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.lwjgl.openal.AL;

public class Game {
	
	public static JFrame frame;
	private static GamePanel game;
	
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
		
		frame = new JFrame("Hidden v1.0");
		
		game = new GamePanel();
		
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
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				AL.destroy();
			}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
			
		});
		
		//frame.setIconImage(new ImageIcon(Game.class.getResource("/CPJD/small-illuminati.png")).getImage());
				
		frame.setVisible(true);
	}
	
	public static void setFullscreen(boolean fullscreen) {
		if(fullscreen) {
			frame.dispose();
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			game.resizeGame((int)d.getWidth(), (int)d.getHeight());
		} else {
			frame.dispose();
			frame.setUndecorated(false);
			frame.setExtendedState(JFrame.NORMAL);
			frame.setSize(1600, 900);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		
			game.resizeGame(1600, 900);
		}
		
	}
}
