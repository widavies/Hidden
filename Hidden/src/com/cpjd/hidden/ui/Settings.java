package com.cpjd.hidden.ui;

import com.cpjd.hidden.gamestate.GameStateManager;
import com.cpjd.hidden.toolbox.Layout;

public class Settings extends Fragment {
	
	public Settings(GameStateManager gsm) {
		super(gsm);
		TOTAL_EXPAND = Layout.HEIGHT / 4;
	}
	
}
