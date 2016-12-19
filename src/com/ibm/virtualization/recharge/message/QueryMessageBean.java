/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.message;

import com.ibm.virtualization.recharge.common.QueryRequestType;

/**
 * Message Bean object for Query
 * 
 * @author ashish
 * 
 */
public class QueryMessageBean extends CoreMessageBean {
	private static final long serialVersionUID = 95;

	// Mobile Number For which balance is to be enquired
	private String chdAccountMobileNo;

	// Opening balance of the account
	private double operatingBalance;

	// available balance of the account
	private double availableBalance;

	// Opening balance of the account
	private double openingBalance;

	// Request Type either 0 for self balance enquiry or 1 for Child balance
	// enquiry
	private QueryRequestType requestType;

	/**
	 * @return Returns the chdAccountMobileNo.
	 */
	public String getChdAccountMobileNo() {
		return chdAccountMobileNo;
	}

	/**
	 * @param chdAccountMobileNo
	 *            The chdAccountMobileNo to set.
	 */
	public void setChdAccountMobileNo(String balMobileNo) {
		this.chdAccountMobileNo = balMobileNo;
	}

	/**
	 * @return the openingBalance
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            the openingBalance to set
	 */
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return the availableBalance
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            the availableBalance to set
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the operatingBalance
	 */
	public double getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            the operatingBalance to set
	 */
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	public QueryRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(QueryRequestType requestType) {
		this.requestType = requestType;
	}
	
	/**
	 * detailed message for the Out Q listener
	 * will be inserted in the transaction
	 * @return String - message in xml format
	 */
	public String getXML() {
		StringBuilder retValue = new StringBuilder();

		retValue.append("<QueryMessageBean>");
		if (this.requestType != null) {
			retValue.append("<responseMsg>").append(this.getResponseMessage()).append(
					"</responseMsg>").append(super.toString());
		}
		if (this.getChdAccountMobileNo() != null
				&& this.getChdAccountMobileNo().length() == 10) {
			retValue.append("<ChildMobileNo>").append(
					this.getChdAccountMobileNo()).append("</ChildMobileNo>");
		}		
		retValue.append("</QueryMessageBean>");
		return retValue.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.message.CoreMessageBean#toString()
	 */
	public String toString() {
		StringBuilder retValue = new StringBuilder();
		if (this.getChdAccountMobileNo() != null
				&& this.getChdAccountMobileNo().length() == 10) {
			retValue.append("<ChildMobileNo>").append(
					this.getChdAccountMobileNo()).append("</ChildMobileNo>");
		}
		retValue.append(super.toString());

		return retValue.toString();
	}
}
