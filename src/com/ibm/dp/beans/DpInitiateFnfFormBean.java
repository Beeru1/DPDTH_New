package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.DpInitiateFnfDto;

public class DpInitiateFnfFormBean extends ValidatorForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String remark;
	private String strChecked;
	private int DistId;
	
	private String strDistName="";
	private String StrDistOlmId="";
	private int days;
	private int parentId;
	private int distTranscId;
	private String searchedDistOlmId;
	private String strMsg="";
	private List<DpInitiateFnfDto>  distList  = new ArrayList<DpInitiateFnfDto>();
	private List<DpInitiateFnfDto> stockList = new ArrayList<DpInitiateFnfDto>();;
	private List<String> headers;
	private List<String[]> output;
	private String product="";
	private int serializedStock;
	private int nonSerializedStock;
	private int inTransitPOStock;
	private int damagedPendingStock;
	private int inTransitDCStock;
	private int upgradePending;
	private int RecoveryPending;
	private String approverRemark="";
	private String confirmerRemark="";
	public String getApproverRemark() {
		return approverRemark;
	}

	public void setApproverRemark(String approverRemark) {
		this.approverRemark = approverRemark;
	}

	public String getConfirmerRemark() {
		return confirmerRemark;
	}

	public void setConfirmerRemark(String confirmerRemark) {
		this.confirmerRemark = confirmerRemark;
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
		return RecoveryPending;
	}

	public void setRecoveryPending(int recoveryPending) {
		RecoveryPending = recoveryPending;
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

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String[]> getOutput() {
		return output;
	}

	public void setOutput(List<String[]> output) {
		this.output = output;
	}

	public List<DpInitiateFnfDto> getDistList() {
		return distList;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getStrDistName() {
		return strDistName;
	}

	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}

	public String getStrDistOlmId() {
		return StrDistOlmId;
	}

	public void setStrDistOlmId(String strDistOlmId) {
		StrDistOlmId = strDistOlmId;
	}

	public void setDistList(List<DpInitiateFnfDto> distList) {
		this.distList = distList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStrChecked() {
		return strChecked;
	}

	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	public int getDistId() {
		return DistId;
	}

	public void setDistId(int distId) {
		DistId = distId;
	}

	public String getSearchedDistOlmId() {
		return searchedDistOlmId;
	}

	public void setSearchedDistOlmId(String searchedDistOlmId) {
		this.searchedDistOlmId = searchedDistOlmId;
	}

	public String getStrMsg() {
		return strMsg;
	}

	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
	}

	public int getDistTranscId() {
		return distTranscId;
	}

	public void setDistTranscId(int distTranscId) {
		this.distTranscId = distTranscId;
	}

	public List<DpInitiateFnfDto> getStockList() {
		return stockList;
	}

	public void setStockList(List<DpInitiateFnfDto> stockList) {
		this.stockList = stockList;
	}


	
}
