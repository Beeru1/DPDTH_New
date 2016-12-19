package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOActorMstrDao;
import com.ibm.hbo.dto.HBOActorMstr;
import com.ibm.hbo.exception.DAOException;

public class HBOActorMstrDaoImpl implements HBOActorMstrDao {

	public static Logger logger = Logger.getRootLogger();

  
	
	public HBOActorMstr[] getActorName(String roleId) throws DAOException {

		logger.info("Entered getActorName for table:ROLE_MASTER");
		String SQL_SELECT="";
		if (roleId.equalsIgnoreCase("1")){
			SQL_SELECT = "SELECT ROLE_ID,ROLE_NAME FROM ROLE_MASTER where role_id in (1,2,3)";
				}
		if (roleId.equalsIgnoreCase("3")){
			SQL_SELECT = "SELECT ROLE_ID,ROLE_NAME FROM ROLE_MASTER where role_id in (4,5)";
		  	
		}
		if (roleId.equalsIgnoreCase("2")){
			SQL_SELECT = "SELECT ROLE_ID,ROLE_NAME FROM ROLE_MASTER where role_id in (6,7)";
			}
		

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

	protected HBOActorMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			HBOActorMstr dto = new HBOActorMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		HBOActorMstr retValue[] = new HBOActorMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected static void populateDto(HBOActorMstr dto, ResultSet rs) throws SQLException {
		dto.setActorId(rs.getString("ROLE_ID"));
		dto.setActorName(rs.getString("ROLE_NAME"));

	}

}
