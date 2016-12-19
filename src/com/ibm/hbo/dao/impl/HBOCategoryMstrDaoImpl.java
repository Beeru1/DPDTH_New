package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOCategoryMstrDao;
import com.ibm.hbo.dto.HBOCategoryMstr;
import com.ibm.hbo.exception.DAOException;

public class HBOCategoryMstrDaoImpl implements HBOCategoryMstrDao {
	
	
	/** 
		* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	**/

	private static Logger logger = Logger.getLogger(HBOCategoryMstrDaoImpl.class.toString());

	protected static final String SQL_SELECT =
		"SELECT CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESCRIPTION FROM CATEGORY_MASTER order by 1";

	public HBOCategoryMstr[] getCategory() throws DAOException {

		logger.info("Entered getCategory for table:CATEGORY_MASTER");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			return fetchMultipleResults(rs);
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

	protected HBOCategoryMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			HBOCategoryMstr dto = new HBOCategoryMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOCategoryMstr retValue[] = new HBOCategoryMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected static void populateDto(HBOCategoryMstr dto, ResultSet rs) throws SQLException {
		dto.setCategoryId(rs.getString("CATEGORY_ID"));

		dto.setCategoryName(rs.getString("CATEGORY_NAME"));

		dto.setCategoryDescription(rs.getString("CATEGORY_DESCRIPTION"));

	}

}
