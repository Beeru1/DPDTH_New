package com.ibm.dp.dto;

import java.util.ArrayList;
import java.util.List;

public class DpInitiateFnfDto {
	
	private String strDistName="";
	private int distId;
	private String strDistOlmId="";
	private int days;
	private int parentId;
	private int distTranscId;
	private List<DpInitiateFnfDto> dataList  = new ArrayList<DpInitiateFnfDto>();
	private String strMsg ="";
	private String product="";
	private int serializedStock;
	private int nonSerializedStock;
	private int inTransitPOStock;
	private int damagedPendingStock;
	private int inTransitDCStock;
	private int upgradePending;
	private int recoveryPending;
	private String approverName="";
	private String approverRemark="";
	private String debitReq="";
	private String strTSMOlmId="";
	private String tsmId="";
	private int reqId;
	private String confirmerName="";
	private String confirmerRemark="";
	private String remark="";
	private int sum=0;
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverRemark() {
		return approverRemark;
	}
	public void setApproverRemark(String approverRemark) {
		this.approverRemark = approverRemark;
	}
	public List<DpInitiateFnfDto> getDataList() {
		return dataList;
	}
	public void setDataList(List<DpInitiateFnfDto> dataList) {
		this.dataList = dataList;
	}
	public String getStrMsg() {
		return strMsg;
	}
	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
	public String getStrDistName() {
		return strDistName;
	}
	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}

	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getDistId() {
		return distId;
	}
	public void setDistId(int distId) {
		this.distId = distId;
	}
	public String getStrDistOlmId() {
		return strDistOlmId;
	}
	public void setStrDistOlmId(String strDistOlmId) {
		this.strDistOlmId = strDistOlmId;
	}
	public int getDistTranscId() {
		return distTranscId;
	}
	public void setDistTranscId(int distTranscId) {
		this.distTranscId = distTranscId;
	}
	public int getDamagedPendingStock() {
		return damagedPendingStock;
	}
	public void setDamagedPendingStock(int damagedPendingStock) {
		this.damagedPendingStock = damagedPendingStock;
	}
	public int getInTransitDCStock() {
		return inTransitDCStock;
	}
	public void setInTransitDCStock(int inTransitDCStock) {
		this.inTransitDCStock = inTransitDCStock;
	}
	public int getInTransitPOStock() {
		return inTransitPOStock;
	}
	public void setInTransitPOStock(int inTransitPOStock) {
		this.inTransitPOStock = inTransitPOStock;
	}
	public int getNonSerializedStock() {
		return nonSerializedStock;
	}
	public void setNonSerializedStock(int nonSerializedStock) {
		this.nonSerializedStock = nonSerializedStock;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getRecoveryPending() {
		return recoveryPending;
	}
	public void setRecoveryPending(int recoveryPending) {
		this.recoveryPending = recoveryPending;
	}
	public int getSerializedStock() {
		return serializedStock;
	}
	public void setSerializedStock(int serializedStock) {
		this.serializedStock = serializedStock;
	}
	public int getUpgradePending() {
		return upgradePending;
	}
	public void setUpgradePending(int upgradePending) {
		this.upgradePending = upgradePending;
	}
	public String getDebitReq() {
		return debitReq;
	}
	public void setDebitReq(String debitReq) {
		this.debitReq = debitReq;
	}
	public String getStrTSMOlmId() {
		return strTSMOlmId;
	}
	public void setStrTSMOlmId(String strTSMOlmId) {
		this.strTSMOlmId = strTSMOlmId;
	}
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public String getConfirmerRemark() {
		return confirmerRemark;
	}
	public void setConfirmerRemark(String confirmerRemark) {
		this.confirmerRemark = confirmerRemark;
	}
	public String getConfirmerName() {
		return confirmerName;
	}
	public void setConfirmerName(String confirmerName) {
		this.confirmerName = confirmerName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
