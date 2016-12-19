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
 * This class is used for Data Transfer of a Menu link params
 * 
 * @author Ashish
 * 
 */
public class Link implements Serializable , Comparable<Link>  {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	public int compareTo(Link o) {
        return Integer.parseInt(this.orderId) - Integer.parseInt(o.orderId) ;
    }
	
	
	private String linkId;
	
	private String orderId;

	private String linkDisplayUrl;

	private String linkDisplayLabel;

	private String topLinkName;

	private String topLinkDisplayLabel;

	public String getLinkDisplayUrl() {
		return linkDisplayUrl;
	}

	public void setLinkDisplayUrl(String linkDisplayUrl) {
		this.linkDisplayUrl = linkDisplayUrl;
	}

	public String getLinkDisplayLabel() {
		return linkDisplayLabel;
	}

	public void setLinkDisplayLabel(String linkDisplayLebel) {
		this.linkDisplayLabel = linkDisplayLebel;
	}

	public String getTopLinkDisplayLabel() {
		return topLinkDisplayLabel;
	}

	public void setTopLinkDisplayLabel(String topLinkDisplayLebel) {
		this.topLinkDisplayLabel = topLinkDisplayLebel;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getTopLinkName() {
		return topLinkName;
	}

	public void setTopLinkName(String topLinkName) {
		this.topLinkName = topLinkName;
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

		retValue = "Link ( " + super.toString() + TAB + "linkId = "
				+ this.linkId + TAB + "linkDisplayUrl = " + this.linkDisplayUrl
				+ TAB + "linkDisplayLabel = " + this.linkDisplayLabel + TAB
				+ "topLinkName = " + this.topLinkName + TAB
				+ "topLinkDisplayLabel = " + this.topLinkDisplayLabel + TAB
				+ " )";

		return retValue;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}