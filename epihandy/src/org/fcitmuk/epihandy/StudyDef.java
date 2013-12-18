/**
 * Java Epihandy - Mobile (J2ME) Data Collection tool
 * (c) 2008- Makerere University Faculty of Computing & IT,
 * This program is free software, distributed under the terms of the
 * Apache License, Version 2.0.
 * */

package org.fcitmuk.epihandy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import org.fcitmuk.db.util.Persistent;
import org.fcitmuk.db.util.PersistentHelper;


/**
 * This class encapsulates all form definitions of a particular study.
 * 
 * @author Daniel Kayiwa
 *
 */
public class StudyDef implements Persistent{

	/** The text indentifier of the study. */
	private String variableName = EpihandyConstants.EMPTY_STRING;

	/** The name of the study. */
	private String name = EpihandyConstants.EMPTY_STRING;

	//Assuming the number of studies will not exceed 127.
	/** The numeric identifier of the study. */
	private int id = EpihandyConstants.NULL_ID;

	/** A list of form definitions (FormDef) in the the study. */
	private Vector forms = new Vector();

	/** Constructs a new study definitions. */
	public StudyDef() {

	}

	/** Copy constructor. */
	public StudyDef(StudyDef studyDef) {
		this(studyDef.getId(),studyDef.getName(),studyDef.getVariableName());
		copyForms(studyDef.getForms());
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param variableName
	 */
	public StudyDef(int id, String name, String variableName) {
		setId(id);
		setName(name);
		setVariableName(variableName);;
	}

	/** 
	 * Constructs a new study definition from the following parameters.
	 * 
	 * @param id - the numeric unique identifier of the study.
	 * @param name - the display name of the study.
	 * @param variableName - the text unique identifier of the study.
	 * @param forms - the collection of form definitions in the study.
	 */
	public StudyDef(int id, String name, String variableName,Vector forms) {
		this(id,name,variableName);
		setForms(forms);
	}

	public Vector getForms() {
		return forms;
	}

	public void setForms(Vector forms) {
		this.forms = forms;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public FormDef getFormAt(byte index){
		return (FormDef)forms.elementAt(index);
	}

	public void addForm(FormDef formDef){
		forms.addElement(formDef);
	}

	public void addForms(Vector formList){
		if(formList != null){
			for(byte i=0; i<formList.size(); i++ )
				forms.addElement(formList.elementAt(i));
		}
	}

	/** 
	 * Reads the study definition object from the supplied stream.
	 * 
	 * @param dis - the stream to read from.
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		
		/*byte[] buffer = new byte[1024];
		StringBuffer sb = new StringBuffer();
		int readIn = 0;
		while((readIn = dis.read(buffer)) > 0)
		{
		     String temp = new String(buffer, 0, readIn);
		     sb.append(temp);  
		}
		String result = sb.toString();
		System.out.println(result);*/
         //use inputLine.toString(); here it would have whole source
         
		setId(dis.readInt());
		setName(dis.readUTF());
		setVariableName(dis.readUTF());
		setForms(PersistentHelper.read(dis,new FormDef().getClass()));
	}

	/** 
	 * Writes the study definition object to the supplied stream.
	 * 
	 * @param dos - the stream to write to.
	 * @throws IOException
	 */
	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(getId());
		dos.writeUTF(getName());
		dos.writeUTF(getVariableName());
		PersistentHelper.write(getForms(), dos);
	}

	/**
	 * Gets a form definition with a given string identifier.
	 * 
	 * @param varName - the string identifier.
	 * @return - the form definition.
	 */
	public FormDef getForm(String varName){
		for(byte i=0; i<forms.size(); i++){
			FormDef def = (FormDef)forms.elementAt(i);
			System.out.println(def.getVariableName());
			if(def.getVariableName().equals(varName))
				return def;
		}

		return null;
	}

	/**
	 * Gets a form definition with a given numeric identifier.
	 * 
	 * @param formId - the numeric identifier.
	 * @return - the form definition.
	 */
	public FormDef getForm(int formId){
		for(byte i=0; i<forms.size(); i++){
			FormDef def = (FormDef)forms.elementAt(i);
			if(def.getId() == formId)
				return def;
		}

		return null;
	}

	public String toString() {
		return getName();
	}

	private void copyForms(Vector forms){
		for(byte i=0; i<forms.size(); i++)
			this.forms.addElement(new FormDef((FormDef)forms.elementAt(i)));
	}
}
