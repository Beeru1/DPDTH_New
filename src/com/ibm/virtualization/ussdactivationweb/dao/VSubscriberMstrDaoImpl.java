/**************************************************************************
 * File     : VSubscriberMstrDaoImpl.java
 * Author   : Ashad
 * Created  : Sep 10, 2008
 * Modified : Sep 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BusinessUserInterface;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.VSubscriberMstrDao;
import com.ibm.virtualization.ussdactivationweb.dto.CircleMasterDTO;
import com.ibm.virtualization.ussdactivationweb.dto.SubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.dto.VSubscriberMstr;
import com.ibm.virtualization.ussdactivationweb.exception.VCircleMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * 
 * @author Ashad This class would handle the Insert,Updation and Search for
 *         Subscriber.
 * 
 */
public class VSubscriberMstrDaoImpl implements VSubscriberMstrDao {
	private static final Logger logger = Logger
			.getLogger(VSubscriberMstrDaoImpl.class);

	/**
	 * 
	 * This method is being called from subscriberRegistration method of
	 * SubscriberAction to check the subscriber Availability for registration .
	 * 
	 * @param checkSubscriber
	 *            DTO containing the Subscriber details
	 * @return list of subscriber
	 * @throws DAOException
	 *             in case of any database exception
	 */
	public List checkAvailability(VSubscriberMstr checkSubscriber)
			throws DAOException {
		logger
				.info("Entering checkAvailability()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		String strMsisdn = checkSubscriber.getMsisdn();
		/** SIM changed from 7 digits to 5 digits after UAT **/
		String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
		String lastFiveDidits = checkSubscriber.getCompSim().substring(Integer.parseInt(subStringIndex));
		List subscriberlist = new ArrayList();
	
		try {

			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(CHECK_SUB_BY_MSISDN_SIM);

			prepareStatement.setString(1, strMsisdn);
			prepareStatement.setString(2, lastFiveDidits);
			prepareStatement.setString(3, Constants.ActiveState);
			prepareStatement.setString(4, checkSubscriber.getCircleCode());
			
			/*prepareStatement.setString(3, strMsisdn);
			prepareStatement.setString(4, lastFiveDidits);
			prepareStatement.setString(5, Constants.ActiveState);*/
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				VSubscriberMstr subscriberDTO = new VSubscriberMstr();
				subscriberDTO.setMsisdn(resultSet.getString(Constants.MSISDN));
				subscriberDTO.setImsi(resultSet.getString(Constants.IMSI));
				subscriberDTO.setSim(resultSet
						.getString(Constants.COMPLETE_SIM));
				String strStatus = resultSet.getString(Constants.STATUS);
				if (Constants.ActiveState.equalsIgnoreCase(strStatus)) {
					subscriberDTO.setStatus(Constants.strActive);
				} else {
					subscriberDTO.setStatus(Constants.strInAcive);
				}
				// subscriberDTO.setStatus(resultSet.getString(STATUS));
				subscriberDTO.setCircleCode(resultSet
						.getString(Constants.CIRCLE_CODE));
				subscriberDTO.setId(resultSet.getString(Constants.SM_ID));
				subscriberDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				subscriberDTO.setServiceClassName(serviceClassDAOImpl
						.getServiceClassNameById(resultSet
								.getInt("SERVICECLASS_ID")));
				// subscriberDTO.setServiceClassName(resultSet.getString("SERVICECLASS_NAME"));
				subscriberlist.add(subscriberDTO);
			}
			

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method checkAvailability() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger
				.info("Exiting  checkAvailability()... method of VSubscriberMstrDAOImpl");
		return subscriberlist;
	}

	
	
	
	
	/**
	 * This method is being called from subscriberRegistration method of
	 * SubscriberAction for save the subscriber information in database
	 * 
	 * @param dto
	 *            containing the Subscriber details
	 * @return no of record inserted
	 * @throws DAOException
	 *             in case of any database exception
	 */

	public boolean insert(VSubscriberMstr subsDTO) throws DAOException {
		logger.info("Entering insert()... method of VSubscriberMstrDAOImpl");
		CallableStatement callStatement = null;
		String procedureStatus = null;
		String procedureMsg = null;
		Connection connection = null;;
		boolean resultFlag = false;
			try {
				/** SIM changed from 7 digits to 5 digits after UAT **/
				String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				String lastFiveDidits = subsDTO.getCompSim().substring(Integer.parseInt(subStringIndex));
				
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				callStatement = connection.prepareCall(VSubscriberMstrDao.PROC_CREATE_SUBSCRIBER);
				callStatement.setString(1, SQLConstants.FTA_SCHEMA);
				callStatement.setString(2, subsDTO.getMsisdn());
				callStatement.setString(3, subsDTO.getImsi());
				callStatement.setString(4, subsDTO.getCompSim());
				callStatement.setString(5, lastFiveDidits);
				callStatement.setString(6, subsDTO.getCircleCode());
				callStatement.setInt(7, subsDTO.getServiceClassId());
				callStatement.setString(8, null);
				callStatement.setInt(9, Integer.parseInt(subsDTO.getUpdatedBy()));
				callStatement.setString(10, Constants.INSTALLED);
				callStatement.registerOutParameter(11, Types.VARCHAR);
				callStatement.registerOutParameter(12, Types.VARCHAR);
				callStatement.execute();
				procedureStatus = callStatement.getString(11);
				procedureMsg = callStatement.getString(12);
				if(!(Constants.PROC_STATUC_11111).equalsIgnoreCase(procedureStatus)) {
					logger.debug("Procedure failed reason code - " +procedureStatus +",Message =" + procedureMsg );
					throw new DAOException("Status =" + procedureStatus + ", Message=" +procedureMsg);
				} else {
					
					resultFlag = true;
				}
				
			logger
					.debug("Record successfully inserted on table:V_SUBSCRIBER_MSTR.");
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.debug("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception");
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, callStatement);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
		}
		logger.info("Exiting insert()... method of VSubscriberMstrDAOImpl");
		
	return resultFlag;
	}
	
	
	
	/**
	 * Being Used in searchSubscriber method of SubscriberAction to search the
	 * existing subscriber by their MSISDN
	 * 
	 * @param strMsisdn :
	 *            String
	 * @param strCircleId :
	 *            String
	 * @return object of List
	 * @throws DAOException
	 *             in case of any database exception This method is to fetch
	 *             data for the subscriber.
	 */

	public List findSubscriberList(String strMsisdn, String circleCode)
			throws DAOException {
		logger
				.info("Entering findSubscriberList()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		List subscriberList = new ArrayList();
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		try {

			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			StringBuffer findSubscriber = new StringBuffer(
					RETRIVE_SUB_BY_MSISDN);
			if (circleCode == null) {
				prepareStatement = connection.prepareStatement(findSubscriber
						.append(" ORDER BY CREATED_DT DESC WITH UR ").toString());
				prepareStatement.setString(1, strMsisdn);
			} else {
				prepareStatement = connection.prepareStatement(findSubscriber
						.append(" AND CIRCLE_CODE=? ORDER BY CREATED_DT DESC WITH UR ").toString());
				prepareStatement.setString(1, strMsisdn);
				prepareStatement.setString(2, circleCode);
			}

			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				VSubscriberMstr subscriberDTO = new VSubscriberMstr();
				subscriberDTO.setCircleCode(resultSet
						.getString(Constants.CIRCLE_CODE));
				subscriberDTO.setImsi(resultSet.getString(Constants.IMSI));
				subscriberDTO.setMsisdn(resultSet.getString(Constants.MSISDN));
				subscriberDTO.setSim(resultSet
						.getString(Constants.COMPLETE_SIM));
				String strStatus = resultSet.getString(Constants.STATUS);
				if (Constants.ActiveState.equalsIgnoreCase(strStatus)) {
					subscriberDTO.setStatus(Constants.strActive);
				} else {
					subscriberDTO.setStatus(Constants.strInAcive);
				}
				subscriberDTO
						.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));
				subscriberDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				subscriberDTO
						.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));
				subscriberDTO.setUpdatedBy(resultSet.getString("UPDATED_BY"));
				subscriberDTO.setId(resultSet.getString(Constants.SM_ID));
				subscriberDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));

				/*
				 * if(!SCIdList.contains(resultSet.getString("SERVICECLASS_ID"))){
				 * SCIdList.add(resultSet.getString("SERVICECLASS_ID")); }
				 */
				subscriberDTO.setServiceClassName(serviceClassDAOImpl
						.getServiceClassNameById(resultSet
								.getInt("SERVICECLASS_ID")));
				//extracting circle Name
				CircleMasterDTO circleMasterDTO = new CircleMasterDTO();
				ViewEditCircleMasterDAOImpl circleDao = new ViewEditCircleMasterDAOImpl();
				circleMasterDTO=circleDao.findByPrimaryKey(subscriberDTO.getCircleCode());
				String concatinatedCirclename =subscriberDTO.getCircleCode()+ " -- " +circleMasterDTO.getCircleName();
				subscriberDTO.setCircleName(concatinatedCirclename);
				
				subscriberList.add(subscriberDTO);

			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method findSubscriberList() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger
				.info("Exiting findSubscriberList()... method of VSubscriberMstrDAOImpl");
		return subscriberList;

	}

	/**
	 * This method is called from initSubscribeUpdate method of SubscriberAction
	 * for searching a subscriber for a particlar (MSISDN,IMSI,SIM) combination
	 * 
	 * @param strMsisdn
	 *            msisdn
	 * @param strSim
	 *            sim
	 * @return DTO containing Subscriber details
	 * @throws DAOException
	 *             in case of any database exception
	 */
	public VSubscriberMstr findByPrimaryKey(String smId)
			throws DAOException {
		logger
				.info("Entering findByPrimaryKey()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		VSubscriberMstr subscriberDTO = new VSubscriberMstr();
		/** SIM changed from 7 digits to 5 digits after UAT **/
		try {
			String sql = SQL_SELECT
					+ " WHERE SM_ID = ?  WITH UR";

			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, smId);
			resultSet = prepareStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				subscriberDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				subscriberDTO.setImsi(resultSet.getString("IMSI"));
				subscriberDTO.setMsisdn(resultSet.getString("MSISDN"));
				subscriberDTO.setSim(resultSet.getString("COMPLETE_SIM"));
				subscriberDTO.setStatus(resultSet.getString("STATUS"));
				subscriberDTO
						.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));
				subscriberDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				subscriberDTO
						.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));
				subscriberDTO.setUpdatedBy(resultSet.getString("UPDATED_BY"));
				subscriberDTO.setId(String.valueOf(resultSet.getInt("SM_ID")));
				subscriberDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("SQLException while finding sim detail: ", e);
		} catch (Exception e) {
			throw new DAOException("Exception while finding sim detail:", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				logger.error(Constants.EXCEPTION);
				new DAOException(
						"Exception occur while closing database resources in method findByPrimaryKey() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger
				.debug("Exiting findByPrimaryKey()... method of VSubscriberMstrDAOImpl");
		return subscriberDTO;
	}

	/**
	 * Used in upadateSubscriber method
	 * 
	 * Method by ravi method is check the existency of sim info
	 * 
	 * @param checkSubscriber
	 *            dto having subscriber details
	 * @return true/false
	 * @throws DAOException
	 *             in case of any database exception
	 */
	public boolean isCheckPass(VSubscriberMstr checkSubscriber)
			throws DAOException {
		logger
				.info("Entering isCheckPass()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement checkPsmt = null;
		ResultSet checkResult = null;
		boolean isCheckPass = true;
		try {
			String strSQL = null;
			/*
			 * if (checkSubscriber.getStatus().equalsIgnoreCase("A")) { strSQL =
			 * SQL_SELECT + " WHERE (( MSISDN = ? or IMSI=? or COMPLETE_SIM=? ) " + "
			 * and status='A') AND SM_ID<>? or " + " (MSISDN = ? and IMSI=? and
			 * COMPLETE_SIM=? and status='D' and SM_ID<>?)"; } else { strSQL =
			 * SQL_SELECT + " WHERE ( MSISDN = ? and IMSI=? and COMPLETE_SIM=? " + "
			 * AND SM_ID<>?)"; }
			 */
			if (Constants.ActiveState.equalsIgnoreCase(checkSubscriber
					.getStatus())) {
				strSQL = new StringBuffer(SQL_SELECT)
				        .append(" WHERE (( MSISDN = ? OR SIM=? ) ")
						.append(" AND status=?) AND SM_ID<>? OR ")
						.append(" (MSISDN = ? AND SIM=? and status=? AND SM_ID<>?)").toString();
			} else {
				strSQL = new StringBuffer(SQL_SELECT)
				.append(" WHERE ( MSISDN = ? AND SIM=?  AND SM_ID<>? )").toString();
			}
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			checkPsmt = connection.prepareStatement(strSQL);
			/** SIM changed from 7 digits to 5 digits after UAT **/
			String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
			String lastFiveDidits = checkSubscriber.getCompSim().substring(Integer.parseInt(subStringIndex));
			if (Constants.ActiveState.equalsIgnoreCase(checkSubscriber
					.getStatus())) {
				checkPsmt.setString(1, checkSubscriber.getMsisdn());
				checkPsmt.setString(2, lastFiveDidits);
				checkPsmt.setString(3, Constants.ActiveState);
				checkPsmt.setInt(4, Integer.parseInt(checkSubscriber.getId()));
				checkPsmt.setString(5, checkSubscriber.getMsisdn());
				checkPsmt.setString(6, lastFiveDidits);
				checkPsmt.setString(7, Constants.InActive);
				checkPsmt.setInt(8, Integer.parseInt(checkSubscriber.getId()));

			} else {
				checkPsmt.setString(1, checkSubscriber.getMsisdn());
				checkPsmt.setString(2, lastFiveDidits);
				checkPsmt.setInt(3, Integer.parseInt(checkSubscriber.getId()));
			}
			checkResult = checkPsmt.executeQuery();
			isCheckPass = checkResult.next();
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, checkPsmt,
						checkResult);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method isCheckPass() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger
				.info("Exiting isCheckPass()... method of VSubscriberMstrDAOImpl");
		return isCheckPass;
	}

	/**
	 * This method used in updateSubscriber method of SubscriberAction for
	 * deleting subscriber from current circle if subscriber existing circle
	 * changed
	 * 
	 * This method is to delete the Mapping for subscriber Search using MSISDN
	 * 
	 * @param msisdn
	 * @param msisdn
	 * @param sim
	 * @return no of record deleted
	 * @throws DAOException
	 *             in case of any database exception
	 */

	public int delete(String msisdn, String sim) throws DAOException {
		logger.info("Entering delete()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int numRows = -1;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(VSubscriberMstrDao.SQL_DELETE);
			prepareStatement.setString(1, msisdn);
			prepareStatement.setString(2, sim);
			numRows = prepareStatement.executeUpdate();
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method delete() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger.info("Exiting delete()... method of VSubscriberMstrDAOImpl");
		return numRows;
	}

	/**
	 * Being called from updateSubscriber method of SubscriberAction This method
	 * is Updating the subscriber status as inactive in database.
	 * 
	 * @param dto
	 *            having subscriber details to update
	 * @return greater than 1 in case of successful update or 0 in case of
	 *         failure
	 * @throws DAOException
	 *             in case of any database exception
	 * 
	 */
	public int updateSubscriberStatus(VSubscriberMstr dto) throws DAOException {
		logger
				.info("Entering updateSubscriberStatus()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int numRows = -1;
		try {
			/** SIM changed from 7 digits to 5 digits after UAT **/
			String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);			
			String lastFiveDidits = dto.getCompSim().substring(Integer.parseInt(subStringIndex));
		
			
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			connection.setAutoCommit(false);
			prepareStatement = connection
					.prepareStatement(VSubscriberMstrDao.SQL_UPDATE_ON_EDIT);
			prepareStatement.setString(1, dto.getImsi());
			prepareStatement.setString(2, dto.getStatus());
			prepareStatement.setString(3, dto.getUpdatedBy());
			prepareStatement.setString(4, lastFiveDidits);
			prepareStatement.setString(5, dto.getCircleCode());
			prepareStatement.setString(6, dto.getCompSim());
			prepareStatement.setInt(7, dto.getServiceClassId());
			prepareStatement.setString(8, dto.getId());
			numRows = prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException sqe) {
			try {
				connection.rollback();
			} catch (SQLException se) {
				logger.error("SQL EXCEPTION" + se.getMessage(), se);
			}
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			try {
				connection.rollback();
			} catch (SQLException se) {
				logger.error("SQL EXCEPTION" + se.getMessage(), se);
			}
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method updateOnEdit() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger
				.info("Exiting updateSubscriberStatus()... method of VSubscriberMstrDAOImpl");
		return numRows;
	}

	/**
	 * Being called from updateSubscriber method of SubscriberAction This method
	 * is Updating the subscriber details in database.
	 * 
	 * @param dto
	 *            having subscriber details to update
	 * @return greater than true in case of successful update or false in case of
	 *         failure
	 * @throws DAOException
	 *             in case of any database exception
	 * 
	 */
	
	public boolean updateSubscriber(VSubscriberMstr subsDTO) throws DAOException {
		logger
				.info("Entering updateSubscriber()... method of VSubscriberMstrDAOImpl");
		CallableStatement callStatement = null;
		String procedureStatus = null;
		String procedureMsg = null;
		Connection connection = null;
		boolean resultFlag = false;
			try {
				/** SIM changed from 7 digits to 5 digits after UAT **/
				String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				String lastFiveDidits = subsDTO.getCompSim().substring(Integer.parseInt(subStringIndex));
				
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				callStatement = connection.prepareCall(VSubscriberMstrDao.PROC_CREATE_SUBSCRIBER);
				callStatement.setString(1, SQLConstants.FTA_SCHEMA);
				callStatement.setString(2, subsDTO.getMsisdn());
				callStatement.setString(3, subsDTO.getImsi());
				callStatement.setString(4, subsDTO.getCompSim());
				callStatement.setString(5, lastFiveDidits);
				callStatement.setString(6, subsDTO.getCircleCode());
				callStatement.setInt(7, subsDTO.getServiceClassId());
				callStatement.setString(8, null);
				callStatement.setInt(9, Integer.parseInt(subsDTO.getUpdatedBy()));
				callStatement.setString(10, Constants.REPROVISIONED);
				callStatement.registerOutParameter(11, Types.VARCHAR);
				callStatement.registerOutParameter(12, Types.VARCHAR);
				callStatement.execute();
				procedureStatus = callStatement.getString(10);
				procedureMsg = callStatement.getString(11);
				if(!(Constants.PROC_STATUC_11111).equalsIgnoreCase(procedureStatus)) {
					logger.debug("Procedure failed reason code - " +procedureStatus +",Message =" + procedureMsg );
					throw new DAOException("Status =" + procedureStatus + ", Message=" +procedureMsg);
				} else {
					
					resultFlag = true;
				}
				
			logger
					.debug("Record successfull inserted on table:V_SUBSCRIBER_MSTR.");
		} catch (SQLException sqe) {
			logger.debug("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.debug("Exception");
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, callStatement);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			}
		
		logger
				.info("Exiting updateSubscriber()... method of VSubscriberMstrDAOImpl");
		return resultFlag;
	}
	/**
	 * This method subscriberDetails() tracks for the subsciber associated with
	 * the MSISDn provided.
	 * 
	 * @param circleCode
	 *            :this is to limit the particular CircleAdmin to look for the
	 *            subsciber in his own circle.
	 * @param smsisdn
	 *            :to fetch the details of associated subscriber
	 * @return an ArrayList:subscriberDetails that contails the detail of
	 *         subscriber
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	//
	// public ArrayList subscriberDetails(String circleCode,String smsisdn) {
	// logger.debug("Entering subscriberDetails()... method of
	// VSubscriberMstrDAOImpl");
	// SubscriberDTO Dto = null;
	// Connection connection = null;
	// PreparedStatement prepareStatement = null;
	// ArrayList subscriberDetails = new ArrayList();
	// ResultSet resultSet = null;
	// try {
	// connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
	// //String query = VSubscriberMstrDao.TRACK_SUBSCRIBER;
	// if (circleCode == null) {
	// String query = VSubscriberMstrDao.TRACK_SUBSCRIBER
	// + " AND sm.MSISDN=? ";
	// prepareStatement = connection.prepareStatement(query);
	// prepareStatement.setInt(1, Constants.Registration);
	// prepareStatement.setString(2, Constants.InActiveTrans);
	// prepareStatement.setInt(3, Constants.Registration);
	// prepareStatement.setString(4, Constants.ActiveTran);
	// prepareStatement.setInt(5, Constants.Activation);
	// prepareStatement.setString(6, Constants.InActiveTrans);
	// prepareStatement.setInt(7, Constants.Activation);
	// prepareStatement.setString(8, Constants.ActiveTran);
	// prepareStatement.setInt(9, Constants.Verification);
	// prepareStatement.setString(10, Constants.InActiveTrans);
	// prepareStatement.setInt(11, Constants.Verification);
	// prepareStatement.setString(12, Constants.ActiveTran);
	// prepareStatement.setString(13, smsisdn);
	// } else {
	// String query = VSubscriberMstrDao.TRACK_SUBSCRIBER
	// + " AND sm.CIRCLE_CODE=? AND sm.MSISDN=? ";
	// prepareStatement = connection.prepareStatement(query);
	// prepareStatement.setInt(1, Constants.Registration);
	// prepareStatement.setString(2, Constants.InActiveTrans);
	// prepareStatement.setInt(3, Constants.Registration);
	// prepareStatement.setString(4, Constants.ActiveTran);
	// prepareStatement.setInt(5, Constants.Activation);
	// prepareStatement.setString(6, Constants.InActiveTrans);
	// prepareStatement.setInt(7, Constants.Activation);
	// prepareStatement.setString(8, Constants.ActiveTran);
	// prepareStatement.setInt(9, Constants.Verification);
	// prepareStatement.setString(10, Constants.InActiveTrans);
	// prepareStatement.setInt(11, Constants.Verification);
	// prepareStatement.setString(12, Constants.ActiveTran);
	// prepareStatement.setString(13, circleCode);
	// prepareStatement.setString(14, smsisdn);
	// }
	// resultSet = prepareStatement.executeQuery();
	// while (resultSet.next()) {
	// Dto = new SubscriberDTO();
	// Dto.setCircle(resultSet.getString("CIRCLE"));
	// Dto.setSmsisdn(resultSet.getString("MSISDN"));
	// Dto.setSimsi(resultSet.getString("IMSI"));
	// Dto.setSsim(resultSet.getString("SIM"));
	// Dto.setServiceClass(resultSet.getString("SERVICECLASS_NAME"));
	// Dto.setTranTypeId(resultSet.getInt("TRAN_TYPE_ID"));
	// Dto.setCreatedDt(resultSet.getString("CREATED_DT"));
	// Dto.setTranTypeName(resultSet.getString("TRAN_TYPE_NAME"));
	// Dto.setStatus(resultSet.getString("SUBSCRIBERSTATUS"));
	// Dto.setRequesterDeatils(getBusinessUserName(resultSet.getString("REQUESTER_MSISDN")));
	// Iterator itr = (Dto.getRequesterDeatils()).iterator();
	// while (itr.hasNext()) {
	// SubscriberDTO subscriberDTO = (SubscriberDTO) itr.next();
	// Dto.setMobileNo(subscriberDTO.getMobileNo());
	// Dto.setUserName(subscriberDTO.getUserName());
	// Dto.setUserType(subscriberDTO.getUserType());
	// }
	// subscriberDetails.add(Dto);
	// }
	//			
	// } catch (DAOException e) {
	// logger.error("DAOException encountered: " + e.getMessage(), e);
	// new DAOException("Exception occur while getting user.", e);
	// } catch (SQLException sqlEx) {
	//			
	// logger.error("SQL exception encountered: " + sqlEx.getMessage(),
	// sqlEx);
	// new DAOException("Exception occur while getting user.", sqlEx);
	// } finally {
	// try {
	// DBConnectionUtil.closeDBResources(connection, prepareStatement,
	// resultSet);
	// } catch (SQLException e) {
	// new DAOException(
	// "Exception occur while closing database resources in method
	// subscriberDetails() of class SearchBusinessUserDAO.",
	// e);
	// }
	// }
	// logger.debug("Exiting subscriberDetails()... method of
	// VSubscriberMstrDAOImpl");
	// return subscriberDetails;
	// }
	//	
	public ArrayList subscriberDetails(String circleCode, String smsisdn,
			int intLb, int intUb) throws DAOException {
		logger
				.info("Entering subscriberDetails()... method of VSubscriberMstrDAOImpl");
		SubscriberDTO Dto = null;
		Connection connection = null;
		Connection connection1 = null;
	    PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement1 = null;
		ArrayList subscriberDetails = new ArrayList();
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		try {

			if (circleCode == null) {
				connection = DBConnection
						.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				String query = VSubscriberMstrDao.GET_MSISDN_DETAILS
						+ " AND sm.MSISDN=? ORDER BY (tm.UPDATED_DT) desc) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setInt(1, Constants.Registration);
				prepareStatement.setString(2, Constants.InActiveTrans);
				prepareStatement.setInt(3, Constants.Registration);
				prepareStatement.setString(4, Constants.ActiveTran);
				prepareStatement.setInt(5, Constants.Activation);
				prepareStatement.setString(6, Constants.InActiveTrans);
				prepareStatement.setInt(7, Constants.Activation);
				prepareStatement.setString(8, Constants.ActiveTran);
				prepareStatement.setInt(9, Constants.Verification);
				prepareStatement.setString(10, Constants.InActiveTrans);
				prepareStatement.setInt(11, Constants.Verification);
				prepareStatement.setString(12, Constants.ActiveTran);
				prepareStatement.setInt(13, Constants.Registration);
				prepareStatement.setString(14, Constants.InFailedTrans);
				prepareStatement.setInt(15, Constants.Activation);
				prepareStatement.setString(16, Constants.InFailedTrans);
				prepareStatement.setInt(17, Constants.Verification);
				prepareStatement.setString(18, Constants.InFailedTrans);
				prepareStatement.setString(19, Constants.ActiveState);
				prepareStatement.setString(20, smsisdn);
				prepareStatement.setString(21, String.valueOf(intUb));
				prepareStatement.setString(22, String.valueOf(intLb + 1));

			} else {
				connection = DBConnection
						.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				String query = VSubscriberMstrDao.GET_MSISDN_DETAILS
						+ " AND sm.CIRCLE_CODE=? AND sm.MSISDN=? ORDER BY (tm.UPDATED_DT) desc ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setInt(1, Constants.Registration);
				prepareStatement.setString(2, Constants.InActiveTrans);
				prepareStatement.setInt(3, Constants.Registration);
				prepareStatement.setString(4, Constants.ActiveTran);
				prepareStatement.setInt(5, Constants.Activation);
				prepareStatement.setString(6, Constants.InActiveTrans);
				prepareStatement.setInt(7, Constants.Activation);
				prepareStatement.setString(8, Constants.ActiveTran);
				prepareStatement.setInt(9, Constants.Verification);
				prepareStatement.setString(10, Constants.InActiveTrans);
				prepareStatement.setInt(11, Constants.Verification);
				prepareStatement.setString(12, Constants.ActiveTran);
				prepareStatement.setInt(13, Constants.Registration);
				prepareStatement.setString(14, Constants.InFailedTrans);
				prepareStatement.setInt(15, Constants.Activation);
				prepareStatement.setString(16, Constants.InFailedTrans);
				prepareStatement.setInt(17, Constants.Verification);
				prepareStatement.setString(18, Constants.InFailedTrans);
				prepareStatement.setString(19, Constants.ActiveState);
				prepareStatement.setString(20, circleCode);
				prepareStatement.setString(21, smsisdn);
				prepareStatement.setString(22, String.valueOf(intUb));
				prepareStatement.setString(23, String.valueOf(intLb + 1));

			}
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Dto = new SubscriberDTO();
				Dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				Dto.setSmsisdn(resultSet.getString("MSISDN"));
				Dto.setSimsi(resultSet.getString("IMSI"));
				String simNo = resultSet.getString("SUBS_SIM");
				simNo = (simNo == null || ("").equals(simNo)) ? resultSet.getString("SIM") : simNo;
				Dto.setSsim(simNo);				
				Dto.setServiceClassId(resultSet.getInt("SERVICECLASS_ID"));
				Dto.setTranTypeId(resultSet.getInt("TRAN_TYPE_ID"));
				Dto.setCreatedDt(resultSet.getString("CREATED_DT"));
				Dto.setTranTypeName(resultSet.getString("TRAN_TYPE_NAME"));
				Dto.setStatus(resultSet.getString("SUBSCRIBERSTATUS"));
				Dto.setRequesterDeatils(getBusinessUserName(resultSet
						.getString("REQUESTER_MSISDN"),resultSet.getString("REQUESTER_TYPE")));
				Dto.setRowNum(resultSet.getString("RNUM"));
				Iterator itr = (Dto.getRequesterDeatils()).iterator();
				while (itr.hasNext()) {
					SubscriberDTO subscriberDTO = (SubscriberDTO) itr.next();
					Dto.setMobileNo(subscriberDTO.getMobileNo());
					Dto.setUserName(subscriberDTO.getUserName());
					
					Dto.setUserType(resultSet
							.getString("REQUESTER_TYPE"));
					//Dto.setUserType(subscriberDTO.getUserType());
				}
				SubscriberDTO subDto = getCircleServiceClass(Dto);
				Dto.setCircle(subDto.getCircle());
				Dto.setServiceClass(subDto.getServiceClass());
				Dto.setDescription(resultSet.getString("OTHER_DETAILS"));
				if(resultSet.getString("SUBSCRIBER_NAME")!=null){
					Dto.setSubsName(resultSet.getString("SUBSCRIBER_NAME"));
				}else{
					Dto.setSubsName("");
				}
				
				subscriberDetails.add(Dto);
			}

		} catch (DAOException e) {
			logger.error("DAOException encountered: " + e.getMessage(), e);
			new DAOException("Exception occur while getting user.", e);
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			new DAOException("Exception occur while getting user.", sqlEx);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
				DBConnectionUtil.closeDBResources(connection1,
						prepareStatement1, resultSet1);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method subscriberDetails().",
						e);
			}
		}
		logger
				.info("Exiting subscriberDetails()... method of VSubscriberMstrDAOImpl");
		return subscriberDetails;
	}

	/**
	 * This method is called to count the details of subscribers based on
	 * particular msisdn
	 * 
	 * @return object of int:total number of details.
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public int countSubscriberDetails(String circleCode, String smsisdn)
			throws DAOException {
		logger
				.info("Entering method countSubscriberDetails() throws DaoException");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (circleCode == null) {
				String query = VSubscriberMstrDao.SQL_COUNT_SUBSCRIBER_DETAILS
						+ " AND sm.MSISDN=? ";
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setString(1, Constants.ActiveState);
				prepareStatement.setString(2, smsisdn);
				ResultSet countSubscriberDetails = prepareStatement
						.executeQuery();
				if (countSubscriberDetails.next()) {
					noofRow = countSubscriberDetails.getInt(1);
				}
				noofPages = PaginationUtils.getPaginationSize(noofRow);
			} else {
				String queryCircleCode = VSubscriberMstrDao.SQL_COUNT_SUBSCRIBER_DETAILS
						+ " AND sm.CIRCLE_CODE=? AND sm.CIRCLE_CODE=tm.CIRCLE_CODE AND sm.MSISDN=? ";
				prepareStatement = connection.prepareStatement(queryCircleCode);
				prepareStatement = connection.prepareStatement(queryCircleCode);
				prepareStatement.setString(1, Constants.ActiveState);
				prepareStatement.setString(2, circleCode);
				prepareStatement.setString(3, smsisdn);
				ResultSet countSubscriberDetails = prepareStatement
						.executeQuery();
				if (countSubscriberDetails.next()) {
					noofRow = countSubscriberDetails.getInt(1);
				}
				noofPages = PaginationUtils.getPaginationSize(noofRow);
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countSubscriberDetails.",
						e);
			}
		}
		logger
				.info("Exiting method countSubscriberDetails() throws DaoException");
		return noofPages;
	}

	/**
	 * This method is called to count the details of subscribers based on
	 * particular msisdn
	 * 
	 * @return object of int:total number of details.
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public ArrayList findSubscriber(String circleCode, String smsisdn)
			throws DAOException {
		logger
				.info("Entering method findSubscriberStatus() throws DaoException");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		SubscriberDTO subscriberDto = null;
		ArrayList subscribers = new ArrayList();
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (circleCode == null) {

				String query = VSubscriberMstrDao.SQL_STATUS_OF_MSISDN;
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setString(1, smsisdn);
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					subscriberDto = new SubscriberDTO();
					subscriberDto.setSmsisdn(resultSet.getString("MSISDN"));
					subscriberDto.setSubscriberStatus(resultSet
							.getString("STATUS"));
					subscribers.add(subscriberDto);
				} 
			} else {
				String query = VSubscriberMstrDao.SQL_STATUS_OF_MSISDN
						+ " AND sm.CIRCLE_CODE=? WITH UR ";
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setString(1, smsisdn);
				prepareStatement.setString(2, circleCode);
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					subscriberDto = new SubscriberDTO();
					subscriberDto.setSmsisdn(resultSet.getString("MSISDN"));
					subscriberDto.setSubscriberStatus(resultSet
							.getString("STATUS"));
					subscribers.add(subscriberDto);
				} 
			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method findSubscriberStatus() .",
						e);
			}
		}
		logger
				.info("Exiting method findSubscriberStatus() throws DaoException");
		return subscribers;
	}

	/**
	 * This method getBusinessUserName() searches for the register number and
	 * gives some detials about business user
	 * 
	 * @param regNo
	 * 
	 * @return an ArrayList:requesterDetails that contails the detail of
	 *         business user
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */

	public ArrayList getBusinessUserName(String regNo,String requesterType) {
		logger
				.info("Entering getBusinessUserName()... method of VSubscriberMstrDAOImpl");
		SubscriberDTO Dto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ArrayList requesterDetails = new ArrayList();
		ResultSet resultSet = null;
		
		if(requesterType !=null && !"".equals(requesterType)){
			requesterType = requesterType.toUpperCase();
		}
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			String query = BusinessUserInterface.BUSINESS_USER_DETAILS;
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, regNo);
			prepareStatement.setString(2, requesterType);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				Dto = new SubscriberDTO();
				Dto.setMobileNo(resultSet.getString("MOBILE_NO"));
				Dto.setUserName(resultSet.getString("USER_NAME"));
				Dto.setUserType(resultSet.getString("USER_TYPE"));
				requesterDetails.add(Dto);
			}
		} catch (DAOException e) {
			logger.error("DAOException encountered: " + e.getMessage(), e);
			new DAOException("Exception occur while getting user.", e);
		} catch (SQLException sqlEx) {

			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			new DAOException("Exception occur while getting user.", sqlEx);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method subscriberDetails() of class SearchBusinessUserDAO.",
						e);
			}
		}
		logger
				.info("Exiting getBusinessUserName()... method of VSubscriberMstrDAOImpl");
		return requesterDetails;
	}

	/**
	 * getting circle and service class for a subscriber
	 * 
	 * @param subscriberDto
	 *            having subscriber details
	 * @return dto having circle and service class
	 * @throws DAOException
	 *             in case of database exception.
	 */
	public SubscriberDTO getCircleServiceClass(SubscriberDTO subscriberDto)
			throws DAOException {
		logger.info("Entering delete()... method of VSubscriberMstrDAOImpl");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet1 = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(VSubscriberMstrDao.GET_CIRCLE_SERVICECLASS);
			prepareStatement.setInt(1, subscriberDto.getServiceClassId());
			prepareStatement.setString(2, subscriberDto.getCircleCode());
			resultSet1 = prepareStatement.executeQuery();
			if (resultSet1.next()) {
				subscriberDto.setCircle(resultSet1.getString("CIRCLE"));
				subscriberDto.setServiceClass(resultSet1
						.getString("SERVICECLASS_NAME"));
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method delete() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}
		logger.info("Exiting delete()... method of VSubscriberMstrDAOImpl");
		return subscriberDto;
	}
	
	
	
	public static List getPendingSubscriberReport(String circleCode,String createdDate,String currentDate)
	throws DAOException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet1 = null;
		List subsReportPendingforActivation = new ArrayList();
		try{
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
//			String newCreatedDate = new String(createdDate);
//			java.util.Date createddt = sdf.parse(newCreatedDate);
//			Timestamp createdDateTimestamp = new Timestamp(createddt.getTime());
//
//			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
//			String newCurrentDate = new String(currentDate);
//			java.util.Date currentdt1 = sdf1.parse(newCurrentDate);
//			Timestamp currentDateTimestamp = new Timestamp(currentdt1.getTime());
//			
			
			java.sql.Date newCreatedDate = Utility.getSqlDateFromString(createdDate, "dd-MM-yyyy-HH-mm-ss");
			java.sql.Date newCurrentDate = Utility.getSqlDateFromString(currentDate, "dd-MM-yyyy-HH-mm-ss");
			
			connection = DBConnection 
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
				.prepareStatement(VSubscriberMstrDao.GET_PENDING_SUBS_REPORT_PENDING);
			prepareStatement.setString(1, circleCode);
			prepareStatement.setString(2, Constants.RELEASED);
			prepareStatement.setString(3, Constants.INSTALLED);
			prepareStatement.setString(4, Constants.REPROVISIONED);
			prepareStatement.setString(5, Constants.ActiveState);
			prepareStatement.setDate(6, newCreatedDate);
			prepareStatement.setDate(7, newCurrentDate);
			
			resultSet1 = prepareStatement.executeQuery();
			
			//	 This First Row, Heading of the File.
			subsReportPendingforActivation.add(new String[] {
					"SUBSCRIBER MSISDN", "SUBSCRIBER SIM",
					" SUBSCRIBER SERVICECLASS", "CREATED DATE", "CREATED BY", "ACTION STATUS", "ACTION DATE" });
			
			while (resultSet1.next()) {
				subsReportPendingforActivation.add(new String[] {resultSet1.getString("MSISDN"),
				resultSet1.getString("SIM"),
				resultSet1.getString("SERVICECLASS_ID"),
				createdDate = USSDCommonUtility.getTimestampAsString(
						resultSet1.getTimestamp("CREATED_DT"), "dd-MM-yyyy-hh-mm-ss"),
				resultSet1.getString("LOGIN_ID"),
				resultSet1.getString("ACTION_TAKEN"),
				currentDate = USSDCommonUtility.getTimestampAsString(
						resultSet1.getTimestamp("ACTION_DATE"), "dd-MM-yyyy-hh-mm-ss")
				
				});
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method delete() of class VSubscriberMstrDaoImpl.",
						e);
			}
		}return subsReportPendingforActivation;
	}

}