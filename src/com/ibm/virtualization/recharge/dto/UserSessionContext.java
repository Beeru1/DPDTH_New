/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import java.util.ArrayList;

/**
 * Bean for Authenticated User Information
 * 
 * @author Paras
 * 
 */
public class UserSessionContext {

	protected long id;

	protected String loginName;

	protected String type;

	protected int groupId;

	protected int circleId;
	
	protected String circleName="";
	
	protected ArrayList roleList;

	protected String Status;

	protected long billableAccId;
	
	protected String accountAddress;
	
	protected String circleCode;
	
	protected String lapuMobileNo;
	
	protected String accountLevel;
	
	protected int heirarchyId;
	
	protected int accesslevel;
// for distributor screen of create account.	
	protected int tradeId;
	
	protected int tradeCategoryId;
	
	protected String contactName;
	
	protected String accountCode;
	
	protected String authType;
	
	protected int accountCityId;
	
	protected String accountCityName;
	
	protected String accountZoneName;
	
	protected int accountZoneId;
	
	protected String accountStateName;
	
	protected int accountStateId;
	
	protected String accountLevelName;
	
	protected String accountName;
	
	protected String userRole;
	
	protected String outletType;
	
	/* Added by Parnika */
	
	private String depositeTypeTSM;

	private String salesTypeTSM;
	
	/* End of changes by Parnika */
	
	
		public String getOutletType() {
		return outletType;
	}

	public String getDepositeTypeTSM() {
			return depositeTypeTSM;
		}

		public void setDepositeTypeTSM(String depositeTypeTSM) {
			this.depositeTypeTSM = depositeTypeTSM;
		}

		public String getSalesTypeTSM() {
			return salesTypeTSM;
		}

		public void setSalesTypeTSM(String salesTypeTSM) {
			this.salesTypeTSM = salesTypeTSM;
		}

	public void setOutletType(String outletType) {
		this.outletType = outletType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountLevelName() {
		return accountLevelName;
	}

	public void setAccountLevelName(String accountLevelName) {
		this.accountLevelName = accountLevelName;
	}

	public int getAccountCityId() {
		return accountCityId;
	}

	public void setAccountCityId(int accountCityId) {
		this.accountCityId = accountCityId;
	}

	public int getAccountZoneId() {
		return accountZoneId;
	}

	public void setAccountZoneId(int accountZoneId) {
		this.accountZoneId = accountZoneId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getLapuMobileNo() {
		return lapuMobileNo;
	}

	public void setLapuMobileNo(String lapuMobileNo) {
		this.lapuMobileNo = lapuMobileNo;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	/**
	 * @return the accountAddress
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * @param accountAddress the accountAddress to set
	 */
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	/**
	 * @return Returns the groupId.
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            The groupId to set.
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return Returns the loginId.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId
	 *            the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue = retValue.append("<UserSessionContext>").append("<id>")
				.append(this.id).append("</id>").append("<loginName>").append(
						this.loginName).append("</loginName>").append("<type>")
				.append(this.type).append("</type>").append("<groupId>")
				.append(this.groupId).append("</groupId>").append("<circleId>")
				.append(this.circleId).append("</circleId>").append("<Status>")
				.append(this.Status).append("</Status>").append(
						"<billableAccId>").append(this.billableAccId).append(
						"</billableAccId>").append(
						"<circleCode>").append(this.circleCode).append(
						"</circleCode>").append("</UserSessionContext>");

		return retValue.toString();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.Status = status;
	}

	/**
	 * @return the billableAccId
	 */
	public long getBillableAccId() {
		return billableAccId;
	}

	/**
	 * @param billableAccId
	 *            the billableAccId to set
	 */
	public void setBillableAccId(long billableAccId) {
		this.billableAccId = billableAccId;
	}

	public int getTradeCategoryId() {
		return tradeCategoryId;
	}

	public void setTradeCategoryId(int tradeCategoryId) {
		this.tradeCategoryId = tradeCategoryId;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getAccesslevel() {
		return accesslevel;
	}

	public void setAccesslevel(int accesslevel) {
		this.accesslevel = accesslevel;
	}

	public int getHeirarchyId() {
		return heirarchyId;
	}

	public void setHeirarchyId(int heirarchyId) {
		this.heirarchyId = heirarchyId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAccountZoneName() {
		return accountZoneName;
	}

	public void setAccountZoneName(String accountZoneName) {
		this.accountZoneName = accountZoneName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getAccountCityName() {
		return accountCityName;
	}

	public void setAccountCityName(String accountCityName) {
		this.accountCityName = accountCityName;
	}

	public ArrayList getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

	public int getAccountStateId() {
		return accountStateId;
	}

	public void setAccountStateId(int accountStateId) {
		this.accountStateId = accountStateId;
	}

	public String getAccountStateName() {
		return accountStateName;
	}

	public void setAccountStateName(String accountStateName) {
		this.accountStateName = accountStateName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
