/*****************************************************************************\
 **
 ** Virtualization - ModernTraderRequestDTO.java - Moderern Trade.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 *  Nov 1, 2007 3:19:10 PM
 *  @author Mohit Aggarwal
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.recharge.common.RequestType;

/**
 * This class contains holds the data for the inputs fields which are
 * required while diffenent stages of the Modern Trade business transactions
 *
 */
public class ModernTradeInputs {
	
	/** circle Id */
	private int circleId;
	
	/** status */
	private String status;
	
	/** created start date */
	private String startDt;
	
	/** created end date */
	private String endDt;

	/** search Type */
	private String searchFieldName;
	
	/** search Value */
	private String searchFieldValue;
	
	/** Session Context */
	private UserSessionContext sessionContext;
	
	/** parentID*/
	private long parentId;

	/** Transaction Type */
	private RequestType requestType;
	


	/**
	 * @return the parentId
	 */
	public long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the searchFieldName
	 */
	public String getSearchFieldName() {
		return searchFieldName;
	}

	/**
	 * @param searchFieldName the searchFieldName to set
	 */
	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	/**
	 * @return the searchFieldValue
	 */
	public String getSearchFieldValue() {
		return searchFieldValue;
	}

	/**
	 * @param searchFieldValue the searchFieldValue to set
	 */
	public void setSearchFieldValue(String searchFieldValue) {
		this.searchFieldValue = searchFieldValue;
	}

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the sessionContext
	 */
	public UserSessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext the sessionContext to set
	 */
	public void setSessionContext(UserSessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	
	
	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "ModernTradeInputs ( " + super.toString() + TAB + "circleId = "
				+ this.circleId + TAB + "status = " + this.status + TAB 
				+ "startDt = " + this.startDt + TAB
				+ "endDt = " + this.endDt + TAB + "searchFieldName = "
				+ this.searchFieldName + TAB + "searchFieldValue = " + this.searchFieldValue + TAB
				+ "parentId = " + this.parentId + TAB
				+ "requestType = " + this.requestType + TAB
				+ "sessionContext.circleId = " + this.sessionContext.getCircleId() + TAB
				+ "sessionContext.id = " + this.sessionContext.getId() + TAB
				+ "sessionContext.getLoginName = " + this.sessionContext.getLoginName() + TAB + "sessionContext.status = "
				+ this.sessionContext.getStatus()  
				+ TAB + " )";

		return retValue;
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	
}
