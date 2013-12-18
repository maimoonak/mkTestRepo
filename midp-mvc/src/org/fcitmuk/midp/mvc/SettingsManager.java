package org.fcitmuk.midp.mvc;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

public abstract class SettingsManager {

	protected abstract void initSettingsView();
	
	protected abstract void initDefaultSettings();
	
	protected abstract boolean validateSettings(Display display);
	
	protected abstract void openSettingsEditor(Display display, Displayable nextDisplayable);
}
