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

import org.fcitmuk.db.util.PersistentHelper;
import org.fcitmuk.db.util.Persistent;


/**
 * This class holds a collection of study definitions.
 * NOTE CAREFULLY: None of the studies contains any forms because this is to just provide
 * info on what studes are available.
 * 
 * @author Daniel Kayiwa
 *
 */
public class StudyDefList implements Persistent{

	/** Collection of study definitions (StudyDef objects). */
	private Vector studies = new Vector();

	/** Constructs a new study collection. */
	public StudyDefList(){

	}

	/** Copy Constructor. */
	public StudyDefList(StudyDefList studyDefList){
		for(byte i=0; i<studyDefList.size(); i++)
			studies.addElement(new StudyDef(studyDefList.getStudy(i)));
	}

	public StudyDefList(Vector studies){
		setStudies(studies);
	}

	public Vector getStudies() {
		return studies;
	}

	public int size(){
		return studies.size();
	}

	public void setStudies(Vector studies) {
		this.studies = studies;
	}

	public StudyDef getStudy(byte index){
		return (StudyDef)studies.elementAt(index);
	}

	public void addStudy(StudyDef studyDef){
		studies.addElement(studyDef);
	}

	public void addStudies(Vector studyList){
		if(studyList != null){
			for(byte i=0; i<studyList.size(); i++ )
				studies.addElement(studyList.elementAt(i));
		}
	}

	/** 
	 * Reads the study collection object from the supplied stream.
	 * 
	 * @param dis - the stream to read from.
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		setStudies(PersistentHelper.read(dis,new StudyDef().getClass()));
	}

	/** 
	 * Writes the study collection object to the supplied stream.
	 * 
	 * @param dos - the stream to write to.
	 * @throws IOException
	 */
	public void write(DataOutputStream dos) throws IOException {
		PersistentHelper.write(getStudies(), dos);
	}
}
