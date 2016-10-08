package com.cpjd.hidden.ui.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cpjd.hidden.ui.UIListener;
import com.cpjd.hidden.ui.View;
import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.hidden.ui.elements.UICheckbox;
import com.cpjd.tools.Layout;

public abstract class UIWindow extends View implements UIListener {
	
	protected int animationSpeed;
	protected int ext;
	protected boolean hover;
	protected boolean closing;
	
	private double orgWidth, orgHeight;
	
	protected ArrayList<UIButton> buttons;
	
	public UIWindow() {
		super();	
		
		ext = 100;
		animationSpeed = 5;
		
		buttons = new ArrayList<UIButton>();
	}
	
	public void update() {
		if(!closing) ext-=animationSpeed;
		if(closing) ext+=animationSpeed;
		if(ext < 0) ext = 0;
		if(ext >= 100) listener.viewClosed(this);
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setLocation(this.x + buttons.get(i).getOriginalX() + ext, this.y + buttons.get(i).getOriginalY() + ext);
			if(closing && ext >= 50) {
				buttons.get(i).setVisible(false);
			}
		}
		
		x = Layout.centerw((int)(Layout.WIDTH / orgWidth));
		y = Layout.centerh((int)(Layout.HEIGHT / orgHeight));
		width = (int)(Layout.WIDTH / orgWidth);
		height = (int)(Layout.HEIGHT / orgHeight);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + ext, y + ext, width - 2*ext, height - 2*ext);
		
		g.setColor(Color.RED);
		if(hover) g.setColor(Color.BLACK);
		g.fillRoundRect(x - ext + width - 50, y + ext + 10, 40, 40, 8, 8);
		g.setColor(Color.BLACK);
		if(hover) g.setColor(Color.RED);
		g.fillRoundRect(x - ext + width - 45, y + ext + 15, 30, 30, 8, 8);
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g);
		}
	}
	
	public void addButton(String text, int x, int y, int hoverWidth, boolean hiddenUntilOpen) {
		UIButton button = new UIButton(text);
		button.setOriginalLocation(x, y);
		button.setColorMode(UIButton.BLACK_TEXT);
		button.addUIListener(this);
		button.setHoverWidth(hoverWidth);
		buttons.add(button);
		button.setVisible(!hiddenUntilOpen);
	}
	
	public void mouseMoved(int x, int y) {
		hover = intersects(x, y);
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mouseMoved(x, y);
		}
	}
	
	public void mousePressed(int x, int y) {
		closing = intersects(x, y);
		if((x < this.x + ext || x > this.x + ext + width - 2*ext || y < this.y + ext || y > this.y + ext + height - 2*ext) && ext <= 0) closing = true;
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).mousePressed(x, y);
		}
	}
	
	public void center(double width, double height) {
		this.x = Layout.centerw((int)width);
		this.y = Layout.centerh((int)height);
		this.width = (int)width;
		this.height = (int)height;
		
		orgWidth = Layout.WIDTH / width;
		orgHeight = Layout.HEIGHT / height;
	}
	public void setAnimationSpeed(int speed) {
		this.animationSpeed = speed;
	}
	private boolean intersects(int mousex, int mousey) {
		return mousex >= this.x + ext + width - 50 && mousex <= this.x + ext + width - 50 + 40 && mousey >= this.y + ext + 10 && mousey <= this.y + ext + 10 + 40;
	}

	@Override
	public void buttonPressed(UIButton button) {}
	@Override
	public void viewClosed(UIWindow window) {}
	@Override
	public void checkBoxPressed(UICheckbox checkBox, boolean checked) {}

}
