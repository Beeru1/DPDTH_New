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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for the methods used for DB2.
 * 
 * @author Mohit Aggarwal
 */

public class CircleTopupConfigDaoRdbmsDB2 extends CircleTopupConfigDaoRdbms {
	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger
			.getLogger(CircleTopupConfigDaoRdbmsDB2.class.getName());
    
	protected final static String SQL_TOPUP_INSERT = "INSERT INTO VR_CIRCLE_TOPUP_CONFIG (CIRCLE_ID, TRANSACTION_TYPE, "
			+ "START_AMOUNT, TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, "
			+ "IN_CARD_GROUP, PROCESSING_CODE, VALIDITY, CREATED_DT, CREATED_BY,"
			+ " TOPUP_CONFIG_ID , STATUS ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NEXT VALUE FOR SEQ_VR_TOPUP_CONFIG, ?)";

	protected final static String SQL_TOPUP_INSERT_FAILED = "INSERT INTO VR_CIRCLE_TOPUP_CONFIG_FAILED (CIRCLE_ID, TRANSACTION_TYPE, "
			+ "START_AMOUNT, TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, "
			+ "IN_CARD_GROUP, PROCESSING_CODE, VALIDITY, CREATED_DT, CREATED_BY,"
			+ " TOPUP_ID,FILE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NEXT VALUE FOR SEQ_VR_TOPUP_CONFIG_FAILED,?)";
	
	
	protected final static String SQL_TOPUP_SELECT_FILE_ID = "SELECT NEXT VALUE FOR SEQ_VR_TOPUP_CONFIG_FILE_ID FROM SYSIBM.SYSDUMMY1";
	
	
	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public CircleTopupConfigDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_TOPUP_INSERT_KEY, SQL_TOPUP_INSERT);
		queryMap.put(SQL_TOPUP_INSERT_FAILED_KEY, SQL_TOPUP_INSERT_FAILED);
		queryMap.put(SQL_TOPUP_SELECT_FILE_ID_KEY, SQL_TOPUP_SELECT_FILE_ID);
		
	}

		
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	
	public List getCircleTopupList(ReportInputs inputDto,int lb,int ub)
	throws DAOException{
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount =1;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			StringBuilder query = new StringBuilder(
				"SELECT * from ( select a.*,ROW_NUMBER() OVER() rnum FROM (");
			query.append(queryMap.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY START_AMOUNT )a )b where rnum <= ? AND rnum >= ?");
			
			ps = connection.prepareStatement(query.toString());
			logger.info("Query after changes=" + query.toString());
			if (inputDto.getStatus() != null && !inputDto.getStatus().equals(""))
			{
				ps.setString(paramCount++,inputDto.getStatus());
			}
			else
			{
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++, inputDto.getCircleId());
			if (inputDto.getStartDt() != null && !inputDto.getStartDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getStartDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getEndDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			ps.setInt(paramCount++, ub);
			ps.setInt(paramCount++, lb+1);
		
			rs = ps.executeQuery();
			while (rs.next()) {
				CircleTopupConfig dto = new CircleTopupConfig();
				dto.setCircleId(rs.getInt("CIRCLE_ID"));
				dto.setLoginName(rs.getString("LOGIN_NAME"));
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setEndAmount(rs.getDouble("TILL_AMOUNT"));
				dto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				dto.setStartAmount(rs.getDouble("START_AMOUNT"));
				dto.setTillAmount(rs.getDouble("TILL_AMOUNT"));
				dto.setEspCommission(rs.getDouble("ESP_COMMISSION"));
				dto.setPspCommission(rs.getDouble("PSP_COMMISSION"));
				dto.setServiceTax(rs.getDouble("SERVICE_TAX"));
				dto.setProcessingFee(rs.getDouble("PROCESSING_FEE"));
				dto.setInCardGroup(rs.getString("IN_CARD_GROUP"));
				dto.setProcessingCode(rs.getString("PROCESSING_CODE"));
				dto.setValidity(rs.getInt("VALIDITY"));
				dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				dto.setCreatedBy(rs.getLong("CREATED_BY"));
				dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				dto.setUpdatedBy(rs.getLong("UPDATED_BY"));
				dto.setTopupConfigId(rs.getInt("TOPUP_CONFIG_ID"));
				dto.setRowNum(rs.getString("RNUM"));
				dto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				topupList.add(dto);
				
				//topupList.add(populateDto(rs));
			}

		} catch (SQLException sqle) {

			logger.fatal(" : SQL Exception occured while find. "
					+ "Exception Message: ", sqle);

			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed .... ");
		return topupList;

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	
	public List getCircleTopupList(ReportInputs inputDto)
	throws DAOException{
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount=1;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			StringBuilder query = new StringBuilder(queryMap.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			logger.info("Query is ="+queryMap.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
				}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			query
			.append(" ORDER BY START_AMOUNT");
			ps = connection.prepareStatement(query.toString());
			if (inputDto.getStatus() != null && !inputDto.getStatus().equals(""))
			{
				ps.setString(paramCount++,inputDto.getStatus());
			}
			else
			{
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++,inputDto.getCircleId());
			if (inputDto.getStartDt() != null && !inputDto.getStartDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getStartDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getEndDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				CircleTopupConfig dto = new CircleTopupConfig();
				dto.setCircleId(rs.getInt("CIRCLE_ID"));
				dto.setLoginName(rs.getString("LOGIN_NAME"));
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setEndAmount(rs.getDouble("TILL_AMOUNT"));
				dto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				dto.setStartAmount(rs.getDouble("START_AMOUNT"));
				dto.setTillAmount(rs.getDouble("TILL_AMOUNT"));
				dto.setEspCommission(rs.getDouble("ESP_COMMISSION"));
				dto.setPspCommission(rs.getDouble("PSP_COMMISSION"));
				dto.setServiceTax(rs.getDouble("SERVICE_TAX"));
				dto.setProcessingFee(rs.getDouble("PROCESSING_FEE"));
				dto.setInCardGroup(rs.getString("IN_CARD_GROUP"));
				dto.setProcessingCode(rs.getString("PROCESSING_CODE"));
				dto.setValidity(rs.getInt("VALIDITY"));
				dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				dto.setCreatedBy(rs.getLong("CREATED_BY"));
				dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				dto.setUpdatedBy(rs.getLong("UPDATED_BY"));
				dto.setTopupConfigId(rs.getInt("TOPUP_CONFIG_ID"));
				topupList.add(dto);
				//topupList.add(populateDto(rs));
			}

		} catch (SQLException sqle) {

			logger.fatal(" : SQL Exception occured while find. "
					+ "Exception Message: ", sqle);

			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed .... ");
		return topupList;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupListCount(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public int getCircleTopupListCount(ReportInputs inputDto) throws DAOException{
		int noofpages=0;
		int noofRow=0;
		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount=1;
		try {
			StringBuilder query = new StringBuilder(queryMap.get(
					SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT_KEY));
			
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
				}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			ps = connection.prepareStatement(query.toString());

			if (inputDto.getStatus() != null && !inputDto.getStatus().equals(""))
			{
				ps.setString(paramCount++,inputDto.getStatus());
			}
			else
			{
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++,inputDto.getCircleId());
			if (inputDto.getStartDt() != null && !inputDto.getStartDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getStartDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				//Date date = Utility.str2date(inputDto.getEndDt(), "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			
			rs = ps.executeQuery();
			if(rs.next()) {
				noofRow=rs.getInt(1);
			}
			if (0 == noofRow) {
				logger.error(" No Circles found  .");
				throw new DAOException(
						ExceptionCode.SlabConfiguration.ERROR_SLAB_NOT_EXIST);
			}
			
			noofpages = Utility.getPaginationSize(noofRow);
			
			logger.info(" Number of Circles found = " + noofRow);
			logger.info("Executed .... ");
			return noofpages;
		} catch (SQLException e) {

			logger.error("SQL Exception occured while finding the no of circles."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.SlabConfiguration.ERROR_SLAB_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}
}