package com.ibm.dp.dto;

public class DPOpenStockDepletionDTO 
{
	
	Integer intTSMID;
	String strTSMName;
	Integer intDistID;
	String strDistName;
	Integer intProdID;
	String strProdName;
	
	public Integer getIntDistID() {
		return intDistID;
	}
	public void setIntDistID(Integer intDistID) {
		this.intDistID = intDistID;
	}
	public Integer getIntProdID() {
		return intProdID;
	}
	public void setIntProdID(Integer intProdID) {
		this.intProdID = intProdID;
	}
	public Integer getIntTSMID() {
		return intTSMID;
	}
	public void setIntTSMID(Integer intTSMID) {
		this.intTSMID = intTSMID;
	}
	public String getStrDistName() {
		return strDistName;
	}
	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}
	public String getStrProdName() {
		return strProdName;
	}
	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}
	public String getStrTSMName() {
		return strTSMName;
	}
	public void setStrTSMName(String strTSMName) {
		this.strTSMName = strTSMName;
	}
}
