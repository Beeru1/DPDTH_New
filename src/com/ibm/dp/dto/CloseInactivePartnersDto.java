package com.ibm.dp.dto;
import java.util.LinkedList;

public class CloseInactivePartnersDto {
	int msgId;
	String filePath;
	String olmid;
	
	String status;
	int rowNo;
	
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public String getOlmid() {
		return olmid;
	}
	public void setOlmid(String olmid) {
		this.olmid = olmid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

}
