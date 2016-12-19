package com.ibm.hbo.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class AssignDistStockFormBean extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String category=null;
	String circle=null;
	String product=null;
	String productName="";
	int productCode;
	String accountFrom="";
	String accountTo="";
	int availableProdQty;
	int assignedProdQty;
	String startingSerial="";
	String endingSno="";
	int userTo=0;
	String userNameTo="";
	int userFrom=0;
	String userNameFrom="";
	ArrayList arrCategory=new ArrayList();
	ArrayList arrCircle=new ArrayList();
	ArrayList arrAssignTo=new ArrayList();
	ArrayList arrAssignFrom=new ArrayList();
	String remarks="";
	//Add on UAT observation by harabns on 16th Sept
	String dcRemarks="";
	String startDt;
	String endDt;
	String methodName;
	ArrayList displayDetails;
	String reportType;
	ArrayList prodlist=null;
	String foc="";
	String billno;
	String vat;
	String rate;
	String err_Msg="";
	ArrayList availableSerialNosList = null;
	String startingSerialNo = "";
	String endingSerialNo = "";
	Integer intSRAvail = null;
	String funcFlag = "";
	
	String accountFromName="";
	String accountToName ="";
	String strfailSerialNo="";
	
	String[] serialNo = null;
	String cond1="";
	String cond2="";		
	String cond3="";
	String cond4="";
	String cond5="";
	String cond6="";
	String cond7="";	
	String cond8="";
	String cond9="";	
	
	String jsArrprodId[];
	String jsArrremarks[];
	String jsArrqty[];
	String jsSrNo[];
	String message="";
	//Added by Shilpa for critical changes BFR 14
	String courierAgency;
	String docketNum;
	boolean validate_flag=false;
	public boolean isValidate_flag() {
		return validate_flag;
	}
	public void setValidate_flag(boolean validate_flag) {
		this.validate_flag = validate_flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getJsArrprodId() {
		return jsArrprodId;
	}
	public void setJsArrprodId(String[] jsArrprodId) {
		this.jsArrprodId = jsArrprodId;
	}
	public String[] getJsArrqty() {
		return jsArrqty;
	}
	public void setJsArrqty(String[] jsArrqty) {
		this.jsArrqty = jsArrqty;
	}
	public String[] getJsArrremarks() {
		return jsArrremarks;
	}
	public void setJsArrremarks(String[] jsArrremarks) {
		this.jsArrremarks = jsArrremarks;
	}
	public String[] getJsSrNo() {
		return jsSrNo;
	}
	public void setJsSrNo(String[] jsSrNo) {
		this.jsSrNo = jsSrNo;
	}
	public String getBillno() {
		return billno; 
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public ArrayList getDisplayDetails() {
		return displayDetails;
	}
	public void setDisplayDetails(ArrayList displayDetails) {
		this.displayDetails = displayDetails;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}
	public String getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}
	public ArrayList getArrCategory() {
		return arrCategory;
	}
	public void setArrCategory(ArrayList arrCategory) {
		this.arrCategory = arrCategory;
	}
	public int getAssignedProdQty() {
		return assignedProdQty;
	}
	public void setAssignedProdQty(int assignedProdQty) {
		this.assignedProdQty = assignedProdQty;
	}
	public int getAvailableProdQty() {
		return availableProdQty;
	}
	public void setAvailableProdQty(int availableProdQty) {
		this.availableProdQty = availableProdQty;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEndingSno() {
		return endingSno;
	}
	public void setEndingSno(String endingSno) {
		this.endingSno = endingSno;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getProductCode() {
		return productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	public String getStartingSerial() {
		return startingSerial;
	}
	public void setStartingSerial(String startingSerial) {
		this.startingSerial = startingSerial;
	}
	public ArrayList getArrAssignFrom() {
		return arrAssignFrom;
	}
	public void setArrAssignFrom(ArrayList arrAssignFrom) {
		this.arrAssignFrom = arrAssignFrom;
	}
	public ArrayList getArrAssignTo() {
		return arrAssignTo;
	}
	public void setArrAssignTo(ArrayList arrAssignTo) {
		this.arrAssignTo = arrAssignTo;
	}
	public int getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(int userFrom) {
		this.userFrom = userFrom;
	}
	public String getUserNameFrom() {
		return userNameFrom;
	}
	public void setUserNameFrom(String userNameFrom) {
		this.userNameFrom = userNameFrom;
	}
	public String getUserNameTo() {
		return userNameTo;
	}
	public void setUserNameTo(String userNameTo) {
		this.userNameTo = userNameTo;
	}
	public int getUserTo() {
		return userTo;
	}
	public void setUserTo(int userTo) {
		this.userTo = userTo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}


	public ArrayList getAvailableSerialNosList() {
		return availableSerialNosList;
	}
	public void setAvailableSerialNosList(ArrayList availableSerialNosList) {
		this.availableSerialNosList = availableSerialNosList;
	}
	public String[] getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String[] serialNo) {
		this.serialNo = serialNo;
	}
	public String getEndingSerialNo() {
		return endingSerialNo;
	}
	public void setEndingSerialNo(String endingSerialNo) {
		this.endingSerialNo = endingSerialNo;
	}
	public String getStartingSerialNo() {
		return startingSerialNo;
	}
	public void setStartingSerialNo(String startingSerialNo) {
		this.startingSerialNo = startingSerialNo;
	}
	public ArrayList getProdlist() {
		return prodlist;
	}
	public void setProdlist(ArrayList prodlist) {
		this.prodlist = prodlist;
	}
	public String getFoc() {
		return foc;
	}
	public void setFoc(String foc) {
		this.foc = foc;
	}
	public Integer getIntSRAvail() {
		return intSRAvail;
	}
	public void setIntSRAvail(Integer intSRAvail) {
		this.intSRAvail = intSRAvail;
	}

	public String getFuncFlag() {
		return funcFlag;
	}
	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getCond1() {
		return cond1;
	}
	public void setCond1(String cond1) {
		this.cond1 = cond1;
	}
	public String getCond2() {
		return cond2;
	}
	public void setCond2(String cond2) {
		this.cond2 = cond2;
	}
	public String getCond3() {
		return cond3;
	}
	public void setCond3(String cond3) {
		this.cond3 = cond3;
	}
	public String getCond4() {
		return cond4;
	}
	public void setCond4(String cond4) {
		this.cond4 = cond4;
	}
	public String getCond5() {
		return cond5;
	}
	public void setCond5(String cond5) {
		this.cond5 = cond5;
	}
	public String getCond6() {
		return cond6;
	}
	public void setCond6(String cond6) {
		this.cond6 = cond6;
	}
	public String getCond7() {
		return cond7;
	}
	public void setCond7(String cond7) {
		this.cond7 = cond7;
	}
	public String getCond8() {
		return cond8;
	}
	public void setCond8(String cond8) {
		this.cond8 = cond8;
	}
	public String getCond9() {
		return cond9;
	}
	public void setCond9(String cond9) {
		this.cond9 = cond9;
	}
	public String getErr_Msg() {
		return err_Msg;
	}
	public void setErr_Msg(String err_Msg) {
		this.err_Msg = err_Msg;
	}
	public String getAccountFromName() {
		return accountFromName;
	}
	public void setAccountFromName(String accountFromName) {
		this.accountFromName = accountFromName;
	}
	public String getAccountToName() {
		return accountToName;
	}
	public void setAccountToName(String accountToName) {
		this.accountToName = accountToName;
	}
	public String getDcRemarks() {
		return dcRemarks;
	}
	public void setDcRemarks(String dcRemarks) {
		this.dcRemarks = dcRemarks;
	}
	public String getCourierAgency() {
		return courierAgency;
	}
	public void setCourierAgency(String courierAgency) {
		this.courierAgency = courierAgency;
	}
	public String getDocketNum() {
		return docketNum;
	}
	public void setDocketNum(String docketNum) {
		this.docketNum = docketNum;
	}
	public String getStrfailSerialNo() {
		return strfailSerialNo;
	}
	public void setStrfailSerialNo(String strfailSerialNo) {
		this.strfailSerialNo = strfailSerialNo;
	}
	public ArrayList getArrCircle() {
		return arrCircle;
	}
	public void setArrCircle(ArrayList arrCircle) {
		this.arrCircle = arrCircle;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}

	

}
