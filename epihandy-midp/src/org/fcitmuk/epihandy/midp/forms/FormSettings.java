package org.fcitmuk.epihandy.midp.forms;

import java.util.Enumeration;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;

import org.fcitmuk.midp.db.util.StorageListener;
import org.fcitmuk.midp.mvc.SettingsManager;
import org.fcitmuk.util.MenuText;
import org.fcitmuk.util.Setting;
import org.fcitmuk.util.SettingList;
import org.fcitmuk.util.SettingView;
import org.fcitmuk.util.Utilities;

public class FormSettings extends SettingsManager{
	public static final String STORAGE_NAME_SETTINGS = "fcitmuk.GeneralSettings";

	public static final String KEY_SINGLE_QUESTION_EDIT = "SINGLE_QUESTION_EDIT";
	public static final String KEY_QUESTION_NUMBERING = "QUESTION_NUMBERING";
	public static final String KEY_OK_ON_RIGHT = "OK_ON_RIGHT";
	public static final String KEY_DELETE_DATA_AFTER_UPLOAD = "DELETE_DATA_AFTER_UPLOAD";
	
	public static final String KEY_DATE_FORMAT = "DATE_FORMAT ";
	
	public static final String KEY_LAST_SELECTED_ITEM = "KEY_LAST_SELECTED_ITEM_LANG";
	public static final String KEY_LOCALE = "LOCALE ";
	
	public static final String KEY_PICTURE_FORMAT = "PICTURE_FORMAT ";
	public static final String KEY_PICTURE_WIDTH = "PICTURE_WIDTH";
	public static final String KEY_PICTURE_HEIGHT = "PICTURE_HEIGHT";
	public static final String KEY_VIDEO_FORMAT = "KEY_VIDEO_FORMAT";
	public static final String KEY_AUDIO_FORMAT = "KEY_AUDIO_FORMAT";
	public static final String KEY_ENCODING = "KEY_ENCODING";

	private final SettingView SETTING_EDITOR;
	private SettingList settingList;
	private Displayable nextDisplayable;

	public SettingList getSettingList(){
		return settingList;
	}
	
	FormSettings(final Display display) {
		settingList = new SettingList(STORAGE_NAME_SETTINGS, true, new StorageListener() {
			
			public void errorOccured(String errorMessage, Exception e) {
				display.setCurrent(new Alert("", e.getMessage(), null, AlertType.ERROR));
			}
		});
		// if no settings initiated yet
		if(settingList == null || settingList.size() == 0){
			initDefaultSettings();
		}
		
		CommandListener saveCommandListner = new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if(validateSettings(display)){
					for (Enumeration e = SETTING_EDITOR.getSettingUiItems().keys(); e.hasMoreElements();) {
						String key = (String) e.nextElement();
						settingList.getSetting(key).setValue(SETTING_EDITOR.getSettingUiItemValue(key));
					}
					
					settingList.saveSettings();
					display.setCurrent(nextDisplayable);
				}
			}
		};
		
		CommandListener cancelCommandListner = new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				display.setCurrent(new Alert("Exiting without saving settings"), nextDisplayable);
			}
		};
		
		SETTING_EDITOR = new SettingView("Form Settings", saveCommandListner, cancelCommandListner);
	}
	
	protected void initSettingsView() {
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(KEY_DATE_FORMAT), 
				new String[]{MenuText.DAY_FIRST(), MenuText.MONTH_FIRST(), MenuText.YEAR_FIRST()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(KEY_SINGLE_QUESTION_EDIT), 
				new String[]{new Boolean(true).toString(), new Boolean(false).toString()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(KEY_QUESTION_NUMBERING), 
				new String[]{new Boolean(true).toString(), new Boolean(false).toString()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(KEY_OK_ON_RIGHT), 
				new String[]{new Boolean(true).toString(), new Boolean(false).toString()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(KEY_DELETE_DATA_AFTER_UPLOAD), 
				new String[]{new Boolean(true).toString(), new Boolean(false).toString()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_PICTURE_FORMAT), 10, TextField.ANY);

		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_PICTURE_WIDTH), 3, TextField.NUMERIC);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_PICTURE_HEIGHT), 3, TextField.NUMERIC);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_VIDEO_FORMAT), 10, TextField.ANY);
	
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_AUDIO_FORMAT), 10, TextField.ANY);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(KEY_ENCODING), 255, TextField.ANY);
	}
	
	protected void initDefaultSettings() {
		settingList.addSetting(new Setting(KEY_DATE_FORMAT, "0", MenuText.DATE_FORMAT(), "The setting to specify date format in forms", "", "", String.valueOf(true), String.valueOf(true)));

		settingList.addSetting(new Setting(KEY_SINGLE_QUESTION_EDIT, new Boolean(true).toString(), MenuText.SINGLE_QUESTION_EDIT(), "The setting to specify if only one question should be editable at a time or complete form could be filled", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_QUESTION_NUMBERING, new Boolean(true).toString(), MenuText.NUMBERING(), "The setting to specify if question numbers should be displayed", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_OK_ON_RIGHT, new Boolean(true).toString(), MenuText.OK_ON_RIGHT(), "The setting to specify if ok button should be on right", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_DELETE_DATA_AFTER_UPLOAD, new Boolean(true).toString(), MenuText.DELETE_AFTER_UPLOAD(), "The setting to specify if data should be deleted after upload on server", "", "", String.valueOf(true), String.valueOf(true)));
		
		settingList.addSetting(new Setting(KEY_PICTURE_FORMAT, "jpeg", MenuText.PICTURE_FORMAT(), "The setting to specify picture format in forms", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_PICTURE_WIDTH, "200", MenuText.PICTURE_WIDTH(), "The setting to specify picture width in forms", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_PICTURE_HEIGHT, "200", MenuText.PICTURE_HEIGHT(), "The setting to specify picture height in forms", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_VIDEO_FORMAT, "mpeg", MenuText.VIDEO_FORMAT(), "The setting to specify video format in forms", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_AUDIO_FORMAT, "x-wav", MenuText.AUDIO_FORMAT(), "The setting to specify audio format in forms", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(KEY_ENCODING, System.getProperty("video.snapshot.encodings"), MenuText.ENCODINGS(), "The setting to specify audio format in forms", "", "", String.valueOf(true), String.valueOf(true)));
	}
	protected boolean validateSettings(Display display) {
		return true;
	}
	public void openSettingsEditor(Display display, Displayable nextDisplayable) {
		this.nextDisplayable = nextDisplayable;
		initSettingsView();
		display.setCurrent(SETTING_EDITOR);
	}
	
	public byte getDateFormat(){
		return Byte.parseByte(settingList.getSettingValue(KEY_DATE_FORMAT,"0"));
	}
	
	public boolean isSingleQtnEdit(){
		return Utilities.stringToBoolean(settingList.getSettingValue(KEY_SINGLE_QUESTION_EDIT, new Boolean(true).toString()));
	}
	
	public boolean isOkOnRight(){
		return Utilities.stringToBoolean(settingList.getSettingValue(KEY_OK_ON_RIGHT, new Boolean(true).toString()));
	}
	
	public boolean isQuestionNumbering(){
		return Utilities.stringToBoolean(settingList.getSettingValue(KEY_QUESTION_NUMBERING, new Boolean(true).toString()));
	}
	
	public boolean deleteDataAfterUpload(){
		return Utilities.stringToBoolean(settingList.getSettingValue(KEY_DELETE_DATA_AFTER_UPLOAD, new Boolean(true).toString()));
	}
	
	public String getAudioFormat(){
		return settingList.getSettingValue(KEY_AUDIO_FORMAT,"x-wav");
	}

	public String getVideoFormat(){
		return settingList.getSettingValue(KEY_VIDEO_FORMAT,"mpeg");
	}
	
	public String getPictureParameters(){
		String format = null;

		String s = settingList.getSettingValue(KEY_PICTURE_FORMAT, "png");
		if(s == null || s.trim().length() == 0)
			return null;
		format = "encoding="+s;

		s = settingList.getSettingValue(KEY_PICTURE_WIDTH, "200");
		if(s == null || s.trim().length() == 0)
			return null;
		format += "&width="+s;

		s = settingList.getSettingValue(KEY_PICTURE_HEIGHT, "200");
		if(s == null || s.trim().length() == 0)
			return null;
		format += "&height="+s;

		return format;
	}
	
/*	public void setDeleteDataAfterUpload(boolean delete){
		SettingList settings = new SettingList(STORAGE_NAME_SETTINGS,true);
		settings.setSetting(KEY_DELETE_DATA_AFTER_UPLOAD,Utilities.booleanToString(delete));
	}*/
}
