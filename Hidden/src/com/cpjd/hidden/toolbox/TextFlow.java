package com.cpjd.hidden.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.cpjd.hidden.main.GamePanel;

public class TextFlow {
	
	private int y;
	private int laps;
	
	String[] code = {
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">Enable-(server.mode_modifier)",
			">Server response received. (y2324) Connection is regulated, attempt to bypass?",
			"|server bypassing(system.enable) client.",
			"bootstrap enabling, system has gained access to the server",
			"enter credentials: ",
			"***********************",
			"Authentication token recieved. checking against checkum locale variables",
			"Authentication revoked. alerting access",
			"system has been revoked of access, enable boot lock>?",
			"^^css()utf-8 encryption enabled. Notice. This connection is encrypted. Attempting to bypass will result in prosecution.",
			"Server bypass console active. Please enter your authentication factor to continue.",
			">> ********",
			">> Server: the connection is encrypted. Client: Would you like to attempt to un-encrypt?",
			">> y",
			">> Server: connection de-encrypted. Access granted",
			">> list files (filter:1998,boston)",
			">> 1 File(s) found, open?",
			">> File opened.",
			">> Mission: H I D E",
			">> Client: Connection compromised."
	};
	
	TextScroll[] scrollers = new TextScroll[code.length];
	
	public TextFlow() {
		for(int i = 0; i < code.length; i++) {
			scrollers[i] = new TextScroll(code[i], false);
			scrollers[i].setDelay(30);
		}
	}
	
	public void update() {
		for(int i = 0; i < code.length; i++) {
			scrollers[i].update();
		}
		if(laps < 4) y+=15;
		
		if(y > 2000) {
			laps++;
			y = 0;
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		for(int i = 0; i < code.length; i++) {
			scrollers[i].draw(g, 5, ((i * 30) + 60) - y);
		}
		if(laps >= 4) {
			g.setColor(Color.RED);
			Font font = g.getFont();
			Font newFont = font.deriveFont(font.getSize() * 10f);
			g.setFont(newFont);
			FontMetrics fm = g.getFontMetrics(newFont);
			g.drawString("H I D E", Layout.centerw(fm.stringWidth("H I D E")), Layout.centerh(fm.getHeight()));
		}
	}
	
}
