package com.ibm.reports.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericReportsOutputDto {
	
	private List<String> headers;
	private List<String[]> output;
	
	
	
	public String distributorId ;
	public String distributorName ;
	public String productType = "";
	public String status = "";
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
	
	 List<GenericReportsOutputDto> recoList= new ArrayList<GenericReportsOutputDto>();
	
	
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
	public int getReceivedUpgrade() {
		return receivedUpgrade;
	}
	public void setReceivedUpgrade(int receivedUpgrade) {
		this.receivedUpgrade = receivedUpgrade;
	}
	public int getReceivedWH() {
		return receivedWH;
	}
	public void setReceivedWH(int receivedWH) {
		this.receivedWH = receivedWH;
	}
	public int getReturnedC2C() {
		return returnedC2C;
	}
	public void setReturnedC2C(int returnedC2C) {
		this.returnedC2C = returnedC2C;
	}
	public int getReturnedChurn() {
		return returnedChurn;
	}
	public void setReturnedChurn(int returnedChurn) {
		this.returnedChurn = returnedChurn;
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
	public int getReturnedSwap() {
		return returnedSwap;
	}
	public void setReturnedSwap(int returnedSwap) {
		this.returnedSwap = returnedSwap;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	/**
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}
	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	/**
	 * @return the output
	 */
	public List<String[]> getOutput() {
		return output;
	}
	/**
	 * @param output the output to set
	 */
	public void setOutput(List<String[]> output) {
		this.output = output;
	}
	public List<GenericReportsOutputDto> getRecoList() {
		return recoList;
	}
	public void setRecoList(List<GenericReportsOutputDto> recoList) {
		this.recoList = recoList;
	}
	
	
	
	/*private String outputColumn1="";
	private String outputColumn2="";
	private String outputColumn3="";
	private String outputColumn4="";
	private String outputColumn5="";
	private String outputColumn6="";
	private String outputColumn7="";
	private String outputColumn8="";
	private String outputColumn9="";
	private String outputColumn10="";
	private String outputColumn11="";
	private String outputColumn12="";
	private String outputColumn13="";
	private String outputColumn14="";
	private String outputColumn15="";
	private String outputColumn16="";
	private String outputColumn17="";
	private String outputColumn18="";
	private String outputColumn19="";
	private String outputColumn20="";
	public String getOutputColumn1() {
		return outputColumn1;
	}
	public void setOutputColumn1(String outputColumn1) {
		this.outputColumn1 = outputColumn1;
	}
	public String getOutputColumn10() {
		return outputColumn10;
	}
	public void setOutputColumn10(String outputColumn10) {
		this.outputColumn10 = outputColumn10;
	}
	public String getOutputColumn11() {
		return outputColumn11;
	}
	public void setOutputColumn11(String outputColumn11) {
		this.outputColumn11 = outputColumn11;
	}
	public String getOutputColumn12() {
		return outputColumn12;
	}
	public void setOutputColumn12(String outputColumn12) {
		this.outputColumn12 = outputColumn12;
	}
	public String getOutputColumn13() {
		return outputColumn13;
	}
	public void setOutputColumn13(String outputColumn13) {
		this.outputColumn13 = outputColumn13;
	}
	public String getOutputColumn14() {
		return outputColumn14;
	}
	public void setOutputColumn14(String outputColumn14) {
		this.outputColumn14 = outputColumn14;
	}
	public String getOutputColumn15() {
		return outputColumn15;
	}
	public void setOutputColumn15(String outputColumn15) {
		this.outputColumn15 = outputColumn15;
	}
	public String getOutputColumn16() {
		return outputColumn16;
	}
	public void setOutputColumn16(String outputColumn16) {
		this.outputColumn16 = outputColumn16;
	}
	public String getOutputColumn17() {
		return outputColumn17;
	}
	public void setOutputColumn17(String outputColumn17) {
		this.outputColumn17 = outputColumn17;
	}
	public String getOutputColumn18() {
		return outputColumn18;
	}
	public void setOutputColumn18(String outputColumn18) {
		this.outputColumn18 = outputColumn18;
	}
	public String getOutputColumn19() {
		return outputColumn19;
	}
	public void setOutputColumn19(String outputColumn19) {
		this.outputColumn19 = outputColumn19;
	}
	public String getOutputColumn2() {
		return outputColumn2;
	}
	public void setOutputColumn2(String outputColumn2) {
		this.outputColumn2 = outputColumn2;
	}
	public String getOutputColumn20() {
		return outputColumn20;
	}
	public void setOutputColumn20(String outputColumn20) {
		this.outputColumn20 = outputColumn20;
	}
	public String getOutputColumn3() {
		return outputColumn3;
	}
	public void setOutputColumn3(String outputColumn3) {
		this.outputColumn3 = outputColumn3;
	}
	public String getOutputColumn4() {
		return outputColumn4;
	}
	public void setOutputColumn4(String outputColumn4) {
		this.outputColumn4 = outputColumn4;
	}
	public String getOutputColumn5() {
		return outputColumn5;
	}
	public void setOutputColumn5(String outputColumn5) {
		this.outputColumn5 = outputColumn5;
	}
	public String getOutputColumn6() {
		return outputColumn6;
	}
	public void setOutputColumn6(String outputColumn6) {
		this.outputColumn6 = outputColumn6;
	}
	public String getOutputColumn7() {
		return outputColumn7;
	}
	public void setOutputColumn7(String outputColumn7) {
		this.outputColumn7 = outputColumn7;
	}
	public String getOutputColumn8() {
		return outputColumn8;
	}
	public void setOutputColumn8(String outputColumn8) {
		this.outputColumn8 = outputColumn8;
	}
	public String getOutputColumn9() {
		return outputColumn9;
	}
	public void setOutputColumn9(String outputColumn9) {
		this.outputColumn9 = outputColumn9;
	}
	
	
*/
}
