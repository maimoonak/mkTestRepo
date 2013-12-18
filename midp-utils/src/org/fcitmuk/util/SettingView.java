package org.fcitmuk.util;

import java.util.Hashtable;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public class SettingView extends Form{
	private Hashtable uiItems = new Hashtable();
	private Command cmdSave;
	private Command cmdCancel;
	
	public SettingView(String title, final CommandListener saveCommandHandler, final CommandListener cancelCommandHandler) {
		super(title);
		cmdSave = new Command ("Save", Command.OK, 0);
		cmdCancel = new Command ("Cancel", Command.EXIT, 1);
		
		addCommand(cmdSave);
		addCommand(cmdCancel);
		setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if(c == cmdSave){
					saveCommandHandler.commandAction(c, d);
				}
				else if(c == cmdCancel){
					cancelCommandHandler.commandAction(c, d);
				}
			}
		});
	}
	
	public Hashtable getSettingUiItems() {
		return uiItems;
	}
	
	public String getSettingUiItemValue(String key) {
		return getItemValue((Item) uiItems.get(key));
	}
	private String getItemValue(Item item){
		if(item instanceof TextField){
			return ((TextField) item).getString();
		}
		else if(item instanceof ChoiceGroup){
			return ((ChoiceGroup) item).getString(((ChoiceGroup) item).getSelectedIndex());
		}
		return "";
	}

	public void appendTextSetting(Setting setting, int maxSize, int itemUIConstraints){
		TextField t = new TextField(setting.getDisplayName(), setting.getValue(), maxSize, itemUIConstraints);
		uiItems.put(setting.getName(), t);
		append(t);
	}
	
	public void appendChoiceSetting(Setting setting, String[] choices, int choiceGroupType){
		ChoiceGroup ch = new ChoiceGroup(setting.getDisplayName(), choiceGroupType, choices, null);
		uiItems.put(setting.getName(), ch);
		append(ch);
	}
	
	public void appendSeparator(){
		StringItem sp = new StringItem("", "................................");
		append(sp);
	}
}
