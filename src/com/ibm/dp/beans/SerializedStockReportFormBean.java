package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class SerializedStockReportFormBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;

	private String  groupId;
	private String isSelectAll;	
	private String isSelectAllDist;	
	private int circleid = -1;
	private String tsmId;	
	private String distId;
	private String fseId;
	private String retId;
	private String date	;
	private String productId = "";
	private List<SelectionCollection> arrCIList=null;
	private List<SelectionCollection> distList=null;
	private List<SelectionCollection> fseList=null;
	private List<SelectionCollection> retList =null;
	private List<SelectionCollection> tsmList =null;
	public List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();
	List<SerializedStockReportDTO> reportStockDataList=new ArrayList<SerializedStockReportDTO>();
	
	private String showCircle="";
	private String showTSM="";
	private String noShowTSM="";
	private String showDist="";	
	public String getNoShowTSM() {
		return noShowTSM;
	}
	public void setNoShowTSM(String noShowTSM) {
		this.noShowTSM = noShowTSM;
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
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public List<SelectionCollection> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<SelectionCollection> tsmList) {
		this.tsmList = tsmList;
	}
	public List<SelectionCollection> getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(List<SelectionCollection> arrCIList) {
		this.arrCIList = arrCIList;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public List<DpProductCategoryDto> getProductList() {
		return productList;
	}
	public void setProductList(List<DpProductCategoryDto> productList) {
		this.productList = productList;
	}
	public List<SerializedStockReportDTO> getReportStockDataList() {
		return reportStockDataList;
	}
	public void setReportStockDataList(List<SerializedStockReportDTO> reportStockDataList) {
		this.reportStockDataList = reportStockDataList;
	}
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public List<SelectionCollection> getDistList() {
		return distList;
	}
	public void setDistList(List<SelectionCollection> distList) {
		this.distList = distList;
	}
	public List<SelectionCollection> getFseList() {
		return fseList;
	}
	public void setFseList(List<SelectionCollection> fseList) {
		this.fseList = fseList;
	}
	public String getFseId() {
		return fseId;
	}
	public void setFseId(String fseId) {
		this.fseId = fseId;
	}
	public String getRetId() {
		return retId;
	}
	public void setRetId(String retId) {
		this.retId = retId;
	}
	public List<SelectionCollection> getRetList() {
		return retList;
	}
	public void setRetList(List<SelectionCollection> retList) {
		this.retList = retList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getIsSelectAll() {
		return isSelectAll;
	}
	public void setIsSelectAll(String isSelectAll) {
		this.isSelectAll = isSelectAll;
	}
	public String getIsSelectAllDist() {
		return isSelectAllDist;
	}
	public void setIsSelectAllDist(String isSelectAllDist) {
		this.isSelectAllDist = isSelectAllDist;
	}
	

}
