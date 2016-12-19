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

import com.ibm.virtualization.framework.bean.ConnectionBean;

/**
 * @author shilpi
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MobileNumberSeries {

	// Series Number
	private int seriesNo;

	// Name of the Series
	private String seriesName;

	// Start of Number Series
	private long seriesFrom;

	// End of Number Series
	private long seriesTo;

	// Request Q Details
	private ConnectionBean reqConnBean;

	// IN Q Details
	private ConnectionBean inConnBean;

	// OUT Q Details
	private ConnectionBean outConnBean;

	/**
	 * @return Returns the seriesNo.
	 */
	public int getSeriesNo() {
		return seriesNo;
	}

	/**
	 * @param seriesNo
	 *            The seriesNo to set.
	 */
	public void setSeriesNo(int seriesNo) {
		this.seriesNo = seriesNo;
	}

	/**
	 * @return Returns the inConnBean.
	 */
	public ConnectionBean getInConnBean() {
		return inConnBean;
	}

	/**
	 * @param inConnBean
	 *            The inConnBean to set.
	 */
	public void setInConnBean(ConnectionBean inConnBean) {
		this.inConnBean = inConnBean;
	}

	/**
	 * @return Returns the outConnBean.
	 */
	public ConnectionBean getOutConnBean() {
		return outConnBean;
	}

	/**
	 * @param outConnBean
	 *            The outConnBean to set.
	 */
	public void setOutConnBean(ConnectionBean outConnBean) {
		this.outConnBean = outConnBean;
	}

	/**
	 * @return Returns the reqConnBean.
	 */
	public ConnectionBean getReqConnBean() {
		return reqConnBean;
	}

	/**
	 * @param reqConnBean
	 *            The reqConnBean to set.
	 */
	public void setReqConnBean(ConnectionBean reqConnBean) {
		this.reqConnBean = reqConnBean;
	}

	/**
	 * @return Returns the seriesFrom.
	 */
	public long getSeriesFrom() {
		return seriesFrom;
	}

	/**
	 * @param seriesFrom
	 *            The seriesFrom to set.
	 */
	public void setSeriesFrom(long seriesFrom) {
		this.seriesFrom = seriesFrom;
	}

	/**
	 * @return Returns the seriesName.
	 */
	public String getSeriesName() {
		return seriesName;
	}

	/**
	 * @param seriesName
	 *            The seriesName to set.
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	/**
	 * @return Returns the seriesTo.
	 */
	public long getSeriesTo() {
		return seriesTo;
	}

	/**
	 * @param seriesTo
	 *            The seriesTo to set.
	 */
	public void setSeriesTo(long seriesTo) {
		this.seriesTo = seriesTo;
	}
	
	public String toString(){
		return "SeriesNo = " + seriesNo + " SeriesName = " + seriesName + " SeriesFrom = " + seriesFrom + " SeriesTo = " + seriesTo + " ReqConnBean = " + reqConnBean + " InConnBean = " + inConnBean + " OutConnBean = " + outConnBean + "\n";
		
	}
}