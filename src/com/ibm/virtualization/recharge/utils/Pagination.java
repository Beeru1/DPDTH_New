package com.ibm.virtualization.recharge.utils;


/**
 * This is a Generic Utility Class which provides helper methods for
 * Virtualization Recharge.
 * 
 * @author Ashish
 * 
 */

public class Pagination {

	private int upperBound;

	private int lowerBound;

	/**
	 * setting the lower and upper bound for Pagination
	 * 
	 * @param request
	 * @param noofpages
	 */
	
	
	 /**
     * @return Returns the lowerBound.
     */
	public int getLowerBound() {
		return lowerBound;
	}

	 /**
     * @param lowerBound
     *            The lowerBound to set.
     */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	 /**
     * @return Returns the upperBound.
     */
	public int getUpperBound() {
		return upperBound;
	}

	 /**
     * @param upperBound
     *            The upperBound to set.
     */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
}
