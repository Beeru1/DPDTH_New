package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPViewHierarchyDTO;

public class DPViewHierarchyFormBean extends ActionForm

{
	String accountName;
	String accountId;
	String distAccId;
	String distAccName;
	String role;
	String productName;
	String distID;
	String distName;
	List distList=new ArrayList();
	private ArrayList arrCGList=null;
	private int circleid = 0;
	private ArrayList arrCIList=null;
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	String fromDate;
	String toDate;
	List<DPViewHierarchyDTO> SCMexceptionReportList;
	List<DPViewHierarchyDTO> revLogReportList;
	private List tsmList = new ArrayList();
	private List revLogList = new ArrayList();
	private List getHierarchyList = new ArrayList();
	private List distArrayList = new ArrayList();
	private String fromTsmId;
	private String[] strCheckedVHD;
	private String[] strCheckedVHDAll;
	
	private List hierarchyList= new ArrayList();
	
	private String strGetReport;
	

	public String[] getStrCheckedVHD() {
		return strCheckedVHD;
	}
	public void setStrCheckedVHD(String[] strCheckedVHD) {
		this.strCheckedVHD = strCheckedVHD;
	}
	public List getGetHierarchyList() {
		return getHierarchyList;
	}
	public void setGetHierarchyList(List getHierarchyList) {
		this.getHierarchyList = getHierarchyList;
	}
	public List getRevLogList() {
		return revLogList;
	}
	public void setRevLogList(List revLogList) {
		this.revLogList = revLogList;
	}

	public String getDistID() {
		return distID;
	}
	public void setDistID(String distID) {
		this.distID = distID;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public ArrayList getArrCGList() {
		return arrCGList;
	}
	public void setArrCGList(ArrayList arrCGList) {
		this.arrCGList = arrCGList;
	}
	public ArrayList getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<DPViewHierarchyDTO> getSCMexceptionReportList() {
		return SCMexceptionReportList;
	}
	public void setSCMexceptionReportList(
			List<DPViewHierarchyDTO> mexceptionReportList) {
		SCMexceptionReportList = mexceptionReportList;
	}
	
	
	
	public List<DPViewHierarchyDTO> getRevLogReportList() {
		return revLogReportList;
	}
	public void setRevLogReportList(
			List<DPViewHierarchyDTO> revLogReportList) {
		this.revLogReportList = revLogReportList;
	}
	public List getTsmList() {
		return tsmList;
	}
	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getDistAccId() {
		return distAccId;
	}
	public void setDistAccId(String distAccId) {
		this.distAccId = distAccId;
	}
	public String getDistAccName() {
		return distAccName;
	}
	public void setDistAccName(String distAccName) {
		this.distAccName = distAccName;
	}
	public String getShowDist() {
		return showDist;
	}
	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}
	public String getShowTSM() {
		return showTSM;
	}
	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}
	public String getStrGetReport() {
		return strGetReport;
	}
	public void setStrGetReport(String strGetReport) {
		this.strGetReport = strGetReport;
	}
	public List getDistArrayList() {
		return distArrayList;
	}
	public void setDistArrayList(List distArrayList) {
		this.distArrayList = distArrayList;
	}
	public String[] getStrCheckedVHDAll() {
		return strCheckedVHDAll;
	}
	public void setStrCheckedVHDAll(String[] strCheckedVHDAll) {
		this.strCheckedVHDAll = strCheckedVHDAll;
	}
	public List getHierarchyList() {
		return hierarchyList;
	}
	public void setHierarchyList(List hierarchyList) {
		this.hierarchyList = hierarchyList;
	}
	public String getFromTsmId() {
		return fromTsmId;
	}
	public void setFromTsmId(String fromTsmId) {
		this.fromTsmId = fromTsmId;
	}

}
