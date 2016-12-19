package com.ibm.dp.dto;

import java.io.Serializable;

public class ReportForUnbarringDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int circleId;
	private String serialNo;
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
