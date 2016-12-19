package com.ibm.dp.dto;

import java.io.Serializable;

public class SerializedStockReportDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String SNO = "";
	 public int distID = 0;
	 public String distName = "";
	 public String tsmName="";
	 public int circle = 0;
	 public int accountType = 0;
	 public String accountName;
	 public String parentAccountName;
	 public String stbType;
	 public String serializedStock;
	 
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public int getDistID() {
		return distID;
	}
	public void setDistID(int distID) {
		this.distID = distID;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getParentAccountName() {
		return parentAccountName;
	}
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	public String getSerializedStock() {
		return serializedStock;
	}
	public void setSerializedStock(String serializedStock) {
		this.serializedStock = serializedStock;
	}
	public String getSNO() {
		return SNO;
	}
	public void setSNO(String sno) {
		SNO = sno;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	  
}
