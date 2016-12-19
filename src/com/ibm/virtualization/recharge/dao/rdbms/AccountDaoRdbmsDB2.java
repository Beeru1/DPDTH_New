package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ResourceReader;
public class AccountDaoRdbmsDB2 extends AccountDaoRdbms{

	protected static final String SQL_SELECT_FOR_PARENT_ACC = " WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT) SELECT GROUPD.GROUP_NAME,LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CATEGORY,LOGIN.STATUS,BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD  WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID  AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS = '"
		+ Constants.ACTIVE_STATUS
		+ "' AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM n ) "; 
    
    protected static final String SQL_SELECT_FOR_PARENT_ACC_COUNT = "WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT) SELECT COUNT(*) FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD  WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID  AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND DETAILS.CIRCLE_ID = ? AND LOGIN.STATUS = '"
		+ Constants.ACTIVE_STATUS
		+ "' AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND DETAILS.ACCOUNT_ID IN (  SELECT ACCOUNT_ID FROM n ) ";

    protected final static String SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY = "SQL_SELECT_ALL_ACC_WHERE_CLAUSE";
    protected static final String SQL_SELECT_ALL_ACC_WHERE_CLAUSE = " FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_LOGIN_MASTER L1 ON DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID, VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2, VR_ACCOUNT_BALANCE BALANCE,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY ";

    protected final static String SQL_SELECT_REPORT_AUTHENTICATION_KEY = "SQL_SELECT_REPORT_AUTHENTICATION";
    protected final static String SQL_SELECT_REPORT_AUTHENTICATION ="select report_ext_password from vr_group_details where group_id = ?";
  
//   protected static final String SQL_SELECT_ALL_ACC_COUNT = "SELECT COUNT(DETAILS.ACCOUNT_ID) "
//			+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;


	protected final static String SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY ="SQL_SELECT_ALL_ACC_COUNT_CHILD";
	protected static final String SQL_SELECT_ALL_ACC_COUNT_CHILD = "WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT)SELECT COUNT(DETAILS.ACCOUNT_ID) "
		+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;
    
//    protected static final String SQL_SELECT_ALL_ACC = " SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE, BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, DETAILS.CATEGORY,LOGIN.STATUS, BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME "
//			+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;
	
    protected final static String SQL_SELECT_ALL_ACC_CHILD_KEY ="SQL_SELECT_ALL_ACC_CHILD";
    protected static final String SQL_SELECT_ALL_ACC_CHILD = " SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE, BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, DETAILS.CATEGORY,LOGIN.STATUS, BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME "
		+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;
    
    protected final static String SQL_SELECT_ALL_ACC_CHILD_DB2_KEY ="SQL_SELECT_ALL_ACC_CHILD_DB2";
    protected static final String SQL_SELECT_ALL_ACC_CHILD_DB2 = " WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT)SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE, BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, DETAILS.CATEGORY,LOGIN.STATUS, BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME "
		+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;
    
    public AccountDaoRdbmsDB2(Connection conn) {
		super(conn);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_KEY,SQL_SELECT_FOR_PARENT_ACC);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY, SQL_SELECT_FOR_PARENT_ACC_COUNT);
		queryMap.put(SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY ,SQL_SELECT_ALL_ACC_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY ,SQL_SELECT_ALL_ACC_COUNT_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_KEY ,SQL_SELECT_ALL_ACC_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_DB2_KEY ,SQL_SELECT_ALL_ACC_CHILD_DB2);
	}
	
    public ArrayList getParentAccountList(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId, int lb, int ub) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ parentCircleId + " AND accountLevelId :" + accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();
		String query;
		int paramCount = 1;
		try {
			StringBuffer sql = new StringBuffer();

			// show records of respective child and grand-child accounts upto
			// n-th level

			sql.append(queryMap.get(SQL_SELECT_FOR_PARENT_ACC_KEY));
			/*
			 * if (Integer.parseInt(accountLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + accountLevelId + "' AND ACCOUNT_LEVEL >='" +
			 * Constants.ACC_LEVEL_MTP + "'"); } else { sql.append(" AND
			 * ACCOUNT_LEVEL <'" + Constants.ACC_LEVEL_MTP + "' AND
			 * ACCOUNT_LEVEL <'" + accountLevelId + "'"); }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID='" + accountLevelId
					+ "'   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID='"
							+ accountLevelId + "'))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, parentCircleId);
					ps.setLong(paramCount++, parentId);
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
					ps.setString(paramCount++, String.valueOf(ub));
					ps.setString(paramCount, String.valueOf(lb + 1));

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					// logger.info(" SQL is :- " + sql);
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);

					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, searchValue.toUpperCase() + "%");
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/*
					 * all child and sub-child and sub-sub-child accounts upto
					 * n-th level
					 */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a )b"
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				}
			}
			logger.info(" This is query getParentAccountList:- sql "
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;

	}
      
   

public ArrayList getAccountList(ReportInputs mtDTO, int lb, int ub)
			throws DAOException {
		logger.info("Started ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		String circleName=null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;
			
			
			if(circleId!=0){
				circleName=getCircleName(circleId);
				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else {	
					// if circle user, show records of respective circle
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			}else {
				// if Administrator , show records from all circles
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			 }
	
			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				} /*
					 * else if (searchType.trim().equalsIgnoreCase(
					 * Constants.SEARCH_TYPE_ALL)) {
					 * 
					 * ps.setString(paramCount++, status);
					 * ps.setDate(paramCount++, new
					 * java.sql.Date(Utility.getDate( startDt).getTime()));
					 * ps.setDate(paramCount++, new
					 * java.sql.Date(Utility.getDate( endDt).getTime()));
					 * ps.setString(paramCount++, String.valueOf(ub));
					 * ps.setString(paramCount++, String.valueOf(lb + 1)); }
					 */
				
				
				if((0 != circleId)&& (circleName.equalsIgnoreCase("NATIONAL")) &&(searchType.trim().equalsIgnoreCase(
						Constants. SEARCH_TYPE_ALL))){	
					// If user selects search type as ALL	
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				}else if((circleName.equalsIgnoreCase("NATIONAL"))&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){					
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
				}
				
				query.append("SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append(") a)b Where rnum<=? and  rnum>=?");
				ps = connection.prepareStatement(query.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				ps.setString(paramCount++, String.valueOf(ub));
				ps.setString(paramCount++, String.valueOf(lb + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));

				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRowNum(rs.getString("RNUM"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				int numberOfPwdHistory = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));	
				if(rs.getInt("LOGIN_ATTEMPTED")==numberOfPwdHistory){
					account.setLocked(true);
				}
				else{
					account.setLocked(false);
				}
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("AccountDaoRdbmsDB2:VirtualizationServiceException occured"+virtualizationExp.getMessage());
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}


public ArrayList getChildAccountList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException {

		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		StringBuilder query = new StringBuilder();
		int paramCount = 1;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_KEY) + " AND LOGIN.STATUS = ? ");
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// // logger.info(" SQL is :- " + sql);
				// query = "SELECT * from(select a.*,ROWNUM rnum FROM (" + sql
				// + ") a " + "Where ROWNUM<=?)Where rnum>=?";
				// ps = connection.prepareStatement(query);
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// ps.setString(paramCount++, String.valueOf(upperBound));
				// ps.setString(paramCount++, String.valueOf(lowerBound + 1));
				// }
				sql
						.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM n )  ");
				query.append("WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT)SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append(") a)b Where rnum<=? and rnum>=?");
				logger.info("query-----------------"+query);
				ps = connection.prepareStatement(query.toString());
				ps.setLong(paramCount++, parentId);
				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRowNum(rs.getString("RNUM"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}



	public int getAccountListCount(ReportInputs mtDTO) throws DAOException {
		logger.info("Started getAccountListCount()...mtDTO " + mtDTO);

		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		int noofRow = 1;
		int noofpages = 0;
		int paramCount = 1;
		String circleName=null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			

			if(circleId!=0){
							circleName=getCircleName(circleId);

				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else{
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				} 
			}else {
				/** if Administrator , show records from all circles */
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			}			

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /* all accounts */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				
				

			
				if(0 != circleId){ 
					if((circleName.equalsIgnoreCase("NATIONAL"))&&(searchType.trim().equalsIgnoreCase(Constants. SEARCH_TYPE_ALL))){	
						//	If user selects search type other than ALL
						sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
					}else if(circleName.equalsIgnoreCase("NATIONAL")&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){	
						//	If user selects search type as ALL	
						sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
					}
				}	
				
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);
		} catch (SQLException e) {
			logger.error("Exception occured while counting the records"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		logger.info("Executed getAccountListCount()...");
		return noofpages;
	}


	public ArrayList getAccountListAll(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getAccountListAll()...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		int circleId = mtDTO.getCircleId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		String circleName=null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			int paramCount = 1;
			
			
			if(circleId!=0){
				circleName=getCircleName(circleId);
				if(circleName.equalsIgnoreCase("NATIONAL")){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");			
				}else {	
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			}else {
					/** if Administrator , show records from all circles */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
			 }
			
			

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				/** search according to mobile no */
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/** search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/** search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/** search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /* all accounts */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				
				if((0 != circleId)&& (circleName.equalsIgnoreCase("NATIONAL")) &&(searchType.trim().equalsIgnoreCase(
						Constants. SEARCH_TYPE_ALL))){	
					// If user selects search type as ALL	
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				}else if((circleName.equalsIgnoreCase("NATIONAL"))&& (searchType.trim()!=Constants. SEARCH_TYPE_ALL)){					
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ACC_NATIONAL_KEY));
				}
				
				ps = connection.prepareStatement(sql.toString());

				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving all Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getAccountListAll()...");
		return accountList;
	}


	public ArrayList getChildAccountListAll(ReportInputs mtDTO)
			throws DAOException {

		logger.info("Started getChildAccountListAll() ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		int paramCount = 1;
		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_DB2_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
				// logger.info("SQL APPENDED IS : "+sql);
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?  ");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				sql
						.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM n )  ");
				ps = connection.prepareStatement(sql.toString());
			
				ps.setLong(paramCount++, parentId);
				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				
			}
			
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				account.setRate(rs.getDouble("RATE"));
				account.setThreshold(rs.getDouble("THRESHOLD"));
				account.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				account.setRegionId(rs.getString("REGION_NAME"));
				account.setCreatedBy(rs.getLong("CREATED_BY"));
				account.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving all child accounts List"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getChildAccountListAll()...");
		return accountList;
	}


	public int getChildAccountListCount(ReportInputs mtDTO)
			throws DAOException {
		logger.info("Started getChildAccountListCount()...");

		/** get the data from thhe Modern Trade DTO */
		String searchType = mtDTO.getSearchFieldName();
		String searchValue = mtDTO.getSearchFieldValue();
		long parentId = mtDTO.getParentId();
		String status = mtDTO.getStatus();
		String startDt = mtDTO.getStartDt();
		String endDt = mtDTO.getEndDt();
		int noofRow = 1;
		int noofpages = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY));
				sql.append(" AND LOGIN.STATUS = ? ");
			}

			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?");
				}
				// else if (searchType.trim().equalsIgnoreCase(
				// Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
				// ps = connection.prepareStatement(sql.toString());
				//					
				// /** set the values in the statement */
				// ps.setLong(paramCount++, parentId);
				// ps.setString(paramCount++, status);
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// startDt).getTime()));
				// ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
				// endDt).getTime()));
				// }
				sql
						.append(" AND DETAILS.ACCOUNT_ID IN ( SELECT ACCOUNT_ID FROM n ) ");
				
				logger.info("sql from account dao......."+sql);
				ps = connection.prepareStatement(sql.toString());
				ps.setLong(paramCount++, parentId);
				ps.setString(paramCount++, status);
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, searchValue.toUpperCase() + "%");
				}
				
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noofRow = rs.getInt(1);
			}
			noofpages = Utility.getPaginationSize(noofRow);

		} catch (SQLException e) {
			logger.error(
					"Exception occured while reteriving Child account list count"
							+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed getChildAccountListCount()...");
		return noofpages;
	}


	
	public ArrayList getParentSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId);
		
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> accountList = new ArrayList<Account>();
		String query;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='"
					+ Constants.ACTIVE_STATUS + "' ");

			/*
			 * if (Integer.parseInt(accLevelId) >= Integer
			 * .parseInt(Constants.ACC_LEVEL_MTP)) { sql.append(" AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("' AND
			 * ACCOUNT_LEVEL >='"); sql.append(Constants.ACC_LEVEL_MTP);
			 * sql.append("'");
			 *  } else { sql.append(" AND ACCOUNT_LEVEL <'");
			 * sql.append(Constants.ACC_LEVEL_MTP); sql.append("' AND
			 * ACCOUNT_LEVEL <'"); sql.append(accLevelId); sql.append("'");
			 *  }
			 */

			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=" + accLevelId
					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
							+ accLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a )b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a )b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));
				}
			}
			logger.info(" this is sql getParentSearchAccountList :-"
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				//account.setTotalRecords(rs.getInt("RECORD_COUNT"));

				accountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Account List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return accountList;
	}

	
	public ArrayList getBillableSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub, String userType, long loginId) throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId
				+ " AND userType :" + userType + " AND loginId: " + loginId);
		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		ArrayList<Account> billableaccountList = new ArrayList<Account>();
		String query;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));

			sql.append(" AND DETAILS.BILLABLE_ACCOUNT='Y' ");
			if (userType.trim().equalsIgnoreCase(Constants.USER_TYPE_EXTERNAL)) {
				sql
						.append(" AND DETAILS.ROOT_LEVEL_ID IN (SELECT ROOT_LEVEL_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=");
				sql.append(loginId);
				sql.append(")");
			}
			sql.append(" AND DETAILS.CIRCLE_ID=? AND  LOGIN.STATUS='");
			sql.append(Constants.ACTIVE_STATUS);
			sql.append("' ");
			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT ");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append("   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=");
			sql.append(accLevelId);
			sql.append("))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ? ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString() + ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));
				}
			}
			logger.info(" this is sql getBillableSearchAccountList :-"
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				account = new Account();

				// new change group name added
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAccountCode(rs.getString("LOGIN_NAME"));
				account.setParentAccount(rs.getString("PARENT"));
				account.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				account.setSimNumber(rs.getString("SIM_NUMBER"));
				account.setAccountName(rs.getString("ACCOUNT_NAME"));
				account.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				account.setEmail(rs.getString("EMAIL"));
				account.setGroupName(rs.getString("GROUP_NAME"));
				account.setCategory(rs.getString("CATEGORY"));
				// account.setParentAccount(rs.getString("PCODE"));
				account.setCircleName(rs.getString("CIRCLE_NAME"));
				account.setStatus(rs.getString("STATUS"));
				account.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				account.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				account.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				billableaccountList.add(account);
			}

		} catch (SQLException e) {
			logger.error("Exception occured while reteriving.Billable List"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info("Executed ...");
		return billableaccountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.CircleDao#getActiveCircleName()
	 */
	public String getCircleName(int circleId) throws DAOException {
		logger.info("Started...");
		String circleName = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CIRCLE_NAME_KEY));
			ps.setInt(1,circleId );
			rs = ps.executeQuery();
			if (rs.next()) {
				circleName = rs.getString("CIRCLE_NAME");
				logger.info("Circle Name retrieved  is : " + circleName);
			} else {
				logger.error("Circle Name Does not Exist for circleId= "
						+ circleName);
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
	 * Method to retrieve the External User and Password
	 * 
	 */
      public String getReportExternalInfo(int groupID)
			throws DAOException {
		logger.info("Started...");
		String extPasswd = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_REPORT_AUTHENTICATION_KEY)+ " with UR");
			ps.setInt(1,groupID );
			rs = ps.executeQuery();
			if (rs.next()) {
				extPasswd = rs.getString("REPORT_EXT_PASSWORD");
			} else {
				logger.error("NO Password found for ext group ID");
				throw new DAOException(
						ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
			}
			logger.info("Executed .... ");
			return extPasswd;
		} catch (SQLException e) {
			logger.fatal("SQL Exception occured while retrieving Circle Name )"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Authentication.ERROR_NOPASSWORD_INFO);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}
 
	
	
    
}
