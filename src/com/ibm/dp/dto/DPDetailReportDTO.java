package com.ibm.dp.dto;

import java.util.Date;

public class DPDetailReportDTO {

	  	String fromDate;
	  	String toDate;
	  	private String circle = "";
		String accountName;
		String role;
		String productName;
		String tsmID;
		String tsmName;
		String distID;
		String distName;
		private String login_Name;
		private int churnInventory;
		private int upgradeInventory;
		private int doaInventory;
		private int defectiveInventory;
		private int c2sInventory;
		String loginName;
		String productCode;

	  
//	getters and setters

	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getDistID() {
		return distID;
	}
	public void setDistID(String distID) {
		this.distID = distID;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getTsmID() {
		return tsmID;
	}
	public void setTsmID(String tsmID) {
		this.tsmID = tsmID;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getLogin_Name() {
		return login_Name;
	}
	public void setLogin_Name(String login_Name) {
		this.login_Name = login_Name;
	}
	public String getFromDate1() {
		return fromDate;
	}
	public void setFromDate1(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getToDate1() {
		return toDate;
	}
	public void setToDate1(String toDate) {
		this.toDate = toDate;
	}
	public int getChurnInventory() {
		return churnInventory;
	}
	public void setChurnInventory(int churnInventory) {
		this.churnInventory = churnInventory;
	}
	public int getDefectiveInventory() {
		return defectiveInventory;
	}
	public void setDefectiveInventory(int defectiveInventory) {
		this.defectiveInventory = defectiveInventory;
	}
	public int getDoaInventory() {
		return doaInventory;
	}
	public void setDoaInventory(int doaInventory) {
		this.doaInventory = doaInventory;
	}
	public int getUpgradeInventory() {
		return upgradeInventory;
	}
	public void setUpgradeInventory(int upgradeInventory) {
		this.upgradeInventory = upgradeInventory;
	}
	public int getC2sInventory() {
		return c2sInventory;
	}
	public void setC2sInventory(int c2sInventory) {
		this.c2sInventory = c2sInventory;
	}
	
	  

}
