/**
 * Java Epihandy - Mobile (J2ME) Data Collection tool
 * (c) 2008- Makerere University Faculty of Computing & IT,
 * This program is free software, distributed under the terms of the
 * Apache License, Version 2.0.
 * */

package org.fcitmuk.communication;

import java.util.Enumeration;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;

import org.fcitmuk.midp.db.util.StorageListener;
import org.fcitmuk.midp.mvc.SettingsManager;
import org.fcitmuk.util.Setting;
import org.fcitmuk.util.SettingList;
import org.fcitmuk.util.SettingView;
import org.ihs.logger.Log;

/**
 * This class shows existing connection parameters and lets the user modify
 * them, before passing them over.
 * 
 * @author Daniel, Omar
 * @author Maimoona
 * 
 */
public class ConnectionSettings extends SettingsManager{

	public static final String CONNECTION_SETTINGS_STORAGE_NAME = "TransportlayerConnectionSettings";

	public static final String SETTING_CONNECTION_TYPE = "connectionsetting.connectionType";
	public static final String SETTING_HTTP_SERVER_URL = "connectionsetting.httpServerUrl";

	public static final String SETTING_BLUETOOTH_SERVER_URL = "connectionsetting.bluetoothServerUrl";
	public static final String SETTING_BLUETOOTH_SERVER_ID = "connectionsetting.bluetoothServerID";
	public static final String SETTING_BLUETOOTH_DEVICE = "connectionsetting.bluetoothDeviceName";

	public static final String SETTING_SMS_DESTINATION_ADDRESS = "connectionsetting.smsDestinationAddress";
	public static final String SETTING_SMS_SOURCE_ADDRESS = "connectionsetting.smsSourceAddress";
	
	private final SettingView SETTING_EDITOR;
	private SettingList settingList;
	private Displayable nextDisplayable;

	public SettingList getSettingList(){
		return settingList;
	}
	
	ConnectionSettings(final Display display) 
	{
		Log.LOGGER().debug("Loading connection settings...");
		settingList = new SettingList(CONNECTION_SETTINGS_STORAGE_NAME, true, new StorageListener() {
			public void errorOccured(String errorMessage, Exception e) {
				Log.LOGGER().error(errorMessage, e);			
			}
		});
		// if no settings initiated yet
		if(settingList == null || settingList.size() == 0){
			initDefaultSettings();
		}
		
		Log.LOGGER().debug("configuring editor..");

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
		
		SETTING_EDITOR = new SettingView("Connection Settings", saveCommandListner, cancelCommandListner);
		Log.LOGGER().debug("Done");
	}
	
	protected boolean validateSettings(Display display)
	{
		String conType = SETTING_EDITOR.getSettingUiItemValue(SETTING_CONNECTION_TYPE);
		String httpServerUrl = SETTING_EDITOR.getSettingUiItemValue(SETTING_HTTP_SERVER_URL).trim();
		if(conType == ConnectionType.HTTP.NAME() && httpServerUrl.compareTo("") == 0){
			display.setCurrent(new Alert(settingList.getSetting(SETTING_HTTP_SERVER_URL).getValidationErrorMessage()), SETTING_EDITOR);
			return false;
		}
		
		String bluetoothServerUrl = SETTING_EDITOR.getSettingUiItemValue(SETTING_BLUETOOTH_SERVER_URL).trim();
		if(conType == ConnectionType.BLUETOOTH.NAME() && bluetoothServerUrl.compareTo("") == 0){
			display.setCurrent(new Alert(settingList.getSetting(SETTING_BLUETOOTH_SERVER_URL).getValidationErrorMessage()), SETTING_EDITOR);
			return false;
		}
		
		return true;
	}
	
	protected void initDefaultSettings(){
		settingList.addSetting(new Setting(SETTING_CONNECTION_TYPE, ConnectionType.HTTP.NAME(), "Connection Type", "The setting to specify connection type for server access", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(SETTING_HTTP_SERVER_URL, "http://localhost:8181/openmrs", "Http Server URL", "The Server URL of web service hosting the request handler", "", "A valid server URL must be specified.", String.valueOf(true), String.valueOf(true)));
		
		settingList.addSetting(new Setting(SETTING_BLUETOOTH_SERVER_URL, "btspp://0050C27FEFA9:1", "Bluetooth Server URL", "The Server URL of bluetooth service hosting the request handler", "", "A valid bluetooth sever URL must be specified", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(SETTING_BLUETOOTH_SERVER_ID, "", "Bluetooth Server ID", "The bluetooth server ID", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(SETTING_BLUETOOTH_DEVICE, "", "Bluetooth Device name", "Bluetooth device name", "", "", String.valueOf(true), String.valueOf(true)));

		settingList.addSetting(new Setting(SETTING_SMS_DESTINATION_ADDRESS, "", "Sms Destination Address", "The sms destination address/number", "", "", String.valueOf(true), String.valueOf(true)));
		settingList.addSetting(new Setting(SETTING_SMS_SOURCE_ADDRESS, "", "Sms Source Address", "The sms source address/number", "", "", String.valueOf(true), String.valueOf(true)));

		settingList.saveSettings();
	}
	
	protected void initSettingsView(){
		SETTING_EDITOR.appendChoiceSetting(settingList.getSetting(SETTING_CONNECTION_TYPE), 
				new String[]{ConnectionType.HTTP.NAME(), ConnectionType.BLUETOOTH.NAME()}, 
				ChoiceGroup.POPUP);
		
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_HTTP_SERVER_URL), 255, TextField.ANY);

		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_BLUETOOTH_SERVER_URL), 255, TextField.ANY);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_BLUETOOTH_SERVER_ID), 255, TextField.ANY);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_BLUETOOTH_DEVICE), 255, TextField.ANY);
	
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_SMS_SOURCE_ADDRESS), 255, TextField.ANY);
		SETTING_EDITOR.appendTextSetting(settingList.getSetting(SETTING_SMS_DESTINATION_ADDRESS), 255, TextField.ANY);
	}

	public void openSettingsEditor(Display display, Displayable nextDisplayable){
		this.nextDisplayable = nextDisplayable;
		initSettingsView();
		display.setCurrent(SETTING_EDITOR);
	}
}
