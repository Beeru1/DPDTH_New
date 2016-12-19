package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class NonSerializedConsumptionReportBean extends ActionForm{
	
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
	private String methodName;
	private String hiddenCircleSelecIds;
	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenSTBTypeSelec;
	private String distLoginId;
	private String hiddenCollectionTypeSelec;
	private String hiddenStatusSelec;
	private String stbType;
	
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
	
	private List<DpProductCategoryDto> dcProductCategListDTO;
	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> tsmList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> distList =new ArrayList<SelectionCollection>();
	private List<SelectionCollection> poStatusList =new ArrayList<SelectionCollection>();
	private List<NonSerializedConsumptionReportDTO> reportList =new ArrayList<NonSerializedConsumptionReportDTO>();
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
	
	
	public List<DpProductCategoryDto> getDcProductCategListDTO() {
		return dcProductCategListDTO;
	}
	public void setDcProductCategListDTO(
			List<DpProductCategoryDto> dcProductCategListDTO) {
		this.dcProductCategListDTO = dcProductCategListDTO;
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
	
	public List<NonSerializedConsumptionReportDTO> getReportList() {
		return reportList;
	}
	public void setReportList(List<NonSerializedConsumptionReportDTO> reportList) {
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
	
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
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
