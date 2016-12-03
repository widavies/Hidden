package com.cpjd.hidden.items;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {
		
		private static final long serialVersionUID = 3193512108391238537L;
		
		public static final int BLOCK = 0;
		public static final int TOOL = 1;
		public static final int ARMOR = 2;
		
		private transient BufferedImage icon;
		private String title;
		private String tooltip;
		private int type;
		private int damage;
		private int ID;
		
		private String filePath;
		
		public Item(BufferedImage icon, String title, String tooltip, int type, int damage, int id, String filePath) {
			this.icon = icon;
			this.title = title;
			this.tooltip = tooltip;
			this.type = type;
			this.damage = damage;
			this.filePath = filePath;
		}
		public int getID() {
			return ID;
		}
		public void setID(int id) {
			this.ID = id;
		}
		public int getType() {
			return type;
		}
		public BufferedImage getIcon() {
			return icon;
		}
		public String getTitle() {
			return title;
		}
		public String getTooltip() {
			return tooltip;
		}
		public int getDamage() {
			return damage;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getPath() {
			return filePath;
		}
		public void setIcon(BufferedImage image) {
			icon = image;
			image = null;
		}
	}