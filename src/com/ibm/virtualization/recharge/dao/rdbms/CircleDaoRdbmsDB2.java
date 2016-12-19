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

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for the methods used for DB2.
 * 
 * @author Mohit Aggarwal
 */
public class CircleDaoRdbmsDB2 extends CircleDaoRdbms {
	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(CircleDaoRdbmsDB2.class
			.getName());

	//protected final static String SQL_CIRCLE_INSERT ="INSERT INTO VR_CIRCLE_MASTER (CIRCLE_ID, CIRCLE_CODE, CIRCLE_NAME, STATUS,COMMISSION_RATE, CREATED_BY, UPDATED_BY, DESCRIPTION, REGION_ID,THRESHOLD,OPENING_BALANCE, OPERATING_BALANCE, AVAILABLE_BALANCE, UPDATED_DT, CREATED_DT,PHONE_NO,TERMS1,TERMS2,TERMS3,TERMS4,REMARKS) VALUES (SEQ_VR_CIRCLE_MASTER.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
	protected final static String SQL_CIRCLE_INSERT ="INSERT INTO VR_CIRCLE_MASTER (CIRCLE_ID, CIRCLE_CODE, CIRCLE_NAME, STATUS,COMMISSION_RATE, CREATED_BY, UPDATED_BY, DESCRIPTION, REGION_ID,THRESHOLD,OPENING_BALANCE, OPERATING_BALANCE, AVAILABLE_BALANCE, UPDATED_DT, CREATED_DT,PHONE_NO,TERMS1,TERMS2,TERMS3,TERMS4,REMARKS,SALES_TAX_NO,SERVICE_TAX_NO,COMPANY_NAME,TIN_NO,ADDRESS1,ADDRESS2,FAX_NO,CST_NO,CST_DATE,SALES_TAX_DATE,TIN_DATE) VALUES (SEQ_VR_CIRCLE_MASTER.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static final String SQL_CIRCLE_UPDATE = "UPDATE VR_CIRCLE_MASTER SET CIRCLE_ID = ?, CIRCLE_CODE = ?, CIRCLE_NAME = ?, STATUS = ?, COMMISSION_RATE = ?, UPDATED_BY = ?, DESCRIPTION = ?, REGION_ID = ?, THRESHOLD = ?, OPENING_BALANCE = ?,  OPERATING_BALANCE = ?,  AVAILABLE_BALANCE = ?, UPDATED_DT = ?,PHONE_NO= ?,TERMS1= ?,TERMS2= ?,TERMS3= ?,TERMS4= ?,REMARKS= ?,SALES_TAX_NO= ?,SERVICE_TAX_NO= ?,COMPANY_NAME= ?,TIN_NO= ?,ADDRESS1= ?,ADDRESS2= ?,FAX_NO= ?,CST_NO= ?,CST_DATE= ?,SALES_TAX_DATE= ?,TIN_DATE= ?  WHERE CIRCLE_ID = ?";

	protected final static String SQL_CIRCLE_SELECT_ON_CODE_NAME = "SELECT CLE.CIRCLE_CODE, CLE.CIRCLE_NAME FROM VR_CIRCLE_MASTER CLE WHERE UPPER(CLE.CIRCLE_CODE) = ? OR UPPER(CLE.CIRCLE_NAME) = ? ORDER BY CLE.CIRCLE_NAME ";

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public CircleDaoRdbmsDB2(Connection connection) {
		//this.connection = connection;
		super(connection);
		queryMap.put(SQL_CIRCLE_INSERT_KEY, SQL_CIRCLE_INSERT);
		queryMap.put(SQL_CIRCLE_UPDATE_KEY, SQL_CIRCLE_UPDATE);
		queryMap.put(SQL_CIRCLE_SELECT_ON_CODE_NAME_KEY,SQL_CIRCLE_SELECT_ON_CODE_NAME);

	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getAllCircles()
	 */
	public ArrayList getAllCircles(ReportInputs inputDto)
			throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			logger.info(" Before Connection");
			StringBuilder query = new StringBuilder(
					queryMap.get(SQL_CIRCLE_SELECT_PARAMETER_BASED_KEY));

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND CLE.CIRCLE_ID = ? ");
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) <= ? ");
			}
			query.append(" ORDER BY CLE.CIRCLE_NAME ");

			ps = connection.prepareStatement(query.toString());
			logger.info("Query is=" + query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());

			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				ps.setInt(paramCount++, inputDto.getCircleId());
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date =
				// Utility.str2date(inputDto.getStartDt(),"MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}

			rs = ps.executeQuery();
			ArrayList<Circle> results = new ArrayList<Circle>();
			while (rs.next()) {
				Circle circle = new Circle();
				circle.setLoginName(rs.getString("LOGIN_NAME"));
				circle.setCircleId(rs.getInt("CIRCLE_ID"));
				circle.setCircleCode(rs.getString("CIRCLE_CODE"));
				circle.setCircleName(rs.getString("CIRCLE_NAME"));
				circle.setStatus(rs.getString("STATUS"));
				circle.setRate(rs.getFloat("COMMISSION_RATE"));
				circle.setCreatedBy(rs.getInt("CREATED_BY"));
				circle.setUpdatedBy(rs.getInt("UPDATED_BY"));
				circle.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				circle.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				circle.setDescription(rs.getString("DESCRIPTION"));
				circle.setRegionId(rs.getInt("REGION_ID"));
				circle.setThreshold(rs.getDouble("THRESHOLD"));
				circle.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				circle.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				circle.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				circle.setRegionName(rs.getString("REGION_NAME"));
				results.add(circle);
				// results.add(populateDto(rs));
			}
			logger.info(" Number of Circles found = " + results.size());
			if (0 == results.size()) {
				logger.error(" No Circles found  .");
				throw new DAOException(
						ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
			}
			logger.info("Executed .... ");
			return results;
		} catch (SQLException e) {

			logger.error("SQL Exception occured while select."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getAllCircles()
	 */
	public ArrayList getAllCircles(ReportInputs inputDto, int lb, int ub)
			throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			logger.info(" Before Connection");
			StringBuilder query = new StringBuilder(
					"SELECT * from ( select a.*,ROW_NUMBER() OVER() rnum FROM (");
			query.append(queryMap.get(SQL_CIRCLE_SELECT_PARAMETER_BASED_KEY));

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND CLE.CIRCLE_ID = ? ");
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY CLE.CIRCLE_NAME )a)b where rnum <= ? AND rnum >= ?");
			ps = connection.prepareStatement(query.toString());
			logger.info("Query after changes=" + query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());

			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				ps.setInt(paramCount++, inputDto.getCircleId());
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getStartDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			ps.setInt(paramCount++, ub);
			ps.setInt(paramCount++, lb + 1);
			logger.info("check lb=" + lb);
			logger.info("check ub=" + ub);
			rs = ps.executeQuery();
			ArrayList<Circle> results = new ArrayList<Circle>();
			while (rs.next()) {
				Circle circle = new Circle();
				circle.setLoginName(rs.getString("LOGIN_NAME"));
				circle.setCircleId(rs.getInt("CIRCLE_ID"));
				circle.setCircleCode(rs.getString("CIRCLE_CODE"));
				circle.setCircleName(rs.getString("CIRCLE_NAME"));
				circle.setStatus(rs.getString("STATUS"));
				circle.setRate(rs.getFloat("COMMISSION_RATE"));
				circle.setCreatedBy(rs.getInt("CREATED_BY"));
				circle.setUpdatedBy(rs.getInt("UPDATED_BY"));
				circle.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				circle.setCreatedDt(rs.getTimestamp("CREATED_DT"));

				// if the description is more than 10 chars then show only
				// limited Description, to be displayed in grid
				circle.setDescription(Utility.delemitDesctiption(rs
						.getString("DESCRIPTION")));
				circle.setRegionId(rs.getInt("REGION_ID"));
				circle.setThreshold(rs.getDouble("THRESHOLD"));
				circle.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				circle.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				circle.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				circle.setRegionName(rs.getString("REGION_NAME"));
				circle.setRowNum(rs.getString("RNUM"));
				circle.setTotalRecords(rs.getInt("RECORD_COUNT"));
				results.add(circle);
				// results.add(populateDto(rs));
			}
			logger.info(" Number of Circles found = " + results.size());
			if (0 == results.size()) {
				logger.error(" No Circles found  .");
				throw new DAOException(
						ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
			}
			logger.info("Executed .... ");
			connection.commit();
			return results;

		} catch (SQLException e) {

			logger.error("SQL Exception occured while select."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */

			DBConnectionManager.releaseResources(ps, rs);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getCirclesCount(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public int getCirclesCount(ReportInputs inputDto) throws DAOException {
		int noofpages = 0;
		int noofRow = 1;
		logger.info("Started...");
		int paramCount = 1;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			logger.info(" Before Connection");
			StringBuilder query = new StringBuilder(
					queryMap.get(SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT_KEY));
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND DATE(CLE.CREATED_DT) <= ?");
			}
			ps = connection.prepareStatement(query.toString());
			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());

			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getStartDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getStartDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}
			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				// Date date = Utility.str2date(inputDto.getEndDt(),
				// "MM/dd/yyyy");
				Date date = Utility.str2date(inputDto.getEndDt());
				ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			if (0 == noofRow) {
				logger.error(" No Circles found  .");
				throw new DAOException(
						ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
			}

			noofpages = Utility.getPaginationSize(noofRow);

			logger.info(" Number of Circles found = " + noofRow);
			logger.info("Executed .... ");
			return noofpages;
		} catch (SQLException e) {

			logger.error(
					"SQL Exception occured while finding the no of circles."
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	
}
