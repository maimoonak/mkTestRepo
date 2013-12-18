package org.fcitmuk.openmrs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.fcitmuk.db.util.Persistent;
import org.fcitmuk.db.util.PersistentHelper;
import org.fcitmuk.db.util.Record;

public class CohortList implements Persistent, Record{

	private Vector cohorts = new Vector();

	public CohortList(){

	}

	public CohortList(Vector cohorts) {
		this.cohorts = cohorts;
	}

	public Vector getCohorts() {
		return cohorts;
	}

	public void setCohorts(Vector cohorts) {
		this.cohorts = cohorts;
	}

	public void addCohort(Cohort cohort){
		cohorts.addElement(cohort);
	}

	public void addCohorts(Vector cohortList){
		if(cohortList != null){
			for(int i=0; i<cohortList.size(); i++ )
				this.cohorts.addElement(cohortList.elementAt(i));
		}
	}

	public int size(){
		return cohorts.size();
	}

	public Cohort getCohort(int index){
		return (Cohort)cohorts.elementAt(index);
	}
	
	public Cohort getCohort(String name){
		for (int i = 0; i < cohorts.size(); i++) {
			Cohort coh = (Cohort)cohorts.elementAt(i);
			if(coh.getName().toLowerCase().compareTo(name.toLowerCase()) == 0){
				return coh;
			}
		}
		return null;
	}

	/** 
	 * Reads the patient field collection object from the supplied stream.
	 * 
	 * @param dis - the stream to read from.
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		setCohorts(PersistentHelper.readBig(dis,new Cohort().getClass()));
	}

	/** 
	 * Writes the patient field collection object to the supplied stream.
	 * 
	 * @param dos - the stream to write to.
	 * @throws IOException
	 */
	public void write(DataOutputStream dos) throws IOException {
		PersistentHelper.writeBig(getCohorts(), dos);
	}

	public int getRecordId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRecordId(int id) {
		// TODO Auto-generated method stub
		
	}
}
