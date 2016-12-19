/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.SubscriberReleaseDAOInterface;
import com.ibm.virtualization.ussdactivationweb.dto.SubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.exception.VCircleMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseDAO {
	
	private static final Logger logger = Logger
	.getLogger(SubscriberReleaseDAO.class.toString());
	
	public int countReleaseSubs(String releasedDate)throws DAOException{
		
		logger
		.debug("Entering method countReleaseSubs()");
		int noofPages = 0;
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
			prepareStatement = connection
			.prepareStatement(SubscriberReleaseDAOInterface.COUNT_RELEASED_SUB);
			prepareStatement.setString(1,Constants.RELEASED);
			prepareStatement.setString(2,releasedDate);
			
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);
			
			
		}catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countReleaseSubs().",
						e);
			}
		}
		logger
				.debug("Exiting method countReleaseSubs()");
		
		return noofPages;
		
	}
	
	
	public ArrayList getReleaseSubs(String releasedDate , int intLb , int intUb) throws DAOException{
		logger
		.debug("Entering method getReleaseSubs()");
	
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList subLit = new ArrayList();
		SubscriberDTO dto = null;
		String sameMsisdn="";
		try{
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
	
			prepareStatement = connection
			.prepareStatement(SubscriberReleaseDAOInterface.RELEASED_SUB);
			prepareStatement.setString(1,Constants.RELEASED);
			prepareStatement.setString(2,releasedDate);
			prepareStatement.setString(3, String.valueOf(intUb));
			prepareStatement.setString(4, String.valueOf(intLb + 1));
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				if( !(sameMsisdn.equalsIgnoreCase(resultSet.getString("MSISDN"))) )
				{
					dto=new SubscriberDTO();
					dto.setMsisdn(resultSet.getString("MSISDN"));
					dto.setSsim(resultSet.getString("SIM"));
					dto.setStatus(resultSet.getString("STATUS"));
					dto.setRegisteredDate(resultSet.getString("REGISTERED_DATE"));
					dto.setVerifiedDate(resultSet.getString("VERIFIED_DATE"));
					dto.setReleasedDate(resultSet.getString("ACTION_DATE"));
					dto.setRowNum(resultSet.getString("RNUM"));
					subLit.add(dto);
				}else{
					
				}
			}
			
		}catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countReleaseSubs().",
						e);
			}
		}
		logger
				.debug("Exiting method getReleaseSubs()");
		
		return subLit;
		
		
	}

}
