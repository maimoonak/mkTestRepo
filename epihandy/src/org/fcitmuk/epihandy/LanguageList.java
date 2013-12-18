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
import java.util.Hashtable;

import org.fcitmuk.db.util.Persistent;
import org.fcitmuk.db.util.PersistentHelper;


/**
 * 
 * @author daniel
 *
 */
public class LanguageList  implements Persistent{

	private Hashtable hashtable = new Hashtable();
	
	public int size(){
		return hashtable.size();
	}
	
	public Hashtable getLanguages(){
		return hashtable;
	}
	
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		hashtable = PersistentHelper.read(dis);
	}

	public void write(DataOutputStream dos) throws IOException {
		PersistentHelper.write(hashtable, dos);
	}
}
