package com.ibm.dp.dto;

import java.io.Serializable;

public class DPDistributorStockTransferDTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer intDistID;
	private String strDistName;
	private Integer intFromDistID;
	private Integer intToDistID;
	private Integer intProductID;
	private String strProductName;
	private String circleCode;
	private String circleName;
	private Integer circleId;
	
	public Integer getIntDistID() {
		return intDistID;
	}
	public void setIntDistID(Integer intDistID) {
		this.intDistID = intDistID;
	}
	public Integer getIntProductID() {
		return intProductID;
	}
	public void setIntProductID(Integer intProductID) {
		this.intProductID = intProductID;
	}
	public String getStrDistName() {
		return strDistName;
	}
	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}
	public String getStrProductName() {
		return strProductName;
	}
	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public Integer getIntFromDistID() {
		return intFromDistID;
	}
	public void setIntFromDistID(Integer intFromDistID) {
		this.intFromDistID = intFromDistID;
	}
	public Integer getIntToDistID() {
		return intToDistID;
	}
	public void setIntToDistID(Integer intToDistID) {
		this.intToDistID = intToDistID;
	}
}
