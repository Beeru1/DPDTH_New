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

import java.io.Serializable;

/**
 * This class is used for Data Transfer of a Group between different layers.
 * 
 * @author bhanu
 * 
 */
public class AccountAccessLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143785149144381711L;

	private int accessLevelId;

	private String accountAccessLevel; 

	private String accountLevelName; 
	
	private String heirarchyId;
	
	public String getHeirarchyId() {
		return heirarchyId;
	}


	public void setHeirarchyId(String heirarchyId) {
		this.heirarchyId = heirarchyId;
	}


	/**
	 * @return the accessLevelId
	 */
	public int getAccessLevelId() {
		return accessLevelId;
	}


	/**
	 * @param accessLevelId the accessLevelId to set
	 */
	public void setAccessLevelId(int accessLevelId) {
		this.accessLevelId = accessLevelId;
	}


	/**
	 * @return the accountAccessLevel
	 */
	public String getAccountAccessLevel() {
		return accountAccessLevel;
	}


	/**
	 * @param accountAccessLevel the accountAccessLevel to set
	 */
	public void setAccountAccessLevel(String accountAccessLevel) {
		this.accountAccessLevel = accountAccessLevel;
	}


	/**
	 * @return the accountLevelName
	 */
	public String getAccountLevelName() {
		return accountLevelName;
	}


	/**
	 * @param accountLevelName the accountLevelName to set
	 */
	public void setAccountLevelName(String accountLevelName) {
		this.accountLevelName = accountLevelName;
	}


	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "AccountAccessLevel ( " + super.toString() + TAB + "accessLevelId = "
				+ this.accessLevelId + TAB + "accountLevelName = " + this.accountLevelName + TAB
				+ "accountAccessLevel = " + this.accountAccessLevel+ ")";

		return retValue;
	}

	

	
}