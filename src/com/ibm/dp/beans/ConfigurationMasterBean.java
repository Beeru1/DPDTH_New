package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;

public class ConfigurationMasterBean extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String configId;
	private String configName;
	private String configDesc;
	private String configValue;
    private String methodName;
    private String isEdit;
    
    
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigDesc() {
		return configDesc;
	}
	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getIsEdit() {
		return isEdit;
	}
	
		
}
