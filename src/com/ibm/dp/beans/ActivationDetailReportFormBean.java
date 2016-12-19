package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class ActivationDetailReportFormBean extends ActionForm

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
	private String fromDate	;
	private String toDate	;
	private String productId = "";
	private List<SelectionCollection> arrCIList=null;
	private List<SelectionCollection> distList=null;
	private List<SelectionCollection> fseList=null;
	private List<SelectionCollection> retList =null;
	private List<SelectionCollection> tsmList =null;
	public List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();
	List<ActivationDetailReportDTO> reportStockDataList=new ArrayList<ActivationDetailReportDTO>();
	
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenProductTypeSelec;
	
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

	public List<ActivationDetailReportDTO> getReportStockDataList() {
		return reportStockDataList;
	}
	public void setReportStockDataList(List<ActivationDetailReportDTO> reportStockDataList) {
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
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenProductTypeSelec() {
		return hiddenProductTypeSelec;
	}
	public void setHiddenProductTypeSelec(String hiddenProductTypeSelec) {
		this.hiddenProductTypeSelec = hiddenProductTypeSelec;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}
	

}
