package com.cpjd.hidden.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.tools.Layout;

/**
 * Logs and displays messages
 * @author Alex Harker
 *
 */
public class MessageLog {
	
	private final int LOG_WIDTH = 452, LOG_HEIGHT_PERCENT = 20;
	
	private static ArrayList<String> messages = new ArrayList<String>();
	
	/**
	 * 
	 * @param log The string that will displayed in the error log
	 */
	public static void log(String log){
		messages.add(log);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(Layout.WIDTH - LOG_WIDTH, 0, Layout.WIDTH, Layout.aligny(LOG_HEIGHT_PERCENT));
		
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		
		g.setColor(Color.BLACK);
		for(int i = messages.size() - 1, j = 0; i >= 0; i--, j++) {
			g.drawString(messages.get(i), Layout.WIDTH - LOG_WIDTH + 5, Layout.aligny(LOG_HEIGHT_PERCENT - 1) - (j * LOG_HEIGHT_PERCENT));
		}
	}
	
	public void clear(){
		messages.clear();
	}
}
