package com.cpjd.hidden.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Layout;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	// Size
	public static int WIDTH = 1600;
	public static int HEIGHT = 900;
	
	// Thread
	private Thread thread;
	private volatile boolean running;
	public static int FPS = 120;
	public static long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;

	// Game State Manager
	private GameStateManager gsm;  
	
	//FPS
	public static int ticks = 0;
	private long lastSec;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		lastSec = System.nanoTime();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			requestFocus();
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		running = true;
		
		Layout.WIDTH = WIDTH;
		Layout.HEIGHT = HEIGHT;
		
		gsm = new GameStateManager();
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			{
				update();
				draw();
				drawToScreen();
				ticks++;
			}
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 0;
	/*		
			if(ticks == FPS){
				long elapsedSec = System.nanoTime() - lastSec;
				System.out.println(FPS + " frames took " + elapsedSec / 1000000000d + " seconds");
				ticks = 0;
				lastSec = System.nanoTime();
			}
		*/	
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				System.err.println("Thread failed to sleep");
			}
			
		}
	}
	
	private void update() {
		gsm.update();
	}
	
	private void draw() {
		gsm.draw(g);
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	// Resizes the window
	public void resizeGame(int width, int height) {
		WIDTH = width; HEIGHT = height;
		
		Layout.WIDTH = WIDTH;
		Layout.HEIGHT = HEIGHT;
		
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	/* Input Handlers */
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	public void keyTyped(KeyEvent key) {}
	public void mousePressed(MouseEvent mouse) { if(mouse != null&& gsm != null) gsm.mousePressed(mouse.getX(), mouse.getY());}
	public void mouseReleased(MouseEvent mouse) { if(mouse != null&& gsm != null) gsm.mouseReleased(mouse.getX(), mouse.getY());}
	public void mouseMoved(MouseEvent mouse) { if(mouse != null && gsm != null) gsm.mouseMoved(mouse.getX(),  mouse.getY());}
	public void mouseDragged(MouseEvent mouse) {}
	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}	
}
