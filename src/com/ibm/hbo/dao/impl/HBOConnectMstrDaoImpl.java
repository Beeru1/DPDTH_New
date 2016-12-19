package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOConnectMstrDao;
import com.ibm.hbo.dto.HBOConnectMstr;
import com.ibm.hbo.exception.DAOException;

public class HBOConnectMstrDaoImpl implements HBOConnectMstrDao {

	/** 
		* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOConnectMstrDaoImpl.class);
	}

	protected static final String SQL_INSERT_WITH_ID =
		"INSERT INTO ARC_CONNECT_MSTR (ARC_ID, ARC_NAME, ARC_ADDRESS, ARC_LOCATION_ID, ARC_DEMOGRAPHY_ID, ARC_OWNER, ARC_SIZE, ARC_PHONE_NO, ARC_EMAIL_ID, ARC_PAYMENT_KIOSK, ARC_VAS_KIOSK, ARC_QMS, SELLING_ABTS, ARC_MANAGER_NAME, ARC_CREATED_DT, ARC_CREATED_BY, ARC_UPDATED_DT, ARC_UPDATED_BY, ZONE_ID, CIRCLE_ID, STATUS, MAX_USERS, TOTAL_NO_USERS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	protected static final String SQL_SELECT =
		"SELECT ARC_ID, ARC_NAME, ARC_ADDRESS, ARC_LOCATION_ID, ARC_DEMOGRAPHY_ID, ARC_OWNER, ARC_SIZE, ARC_PHONE_NO, ARC_EMAIL_ID, ARC_PAYMENT_KIOSK, ARC_VAS_KIOSK, ARC_QMS, SELLING_ABTS, ARC_MANAGER_NAME, ARC_CREATED_DT, ARC_CREATED_BY, ARC_UPDATED_DT, ARC_UPDATED_BY, ZONE_ID, CIRCLE_ID, STATUS, MAX_USERS, TOTAL_NO_USERS FROM ARC_CONNECT_MSTR ";

	protected static final String SQL_UPDATE =
		"UPDATE ARC_CONNECT_MSTR SET ARC_ID = ?, ARC_NAME = ?, ARC_ADDRESS = ?, ARC_LOCATION_ID = ?, ARC_DEMOGRAPHY_ID = ?, ARC_OWNER = ?, ARC_SIZE = ?, ARC_PHONE_NO = ?, ARC_EMAIL_ID = ?, ARC_PAYMENT_KIOSK = ?, ARC_VAS_KIOSK = ?, ARC_QMS = ?, SELLING_ABTS = ?, ARC_MANAGER_NAME = ?, ARC_CREATED_DT = ?, ARC_CREATED_BY = ?, ARC_UPDATED_DT = ?, ARC_UPDATED_BY = ?, ZONE_ID = ?, CIRCLE_ID = ?, STATUS = ?, MAX_USERS = ?, TOTAL_NO_USERS = ? WHERE ARC_ID = ?";

	protected static final String SQL_DELETE = "DELETE FROM ARC_CONNECT_MSTR WHERE ARC_ID = ?";

	public int insert(HBOConnectMstr dto) throws DAOException {

		logger.info("Entered insert for table ARC_CONNECT_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int rowsUpdated = 0;
		try {
			String sql = SQL_INSERT_WITH_ID;
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			int paramCount = 1;

			ps.setString(paramCount++, dto.getArcId());
			ps.setString(paramCount++, dto.getArcName());
			ps.setString(paramCount++, dto.getArcAddress());
			ps.setString(paramCount++, dto.getArcLocationId());
			ps.setString(paramCount++, dto.getArcDemographyId());
			ps.setString(paramCount++, dto.getArcOwner());
			ps.setString(paramCount++, dto.getArcSize());
			ps.setString(paramCount++, dto.getArcPhoneNo());
			ps.setString(paramCount++, dto.getArcEmailId());
			ps.setString(paramCount++, dto.getArcPaymentKiosk());
			ps.setString(paramCount++, dto.getArcVasKiosk());
			ps.setString(paramCount++, dto.getArcQms());
			ps.setString(paramCount++, dto.getSellingAbts());
			ps.setString(paramCount++, dto.getArcManagerName());
			ps.setString(paramCount++, dto.getArcCreatedDt());
			ps.setString(paramCount++, dto.getArcCreatedBy());
			ps.setString(paramCount++, dto.getArcUpdatedDt());
			ps.setString(paramCount++, dto.getArcUpdatedBy());
			ps.setString(paramCount++, dto.getZoneId());
			ps.setString(paramCount++, dto.getCircleId());
			ps.setString(paramCount++, dto.getStatus());
			ps.setString(paramCount++, dto.getMaxUsers());
			ps.setString(paramCount++, dto.getTotalNoUsers());
			rowsUpdated = ps.executeUpdate();
			con.commit();

			logger.info(
				"Row insertion successful on table:ARC_CONNECT_MSTR. Inserted:"
					+ rowsUpdated
					+ " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
			}
		}

		return rowsUpdated;
	}

	public HBOConnectMstr findByPrimaryKey(String arcId) throws DAOException {

		logger.info("Entered findByPrimaryKey for table:ARC_CONNECT_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = SQL_SELECT + " WHERE ARC_CONNECT_MSTR.ARC_ID = ?";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, arcId);
			rs = ps.executeQuery();
			return fetchSingleResult(rs);
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	public int update(HBOConnectMstr dto) throws DAOException {

		logger.info("Entered update for table ARC_CONNECT_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_UPDATE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			if (dto.getArcId() == null)
				ps.setNull(1, Types.DECIMAL);
			else
				ps.setString(1, dto.getArcId());
			if (dto.getArcName() == null)
				ps.setNull(2, Types.VARCHAR);
			else
				ps.setString(2, dto.getArcName());
			if (dto.getArcAddress() == null)
				ps.setNull(3, Types.VARCHAR);
			else
				ps.setString(3, dto.getArcAddress());
			if (dto.getArcLocationId() == null)
				ps.setNull(4, Types.DECIMAL);
			else
				ps.setString(4, dto.getArcLocationId());
			if (dto.getArcDemographyId() == null)
				ps.setNull(5, Types.DECIMAL);
			else
				ps.setString(5, dto.getArcDemographyId());
			if (dto.getArcOwner() == null)
				ps.setNull(6, Types.VARCHAR);
			else
				ps.setString(6, dto.getArcOwner());
			if (dto.getArcSize() == null)
				ps.setNull(7, Types.VARCHAR);
			else
				ps.setString(7, dto.getArcSize());
			if (dto.getArcPhoneNo() == null)
				ps.setNull(8, Types.VARCHAR);
			else
				ps.setString(8, dto.getArcPhoneNo());
			if (dto.getArcEmailId() == null)
				ps.setNull(9, Types.VARCHAR);
			else
				ps.setString(9, dto.getArcEmailId());
			if (dto.getArcPaymentKiosk() == null)
				ps.setNull(10, Types.VARCHAR);
			else
				ps.setString(10, dto.getArcPaymentKiosk());
			if (dto.getArcVasKiosk() == null)
				ps.setNull(11, Types.VARCHAR);
			else
				ps.setString(11, dto.getArcVasKiosk());
			if (dto.getArcQms() == null)
				ps.setNull(12, Types.VARCHAR);
			else
				ps.setString(12, dto.getArcQms());
			if (dto.getSellingAbts() == null)
				ps.setNull(13, Types.VARCHAR);
			else
				ps.setString(13, dto.getSellingAbts());
			if (dto.getArcManagerName() == null)
				ps.setNull(14, Types.VARCHAR);
			else
				ps.setString(14, dto.getArcManagerName());
			if (dto.getArcCreatedDt() == null)
				ps.setNull(15, Types.TIMESTAMP);
			else
				ps.setString(15, dto.getArcCreatedDt());
			if (dto.getArcCreatedBy() == null)
				ps.setNull(16, Types.DECIMAL);
			else
				ps.setString(16, dto.getArcCreatedBy());
			if (dto.getArcUpdatedDt() == null)
				ps.setNull(17, Types.TIMESTAMP);
			else
				ps.setString(17, dto.getArcUpdatedDt());
			if (dto.getArcUpdatedBy() == null)
				ps.setNull(18, Types.DECIMAL);
			else
				ps.setString(18, dto.getArcUpdatedBy());
			if (dto.getZoneId() == null)
				ps.setNull(19, Types.DECIMAL);
			else
				ps.setString(19, dto.getZoneId());
			if (dto.getCircleId() == null)
				ps.setNull(20, Types.DECIMAL);
			else
				ps.setString(20, dto.getCircleId());
			if (dto.getStatus() == null)
				ps.setNull(21, Types.CHAR);
			else
				ps.setString(21, dto.getStatus());
			if (dto.getMaxUsers() == null)
				ps.setNull(22, Types.DECIMAL);
			else
				ps.setString(22, dto.getMaxUsers());
			if (dto.getTotalNoUsers() == null)
				ps.setNull(23, Types.DECIMAL);
			else
				ps.setString(23, dto.getTotalNoUsers());
			ps.setString(24, dto.getArcId());
			numRows = ps.executeUpdate();

			logger.info(
				"Update successful on table:ARC_CONNECT_MSTR. Updated:" + numRows + " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	public int delete(String arcId) throws DAOException {

		logger.info("Entered delete for table ARC_CONNECT_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_DELETE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, arcId);
			numRows = ps.executeUpdate();

			logger.info(
				"Delete successful on table:ARC_CONNECT_MSTR. Deleted:" + numRows + " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while delete." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while delete." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	protected HBOConnectMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			HBOConnectMstr dto = new HBOConnectMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOConnectMstr retValue[] = new HBOConnectMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected HBOConnectMstr fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			HBOConnectMstr dto = new HBOConnectMstr();
			populateDto(dto, rs);
			return dto;
		} else
			return null;
	}

	protected static void populateDto(HBOConnectMstr dto, ResultSet rs) throws SQLException {
		dto.setArcId(rs.getString("ARC_ID"));

		dto.setArcName(rs.getString("ARC_NAME"));

		dto.setArcAddress(rs.getString("ARC_ADDRESS"));

		dto.setArcLocationId(rs.getString("ARC_LOCATION_ID"));

		dto.setArcDemographyId(rs.getString("ARC_DEMOGRAPHY_ID"));

		dto.setArcOwner(rs.getString("ARC_OWNER"));

		dto.setArcSize(rs.getString("ARC_SIZE"));

		dto.setArcPhoneNo(rs.getString("ARC_PHONE_NO"));

		dto.setArcEmailId(rs.getString("ARC_EMAIL_ID"));

		dto.setArcPaymentKiosk(rs.getString("ARC_PAYMENT_KIOSK"));

		dto.setArcVasKiosk(rs.getString("ARC_VAS_KIOSK"));

		dto.setArcQms(rs.getString("ARC_QMS"));

		dto.setSellingAbts(rs.getString("SELLING_ABTS"));

		dto.setArcManagerName(rs.getString("ARC_MANAGER_NAME"));

		dto.setArcCreatedDt(rs.getString("ARC_CREATED_DT"));

		dto.setArcCreatedBy(rs.getString("ARC_CREATED_BY"));

		dto.setArcUpdatedDt(rs.getString("ARC_UPDATED_DT"));

		dto.setArcUpdatedBy(rs.getString("ARC_UPDATED_BY"));

		dto.setZoneId(rs.getString("ZONE_ID"));

		dto.setCircleId(rs.getString("CIRCLE_ID"));

		dto.setStatus(rs.getString("STATUS"));

		dto.setMaxUsers(rs.getString("MAX_USERS"));

		dto.setTotalNoUsers(rs.getString("TOTAL_NO_USERS"));

	}

	public HBOConnectMstr[] getConnectData() throws DAOException {
		logger.info("Entered getConnectData for table:ARC_CONNECT_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			return fetchMultipleResults(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while getConnectData"
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while getConnectData" + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

}
