package com.ibm.dp.dto;

import java.util.Date;

public class DistRecoDto implements Cloneable {
	private String dataTypa;
	private String dataTypaId;
	private String productName;
	private String productType;
	private String productId;
	//Open Stock
	private String pendingPOIntransitOpen;
	private String openingPendgDCIntrnsit;
	private String defectiveOpenStock;
	private String upgradeOpenStock;
	private String serialisedOpenStock;
	private String churnOpenStock;
	private String nonSerialisedOpenStock;
	//Recvd Stock
	private String receivedWh;
	private String receivedInterSSDOK;
	private String receivedHierarchyTrans;
	private String receivedInterSSDDef;
	private String receivedUpgrade;
	private String receivedChurn;
	//Ret Stock
	private String returnedFresh;
	private String returnedInterSSDOk;
	private String returnedHierarchyTrans;
	private String returnedInterSSDDEF;
	private String returnedDoaBI;
	private String returnedDoaAi;
	private String returnedDefectiveSwap;
	private String returnedC2S;
	private String returnedChurn;
	//Activation
	private String serialisedActivation;
	private String nonSerialisedActivation;
	//Inventory Change
	private String inventoryChange;
	//Adjustment 
	private String flushOutOk;
	private String flushOutDefective;
	private String recoQty;
	//Closing Stock
	private String serialisedClosingStock;
	private String nonSerialisedClosingStock;
	private String defectiveClosingStock;
	private String upgradeClosingStock;
	private String churnClosingStock;
//	Closing In Transit
	private String pendingPOIntransit;
	private String pendingDCIntransit;
	//Totals
	private String openTotal;
	private String receivedTotal;
	private String returnedTotal;
	private String activationTotal;
	private String adjustmentTotal;
	private String closingTotal;
	private String invChngTotal;
	//Ends here
	private String isPartnerEntered;
	private String distId;
	private String recoPeriodId;
	private String circleId;
//	Added by shilpa on 2-5-12 for UAT Observation
	private String remarks; 
// Added By Sadiqua	
	
	private String isValidToEnter;
	private String isValidToSubmit;
	private String isTextField;
	private String isSubmitted;
	//added by vishwas
	private String showDetailsClosingser;
	private String showDetailsClosingnon;
	private String showDetailsClosingdef;
	private String showDetailsClosingup;
	private String showDetailsClosingchu;
	private String showDetailsClosingPPO;
	private String showDetailsClosingPDC;
	//--
	private String showDetailsClosingACTTOL;
	private String showDetailsClosingADJTOL;
	private String showDetailsClosingINVCHG;
	//---
	private String showDetailsOpeningser;
	//private String showDetailsOpeningnon;
	private String showDetailsOpeningdef;
	private String showDetailsOpeningup;
	private String showDetailsOpeningchu;
	//----receive
	private String showDetailsreceivedWh;
	private String showDetailsreceivedInterSSDOK;
	private String showDetailsreceivedHierarchyTrans;
	private String showDetailsreceivedInterSSDDef;
	private String showDetailsreceivedUpgrade;
	private String showDetailsreceivedChurn;
	//-----return-----
	
	private String showDetailsreturnedFresh;
	private String showDetailsreturnedInterSSDOk;
	//private String showDetailsreturnedHierarchyTrans;
	private String showDetailsreturnedInterSSDDEF;
	private String showDetailsreturnedDoaBI;
	private String showDetailsreturnedDoaAi;
	private String showDetailsreturnedDefectiveSwap;
	private String showDetailsreturnedC2S;
	private String showDetailsRChurn;
	public String getIsSubmitted() {
		return isSubmitted;
	}
	public void setIsSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	private String certificateId;
	private String returnedDefective;
	private String recoStatus;
	private String serialisedAdjustment;
	private String nonSerialisedAdjustment;
	private String certId;
	private String varVal;
	private String productSerialNo;
	// adding by ram 
	private String prNo;
	private String poNo;
	private String distPurchageDate;
	private int fscId;
	private String fscPurchageDate;
	private int retailerId;
	private String retailerPurchageDate;
	private String invoiceNo;
	private Date acceptDate;
	private String distRemarks;
	private String distOlmId;
	private String distName;
	private String tsmName;
	private String circleName;
	private String dcNo;
	private String dcDate;
	private String collectionType;
	private int dispatchedQtyPerDc;
	private String dcStbStatus;
	private String fscName;
	private String statusDesc;
	private String poDate;
	private String poStatus;
	private String stbStatus;
	private Date lastPoAcceptanceDate;
	private String collectionDate;
	private String inventoryChangeDate;
	private String recoveredStbNo;
	private String installedStbNo;
	private String defectType;
	private String aging;
	private String fromDistId;
	private String toDistId;
	private String fromDistName;
	private String toDistName;
	private String initiatorName;
	
	private String transferType;
	private Date initiationDate;
	private Date transferDate;
	private Date acceptanceDate;
	private Date whReceivedDate;
	private String retailerName;
	private String activatedSerialNo;
	private Date activationDate;
	private String newProductName;
	//End adding by ram 
	//Added by Rohit
	private String accountName;
	private String typeOfStock;
	private int stockType;
	private String distFlag;
	private String flushOutFlag;
	private String dthRemarks;
	private String serialNo;
	private String oldOrNewReco;
	private String debitFlag;
	//End adding by ram 
	
		private String err_msg;
	public String strDistOLMIds="";
	//added by ABU
	private String otherRemarks;
	
	//end of ABU changes
	
	
	
	
	public String getDebitFlag() {
		return debitFlag;
	}
	public void setDebitFlag(String debitFlag) {
		this.debitFlag = debitFlag;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getTypeOfStock() {
		return typeOfStock;
	}
	public void setTypeOfStock(String typeOfStock) {
		this.typeOfStock = typeOfStock;
	}
	public String getFlushOutFlag() {
		return flushOutFlag;
	}
	public void setFlushOutFlag(String flushOutFlag) {
		this.flushOutFlag = flushOutFlag;
	}
	public String getDthRemarks() {
		return dthRemarks;
	}
	public void setDthRemarks(String dthRemarks) {
		this.dthRemarks = dthRemarks;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getStrDistOLMIds() {
		return strDistOLMIds;
	}
	public void setStrDistOLMIds(String strDistOLMIds) {
		this.strDistOLMIds = strDistOLMIds;
	}
	public String getProductSerialNo() {
		return productSerialNo;
	}
	public String getNewProductName() {
		return newProductName;
	}
	public void setNewProductName(String newProductName) {
		this.newProductName = newProductName;
	}
	public Date getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	public String getActivatedSerialNo() {
		return activatedSerialNo;
	}
	public void setActivatedSerialNo(String activatedSerialNo) {
		this.activatedSerialNo = activatedSerialNo;
	}
	public String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	public Date getWhReceivedDate() {
		return whReceivedDate;
	}
	public void setWhReceivedDate(Date whReceivedDate) {
		this.whReceivedDate = whReceivedDate;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getFromDistName() {
		return fromDistName;
	}
	public void setFromDistName(String fromDistName) {
		this.fromDistName = fromDistName;
	}
	public String getToDistName() {
		return toDistName;
	}
	public void setToDistName(String toDistName) {
		this.toDistName = toDistName;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
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
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
		
	public String getRecoveredStbNo() {
		return recoveredStbNo;
	}
	public void setRecoveredStbNo(String recoveredStbNo) {
		this.recoveredStbNo = recoveredStbNo;
	}
	public String getInstalledStbNo() {
		return installedStbNo;
	}
	public void setInstalledStbNo(String installedStbNo) {
		this.installedStbNo = installedStbNo;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public String getAging() {
		return aging;
	}
	public void setAging(String aging) {
		this.aging = aging;
	}
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}
	public String getStbStatus() {
		return stbStatus;
	}
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	public Date getLastPoAcceptanceDate() {
		return lastPoAcceptanceDate;
	}
	public void setLastPoAcceptanceDate(Date lastPoAcceptanceDate) {
		this.lastPoAcceptanceDate = lastPoAcceptanceDate;
	}
	
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getFscName() {
		return fscName;
	}
	public void setFscName(String fscName) {
		this.fscName = fscName;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public int getDispatchedQtyPerDc() {
		return dispatchedQtyPerDc;
	}
	public void setDispatchedQtyPerDc(int dispatchedQtyPerDc) {
		this.dispatchedQtyPerDc = dispatchedQtyPerDc;
	}
	public String getDcStbStatus() {
		return dcStbStatus;
	}
	public void setDcStbStatus(String dcStbStatus) {
		this.dcStbStatus = dcStbStatus;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getDistPurchageDate() {
		return distPurchageDate;
	}
	public void setDistPurchageDate(String distPurchageDate) {
		this.distPurchageDate = distPurchageDate;
	}
	public int getFscId() {
		return fscId;
	}
	public void setFscId(int fscId) {
		this.fscId = fscId;
	}
	public String getFscPurchageDate() {
		return fscPurchageDate;
	}
	public void setFscPurchageDate(String fscPurchageDate) {
		this.fscPurchageDate = fscPurchageDate;
	}
	public int getRetailerId() {
		return retailerId;
	}
	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}
	public String getRetailerPurchageDate() {
		return retailerPurchageDate;
	}
	public void setRetailerPurchageDate(String retailerPurchageDate) {
		this.retailerPurchageDate = retailerPurchageDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public String getDistRemarks() {
		return distRemarks;
	}
	public void setDistRemarks(String distRemarks) {
		this.distRemarks = distRemarks;
	}
	public void setProductSerialNo(String productSerialNo) {
		this.productSerialNo = productSerialNo;
	}
	public String getDataTypa() {
		return dataTypa;
	}
	public void setDataTypa(String dataTypa) {
		this.dataTypa = dataTypa;
	}
	public String getIsPartnerEntered() {
		return isPartnerEntered;
	}
	public void setIsPartnerEntered(String isPartnerEntered) {
		this.isPartnerEntered = isPartnerEntered;
	}
	public String getNonSerialisedActivation() {
		return nonSerialisedActivation;
	}
	public void setNonSerialisedActivation(String nonSerialisedActivation) {
		this.nonSerialisedActivation = nonSerialisedActivation;
	}
	public String getNonSerialisedOpenStock() {
		return nonSerialisedOpenStock;
	}
	public void setNonSerialisedOpenStock(String nonSerialisedOpenStock) {
		this.nonSerialisedOpenStock = nonSerialisedOpenStock;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReceivedChurn() {
		return receivedChurn;
	}
	public void setReceivedChurn(String receivedChurn) {
		this.receivedChurn = receivedChurn;
	}
	
	
	public String getReceivedUpgrade() {
		return receivedUpgrade;
	}
	public void setReceivedUpgrade(String receivedUpgrade) {
		this.receivedUpgrade = receivedUpgrade;
	}
	public String getReceivedWh() {
		return receivedWh;
	}
	public void setReceivedWh(String receivedWh) {
		this.receivedWh = receivedWh;
	}
	public String getReturnedC2S() {
		return returnedC2S;
	}
	public void setReturnedC2S(String returnedC2S) {
		this.returnedC2S = returnedC2S;
	}
	public String getReturnedDefectiveSwap() {
		return returnedDefectiveSwap;
	}
	public void setReturnedDefectiveSwap(String returnedDefectiveSwap) {
		this.returnedDefectiveSwap = returnedDefectiveSwap;
	}
	public String getReturnedDoaAi() {
		return returnedDoaAi;
	}
	public void setReturnedDoaAi(String returnedDoaAi) {
		this.returnedDoaAi = returnedDoaAi;
	}
	public String getReturnedDoaBI() {
		return returnedDoaBI;
	}
	public void setReturnedDoaBI(String returnedDoaBI) {
		this.returnedDoaBI = returnedDoaBI;
	}
	
	public String getChurnClosingStock() {
		return churnClosingStock;
	}
	public void setChurnClosingStock(String churnClosingStock) {
		this.churnClosingStock = churnClosingStock;
	}
	public String getChurnOpenStock() {
		return churnOpenStock;
	}
	public void setChurnOpenStock(String churnOpenStock) {
		this.churnOpenStock = churnOpenStock;
	}
	public String getDefectiveClosingStock() {
		return defectiveClosingStock;
	}
	public void setDefectiveClosingStock(String defectiveClosingStock) {
		this.defectiveClosingStock = defectiveClosingStock;
	}
	public String getDefectiveOpenStock() {
		return defectiveOpenStock;
	}
	public void setDefectiveOpenStock(String defectiveOpenStock) {
		this.defectiveOpenStock = defectiveOpenStock;
	}
	public String getFlushOutDefective() {
		return flushOutDefective;
	}
	public void setFlushOutDefective(String flushOutDefective) {
		this.flushOutDefective = flushOutDefective;
	}
	public String getFlushOutOk() {
		return flushOutOk;
	}
	public void setFlushOutOk(String flushOutOk) {
		this.flushOutOk = flushOutOk;
	}
	public String getNonSerialisedClosingStock() {
		return nonSerialisedClosingStock;
	}
	public void setNonSerialisedClosingStock(String nonSerialisedClosingStock) {
		this.nonSerialisedClosingStock = nonSerialisedClosingStock;
	}
	public String getPendingDCIntransit() {
		return pendingDCIntransit;
	}
	public void setPendingDCIntransit(String pendingDCIntransit) {
		this.pendingDCIntransit = pendingDCIntransit;
	}
	public String getPendingPOIntransit() {
		return pendingPOIntransit;
	}
	public void setPendingPOIntransit(String pendingPOIntransit) {
		this.pendingPOIntransit = pendingPOIntransit;
	}
	public String getReceivedHierarchyTrans() {
		return receivedHierarchyTrans;
	}
	public void setReceivedHierarchyTrans(String receivedHierarchyTrans) {
		this.receivedHierarchyTrans = receivedHierarchyTrans;
	}
	public String getReceivedInterSSDDef() {
		return receivedInterSSDDef;
	}
	public void setReceivedInterSSDDef(String receivedInterSSDDef) {
		this.receivedInterSSDDef = receivedInterSSDDef;
	}
	public String getReceivedInterSSDOK() {
		return receivedInterSSDOK;
	}
	public void setReceivedInterSSDOK(String receivedInterSSDOK) {
		this.receivedInterSSDOK = receivedInterSSDOK;
	}
	public String getRecoQty() {
		return recoQty;
	}
	public void setRecoQty(String recoQty) {
		this.recoQty = recoQty;
	}
	public String getRecoStatus() {
		return recoStatus;
	}
	public void setRecoStatus(String recoStatus) {
		this.recoStatus = recoStatus;
	}
	public String getReturnedChurn() {
		return returnedChurn;
	}
	public void setReturnedChurn(String returnedChurn) {
		this.returnedChurn = returnedChurn;
	}
	public String getReturnedDefective() {
		return returnedDefective;
	}
	public void setReturnedDefective(String returnedDefective) {
		this.returnedDefective = returnedDefective;
	}
	public String getReturnedFresh() {
		return returnedFresh;
	}
	public void setReturnedFresh(String returnedFresh) {
		this.returnedFresh = returnedFresh;
	}
	public String getReturnedHierarchyTrans() {
		return returnedHierarchyTrans;
	}
	public void setReturnedHierarchyTrans(String returnedHierarchyTrans) {
		this.returnedHierarchyTrans = returnedHierarchyTrans;
	}
	public String getReturnedInterSSDOk() {
		return returnedInterSSDOk;
	}
	public void setReturnedInterSSDOk(String returnedInterSSDOk) {
		this.returnedInterSSDOk = returnedInterSSDOk;
	}
	
	public String getSerialisedClosingStock() {
		return serialisedClosingStock;
	}
	public void setSerialisedClosingStock(String serialisedClosingStock) {
		this.serialisedClosingStock = serialisedClosingStock;
	}
	public String getUpgradeClosingStock() {
		return upgradeClosingStock;
	}
	public void setUpgradeClosingStock(String upgradeClosingStock) {
		this.upgradeClosingStock = upgradeClosingStock;
	}
	public String getUpgradeOpenStock() {
		return upgradeOpenStock;
	}
	public void setUpgradeOpenStock(String upgradeOpenStock) {
		this.upgradeOpenStock = upgradeOpenStock;
	}
	
	public String getSerialisedActivation() {
		return serialisedActivation;
	}
	public void setSerialisedActivation(String serialisedActivation) {
		this.serialisedActivation = serialisedActivation;
	}
	public String getSerialisedOpenStock() {
		return serialisedOpenStock;
	}
	public void setSerialisedOpenStock(String serialisedOpenStock) {
		this.serialisedOpenStock = serialisedOpenStock;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDataTypaId() {
		return dataTypaId;
	}
	public void setDataTypaId(String dataTypaId) {
		this.dataTypaId = dataTypaId;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getRecoPeriodId() {
		return recoPeriodId;
	}
	public void setRecoPeriodId(String recoPeriodId) {
		this.recoPeriodId = recoPeriodId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	// Added By Sadiqua	
	
	public String getIsValidToEnter() {
		return isValidToEnter;
	}
	public void setIsValidToEnter(String isValidToEnter) {
		this.isValidToEnter = isValidToEnter;
	}
	public String getIsValidToSubmit() {
		return isValidToSubmit;
	}
	public void setIsValidToSubmit(String isValidToSubmit) {
		this.isValidToSubmit = isValidToSubmit;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getInventoryChange() {
		return inventoryChange;
	}
	public void setInventoryChange(String inventoryChange) {
		this.inventoryChange = inventoryChange;
	}
	public String getNonSerialisedAdjustment() {
		return nonSerialisedAdjustment;
	}
	public void setNonSerialisedAdjustment(String nonSerialisedAdjustment) {
		this.nonSerialisedAdjustment = nonSerialisedAdjustment;
	}
	public String getSerialisedAdjustment() {
		return serialisedAdjustment;
	}
	public void setSerialisedAdjustment(String serialisedAdjustment) {
		this.serialisedAdjustment = serialisedAdjustment;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getOpeningPendgDCIntrnsit() {
		return openingPendgDCIntrnsit;
	}
	public void setOpeningPendgDCIntrnsit(String openingPendgDCIntrnsit) {
		this.openingPendgDCIntrnsit = openingPendgDCIntrnsit;
	}
	public String getPendingPOIntransitOpen() {
		return pendingPOIntransitOpen;
	}
	public void setPendingPOIntransitOpen(String pendingPOIntransitOpen) {
		this.pendingPOIntransitOpen = pendingPOIntransitOpen;
	}
	public String getReturnedInterSSDDEF() {
		return returnedInterSSDDEF;
	}
	public void setReturnedInterSSDDEF(String returnedInterSSDDEF) {
		this.returnedInterSSDDEF = returnedInterSSDDEF;
	}
	public String getIsTextField() {
		return isTextField;
	}
	public void setIsTextField(String isTextField) {
		this.isTextField = isTextField;
	}
	public String getVarVal() {
		return varVal;
	}
	public void setVarVal(String varVal) {
		this.varVal = varVal;
	}
	public String getActivationTotal() {
		return activationTotal;
	}
	public void setActivationTotal(String activationTotal) {
		this.activationTotal = activationTotal;
	}
	public String getClosingTotal() {
		return closingTotal;
	}
	public void setClosingTotal(String closingTotal) {
		this.closingTotal = closingTotal;
	}
	public String getOpenTotal() {
		return openTotal;
	}
	public void setOpenTotal(String openTotal) {
		this.openTotal = openTotal;
	}
	public String getReceivedTotal() {
		return receivedTotal;
	}
	public void setReceivedTotal(String receivedTotal) {
		this.receivedTotal = receivedTotal;
	}
	public String getReturnedTotal() {
		return returnedTotal;
	}
	public void setReturnedTotal(String returnedTotal) {
		this.returnedTotal = returnedTotal;
	}
	public String getAdjustmentTotal() {
		return adjustmentTotal;
	}
	public void setAdjustmentTotal(String adjustmentTotal) {
		this.adjustmentTotal = adjustmentTotal;
	}
	public String getInvChngTotal() {
		return invChngTotal;
	}
	public void setInvChngTotal(String invChngTotal) {
		this.invChngTotal = invChngTotal;
	}
	public DistRecoDto clone() throws CloneNotSupportedException {
		DistRecoDto cloned;
		cloned = (DistRecoDto)super.clone();
		return cloned;
	}
	public int getStockType() {
		return stockType;
	}
	public void setStockType(int stockType) {
		this.stockType = stockType;
	}
	public String getDistFlag() {
		return distFlag;
	}
	public void setDistFlag(String distFlag) {
		this.distFlag = distFlag;
	}
	public String getOldOrNewReco() {
		return oldOrNewReco;
	}
	public void setOldOrNewReco(String oldOrNewReco) {
		this.oldOrNewReco = oldOrNewReco;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getInventoryChangeDate() {
		return inventoryChangeDate;
	}
	public void setInventoryChangeDate(String inventoryChangeDate) {
		this.inventoryChangeDate = inventoryChangeDate;
	}
	public String getShowDetailsClosingchu() {
		return showDetailsClosingchu;
	}
	public void setShowDetailsClosingchu(String showDetailsClosingchu) {
		this.showDetailsClosingchu = showDetailsClosingchu;
	}
	public String getShowDetailsClosingdef() {
		return showDetailsClosingdef;
	}
	public void setShowDetailsClosingdef(String showDetailsClosingdef) {
		this.showDetailsClosingdef = showDetailsClosingdef;
	}
	public String getShowDetailsClosingnon() {
		return showDetailsClosingnon;
	}
	public void setShowDetailsClosingnon(String showDetailsClosingnon) {
		this.showDetailsClosingnon = showDetailsClosingnon;
	}
	public String getShowDetailsClosingPDC() {
		return showDetailsClosingPDC;
	}
	public void setShowDetailsClosingPDC(String showDetailsClosingPDC) {
		this.showDetailsClosingPDC = showDetailsClosingPDC;
	}
	public String getShowDetailsClosingPPO() {
		return showDetailsClosingPPO;
	}
	public void setShowDetailsClosingPPO(String showDetailsClosingPPO) {
		this.showDetailsClosingPPO = showDetailsClosingPPO;
	}
	public String getShowDetailsClosingser() {
		return showDetailsClosingser;
	}
	public void setShowDetailsClosingser(String showDetailsClosingser) {
		this.showDetailsClosingser = showDetailsClosingser;
	}
	public String getShowDetailsClosingup() {
		return showDetailsClosingup;
	}
	public void setShowDetailsClosingup(String showDetailsClosingup) {
		this.showDetailsClosingup = showDetailsClosingup;
	}
	public String getShowDetailsClosingACTTOL() {
		return showDetailsClosingACTTOL;
	}
	public void setShowDetailsClosingACTTOL(String showDetailsClosingACTTOL) {
		this.showDetailsClosingACTTOL = showDetailsClosingACTTOL;
	}
	public String getShowDetailsClosingADJTOL() {
		return showDetailsClosingADJTOL;
	}
	public void setShowDetailsClosingADJTOL(String showDetailsClosingADJTOL) {
		this.showDetailsClosingADJTOL = showDetailsClosingADJTOL;
	}
	public String getShowDetailsClosingINVCHG() {
		return showDetailsClosingINVCHG;
	}
	public void setShowDetailsClosingINVCHG(String showDetailsClosingINVCHG) {
		this.showDetailsClosingINVCHG = showDetailsClosingINVCHG;
	}
	public String getShowDetailsOpeningchu() {
		return showDetailsOpeningchu;
	}
	public void setShowDetailsOpeningchu(String showDetailsOpeningchu) {
		this.showDetailsOpeningchu = showDetailsOpeningchu;
	}
	public String getShowDetailsOpeningdef() {
		return showDetailsOpeningdef;
	}
	public void setShowDetailsOpeningdef(String showDetailsOpeningdef) {
		this.showDetailsOpeningdef = showDetailsOpeningdef;
	}
	public String getShowDetailsOpeningser() {
		return showDetailsOpeningser;
	}
	public void setShowDetailsOpeningser(String showDetailsOpeningser) {
		this.showDetailsOpeningser = showDetailsOpeningser;
	}
	public String getShowDetailsOpeningup() {
		return showDetailsOpeningup;
	}
	public void setShowDetailsOpeningup(String showDetailsOpeningup) {
		this.showDetailsOpeningup = showDetailsOpeningup;
	}
	public String getShowDetailsreceivedChurn() {
		return showDetailsreceivedChurn;
	}
	public void setShowDetailsreceivedChurn(String showDetailsreceivedChurn) {
		this.showDetailsreceivedChurn = showDetailsreceivedChurn;
	}
	public String getShowDetailsreceivedHierarchyTrans() {
		return showDetailsreceivedHierarchyTrans;
	}
	public void setShowDetailsreceivedHierarchyTrans(
			String showDetailsreceivedHierarchyTrans) {
		this.showDetailsreceivedHierarchyTrans = showDetailsreceivedHierarchyTrans;
	}
	public String getShowDetailsreceivedInterSSDDef() {
		return showDetailsreceivedInterSSDDef;
	}
	public void setShowDetailsreceivedInterSSDDef(
			String showDetailsreceivedInterSSDDef) {
		this.showDetailsreceivedInterSSDDef = showDetailsreceivedInterSSDDef;
	}
	public String getShowDetailsreceivedInterSSDOK() {
		return showDetailsreceivedInterSSDOK;
	}
	public void setShowDetailsreceivedInterSSDOK(
			String showDetailsreceivedInterSSDOK) {
		this.showDetailsreceivedInterSSDOK = showDetailsreceivedInterSSDOK;
	}
	public String getShowDetailsreceivedUpgrade() {
		return showDetailsreceivedUpgrade;
	}
	public void setShowDetailsreceivedUpgrade(String showDetailsreceivedUpgrade) {
		this.showDetailsreceivedUpgrade = showDetailsreceivedUpgrade;
	}
	public String getShowDetailsreceivedWh() {
		return showDetailsreceivedWh;
	}
	public void setShowDetailsreceivedWh(String showDetailsreceivedWh) {
		this.showDetailsreceivedWh = showDetailsreceivedWh;
	}
	public String getShowDetailsRChurn() {
		return showDetailsRChurn;
	}
	public void setShowDetailsRChurn(String showDetailsRChurn) {
		this.showDetailsRChurn = showDetailsRChurn;
	}
	public String getShowDetailsreturnedC2S() {
		return showDetailsreturnedC2S;
	}
	public void setShowDetailsreturnedC2S(String showDetailsreturnedC2S) {
		this.showDetailsreturnedC2S = showDetailsreturnedC2S;
	}
	public String getShowDetailsreturnedDefectiveSwap() {
		return showDetailsreturnedDefectiveSwap;
	}
	public void setShowDetailsreturnedDefectiveSwap(
			String showDetailsreturnedDefectiveSwap) {
		this.showDetailsreturnedDefectiveSwap = showDetailsreturnedDefectiveSwap;
	}
	public String getShowDetailsreturnedDoaAi() {
		return showDetailsreturnedDoaAi;
	}
	public void setShowDetailsreturnedDoaAi(String showDetailsreturnedDoaAi) {
		this.showDetailsreturnedDoaAi = showDetailsreturnedDoaAi;
	}
	public String getShowDetailsreturnedDoaBI() {
		return showDetailsreturnedDoaBI;
	}
	public void setShowDetailsreturnedDoaBI(String showDetailsreturnedDoaBI) {
		this.showDetailsreturnedDoaBI = showDetailsreturnedDoaBI;
	}
	public String getShowDetailsreturnedFresh() {
		return showDetailsreturnedFresh;
	}
	public void setShowDetailsreturnedFresh(String showDetailsreturnedFresh) {
		this.showDetailsreturnedFresh = showDetailsreturnedFresh;
	}
	public String getShowDetailsreturnedInterSSDDEF() {
		return showDetailsreturnedInterSSDDEF;
	}
	public void setShowDetailsreturnedInterSSDDEF(
			String showDetailsreturnedInterSSDDEF) {
		this.showDetailsreturnedInterSSDDEF = showDetailsreturnedInterSSDDEF;
	}
	public String getShowDetailsreturnedInterSSDOk() {
		return showDetailsreturnedInterSSDOk;
	}
	public void setShowDetailsreturnedInterSSDOk(
			String showDetailsreturnedInterSSDOk) {
		this.showDetailsreturnedInterSSDOk = showDetailsreturnedInterSSDOk;
	}
	public String getDcDate() {
		return dcDate;
	}
	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}
	public String getOtherRemarks() {
		return otherRemarks;
	}
	
	
}
