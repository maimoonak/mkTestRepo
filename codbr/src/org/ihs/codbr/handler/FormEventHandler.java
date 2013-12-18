package org.ihs.codbr.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.fcitmuk.communication.ConnectionParameter;
import org.fcitmuk.db.util.Persistent;
import org.fcitmuk.epihandy.FormData;
import org.fcitmuk.epihandy.FormDef;
import org.fcitmuk.epihandy.QuestionData;
import org.fcitmuk.epihandy.StudyDef;
import org.fcitmuk.epihandy.midp.db.EpihandyDataStorage;
import org.fcitmuk.epihandy.midp.forms.FormListener;
import org.fcitmuk.openmrs.XformTempList;
import org.ihs.codbr.MainMidlet;
import org.ihs.codbr.constant.WebServerConstants;
import org.ihs.xform.EpihandyXform;
import org.ihs.xform.StringReader;

public class FormEventHandler implements FormListener, ResponseNotification{
	private MainMidlet mainMidlet;
	private StringBuffer xmlModel = new StringBuffer();
	private StudyDef study;

	public FormEventHandler(MainMidlet mainMidlet) {
		this.mainMidlet = mainMidlet;
	}
	public boolean beforeFormCancelled(FormData data) {
		// TODO Auto-generated method stub
		return true;
	}

	public void afterFormCancelled(FormData data) {
		// TODO Auto-generated method stub
		
	}

	public boolean beforeFormSaved(FormData data, boolean isNew) {
		//if(isNew){
			//EpihandyDataStorage.saveFormData(study.getId(), data);
		//}
/*		final String str = EpihandyXform.fromFormData2XformModel(data);
		final String str2 = EpihandyXform.updateXformModel(EpihandyXform.getDocument(new StringReader(xmlModel.toString())), data);
		str2.toLowerCase();

		//RequestHeader reqh = RequestHeader.getRequestHeader(RequestHeader.ACTION_UPLOAD_FORMS, mainMidlet.getCurrentXformUser().getName(), mainMidlet.getCurrentXformUser().getClearTextPassword(), mainMidlet.getLocale(), new Object[]{str});
		mainMidlet.sendDownloadRequest(WebServerConstants.SUBURL_XFORM_FORM_UPLOAD_XML, true, 
				new ConnectionParameter(WebServerConstants.PARAM_XFORM_BATCH_ENTRY, "true"), false, new Persistent() {
					
					public void write(DataOutputStream dos) throws IOException {
						dos.writeByte(1);
						dos.writeUTF(str2);
					}
					
					public void read(DataInputStream dis) throws IOException,
							InstantiationException, IllegalAccessException {
						// TODO Auto-generated method stub
						
					}
				}, new XformTempList(), this, false);
		return false;*/
		return true;
	}

	public void afterFormSaved(FormData data, boolean isNew) {
		// TODO Auto-generated method stub
		
	}

/*	public boolean beforeFormDisplay(FormData data, String xmlModel) {
		this.xmlModel = new StringBuffer(xmlModel);
		return true;
	}*/
	
	public boolean beforeFormDisplay(FormData data) {
		return true;
	}

	public boolean beforeQuestionEdit(QuestionData data) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean afterQuestionEdit(QuestionData data) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean beforeFormDataListDisplay(FormDef formDef, StudyDef studyDef) {
		study = studyDef;
		return true;
	}

	public boolean beforeFormDelete(FormData data) {
		// TODO Auto-generated method stub
		return true;
	}

	public void afterFormDelete(FormData data) {
		// TODO Auto-generated method stub
		
	}

	public boolean beforeFormDefListDisplay(Vector formDefList) {
		// TODO Auto-generated method stub
		return true;
	}
	public void response(Object otherInformation, Persistent persistentData) {
		Class clz = persistentData.getClass();
		clz.getName();
	}
	public void error(Object errorInformation) {
		// TODO Auto-generated method stub
		
	}

}
