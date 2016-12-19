package com.ibm.hbo.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class HBOSendEmailDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5463740774831073427L;
	private String senderId;
	private String message_Sent;
	private String receiverId;
	private String ccReceiverId;
	private String subjectId;
	ArrayList subjectList;
	private String subject;
	private String lapuMobileNo;
	private String message;
	public String getCcReceiverId() {
		return ccReceiverId;
	}
	public void setCcReceiverId(String ccReceiverId) {
		this.ccReceiverId = ccReceiverId;
	}
	public String getLapuMobileNo() {
		return lapuMobileNo;
	}
	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage_Sent() {
		return message_Sent;
	}
	public void setMessage_Sent(String message_Sent) {
		this.message_Sent = message_Sent;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
