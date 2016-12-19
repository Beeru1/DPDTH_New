package com.ibm.dp.dto;

public class ConfigurationMaster {
	
	private String configId;
	
	private String configName;

	private String configDesc;

	private String ConfigValue;
	

	public void setConfigValue(String configValue) {
		ConfigValue = configValue;
	}

	public String getConfigValue() {
		return ConfigValue;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigId() {
		return configId;
	}

	
}
