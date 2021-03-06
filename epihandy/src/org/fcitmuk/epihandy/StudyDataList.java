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

import org.fcitmuk.db.util.Persistent;
import org.fcitmuk.db.util.PersistentHelper;

import com.sun.cldc.io.ConsoleOutputStream;


/**
 * This class holds a collection of study data.
 * 
 * @author Daniel Kayiwa
 *
 */
public class StudyDataList  implements Persistent{

	/** Collection of studies. */
	private Vector studies = new Vector();

	/** Constructs a new study data collection. */
	public StudyDataList(){

	}

	public StudyDataList(Vector studies){
		setStudies(studies);
	}

	public Vector getStudies() {
		return studies;
	}

	public void setStudies(Vector studies) {
		this.studies = studies;
	}

	public void addStudy(StudyData studyData){
		studies.addElement(studyData);
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
		setStudies(PersistentHelper.read(dis,new StudyData().getClass()));
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
