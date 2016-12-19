package com.ibm.dp.dto;

import java.util.List;

public class NonSerializedConsumptionReportDTO {
	
	private String showCircle="";
	public String productCategory= "";
	 public String collectionId = "";
	 public String collectionName = "";
	private String showTSM="";
	private String showDist="";	
	private String fromDate;
	private String toDate;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenPoStatusSelec;
	private String distName;
	private String tsmName;
	private String collectionType;
	private String oracleLocatorCode;
	private String date;
	private String batchId;
	private String itemcode;
	private String quantity;
	private String installationDate;
	private String createdBy;
	private String creationDate;
	private String lastUpdatedBy;
	private String lastUpdateDate;
	private String circleName;
	private String hiddenSTBTypeSelec;
	private String hiddenCollectionTypeSelec;
	private String hiddenStatusSelec;
	private String distLoginId;
	private String stbType;
	private String inventoryChangeDate;
	private String recoveredSTB;
	private String installedSTB;
	
	private String status;
	private String distId;
	
	
	List<DpProductCategoryDto> dcProductCategListDTO;
	
	public List<DpProductCategoryDto> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}
	public void setDcProductCategListDTO(
			List<DpProductCategoryDto> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
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
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
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
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
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
	
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getOracleLocatorCode() {
		return oracleLocatorCode;
	}
	public void setOracleLocatorCode(String oracleLocatorCode) {
		this.oracleLocatorCode = oracleLocatorCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	

}

