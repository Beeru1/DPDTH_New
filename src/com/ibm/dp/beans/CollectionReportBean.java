package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class CollectionReportBean extends ActionForm{
	
	private String showCircle="";
	private String showTSM="";
	private String showDist="";	
	private String circleId;
	private String circleName;
	private String tsmId;	
	private String distId;	
	private String productId;
	private String fromDate;
	private String toDate;
	private String poStatus;
	private String dateOption;
	private String status;
	private String searchCriteria;
	private String methodName;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenSTBTypeSelec;
	private String distLoginId;
	private String hiddenCollectionTypeSelec;
	private String hiddenStatusSelec;
	private String stbType;
	private String inventoryChangeDate;
	private String recoveredSTB;
	private String installedSTB;
	private String dcNo;
	private String hiddenProductSeletIds;

	private List<DpProductCategoryDto> productList =new ArrayList<DpProductCategoryDto>();
	private List<CollectionReportDTO> collectionType;
	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> tsmList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> distList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> poStatusList =new ArrayList<SelectionCollection>();
	private List<CollectionReportDTO> reportList =new ArrayList<CollectionReportDTO>();
	private String collectionName;
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public List<SelectionCollection> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
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
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public List<SelectionCollection> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<SelectionCollection> tsmList) {
		this.tsmList = tsmList;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}
	
	public List<SelectionCollection> getPoStatusList() {
		return poStatusList;
	}
	public void setPoStatusList(List<SelectionCollection> poStatusList) {
		this.poStatusList = poStatusList;
	}
	
	public List<CollectionReportDTO> getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(List<CollectionReportDTO> collectionType) {
		this.collectionType = collectionType;
	}
	
	public String getHiddenCollectionTypeSelec() {
		return hiddenCollectionTypeSelec;
	}
	public void setHiddenCollectionTypeSelec(String hiddenCollectionTypeSelec) {
		this.hiddenCollectionTypeSelec = hiddenCollectionTypeSelec;
	}
	public String getHiddenStatusSelec() {
		return hiddenStatusSelec;
	}
	public void setHiddenStatusSelec(String hiddenStatusSelec) {
		this.hiddenStatusSelec = hiddenStatusSelec;
	}
	public String getHiddenSTBTypeSelec() {
		return hiddenSTBTypeSelec;
	}
	public void setHiddenSTBTypeSelec(String hiddenSTBTypeSelec) {
		this.hiddenSTBTypeSelec = hiddenSTBTypeSelec;
	}
	public List<CollectionReportDTO> getReportList() {
		return reportList;
	}
	public void setReportList(List<CollectionReportDTO> reportList) {
		this.reportList = reportList;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public String getInstalledSTB() {
		return installedSTB;
	}
	public void setInstalledSTB(String installedSTB) {
		this.installedSTB = installedSTB;
	}
	public String getInventoryChangeDate() {
		return inventoryChangeDate;
	}
	public void setInventoryChangeDate(String inventoryChangeDate) {
		this.inventoryChangeDate = inventoryChangeDate;
	}
	public String getRecoveredSTB() {
		return recoveredSTB;
	}
	public void setRecoveredSTB(String recoveredSTB) {
		this.recoveredSTB = recoveredSTB;
	}
	public String getHiddenProductSeletIds() {
		return hiddenProductSeletIds;
	}
	public void setHiddenProductSeletIds(String hiddenProductSeletIds) {
		this.hiddenProductSeletIds = hiddenProductSeletIds;
	}
	public List<DpProductCategoryDto> getProductList() {
		return productList;
	}
	public void setProductList(List<DpProductCategoryDto> productList) {
		this.productList = productList;
	}
	
	

}
