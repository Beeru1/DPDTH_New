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

/* This class will be used as message for post paid. */
public class PostPaidMessageBean extends RechargeMessageBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7004557968174180931L;

	private String abtsContactNumber;

	public String getAbtsContactNumber() {
		return abtsContactNumber;
	}

	public void setAbtsContactNumber(String abtsContactNumber) {
		this.abtsContactNumber = abtsContactNumber;
	}

	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue = retValue.append("<PostPaidMessageBean>").append(
				"<abtsContactNumber>").append(this.abtsContactNumber).append(
				"</abtsContactNumber>").append(super.toString()).append(
				"</PostPaidMessageBean>");
		return retValue.toString();
	}
	

	public String XMLMessage(long sourceCircleId){
		StringBuffer xml = new StringBuffer();
		xml.append("<PostPaidTransaction>")
		.append("<subscriberCircleId>").append(this.getSubscriberCircleId()).append("</subscriberCircleId>")
		.append("<requesterCircleId>").append(sourceCircleId).append("</requesterCircleId>").append("<sourceCircleCode>").append(getSourceCircleCode()).append("</sourceCircleCode>").append("<subscriberCircleCode>").append(getSubscriberCircleCode()).append("</subscriberCircleCode>")
		.append("</PostPaidTransaction>");
		return xml.toString();
	}
	
}
