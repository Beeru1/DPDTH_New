/*
 * Created on July 22, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.utilities;

/**************************************************************************
 * File     : Pagination.java
 * Author   : aalok
 * Created  : Jul 24, 2008
 * Modified : Jul 24, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Jul 24, 2008 	aalok	First Cut.
 * V0.2		Jul 24, 2008  	aalok	First modification
 **************************************************************************
 *
 * Copyright @ 2008 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

/*******************************************************************************
 * This is a Generic Utility Class which provides helper methods for
 * 
 * 
 * @author aalok
 * @version 1.0
 ******************************************************************************/

public class Pagination {

	private int upperBound;
	private int lowerBound;

	/**
	 * setters and getters for the lower and upper bound for Pagination
	 */
	public int getLowerBound() {
		return lowerBound;
	}

	 /**
     * @param lowerBound int
     *            The lowerBound to set.
     */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	 /**
     * @return upperBound - Returns the upperBound.
     */
	public int getUpperBound() {
		return upperBound;
	}

	 /**
     * @param upperBound int
     *            The upperBound to set.
     */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
}
