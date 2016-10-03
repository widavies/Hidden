package com.cpjd.hidden.ui;

import com.cpjd.hidden.ui.elements.UIButton;
import com.cpjd.hidden.ui.elements.UICheckbox;
import com.cpjd.hidden.ui.windows.UIWindow;

public interface UIListener {
	
	public abstract void buttonPressed(UIButton button);
	public abstract void checkBoxPressed(UICheckbox checkBox, boolean checked);
	public abstract void viewClosed(UIWindow window);

}
