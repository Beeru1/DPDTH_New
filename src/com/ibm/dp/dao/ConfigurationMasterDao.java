package com.ibm.dp.dao;

import com.ibm.dp.dto.ConfigurationMaster;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ConfigurationMasterDao {

	public void insertConfigDetail(ConfigurationMaster configMaster)throws DAOException;

	public void updateConfigDetails(ConfigurationMaster configMaster) throws DAOException;

	public ConfigurationMaster getConfigDetails(String configId) throws DAOException;

	
}
