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

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.GroupDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.service.GroupService;
/**
 * This class provides implementation for group related functionalities.
 * 
 * @author Paras
 * 
 */
public class GroupServiceImpl implements GroupService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(GroupServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#createGroup(com.ibm.virtualization.recharge.dto.Group)
	 */
	public void createGroup(Group group) throws NumberFormatException, VirtualizationServiceException {
		logger.info("Started..." + group);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			String groupName="";
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			if (group != null) {
			 groupName = group.getGroupName();
			// Check if Group Name Entered already exists.
			if (checkGroupNameAvailability(groupName)) {
				// Group Name for new group is not in use.
				// Send the DTO data to DAO for insertion into database
				
					try {
						/*
						 * Call the validate method of GroupValidator to
						 * validate Group DTO data.
						 */
						ValidatorFactory.getGroupValidator().validateInsert(
								group);
						groupDao.insertGroup(group);
						/* Commit the transaction */
						DBConnectionManager.commitTransaction(connection);
					} catch (VirtualizationServiceException validationExp) {
						logger
								.error(" Exception occured : Validation Failed for Group Data.");
						throw new VirtualizationServiceException(validationExp
								.getMessage());
					}
				}
			} else {
				// else throw an exception that Group already exist.
				logger.error(" A Group already exist with groupName ="
						+ groupName);
				throw new VirtualizationServiceException(
						ExceptionCode.GroupRole.ERROR_GROUP_NAME_ALREADY_EXIST);
			}
		} catch (DAOException daoExp) {
			logger.fatal(" caught DAOException : unable to create new Group."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed...Group created successfully.");
	}

	/**
	 * This method returns true if no group is found in the database with the
	 * name provided
	 * 
	 * @param groupName
	 * @return
	 * @throws VirtualizationServiceException
	 */
	private boolean checkGroupNameAvailability(String groupName)
			throws VirtualizationServiceException {
		logger.info("Started...groupName " + groupName);
		Group group = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			group = new Group();
			group = groupDao.getGroup(groupName);
			logger.info(" Executed ....");
		} catch (DAOException daoExp) {
			logger.error(" caught DAOException." + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			logger.info(" Finally release connection.");
			DBConnectionManager.releaseResources(connection);
		}
		if (group == null) {
			// groupName is available
			return true;
		} else {
			// groupName already in Use
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#updateGroup(com.ibm.virtualization.recharge.dto.Group)
	 */
	public void updateGroup(Group group) throws VirtualizationServiceException {
		logger.info("Started..." + group);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(
					connection);
			if (group != null) {
				try {
					/*
					 * Call the validate method of GroupValidator to validate
					 * Group DTO data.
					 */
					ValidatorFactory.getGroupValidator().validateUpdate(group);

				} catch (VirtualizationServiceException validationExp) {
					logger
							.error(" Exception occured : Validation Failed for Group Data.");
					throw new VirtualizationServiceException(validationExp
							.getMessage());
				}

				/* Check if group exist with name specified */
				Group retrievedGroup = groupDao.getGroup(group.getGroupName());

				/*
				 * If no group exist with the name specified then update else if
				 * no group exist with the name specified and the same id then
				 * update
				 */
				if (retrievedGroup == null
						|| retrievedGroup.getGroupId() == group.getGroupId()) {
					groupDao.updateGroup(group);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				} else {
					logger
							.error(" Exception occured : Group Name already in use.");
					throw new VirtualizationServiceException(
							ExceptionCode.GroupRole.ERROR_GROUP_NAME_ALREADY_EXIST);
				}
			}
		} catch (DAOException daoExp) {
			logger.error(" caught DAOException." + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException:"
					+ "unable to get value from resource bundle"
					+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... Group updated successfully.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#getGroup(java.lang.String)
	 */
	public Group getGroup(int groupId)
			throws VirtualizationServiceException {
		logger.info("Started...groupName " + groupId);
		Group group;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			group = new Group();
			group = groupDao.getGroup(groupId);
		} catch (DAOException daoExp) {
			logger.error(" Caught DAOException exception."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... Group details returned successfully.");
		return group;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#getGroup(java.lang.String)
	 */
	public Group getGroup(String groupName)
			throws VirtualizationServiceException {
		logger.info("Started...groupName " + groupName);
		Group group;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			group = new Group();
			group = groupDao.getGroup(groupName);
		} catch (DAOException daoExp) {
			logger.error(" Caught DAOException exception."
					+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... Group details returned successfully.");
		return group;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#getGroups()
	 */
	public ArrayList getGroups(int groupId) throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList groupList = null;
		Connection connection = null;
		try {
			groupList = new ArrayList();
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			groupList = groupDao.getGroups(groupId);
		} catch (DAOException daoExp) {
			logger
					.error("Caught DAOException exception."
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... List of Groups returned successfully.");
		return groupList;
	}
	public ArrayList getGroups() throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList groupList = null;
		Connection connection = null;
		try {
			groupList = new ArrayList();
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			groupList = groupDao.getGroups();
		} catch (DAOException daoExp) {
			logger
					.error("Caught DAOException exception."
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... List of Groups returned successfully.");
		return groupList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupService#getGroupName(int)
	 */
	public String getGroupName(int groupId)
			throws VirtualizationServiceException {
		logger.info("Started...groupId " + groupId);
		String groupName = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			groupName = groupDao.getGroupName(groupId);
		} catch (DAOException daoExp) {
			logger
					.error("Caught DAOException exception."
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ...Group Name returned successfully.");
		return groupName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.GroupService#getExternalGroups()
	 */
	public ArrayList getGroups(String groupId) throws VirtualizationServiceException {
		logger.info("Started...type "+groupId);
		ArrayList groupList = null;
		Connection connection = null;
		try {
			groupList = new ArrayList();
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			GroupDao groupDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getGroupDao(connection);
			groupList = groupDao.getGroups(groupId);
		} catch (DAOException daoExp) {
			logger
					.error("Caught DAOException exception."
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed... List of External Groups returned successfully. for  type :-"+groupId);
		return groupList;
	}
	
	
	
}