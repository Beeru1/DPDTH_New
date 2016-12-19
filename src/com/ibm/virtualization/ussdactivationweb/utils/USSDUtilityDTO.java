
/*
 * Created on Feb 26, 2008
 * 
 */
  
 
package com.ibm.virtualization.ussdactivationweb.utils;

/**
 * @author Ashad
 *
 *  This class contains the information of Paging process
 */
public class USSDUtilityDTO {

	private String strNext;
	private String strPrevPage;
	private int intUpperBound;
	private int intLowerBound;
	
	/**
	 * @return Returns the strNext.
	 */
	public String getNext() {
		return strNext;
	}
	/**
	 * @param strNext The strNext to set.
	 */
	public void setNext(String strNext) {
		this.strNext = strNext;
	}
	/**
	 * @return Returns the strPrevPage.
	 */
	public String getPrevPage() {
		return strPrevPage;
	}
	/**
	 * @param strPrevPage The strPrevPage to set.
	 */
	public void setPrevPage(String strPrevPage) {
		this.strPrevPage = strPrevPage;
	}
	
	/**
	 * @return Returns the intLowerBound.
	 */
	public int getLowerBound() {
		return intLowerBound;
	}
	/**
	 * @param intLowerBound The intLowerBound to set.
	 */
	public void setLowerBound(int intLowerBound) {
		this.intLowerBound = intLowerBound;
	}
	/**
	 * @return Returns the intUpperBound.
	 */
	public int getUpperBound() {
		return intUpperBound;
	}
	/**
	 * @param intUpperBound The intUpperBound to set.
	 */
	public void setUpperBound(int intUpperBound) {
		this.intUpperBound = intUpperBound;
	}
	
	
}
