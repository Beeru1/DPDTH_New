package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpDcFreshStatusDto;

public class DpDcFreshStatusBean extends ActionForm{

	private String methodName;
	private String strStatus;
	private String strDCNo;
	private String strDCDate;
	private String strBOTreeRemarks;
	private String strSuccessMsg;
	private String strSerialNo;
	private String[] hidArrDcNos;
	private List<DpDcFreshStatusDto> dcNosList ;
	private String strDcStatus;
	
	
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStrBOTreeRemarks() {
		return strBOTreeRemarks;
	}
	public void setStrBOTreeRemarks(String strBOTreeRemarks) {
		this.strBOTreeRemarks = strBOTreeRemarks;
	}
	public String getStrDCDate() {
		return strDCDate;
	}
	public void setStrDCDate(String strDCDate) {
		this.strDCDate = strDCDate;
	}
	public String getStrDCNo() {
		return strDCNo;
	}
	public void setStrDCNo(String strDCNo) {
		this.strDCNo = strDCNo;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}
	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
	public String getStrSerialNo() {
		return strSerialNo;
	}
	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
	}
	public String[] getHidArrDcNos() {
		return hidArrDcNos;
	}
	public void setHidArrDcNos(String[] hidArrDcNos) {
		this.hidArrDcNos = hidArrDcNos;
	}
	public String getStrDcStatus() {
		return strDcStatus;
	}
	public void setStrDcStatus(String strDcStatus) {
		this.strDcStatus = strDcStatus;
	}
	public List<DpDcFreshStatusDto> getDcNosList() {
		return dcNosList;
	}
	public void setDcNosList(List<DpDcFreshStatusDto> dcNosList) {
		this.dcNosList = dcNosList;
	}
	
	
	
	
}
