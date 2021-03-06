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
import java.util.Vector;
import org.fcitmuk.db.util.*;


/**
 * This calls encapsulates data collected in all forms of a particular study.
 * 
 * @author Daniel Kayiwa
 *
 */
public class StudyData implements Persistent{
	private int id = EpihandyConstants.NULL_ID; //this is just for storage;
	private StudyDef def;
	private Vector forms = new Vector();

	/** Creates a new study data object. */
	public StudyData(){
		
	}

	/**
	 * Creates a new study data object form these parameters.
	 * 
	 * @param id - the id of the study definition represented by this data.
	 */
	public StudyData(int id) {
		setId(id);
	}

	/**
	 * Creates a new study data object form these parameters.
	 * 
	 * @param def - reference to the study definition represented by this data.
	 */
	public StudyData(StudyDef def) {
		this();
		setDef(def);
		setId(def.getId());
	}

	public StudyDef getDef() {
		return def;
	}

	public void setDef(StudyDef def) {
		this.def = def;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector getForms() {
		return forms;
	}

	public void setForms(Vector forms) {
		this.forms = forms;
	}

	public void addForm(FormData formData){
		forms.addElement(formData);
	}

	public void addForms(Vector formList){
		if(formList != null){
			for(byte i=0; i<formList.size(); i++ )
				forms.addElement(formList.elementAt(i));
		}
	}

	/** 
	 * Reads the study data object from the supplied stream.
	 * 
	 * @param dis - the stream to read from.
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		setId(dis.readInt());
		setForms(PersistentHelper.read(dis,new FormData().getClass()));
	}

	/** 
	 * Writes the study data object to the supplied stream.
	 * 
	 * @param dos - the stream to write to.
	 * @throws IOException
	 */
	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(getId());
		PersistentHelper.write(getForms(), dos);
	}
}
