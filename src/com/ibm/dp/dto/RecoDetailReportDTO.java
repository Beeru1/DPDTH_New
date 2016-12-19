package com.ibm.dp.dto;

import java.io.Serializable;

public class RecoDetailReportDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String distributorId ;
	public String distributorName ;
	public String productType = "";
	public String status = "";
	public String remarks="";
	 public int penPOOpen = 0;
	 public int penDcOpen = 0;
	 public String type="";
	 public int serOpening = 0;
	 public int nsrOpening= 0;
	 public int defOpen = 0;
	 public int upgardeOpen = 0;
	 public int churnOpen = 0;
	 public int receivedWH = 0;
	 public int receivedInterSSDOk = 0;
	 public int receivedInterSSDDef= 0;
	 public int receivedUpgrade = 0;
	 public int receivedChurn = 0;
	 public int returnedFresh = 0;
	 public int returnedInterSSDOk = 0;
	 public int returnedInterSSDDef = 0;
	 public int returnedDOABI = 0;
	 public int returnedDOAAI = 0;
	 public int returnedSwap = 0;
	 public int returnedC2C = 0;
	 public int returnedChurn = 0;
	 public int totalActivation = 0;
	 public int inventoryChange = 0;
	 public int adjustment = 0;
	 
	 public int serClosing = 0;
	 public int nsrClosing= 0;
	 public int defClosing= 0;
	 public int upgardeClosing = 0;
	 public int churnClosing = 0;
	 public int penPOClosing = 0;
	 public int penDcClosing = 0;
	 
	 public int varOpeningStock = 0;
	 public int varReceivedVH = 0;
	 
	 public int varReceivedInterSSD = 0;
	 public int varReceivedUpgrade = 0;
	 public int varReceivedChurn = 0;
	 public int varReceivedIntransit = 0;
	 
	 public int varReturnedFresh = 0;
	 public int varReturnedInTransit = 0;
	 public int varReturnedInterSSD = 0;
	 public int varReturnedDOABI = 0;
	 public int varReturnedDOAAI = 0;
	 public int varReturnedSwap = 0;
	 public int varReturnedC2C = 0;
	 
	 public int varTotalActivation = 0;
	 public int varClosingStock = 0;
	 
	 
	 // addded for Reco new changes in DP Enhancement by Rishab
	 public int opening = 0;
	 public int dispatch = 0;
	 public int returnVal = 0;
	 public int distPdis = 0;
	 public int distPrec = 0;
	 public int derivedClosing = 0;
	 public int systemCL = 0;
	 public int variance = 0;
	 public int inverntoryChangeout = 0;
	 
	 

	 
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getReceivedChurn() {
		return receivedChurn;
	}
	public void setReceivedChurn(int receivedChurn) {
		this.receivedChurn = receivedChurn;
	}
	public int getReceivedUpgrade() {
		return receivedUpgrade;
	}
	public void setReceivedUpgrade(int receivedUpgrade) {
		this.receivedUpgrade = receivedUpgrade;
	}
	public int getReturnedC2C() {
		return returnedC2C;
	}
	public void setReturnedC2C(int returnedC2C) {
		this.returnedC2C = returnedC2C;
	}
	public int getReturnedDOAAI() {
		return returnedDOAAI;
	}
	public void setReturnedDOAAI(int returnedDOAAI) {
		this.returnedDOAAI = returnedDOAAI;
	}
	public int getReturnedDOABI() {
		return returnedDOABI;
	}
	public void setReturnedDOABI(int returnedDOABI) {
		this.returnedDOABI = returnedDOABI;
	}
	public int getReturnedFresh() {
		return returnedFresh;
	}
	public void setReturnedFresh(int returnedFresh) {
		this.returnedFresh = returnedFresh;
	}
	public int getReturnedSwap() {
		return returnedSwap;
	}
	public void setReturnedSwap(int returnedSwap) {
		this.returnedSwap = returnedSwap;
	}
	public int getTotalActivation() {
		return totalActivation;
	}
	public void setTotalActivation(int totalActivation) {
		this.totalActivation = totalActivation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public int getVarClosingStock() {
		return varClosingStock;
	}
	public void setVarClosingStock(int varClosingStock) {
		this.varClosingStock = varClosingStock;
	}
	public int getVarOpeningStock() {
		return varOpeningStock;
	}
	public void setVarOpeningStock(int varOpeningStock) {
		this.varOpeningStock = varOpeningStock;
	}
	public int getVarReceivedChurn() {
		return varReceivedChurn;
	}
	public void setVarReceivedChurn(int varReceivedChurn) {
		this.varReceivedChurn = varReceivedChurn;
	}
	public int getVarReceivedInterSSD() {
		return varReceivedInterSSD;
	}
	public void setVarReceivedInterSSD(int varReceivedInterSSD) {
		this.varReceivedInterSSD = varReceivedInterSSD;
	}
	public int getVarReceivedIntransit() {
		return varReceivedIntransit;
	}
	public void setVarReceivedIntransit(int varReceivedIntransit) {
		this.varReceivedIntransit = varReceivedIntransit;
	}
	public int getVarReceivedUpgrade() {
		return varReceivedUpgrade;
	}
	public void setVarReceivedUpgrade(int varReceivedUpgrade) {
		this.varReceivedUpgrade = varReceivedUpgrade;
	}
	public int getVarReceivedVH() {
		return varReceivedVH;
	}
	public void setVarReceivedVH(int varReceivedVH) {
		this.varReceivedVH = varReceivedVH;
	}
	public int getVarReturnedC2C() {
		return varReturnedC2C;
	}
	public void setVarReturnedC2C(int varReturnedC2C) {
		this.varReturnedC2C = varReturnedC2C;
	}
	public int getVarReturnedDOAAI() {
		return varReturnedDOAAI;
	}
	public void setVarReturnedDOAAI(int varReturnedDOAAI) {
		this.varReturnedDOAAI = varReturnedDOAAI;
	}
	public int getVarReturnedDOABI() {
		return varReturnedDOABI;
	}
	public void setVarReturnedDOABI(int varReturnedDOABI) {
		this.varReturnedDOABI = varReturnedDOABI;
	}
	public int getVarReturnedFresh() {
		return varReturnedFresh;
	}
	public void setVarReturnedFresh(int varReturnedFresh) {
		this.varReturnedFresh = varReturnedFresh;
	}
	public int getVarReturnedInterSSD() {
		return varReturnedInterSSD;
	}
	public void setVarReturnedInterSSD(int varReturnedInterSSD) {
		this.varReturnedInterSSD = varReturnedInterSSD;
	}
	public int getVarReturnedInTransit() {
		return varReturnedInTransit;
	}
	public void setVarReturnedInTransit(int varReturnedInTransit) {
		this.varReturnedInTransit = varReturnedInTransit;
	}
	public int getVarReturnedSwap() {
		return varReturnedSwap;
	}
	public void setVarReturnedSwap(int varReturnedSwap) {
		this.varReturnedSwap = varReturnedSwap;
	}
	public int getVarTotalActivation() {
		return varTotalActivation;
	}
	public void setVarTotalActivation(int varTotalActivation) {
		this.varTotalActivation = varTotalActivation;
	}
	public int getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(int adjustment) {
		this.adjustment = adjustment;
	}
	public int getChurnClosing() {
		return churnClosing;
	}
	public void setChurnClosing(int churnClosing) {
		this.churnClosing = churnClosing;
	}
	public int getChurnOpen() {
		return churnOpen;
	}
	public void setChurnOpen(int churnOpen) {
		this.churnOpen = churnOpen;
	}
	public int getDefClosing() {
		return defClosing;
	}
	public void setDefClosing(int defClosing) {
		this.defClosing = defClosing;
	}
	public int getDefOpen() {
		return defOpen;
	}
	public void setDefOpen(int defOpen) {
		this.defOpen = defOpen;
	}
	public int getInventoryChange() {
		return inventoryChange;
	}
	public void setInventoryChange(int inventoryChange) {
		this.inventoryChange = inventoryChange;
	}
	public int getNsrClosing() {
		return nsrClosing;
	}
	public void setNsrClosing(int nsrClosing) {
		this.nsrClosing = nsrClosing;
	}
	public int getNsrOpening() {
		return nsrOpening;
	}
	public void setNsrOpening(int nsrOpening) {
		this.nsrOpening = nsrOpening;
	}
	public int getPenDcClosing() {
		return penDcClosing;
	}
	public void setPenDcClosing(int penDcClosing) {
		this.penDcClosing = penDcClosing;
	}
	public int getPenDcOpen() {
		return penDcOpen;
	}
	public void setPenDcOpen(int penDcOpen) {
		this.penDcOpen = penDcOpen;
	}
	public int getPenPOClosing() {
		return penPOClosing;
	}
	public void setPenPOClosing(int penPOClosing) {
		this.penPOClosing = penPOClosing;
	}
	public int getPenPOOpen() {
		return penPOOpen;
	}
	public void setPenPOOpen(int penPOOpen) {
		this.penPOOpen = penPOOpen;
	}
	public int getReceivedInterSSDDef() {
		return receivedInterSSDDef;
	}
	public void setReceivedInterSSDDef(int receivedInterSSDDef) {
		this.receivedInterSSDDef = receivedInterSSDDef;
	}
	public int getReceivedInterSSDOk() {
		return receivedInterSSDOk;
	}
	public void setReceivedInterSSDOk(int receivedInterSSDOk) {
		this.receivedInterSSDOk = receivedInterSSDOk;
	}
	public int getReceivedWH() {
		return receivedWH;
	}
	public void setReceivedWH(int receivedWH) {
		this.receivedWH = receivedWH;
	}
	public int getReturnedChurn() {
		return returnedChurn;
	}
	public void setReturnedChurn(int returnedChurn) {
		this.returnedChurn = returnedChurn;
	}
	public int getReturnedInterSSDDef() {
		return returnedInterSSDDef;
	}
	public void setReturnedInterSSDDef(int returnedInterSSDDef) {
		this.returnedInterSSDDef = returnedInterSSDDef;
	}
	public int getReturnedInterSSDOk() {
		return returnedInterSSDOk;
	}
	public void setReturnedInterSSDOk(int returnedInterSSDOk) {
		this.returnedInterSSDOk = returnedInterSSDOk;
	}
	public int getSerClosing() {
		return serClosing;
	}
	public void setSerClosing(int serClosing) {
		this.serClosing = serClosing;
	}
	public int getSerOpening() {
		return serOpening;
	}
	public void setSerOpening(int serOpening) {
		this.serOpening = serOpening;
	}
	public int getUpgardeClosing() {
		return upgardeClosing;
	}
	public void setUpgardeClosing(int upgardeClosing) {
		this.upgardeClosing = upgardeClosing;
	}
	public int getUpgardeOpen() {
		return upgardeOpen;
	}
	public void setUpgardeOpen(int upgardeOpen) {
		this.upgardeOpen = upgardeOpen;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getOpening() {
		return opening;
	}
	public void setOpening(int opening) {
		this.opening = opening;
	}
	public int getDispatch() {
		return dispatch;
	}
	public void setDispatch(int dispatch) {
		this.dispatch = dispatch;
	}
	public int getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(int returnVal) {
		this.returnVal = returnVal;
	}

	

	public int getDistPdis() {
		return distPdis;
	}
	public void setDistPdis(int distPdis) {
		this.distPdis = distPdis;
	}
	public int getDistPrec() {
		return distPrec;
	}
	public void setDistPrec(int distPrec) {
		this.distPrec = distPrec;
	}
	public int getDerivedClosing() {
		return derivedClosing;
	}
	
	public void setDerivedClosing(int derivedClosing) {
		this.derivedClosing = derivedClosing;
	}
	public int getSystemCL() {
		return systemCL;
	}
	public void setSystemCL(int systemCL) {
		this.systemCL = systemCL;
	}
	public int getVariance() {
		return variance;
	}
	public void setVariance(int variance) {
		this.variance = variance;
	}
	public int getInverntoryChangeout() {
		return inverntoryChangeout;
	}
	public void setInverntoryChangeout(int inverntoryChangeout) {
		this.inverntoryChangeout = inverntoryChangeout;
	}

	
	 
	
	 

}
