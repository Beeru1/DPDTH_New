package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;
import com.ibm.dp.dto.AlertConfigurationDTO;
public class DPAlertConfigurationBean  extends ValidatorForm  {
	
	/**
	 * 
	 */
	

	private List<AlertConfigurationDTO>  alertList  = new ArrayList<AlertConfigurationDTO>();
	private List<AlertConfigurationDTO> userGroupId = new ArrayList<AlertConfigurationDTO>() ;
	private String strNoteMsg = "";
	private String tsmGracePeriod;
	private String zsmGracePeriod;
	private String strMsg="";
	
	public String getStrMsg() {
		return strMsg;
	}
	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
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
	public String getStrNoteMsg() {
		return strNoteMsg;
	}
	public void setStrNoteMsg(String strNoteMsg) {
		this.strNoteMsg = strNoteMsg;
	}
	public List<AlertConfigurationDTO> getAlertList() {
		return alertList;
	}
	public void setAlertList(List<AlertConfigurationDTO> alertList) {
		this.alertList = alertList;
	}
	public List<AlertConfigurationDTO> getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(List<AlertConfigurationDTO> userGroupId) {
		this.userGroupId = userGroupId;
	}
	
		
	
	
		
	}
	

		

