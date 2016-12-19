/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao;

import java.util.ArrayList;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for SysConfigDao Implementation.
 * 
 * @author Navroze
 * 
 */
public interface SysConfigDao {
	/**
	 * This method inserts details of the System Configuartion.
	 * 
	 * @param sysconfig
	 * @return
	 * @throws DAOException
	 */
	public void insertSystemConfigurationDetail(SysConfig sysconfig)
			throws DAOException;

	/**
	 * This method returns list of system configuartion details for a circle .
	 * 
	 * @param circleId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getSystemConfigurationList(int circleId)
			throws DAOException;

	/**
	 * This method returns system configuartion details.
	 * 
	 * @param sysConfigId
	 * @return SysConfig
	 * @throws DAOException
	 */
	public SysConfig getSystemConfigurationDetails(int circleId,
			TransactionType transactionType, ChannelType channelName)
			throws DAOException;

	/**
	 * This method returns system configuartion details.
	 * 
	 * @param sysConfigId
	 * @return SysConfig
	 * @throws DAOException
	 */
	public SysConfig getSystemConfigurationDetails(long sysConfigId)
			throws DAOException;

	/**
	 * This method updates system configuration details
	 * 
	 * @param sysconfig
	 * @throws DAOException
	 */
	public void updateSystemConfiguration(SysConfig sysconfig)
			throws DAOException;

}