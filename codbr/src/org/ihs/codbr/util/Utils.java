package org.ihs.codbr.util;

import java.util.Vector;

import javax.microedition.lcdui.ChoiceGroup;

import org.fcitmuk.openmrs.Cohort;
import org.fcitmuk.openmrs.CohortList;
import org.fcitmuk.openmrs.OpenmrsDataStorage;
import org.fcitmuk.openmrs.User;
import org.fcitmuk.openmrs.util.SecurityUtils;

public class Utils {

	public static boolean isEmptyOrWhiteSpaceOnly(String string){
		if(string == null || string.trim().length() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isFirstLaunch(){
		Vector xfus = OpenmrsDataStorage.getXformUsers();
		if(xfus == null ||xfus.size() == 0){
			return true;
		}
		
		return false;
	}
	

	public static void loadCohorts(ChoiceGroup item){
		CohortList stored = OpenmrsDataStorage.getCohorts();
		if(stored != null){
			for (int i = 0; i < stored.size(); i++) {
				Cohort coh = ((Cohort)stored.getCohorts().elementAt(i));
				item.append(coh.getName(), null);
			}
		}
	}
	
	public static User authenticate(String username, String clearTextPassword){
		User xfu = OpenmrsDataStorage.getXformUser(username);
		if(xfu == null){
			//UIUtils.showPopup(ErrorMessages.USER_NOT_EXIST, null, display);
			return null;
		}
		else if(!SecurityUtils.authenticate(xfu, clearTextPassword)){
			//UIUtils.showPopup(ErrorMessages.PASSWORD_NOT_MATCH, null, display);
			return null;
		}
		
		return xfu;
	}
}
