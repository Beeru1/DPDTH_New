package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.GenericOptionDTO;

public class DayWiseActivationSummaryReportBean extends ActionForm{
	
	private int circleid = 0;
	private String circleId;		
	private String fromDate;
	private String toDate;
	private String circleName;
	private String showCircle;
	private String methodName;
	private String hiddenCircleSelecIds;
	private String submitBtn;
	private List circleReportList;
	private List headers;
	private List output;
	private String showTSM="";
	String accountId;
	public List<ProductMasterDto> productList = new ArrayList<ProductMasterDto>();
	private ArrayList arrCIList=null;
	private ArrayList arrCGList=null;
	private String accountLevel;
	
	public String getAccountLevel() {
		return accountLevel;
	}



	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}



	public ArrayList getArrCGList() {
		return arrCGList;
	}



	public void setArrCGList(ArrayList arrCGList) {
		this.arrCGList = arrCGList;
	}



	public List getHeaders() {
		return headers;
	}



	public void setHeaders(List headers) {
		this.headers = headers;
	}



	public List getOutput() {
		return output;
	}



	public void setOutput(List output) {
		this.output = output;
	}



	public List getCircleReportList() {
		return circleReportList;
	}



	public void setCircleReportList(List circleReportList) {
		this.circleReportList = circleReportList;
	}



	public String getSubmitBtn() {
		return submitBtn;
	}



	public void setSubmitBtn(String submitBtn) {
		this.submitBtn = submitBtn;
	}



	public String getShowCircle() {
		return showCircle;
	}







	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}



	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}



	public String getMethodName() {
		return methodName;
	}



	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}



	private List<SelectionCollection> circleList =new ArrayList<SelectionCollection>();



	public String getCircleId() {
		return circleId;
	}



	public void setCircleId(String circleId) {
		this.circleId = circleId;
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



	public String getCircleName() {
		return circleName;
	}



	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}



	public List<SelectionCollection> getCircleList() {
		return circleList;
	}



	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
	}



	public void setShowCircle(String accountNotShowCircle) {
		
		
	}



	public String getShowTSM() {
		return showTSM;
	}



	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}



	public String getAccountId() {
		return accountId;
	}



	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	public List<ProductMasterDto> getProductList() {
		return productList;
	}



	public void setProductList(List<ProductMasterDto> productList) {
		this.productList = productList;
	}



	public ArrayList getArrCIList() {
		return arrCIList;
	}



	public void setArrCIList(ArrayList arrCIList) {
		this.arrCIList = arrCIList;
	}



	public int getCircleid() {
		return circleid;
	}



	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	
	
}