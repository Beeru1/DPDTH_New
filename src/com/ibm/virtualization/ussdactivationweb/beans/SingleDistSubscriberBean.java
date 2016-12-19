package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author
 */
public class SingleDistSubscriberBean extends ActionForm
{
	 
	 private String strSrchDist;
	 private String searchDistCode;
	 private String searchDistName;
	 private List circleList;
	 private List distList;
	 private List mapdSubList;
	 private List unmpdSubList;
	 private String subscriberSIM;
	 private String subscriberIMSI;
	 private String subscriberMSISDN;
	 private String distributorName;
	 private String methodName;
	 private String selectedDist;
	 private String[] seldSub;
	 private String distributorId;
	 private String CircleId=null;
	 private String completeSIM;
	 private String strCrlTest;
	 
	 private String circleCode;
	 
	/**
	 * @return Returns the location.
	 */
	public int getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(int location) {
		this.location = location;
	}
	/**
	 * @return Returns the next.
	 */
	public int getNext() {
		return next;
	}
	/**
	 * @param next The next to set.
	 */
	public void setNext(int next) {
		this.next = next;
	}
	/**
	 * @return Returns the prev.
	 */
	public int getPrev() {
		return prev;
	}
	/**
	 * @param prev The prev to set.
	 */
	public void setPrev(int prev) {
		this.prev = prev;
	}
	/**
	 * @return Returns the prevDisable.
	 */
	public int getPrevDisable() {
		return prevDisable;
	}
	/**
	 * @param prevDisable The prevDisable to set.
	 */
	public void setPrevDisable(int prevDisable) {
		this.prevDisable = prevDisable;
	}
	 private int next=0;
		
	 private int prev=0;
	 
	 private int location=0;
		
     private int prevDisable=0;
	 
	 
	 
	/**
	 * @return Returns the circle_test.
	 */
	public String getCircleTest() {
		return strCrlTest;
	}
	/**
	 * @param circle_test The circle_test to set.
	 */
	public void setCircleTest(String strCrlTest) {
		this.strCrlTest = strCrlTest;
	}
		/**
	 * @return Returns the circleId.
	 */
	public String getCircleId() {
		return CircleId;
	}
	/**
	 * @param circleId The circleId to set.
	 */
	public void setCircleId(String circleId) {
		CircleId = circleId;
	}
	/**
	 * @return Returns the circleList.
	 */
	public List getCircleList() {
		return circleList;
	}
	/**
	 * @param circleList The circleList to set.
	 */
	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}
	/**
	 * @return Returns the completeSIM.
	 */
	public String getCompleteSIM() {
		return completeSIM;
	}
	/**
	 * @param completeSIM The completeSIM to set.
	 */
	public void setCompleteSIM(String completeSIM) {
		this.completeSIM = completeSIM;
	}
	/**
	 * @return Returns the distList.
	 */
	public List getDistList() {
		return distList;
	}
	/**
	 * @param distList The distList to set.
	 */
	public void setDistList(List distList) {
		this.distList = distList;
	}
	/**
	 * @return Returns the distributorId.
	 */
	public String getDistributorId() {
		return distributorId;
	}
	/**
	 * @param distributorId The distributorId to set.
	 */
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	/**
	 * @return Returns the distributorName.
	 */
	public String getDistributorName() {
		return distributorName;
	}
	/**
	 * @param distributorName The distributorName to set.
	 */
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	/**
	 * @return Returns the mappedSubscriberList.
	 */
	public List getMappedSubscriberList() {
		return mapdSubList;
	}
	/**
	 * @param mappedSubscriberList The mappedSubscriberList to set.
	 */
	public void setMappedSubscriberList(List mapdSubList) {
		this.mapdSubList = mapdSubList;
	}
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return Returns the searchDistBasis.
	 */
	public String getSearchDistBasis() {
		return strSrchDist;
	}
	/**
	 * @param searchDistBasis The searchDistBasis to set.
	 */
	public void setSearchDistBasis(String strSrchDist) {
		this.strSrchDist = strSrchDist;
	}
	/**
	 * @return Returns the searchDistCode.
	 */
	public String getSearchDistCode() {
		return searchDistCode;
	}
	/**
	 * @param searchDistCode The searchDistCode to set.
	 */
	public void setSearchDistCode(String searchDistCode) {
		this.searchDistCode = searchDistCode;
	}
	/**
	 * @return Returns the searchDistName.
	 */
	public String getSearchDistName() {
		return searchDistName;
	}
	/**
	 * @param searchDistName The searchDistName to set.
	 */
	public void setSearchDistName(String searchDistName) {
		this.searchDistName = searchDistName;
	}
	/**
	 * @return Returns the selectedDist.
	 */
	public String getSelectedDist() {
		return selectedDist;
	}
	/**
	 * @param selectedDist The selectedDist to set.
	 */
	public void setSelectedDist(String selectedDist) {
		this.selectedDist = selectedDist;
	}
	/**
	 * @return Returns the selectedSubscriber.
	 */
	public String[] getSelectedSubscriber() {
		return seldSub;
	}
	/**
	 * @param selectedSubscriber The selectedSubscriber to set.
	 */
	public void setSelectedSubscriber(String[] seleSubs) {
		this.seldSub = seleSubs;
	}
	/**
	 * @return Returns the subscriberIMSI.
	 */
	public String getSubscriberIMSI() {
		return subscriberIMSI;
	}
	/**
	 * @param subscriberIMSI The subscriberIMSI to set.
	 */
	public void setSubscriberIMSI(String subscriberIMSI) {
		this.subscriberIMSI = subscriberIMSI;
	}
	/**
	 * @return Returns the subscriberMSISDN.
	 */
	public String getSubscriberMSISDN() {
		return subscriberMSISDN;
	}
	/**
	 * @param subscriberMSISDN The subscriberMSISDN to set.
	 */
	public void setSubscriberMSISDN(String subscriberMSISDN) {
		this.subscriberMSISDN = subscriberMSISDN;
	}
	/**
	 * @return Returns the subscriberSIM.
	 */
	public String getSubscriberSIM() {
		return subscriberSIM;
	}
	/**
	 * @param subscriberSIM The subscriberSIM to set.
	 */
	public void setSubscriberSIM(String subscriberSIM) {
		this.subscriberSIM = subscriberSIM;
	}
	/**
	 * @return Returns the unmappedSubscriberList.
	 */
	public List getUnmappedSubscriberList() {
		return unmpdSubList;
	}
	/**
	 * @param unmappedSubscriberList The unmappedSubscriberList to set.
	 */
	public void setUnmappedSubscriberList(List unmpdSubList) {
		this.unmpdSubList = unmpdSubList;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
}