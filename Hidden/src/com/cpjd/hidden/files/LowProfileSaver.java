package com.cpjd.hidden.files;

import com.cpjd.hidden.gamestate.GameState;

/**
 * Saves the game in a different thread to stop the game from freezing.
 * @author Will Davies
 *
 */
public class LowProfileSaver implements Runnable {

	private Thread thread;
	private GameState state;
	
	public LowProfileSaver(GameState state) {
		this.state = state;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		GameSave prior = IO.deserializeGameSave();
		GameSave toSave = state.getSave(prior);
		IO.serializeGameSave(toSave);
		
		try {
			thread.join();
		} catch(Exception e) {
			System.err.println("Couldn't stop game save thread");
		}
	}
	
}
