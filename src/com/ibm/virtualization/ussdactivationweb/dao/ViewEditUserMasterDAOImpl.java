
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.VUserMstrDao;
//import com.ibm.virtualization.ussdactivationweb.dto.ViewEditUserMasterDTO;
import com.ibm.virtualization.ussdactivationweb.exception.VUserMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;


public class ViewEditUserMasterDAOImpl {
	private static final Logger logger = Logger
	.getLogger(ViewEditUserMasterDAOImpl.class);

	/**
	 * This method insert the user information in to the database. 
	 * @param dto : ViewEditUserMasterDTO it holds user information 
	 * @return no of records inserted in the database in int
	 * @throws VUserMstrDaoException
	 */
	/*
	public  int insert(ViewEditUserMasterDTO dto) throws VUserMstrDaoException {

		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		int intRowsUpdated = 0;
		try {
			conn =DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_INSERT_USER_WITH_ID);
			int intParamCount = 1;
			pStmt.setString(intParamCount++,  dto.getUserId());
			pStmt.setString(intParamCount++,  dto.getLoginId());
			pStmt.setString(intParamCount++,  dto.getStatus());
			pStmt.setString(intParamCount++,  dto.getCreatedBy());
			pStmt.setTimestamp(intParamCount++,  dto.getCreatedDt());
			pStmt.setTimestamp(intParamCount++,  dto.getUpdatedDt());
			pStmt.setString(intParamCount++,  dto.getEmailId());
			pStmt.setString(intParamCount++,  dto.getUpdatedBy());
			pStmt.setString(intParamCount++,  dto.getCircleId());
			pStmt.setString(intParamCount++,  dto.getPassword());
			pStmt.setTimestamp(intParamCount++,  dto.getPasswordChangedDt());
			pStmt.setString(intParamCount++,  dto.getUserType());
			pStmt.setString(intParamCount++,  dto.getGroupId());
			pStmt.setString(intParamCount++,  dto.getLoginAttempted());
			intRowsUpdated=pStmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			throw new VUserMstrDaoException("SQLException occured while inserting user data:" , e);
		} catch (Exception e) {
			throw new VUserMstrDaoException("Exception occured while inserting user data. " , e);
		} finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt,resultSet);
			}catch(SQLException e){
				throw new VUserMstrDaoException("Exception in closing database resources.",e);
			}
		}

		return intRowsUpdated;
	}
	
	/**
	 * This method find the user in the database by userid. 
	 * @param strUserId : String, it holds user id 
	 * @return Object of ViewEditUserMasterDTO which contains user information
	 * @throws VUserMstrDaoException
	 **
	public  ViewEditUserMasterDTO findByPrimaryKey(String strUserId) throws VUserMstrDaoException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_SELECT_USER + " WHERE USER_ID = ? WITH UR");
			pStmt.setString(1, strUserId);
			resultSet = pStmt.executeQuery();
			return fetchSingleResult(resultSet);
		} catch (SQLException e) {
			throw new VUserMstrDaoException("SQLException occured while find user: " , e);
		} catch (Exception e) {
			throw new VUserMstrDaoException("Exception occured while find user: " , e);
		} finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt,resultSet);
			}catch(SQLException e){
				throw new VUserMstrDaoException("Exception in closing database resources.",e);
			}
		}

	}
	/**
	 * This method updat the user information by user id. 
	 * @param dto : ViewEditUserMasterDTO, it holds user information  
	 * @return no of records inserted in int value 
	 * @throws VUserMstrDaoException
	 **
	public  int update(ViewEditUserMasterDTO dto) throws VUserMstrDaoException {

		Connection conn = null;
		PreparedStatement pStmt = null;
		int intNumRows = -1;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_UPDATE_USER);
			pStmt.setString(1,  dto.getUserId());
			pStmt.setString(2,  dto.getLoginId());
			pStmt.setString(3,  dto.getStatus());
			pStmt.setString(4,  dto.getCreatedBy());
			pStmt.setTimestamp(5,  dto.getCreatedDt());
			pStmt.setTimestamp(6,  dto.getUpdatedDt());
			pStmt.setString(7,  dto.getEmailId());
			pStmt.setString(8,  dto.getUpdatedBy());
			pStmt.setString(9,  dto.getCircleId());
			pStmt.setString(10,  dto.getPassword());
			pStmt.setTimestamp(11,  dto.getPasswordChangedDt());
			pStmt.setString(12,  dto.getUserType());
			pStmt.setString(13,  dto.getGroupId());
			pStmt.setString(14,  dto.getLoginAttempted());
			pStmt.setString(15,  dto.getUserId());
			intNumRows = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new VUserMstrDaoException("SQLException occured while updating user date :" , e);
		} catch (Exception e) {
			throw new VUserMstrDaoException("Exception occured while updating user date " , e);
		} finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt);
			}catch(SQLException e){
				throw new VUserMstrDaoException("Exception in closing database resources.",e);
			}
		}
		return intNumRows;
	}
	/**
	 * This method delete the user information by user id. 
	 * @param userId : String, it holds user id  
	 * @return no of records deleted  
	 * @throws VUserMstrDaoException
	 **
	public  int delete(String userId) throws VUserMstrDaoException {

		Connection conn = null;
		PreparedStatement pStmt = null;
		int intNumRows = -1;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_DELETE_USER);
			pStmt.setString(1, userId);
			intNumRows = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new VUserMstrDaoException("SQLException occured while deletind user:", e);
		} catch (Exception e) {
			throw new VUserMstrDaoException("Exception occured while deletind user: " , e);
		} finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt);
			}catch(SQLException e){
				throw new VUserMstrDaoException("Exception in closing database resources.",e);
			}
		}
		return intNumRows;
	}
	/**
	 * This method will fetchMultipleResults. 
	 * @param resultSet : ResultSet, it returns the resultset of users  
	 * @return no of records deleted  
	 * @throws SQLException
	 **
	protected  ViewEditUserMasterDTO[] fetchMultipleResults(ResultSet resultSet) throws SQLException {
		ArrayList results = new ArrayList();
		while (resultSet.next()) {
			ViewEditUserMasterDTO dto = new ViewEditUserMasterDTO();
			populateDto(dto, resultSet);
			results.add(dto);
		}
		ViewEditUserMasterDTO retValue[] = new ViewEditUserMasterDTO[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	/**
	 * This method will fetchMultipleResults. 
	 * @param resultSet : ResultSet, it returns the resultset of users  
	 * @return no of records deleted  
	 * @throws SQLException
	 **
	protected  ViewEditUserMasterDTO fetchSingleResult(ResultSet resultSet) throws SQLException {
		if (resultSet.next()) {
			ViewEditUserMasterDTO dto = new ViewEditUserMasterDTO();
			populateDto(dto, resultSet);
			return dto;
		} else 
			return null;
	}
	/**
	 * This method will fetchMultipleResults. 
	 * @param resultSet : ResultSet, it returns the resultset of users 
	 * @param dto : ViewEditUserMasterDTO, it holds the user information
	 * @return no of records deleted  
	 * @throws SQLException
	 **
	protected static void populateDto(ViewEditUserMasterDTO dto, ResultSet resultSet) throws SQLException {
		dto.setUserId(resultSet.getString("USER_ID"));

		dto.setLoginId(resultSet.getString("LOGIN_ID"));

		dto.setStatus(resultSet.getString("STATUS"));

		dto.setCreatedBy(resultSet.getString("CREATED_BY"));

		dto.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));

		dto.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));

		dto.setEmailId(resultSet.getString("EMAIL_ID"));

		dto.setUpdatedBy(resultSet.getString("UPDATED_BY"));

		dto.setCircleId(resultSet.getString("CIRCLE_CODE"));

		dto.setPassword(resultSet.getString("PASSWORD"));

		dto.setPasswordChangedDt(resultSet.getTimestamp("PASSWORD_CHANGED_DT"));

		dto.setUserType(resultSet.getString("USER_TYPE"));

		dto.setGroupId(resultSet.getString("GROUP_ID"));

		dto.setLoginAttempted(resultSet.getString("LOGIN_ATTEMPTED"));

	}



	/**
	 * This method will return the user list. 
	 * @return list of users
	 * @throws VUserMstrDaoException
	 **
	public  ArrayList getUserList() throws VUserMstrDaoException {

		ArrayList arrUserList=new ArrayList();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(VUserMstrDao.SQL_SELECT_USER);
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				ViewEditUserMasterDTO dto = new ViewEditUserMasterDTO();
				dto.setUserId(resultSet.getString("USER_ID"));

				dto.setLoginId(resultSet.getString("LOGIN_ID"));

				dto.setStatus(resultSet.getString("STATUS"));

				dto.setCreatedBy(resultSet.getString("CREATED_BY"));

				dto.setCreatedDt(resultSet.getTimestamp("CREATED_DT"));

				dto.setUpdatedDt(resultSet.getTimestamp("UPDATED_DT"));

				dto.setEmailId(resultSet.getString("EMAIL_ID"));

				dto.setUpdatedBy(resultSet.getString("UPDATED_BY"));

				dto.setCircleId(resultSet.getString("CIRCLE_CODE"));

				dto.setPassword(resultSet.getString("PASSWORD"));

				dto.setPasswordChangedDt(resultSet.getTimestamp("PASSWORD_CHANGED_DT"));

				dto.setUserType(resultSet.getString("USER_TYPE"));

				dto.setGroupId(resultSet.getString("GROUP_ID"));

				dto.setLoginAttempted(resultSet.getString("LOGIN_ATTEMPTED"));

				arrUserList.add(dto);
			}

			return arrUserList;
		} catch (SQLException e) {
			throw new VUserMstrDaoException("SQLException occured while find. " , e);
		} catch (Exception e) {
			throw new VUserMstrDaoException("Exception occured while find. " + e.getMessage(), e);
		} finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt,resultSet);
			}catch(SQLException e){
				throw new VUserMstrDaoException("Exception in closing database resources.",e);
			}
		}

	}  
	/**
	 * This method will return the user list. 
	 * @param strCreatedBy : String; it contains id of creator
	 * @param strUserId : String; it contains id of user
	 * @return it return login-Id
	 * @throws SQLException
	 **
	public  String findCreatedBy(String strCreatedBy,String strUserId) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String results = null;
		try{
			conn = DBConnection.getDBConnection();
			String sqlAll = VUserMstrDao.SQL_USER_LOGIN_ID ;
			sqlAll=sqlAll+" where CREATED_BY="+Integer.parseInt(strCreatedBy) +"and USER_ID="+Integer.parseInt(strUserId);
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sqlAll);
			if(resultSet!=null){	
				while (resultSet.next()){
					results=resultSet.getString(1);
				}  
			}	
			return results;
		}catch (SQLException e){
			throw new SQLException("SQLException has occured; " + e.toString());
		} catch (Exception e){
			throw new SQLException("SQLException has occured while finding user : " + e.toString());
		}finally{
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
				if (conn != null){
					conn.close();
				}
			}catch (Exception e){
				throw new SQLException("SQLException has occured closing database resources." + e.toString());
			}
		}

	}
	/**
	 * This method will return the user list. 
	 * @param updatedBy : String; it contains id of creator
	 * @param strUserId : String; it contains id of user
	 * @return it return login-Id
	 * @throws SQLException
	 **
	public  String findUpdatedBy(String updatedBy,String userId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String results = null;
		try{
			conn = DBConnection.getDBConnection();
			stmt = conn.createStatement();
			String sqlAll = VUserMstrDao.SQL_USER_LOGIN_ID ;
			sqlAll=sqlAll+" where UPDATED_BY="+Integer.parseInt(updatedBy) +"and USER_ID="+Integer.parseInt(userId);
			resultSet = stmt.executeQuery(sqlAll);
			if(resultSet!=null){	
				while (resultSet.next()){
					results=resultSet.getString(1);
				}  
			}	
			return results;
		} catch (SQLException e){
			throw new SQLException("SQLException occured while finding user: " + e.toString());
		} catch (Exception e){
			throw new SQLException("Exception occured while finding user: " + e.toString());
		}finally{
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e){
				throw new SQLException("SQLException occured while closing database resources: " + e.toString());
			}
		}
	}

	
	/**
	 * This method will return the user list. 
	 * @param loginId : String; it contains user loginid 
	 * @return it return login-Id
	 * @throws SQLException
	 **
	public  String checkLoginIdAvailability(String strLoginId) throws SQLException {

		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String status=null;
		try {
			String sql = VUserMstrDao.SQL_SELECT_USER + " where V_USER_MSTR.LOGIN_ID = '" + strLoginId +"'";
			conn = DBConnection.getDBConnection();
			pStmt = conn.prepareStatement(sql);
			resultSet = pStmt.executeQuery();
			status="isAvailable";
			if(resultSet!=null) {	
				while(resultSet.next()){
					if (resultSet.getString("LOGIN_ID").equalsIgnoreCase(strLoginId)){
						status="inUse";

					}
				}            	
			}	
			return status;
		} catch (SQLException e){
			logger.debug("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new SQLException("SQLException occured while validating user." + e.toString());
		}catch (Exception e){
			logger.debug("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new SQLException("Exception occured while validating user." + e.toString());
		}finally {
			try{
				DBConnectionUtil.closeDBResources(conn, pStmt,resultSet);
			}catch(SQLException e){
				throw new SQLException("Exception in closing database resources."+e.toString());
			}
		}  

	}*/


}
