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


/** The definition of a page in a form or questionaire. 
 * 
 * @author Daniel Kayiwa
 *
 */
public class PageDef implements Persistent{

	/** A list of questions on a page. */
	private Vector questions = new Vector();

	/** The page number. */
	private byte pageNo = EpihandyConstants.NULL_ID;

	/** The name of the page. */
	private String name = EpihandyConstants.EMPTY_STRING;

	public PageDef() {

	}

	public PageDef(PageDef pageDef) {
		setPageNo(pageDef.getPageNo());
		setName(pageDef.getName());
		copyQuestions(pageDef.getQuestions());
	}

	public PageDef(String name, byte pageNo,Vector questions) {
		setName(name);
		setPageNo(pageNo);
		setQuestions(questions);
	}

	public byte getPageNo() {
		return pageNo;
	}

	public void setPageNo(byte pageNo) {
		this.pageNo = pageNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector getQuestions() {
		return questions;
	}

	public void addQuestion(QuestionDef qtn){
		questions.addElement(qtn);
	}

	public void setQuestions(Vector questions) {
		this.questions = questions;
	}

	public QuestionDef getQuestion(String varName){
		for(byte i=0; i<getQuestions().size(); i++){
			QuestionDef def = (QuestionDef)getQuestions().elementAt(i);
			if(def.getVariableName().equals(varName))
				return def;
			
//			Without this, then we have not validation and skip rules in repeat questions.
			if(def.getType() == QuestionDef.QTN_TYPE_REPEAT && def.getRepeatQtnsDef() != null){
				def = def.getRepeatQtnsDef().getQuestion(varName);
				if(def != null)
					return def;
			}
		}

		return null;
	}
	
	//method added by Omar on 12-7-11
	public QuestionDef getQuestionByName(String name)
	{
		int count = getQuestions().size();
		for(byte i=0; i<count; i++){
			QuestionDef def = (QuestionDef)getQuestions().elementAt(i);
			if(def.getText().equalsIgnoreCase(name))
				return def;
			
//			Without this, then we have not validation and skip rules in repeat questions.
			if(def.getType() == QuestionDef.QTN_TYPE_REPEAT && def.getRepeatQtnsDef() != null){
				def = def.getRepeatQtnsDef().getQuestion(name);
				if(def != null)
					return def;
			}
		}

		return null;

	}

	public QuestionDef getQuestion(byte id){
		for(byte i=0; i<getQuestions().size(); i++){
			QuestionDef def = (QuestionDef)getQuestions().elementAt(i);
			if(def.getId() == id)
				return def;
			
//			Without this, then we have not validation and skip rules in repeat questions.
			if(def.getType() == QuestionDef.QTN_TYPE_REPEAT && def.getRepeatQtnsDef() != null){
				def = def.getRepeatQtnsDef().getQuestion(id);
				if(def != null)
					return def;
			}
		}

		return null;
	}

	/** Reads a page definition object from the supplied stream. */
	public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
		setPageNo(dis.readByte());
		setName(dis.readUTF());
		setQuestions(PersistentHelper.read(dis,new QuestionDef().getClass()));
	}

	/** Write the page definition object to the supplied stream. */
	public void write(DataOutputStream dos) throws IOException {
		dos.writeByte(getPageNo());
		dos.writeUTF(getName());
		PersistentHelper.write(getQuestions(), dos);
	}

	public String toString() {
		return getName();
	}

	private void copyQuestions(Vector questions){
		for(byte i=0; i<questions.size(); i++)
			this.questions.addElement(new QuestionDef((QuestionDef)questions.elementAt(i)));
	}
}
