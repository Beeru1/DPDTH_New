/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * @author Anju
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOSendEmailFormBean extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6496416218902461506L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private String senderId;
	private String message_Sent;
	private String receiverId;
	private String ccReceiverId;
	private String subjectId;
	ArrayList subjectList;
	private String subject;
	private String lapuMobileNo;
	private String message;
	private String senderName;
	private String distCode;
	

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLapuMobileNo() {
		return lapuMobileNo;
	}

	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCcReceiverId() {
		return ccReceiverId;
	}

	public void setCcReceiverId(String ccReceiverId) {
		this.ccReceiverId = ccReceiverId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public ArrayList getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList subjectList) {
		this.subjectList = subjectList;
	}
	/**
	 * @return
	 */
	public String getMessage_Sent() {
		return message_Sent;
	}

	/**
	 * @return
	 */
	public String getReceiverId() {
		return receiverId;
	}

	/**
	 * @param string
	 */
	public void setMessage_Sent(String string) {
		message_Sent = string;
	}

	/**
	 * @param string
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public void reset()
	{
		subject = "0";
		message_Sent="";
	}
	
}
