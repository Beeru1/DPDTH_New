package com.ibm.dp.dto;

import java.io.Serializable;

public class AlertConfigurationDTO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int accountId;
	private int  alertId;
	private String alertDesc;
	private boolean  enableFlag;
	private String enabledChecked="";
	private String disabledChecked="";	
	private int alertMstrStatus;	
	private String tsmGracePeriod;
	private String zsmGracePeriod;
	
	private String checkedStatus;
	
	public String getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(String checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public int getAlertMstrStatus() {
		return alertMstrStatus;
	}
	public void setAlertMstrStatus(int alertMstrStatus) {
		this.alertMstrStatus = alertMstrStatus;
	}
	public String getAlertDesc() {
		return alertDesc;
	}
	public void setAlertDesc(String alertDesc) {
		this.alertDesc = alertDesc;
	}
	public int getAlertId() {
		return alertId;
	}
	public void setAlertId(int alertId) {
		this.alertId = alertId;
	}
	public boolean isEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getDisabledChecked() {
		return disabledChecked;
	}
	public void setDisabledChecked(String disabledChecked) {
		this.disabledChecked = disabledChecked;
	}
	public String getEnabledChecked() {
		return enabledChecked;
	}
	public void setEnabledChecked(String enabledChecked) {
		this.enabledChecked = enabledChecked;
	}
	public String getTsmGracePeriod() {
		return tsmGracePeriod;
	}
	public void setTsmGracePeriod(String tsmGracePeriod) {
		this.tsmGracePeriod = tsmGracePeriod;
	}
	public String getZsmGracePeriod() {
		return zsmGracePeriod;
	}
	public void setZsmGracePeriod(String zsmGracePeriod) {
		this.zsmGracePeriod = zsmGracePeriod;
	}

}