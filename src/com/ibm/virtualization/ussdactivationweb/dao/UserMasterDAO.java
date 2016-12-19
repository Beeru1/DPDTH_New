/**************************************************************************
 * File     : UserMasterDao.java
 * Author   : Pragati
 * Created  : Sep 6, 2008
 * Modified : Sep 6, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
 
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.beans.ModuleBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.VUserMstrDao;
//import com.ibm.virtualization.ussdactivationweb.dto.UserDTO;
import com.ibm.virtualization.ussdactivationweb.exception.VUserMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDException;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;
import com.ibm.virtualization.ussdactivationweb.utils.email.EmailDTO;
import com.ibm.virtualization.ussdactivationweb.utils.email.EmailUtility;

/**
 * This class is responsible for verifing, inserting and updating users.
 * 
 * @author Ashad
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class UserMasterDAO {

	private static final Logger logger = Logger.getLogger(UserMasterDAO.class);

	private static final String CLASSNAME = "com.ibm.virtualization.ussdactivationweb.dao.UserMasterDAO";

	/**
	 * This method implementation should Update The user Login Status
	 * 
	 * @param String
	 *            contains input parameters like loginId, Status.
	 * @return void
	 * @exception USSDException
	 * 
	 */
	public void updateLoginStatus(UserDetailsBean userBean)
	throws USSDException {
		logger.debug("Entering into updateLoginStatus(): ... of UserMasterDAO");
		Connection conn = null;
		PreparedStatement pstmtUpdateLoginStatus = null;
		try {
			conn = DBConnection.getDBConnection();

			pstmtUpdateLoginStatus = conn
			.prepareStatement(VUserMstrDao.UPDATE_LOGIN_STATUS_AND_LAST_ACCESS_TIME);
			pstmtUpdateLoginStatus.setString(1, userBean.getUserIp());
			pstmtUpdateLoginStatus.setString(2, userBean.getLoginStatus());
			pstmtUpdateLoginStatus.setString(3, userBean.getLoginId());
			pstmtUpdateLoginStatus.executeUpdate();

		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, "updateLoginStatus", sqe
					.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, "updateLoginStatus", e
					.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmtUpdateLoginStatus);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting updateLoginStatus(): ... of UserMasterDAO");
	}

	public void updatFirstTimeStatus(UserDetailsBean userBean)
	throws USSDException {
		logger
		.debug("Entering into updatFirstTimeStatus(): ... of UserMasterDAO");
		Connection conn = null;
		PreparedStatement pstmtUpdateLoginStatus = null;
		try {
			conn = DBConnection.getDBConnection();

			pstmtUpdateLoginStatus = conn
			.prepareStatement(VUserMstrDao.UPDATE_FIRST_TIME_LOGIN_STATUS);
			pstmtUpdateLoginStatus.setString(1, userBean.getLoginId());

			pstmtUpdateLoginStatus.executeUpdate();

		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, "updateLoginStatus", sqe
					.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, "updateLoginStatus", e
					.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmtUpdateLoginStatus);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting updatFirstTimeStatus(): ... of UserMasterDAO");
	}

	/**
	 * This method retrieve the user info from database and set in
	 * UserDetailsBean
	 * 
	 * @param String
	 *            contains input parameters like loginId
	 * @return UserDetailsBean
	 * @exception USSDException
	 * 
	 */

	public String getCircleStatus(String strLoginId) throws USSDException {
		logger.debug("Entering into getCircleStatus(): ... of UserMasterDAO");
		Connection conn = null;
		Connection conn1 = null;
		String circleStatus = null;
		String circleCode = null;
		String methodName = "getCircleStatus";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		try {
			conn1 = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			pstmt1 = conn1.prepareStatement(VUserMstrDao.GET_USER_CIRCLE);
			pstmt1.setString(1, strLoginId);
			resultSet1 = pstmt1.executeQuery();
			if (resultSet1.next()) {
				circleCode = resultSet1.getString("CIRCLE_CODE");
			}
			conn = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			pstmt = conn.prepareStatement(VUserMstrDao.GET_CIRCLE_STATUS);
			pstmt.setString(1, circleCode);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				circleStatus = resultSet.getString("STATUS");
			}
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			throw new USSDException(CLASSNAME, methodName, sqe.getMessage(),
					ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, methodName, e.getMessage(),
					ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmt, resultSet);
				DBConnectionUtil.closeDBResources(conn1, pstmt1, resultSet1);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting getCircleStatus(): ... of UserMasterDAO");
		return circleStatus;
	}

	/**
	 * This method retrieve the user info from database and set in
	 * UserDetailsBean
	 * 
	 * @param String
	 *            contains input parameters like strLoginId
	 * @return UserDetailsBean
	 * @exception USSDException
	 * 
	 */
	public UserDetailsBean retrieveUserDetails(String strLoginId,
			String password) throws USSDException {
		logger
		.debug("Entering into retrieveUserDetails(): ... of UserMasterDAO");
		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement retrieveUserDetails = null;
		PreparedStatement retrieveUserDetails1 = null;
		UserDetailsBean userBean = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		try {
			conn = DBConnection.getDBConnection();
			retrieveUserDetails = conn
			.prepareStatement(VUserMstrDao.RETRIEVE_USER_DETAILS);
			strLoginId = strLoginId.trim().toLowerCase();
			retrieveUserDetails.setString(1, strLoginId);
			retrieveUserDetails.setString(2, password);
			resultSet = retrieveUserDetails.executeQuery();
			if (resultSet.next()) {
				userBean = new UserDetailsBean();

				String userType = resultSet.getString("USER_TYPE");
				userBean.setUserId(String.valueOf(resultSet.getInt("USER_ID")));
				userBean.setGroupId(resultSet.getInt("GROUP_ID"));
				userBean.setLoginId(resultSet.getString("LOGIN_ID"));
				userBean.setFirstTimeLogIn(Integer.parseInt(resultSet
						.getString("FIRST_TIME_LOGIN")));
				userBean.setLoginAttempted(resultSet.getInt("LOGIN_ATTEMPTED"));
				userBean.setLoginStatus(resultSet.getString("LOGIN_STATUS"));
				userBean.setZoneCode(resultSet.getInt("ZONE_CODE"));
				int zoneCode= resultSet.getInt("ZONE_CODE");
				userBean.setUserType(userType);
				if (!userType.equals("A")) {
					String circleId = resultSet.getString("CIRCLE_CODE");
					conn1 = DBConnection
					.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
					retrieveUserDetails1 = conn1
					.prepareStatement(VUserMstrDao.RETRIEVE_CIRCLE_DETAILS);

					retrieveUserDetails1.setString(1, circleId);
					resultSet1 = retrieveUserDetails1.executeQuery();
					if (resultSet1.next()) {
						userBean.setCircleName(resultSet1
								.getString("CIRCLE_NAME"));
						userBean.setCircleId(resultSet1
								.getString("CIRCLE_CODE"));
						userBean.setHubCode(resultSet1.getString("HUB_CODE"));
						userBean.setHubName(resultSet1.getString("HUB_NAME"));
					}
				}
				if(zoneCode!=0){
					userBean.setZoneName(getZoneName(userBean.getZoneCode()));
				}
			}
		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, "retrieveUserDetails", sqe
					.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, "retrieveUserDetails", e
					.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, retrieveUserDetails,
						resultSet);
				DBConnectionUtil.closeDBResources(conn1, retrieveUserDetails1,
						resultSet1);
			} catch (SQLException e) {
				throw new USSDException(
						" Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting retrieveUserDetails(): ... of UserMasterDAO");
		return userBean;
	}

	/**
	 * This method implementation Retrieve Menus and return to calling user.
	 * 
	 * @param String
	 *            contains input parameters like strLoginId
	 * @return java.util.ArrayList
	 * @exception USSDException
	 * 
	 */
	public ArrayList retrieveMenu(String strLoginId) throws USSDException {
		logger.debug("Entering into retrieveMenu(): ... of UserMasterDAO");
		Connection conn = null;
		PreparedStatement pstmtMenu = null;
		ArrayList menulist = new ArrayList();
		ResultSet menus = null;
		try {
			conn = DBConnection.getDBConnection();
			pstmtMenu = conn
			.prepareStatement(VUserMstrDao.USERMASTER_VERIFYUSER1);
			 String strLoginId1 = strLoginId.toLowerCase();
			pstmtMenu.setString(1, strLoginId1);
			menus = pstmtMenu.executeQuery();
			ModuleBean mBean = null;
			while (menus.next()) {
				mBean = new ModuleBean();
				mBean.setModuleName(menus.getString("MODULE_NAME"));
				mBean.setModuleUrl(menus.getString("MODULE_URL"));
				mBean.setSection(menus.getString("SECTION"));
				menulist.add(mBean);
			}
		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, "retrieveMenu",
					sqe.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, "retrieveMenu", e.getMessage(),
					ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmtMenu, menus);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting retrieveMenu(): ... of UserMasterDAO");
		return menulist;
	}

	/**
	 * This method update the user password
	 * 
	 * @param int
	 *            contains input parameters like strUserId.
	 * @param String
	 *            contains input parameters pwd
	 * @return int
	 * @exception USSDException
	 * 
	 */
	public int changePassword(String emailId, int strUserId,
			String strPwd) throws USSDException {
		logger.debug("Entering into changePassword(): ... of UserMasterDAO");
		Connection conn = null;
		PreparedStatement pstmtChangePassword = null;
		try {
			conn = DBConnection.getDBConnection();
			pstmtChangePassword = conn
			.prepareStatement(VUserMstrDao.CHANGE_PASSWORD);
			pstmtChangePassword.setString(1, strPwd);
			pstmtChangePassword.setInt(2, strUserId);
			int i = pstmtChangePassword.executeUpdate();

			// Sending mail
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setTo(emailId);
			emailDTO.setFrom(Constants.SENDING_FROM);
			String strSub = Utility.getValueFromBundle(Constants.EMAIL_SUBJECT,
					Constants.EMAIL_RESOURCE_BUNDLE);
			emailDTO.setSubject(strSub);
		 String message = EmailUtility.FormatMsgForChangePwd(emailId);
			
//			String strMessgae = Utility.getValueFromBundle(
//					Constants.EMAIL_MESSAGE_CHANGE_PASSWORD,
//					Constants.EMAIL_RESOURCE_BUNDLE);
			emailDTO.setMessage(message);
			EmailUtility.sendEmail(emailDTO);
			return i;

		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, Constants.CHANGEPASSWORD, sqe
					.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, Constants.CHANGEPASSWORD, e
					.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmtChangePassword);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
			logger.debug("Exiting changePassword(): ... of UserMasterDAO");
		}

	}

	/**
	 * This method insert user old password in V_PASSWORD_HIST table
	 * 
	 * @param int
	 *            contains input parameters like strUserId.
	 * @param String
	 *            contains input parameters password
	 * @return void
	 * @exception USSDException
	 * 
	 */
	public void insertPasswordHistory(int intUserId, String strPassword)
	throws USSDException {
		logger
		.debug("Entering into insertPasswordHistory(): ... of UserMasterDAO");
		Connection conn = null;
		String methodName = Constants.CHANGEPASSWORD;
		PreparedStatement pstmtChangePassword = null;
		String query = "";
		boolean isPrevAutoCommit = true;
		try {
			conn = DBConnection.getDBConnection();
			isPrevAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			query = "{call  PROC_PASSWORD_HISTORY(?,?)}";
			pstmtChangePassword = conn.prepareStatement(query);
			pstmtChangePassword.setInt(1, intUserId);
			pstmtChangePassword.setString(2, strPassword);
			pstmtChangePassword.executeUpdate();
			DBConnectionUtil.closeDBResources(null, pstmtChangePassword);

			
			pstmtChangePassword = conn.prepareStatement(VUserMstrDao.CHANGE_PASSWORD);
			pstmtChangePassword.setString(1, strPassword);
			pstmtChangePassword.setInt(2, intUserId);
			pstmtChangePassword.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException sqe) {
			try {
				if(conn != null) {	
					conn.rollback();
				}
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
			throw new USSDException(CLASSNAME, methodName, sqe.getMessage(),
					ErrorCodes.SQLEXCEPTION);
			
		} catch (Exception e) {
			try {
				if(conn != null) {	
					conn.rollback();
				}
			} catch (SQLException ex) {
				throw new USSDException(
						"Exception in closing database resources.", ex);
			}
			throw new USSDException(CLASSNAME, methodName, e.getMessage(),
					ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				if(conn != null) {
					conn.setAutoCommit(isPrevAutoCommit);
				}
				DBConnectionUtil.closeDBResources(conn, pstmtChangePassword);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting insertPasswordHistory(): ... of UserMasterDAO");
	}

	/*
	 * This method retrive the user current password @param int contains input
	 * parameters like strUserId. @return int @exception USSDException
	 * 
	 */
	public String getCurrentPassword(int strUserId) throws USSDException {
		logger
		.debug("Entering into getCurrentPassword(): ... of UserMasterDAO");
		Connection conn = null;
		PreparedStatement pstmtCheckPassword = null;
		String strPassword = "";
		ResultSet cpassword = null;
		try {
			conn = DBConnection.getDBConnection();
			pstmtCheckPassword = conn
			.prepareStatement(VUserMstrDao.GET_PASSWORD);
			pstmtCheckPassword.setInt(1, strUserId);
			cpassword = pstmtCheckPassword.executeQuery();
			if (cpassword.next()) {
				strPassword = cpassword.getString("PASSWORD");
			}

		} catch (SQLException sqe) {
			throw new USSDException(CLASSNAME, Constants.CHANGEPASSWORD, sqe
					.getMessage(), ErrorCodes.SQLEXCEPTION);
		} catch (Exception e) {
			throw new USSDException(CLASSNAME, Constants.CHANGEPASSWORD, e
					.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmtCheckPassword,
						cpassword);
			} catch (SQLException e) {
				throw new USSDException(
						"Exception in closing database resources.", e);
			}
		}
		logger.debug("Exiting getCurrentPassword(): ... of UserMasterDAO");
		return strPassword;
	}

	/**
	 * This method check whether the given user Exists or not.
	 * 
	 * @param emailId
	 *            ,it is the loginId of user,whose details have to be retrieved
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public void userExists(String loginId, int flag) throws DAOException {
		logger.debug("Into UserDAOImpl:userExists() emailId :" + loginId
				+ " flag :" + flag);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String name = null;
		try {
			connection = DBConnection.getDBConnection();
			preparedStatement = connection
			.prepareStatement(VUserMstrDao.GET_USER_BY_LOGINID);
			preparedStatement.setString(1, loginId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				name = resultSet.getString(1);
				if (name != null && flag == 0) {
					throw new DAOException(ErrorCodes.User.USER_ALREADY_EXISTS);
				}
			}
			if (flag == 1 && name == null) {
				throw new DAOException(ErrorCodes.User.USER_DOES_NOT_EXISTS);
			}

		} catch (SQLException sqe) {
			logger.debug("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.debug("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						preparedStatement, resultSet);
			} catch (SQLException e) {
				logger.debug("Exception in closing database resources.", e);
			}
			logger.debug("Exiting UserDAOImpl:userExists() ... ");
		}
	}

	/**
	 * This method generates new password and update that value in JSP.
	 * 
	 * @param userDTO
	 *            object of UserDTO, holds data of given user
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	/*
	public void sendingEmail(UserDTO userdto) throws DAOException {
		logger.debug("Into UserDAOImpl:forgotPwd() ");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBConnection.getDBConnection();

			// Sending mail
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setTo(userdto.getUserLoginId());
			emailDTO.setFrom(Constants.SENDING_FROM);
			emailDTO.setSubject(userdto.getSubject());
			emailDTO.setMessage(userdto.getMessage());
			EmailUtility.sendEmail(emailDTO);
		} catch (Exception e) {
			logger.debug("Exception");
			throw new DAOException(e.getMessage(), e);
		}
		finally{
			try {
				DBConnectionUtil.closeDBResources(connection, preparedStatement,
						resultSet);
			} catch (SQLException e) {
				logger.debug("Exception in closing database resources.", e);
			}
			logger.debug("Exiting UserDAOImpl:forgotPwd() ... ");
		}
	}
*/
	/**
	 * This method generates new password and update that value in JSP.
	 * 
	 * @param userDTO
	 *            object of UserDTO, holds data of given user
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	/*
	public void forgotPwd(UserDTO userdto) throws DAOException {
		logger.debug("Into UserDAOImpl:forgotPwd() ");
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnection.getDBConnection();
			preparedStatement = connection
			.prepareStatement(VUserMstrDao.FORGOT_PWD);
			preparedStatement.setString(1, userdto.getUserPass());
			preparedStatement.setString(2, userdto.getUserLoginId());
			preparedStatement.executeUpdate();
		} catch (SQLException sqe) {
			logger.debug("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());

		} catch (Exception e) {
			logger.error("Exception");
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			try {
				DBConnectionUtil.closeDBResources(connection, preparedStatement,
						resultSet);
			} catch (SQLException e) {
				logger.error("Exception in closing database resources.", e);
			}
			logger.debug("Exiting UserDAOImpl:forgotPwd() ... ");
		}
	}
	*/
	private static String getZoneName(int zoneCode)
	throws VUserMstrDaoException {
		logger
		.debug("Entering method getCircleName() throws VUserMstrDaoException");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String forward = null;
		try {
			
			conn = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			pStmt = conn.prepareStatement("select LOCATION_NAME from Prodcat.PC_LOCATION_DATA where LOC_DATA_ID= ? ");
			pStmt.setInt(1, zoneCode);
			resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				forward = resultSet.getString("LOCATION_NAME");
			}
			return forward;

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VUserMstrDaoException("SQLException occured : "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception e) {
			logger.error("Exception encountered: " + e.getMessage(), e);
			throw new VUserMstrDaoException("Exception occured: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pStmt, resultSet);
			} catch (SQLException e) {
				throw new VUserMstrDaoException(
						"Exception in closing database resources.", e);
			}
			logger
			.debug("Exiting method getCircleName() throws VUserMstrDaoException");
		}
	}

}
