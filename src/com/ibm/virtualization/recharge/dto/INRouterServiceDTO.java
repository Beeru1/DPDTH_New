/**
 * 
 */
package com.ibm.virtualization.recharge.dto;

/**
 * @author puneetla
 * 
 */
public class INRouterServiceDTO {
	private int externalConfigId;

	private int subsCircleId;
	private String airUrl;
	private String subscriberCircleCode; 
	
	private String wireLessCode;
	
	private String wireLineCode;
	
	private String dthCode;
	
	public String getAirUrl() {
		return airUrl;
	}

	public void setAirUrl(String airUrl) {
		this.airUrl = airUrl;
	}

	public int getExternalConfigId() {
		return externalConfigId;
	}

	public void setExternalConfigId(int externalConfigId) {
		this.externalConfigId = externalConfigId;
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

		retValue = "INRouterServiceDTO ( " + super.toString() + TAB
				+ "externalConfigId = " + this.externalConfigId + TAB
				+ "airUrl = " + this.airUrl + TAB + "subscriberCircleCode ="+ this.subscriberCircleCode+ TAB +" )";

		return retValue;
	}

	/**
	 * @return the subsCircleId
	 */
	public int getSubsCircleId() {
		return subsCircleId;
	}

	/**
	 * @param subsCircleId the subsCircleId to set
	 */
	public void setSubsCircleId(int circleId) {
		this.subsCircleId = circleId;
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
