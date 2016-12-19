package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.upload.FormFile;

import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;

public class DistRecoBean extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stbTypeId;
	private String recoPeriodId;
	private String methodName;
	private String message;
	private List<DpProductTypeMasterDto>  prodTypeList  = new ArrayList<DpProductTypeMasterDto>();
	private List<RecoPeriodDTO>  recoPeriodList  = new ArrayList<RecoPeriodDTO>();
	private List<DistRecoDto>  productRecoList  = new ArrayList<DistRecoDto>();
	private List<PrintRecoDto>  printRecoList  = new ArrayList<PrintRecoDto>();
	private List<DistRecoDto>  dropDownProductRecoList  = new ArrayList<DistRecoDto>();
	private String certDate;
	private String refNo;
	private String disabledLink;
	private String distName;
	
	private String certificateId;
	private String certId;
	private String isValidToEnter;
	// Added by ram
	private boolean newRecoLiveDate;
	private String tabId;
	private String columnId;
	private FormFile uploadedFile;
	private String browseButtonName;
	private byte stockType; 
	private int productsLists;
	
	// end adding by ram
	private List<DistRecoDto> detailsList;
	private String strmsg;
	private int productComboValue;
	private int totalSerializedStock;
	private int totalDefectiveStock;
	private int totalUpgradeStock;
	private int totalChurnedStock;
	private String oldOrNewReco;
	private String swapProductList;
	
	
	
	
	
	
	
	public String getOldOrNewReco() {
		return oldOrNewReco;
	}
	public void setOldOrNewReco(String oldOrNewReco) {
		this.oldOrNewReco = oldOrNewReco;
	}
	public int getTotalChurnedStock() {
		return totalChurnedStock;
	}
	public void setTotalChurnedStock(int totalChurnedStock) {
		this.totalChurnedStock = totalChurnedStock;
	}
	public int getTotalDefectiveStock() {
		return totalDefectiveStock;
	}
	public void setTotalDefectiveStock(int totalDefectiveStock) {
		this.totalDefectiveStock = totalDefectiveStock;
	}
	public int getTotalSerializedStock() {
		return totalSerializedStock;
	}
	public void setTotalSerializedStock(int totalSerializedStock) {
		this.totalSerializedStock = totalSerializedStock;
	}
	public int getTotalUpgradeStock() {
		return totalUpgradeStock;
	}
	public void setTotalUpgradeStock(int totalUpgradeStock) {
		this.totalUpgradeStock = totalUpgradeStock;
	}
	public int getProductsLists() {
		return productsLists;
	}
	public void setProductsLists(int productsLists) {
		this.productsLists = productsLists;
	}
	public int getProductComboValue() {
		return productComboValue;
	}
	public void setProductComboValue(int productComboValue) {
		this.productComboValue = productComboValue;
	}
	public String getBrowseButtonName() {
		return browseButtonName;
	}
	public void setBrowseButtonName(String browseButtonName) {
		this.browseButtonName = browseButtonName;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public String getTabId() {
		return tabId;
	}
	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public List<DistRecoDto> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<DistRecoDto> detailsList) {
		this.detailsList = detailsList;
	}
	public boolean isNewRecoLiveDate() {
		return newRecoLiveDate;
	}
	public void setNewRecoLiveDate(boolean newRecoLiveDate) {
		this.newRecoLiveDate = newRecoLiveDate;
	}
	public String getRecoPeriodId() {
		return recoPeriodId;
	}
	public void setRecoPeriodId(String recoPeriodId) {
		this.recoPeriodId = recoPeriodId;
	}
	public List<RecoPeriodDTO> getRecoPeriodList() {
		return recoPeriodList;
	}
	public void setRecoPeriodList(List<RecoPeriodDTO> recoPeriodList) {
		this.recoPeriodList = recoPeriodList;
	}
	public String getStbTypeId() {
		return stbTypeId;
	}
	public void setStbTypeId(String stbTypeId) {
		this.stbTypeId = stbTypeId;
	}
	
	public List<DpProductTypeMasterDto> getProdTypeList() {
		return prodTypeList;
	}
	public void setProdTypeList(List<DpProductTypeMasterDto> prodTypeList) {
		this.prodTypeList = prodTypeList;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<DistRecoDto> getProductRecoList() {
		return productRecoList;
	}
	public void setProductRecoList(List<DistRecoDto> productRecoList) {
		this.productRecoList = productRecoList;
	}
	
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public List<PrintRecoDto> getPrintRecoList() {
		return printRecoList;
	}
	public void setPrintRecoList(List<PrintRecoDto> printRecoList) {
		this.printRecoList = printRecoList;
	}
	public String getCertDate() {
		return certDate;
	}
	public void setCertDate(String certDate) {
		this.certDate = certDate;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getDisabledLink() {
		return disabledLink;
	}
	public void setDisabledLink(String disabledLink) {
		this.disabledLink = disabledLink;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getIsValidToEnter() {
		return isValidToEnter;
	}
	public void setIsValidToEnter(String isValidToEnter) {
		this.isValidToEnter = isValidToEnter;
	}
	public byte getStockType() {
		return stockType;
	}
	public void setStockType(byte stockType) {
		this.stockType = stockType;
	}
	public List<DistRecoDto> getDropDownProductRecoList() {
		return dropDownProductRecoList;
	}
	public void setDropDownProductRecoList(List<DistRecoDto> dropDownProductRecoList) {
		this.dropDownProductRecoList = dropDownProductRecoList;
	}
	public String getSwapProductList() {
		return swapProductList;
	}
	public void setSwapProductList(String swapProductList) {
		this.swapProductList = swapProductList;
	}
	
	
	
	

}
