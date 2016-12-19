package com.ibm.dp.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class RecoPeriodDTO implements Serializable  {
	 public String recoPeriodId = "";
	 public String recoPeriodName = "";
	 public String fromDate="";
	 public String toDate="";
	 public String gracePeriod="";
	 public String recoPeriod="";
	 public String recoPeriodAction="";
	 public String updatedby="";
	 public String distOlmId;
	 public String remarks="";
	 public String distId="";
	 public String linkFlag="";
	 private String err_msg="";
	 private String olmId="";
	 private ArrayList list_srno=null;
	
	 public String getRecoPeriodId() {
		return recoPeriodId;
	}
	public void setRecoPeriodId(String recoPeriodId) {
		this.recoPeriodId = recoPeriodId;
	}
	public String getRecoPeriodName() {
		return recoPeriodName;
	}
	public void setRecoPeriodName(String recoPeriodName) {
		this.recoPeriodName = recoPeriodName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getRecoPeriod() {
		return recoPeriod;
	}
	public void setRecoPeriod(String recoPeriod) {
		this.recoPeriod = recoPeriod;
	}
	public String getRecoPeriodAction() {
		return recoPeriodAction;
	}
	public void setRecoPeriodAction(String recoPeriodAction) {
		this.recoPeriodAction = recoPeriodAction;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	 
	public String getDistId() {
			return distId;
		}
	public void setDistId(String distId) {
			this.distId = distId;
		}
	public String getLinkFlag() {
		return linkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}
}
