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
import java.util.Date;

import com.ibm.virtualization.framework.bean.ChannelType;

/**
 * This class is used for Data Transfer of a Circle between different layers.
 * 
 * @author Paras
 * 
 */
public class InMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493965352309549247L;

	private String mobileSeriesId;

	private String primaryId;

	private String secondryId;

	private int circleId;

	private long externalIntType;

	private String externalUrl;

	private int externalId;
	
	private String subscriberCircleCode;
	
	private String wireLessCode;
	
	private String wireLineCode;
	
	private String dthCode;

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public int getExternalId() {
		return externalId;
	}

	public void setExternalId(int externalId) {
		this.externalId = externalId;
	}

	public long getExternalIntType() {
		return externalIntType;
	}

	public void setExternalIntType(long externalIntType) {
		this.externalIntType = externalIntType;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getSecondryId() {
		return secondryId;
	}

	public void setSecondryId(String secondryId) {
		this.secondryId = secondryId;
	}

	public String getMobileSeriesId() {
		return mobileSeriesId;
	}

	public void setMobileSeriesId(String mobileSeriesId) {
		this.mobileSeriesId = mobileSeriesId;
	}
	
	public String toString(){
		return "Circle Id = " + circleId + " ExternalId =" + externalId  + " ExternalIntType = " + externalIntType  + " ExternalUrl = " + externalUrl  + " PrimaryId = " + primaryId  + " SecondryId = " + secondryId  + " MobileSeriesId = " + mobileSeriesId;
	}

	public String getSubscriberCircleCode() {
		return subscriberCircleCode;
	}

	public void setSubscriberCircleCode(String subscriberCircleCode) {
		this.subscriberCircleCode = subscriberCircleCode;
	}

	public String getDthCode() {
		return dthCode;
	}

	public void setDthCode(String dthCode) {
		this.dthCode = dthCode;
	}

	public String getWireLessCode() {
		return wireLessCode;
	}

	public void setWireLessCode(String wireLessCode) {
		this.wireLessCode = wireLessCode;
	}

	public String getWireLineCode() {
		return wireLineCode;
	}

	public void setWireLineCode(String wireLineCode) {
		this.wireLineCode = wireLineCode;
	}
}
