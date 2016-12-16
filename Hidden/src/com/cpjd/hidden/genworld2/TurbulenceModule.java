package com.cpjd.hidden.genworld2;

import java.util.Random;

public class TurbulenceModule {
	protected ModuleListener listener;
	protected int[][][] map;
	protected int progress;

	protected Random r;
	protected Thread thread;
	
	public int getProgress() {
		return progress;
	}
}
