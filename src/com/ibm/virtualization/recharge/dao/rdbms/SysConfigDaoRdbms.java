/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.SysConfigDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * SysConfigDao interface
 * 
 * @author Navroze
 * 
 * 
 */
public class SysConfigDaoRdbms extends BaseDaoRdbms implements SysConfigDao {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(SysConfigDaoRdbms.class
			.getName());

	/* SQL Used within DaoImpl */
	protected final static String SQL_SYSCONFIG_INSERT_KEY = "SQL_SYSCONFIG_INSERT";
	protected static String SQL_SYSCONFIG_INSERT = "INSERT INTO VR_SYSTEM_CONFIG (VR_SYSTEM_CONFIG_ID, CIRCLE_ID, TRANSACTION_TYPE, CHANNEL, MIN_AMOUNT, MAX_AMOUNT,CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT ) VALUES (SEQ_VR_SYSTEM_CONFIG.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	protected final static String SQL_SYSCONFIG_SELECT_ALL_KEY = "SQL_SYSCONFIG_SELECT_ALL";
	protected static String SQL_SYSCONFIG_SELECT_ALL = "SELECT SYSCONFIG.VR_SYSTEM_CONFIG_ID, SYSCONFIG.CIRCLE_ID , CIRCLE.CIRCLE_NAME, SYSCONFIG.TRANSACTION_TYPE, SYSCONFIG.CHANNEL , SYSCONFIG.MIN_AMOUNT, SYSCONFIG.MAX_AMOUNT, SYSCONFIG.CREATED_BY, SYSCONFIG.CREATED_DT, SYSCONFIG.UPDATED_BY, SYSCONFIG.UPDATED_DT FROM VR_SYSTEM_CONFIG SYSCONFIG, VR_CIRCLE_MASTER CIRCLE WHERE CIRCLE.STATUS='"
			+ Constants.ACTIVE_STATUS
			+ "' AND SYSCONFIG.CIRCLE_ID=CIRCLE.CIRCLE_ID ORDER BY SYSCONFIG.CIRCLE_ID , SYSCONFIG.CHANNEL , SYSCONFIG.TRANSACTION_TYPE ";

	protected final static String SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID_KEY = "SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID";
	protected static String SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID = "SELECT SYSCONFIG.VR_SYSTEM_CONFIG_ID, SYSCONFIG.CIRCLE_ID, CIRCLE.CIRCLE_NAME , SYSCONFIG.TRANSACTION_TYPE, SYSCONFIG.CHANNEL , SYSCONFIG.MIN_AMOUNT,SYSCONFIG.MAX_AMOUNT FROM VR_SYSTEM_CONFIG SYSCONFIG, VR_CIRCLE_MASTER CIRCLE WHERE SYSCONFIG.CIRCLE_ID = CIRCLE.CIRCLE_ID AND SYSCONFIG.CIRCLE_ID = ? ORDER BY CHANNEL , TRANSACTION_TYPE";

	protected final static String SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID_KEY = "SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID";
	protected static String SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID = "SELECT SYSCONFIG.VR_SYSTEM_CONFIG_ID, SYSCONFIG.CIRCLE_ID , CIRCLE.CIRCLE_NAME, SYSCONFIG.TRANSACTION_TYPE, SYSCONFIG.CHANNEL , SYSCONFIG.MIN_AMOUNT, SYSCONFIG.MAX_AMOUNT , SYSCONFIG.CREATED_BY, SYSCONFIG.CREATED_DT, SYSCONFIG.UPDATED_BY, SYSCONFIG.UPDATED_DT FROM VR_SYSTEM_CONFIG SYSCONFIG, VR_CIRCLE_MASTER CIRCLE WHERE SYSCONFIG.CIRCLE_ID = CIRCLE.CIRCLE_ID AND SYSCONFIG.VR_SYSTEM_CONFIG_ID = ? ";

	protected final static String SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID_KEY = "SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID";
	protected static String SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID = "SELECT SYSCONFIG.TRANSACTION_TYPE, SYSCONFIG.CHANNEL , SYSCONFIG.MIN_AMOUNT, SYSCONFIG.MAX_AMOUNT FROM VR_SYSTEM_CONFIG SYSCONFIG WHERE SYSCONFIG.CIRCLE_ID  = ? AND SYSCONFIG.TRANSACTION_TYPE=? AND SYSCONFIG.CHANNEL=?";

	protected final static String SQL_SYSCONFIG_UPDATE_KEY = "SQL_SYSCONFIG_UPDATE";
	protected static String SQL_SYSCONFIG_UPDATE = "UPDATE VR_SYSTEM_CONFIG SET CIRCLE_ID = ? , TRANSACTION_TYPE = ? , CHANNEL = ? , MIN_AMOUNT = ? , MAX_AMOUNT = ? , UPDATED_DT = ?, UPDATED_BY = ?   WHERE VR_SYSTEM_CONFIG_ID = ? ";

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public SysConfigDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_SYSCONFIG_INSERT_KEY,SQL_SYSCONFIG_INSERT);
		queryMap.put(SQL_SYSCONFIG_SELECT_ALL_KEY ,SQL_SYSCONFIG_SELECT_ALL);
		queryMap.put(SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID_KEY ,SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID);
		queryMap.put(SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID_KEY,SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID);
		queryMap.put(SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID_KEY ,SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID);
		queryMap.put(SQL_SYSCONFIG_UPDATE_KEY ,SQL_SYSCONFIG_UPDATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SysConfigDao#insertSystemConfigurationDetail(com.ibm.virtualization.recharge.dto.SysConfig)
	 */
	public void insertSystemConfigurationDetail(SysConfig sysConfig)
			throws DAOException {

		logger.info("Started...");
		PreparedStatement ps = null;
		int rowsUpdated = 0;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SYSCONFIG_INSERT_KEY));
			int paramCount = 1;

			/*
			 * SystemConfig ID is getting generated with Sequence
			 * SEQ_VR_SYSTEM_CONFIG
			 */
			ps.setInt(paramCount++, sysConfig.getCircleId());
			ps.setString(paramCount++, sysConfig.getTransactionType());
			ps.setString(paramCount++, sysConfig.getChannelName());

			ps.setDouble(paramCount++, sysConfig.getMinAmount());
			ps.setDouble(paramCount++, sysConfig.getMaxAmount());
			ps.setLong(paramCount++, sysConfig.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, sysConfig.getUpdatedBy());
			ps.setTimestamp(paramCount, Utility.getDateTime());
			rowsUpdated = ps.executeUpdate();
			logger
					.info(" Row insertion successful on table:VR_SYSTEM_CONFIG. Inserted:"
							+ rowsUpdated + " rows");
			logger.info("Executed ....");
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			// TODO Paras Sep 04,2007 Need to see if a separate check is
			// required for unique constraints.
			throw new DAOException(
					ExceptionCode.SystemConfig.ERROR_INSERT_SYS_CONFIG_ALEARDY_EXIST);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SysConfigDao#getSystemConfigurationList(int)
	 */
	public ArrayList getSystemConfigurationList(int circleId)
			throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<SysConfig> sysConfigList = new ArrayList<SysConfig>();
		try {

			if (circleId != 0) {
				ps = connection
						.prepareStatement(queryMap.get(SQL_SYSCONFIG_SELECT_ON_CIRCLE_ID_KEY));
				ps.setInt(1, circleId);
			} else {
				ps = connection.prepareStatement(queryMap.get(SQL_SYSCONFIG_SELECT_ALL_KEY));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				sysConfigList.add(populateDto(rs));
			}
			logger.info("Executed ....");
			return sysConfigList;
		} catch (SQLException e) {
			logger.error(" SQL Exception occured while selection of data."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return sysConfig
	 * @throws SQLException,
	 *             DAOException
	 */
	private SysConfig populateDto(ResultSet rs) throws SQLException,
			DAOException {
		logger.info("Started... ");
		SysConfig sysConfig = new SysConfig();
		sysConfig.setSysConfigId(rs.getLong("VR_SYSTEM_CONFIG_ID"));
		sysConfig.setCircleId(rs.getInt("CIRCLE_ID"));
		sysConfig.setCircleName(rs.getString("CIRCLE_NAME"));
		sysConfig.setChannelName(rs.getString("CHANNEL"));

		sysConfig.setMinAmount(rs.getDouble("MIN_AMOUNT"));
		sysConfig.setMaxAmount(rs.getDouble("MAX_AMOUNT"));
		sysConfig.setTransactionType(rs.getString("TRANSACTION_TYPE"));
		logger.info("Executed ....");
		return sysConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SysConfigDao#getSystemConfigurationDetails(long)
	 */
	public SysConfig getSystemConfigurationDetails(long sysConfigId)
			throws DAOException {
		logger.info("Started...sysConfigId:" + sysConfigId);
		logger.info(" table VR_SYSTEM_CONFIG");

		PreparedStatement ps = null;
		ResultSet rs = null;
		SysConfig sysConfig = null;
		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_SYSCONFIG_SELECT_ON_VR_SYSTEM_CONFIG_ID_KEY));
			ps.setLong(1, sysConfigId);
			rs = ps.executeQuery();
			if (rs.next()) {
				sysConfig = populateDto(rs);
			}
			logger.info("Executed ....");
			return sysConfig;

		} catch (SQLException e) {
			logger.error("SQL Exception occured while selection of data."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_FETCH);

		} finally {

			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SysConfigDao#getSystemConfigurationDetails(long,
	 *      java.lang.String, java.lang.String)
	 */
	public SysConfig getSystemConfigurationDetails(int circleId,
			TransactionType transactionType, ChannelType channelName)
			throws DAOException {
		logger
				.info("Started...circleId:" + circleId + ":transactionType:"
						+ transactionType.name() + ":channelName:"
						+ channelName.name());

		PreparedStatement ps = null;
		ResultSet rs = null;
		SysConfig sysConfig = null;
		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_SYSCONFIG_SELECT_ON_SYSTEM_CIRCLE_ID_KEY));
			ps.setInt(1, circleId);
			ps.setString(2, transactionType.name());
			ps.setString(3, channelName.name());
			rs = ps.executeQuery();
			if (rs.next()) {
				sysConfig = new SysConfig();

				sysConfig.setChannelName(rs.getString("CHANNEL"));
				sysConfig.setMinAmount(rs.getDouble("MIN_AMOUNT"));
				sysConfig.setMaxAmount(rs.getDouble("MAX_AMOUNT"));
				sysConfig.setTransactionType(rs.getString("TRANSACTION_TYPE"));
			}
			logger.info("Executed ....");
			return sysConfig;

		} catch (SQLException e) {

			logger.error("SQL Exception occured while selection of data."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_FETCH);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.SysConfigDao#updateSystemConfiguration(com.ibm.virtualization.recharge.dto.SysConfig)
	 */
	public void updateSystemConfiguration(SysConfig sysConfig)
			throws DAOException {

		logger.info("Started...");
		PreparedStatement ps = null;
		int numRows = -1;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SYSCONFIG_UPDATE_KEY));
			int paramCount = 1;
			ps.setInt(paramCount++, sysConfig.getCircleId());
			ps.setString(paramCount++, sysConfig.getTransactionType());
			ps.setString(paramCount++, sysConfig.getChannelName());
			ps.setDouble(paramCount++, sysConfig.getMinAmount());
			ps.setDouble(paramCount++, sysConfig.getMaxAmount());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, sysConfig.getUpdatedBy());
			ps.setLong(paramCount, sysConfig.getSysConfigId());

			numRows = ps.executeUpdate();
			logger
					.info("Executed ...pdate successful on table:SYSCONFIG_DETAILS. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_ALREDY_DEFINED);
		} finally {

			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
	}

}
