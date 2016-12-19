/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service;

import java.util.ArrayList;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for system configuration management service.
 * 
 * @author Navroze
 * 
 */
public interface SysConfigService {

	/**
	 * This method creates a system configuration
	 * 
	 * @param sysconfig
	 * @throws VirtualizationServiceException
	 */
	public void defineSystemConfiguration(SysConfig sysconfig)
			throws VirtualizationServiceException;

	/**
	 * This method returns list of system configuration details for a circle
	 * 
	 * @param circleId
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getSystemConfigurationList(int circleId)
			throws VirtualizationServiceException;

	/**
	 * This method returns system configuartion details.
	 * 
	 * @param sysConfigId
	 * @return SysConfig
	 * @throws VirtualizationServiceException
	 */
	public SysConfig getSystemConfigurationDetails(long sysConfigId)
			throws VirtualizationServiceException;

	/**
	 * This method returns system configuartion details.
	 * 
	 * @param sysConfigId
	 * @return SysConfig
	 * @throws VirtualizationServiceException
	 */
	public SysConfig getSystemConfigurationDetails(int circleId,
			TransactionType transactionType, ChannelType channeltype)
			throws VirtualizationServiceException;

	/**
	 * This method updates details of system configuration
	 * 
	 * @param sysconfig
	 * @throws VirtualizationServiceException
	 */
	public void updateSystemConfiguration(SysConfig sysconfig)
			throws VirtualizationServiceException;
}