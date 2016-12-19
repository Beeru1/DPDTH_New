/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.GroupRoleMappingDao;
import com.ibm.virtualization.recharge.dao.rdbms.GroupRoleMappingDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.GroupRoleMapping;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.GroupRoleMappingService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for Group Role Mapping.
 * 
 * @author Navroze
 * 
 */

public class GroupRoleMappingServiceImpl implements GroupRoleMappingService {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(GroupRoleMappingServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingService#listRoleAssigned(java.lang.integer)
	 */

	public ArrayList getRoleAssignedList(int groupId)
			throws VirtualizationServiceException {

		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList roleListAssigned = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();

			GroupRoleMappingDao groupDao = new GroupRoleMappingDaoRdbms(
					connection);
			roleListAssigned = groupDao.getRoleAssignedList(groupId);

		} catch (DAOException daoExp) {
			logger.fatal(" caught DAOException : unable to get role list."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");
		return roleListAssigned;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingService#getRoleAssignedList(int,
	 *      com.ibm.virtualization.recharge.common.ChannelType)
	 */
	public ArrayList getRoleAssignedList(int groupId, ChannelType channeltype)
			throws VirtualizationServiceException {

		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList roleListAssigned = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();

			GroupRoleMappingDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupRoleMappingDao(connection);
			roleListAssigned = groupDao.getRoleAssignedList(groupId,channeltype);
		} catch (DAOException daoExp) {
			logger.fatal(" caught DAOException : unable to get role list."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");
		return roleListAssigned;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingService#listRoleNotAssigned(java.lang.integer)
	 */

	public ArrayList getRoleNotAssignedList(int groupId)
			throws VirtualizationServiceException {

		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList roleListNotAssigned = null;

		try {
			connection = DBConnectionManager.getDBConnection();
			GroupRoleMappingDao groupDao = new GroupRoleMappingDaoRdbms(
					connection);
			roleListNotAssigned = groupDao.getRoleNotAssignedList(groupId);

		} catch (DAOException daoExp) {
			logger.fatal("aught DAOException : unable to get role list."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");
		return roleListNotAssigned;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingService#getRoleNotAssignedList(int,
	 *      com.ibm.virtualization.recharge.common.ChannelType)
	 */
	public ArrayList getRoleNotAssignedList(int groupId, ChannelType channelType)
			throws VirtualizationServiceException {

		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList roleListNotAssigned = null;

		try {
			connection = DBConnectionManager.getDBConnection();
			GroupRoleMappingDao groupDao = new GroupRoleMappingDaoRdbms(
					connection);
			roleListNotAssigned = groupDao.getRoleNotAssignedList(groupId ,channelType);
		} catch (DAOException daoExp) {
			logger.fatal("aught DAOException : unable to get role list."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");
		return roleListNotAssigned;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingService#updateRoles(com.ibm.virtualization.recharge.dto.GroupRoleMapping)
	 */

	public void updateRoles(GroupRoleMapping groupRole,ChannelType channelType)
			throws VirtualizationServiceException {

		logger.info("Started... " + groupRole);
		Connection connection = null;

		try {
			connection = DBConnectionManager.getDBConnection();
			GroupRoleMappingDao groupDao = new GroupRoleMappingDaoRdbms(
					connection);
			groupDao.updateRoles(groupRole,channelType);

		} catch (DAOException daoExp) {
			logger.fatal(" caught DAOException : unable to update role list."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");

	}
}
