package com.ibm.dp.dto;

import java.sql.Date;
import java.util.List;

public class InterSSDReportDTO {

	private String showCircle="";
	public String productCategory= "";
	 public String collectionId = "";
	 public String collectionName = "";
	private String showTSM="";
	private String showDist="";	
	private String fromDate;
	private String toDate;
	private String dateOption;
	private String searchOption;
	//private String searchCriteria;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenPoStatusSelec;
	private String distName;
	private String tsmName;
	private String toDistName;
	private String toTsmName;
	private String zbmName;
	private String collectionType;
	private String circleName;
	private String hiddenSTBTypeSelec;
	private String hiddenCollectionTypeSelec;
	private String hiddenStatusSelec;
	private String distLoginId;
	private String toDistLoginId;
	private String stbType;
	private String inventoryChangeDate;
	private String recoveredSTB;
	private String installedSTB;
	private String dcNo;
	private String status;
	private String transferType;
	private String stbSerialNo;
	
	
	private Date initiationDate;
	private Date transferDate;
	private Date acceptanceDate;
	
	
	
	List<DpProductCategoryDto> dcProductCategListDTO;
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public List<DpProductCategoryDto> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}
	public void setDcProductCategListDTO(
			List<DpProductCategoryDto> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getHiddenCollectionTypeSelec() {
		return hiddenCollectionTypeSelec;
	}
	public void setHiddenCollectionTypeSelec(String hiddenCollectionTypeSelec) {
		this.hiddenCollectionTypeSelec = hiddenCollectionTypeSelec;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenPoStatusSelec() {
		return hiddenPoStatusSelec;
	}
	public void setHiddenPoStatusSelec(String hiddenPoStatusSelec) {
		this.hiddenPoStatusSelec = hiddenPoStatusSelec;
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
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
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
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getRecoveredSTB() {
		return recoveredSTB;
	}
	public void setRecoveredSTB(String recoveredSTB) {
		this.recoveredSTB = recoveredSTB;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getZbmName() {
		return zbmName;
	}
	public void setZbmName(String zbmName) {
		this.zbmName = zbmName;
	}
	public String getStbSerialNo() {
		return stbSerialNo;
	}
	public void setStbSerialNo(String stbSerialNo) {
		this.stbSerialNo = stbSerialNo;
	}
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public Date getInitiationDate() {
		return initiationDate;
	}
	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public String getToDistName() {
		return toDistName;
	}
	public void setToDistName(String toDistName) {
		this.toDistName = toDistName;
	}
	public String getToTsmName() {
		return toTsmName;
	}
	public void setToTsmName(String toTsmName) {
		this.toTsmName = toTsmName;
	}
	public String getToDistLoginId() {
		return toDistLoginId;
	}
	public void setToDistLoginId(String toDistLoginId) {
		this.toDistLoginId = toDistLoginId;
	}
	
}
