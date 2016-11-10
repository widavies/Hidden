package com.cpjd.hidden.prisons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.cpjd.hidden.gamestate.GameStateManager;

// Stores information about a particular tier of prison
public class PrisonID {
	public int x, y;
	public String name;
	public String tier;
	public String hostage;
	public int reward;
	public int timeLimit;
	
	private static final int rectWidth = 225;
	private static final int halfWidth = rectWidth / 2;
	private static final int rectHeight = 115;
	
	private static final String tierText = "Tier: ";
	private static final String hostageText = "Hostage: ";
	private static final String timeLimitText = "Time: ";
	private static final String rewardText = "Reward: ";
	private static final String moneyName = "Credits";
	
	private boolean open = false;
	private int width;
	private int height;
	private final int growSpeed = 10;
	private boolean drawing = false;
	
	public void update(){
		
		if(drawing){
		
			width += growSpeed;
			height += growSpeed;
			
			if(width > rectWidth){
				width = rectWidth;
			}
			if(height > rectHeight){
				height = rectHeight;
			}
			
			if(width == rectWidth && height == rectHeight){
				open = true;
			}
		}else{
			width = 0;
			height = 0;
			open = false;
		}
		drawing = false;
	}
	
	public void draw(Graphics2D g, double xOffset, double yOffset){
		
		int drawX = (int) (x - width / 2 - xOffset);
		int drawY = (int) (y - height / 2 - yOffset);
		
		g.setColor(Color.white);
		g.fillRoundRect(drawX, drawY, width, height, 10, 10);
		
		if(open){
		
			g.setColor(Color.black);
			g.setFont(GameStateManager.font.deriveFont(18f));
			
			FontMetrics metrics = g.getFontMetrics();
			
			g.drawString(name, drawX + halfWidth - metrics.stringWidth(name) / 2, drawY + metrics.getHeight());
			g.drawString(tierText + tier, drawX + halfWidth - metrics.stringWidth(tierText + tier) / 2, drawY + 2 * metrics.getHeight());
			g.drawString(hostageText + hostage, drawX + halfWidth - metrics.stringWidth(hostageText + hostage) / 2, drawY + 3 * metrics.getHeight());
			g.drawString(rewardText + reward + " " + moneyName, drawX + halfWidth - metrics.stringWidth(rewardText + reward + " " + moneyName) / 2, drawY + 4 * metrics.getHeight());
			g.drawString(timeLimitText + timeLimit, drawX + halfWidth - metrics.stringWidth(timeLimitText + timeLimit) / 2, drawY + 5 * metrics.getHeight());
			
		}
		
		drawing = true;
	}
}
