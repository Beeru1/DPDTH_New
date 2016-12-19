package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.DpDcFreshStatusDto;

public class DpDcDamageStatusBean extends ActionForm{

	private String methodName;
	private String strStatus;
	private String strDCNo;
	private String strDCDate;
	private String strBOTreeRemarks;
	private String strSuccessMsg;
	private String strSerialNo;
	private String[] hidArrDcNos;
	private String hidCourAgen="";
	private String hidDocketNum="";
	private List<DpDcDamageStatusDto> dcNosList ;
	private String strDcStatus;
	private String dcType;
	
	private String methodNameFresh;
	private String strStatusFresh;
	private String strDCNoFresh;
	private String strDCDateFresh;
	private String strBOTreeRemarksFresh;
	private String strSuccessMsgFresh;
	private String strSerialNoFresh;
	private String[] hidArrDcNosFresh;
	private List<DpDcFreshStatusDto> dcNosListFresh ;
	private String strDcStatusFresh;
	
	private String methodNameChurn;
	private String strStatusChurn;
	private String strDCNoChurn;
	private String strDCDateChurn;
	private String strBOTreeRemarksChurn;
	private String strSuccessMsgChurn;
	private String strSerialNoChurn;
	private String[] hidArrDcNosChurn;
	private List<DpDcChurnStatusDto> dcNosListChurn;
	private String strDcStatusChurn;

	public String getMethodName() {
		return methodName;
	}
	public List<DpDcFreshStatusDto> getDcNosListFresh() {
		return dcNosListFresh;
	}
	public void setDcNosListFresh(List<DpDcFreshStatusDto> dcNosListFresh) {
		this.dcNosListFresh = dcNosListFresh;
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
	public List<DpDcDamageStatusDto> getDcNosList() {
		return dcNosList;
	}
	public void setDcNosList(List<DpDcDamageStatusDto> dcNosList) {
		this.dcNosList = dcNosList;
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
	public String[] getHidArrDcNosFresh() {
		return hidArrDcNosFresh;
	}
	public void setHidArrDcNosFresh(String[] hidArrDcNosFresh) {
		this.hidArrDcNosFresh = hidArrDcNosFresh;
	}
	public String getMethodNameFresh() {
		return methodNameFresh;
	}
	public void setMethodNameFresh(String methodNameFresh) {
		this.methodNameFresh = methodNameFresh;
	}
	public String getStrBOTreeRemarksFresh() {
		return strBOTreeRemarksFresh;
	}
	public void setStrBOTreeRemarksFresh(String strBOTreeRemarksFresh) {
		this.strBOTreeRemarksFresh = strBOTreeRemarksFresh;
	}
	public String getStrDCDateFresh() {
		return strDCDateFresh;
	}
	public void setStrDCDateFresh(String strDCDateFresh) {
		this.strDCDateFresh = strDCDateFresh;
	}
	public String getStrDCNoFresh() {
		return strDCNoFresh;
	}
	public void setStrDCNoFresh(String strDCNoFresh) {
		this.strDCNoFresh = strDCNoFresh;
	}
	public String getStrDcStatusFresh() {
		return strDcStatusFresh;
	}
	public void setStrDcStatusFresh(String strDcStatusFresh) {
		this.strDcStatusFresh = strDcStatusFresh;
	}
	public String getStrSerialNoFresh() {
		return strSerialNoFresh;
	}
	public void setStrSerialNoFresh(String strSerialNoFresh) {
		this.strSerialNoFresh = strSerialNoFresh;
	}
	public String getStrStatusFresh() {
		return strStatusFresh;
	}
	public void setStrStatusFresh(String strStatusFresh) {
		this.strStatusFresh = strStatusFresh;
	}
	public String getStrSuccessMsgFresh() {
		return strSuccessMsgFresh;
	}
	public void setStrSuccessMsgFresh(String strSuccessMsgFresh) {
		this.strSuccessMsgFresh = strSuccessMsgFresh;
	}
	public String getDcType() {
		return dcType;
	}
	public void setDcType(String dcType) {
		this.dcType = dcType;
	}
	public String getHidCourAgen() {
		return hidCourAgen;
	}
	public void setHidCourAgen(String hidCourAgen) {
		this.hidCourAgen = hidCourAgen;
	}
	public String getHidDocketNum() {
		return hidDocketNum;
	}
	public void setHidDocketNum(String hidDocketNum) {
		this.hidDocketNum = hidDocketNum;
	}
	public List<DpDcChurnStatusDto> getDcNosListChurn() {		return dcNosListChurn;	}
	
	public void setDcNosListChurn(List<DpDcChurnStatusDto> dcNosListChurn) {		this.dcNosListChurn = dcNosListChurn;}
	
	public String[] getHidArrDcNosChurn() {		return hidArrDcNosChurn;	}
	public void setHidArrDcNosChurn(String[] hidArrDcNosChurn) {		this.hidArrDcNosChurn = hidArrDcNosChurn;	}
	
	public String getMethodNameChurn() {		return methodNameChurn;	}
	public void setMethodNameChurn(String methodNameChurn) {		this.methodNameChurn = methodNameChurn;	}
	
	public String getStrBOTreeRemarksChurn() {		return strBOTreeRemarksChurn;	}
	public void setStrBOTreeRemarksChurn(String strBOTreeRemarksChurn) {		this.strBOTreeRemarksChurn = strBOTreeRemarksChurn;	}
	
	public String getStrDCDateChurn() {		return strDCDateChurn;	}
	public void setStrDCDateChurn(String strDCDateChurn) {		this.strDCDateChurn = strDCDateChurn;	}
	
	public String getStrDCNoChurn() {		return strDCNoChurn;	}
	public void setStrDCNoChurn(String strDCNoChurn) {		this.strDCNoChurn = strDCNoChurn;	}
	
	public String getStrDcStatusChurn() {		return strDcStatusChurn;	}
	public void setStrDcStatusChurn(String strDcStatusChurn) {		this.strDcStatusChurn = strDcStatusChurn;	}

	public String getStrSerialNoChurn() {		return strSerialNoChurn;	}
	public void setStrSerialNoChurn(String strSerialNoChurn) {		this.strSerialNoChurn = strSerialNoChurn;	}
	
	public String getStrStatusChurn() {		return strStatusChurn;	}
	public void setStrStatusChurn(String strStatusChurn) {		this.strStatusChurn = strStatusChurn;	}
	
	public String getStrSuccessMsgChurn() {		return strSuccessMsgChurn;	}
	public void setStrSuccessMsgChurn(String strSuccessMsgChurn) {		this.strSuccessMsgChurn = strSuccessMsgChurn;	}
	
	
}
