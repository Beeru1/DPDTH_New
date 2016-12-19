package com.ibm.dp.service;

import com.ibm.dp.dto.ConfigurationMaster;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface ConfigurationService {

	public void insertConfigurationDetail(ConfigurationMaster configMaster) throws VirtualizationServiceException;
	public ConfigurationMaster getConfigDetails(String  configId) throws  VirtualizationServiceException;
	public void updateConfigDetails(ConfigurationMaster configMaster) throws VirtualizationServiceException;

}
