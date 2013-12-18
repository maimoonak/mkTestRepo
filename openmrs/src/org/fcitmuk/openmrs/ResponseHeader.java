/**
 * Java Epihandy - Mobile (J2ME) Data Collection tool
 * (c) 2008- Makerere University Faculty of Computing & IT,
 * This program is free software, distributed under the terms of the
 * Apache License, Version 2.0.
 * */

package org.fcitmuk.openmrs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.fcitmuk.db.util.Persistent;

/**
 * Contains the header of a connection response.
 * 
 * @author Daniel
 *
 */
public class ResponseHeader implements Persistent{

	/** Problems occured during execution of the request. */
	public static final byte STATUS_ERROR = 0;
	
	/** Request completed successfully. */
	public static final byte STATUS_SUCCESS = 1;
	
	/** Not permitted to carry out the requested operation. */
	public static final byte STATUS_ACCESS_DENIED = 2;
	
	private byte status = STATUS_ERROR;
	
	
	public ResponseHeader(){
		
	}
	
	public ResponseHeader(byte status){
		setStatus(status);
	}
	
	/**
	 * @see org.fcitmuk.db.util.Persistent#read(java.io.DataInputStream)
	 */
	public void read(DataInputStream dis) throws IOException {
		setStatus(dis.readByte());
	}

	/**
	 * @see org.fcitmuk.db.util.Persistent#write(java.io.DataOutputStream)
	 */
	public void write(DataOutputStream dos) throws IOException {
		dos.writeByte(getStatus());
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	public boolean isSuccess(){
		return getStatus() == STATUS_SUCCESS;
	}
}
