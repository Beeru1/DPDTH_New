package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOZoneMstrDao;

import com.ibm.hbo.dto.HBOZoneMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.exception.DAOException;

public class HBOZoneMstrDaoImpl implements HBOZoneMstrDao {

	/** 
		* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	**/

	private static Logger logger = Logger.getLogger(HBOZoneMstrDaoImpl.class.toString());

	protected static final String SQL_INSERT_WITH_ID =
		"INSERT INTO ARC_ZONE_MSTR (ZONE_ID, ZONE_NAME, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, CIRCLE_ID, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	protected static final String SQL_SELECT =
		"SELECT ARC_ZONE_MSTR.ZONE_ID, ARC_ZONE_MSTR.ZONE_NAME, ARC_ZONE_MSTR.CREATED_DT, ARC_ZONE_MSTR.CREATED_BY, ARC_ZONE_MSTR.UPDATED_DT, ARC_ZONE_MSTR.UPDATED_BY, ARC_ZONE_MSTR.CIRCLE_ID, ARC_ZONE_MSTR.STATUS FROM ARC_ZONE_MSTR ";

	protected static final String SQL_SELECT_ZONE =
		"SELECT ZONE_ID, ZONE_NAME,CREATED_DT,CREATED_BY, UPDATED_DT,UPDATED_BY, CIRCLE_ID, STATUS FROM ARC_ZONE_MSTR ";

	protected static final String SQL_UPDATE =
		"UPDATE ARC_ZONE_MSTR SET ZONE_ID = ?, ZONE_NAME = ?, CREATED_DT = ?, CREATED_BY = ?, UPDATED_DT = ?, UPDATED_BY = ?, CIRCLE_ID = ?, STATUS = ? WHERE ZONE_ID = ?";

	protected static final String SQL_DELETE = "DELETE FROM ARC_ZONE_MSTR WHERE ZONE_ID = ?";

	public int insert(HBOZoneMstr dto) throws HBOException {

		logger.info("Entered insert for table ARC_ZONE_MSTR");

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

			ps.setString(paramCount++, dto.getZoneId());
			ps.setString(paramCount++, dto.getZoneName());
			ps.setTimestamp(paramCount++, dto.getCreatedDt());
			ps.setString(paramCount++, dto.getCreatedBy());
			ps.setTimestamp(paramCount++, dto.getUpdatedDt());
			ps.setString(paramCount++, dto.getUpdatedBy());
			ps.setString(paramCount++, dto.getCircleId());
			ps.setString(paramCount++, dto.getStatus());
			rowsUpdated = ps.executeUpdate();
			con.commit();

			logger.info(
				"Row insertion successful on table:ARC_ZONE_MSTR. Inserted:"
					+ rowsUpdated
					+ " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new HBOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new HBOException("Exception: " + e.getMessage(), e);
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

	public HBOZoneMstr findByPrimaryKey(String zoneId) throws HBOException {

		logger.info("Entered findByPrimaryKey for table:ARC_ZONE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = SQL_SELECT + " WHERE ARC_ZONE_MSTR.ZONE_ID = ?";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, zoneId);
			rs = ps.executeQuery();
			return fetchSingleResult(rs);
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new HBOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new HBOException("Exception: " + e.getMessage(), e);
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

	public int update(HBOZoneMstr dto) throws HBOException {

		logger.info("Entered update for table ARC_ZONE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_UPDATE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			if (dto.getZoneId() == null)
				ps.setNull(1, Types.DECIMAL);
			else
				ps.setString(1, dto.getZoneId());
			if (dto.getZoneName() == null)
				ps.setNull(2, Types.VARCHAR);
			else
				ps.setString(2, dto.getZoneName());
			if (dto.getCreatedDt() == null)
				ps.setNull(3, Types.TIMESTAMP);
			else
				ps.setTimestamp(3, dto.getCreatedDt());
			if (dto.getCreatedBy() == null)
				ps.setNull(4, Types.DECIMAL);
			else
				ps.setString(4, dto.getCreatedBy());
			if (dto.getUpdatedDt() == null)
				ps.setNull(5, Types.TIMESTAMP);
			else
				ps.setTimestamp(5, dto.getUpdatedDt());
			if (dto.getUpdatedBy() == null)
				ps.setNull(6, Types.DECIMAL);
			else
				ps.setString(6, dto.getUpdatedBy());
			if (dto.getCircleId() == null)
				ps.setNull(7, Types.DECIMAL);
			else
				ps.setString(7, dto.getCircleId());
			if (dto.getStatus() == null)
				ps.setNull(8, Types.CHAR);
			else
				ps.setString(8, dto.getStatus());
			ps.setString(9, dto.getZoneId());
			numRows = ps.executeUpdate();

			logger.info("Update successful on table:ARC_ZONE_MSTR. Updated:" + numRows + " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new HBOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new HBOException("Exception: " + e.getMessage(), e);
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

	public int delete(String zoneId) throws HBOException {

		logger.info("Entered delete for table ARC_ZONE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_DELETE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, zoneId);
			numRows = ps.executeUpdate();

			logger.info("Delete successful on table:ARC_ZONE_MSTR. Deleted:" + numRows + " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while delete." + "Exception Message: " + e.getMessage());
			throw new HBOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while delete." + "Exception Message: " + e.getMessage());
			throw new HBOException("Exception: " + e.getMessage(), e);
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

	protected HBOZoneMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			HBOZoneMstr dto = new HBOZoneMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOZoneMstr retValue[] = new HBOZoneMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected HBOZoneMstr fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			HBOZoneMstr dto = new HBOZoneMstr();
			populateDto(dto, rs);
			return dto;
		} else
			return null;
	}

	protected static void populateDto(HBOZoneMstr dto, ResultSet rs) throws SQLException {
		dto.setZoneId(rs.getString("ZONE_ID"));

		dto.setZoneName(rs.getString("ZONE_NAME"));

		dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));

		dto.setCreatedBy(rs.getString("CREATED_BY"));

		dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));

		dto.setUpdatedBy(rs.getString("UPDATED_BY"));

		dto.setCircleId(rs.getString("CIRCLE_ID"));

		dto.setStatus(rs.getString("STATUS"));

	}

	public HBOZoneMstr[] getZoneData() throws DAOException {
		logger.info("Entered getZoneData for table:ARC_ZONE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ZONE);
			rs = ps.executeQuery();
			return fetchMultipleResults(rs);
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while getZoneData" + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while getZoneData" + "Exception Message: " + e.getMessage());
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
