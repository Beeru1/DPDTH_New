/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **Created On 30-aug-2007
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ibm.db2.jcc.b.SqlException;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Link;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.RechargeTransCircleDetail;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
/**
 * This class provides the implementation for all the method declarations in
 * LoginDao interface
 * 
 * @author Anju
 * 
 */
public class LoginDaoRdbms extends BaseDaoRdbms implements LoginDao {

	/* Logger for class LoginDaoRdbms. */

	protected static Logger logger = Logger.getLogger(LoginDaoRdbms.class
			.getName());

	/* SQL Used within DaoImpl */
	protected static final String SQL_INSERT_LOGIN_DETAIL_HIST = DBQueries.SQL_INSERT_LOGIN_DETAIL_HIST;
	
	protected final static String SQL_LOGIN_SELECT_ON_ID_KEY = "SQL_LOGIN_SELECT_ON_ID";
	protected static String SQL_LOGIN_SELECT_ON_ID = " SELECT TYPE, GROUP_ID FROM VR_LOGIN_MASTER WHERE LOGIN_ID = ? AND STATUS = '"
			+ Constants.ACTIVE_STATUS + "'";

	protected final static String SQL_LOGIN_SELECT_SEQID_KEY = "SQL_LOGIN_SELECT_SEQID";
	protected static String SQL_LOGIN_SELECT_SEQID = "SELECT SEQ_VR_LOGIN_MASTER.NEXTVAL FROM DUAL ";

	protected final static String SQL_LOGIN_INSERT_KEY = "SQL_LOGIN_INSERT";
	//protected static String SQL_LOGIN_INSERT = "INSERT INTO VR_LOGIN_MASTER (LOGIN_ID, LOGIN_NAME, PASSWORD, TYPE, STATUS, LOGIN_ATTEMPTED, GROUP_ID, IPWD_CHANGED_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	protected static String SQL_LOGIN_INSERT = "INSERT INTO VR_LOGIN_MASTER (LOGIN_ID, LOGIN_NAME, PASSWORD, TYPE, STATUS, LOGIN_ATTEMPTED, GROUP_ID, IPWD_CHANGED_DT) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP-?)";
	
	protected final static String SQL_LOGIN_UPDATE_KEY = "SQL_LOGIN_UPDATE";
	protected static String SQL_LOGIN_UPDATE = "UPDATE VR_LOGIN_MASTER SET TYPE = ?, GROUP_ID = ?, STATUS = ? WHERE LOGIN_ID = ?";

	// for insertion of use based on account code
	protected final static String SQL_ACC_INSERT_KEY = "SQL_ACC_INSERT";
	protected static String SQL_ACC_INSERT = "INSERT INTO VR_LOGIN_MASTER (LOGIN_ID, LOGIN_NAME,GROUP_ID, PASSWORD, TYPE, STATUS,IPWD_CHANGED_DT,LOGIN_ATTEMPTED,M_PASSWORD) VALUES (?, ?, ?, ?, ?,?,sysdate-?,?,?)";

	// for updating user information based on account
	protected final static String SQL_ACC_UPDATE_KEY = "SQL_ACC_UPDATE";
	protected static String SQL_ACC_UPDATE = "UPDATE VR_LOGIN_MASTER SET LOGIN_NAME=?,STATUS=?,GROUP_ID=? WHERE  LOGIN_ID=? ";

	// For Selecting User Context
	protected final static String SQL_SELECT_USER_CONTEXT_KEY = "SQL_SELECT_USER_CONTEXT";
	protected static String SQL_SELECT_USER_CONTEXT = " SELECT VR_LOGIN_MASTER.LOGIN_ID,VR_LOGIN_MASTER.LOGIN_NAME,VR_LOGIN_MASTER.TYPE,VR_LOGIN_MASTER.GROUP_ID,VR_ACCOUNT_DETAILS.CIRCLE_ID  FROM VR_LOGIN_MASTER,VR_ACCOUNT_DETAILS WHERE VR_ACCOUNT_DETAILS.ACCOUNT_ID = VR_LOGIN_MASTER.LOGIN_ID AND VR_LOGIN_MASTER.LOGIN_ID =? AND VR_LOGIN_MASTER.STATUS = '"
			+ Constants.ACTIVE_STATUS + "'";

	// //For Selecting User Context FOR mOBILE USER
	// protected static String SQL_SELECT_USERCONTEXT_MUSER = " SELECT
	// VR_LOGIN_MASTER.LOGIN_ID,VR_LOGIN_MASTER.LOGIN_NAME,VR_LOGIN_MASTER.TYPE,VR_LOGIN_MASTER.GROUP_ID,VR_ACCOUNT_DETAILS.CIRCLE_ID,VR_LOGIN_MASTER.STATUS,VR_ACCOUNT_DETAILS.OPENING_DT
	// FROM VR_LOGIN_MASTER,VR_ACCOUNT_DETAILS WHERE
	// VR_ACCOUNT_DETAILS.MOBILE_NUMBER = ? AND VR_ACCOUNT_DETAILS.ACCOUNT_ID =
	// VR_LOGIN_MASTER.LOGIN_ID AND VR_LOGIN_MASTER.M_PASSWORD = ?";
	protected final static String SQL_SELECT_USERCONTEXT_MUSER_KEY = "SQL_SELECT_USERCONTEXT_MUSER";
	protected static String SQL_SELECT_USERCONTEXT_MUSER = " SELECT VR_LOGIN_MASTER.LOGIN_ID,VR_LOGIN_MASTER.LOGIN_NAME,VR_LOGIN_MASTER.TYPE,VR_LOGIN_MASTER.GROUP_ID,VR_ACCOUNT_DETAILS.ACCOUNT_ADDRESS,VR_ACCOUNT_DETAILS.CIRCLE_ID,VR_LOGIN_MASTER.STATUS,VR_ACCOUNT_DETAILS.BILLABLE_ACC_ID,VR_ACCOUNT_DETAILS.OPENING_DT,VR_CIRCLE_MASTER.STATUS as CIRCLE_STATUS,VR_CIRCLE_MASTER.CIRCLE_CODE as CIRCLE_CODE FROM VR_LOGIN_MASTER,VR_ACCOUNT_DETAILS,VR_CIRCLE_MASTER WHERE VR_ACCOUNT_DETAILS.MOBILE_NUMBER = ? AND  VR_ACCOUNT_DETAILS.ACCOUNT_ID = VR_LOGIN_MASTER.LOGIN_ID AND VR_CIRCLE_MASTER.CIRCLE_ID = VR_ACCOUNT_DETAILS.CIRCLE_ID AND VR_LOGIN_MASTER.M_PASSWORD = ?";

	// For validating the external web user.
	protected final static String SQL_AUTHENTICATE_USER_KEY = "SQL_AUTHENTICATE_USER";
	protected static String SQL_AUTHENTICATE_USER = "SELECT VR_LOGIN_MASTER.LOGIN_ID, VR_LOGIN_MASTER.LOGIN_NAME, VR_LOGIN_MASTER.TYPE, VR_LOGIN_MASTER.GROUP_ID, VR_ACCOUNT_DETAILS.CIRCLE_ID,VR_ACCOUNT_DETAILS.OPENING_DT FROM VR_LOGIN_MASTER, VR_ACCOUNT_DETAILS WHERE VR_LOGIN_MASTER.LOGIN_ID = ? AND  VR_LOGIN_MASTER.PASSWORD = ? AND VR_LOGIN_MASTER.STATUS = '"
			+ Constants.ACTIVE_STATUS
			+ "' AND  VR_ACCOUNT_DETAILS.ACCOUNT_ID = VR_LOGIN_MASTER.LOGIN_ID ";

	/* QUERY TO UPDATE PASSWORD */
	protected final static String SQL_LOGIN_UPDATE_PASSWORD_KEY = "SQL_LOGIN_UPDATE_PASSWORD";
	protected static String SQL_LOGIN_UPDATE_PASSWORD = " UPDATE VR_LOGIN_MASTER SET PASSWORD = ?, IPWD_CHANGED_DT = ?,LOGIN_ATTEMPTED=0 WHERE PASSWORD = ? AND LOGIN_ID = ?";

	protected final static String SQL_LOGIN_RESET_IPASSWORD_KEY = "SQL_LOGIN_RESET_IPASSWORD"; 
	protected static String SQL_LOGIN_RESET_IPASSWORD = " UPDATE VR_LOGIN_MASTER SET PASSWORD = ?, IPWD_CHANGED_DT = (sysdate-?) WHERE PASSWORD = ? AND LOGIN_ID = ?";
	
	
	/* QUERY TO CREATE PASSWORD HISTORY */

	// protected static String SQL_VR_PASSWORD_HISTORY_INSERT = " INSERT INTO
	// VR_PASSWORD_HISTORY (HISTORY_ID , OLDPASSWORD ,LOGIN_ID ,
	// PASSWORD_CHANGE_DT ) VALUES (SEQ_VR_PASSWORD_HISTORY.NEXTVAL ,?, ?, ? )";
	/* QUERY TO UPDATE M-PASSWORD */
	protected final static String SQL_LOGIN_UPDATE_M_PASSWORD_KEY = "SQL_LOGIN_UPDATE_M_PASSWORD";
	protected static String SQL_LOGIN_UPDATE_M_PASSWORD = " UPDATE VR_LOGIN_MASTER SET M_PASSWORD = ? WHERE M_PASSWORD=? AND  LOGIN_ID = ? ";
	
	protected final static String SQL_LOGIN_RESET_M_PASSWORD_KEY = "SQL_LOGIN_RESET_M_PASSWORD";
	protected static String SQL_LOGIN_RESET_M_PASSWORD = " UPDATE VR_LOGIN_MASTER SET M_PASSWORD=? WHERE  LOGIN_ID = ?";

	protected final static String SQL_CHECK_CHILD_STATUS_KEY = "SQL_CHECK_CHILD_STATUS";
	protected static String SQL_CHECK_CHILD_STATUS = "SELECT LOGIN_ID, STATUS FROM VR_LOGIN_MASTER WHERE LOGIN_ID IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE PARENT_ACCOUNT = ?  and ACCOUNT_ID != ?) AND STATUS = '"
			+ Constants.ACTIVE_STATUS + "'";
	
	/* Added By Parnika */
	
	protected final static String SQL_CHECK_STATUS_KEY = "SQL_CHECK_STATUS";
	protected static String SQL_CHECK_STATUS = "SELECT STATUS from VR_LOGIN_MASTER where LOGIN_ID = ? with ur";
	
	/* End of changes by Parnika */

	protected final static String SQL_CHECK_PARENT_STATUS_KEY = "SQL_CHECK_PARENT_STATUS";
	protected static String SQL_CHECK_PARENT_STATUS = "SELECT LOGIN.LOGIN_ID FROM VR_ACCOUNT_DETAILS ACCOUNT, VR_LOGIN_MASTER LOGIN WHERE ACCOUNT.ACCOUNT_ID = ? AND ( LOGIN.LOGIN_ID = ACCOUNT.PARENT_ACCOUNT OR (ACCOUNT.ACCOUNT_ID = LOGIN.LOGIN_ID AND ACCOUNT.PARENT_ACCOUNT IS NULL) ) AND LOGIN.STATUS = '"
			+ Constants.INACTIVE_STATUS + "'"; //not used

	/* Query to load Role-Link mappings */
	protected final static String SQL_ROLE_LINK_MAP_KEY = "SQL_ROLE_LINK_MAP";
	protected final static String SQL_ROLE_LINK_MAP = "SELECT DISTINCT rm.ROLE_NAME,l.LINK_ID,l.LINK_URL,l.LINK_LABEL,topl.LINK_NAME AS TOP_LINK_NAME ,l.order_ID  "
			+ " FROM VR_ROLE_MASTER rm, VR_LINK_ROLE_MAPPING lrMap, VR_LINK_MASTER l, VR_LINK_MASTER topl "
			+ " WHERE rm.ROLE_ID=lrMap.ROLE_ID "
			+ " AND lrMap.LINK_ID=l.LINK_ID "
			+ " AND l.TOP_LINK_ID= topl.LINK_ID ORDER BY l.ORDER_ID , l.LINK_ID";
			
	protected final static String SQL_USER__CHECK_LOCKED= "SELECT LOGIN_ATTEMPTED FROM VR_LOGIN_MASTER WHERE  LOGIN_ID = ? with ur"; //Neetika
//	Query to unlock the locked user 
											//Query updated to resolve the login issue @ CR58299								
  //protected static final String SQL_USER_UPDATE_UNLOCK="UPDATE VR_LOGIN_MASTER SET LOGIN_ATTEMPTED=0 WHERE LOGIN_ATTEMPTED=3 AND LOGIN_ID = ?";
	protected static final String SQL_USER_UPDATE_UNLOCK="UPDATE VR_LOGIN_MASTER SET LOGIN_ATTEMPTED=0 WHERE LOGIN_ATTEMPTED >= 3 AND LOGIN_ID = ?";
	protected static final String SQL_USER_UPDATE_INACTIVE="UPDATE VR_LOGIN_MASTER SET STATUS=? WHERE LOGIN_ID = ?";
	
	protected final static String SQL_SELECT_SOURCE_CIRCLE_KEY = "SQL_SELECT_SOURCE_CIRCLE";
	protected static String SQL_SELECT_SOURCE_CIRCLE = " SELECT VR_ACCOUNT_DETAILS.CIRCLE_ID,VR_ACCOUNT_DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_CODE as CIRCLE_CODE FROM VR_ACCOUNT_DETAILS,VR_CIRCLE_MASTER CIRCLE WHERE VR_ACCOUNT_DETAILS.MOBILE_NUMBER = ? AND CIRCLE.CIRCLE_ID=VR_ACCOUNT_DETAILS.CIRCLE_ID ";
											//Query updated to resolve the password change issue @ CR58299
	//protected final static String updateChngdPass="update VR_LOGIN_MASTER SET PASSWORD=?,IPWD_CHANGED_DT=(current timestamp - 45 days) where LOGIN_ID=? ";
	
	protected final static String updateChngdPass="update VR_LOGIN_MASTER SET PASSWORD=?,IPWD_CHANGED_DT= current timestamp  where LOGIN_ID=? ";
	
	protected static final String SQL_INSERT_ACC_DETAIL_HIST_IT_HELP = DBQueries.SQL_INSERT_ACC_DETAIL_HIST_IT_HELP;
	
	/**
	 * Default Constructor
	 * 
	 */

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public LoginDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_SELECT_SOURCE_CIRCLE_KEY, SQL_SELECT_SOURCE_CIRCLE);
		queryMap.put(SQL_LOGIN_SELECT_ON_ID_KEY, SQL_LOGIN_SELECT_ON_ID);
		queryMap.put(SQL_LOGIN_SELECT_SEQID_KEY, SQL_LOGIN_SELECT_SEQID);
		queryMap.put(SQL_LOGIN_INSERT_KEY, SQL_LOGIN_INSERT);
		queryMap.put(SQL_LOGIN_UPDATE_KEY, SQL_LOGIN_UPDATE);
		queryMap.put(SQL_ACC_INSERT_KEY, SQL_ACC_INSERT);
		queryMap.put(SQL_ACC_UPDATE_KEY, SQL_ACC_UPDATE);
		queryMap.put(SQL_SELECT_USER_CONTEXT_KEY, SQL_SELECT_USER_CONTEXT);
		queryMap.put(SQL_SELECT_USERCONTEXT_MUSER_KEY,
				SQL_SELECT_USERCONTEXT_MUSER);
		queryMap.put(SQL_AUTHENTICATE_USER_KEY, SQL_AUTHENTICATE_USER);
		queryMap.put(SQL_LOGIN_UPDATE_PASSWORD_KEY, SQL_LOGIN_UPDATE_PASSWORD);
		queryMap.put(SQL_LOGIN_UPDATE_M_PASSWORD_KEY,
				SQL_LOGIN_UPDATE_M_PASSWORD);
		queryMap.put(SQL_CHECK_CHILD_STATUS_KEY, SQL_CHECK_CHILD_STATUS);
		queryMap.put(SQL_CHECK_PARENT_STATUS_KEY, SQL_CHECK_PARENT_STATUS);
		queryMap.put(SQL_ROLE_LINK_MAP_KEY, SQL_ROLE_LINK_MAP);
		queryMap.put(SQL_LOGIN_RESET_M_PASSWORD_KEY, SQL_LOGIN_RESET_M_PASSWORD);
		queryMap.put(SQL_LOGIN_RESET_IPASSWORD_KEY, SQL_LOGIN_RESET_IPASSWORD);
		queryMap.put(SQL_CHECK_STATUS_KEY, SQL_CHECK_STATUS);		
		
			
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#updateAccountUser(com.ibm.virtualization.recharge.dto.User)
	 */

	public void updateAccountUser(long accountId, String accountCode,
			String status, int groupId, String parentCode,Timestamp currentTime) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rowsUpdated = 0;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			// to deactivate the user account check if any of the child account
			// is active
			if (!(Constants.ACTIVE_STATUS.equalsIgnoreCase(status))) {
				
				/* Added by Parnika for not checking Inactive Users */
				
				ps1  = connection.prepareStatement(queryMap.get(SQL_CHECK_STATUS_KEY));
				ps1.setLong(1, accountId);
				rs1 = ps1.executeQuery();
				
				if(rs1.next()){
					if(rs1.getString("STATUS").equalsIgnoreCase(Constants.ACTIVE_STATUS)){
						ps = connection.prepareStatement(queryMap.get(SQL_CHECK_CHILD_STATUS_KEY));
						ps.setLong(1, accountId);
						ps.setLong(2, accountId);
						rs = ps.executeQuery();
						if (rs.next()) {
							logger
									.info(accountId
											+ " Account tried to deactivate while child account is active. ");
							throw new DAOException(
									ExceptionCode.Account.ERROR_ACCOUNT_CHILD_ACTIVE);
						}
					}
				}
			}
			//*** Commented by Shilpa on 31-1-2013 for not checking Active status of parent while updating status of user
			/*else {
				if (null != parentCode
						&& (!parentCode.trim().equalsIgnoreCase(""))) {
					// to activate the user account check if parent account is
					// not
					// in-active
					ps = connection.prepareStatement(queryMap.get(SQL_CHECK_PARENT_STATUS_KEY));
					ps.setLong(1, accountId);
					rs = ps.executeQuery();
					if (rs.next()) {
						logger.info(accountId
										+ " Account tried to activate while parent account is In-active. ");
						throw new DAOException(
								ExceptionCode.Account.ERROR_ACCOUNT_PARENT_INACTIVE);
					}
				}
			}*/
//			*** Commented by Shilpa on 31-1-2013 for not checking Active status of parent while updating status of user
			// Ends here
			
			ps = null;
			ps = connection.prepareStatement(queryMap.get(SQL_ACC_UPDATE_KEY));

			ps.setString(1, accountCode);
			ps.setString(2, status);
			ps.setInt(3, groupId);
			ps.setLong(4, accountId);
			rowsUpdated = ps.executeUpdate();
			if (rowsUpdated == 0) {
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}else{
				if(groupId !=8 && groupId !=9 ){
					insertLoginHistDetails(accountId,currentTime);
				}
			}
			logger.info("Row Updated successfully on table:. updated:"
					+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.error("SQL Exception occured while updating. table "
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Account.ERROR_ACCOUNT_ALREADY_USED);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}
	public void insertLoginHistDetails(long accountId,Timestamp currentTime) throws DAOException {
		
		PreparedStatement ps = null;
		
		try {
				
				String sql=SQL_INSERT_LOGIN_DETAIL_HIST.replace("?2", "timestamp('"+currentTime+"')");
				logger.info(sql);
				ps = connection.prepareStatement(sql);
				//ps.setTimestamp(1, currentTime);
				ps.setLong(1, accountId);
				ps.executeUpdate();
			
			}
		catch (Exception e) {
			
			e.printStackTrace();
				
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		finally
		{
			DBConnectionManager.releaseResources(ps, null);
		}
		
	}
	
	public void insertHistDetails(long accountId,Timestamp currentTime) throws DAOException {
		
		PreparedStatement ps = null;

		try {
		
			String sql=SQL_INSERT_ACC_DETAIL_HIST_IT_HELP.replace("?2", "timestamp('"+currentTime+"')");	
			ps = connection.prepareStatement(sql);
				//ps.setTimestamp(1, currentTime);
				ps.setLong(1, accountId);
				ps.executeUpdate();
			
			}
		catch (Exception e) {
			
			e.printStackTrace();
				
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		finally
		{
			DBConnectionManager.releaseResources(ps, null);
		}
		
		
			
		}
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#insertAccountUser(com.ibm.virtualization.recharge.dto.Login, int)
	 */

	public long insertAccountUser(Login login,Timestamp currentTime) throws DAOException {
		logger.info(" Started..." + login );
		PreparedStatement ps = null;
		ResultSet rs = null;
		long loginId;
		try {
			
			//getting no. of password expiry days to expired date  so if user login first time then it directly goes to change password
			int numberExpDays =Integer.parseInt(ResourceReader.getValueFromBundle(Constants.PWD_EXPIRY_LIMIT ,Constants.GSD_RESOURCE_BUNDLE));
			logger.info(" numberExpDays :-" + numberExpDays );
			
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_SELECT_SEQID_KEY));
			rs = ps.executeQuery();
			// getting sequence id before insert that will be used as ID
			if (rs.next()) {
				loginId = rs.getLong("NEXTVAL");
			} else {
				throw new DAOException(ExceptionCode.User.ERROR_USER_SEQ_ID);
			}
			
			// clear parameter for insert paraent table information
			ps.clearParameters();
			logger.info("Inserted row in  table VR_LOGIN_MASTER where login id"+ loginId);
			ps = connection.prepareStatement(queryMap.get(SQL_ACC_INSERT_KEY));
			
			int paramCount = 1;
			ps.setLong(paramCount++, loginId);
			String loginName=login.getLoginName();
			
			if(login.getGroupId()==9) 
				ps.setString(paramCount++, "RET"+loginId);
			else if(login.getGroupId()==8) 
				ps.setString(paramCount++, "FSE"+loginId);
			else
				ps.setString(paramCount++, loginName);
			
			ps.setLong(paramCount++, login.getGroupId());
			//added by aman for generating password for finance user
			if(login.getGroupId() == Constants.FINANCE_USER_ID)
			{
				ps.setString(paramCount++, login.getPassword());
			}
			else if(login.getGroupId() >= Constants.FSE_ID && login.getHeiarachyId().equalsIgnoreCase("1")){
				ps.setString(paramCount++, null);
			}else{
				ps.setString(paramCount++, login.getPassword());
			}
			ps.setString(paramCount++, Constants.USER_TYPE_EXTERNAL);
			/* Default status is active for insert */
			ps.setString(paramCount++, login.getStatus());
			ps.setInt(paramCount++,numberExpDays);
			ps.setLong(paramCount++, login.getLoginAttempted());
			ps.setString(paramCount, null ); //login.getMPassword());
			
			ps.executeUpdate();
			if(login.getGroupId() !=8 && login.getGroupId() !=9 ){
				insertLoginHistDetails(loginId,currentTime);
			}
			
			connection.commit();
			logger.info(" Level - 1 : VR_LOGIN_MASTER Details inserted  id is "+ loginId);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error(" SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Account.ERROR_ACCOUNT_ALREADY_USED);
		
				
			
		} finally {
			/* Close the statement,resultset */

			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ... Row insertion successful  ");

		return loginId;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#insertAccountUser(com.ibm.virtualization.recharge.dto.Login, int)
	 */

	public long insertIdHelpAccountUser(Login login,Timestamp currentTime) throws DAOException {
		logger.info(" Started...insertIdHelpAccountUser " + login );
		PreparedStatement ps = null;
		ResultSet rs = null;
		long loginId;
		try {
			
			//getting no. of password expiry days to expired date  so if user login first time then it directly goes to change password
			int numberExpDays =Integer.parseInt(ResourceReader.getValueFromBundle(Constants.PWD_EXPIRY_LIMIT ,Constants.GSD_RESOURCE_BUNDLE));
			logger.info(" numberExpDays :-" + numberExpDays );
			
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_SELECT_SEQID_KEY));
			rs = ps.executeQuery();
			// getting sequence id before insert that will be used as ID
			if (rs.next()) {
				loginId = rs.getLong("NEXTVAL");
			} else {
				throw new DAOException(ExceptionCode.User.ERROR_USER_SEQ_ID);
			}
			// clear parameter for insert paraent table information
			ps.clearParameters();
			logger
					.info("Inserted row in  table VR_LOGIN_MASTER where login id"
							+ loginId);
			ps = connection.prepareStatement(queryMap.get(SQL_ACC_INSERT_KEY));
			int paramCount = 1;
			ps.setLong(paramCount++, loginId);
			String loginName=login.getLoginName();
			
			ps.setString(paramCount++, loginName);
		//	logger.info(" User password is "	+ login.getPassword());
			ps.setLong(paramCount++, login.getGroupId());
			ps.setString(paramCount++, login.getPassword());
			ps.setString(paramCount++, Constants.USER_TYPE_EXTERNAL);
			/* Default status is active for insert */
			ps.setString(paramCount++, login.getStatus());
			ps.setInt(paramCount++,numberExpDays);
			ps.setLong(paramCount++, login.getLoginAttempted());
			ps.setString(paramCount, null ); //login.getMPassword());
			ps.executeUpdate();
			
			insertLoginHistDetails(loginId,currentTime);
			connection.commit();
			logger.info(" Level - 1 : VR_LOGIN_MASTER Details inserted  id is "	+ loginId);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error(" SQL Exception occured while inserting."+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Account.ERROR_ACCOUNT_ALREADY_USED);
		
		} finally {
			/* Close the statement,resultset */

			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info("Executed ..insertIdHelpAccountUser. Row insertion successful  ");

		return loginId;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#insertUser(com.ibm.virtualization.recharge.dto.User, int)
	 */
	public long insertUser(User user) throws DAOException {
		logger.info(" Started..." + user);
		PreparedStatement ps = null;
		ResultSet rs = null;
		long loginId;
	try {
		  //getting no. of password expiry days to expired date  so if user login first time then it directly goes to change password
		  	int numberExpDays =Integer.parseInt(ResourceReader.getValueFromBundle(Constants.PWD_EXPIRY_LIMIT ,Constants.GSD_RESOURCE_BUNDLE));
		  	logger.info(" numberExpDays :-" + numberExpDays );
		  
		    ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_SELECT_SEQID_KEY));

			rs = ps.executeQuery();
			// getting sequence id before insert that will be used as ID
			// for both parent and child tables
			if (rs.next()) {
				loginId = rs.getLong("NEXTVAL");
			} else {
				throw new DAOException(ExceptionCode.User.ERROR_USER_SEQ_ID);
			}
			// clear parameter for insert paraent table information
			ps.clearParameters();
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_INSERT_KEY));
			int paramCount = 1;
			ps.setLong(paramCount++, loginId);
			ps.setString(paramCount++, user.getLoginName());
			ps.setString(paramCount++, user.getPassword());
			ps.setString(paramCount++, user.getType());
			/* Default status is active for insert */
			ps.setString(paramCount++, "A");
			ps.setInt(paramCount++, user.getLoginAttempted());
			ps.setInt(paramCount++, user.getGroupId());
			//ps.setTimestamp(paramCount, Utility.getDateTime());
			ps.setInt(paramCount, numberExpDays);
			ps.executeUpdate();
			logger.info("Level - 1 : VR_LOGIN_MASTER Details Inserted ");
			/*
			 * * Commit only after inserting details in VR_USER_DETAILS or
			 * VR_ACCOUNT_DETAILS
			 */
		} catch (SQLException e) {
			logger.error(" SQL Exception occured while inserting."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.User.ERROR_USER_NAME_ALREADY_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger
				.info("Executed ...Row insertion successful on table: VR_LOGIN_MASTER ");
		return loginId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#getLoginUserId(com.ibm.virtualization.recharge.dto.Login)
	 */
	public Login getAuthenticationDetails(long loginId) throws DAOException {
		logger.info(" Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Login login = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_SELECT_ON_ID_KEY));
			ps.setLong(1, loginId);
			rs = ps.executeQuery();
			// getting user idand set into Login DTO
			if (rs.next()) {
				login = new Login();
				login.setType(rs.getString("TYPE"));
				login.setGroupId(rs.getInt("GROUP_ID"));
			} else {
				logger.error(" No Record Found, According To Entered User ID ");
				// not found errors.login.user_invalid
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_LOGIN_NAME_NOT_EXIST);
			}
		} catch (SQLException e) {
			logger.error("Exception occured while searching."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed...retrieve user Id  successfully");
		return login;
	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	protected static Login populateDto(ResultSet rs) throws DAOException,
			SQLException {
		logger.info(" Started...");
		Login login = new Login();
		login.setLoginId(rs.getInt("LOGIN_ID"));
		login.setLoginName(rs.getString("LOGIN_NAME"));
		login.setType(rs.getString("TYPE"));
		login.setStatus(rs.getString("STATUS"));
		login.setGroupId(rs.getInt("GROUP_ID"));
		logger.info("Executed... data populated successfully.");
		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#updateUserLogin(com.ibm.virtualization.recharge.dto.User)
	 */
	public void updateUserLogin(User user) throws DAOException {
		logger.info(" Started...");
		PreparedStatement ps = null;
		int numRows = -1;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_UPDATE_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, user.getType());
			ps.setInt(paramCount++, user.getGroupId());
			ps.setString(paramCount++, user.getStatus());
			ps.setLong(paramCount, user.getUserId());
			numRows = ps.executeUpdate();

			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.fatal(" SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.User.ERROR_USER_NAME_ALREADY_EXIST);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info(" Executed ....");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getUserContext(long,java.lang.String)
	 */
	public UserSessionContext getUserContext(long loginId) throws DAOException {
		logger.info(" Started...");
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		UserSessionContext context = null;
		try {

			pStmt = connection.prepareStatement(queryMap.get(SQL_SELECT_USER_CONTEXT_KEY));
			pStmt.setLong(1, loginId);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				context = new UserSessionContext();
				context.setId(rs.getLong("LOGIN_ID"));
				context.setLoginName(rs.getString("LOGIN_NAME"));
				context.setGroupId(rs.getInt("GROUP_ID"));
				context.setType(rs.getString("TYPE"));
				context.setCircleId(rs.getInt("CIRCLE_ID"));
			} else {
				throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
			}
		} catch (SQLException se) {

			logger.error(" SQLException " + se.getMessage()
					+ " occured while getting UserContext for loginId "
					+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pStmt, rs);
		}
		logger
				.info("Executed .... successfully get getUserContext for loginId "
						+ loginId);
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getMUserContext(java.lang.String,java.lang.String)
	 */
	public UserSessionContext getMUserContext(String mobileNumber,
			String mPassword) throws DAOException {
		logger.info(" Started...");
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		UserSessionContext context = null;
		try {
			pStmt = connection.prepareStatement(queryMap.get(SQL_SELECT_USERCONTEXT_MUSER_KEY));
			pStmt.setString(1, mobileNumber);
			pStmt.setString(2, mPassword);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				context = new UserSessionContext();
				context.setId(rs.getLong("LOGIN_ID"));
				context.setLoginName(rs.getString("LOGIN_NAME"));
				context.setGroupId(rs.getInt("GROUP_ID"));
				context.setType(rs.getString("TYPE"));
				context.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				context.setCircleId(rs.getInt("CIRCLE_ID"));
				context.setStatus(rs.getString("STATUS"));
				context.setBillableAccId(rs.getLong("BILLABLE_ACC_ID"));
				context.setCircleCode(rs.getString("CIRCLE_CODE"));
				Date openingDate = rs.getDate("OPENING_DT");
				String circleStatus = rs.getString("CIRCLE_STATUS");
				logger.info("opening Date:" + openingDate);
				if (openingDate != null) {

					if (openingDate.compareTo(new Date()) > 0) {
						logger
								.fatal("opening date is greater than current date");
						throw new DAOException(
								ExceptionCode.Authentication.ERROR_INACTIVE_SOURCEACCOUNT);
					} else {
						if (!("A".equalsIgnoreCase(circleStatus))) {
							logger.fatal("circle is Inactive");
							throw new DAOException(
									ExceptionCode.Authentication.ERROR_INACTIVE_SOURCECIRCLE);
						}
					}
				}
			} else {
				throw new DAOException(ExceptionCode.User.ERROR_USER_NOT_FOUND);
			}
		} catch (SQLException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while getting UserContext for mobileNumber "
					+ mobileNumber, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pStmt, rs);
		}
		logger
				.info(" Executed ....successfully get getUserContext for mobileNumber "
						+ mobileNumber);
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#authenticateAccount(java.lang.String,
	 *      java.lang.String)
	 */
	public UserSessionContext authenticateAccount(String accountCode,
			String ipassword) throws DAOException {
		logger.info("Started... ");
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		UserSessionContext context = null;
		try {

			pStmt = connection.prepareStatement(queryMap.get(SQL_AUTHENTICATE_USER_KEY));
			pStmt.setString(1, accountCode);
			pStmt.setString(2, ipassword);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				context = new UserSessionContext();
				context.setId(rs.getLong("LOGIN_ID"));
				context.setLoginName(rs.getString("LOGIN_NAME"));
				context.setGroupId(rs.getInt("GROUP_ID"));
				context.setType(rs.getString("TYPE"));
				context.setCircleId(rs.getInt("CIRCLE_ID"));
				Date openingDate = rs.getDate("OPENING_DT");
				if (openingDate != null) {

					if (openingDate.compareTo(new Date()) > 0) {
						logger
								.fatal("opening date is greater than current date");
						throw new DAOException(
								ExceptionCode.Authentication.ERROR_INACTIVE_SOURCEACCOUNT);
					}
				}

			} else {
				return null;
			}
		} catch (SQLException se) {
			logger.error("occured while authenticating account user. ", se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pStmt, rs);
		}
		logger
				.info(" Executed ....Successfully authenticated account user with code  "
						+ accountCode);

		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#updatePassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void updatePassword(long loginId, String oldPassword,
			String newPassword) throws DAOException {
		logger.info("Started... ");

		PreparedStatement ps = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_UPDATE_PASSWORD_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, newPassword);
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setString(paramCount++, oldPassword);
			ps.setLong(paramCount++, loginId);
			numRows = ps.executeUpdate();
			if (numRows == 0) {
				logger
						.error(" User tried to change passsword with wrong password.");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_INVALID_CHANGE_OLD_PASSWORD);
			}
			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.fatal(" SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
		
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info(" Executed ....");

	}
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#resetIPassword(long, java.lang.String, java.lang.String, int)
	 */
	public void resetIPassword(long loginId, String oldPassword,
			String newPassword,int expDays) throws DAOException {
		logger.info("Started... ");

		PreparedStatement ps = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_RESET_IPASSWORD_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, newPassword);
			ps.setInt(paramCount++, expDays);
			ps.setString(paramCount++, oldPassword);
			ps.setLong(paramCount, loginId);
			numRows = ps.executeUpdate();
			logger.info("sql :- "+queryMap.get(SQL_LOGIN_RESET_IPASSWORD_KEY));
			if (numRows == 0) {
				logger
						.error(" User tried to reset passsword .");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_INVALID_CHANGE_OLD_PASSWORD);
			}
			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.fatal(" SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info(" Executed ....");

	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#updatePasswordHistory(long,
	 *      java.lang.String)
	 */
	public void updatePasswordHistory(long loginId, String oldPassword,
			int numberOfPwdHistory) throws DAOException {
		logger.info("Started... ");

		PreparedStatement ps = null;
		/*
		 * Calling the stored procedure to check valid number of password
		 * history for a user as per the policy defined in
		 * RechargeCore.properties and insert old password into history table
		 */
		try {
			CallableStatement proc = connection
					.prepareCall("{ call DPDTH.PROC_UPDATE_VR_PWD_HISTORY(?,?,?,?) }");
			proc.setLong(1, loginId);
			proc.setInt(2, numberOfPwdHistory);
			proc.setString(3, oldPassword);
			proc.setTimestamp(4, Utility.getDateTime());
			proc.execute();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger
					.fatal(
							" SQL Exception occured while Calling Stored Procedure PROC_UPDATE_VR_PWD_HISTORY."
									+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info(" Executed ....");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#updateMPassword(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateMPassword(long loginId,
			String newPassword,String oldPassword) throws DAOException {
		logger.info("Started...");

		PreparedStatement ps = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_UPDATE_M_PASSWORD_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, newPassword);
			ps.setString(paramCount++, oldPassword);
			// ps.setTimestamp(paramCount++, Utility.getDateTime()); Todo add
			// date time
			ps.setLong(paramCount, loginId);
			numRows = ps.executeUpdate();
			if (numRows == 0) {
				logger
						.error(" User tried to change M-passsword with wrong M-password.");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_INVALID_CHANGE_OLD_PASSWORD);
			}
			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			logger.fatal(" SQL Exception occured while updating M-Password ."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info(" Executed ....");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#resetMPassword(long, java.lang.String)
	 */
	public void resetMPassword(long loginId,
			String newPassword) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		int numRows = -1;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_LOGIN_RESET_M_PASSWORD_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, newPassword);
			// ps.setTimestamp(paramCount++, Utility.getDateTime()); Todo add
			// date time
			ps.setLong(paramCount++, loginId);
			numRows = ps.executeUpdate();
		
			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated: reset m-password "
							+ numRows + " rows");
		} catch (SQLException e) {
			logger.fatal(" SQL Exception occured while reset M-Password ."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		logger.info(" Executed ....");
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#isUserLocked(long)
	 */
	
	public boolean isUserLocked(long loginId) throws DAOException {
		logger.info("Started...");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int count=-1;
		boolean flag=false;
			
		try {		
			
		ps = connection.prepareStatement(SQL_USER__CHECK_LOCKED);
		ps.setLong(1, loginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			//int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));	
			int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_INVALID_COUNT));
	
			if(count >= numberOfPwdHistory){
				flag=true;
			}
								
		} catch (SQLException e) {
			logger.error("isUserLocked() : SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("LoginDaoRdbms:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}	
		
		return flag;
		
	}
	
	
	
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.LoginDao#getRoleLinkMap()
	 */
	public LinkedHashMap<String, ArrayList> getRoleLinkMap() throws DAOException {
		ArrayList<Link> roleLinkList=new ArrayList<Link>();
		LinkedHashMap<String, ArrayList> roleLinkMap = new LinkedHashMap<String, ArrayList>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			pStmt = connection.prepareStatement(queryMap.get(SQL_ROLE_LINK_MAP_KEY));
			rs = pStmt.executeQuery();
			while (rs.next()) {
				
				Link link = new Link();
				link.setOrderId(rs.getString("ORDER_ID"));
				link.setLinkId(rs.getString("LINK_ID"));
				link.setLinkDisplayLabel(rs.getString("LINK_LABEL"));
				link.setLinkDisplayUrl(rs.getString("LINK_URL"));
				link.setTopLinkName(rs.getString("TOP_LINK_NAME"));
				String roleName=rs.getString("ROLE_NAME");
				ArrayList list;
				if(roleLinkMap.get(roleName)!=null)
				{
					list=(ArrayList)roleLinkMap.get(roleName);
					
					
				}else
				{
					list=new ArrayList<Link>();
					
					
				}
				list.add(link);
				roleLinkMap.put(rs.getString("ROLE_NAME"), list);
			}
		} catch (SQLException se) {
			logger.error("loadRoleLinkMap : SQLException " + se.getMessage()
					+ " occured while geting link and role information", se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch (Exception e) {
			logger.error("loadRoleLinkMap : Exception " + e.getMessage()
					+ " while geting link and role information", e);
		} finally {
			/* Close the preparedstatement ,resultset. */
			DBConnectionManager.releaseResources(pStmt, rs);
		}

		return roleLinkMap;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#unlockUser(java.lang.String)
	 */
	
	public void unlockUser(long loginId) throws DAOException {

		logger.info("Started unlockUser...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int numRows = -1;

		try {

			ps = connection.prepareStatement(SQL_USER_UPDATE_UNLOCK);
			logger.info("Login+++++++++++++++++++++" + loginId);
			ps.setLong(1, loginId);
			numRows = ps.executeUpdate();
	
			logger
					.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"
							+ numRows + " rows");
			logger.info("Executed ....");

			if (0 == numRows) {
				logger.error(" User is already unlocked  .");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_USER_ALREADY_LOCKED);
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("unlockUser() : SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.UserDao#getMUserContext(java.lang.String,java.lang.String)
	 */
	public RechargeTransCircleDetail getSourceCircleId(String mobileNumber) throws DAOException {
		logger.info(" Started...");
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		RechargeTransCircleDetail rechargeTransCircleDetail=new RechargeTransCircleDetail();
		try {
			pStmt = connection.prepareStatement(queryMap.get(SQL_SELECT_SOURCE_CIRCLE_KEY));
			pStmt.setString(1, mobileNumber);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				rechargeTransCircleDetail.setSourceCircleId(rs.getInt("CIRCLE_ID"));
				rechargeTransCircleDetail.setSourceAccountId(rs.getLong("ACCOUNT_ID"));
				rechargeTransCircleDetail.setSourceCircleCode(rs.getString("CIRCLE_CODE"));
				
			}
		}
			catch (SQLException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while getting UserContext for mobileNumber "
					+ mobileNumber, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pStmt, rs);
		}
		logger
				.info(" Executed ....successfully get getUserContext for mobileNumber "
						+ mobileNumber);
		return rechargeTransCircleDetail;
	}
  
	public void changePassword(long loginId,String newPassword) throws DAOException{
		PreparedStatement pstmt= null;
		int rowsUpdated = 0;
		try{
		pstmt = connection.prepareStatement(updateChngdPass);
		pstmt.setString(1,newPassword);
		pstmt.setLong(2,loginId);
		rowsUpdated=pstmt.executeUpdate();
		DBConnectionManager.commitTransaction(connection);
		}catch (SQLException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while Updating password  "
					+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally 
		{

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pstmt,null);
		}
		logger.info(" No of Updated rec - "+rowsUpdated+" Password Changed for- "+ loginId);
		
	}
	
	public int checkPswdExpiring(String loginId) throws DAOException{
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int days= 0;
		int remainPswdExpDaysInt = 0;
		try{
			String remainPswdExpDays= ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.DAYS_REMAIN_FOR_PSSWD_CHANGE);
			
			if(remainPswdExpDays!=null && !remainPswdExpDays.equalsIgnoreCase("")){
				remainPswdExpDaysInt = Integer.parseInt(remainPswdExpDays);
			}
			
		String expDays= ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PASSWORD_EXPIRING_DAYS);
		if(expDays!=null && !expDays.equalsIgnoreCase("")){
			int expDaysInt = Integer.parseInt(expDays);
			
			pstmt = connection.prepareStatement("Select (? - (days(Current Timestamp) - days(IPWD_CHANGED_DT))) as days from VR_LOGIN_MASTER where LOGIN_ID=? with ur");//Neetika on 6 Dec
			pstmt.setInt(1,expDaysInt);
			pstmt.setString(2,loginId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				days = rs.getInt("days");
			}
		}
		logger.info(loginId+"days====="+days);
			if(days<=remainPswdExpDaysInt){
				return days;
			}
			else{
				return 0;
			}
		}
		catch (VirtualizationServiceException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while Updating password  "
					+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} 
		catch (SQLException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while Updating password  "
					+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally 
		{

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pstmt,null);
		}
		
	}
	
	
	public void inActiveIdHelpdeskUser(long loginId,Timestamp currentTime) throws DAOException {

		logger.info("Started inActiveIdHelpdeskUser...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int numRows = -1;

		try {

			ps = connection.prepareStatement(SQL_USER_UPDATE_INACTIVE);
			logger.info("Login+++++++++++++++++++++" + loginId);
			ps.setString(1, "I");
			ps.setLong(2, loginId);
			numRows = ps.executeUpdate();
			if(numRows>0){
				insertLoginHistDetails(loginId,currentTime);
				insertHistDetails(loginId,currentTime);
			}
			
			
			logger.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"+ numRows + " rows");
			logger.info("Executed ....");


		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("unlockUser() : SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}
	public void activeIdHelpdeskUser(long loginId,Timestamp currentTime) throws DAOException {

		logger.info("Started inActiveIdHelpdeskUser...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int numRows = -1;

		try {

			ps = connection.prepareStatement(SQL_USER_UPDATE_INACTIVE);
			logger.info("Login+++++++++++++++++++++" + loginId);
			ps.setString(1, "A");
			ps.setLong(2, loginId);
			numRows = ps.executeUpdate();
			if(numRows>0){
				insertLoginHistDetails(loginId,currentTime);
				insertHistDetails(loginId,currentTime);
			}
			logger.info(" Update successful on table:VR_LOGIN_MASTER. Updated:"+ numRows + " rows");
			logger.info("Executed ....");

			

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("unlockUser() : SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}

	public void changePassword(String changePwd, long loginId, String newPassword) throws DAOException {
		PreparedStatement pstmt= null;
		int rowsUpdated = 0;
		try{
		pstmt = connection.prepareStatement(updateChngdPass);
		pstmt.setString(1,newPassword);
		pstmt.setLong(2,loginId);
		rowsUpdated=pstmt.executeUpdate();
		DBConnectionManager.commitTransaction(connection);
		}catch (SQLException se) {
			logger.error("SQLException " + se.getMessage()
					+ " occured while Updating password  "
					+ loginId, se);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally 
		{

			// finally closing resultset and prepared statement
			DBConnectionManager.releaseResources(pstmt,null);
		}
		logger.info(" No of Updated rec - "+rowsUpdated+" Password Changed for- "+ loginId);
		
	}
	
	public void sendPasswordSMS(SMSDto sMSDto) throws DAOException {
		
		try{
			logger.info("logindaordbms > sendPasswordSMS  inserting in DP_SEND_SMS  ");
			int login_id=SMSUtility.getDistId(sMSDto.getAccountName());
			String message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_CHANGE_PASSWORD, sMSDto, connection, login_id+"");
			if(message1!=null && !message1.equalsIgnoreCase(""))
			{
			String message=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_CHANGE_PASSWORD, sMSDto, connection);
		    logger.info("SMS Text :: "+message);
			if(message != null && !message.equalsIgnoreCase("") && sMSDto.getMobileNumber() != null && !sMSDto.getMobileNumber().equalsIgnoreCase(""))
			{
				logger.info("add Constants.CONFIRM_ID_CHANGE_PASSWORD");
				// Constants.CONFIRM_ID_CHANGE_PASSWORD 
				SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getMobileNumber()}, message, Constants.CONFIRM_ID_CHANGE_PASSWORD);
			}
			}
		}catch (Exception se) {
			logger.error("Exception " + se.getMessage()
					+ " occured while saving password SMS ");
			//throw new DAOException("Exception occured while saving forget password SMS ");
		} 
	}
}
