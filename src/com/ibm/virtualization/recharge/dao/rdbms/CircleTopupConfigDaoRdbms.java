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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.CircleTopupConfigDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UploadTopupSlab;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * CircleTopupConfigDao interface
 * 
 * @author Kumar Saurabh
 * 
 */

public class CircleTopupConfigDaoRdbms extends BaseDaoRdbms implements
		CircleTopupConfigDao {
	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger
			.getLogger(CircleTopupConfigDaoRdbms.class.getName());

	/*
	 * SQL Used within DaoImpl
	 */
	protected final static String SQL_TOPUP_INSERT_KEY = "SQL_TOPUP_INSERT";

	protected final static String SQL_TOPUP_INSERT = "INSERT INTO VR_CIRCLE_TOPUP_CONFIG (CIRCLE_ID, TRANSACTION_TYPE, "
			+ "START_AMOUNT, TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, "
			+ "IN_CARD_GROUP, PROCESSING_CODE, VALIDITY, CREATED_DT, CREATED_BY,"
			+ " TOPUP_CONFIG_ID , STATUS ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,SEQ_VR_TOPUP_CONFIG.nextval, ?)";

	protected final static String SQL_TOPUP_SELECT_CHANGED_KEY = "SQL_TOPUP_SELECT_CHANGED";

	protected final static String SQL_TOPUP_SELECT_CHANGED = "SELECT c.LOGIN_NAME,a.CIRCLE_ID, a.TRANSACTION_TYPE, a.START_AMOUNT, "
			+ " a.TILL_AMOUNT, a.ESP_COMMISSION, a.PSP_COMMISSION, a.SERVICE_TAX, b.STATUS, "
			+ " a.PROCESSING_FEE, a.IN_CARD_GROUP, a.PROCESSING_CODE "
			+ " ,a.CREATED_DT, a.CREATED_BY, a.UPDATED_DT, a.UPDATED_BY,"
			+ " a.TOPUP_CONFIG_ID, b.CIRCLE_NAME FROM VR_CIRCLE_TOPUP_CONFIG a, "
			+ " VR_CIRCLE_MASTER b, VR_LOGIN_MASTER c WHERE a.CIRCLE_ID = ? "
			+ " AND b.CIRCLE_ID = a.CIRCLE_ID AND b.CREATED_BY = c.LOGIN_ID "
			+ " ORDER BY a.START_AMOUNT  ";

	protected final static String SQL_TOPUP_SELECT_ID_CHANGED_KEY = "SQL_TOPUP_SELECT_ID_CHANGED";

	protected final static String SQL_TOPUP_SELECT_ID_CHANGED = "SELECT c.LOGIN_NAME,a.CIRCLE_ID, a.TRANSACTION_TYPE, a.START_AMOUNT, "
			+ " a.TILL_AMOUNT, a.ESP_COMMISSION, a.PSP_COMMISSION, a.SERVICE_TAX, a.STATUS, "
			+ " a.PROCESSING_FEE, a.IN_CARD_GROUP, a.PROCESSING_CODE "
			+ " ,a.CREATED_DT, a.CREATED_BY, a.UPDATED_DT, a.UPDATED_BY,"
			+ " a.TOPUP_CONFIG_ID, b.CIRCLE_NAME, a.VALIDITY FROM VR_CIRCLE_TOPUP_CONFIG a, "
			+ " VR_CIRCLE_MASTER b, VR_LOGIN_MASTER c WHERE a.TOPUP_CONFIG_ID = ? "
			+ " AND b.CIRCLE_ID = a.CIRCLE_ID AND b.CREATED_BY = c.LOGIN_ID "
			+ " ORDER BY a.START_AMOUNT  ";

	protected final static String SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY = "SQL_TOPUP_SELECT_ARGUMENT_BASED";

	protected final static String SQL_TOPUP_SELECT_ARGUMENT_BASED = " SELECT VR_CIRCLE_TOPUP_CONFIG.VALIDITY,VR_LOGIN_MASTER.LOGIN_NAME ,VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID,"
			+ " VR_CIRCLE_TOPUP_CONFIG.TRANSACTION_TYPE, VR_CIRCLE_TOPUP_CONFIG.START_AMOUNT, VR_CIRCLE_TOPUP_CONFIG.STATUS,"
			+ "VR_CIRCLE_TOPUP_CONFIG.TILL_AMOUNT, VR_CIRCLE_TOPUP_CONFIG.ESP_COMMISSION, VR_CIRCLE_TOPUP_CONFIG.PSP_COMMISSION,"
			+ " VR_CIRCLE_TOPUP_CONFIG.SERVICE_TAX, VR_CIRCLE_TOPUP_CONFIG.PROCESSING_FEE, VR_CIRCLE_TOPUP_CONFIG.IN_CARD_GROUP,"
			+ " VR_CIRCLE_TOPUP_CONFIG.PROCESSING_CODE ,VR_CIRCLE_TOPUP_CONFIG.CREATED_DT, VR_CIRCLE_TOPUP_CONFIG.CREATED_BY,"
			+ " VR_CIRCLE_TOPUP_CONFIG.UPDATED_DT, VR_CIRCLE_TOPUP_CONFIG.UPDATED_BY, VR_CIRCLE_TOPUP_CONFIG.TOPUP_CONFIG_ID,"
			+ " VR_CIRCLE_MASTER.CIRCLE_NAME, COUNT(VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID)over() RECORD_COUNT FROM VR_CIRCLE_TOPUP_CONFIG, VR_CIRCLE_MASTER,VR_LOGIN_MASTER "
			+ " WHERE VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID = VR_CIRCLE_MASTER.CIRCLE_ID"
			+ " AND VR_LOGIN_MASTER.LOGIN_ID = VR_CIRCLE_TOPUP_CONFIG.CREATED_BY "
			+ " AND VR_CIRCLE_TOPUP_CONFIG.STATUS = ? AND VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID = ? ";

	protected final static String SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT_KEY = "SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT";

	protected final static String SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT = "SELECT COUNT(*) FROM VR_CIRCLE_TOPUP_CONFIG,"
			+ "VR_CIRCLE_MASTER WHERE VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID = VR_CIRCLE_MASTER.CIRCLE_ID "
			+ " AND VR_CIRCLE_TOPUP_CONFIG.STATUS = ? AND VR_CIRCLE_TOPUP_CONFIG.CIRCLE_ID = ? ";

	protected final static String SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE_KEY = "SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE";

	protected final static String SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE = "SELECT CIRCLE_ID, TRANSACTION_TYPE, START_AMOUNT, "
			+ "TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, IN_CARD_GROUP, PROCESSING_CODE, VALIDITY"
			+ ",CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, TOPUP_CONFIG_ID , STATUS FROM VR_CIRCLE_TOPUP_CONFIG WHERE CIRCLE_ID = ? AND TRANSACTION_TYPE = ? ORDER BY START_AMOUNT ";

	protected final static String SQL_TOPUP_CIRCLE_VALIDATE_KEY = "SQL_TOPUP_CIRCLE_VALIDATE";

	protected final static String SQL_TOPUP_CIRCLE_VALIDATE = "SELECT CIRCLE_ID, TRANSACTION_TYPE, START_AMOUNT, "
			+ "TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, IN_CARD_GROUP, PROCESSING_CODE, VALIDITY"
			+ ",  CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, TOPUP_CONFIG_ID , STATUS FROM VR_CIRCLE_TOPUP_CONFIG WHERE CIRCLE_ID = ?"
			+ " AND TRANSACTION_TYPE=? AND STATUS='A' AND ((? BETWEEN START_AMOUNT AND TILL_AMOUNT ) OR (? BETWEEN START_AMOUNT AND TILL_AMOUNT )or (START_AMOUNT BETWEEN ? AND ? )or (TILL_AMOUNT BETWEEN ? AND ? ))";

	protected final static String SQL_TOPUP_CIRCLE_ID_KEY = "SQL_TOPUP_CIRCLE_ID";

	protected final static String SQL_TOPUP_CIRCLE_ID = "SELECT CIRCLE_ID, TRANSACTION_TYPE, START_AMOUNT, "
			+ "TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, IN_CARD_GROUP, PROCESSING_CODE, VALIDITY"
			+ ",  CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, TOPUP_CONFIG_ID , STATUS FROM VR_CIRCLE_TOPUP_CONFIG WHERE CIRCLE_ID = ?"
			+ " AND ? BETWEEN START_AMOUNT AND TILL_AMOUNT AND TRANSACTION_TYPE=? ";

	protected final static String SQL_TOPUP_UPDATE_KEY = "SQL_TOPUP_UPDATE";

	protected static final String SQL_TOPUP_UPDATE = "UPDATE VR_CIRCLE_TOPUP_CONFIG SET TRANSACTION_TYPE = ? , START_AMOUNT =?, TILL_AMOUNT =?, ESP_COMMISSION =?, "
			+ "PSP_COMMISSION =?, SERVICE_TAX =?, PROCESSING_FEE =?, IN_CARD_GROUP =?, PROCESSING_CODE =?, VALIDITY=?, "
			+ " UPDATED_DT =?, UPDATED_BY =? , STATUS = ? WHERE TOPUP_CONFIG_ID=?";

	protected final static String SQL_TOPUP_UPDATE_STATUS_KEY = "SQL_TOPUP_UPDATE_STATUS";

	protected static final String SQL_TOPUP_UPDATE_STATUS = "UPDATE VR_CIRCLE_TOPUP_CONFIG SET STATUS ='D' WHERE CIRCLE_ID=?";

	protected final static String SQL_TOPUP_UPLOAD_CIRCLE_ID_KEY = "SQL_TOPUP_UPLOAD_CIRCLE_ID";

	protected static final String SQL_TOPUP_UPLOAD_CIRCLE_ID = "SELECT * FROM VR_CIRCLE_TOPUP_CONFIG_FAILED WHERE  FILE_ID=(Select max(FILE_ID) from VR_CIRCLE_TOPUP_CONFIG_FAILED where CIRCLE_ID=?)";

	protected final static String SQL_TOPUP_INSERT_FAILED_KEY = "SQL_TOPUP_INSERT_FAILED";

	protected final static String SQL_TOPUP_INSERT_FAILED = "INSERT INTO VR_CIRCLE_TOPUP_CONFIG_FAILED (CIRCLE_ID, TRANSACTION_TYPE, "
			+ "START_AMOUNT, TILL_AMOUNT, ESP_COMMISSION, PSP_COMMISSION, SERVICE_TAX, PROCESSING_FEE, "
			+ "IN_CARD_GROUP, PROCESSING_CODE, VALIDITY, CREATED_DT, CREATED_BY,"
			+ " TOPUP_ID ,FILE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,SEQ_VR_TOPUP_CONFIG_FAILED.nextval,?)";
	
	protected final static String SQL_TOPUP_SELECT_FILE_ID_KEY = "SQL_TOPUP_SELECT_FILE_ID";

	protected final static String SQL_TOPUP_SELECT_FILE_ID = "SELECT SEQ_VR_TOPUP_CONFIG_FILE_ID.nextval FROM DUAL";

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public CircleTopupConfigDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_TOPUP_INSERT_KEY, SQL_TOPUP_INSERT);
		queryMap.put(SQL_TOPUP_SELECT_CHANGED_KEY, SQL_TOPUP_SELECT_CHANGED);
		queryMap.put(SQL_TOPUP_SELECT_ID_CHANGED_KEY,
				SQL_TOPUP_SELECT_ID_CHANGED);
		queryMap.put(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY,
				SQL_TOPUP_SELECT_ARGUMENT_BASED);
		queryMap.put(SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT_KEY,
				SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT);
		queryMap.put(SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE_KEY,
				SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE);
		queryMap.put(SQL_TOPUP_CIRCLE_ID_KEY, SQL_TOPUP_CIRCLE_ID);
		queryMap.put(SQL_TOPUP_CIRCLE_VALIDATE_KEY, SQL_TOPUP_CIRCLE_VALIDATE);
		queryMap.put(SQL_TOPUP_UPDATE_KEY, SQL_TOPUP_UPDATE);
		queryMap.put(SQL_TOPUP_UPDATE_STATUS_KEY, SQL_TOPUP_UPDATE_STATUS);
		queryMap
				.put(SQL_TOPUP_UPLOAD_CIRCLE_ID_KEY, SQL_TOPUP_UPLOAD_CIRCLE_ID);
		queryMap.put(SQL_TOPUP_INSERT_FAILED_KEY, SQL_TOPUP_INSERT_FAILED);
		queryMap.put(SQL_TOPUP_SELECT_FILE_ID_KEY, SQL_TOPUP_SELECT_FILE_ID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#insertCircleTopup(com.ibm.virtualization.recharge.dto.CircleTopupConfig)
	 */
	public void insertCircleTopup(CircleTopupConfig dto) throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;

		int rowsUpdated = 0;
		int i = 1;
		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_TOPUP_INSERT_KEY));
			logger.info("Query is " + SQL_TOPUP_INSERT);
			ps.setInt(i++, dto.getCircleId());
			ps.setString(i++, dto.getTransactionType());
			ps.setDouble(i++, dto.getStartAmount());
			ps.setDouble(i++, dto.getTillAmount());
			ps.setDouble(i++, dto.getEspCommission());
			ps.setDouble(i++, dto.getPspCommission());
			ps.setDouble(i++, dto.getServiceTax());
			ps.setDouble(i++, dto.getProcessingFee());
			ps.setString(i++, dto.getInCardGroup());
			ps.setString(i++, dto.getProcessingCode());
			ps.setInt(i++, dto.getValidity());
			ps.setTimestamp(i++, Utility.getDateTime());
			ps.setLong(i++, dto.getCreatedBy());
			ps.setString(i, "A");
			rowsUpdated = ps.executeUpdate();
			logger.info(" Row insertion successful on table: Inserted:"
					+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.fatal("i SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed .... ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopup(int)
	 */

	public List getCircleTopup(int circleId) throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			// ps = connection.prepareStatement(SQL_TOPUP_SELECT);
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_SELECT_CHANGED_KEY));
			logger.info("Query is " + SQL_TOPUP_SELECT_CHANGED);
			ps.setInt(1, circleId);
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
				// dto.setValidity(rs.getInt("VALIDITY"));
				dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));

				dto.setCreatedBy(rs.getLong("CREATED_BY"));
				dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));
				dto.setUpdatedBy(rs.getLong("UPDATED_BY"));
				dto.setTopupConfigId(rs.getInt("TOPUP_CONFIG_ID"));
				topupList.add(dto);
				// topupList.add(populateDto(rs));
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
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopup(int,
	 *      java.lang.String)
	 */
	public List getCircleTopup(int circleId, String transactionType)
			throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_SELECT_ON_CIRCLE_TRANSACTION_TYPE_KEY));
			ps.setInt(1, circleId);
			ps.setString(2, transactionType);
			rs = ps.executeQuery();
			while (rs.next()) {
				topupList.add(populateDto(rs));
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
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */

	public List getCircleTopupList(ReportInputs inputDto, int lb, int ub)
			throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			StringBuilder query = new StringBuilder(
					"SELECT * from ( select a.*,ROWNUM rnum FROM (");
			query.append(queryMap.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY START_AMOUNT )a Where ROWNUM <= ? ) where rnum >= ?");

			ps = connection.prepareStatement(query.toString());
			logger.info("Query after changes=" + query.toString());
			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++, inputDto.getCircleId());
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
				// topupList.add(populateDto(rs));
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
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */

	public List getCircleTopupList(ReportInputs inputDto) throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		List<CircleTopupConfig> topupList = new ArrayList<CircleTopupConfig>();

		try {
			StringBuilder query = new StringBuilder(queryMap
					.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			logger.info("Query is ="
					+ queryMap.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_KEY));
			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			query.append(" ORDER BY START_AMOUNT");
			ps = connection.prepareStatement(query.toString());
			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++, inputDto.getCircleId());
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
				// topupList.add(populateDto(rs));
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
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupListCount(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public int getCircleTopupListCount(ReportInputs inputDto)
			throws DAOException {
		int noofpages = 0;
		int noofRow = 0;
		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		try {
			StringBuilder query = new StringBuilder(queryMap
					.get(SQL_TOPUP_SELECT_ARGUMENT_BASED_COUNT_KEY));

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query
						.append(" AND TRUNC(VR_CIRCLE_TOPUP_CONFIG.CREATED_DT) <= ? ");
			}
			ps = connection.prepareStatement(query.toString());

			if (inputDto.getStatus() != null
					&& !inputDto.getStatus().equals("")) {
				ps.setString(paramCount++, inputDto.getStatus());
			} else {
				throw new DAOException(
						ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
			}
			ps.setInt(paramCount++, inputDto.getCircleId());
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
						ExceptionCode.SlabConfiguration.ERROR_SLAB_NOT_EXIST);
			}

			noofpages = Utility.getPaginationSize(noofRow);

			logger.info(" Number of Circles found = " + noofRow);
			logger.info("Executed .... ");
			return noofpages;
		} catch (SQLException e) {

			logger.error(
					"SQL Exception occured while finding the no of circles."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.SlabConfiguration.ERROR_SLAB_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopupOfId(int)
	 */

	public CircleTopupConfig getCircleTopupOfId(int topupConfigId)
			throws DAOException {
		logger.info("Started...topupConfigId " + topupConfigId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		CircleTopupConfig dto = null;

		try {

			// ps = connection.prepareStatement(SQL_TOPUP_SELECT_ID);
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_SELECT_ID_CHANGED_KEY));
			logger.info("Query is ="
					+ queryMap.get(SQL_TOPUP_SELECT_ID_CHANGED_KEY));
			ps.setInt(1, topupConfigId);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new CircleTopupConfig();
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

				// dto = populateDto(rs);
			}

		} catch (SQLException sqle) {
			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: ", sqle);
			sqle.printStackTrace();
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed .... ");
		return dto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopup(int,
	 *      double, com.ibm.virtualization.recharge.common.TransactionType)
	 */
	public ArrayList getCircleTopupList(int circleId, double startAmount,
			double tillAmount, TransactionType transactionType)
			throws DAOException {
		logger.info("Started fetching circle topup config details ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		CircleTopupConfig dto = null;
		ArrayList circleTopupList = new ArrayList();
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_CIRCLE_VALIDATE_KEY));
			logger.info("Query is "
					+ queryMap.get(SQL_TOPUP_CIRCLE_VALIDATE_KEY));
			ps.setInt(1, circleId);
			ps.setString(2, transactionType.name());
			ps.setDouble(3, startAmount);
			ps.setDouble(4, tillAmount);
			ps.setDouble(5, startAmount);
			ps.setDouble(6, tillAmount);
			ps.setDouble(7, startAmount);
			ps.setDouble(8, tillAmount);
			rs = ps.executeQuery();
			while (rs.next()) {
				// dto = populateDto(rs);

				logger.info("Started...in populateDto ");
				dto = new CircleTopupConfig();
				dto.setCircleId(rs.getInt("CIRCLE_ID"));

				// dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setEndAmount(rs.getDouble("TILL_AMOUNT"));

				dto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				dto.setStartAmount(rs.getDouble("START_AMOUNT"));
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

				// dto.setStatus(rs.getString("STATUS"));
				logger.info("executed...  ");
				circleTopupList.add(dto);
			}
			// else {
			// logger.warn("Circle topup config not defined ");
			// throw new DAOException(
			// ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL);
			// }
			logger.info("Successfully returned circle topup config details ");
		} catch (SQLException sqle) {
			logger.warn("SQL Exception occured. Exception Message: ", sqle);
			logger.fatal(" SQL Exception occured. Exception Message: "
					+ sqle.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		return circleTopupList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getCircleTopup(int,
	 *      double, com.ibm.virtualization.recharge.common.TransactionType)
	 */
	public CircleTopupConfig getCircleTopup(int circleId,
			double transactionAmount, TransactionType transactionType)
			throws DAOException {
		logger
				.info("Started fetching circle topup config details for circle id :"
						+ circleId
						+ "transamount :"
						+ transactionAmount
						+ "transaction type :" + transactionType.name());
		PreparedStatement ps = null;
		ResultSet rs = null;
		CircleTopupConfig dto = null;
		ArrayList circleTopupList = new ArrayList();
		try {

			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_CIRCLE_ID_KEY));
			logger.info("Query is " + queryMap.get(SQL_TOPUP_CIRCLE_ID_KEY));
			ps.setInt(1, circleId);
			ps.setDouble(2, transactionAmount);
			ps.setString(3, transactionType.name());

			rs = ps.executeQuery();
			if (rs.next()) {
				// dto = populateDto(rs);

				logger.info("Started...in populateDto ");
				dto = new CircleTopupConfig();
				dto.setCircleId(rs.getInt("CIRCLE_ID"));

				// dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setEndAmount(rs.getDouble("TILL_AMOUNT"));

				dto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				dto.setStartAmount(rs.getDouble("START_AMOUNT"));
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

				// dto.setStatus(rs.getString("STATUS"));
				logger.info("executed...  ");
				circleTopupList.add(dto);
			} else {
				logger.warn("Circle topup config not defined ");
				throw new DAOException(
						ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL);
			}
			logger.info("Successfully returned circle topup config details ");
		} catch (SQLException sqle) {
			logger.warn("SQL Exception occured. Exception Message: ", sqle);
			logger.fatal(" SQL Exception occured. Exception Message: "
					+ sqle.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, rs);
		}

		return dto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#updateCircleTopup(com.ibm.virtualization.recharge.dto.CircleTopupConfig)
	 */
	public void updateCircleTopup(CircleTopupConfig dto) throws DAOException {

		logger.info("Started... ");
		PreparedStatement ps = null;
		int numRows = -1;
		int i = 1;

		try {
			ps = connection
					.prepareStatement(queryMap.get(SQL_TOPUP_UPDATE_KEY));
			ps.setString(i++, dto.getTransactionType());
			ps.setDouble(i++, dto.getStartAmount());
			ps.setDouble(i++, dto.getTillAmount());
			ps.setDouble(i++, dto.getEspCommission());
			ps.setDouble(i++, dto.getPspCommission());
			ps.setDouble(i++, dto.getServiceTax());
			ps.setDouble(i++, dto.getProcessingFee());
			ps.setString(i++, dto.getInCardGroup());
			ps.setString(i++, dto.getProcessingCode());
			ps.setInt(i++, dto.getValidity());
			ps.setTimestamp(i++, Utility.getDateTime());
			ps.setLong(i++, dto.getUpdatedBy());
			ps.setString(i++, dto.getStatus());
			ps.setInt(i, dto.getTopupConfigId());

			numRows = ps.executeUpdate();
			logger.info("Update successful :  Updated:" + numRows + " rows");

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed .... ");

	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return dto
	 * @throws SQLException
	 */
	private CircleTopupConfig populateDto(ResultSet rs) throws SQLException {
		logger.info("Started...in populateDto ");
		CircleTopupConfig dto = new CircleTopupConfig();
		dto.setCircleId(rs.getInt("CIRCLE_ID"));
		dto.setCircleName(rs.getString("CIRCLE_NAME"));
		dto.setStatus(rs.getString("STATUS"));
		dto.setEndAmount(rs.getDouble("TILL_AMOUNT"));
		dto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
		dto.setStartAmount(rs.getDouble("START_AMOUNT"));
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

		// dto.setStatus(rs.getString("STATUS"));
		logger.info("executed...  ");

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#uploadSlabData(ArrayList
	 *      circleTopupDtoList)
	 */
	public int uploadSlabData(ArrayList circleTopupDtoList, int circleId,
			String transactionType, long createdBy) throws DAOException {

		logger.info("Started uploadSlabData...");

		PreparedStatement ps = null;
		int rowsCorrectDataUpdated = 0;
		//int rowsIncorrectDataUpdated = 0;

		try {
			connection.setAutoCommit(false);

			if (circleTopupDtoList.size() != 0) {
				int numRows = updateStatusDeactivate(circleId);
				logger.info("No of rows for which status is set Inactive are"
						+ numRows);

				ps = connection.prepareStatement(queryMap
						.get(SQL_TOPUP_INSERT_KEY));
				Iterator insertedIter = circleTopupDtoList.iterator();
				while (insertedIter.hasNext()) {
					int i = 1;
					UploadTopupSlab topupDto = (UploadTopupSlab) insertedIter
							.next();
					logger.info("Query is " + SQL_TOPUP_INSERT);
					ps.setInt(i++, circleId);
					ps.setString(i++, transactionType);
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getStartAmount()));
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getTillAmount()));
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getEspCommission()));
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getPspCommission()));
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getServiceTax()));
					ps.setDouble(i++, Double.parseDouble(topupDto
							.getProcessingFee()));
					ps.setString(i++, topupDto.getInCardGroup());
					ps.setString(i++, topupDto.getProcessingCode());
					ps.setInt(i++, Integer.parseInt(topupDto.getValidity()));
					ps.setTimestamp(i++, Utility.getDateTime());
					ps.setLong(i++, createdBy);
					ps.setString(i, "A");
					ps.addBatch();
				}
				int[] updateCounts = ps.executeBatch();
				rowsCorrectDataUpdated = updateCounts.length;
				logger.info(" Row insertion successful on table: Inserted:"
						+ updateCounts.length);
			} else {
				logger.error("No records updated");
				throw new DAOException(
						ExceptionCode.SlabConfiguration.ERROR_INSERT_TOPUP_FAILED);
			}
		} catch (NumberFormatException numExp) {
			logger.error("Exception occured while parsing."
					+ "Exception Message: ", numExp);
			// Ideally the control should not reach here as input data has
			// already been validated at service layer
			throw new DAOException(
					ExceptionCode.SlabConfiguration.ERROR_INSERT_TOPUP_FAILED);
		} catch (SQLException e) {
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			 int i=0; Exception s=e.getNextException(); while(s!=null && i<5){
				  logger.debug("exception"+s.getMessage()); s=e.getNextException();
				 i++; }
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed uploadSlabData .... ");
		return rowsCorrectDataUpdated;

	}

	/**
	 * This function is used to deactivate all the existing records for a cirlc
	 * for which data is being uploaded
	 * 
	 * @param circleId
	 * @return numRows
	 * @throws DAOException
	 */

	public int updateStatusDeactivate(int circleId) throws DAOException {

		logger.info("Started updateStatusDeactivate... ");
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_UPDATE_STATUS_KEY));
			ps.setInt(1, circleId);
			numRows = ps.executeUpdate();
			logger.info("Update successful :  Updated:" + numRows + " rows");

		} catch (SQLException e) {
			logger.error("SQL Exception occured while updating the status."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed .... ");
		return numRows;

	}

	public int uploadSlabIncorrectData(ArrayList topupIncorrectDtoList,
			int circleId, String transactionType, long createdBy)
			throws DAOException {

		logger.info("Started uploadSlabData...");

		PreparedStatement ps = null;
		int rowsUpdated = 0;
		ResultSet rs = null;
		int fileId = 0;
		PreparedStatement preparedStatment = null;

		try {
			connection.setAutoCommit(false);

			if (topupIncorrectDtoList.size() != 0) {

				preparedStatment = connection.prepareStatement(queryMap
						.get(SQL_TOPUP_SELECT_FILE_ID_KEY));
				rs = preparedStatment.executeQuery();
				if(rs.next()) {
					fileId = rs.getInt(1);
				}
				logger.debug("fileID" + fileId);

				ps = connection.prepareStatement(queryMap
						.get(SQL_TOPUP_INSERT_FAILED_KEY));

				Iterator insertedIter = topupIncorrectDtoList.iterator();

				while (insertedIter.hasNext()) {
					int i = 1;
					UploadTopupSlab IncorrectDataDto = (UploadTopupSlab) insertedIter
							.next();
					logger.info("Query is " + SQL_TOPUP_INSERT_FAILED);
					ps.setInt(i++, circleId);
					ps.setString(i++, transactionType);
					ps.setString(i++, IncorrectDataDto.getStartAmount());
					ps.setString(i++, IncorrectDataDto.getTillAmount());
					ps.setString(i++, IncorrectDataDto.getEspCommission());
					ps.setString(i++, IncorrectDataDto.getPspCommission());
					ps.setString(i++, IncorrectDataDto.getServiceTax());
					ps.setString(i++, IncorrectDataDto.getProcessingFee());
					ps.setString(i++, IncorrectDataDto.getInCardGroup());
					ps.setString(i++, IncorrectDataDto.getProcessingCode());
					ps.setString(i++, IncorrectDataDto.getValidity());
					ps.setTimestamp(i++, Utility.getDateTime());
					ps.setLong(i++, createdBy);
					ps.setInt(i, fileId);
					ps.addBatch();
				}

				int[] updateCounts = ps.executeBatch();
				rowsUpdated = updateCounts.length;

				logger.info(" Row insertion successful on table: Inserted:"
						+ rowsUpdated);
			}
		} catch (NumberFormatException numExp) {
			logger.error("Exception occured while parsing."
					+ "Exception Message: ", numExp);
			// Ideally the control should not reach here as input data has
			// already been validated at service layer
			throw new DAOException(
					ExceptionCode.SlabConfiguration.ERROR_INSERT_INCORRECT_DATA_FAILED);
		} catch (SQLException e) {
			
			 int i=0; Exception s=e.getNextException(); while(s!=null && i<5){
			  logger.debug("exception"+s.getMessage()); s=e.getNextException();
			 i++; }
			 
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			logger.error("Table VR_CIRCLE_TOPUP_CONFIG_FAILED does not exists");
			//throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(preparedStatment, null);

		}
		logger.info("Executed uploadSlabData .... ");
		return rowsUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleTopupConfigDao#getTopupConfigFailData(int,int)
	 */
	public ArrayList getTopupConfigFailData(int circleId) throws DAOException {
		logger.info("Started getTopupConfigFailData ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		UploadTopupSlab dto = null;
		ArrayList<UploadTopupSlab> topupIncorrectDataList = new ArrayList<UploadTopupSlab>();
		try {
			ps = connection.prepareStatement(queryMap
					.get(SQL_TOPUP_UPLOAD_CIRCLE_ID_KEY));
			logger.info("Query is "
					+ queryMap.get(SQL_TOPUP_UPLOAD_CIRCLE_ID_KEY));
			ps.setInt(1, circleId);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new UploadTopupSlab();
				dto.setStartAmount(rs.getString("START_AMOUNT"));
				dto.setTillAmount(rs.getString("TILL_AMOUNT"));
				dto.setEspCommission(rs.getString("ESP_COMMISSION"));
				dto.setPspCommission(rs.getString("PSP_COMMISSION"));
				dto.setServiceTax(rs.getString("SERVICE_TAX"));
				dto.setProcessingFee(rs.getString("PROCESSING_FEE"));
				dto.setInCardGroup(rs.getString("IN_CARD_GROUP"));
				dto.setProcessingCode(rs.getString("PROCESSING_CODE"));
				dto.setValidity(rs.getString("VALIDITY"));
				topupIncorrectDataList.add(dto);
			}
			logger
					.info("Successfully returned circle topup config failed data ");
		} catch (SQLException sqle) {
			logger.error("SQL Exception occured. Exception Message: ", sqle);
			logger.error(" SQL Exception occured. Exception Message: "
					+ sqle.getMessage());
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			// Close the preparedstatement ,resultset.
			DBConnectionManager.releaseResources(ps, rs);
		}

		return topupIncorrectDataList;

	}

}