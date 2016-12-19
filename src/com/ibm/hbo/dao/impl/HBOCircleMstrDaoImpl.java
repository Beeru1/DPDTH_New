package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOCircleMstrDao;
import com.ibm.hbo.dto.HBOCircleDTO;
import com.ibm.hbo.exception.DAOException;

public class HBOCircleMstrDaoImpl implements HBOCircleMstrDao {

	/**
	 * Logger for this class. Use logger.log(message) for logging. Refer to
	 * 
	 * @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html
	 *       for logging options and configuration.
	 */

	private static Logger logger = Logger.getLogger(HBOCircleMstrDaoImpl.class.toString());

	
	protected static final String SQL_SELECT =
		"SELECT CIRCLE_MASTER.CIRCLE_NAME, CIRCLE_MASTER.CIRCLE_ID FROM CIRCLE_MASTER ";

	protected static final String SQL_SELECT_CIRCLE ="SELECT CIRCLE_NAME, CIRCLE_ID FROM CIRCLE_MASTER ";

	

	public HBOCircleDTO findByPrimaryKey(String circleId) throws DAOException {

		logger.info("Entered findByPrimaryKey for table:CIRCLE_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = SQL_SELECT + " WHERE CIRCLE_MASTER.CIRCLE_ID = ?";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, circleId);
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


	protected HBOCircleDTO[] fetchMultipleResults(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			logger.info(">>>>>IN CIRCLE DAO IMPL");
			HBOCircleDTO dto = new HBOCircleDTO();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOCircleDTO retValue[] = new HBOCircleDTO[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected HBOCircleDTO fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			HBOCircleDTO dto = new HBOCircleDTO();
			populateDto(dto, rs);
			return dto;
		} else
			return null;
	}

	protected static void populateDto(HBOCircleDTO dto, ResultSet rs) throws SQLException {
		dto.setCircleName(rs.getString("CIRCLE_NAME"));

		dto.setCircleId(rs.getString("CIRCLE_ID"));

	}

	public HBOCircleDTO[] getCircleData() throws DAOException {
		logger.info("Entered getCircleData for table:CIRCLE_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_CIRCLE);
			rs = ps.executeQuery();
			return fetchMultipleResults(rs);
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while getCircleData"
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while getCircleData" + "Exception Message: " + e.getMessage());
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
