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
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * CircleDao interface
 * 
 * @author Paras
 * 
 * 
 */
public class CircleDaoRdbms extends BaseDaoRdbms implements CircleDao {
	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(CircleDaoRdbms.class
			.getName());

	/*
	 * SQL Used within DaoImpl
	 */
	
	
	
	protected final static String SQL_CIRCLE_INSERT_KEY ="SQL_CIRCLE_INSERT";
	protected final static String SQL_CIRCLE_INSERT = "INSERT INTO VR_CIRCLE_MASTER (CIRCLE_ID, CIRCLE_CODE, CIRCLE_NAME, STATUS,COMMISSION_RATE, CREATED_BY, UPDATED_BY, DESCRIPTION, REGION_ID,THRESHOLD,OPENING_BALANCE, OPERATING_BALANCE, AVAILABLE_BALANCE, UPDATED_DT, CREATED_DT,PHONE_NO,TERMS1,TERMS2,TERMS3,TERMS4,REMARKS,SALE_TAX_NO,SERVICE_TAX_NO,COMPANY_NAME,TIN_NO,ADDRESS1,ADDRESS2,FAX_NO,CST_NO,CST_DATE,SALE_TAX_DATE,TIN_DATE) VALUES (SEQ_VR_CIRCLE_MASTER.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//protected final static String SQL_CIRCLE_INSERT = "INSERT INTO VR_CIRCLE_MASTER (CIRCLE_ID, CIRCLE_CODE, CIRCLE_NAME, STATUS,COMMISSION_RATE, CREATED_BY, UPDATED_BY, DESCRIPTION, REGION_ID,THRESHOLD,OPENING_BALANCE, OPERATING_BALANCE, AVAILABLE_BALANCE, UPDATED_DT, CREATED_DT,PHONE_NO,TERMS1,TERMS2,TERMS3,TERMS4,REMARKS) VALUES (SEQ_VR_CIRCLE_MASTER.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";

	protected final static String SQL_CIRCLE_SELECT_KEY = "SQL_CIRCLE_SELECT";
	protected final static String SQL_CIRCLE_SELECT = "SELECT CLE.CIRCLE_ID, CLE.CIRCLE_CODE, CLE.CIRCLE_NAME, CLE.STATUS, CLE.COMMISSION_RATE, CLE.CREATED_BY, CLE.UPDATED_BY, CLE.UPDATED_DT, CLE.CREATED_DT, CLE.DESCRIPTION, CLE.REGION_ID, CLE.THRESHOLD, CLE.OPENING_BALANCE,  CLE.OPERATING_BALANCE,  CLE.AVAILABLE_BALANCE, RGN.REGION_NAME,CLE.PHONE_NO,CLE.TERMS1,CLE.TERMS2,CLE.TERMS3,CLE.TERMS4,CLE.REMARKS,CLE.SALES_TAX_NO,CLE.SERVICE_TAX_NO,CLE.COMPANY_NAME,CLE.TIN_NO,CLE.ADDRESS1,CLE.ADDRESS2,CLE.FAX_NO,CLE.CST_NO,CLE.CST_DATE,CLE.SALES_TAX_DATE,CLE.TIN_DATE FROM VR_CIRCLE_MASTER CLE, VR_REGION_MASTER RGN  WHERE CLE.REGION_ID = RGN.REGION_ID AND UPPER(CLE.CIRCLE_NAME) = ?";

	protected final static String SQL_CIRCLE_SELECT_ON_ID_KEY = "SQL_CIRCLE_SELECT_ON_ID"; 
	protected final static String SQL_CIRCLE_SELECT_ON_ID = "SELECT CLE.CIRCLE_ID, CLE.CIRCLE_CODE, CLE.CIRCLE_NAME, CLE.STATUS, CLE.COMMISSION_RATE, CLE.CREATED_BY, CLE.UPDATED_BY, CLE.UPDATED_DT, CLE.CREATED_DT, CLE.DESCRIPTION, CLE.REGION_ID, CLE.THRESHOLD, CLE.OPENING_BALANCE,  CLE.OPERATING_BALANCE,  CLE.AVAILABLE_BALANCE, RGN.REGION_NAME,CLE.PHONE_NO,CLE.TERMS1,CLE.TERMS2,CLE.TERMS3,CLE.TERMS4,CLE.REMARKS,CLE.SALES_TAX_NO,CLE.SERVICE_TAX_NO,CLE.COMPANY_NAME,CLE.TIN_NO,CLE.ADDRESS1,CLE.ADDRESS2,CLE.FAX_NO,CLE.CST_NO,CLE.CST_DATE,CLE.SALES_TAX_DATE,CLE.TIN_DATE FROM VR_CIRCLE_MASTER CLE, VR_REGION_MASTER RGN  WHERE CLE.REGION_ID = RGN.REGION_ID AND CLE.CIRCLE_ID = ?";

	protected final static String SQL_CIRCLE_UPDATE_KEY = "SQL_CIRCLE_UPDATE";
	protected static final String SQL_CIRCLE_UPDATE = "UPDATE VR_CIRCLE_MASTER SET CIRCLE_ID = ?, CIRCLE_CODE = ?, CIRCLE_NAME = ?, STATUS = ?, COMMISSION_RATE = ?, UPDATED_BY = ?, DESCRIPTION = ?, REGION_ID = ?, THRESHOLD = ?, OPENING_BALANCE = ?,  OPERATING_BALANCE = ?,  AVAILABLE_BALANCE = ?, UPDATED_DT = CURRENT_TIMESTAMP,PHONE_NO= ?,TERMS1= ?,TERMS2= ?,TERMS3= ?,TERMS4= ?,REMARKS= ?,SALES_TAX_NO= ?,SERVICE_TAX_NO= ?,COMPANY_NAME= ?,TIN_NO= ?,ADDRESS1= ?,ADDRESS2= ?,FAX_NO= ?,CST_NO= ?,CST_DATE= ?,SALES_TAX_DATE= ?,TIN_DATE= ?  WHERE CIRCLE_ID = ?";

	protected final static String SQL_CIRCLE_SELECT_ALL_KEY = "SQL_CIRCLE_SELECT_ALL";
	protected final static String SQL_CIRCLE_SELECT_ALL = "SELECT CLE.CIRCLE_ID, CLE.CIRCLE_CODE, CLE.CIRCLE_NAME, CLE.DESCRIPTION, CLE.DETAILS, CLE.REGION_ID, CLE.STATUS, CLE.OPENING_BALANCE, CLE.OPERATING_BALANCE, CLE.AVAILABLE_BALANCE, CLE.COMMISSION_RATE, CLE.THRESHOLD, CLE.CREATED_DT, CLE.CREATED_BY, CLE.UPDATED_DT, CLE.UPDATED_BY , RGN.REGION_NAME FROM VR_CIRCLE_MASTER CLE, VR_REGION_MASTER RGN WHERE CLE.REGION_ID = RGN.REGION_ID ORDER BY CLE.CIRCLE_NAME ";

	protected final static String SQL_CIRCLE_SELECT_ON_CODE_NAME_KEY = "SQL_CIRCLE_SELECT_ON_CODE_NAME";
	protected final static String SQL_CIRCLE_SELECT_ON_CODE_NAME = "SELECT CLE.CIRCLE_CODE, CLE.CIRCLE_NAME FROM VR_CIRCLE_MASTER CLE WHERE UPPER(CLE.CIRCLE_CODE) = ? OR UPPER(CLE.CIRCLE_NAME) = ? ORDER BY CLE.CIRCLE_NAME ";

	protected final static String SQL_CIRCLE_SELECT_PARAMETER_BASED_KEY = "SQL_CIRCLE_SELECT_PARAMETER_BASED";
	protected final static String SQL_CIRCLE_SELECT_PARAMETER_BASED = " SELECT LGN.LOGIN_NAME,CLE.CIRCLE_ID, CLE.CIRCLE_CODE, "
			+ "CLE.CIRCLE_NAME, CLE.DESCRIPTION, CLE.DETAILS, CLE.REGION_ID, CLE.STATUS, CLE.OPENING_BALANCE,"
			+ " CLE.OPERATING_BALANCE, CLE.AVAILABLE_BALANCE, CLE.COMMISSION_RATE, CLE.THRESHOLD, CLE.CREATED_DT, "
			+ "CLE.CREATED_BY, CLE.UPDATED_DT, CLE.UPDATED_BY, RGN.REGION_NAME, COUNT(CLE.REGION_ID)over() RECORD_COUNT  FROM VR_CIRCLE_MASTER CLE,"
			+ " VR_REGION_MASTER RGN, VR_LOGIN_MASTER LGN WHERE CLE.REGION_ID = RGN.REGION_ID AND LGN.LOGIN_ID = CLE.CREATED_BY AND CLE.STATUS = ? ";

	/* Find count for Total Circles */
	protected final static String SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT_KEY ="SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT";
	protected final static String SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT = "SELECT COUNT(*) FROM VR_CIRCLE_MASTER "
			+ "CLE, VR_REGION_MASTER RGN WHERE CLE.REGION_ID = RGN.REGION_ID AND CLE.STATUS = ? ";

	protected final static String SQL_CIRCLE_SELECT_ACTIVE_KEY = "SQL_CIRCLE_SELECT_ACTIVE";
	protected final static String SQL_CIRCLE_SELECT_ACTIVE = "SELECT CLE.CIRCLE_ID, CLE.CIRCLE_CODE, CLE.CIRCLE_NAME, CLE.DESCRIPTION, CLE.DETAILS, CLE.REGION_ID, CLE.STATUS, CLE.OPENING_BALANCE, CLE.OPERATING_BALANCE, CLE.AVAILABLE_BALANCE, CLE.COMMISSION_RATE, CLE.THRESHOLD, CLE.CREATED_DT, CLE.CREATED_BY, CLE.UPDATED_DT, CLE.UPDATED_BY , RGN.REGION_NAME FROM VR_CIRCLE_MASTER CLE, VR_REGION_MASTER RGN WHERE CLE.REGION_ID = RGN.REGION_ID AND CLE.STATUS = '"
			+ Constants.ACTIVE_STATUS + "' ORDER BY CLE.CIRCLE_NAME ";

	protected final static String SQL_CIRCLE_SELECT_CIRCLE_NAME_KEY = "SQL_CIRCLE_SELECT_CIRCLE_NAME";
	protected final static String SQL_CIRCLE_SELECT_CIRCLE_NAME = "SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER WHERE CIRCLE_ID = ?";

	protected final static String SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME_KEY = "SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME";
	protected final static String SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME = "SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER WHERE STATUS = '"
			+ Constants.ACTIVE_STATUS + "' AND CIRCLE_ID = ?";

	protected final static String SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE_KEY ="SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE";
	protected final static String SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE = "UPDATE VR_CIRCLE_MASTER SET OPERATING_BALANCE=(OPERATING_BALANCE-?) ,UPDATED_DT=?,UPDATED_BY=? WHERE (OPERATING_BALANCE-?)>=0 AND CIRCLE_ID=? AND STATUS='"
			+ Constants.ACTIVE_STATUS + "'";

	protected final static String SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE_KEY = "SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE";
	protected final static String SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE = "UPDATE VR_CIRCLE_MASTER  SET AVAILABLE_BALANCE=(AVAILABLE_BALANCE-?) ,UPDATED_DT=?,UPDATED_BY=? WHERE  STATUS='"
			+ Constants.ACTIVE_STATUS + "' AND CIRCLE_ID=?";

	protected final static String SQL_CIRCLE_INCREASE_OPERATING_BALANCE_KEY = "SQL_CIRCLE_INCREASE_OPERATING_BALANCE";
	protected final static String SQL_CIRCLE_INCREASE_OPERATING_BALANCE = "UPDATE VR_CIRCLE_MASTER SET OPERATING_BALANCE=(OPERATING_BALANCE+?) ,UPDATED_DT=?,UPDATED_BY=? WHERE STATUS='"
			+ Constants.ACTIVE_STATUS + "' AND CIRCLE_ID=?";

	protected final static String SQL_UPDATE_BALANCE_KEY = "SQL_UPDATE_BALANCE";
	protected final static String SQL_UPDATE_BALANCE = "UPDATE VR_CIRCLE_MASTER SET OPERATING_BALANCE=(OPERATING_BALANCE-?),AVAILABLE_BALANCE=(AVAILABLE_BALANCE-?),UPDATED_DT=?,UPDATED_BY=? WHERE (OPERATING_BALANCE-?)>=0 AND CIRCLE_ID=? AND STATUS='"
			+ Constants.ACTIVE_STATUS + "'";

//	protected Connection connection = null;
	
	

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	
	public CircleDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_CIRCLE_INSERT_KEY,SQL_CIRCLE_INSERT);
		queryMap.put(SQL_CIRCLE_SELECT_KEY,SQL_CIRCLE_SELECT);
		queryMap.put(SQL_CIRCLE_SELECT_ON_ID_KEY,SQL_CIRCLE_SELECT_ON_ID);
		queryMap.put(SQL_CIRCLE_UPDATE_KEY,SQL_CIRCLE_UPDATE);
		queryMap.put(SQL_CIRCLE_SELECT_ALL_KEY,SQL_CIRCLE_SELECT_ALL);
		queryMap.put(SQL_CIRCLE_SELECT_ON_CODE_NAME_KEY,SQL_CIRCLE_SELECT_ON_CODE_NAME);
		queryMap.put(SQL_CIRCLE_SELECT_PARAMETER_BASED_KEY,SQL_CIRCLE_SELECT_PARAMETER_BASED);
		queryMap.put(SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT_KEY,SQL_CIRCLE_SELECT_PARAMETER_BASED_COUNT);
		queryMap.put(SQL_CIRCLE_SELECT_ACTIVE_KEY,SQL_CIRCLE_SELECT_ACTIVE);
		queryMap.put(SQL_CIRCLE_SELECT_CIRCLE_NAME_KEY,SQL_CIRCLE_SELECT_CIRCLE_NAME);
		queryMap.put(SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME_KEY,SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME);
		queryMap.put(SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE_KEY,SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE);
		queryMap.put(SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE_KEY,SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE);
		queryMap.put(SQL_CIRCLE_INCREASE_OPERATING_BALANCE_KEY,SQL_CIRCLE_INCREASE_OPERATING_BALANCE);
		queryMap.put(SQL_UPDATE_BALANCE_KEY,SQL_UPDATE_BALANCE);
	}

	/**
	 * 
	 */
	public int updateCircleOperatingBal(double balance, long updateBy,
			long circleId) throws DAOException {

		logger.info("Started... balance :" + balance + " And updateBy:"
				+ updateBy + "And circleId:" + circleId);

		PreparedStatement ps = null;
		int numRows = -1;

		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_CIRCLE_UPDATE_ACC_OPERATING_BALANCE_KEY));

			ps.setDouble(1, balance);
			ps.setTimestamp(2, Utility.getDateTime());
			ps.setLong(3, updateBy);
			ps.setDouble(4, balance);
			ps.setLong(5, circleId);
			numRows = ps.executeUpdate();

			logger
					.info("Executed :Update successful on VR_CIRCLE_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update.Circle "
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);

		}
		//logger.info("Executed .... ");
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#insertCircle(com.ibm.virtualization.recharge.dto.Circle)
	 */
	public void insertCircle(Circle circle) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		int rowsUpdated = 0;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_INSERT_KEY));
			int paramCount = 1;
			// TODO BAhuja Aug.17, 2007 Instead of using SysDate, the date
			// should be passed from Service.

			/*
			 * Circle ID is getting generated with Sequence SEQ_VR_CIRCLE_MASTER
			 */
			ps.setString(paramCount++, circle.getCircleCode());
			ps.setString(paramCount++, circle.getCircleName());
			ps.setString(paramCount++, "A");
			ps.setFloat(paramCount++, 0);
			ps.setLong(paramCount++, circle.getCreatedBy());
			ps.setLong(paramCount++, circle.getUpdatedBy());
			ps.setString(paramCount++, circle.getDescription());
			ps.setInt(paramCount++, circle.getRegionId());
			ps.setDouble(paramCount++, 0.0);
			ps.setDouble(paramCount++, 0.0);
			ps.setDouble(paramCount++, 0.0);
			ps.setDouble(paramCount++, 0.0);
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			
			ps.setTimestamp(paramCount++, Utility.getDateTime());//14
			
			
			ps.setString(paramCount++,circle.getPhone() );
			
			ps.setString(paramCount++,circle.getTerms1() );
			
			ps.setString(paramCount++,circle.getTerms2() );
			
			ps.setString(paramCount++,circle.getTerms3() );
			
			ps.setString(paramCount++,circle.getTerms4() );
			
			ps.setString(paramCount++, circle.getRemarks());
			
			ps.setString(paramCount++,circle.getSaletax());
			
			ps.setString(paramCount++,circle.getService() );
			
			ps.setString(paramCount++,circle.getCompName() );
			
			ps.setString(paramCount++,circle.getTin() );
			
			ps.setString(paramCount++,circle.getAddress1() );
			
			ps.setString(paramCount++,circle.getAddress2() );
			
			ps.setString(paramCount++,circle.getFax());
			
			ps.setString(paramCount++,circle.getCst() );
			
			// Add by harbans
			Calendar cal = Calendar.getInstance();
			java.sql.Date jsqlD = 
			       new java.sql.Date( cal.getTime().getTime() );

			if(circle.getCstdate()!=null && circle.getCstdate().length()>0)
			{
				java.sql.Date cstDate = Utility.getSqlDateFromString(circle.getCstdate(),"MM/dd/yyyy");
				ps.setDate(paramCount++,cstDate);
			}
			else
			{
				ps.setDate(paramCount++, jsqlD);				
			}
			
			if(circle.getSaletaxdate()!=null && circle.getSaletaxdate().length()>0)
			{
				java.sql.Date saletaxDate = Utility.getSqlDateFromString(circle.getCstdate(),"MM/dd/yyyy");
				ps.setDate(paramCount++,saletaxDate);
			}
			else
			{
				ps.setDate(paramCount++, jsqlD);				
			}
			
			if(circle.getTinDate()!=null && circle.getTinDate().length()>0)
			{
				java.sql.Date tinDate = Utility.getSqlDateFromString(circle.getCstdate(),"MM/dd/yyyy"); 
				ps.setDate(paramCount++,tinDate);
			}
			else
			{
				ps.setDate(paramCount++, jsqlD);				
			}
			
			rowsUpdated = ps.executeUpdate();
			logger.info(" Row insertion successful on table: Inserted:"
					+ rowsUpdated + " rows");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(					"insertCircle : SQL Exception occured while inserting."							+ "Exception Message: ", e);
			throw new DAOException(					ExceptionCode.Circle.ERROR_CIRCLE_ALREADY_DEFINED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed .... ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getCircle(java.lang.String)
	 */
	public Circle getCircle(String circleName) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_KEY));
			ps.setString(1, circleName.toUpperCase());
			rs = ps.executeQuery();
			if (rs.next()) {
				//populateDto(rs)
				return populateDtoPart2(rs);
			}
			logger.info("Executed .... ");
			return null;
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getCircles(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList<Circle> getCircles(String circleCode, String circleName)
			throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_ON_CODE_NAME_KEY));
			ps.setString(1, circleCode.toUpperCase());
			ps.setString(2, circleName.toUpperCase());
			rs = ps.executeQuery();
			ArrayList<Circle> results = new ArrayList<Circle>();
			while (rs.next()) {
				Circle circle = new Circle();
				circle.setCircleCode(rs.getString("CIRCLE_CODE"));
				circle.setCircleName(rs.getString("CIRCLE_NAME"));
				results.add(circle);
			}
			logger.info(" Number of Circles found = " + results.size());
			logger.info("Executed .... ");
			return results;
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getCircle(int)
	 */
	public Circle getCircle(int circleId) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_ON_ID_KEY));
			ps.setInt(1, circleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//populateDto(rs);
				return populateDtoPart2(rs);
			}
			logger.info("Executed .... ");
			return null;
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#updateCircle(com.ibm.virtualization.recharge.dto.Circle)
	 */
	public void updateCircle(Circle circle) throws DAOException {
		logger.info("Started...updateCircle");
		PreparedStatement ps = null;
		int numRows = -1;

		try {

			ps = connection.prepareStatement(SQL_CIRCLE_UPDATE);
			logger.info("QUERY TO EDIT CIRCLE  ::  "+SQL_CIRCLE_UPDATE);
			int paramCount = 1;
			ps.setInt(paramCount++, circle.getCircleId());
			ps.setString(paramCount++, circle.getCircleCode());
			ps.setString(paramCount++, circle.getCircleName());
			ps.setString(paramCount++, circle.getStatus());
			ps.setFloat(paramCount++, circle.getRate());
			ps.setLong(paramCount++, circle.getUpdatedBy());
			ps.setString(paramCount++, circle.getDescription());
			ps.setInt(paramCount++, circle.getRegionId());
			ps.setDouble(paramCount++, circle.getThreshold());
			ps.setDouble(paramCount++, circle.getOpeningBalance());
			ps.setDouble(paramCount++, circle.getOperatingBalance());
			ps.setDouble(paramCount++, circle.getAvailableBalance());
			//ps.setTimestamp(paramCount++, Utility.getDateTime());

			ps.setString(paramCount++, circle.getPhone());

			ps.setString(paramCount++, circle.getTerms1());

			ps.setString(paramCount++, circle.getTerms2());

			ps.setString(paramCount++, circle.getTerms3());

			ps.setString(paramCount++, circle.getTerms4());

			ps.setString(paramCount++, circle.getRemarks());
			

			ps.setString(paramCount++, circle.getSaletax());

			ps.setString(paramCount++, circle.getService());

			ps.setString(paramCount++, circle.getCompName());

			ps.setString(paramCount++, circle.getTin());

			ps.setString(paramCount++, circle.getAddress1());

			ps.setString(paramCount++, circle.getAddress2());

			ps.setString(paramCount++, circle.getFax());

			ps.setString(paramCount++, circle.getCst());
			
			java.sql.Date sqlDate = new java.sql.Date(com.ibm.utilities.Utility.getDateAsDate(circle.getCstdate(), "MM/dd/yyyy").getTime());
			logger.info("CST DATE  ::  "+circle.getCstdate());
			ps.setDate(paramCount++, sqlDate);
			
			sqlDate = new java.sql.Date(com.ibm.utilities.Utility.getDateAsDate(circle.getSaletaxdate(), "MM/dd/yyyy").getTime());
			logger.info("SALES DATE  ::  "+circle.getSaletaxdate());
			ps.setDate(paramCount++, sqlDate);
			
			sqlDate = new java.sql.Date(com.ibm.utilities.Utility.getDateAsDate(circle.getTinDate(), "MM/dd/yyyy").getTime());
			logger.info("TIN DATE  ::  "+circle.getTinDate());
			ps.setDate(paramCount++, sqlDate);
			
			ps.setInt(paramCount, circle.getCircleId());

			numRows = ps.executeUpdate();

			logger
					.info("Update successful on VR_CIRCLE_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.error("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_ID_IN_USE);
		}
		catch (Exception e) {
			logger.error("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Circle.ERROR_CIRCLE_ID_IN_USE);
		}finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		//logger.info("Executed .... ");
	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return circle
	 * @throws SQLException
	 */
	private Circle populateDto(ResultSet rs) throws SQLException, DAOException {
		logger.info("Started...populateDto");
		Circle circle = new Circle();
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
		//logger.info("Executed ....");
		return circle;
	}

	private Circle populateDtoPart2(ResultSet rs) throws SQLException, DAOException {
		//logger.info("Started...");
		Circle circle = new Circle();
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
		


		circle.setPhone(rs.getString("PHONE_NO"));
		circle.setTerms1(rs.getString("TERMS1"));
		circle.setTerms2(rs.getString("TERMS2"));
		circle.setTerms3(rs.getString("TERMS3"));
		circle.setTerms4(rs.getString("TERMS4"));
		circle.setRemarks(rs.getString("REMARKS"));
		circle.setSaletax(rs.getString("SALES_TAX_NO"));
		circle.setService(rs.getString("SERVICE_TAX_NO"));
		circle.setCompName(rs.getString("COMPANY_NAME"));
		circle.setTin(rs.getString("TIN_NO"));
		circle.setAddress1(rs.getString("ADDRESS1"));
		circle.setAddress2(rs.getString("ADDRESS2"));
		circle.setFax(rs.getString("FAX_NO"));
		circle.setCst(rs.getString("CST_NO"));
		
		String cstdate="",tindate="",saletaxdate="";
		
		String date=rs.getString("CST_DATE");
		if(date!=null && date.length()>0)
		cstdate= date.substring(5,7)+"/"+date.substring(8)+"/"+date.substring(0,4);
		
		date=rs.getString("SALES_TAX_DATE");
		if(date!=null && date.length()>0)
		saletaxdate= date.substring(5,7)+"/"+date.substring(8)+"/"+date.substring(0,4);
		date=rs.getString("TIN_DATE");
		if(date!=null && date.length()>0)
		tindate= date.substring(5,7)+"/"+date.substring(8)+"/"+date.substring(0,4);
		
		circle.setCstdate(cstdate);
		circle.setSaletaxdate(saletaxdate);
		circle.setTinDate(tindate);
		//logger.info("Executed ....");
		return circle;
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getAllCircles()
	 */
	public ArrayList getAllCircles() throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			logger.info(" Before Connection");
			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_ALL_KEY));
			rs = ps.executeQuery();
			ArrayList<Circle> results = new ArrayList<Circle>();
			while (rs.next()) {
				results.add(populateDto(rs));
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
				query.append(" AND TRUNC(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(CLE.CREATED_DT) <= ? ");
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
				ps.setInt(paramCount++,inputDto.getCircleId());
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
					"SELECT * from ( select a.*,ROWNUM rnum FROM (");
			query.append(queryMap.get(SQL_CIRCLE_SELECT_PARAMETER_BASED_KEY));

			if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				query.append(" AND CLE.CIRCLE_ID = ? ");
			}

			if (inputDto.getStartDt() != null
					&& !inputDto.getStartDt().equals("")) {
				query.append(" AND TRUNC(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(CLE.CREATED_DT) <= ? ");
			}
			query
					.append(" ORDER BY CLE.CIRCLE_NAME )a Where ROWNUM <= ? ) where rnum >= ?");
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
				ps.setInt(paramCount++,inputDto.getCircleId());
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
				query.append(" AND TRUNC(CLE.CREATED_DT) >= ? ");
			}

			if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
				query.append(" AND TRUNC(CLE.CREATED_DT) <= ?");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getActiveCircles()
	 */
	public ArrayList getActiveCircles() throws DAOException {

		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_ACTIVE_KEY));
			rs = ps.executeQuery();
			ArrayList<Circle> activeCircleList = new ArrayList<Circle>();
			while (rs.next()) {
				activeCircleList.add(populateDto(rs));
			}
			if (0 == activeCircleList.size()) {
				logger.error(" No Active Circles found.");
				throw new DAOException(
						ExceptionCode.Circle.ERROR_ACTIVE_CIRCLE_NOT_EXIST);
			}
			return activeCircleList;
		} catch (SQLException e) {
			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Circle.ERROR_ACTIVE_CIRCLE_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getActiveCircleName(int)
	 */
	public String getActiveCircleName(int circleId) throws DAOException {
		logger.info("Started...");
		String circleName = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_CIRCLE_SELECT_ACTIVE_CIRCLE_NAME_KEY));
			ps.setInt(1, circleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				circleName = rs.getString("CIRCLE_NAME");
				logger.info("Circle Name retrieved  is : " + circleName);
			} else {
				logger.error("Circle Name Does not Exist for circleId= "
						+ circleId);
				throw new DAOException(
						ExceptionCode.Circle.ERROR_CIRCLE_NOT_EXIST);
			}
			logger.info("Executed .... ");
			return circleName;
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
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
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#updateCircleAvailableBal(dto)
	 */

	public void reduceCircleAvailableBalance(double transAmount, long updateBy,
			int circleId) throws DAOException {

		logger.info("Started...");
		PreparedStatement ps = null;
		int numRows = -1;
		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_CIRCLE_REDUCE_AVAILABLE_BALANCE_KEY));

			ps.setDouble(1, transAmount);
			ps.setTimestamp(2, Utility.getDateTime());
			ps.setLong(3, updateBy);
			ps.setLong(4, circleId);
			numRows = ps.executeUpdate();
			logger
					.info("u :Update successful on VR_CIRCLE_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed .... ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#updateCircleOpBalRej(dto)
	 */

	public void increaseCircleOperatingBalance(double transAmount,
			long updateBy, int circleId) throws DAOException {

		logger.info("Started... ");
		PreparedStatement ps = null;
		int numRows = -1;

		try {

			ps = connection
					.prepareStatement(queryMap.get(SQL_CIRCLE_INCREASE_OPERATING_BALANCE_KEY));

			ps.setDouble(1, transAmount);
			ps.setTimestamp(2, Utility.getDateTime());
			ps.setLong(3, updateBy);
			ps.setLong(4, circleId);
			numRows = ps.executeUpdate();
			logger
					.info("Update successful on VR_CIRCLE_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed .... ");
	}

	/*
	 * 
	 */
	public int doUpdateCircleBalance(double balance, long updateBy,
			long circleId) throws DAOException {

		logger.info("Started...");
		logger.info("balance :" + balance + ":updateBy:" + updateBy
				+ ":circleId:" + circleId);
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			/*
			 * Get the connection from DBConnection class
			 */
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_BALANCE_KEY));
			int paramCount = 1;
			ps.setDouble(paramCount++, balance);
			ps.setDouble(paramCount++, balance);
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, updateBy);
			ps.setDouble(paramCount++, balance);
			ps.setLong(paramCount, circleId);
			numRows = ps.executeUpdate();
			logger
					.info("Update successful on VR_CIRCLE_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed ....");
		return numRows;
	}

}
