package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.dp.common.RDBMSQueries;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPCreateAcountITHelpRdbmsDB2  extends DPCreateAccountITHelpDaoImpl {

	protected static final String SQL_SELECT_FOR_PARENT_ACC = RDBMSQueries.SQL_SELECT_FOR_PARENT_ACC;

	protected static final String SQL_SELECT_FOR_PARENT_ACC_COUNT = RDBMSQueries.SQL_SELECT_FOR_PARENT_ACC_COUNT;

	protected final static String SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY = "SQL_SELECT_ALL_ACC_WHERE_CLAUSE";

	protected static final String SQL_SELECT_ALL_ACC_WHERE_CLAUSE = RDBMSQueries.SQL_SELECT_ALL_ACC_WHERE_CLAUSE;

	protected final static String SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY = "SQL_SELECT_ALL_ACC_COUNT_CHILD";

	protected static final String SQL_SELECT_ALL_ACC_COUNT_CHILD = RDBMSQueries.SQL_SELECT_ALL_ACC_COUNT_CHILD;

	protected final static String SQL_SELECT_ALL_ACC_CHILD_KEY = "SQL_SELECT_ALL_ACC_CHILD";

	protected static final String SQL_SELECT_ALL_ACC_CHILD = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD;
	
	protected final static String SQL_SELECT_ALL_ACC_CHILD_COUNT_KEY = "SQL_SELECT_ALL_ACC_CHILD_COUNT";

	protected static final String SQL_SELECT_ALL_ACC_CHILD_COUNT = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD_COUNT;

	protected final static String SQL_SELECT_ALL_ACC_CHILD_DB2_KEY = "SQL_SELECT_ALL_ACC_CHILD_DB2";

	protected static final String SQL_SELECT_ALL_ACC_CHILD_DB2 = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD_DB2;
	
	protected static final String SQL_SELECT_ALL_ACC_CHILD_DIST_KEY = "SQL_SELECT_ALL_ACC__DIST_CHILD";
	
	protected static final String SQL_SELECT_ALL_ACC__DIST_CHILD = RDBMSQueries.SQL_SELECT_ALL_ACC_CHILD_DIST;

	protected final static String SQL_SELECT_PARENT_TRADE_KEY = "SQL_SELECT_PARENT_TRADE";

	protected final static String SQL_SELECT_PARENT_TRADE = RDBMSQueries.SQL_SELECT_PARENT_TRADE;
	
	protected final static String SQL_SELECT_ALL_ACC_H2_KEY = "SQL_SELECT_ALL_ACC_H2";
	
	protected final static String SQL_SELECT_ALL_ACC_H2 = RDBMSQueries.SQL_SELECT_ALL_ACC_H2;
	

	public DPCreateAcountITHelpRdbmsDB2(Connection conn) {
		super(conn);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_KEY, SQL_SELECT_FOR_PARENT_ACC);
		queryMap.put(SQL_SELECT_FOR_PARENT_ACC_COUNT_KEY,
				SQL_SELECT_FOR_PARENT_ACC_COUNT);
		queryMap.put(SQL_SELECT_ALL_ACC_WHERE_CLAUSE_KEY,
				SQL_SELECT_ALL_ACC_WHERE_CLAUSE);
		queryMap.put(SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY,
				SQL_SELECT_ALL_ACC_COUNT_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_COUNT_KEY,
				SQL_SELECT_ALL_ACC_CHILD_COUNT);		
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_KEY, SQL_SELECT_ALL_ACC_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_DIST_KEY, SQL_SELECT_ALL_ACC__DIST_CHILD);
		queryMap.put(SQL_SELECT_ALL_ACC_CHILD_DB2_KEY,
				SQL_SELECT_ALL_ACC_CHILD_DB2);
		queryMap.put(SQL_SELECT_PARENT_TRADE_KEY, SQL_SELECT_PARENT_TRADE);
		queryMap.put(SQL_SELECT_ALL_ACC_H2_KEY, SQL_SELECT_ALL_ACC_H2);
	}

	public ArrayList getParentAccountList(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId, int lb, int ub, int accountLevel)
			throws DAOException {
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ parentCircleId + " AND accountLevelId :" + accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		String query = null;
		int paramCount = 1;
		try {
			StringBuffer sql = new StringBuffer();

			// show records of respective child and grand-child accounts upto
			// n-th level
			sql.append(queryMap.get(SQL_SELECT_FOR_PARENT_ACC_KEY));
			sql.append(" AND DETAILS.ACCOUNT_LEVEL IN(SELECT ");
			sql
					.append("LEVEL_ID FROM VR_ACCOUNT_LEVEL_MASTER level1 WHERE level1.ACC_LEVEL <(SELECT");
			sql
					.append(" level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 ");
			sql.append(" WHERE	LEVEL_ID=" + accountLevelId
					+ "   ) AND level1.HIERARCHY_ID =(SELECT ");
			sql
					.append("	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID="
							+ accountLevelId + "))");

			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ORDER BY LOGIN_NAME  ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(paramCount++, parentCircleId);
					ps.setLong(paramCount++, parentId);
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
					ps.setString(paramCount++, String.valueOf(ub));
					ps.setString(paramCount, String.valueOf(lb + 1));

				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					 ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					 logger.info(" SQL is :- " + sql);
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);

					ps.setInt(1, parentCircleId);
					 ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and  rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/*
					 * all child and sub-child and sub-sub-child accounts upto
					 * n-th level
					 */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a )b "
							+ "Where rnum<=? and  rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, parentCircleId);
					ps.setLong(2, parentId);
					ps.setString(2, String.valueOf(ub));
					ps.setString(3, String.valueOf(lb + 1));
				}
			}
			logger.info(" This is query getParentAccountList:- sql "
					+ sql.toString());
			logger.info(" Compplete Querry is :-  " + query);
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setAccountLevelId(rs.getInt("ACCOUNT_LEVEL"));
				if (accountLevel >= Constants.DISTRIBUTOR_ID) {
					ps = connection.prepareStatement(queryMap
							.get(SQL_SELECT_PARENT_TRADE_KEY));

					ps.setLong(1, accountDto.getAccountId());
					ResultSet rs1 = null;
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						accountDto.setTradeName(rs1.getString("CHANNEL_NAME"));
						accountDto.setTradeCategoryName(rs1
								.getString("CATEGORY_NAME"));
						accountDto.setTradeId(rs1.getInt("DIST_CHANNEL_ID"));
						accountDto.setTradeCategoryId(rs1
								.getInt("CHANNEL_CATEGORY_ID"));
					}
				}
				accountList.add(accountDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();

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

	public ArrayList getAccountList(ReportInputs reportInputDto, int lb, int ub)
			throws DAOException,
			NumberFormatException,
			com.ibm.virtualization.recharge.exception.VirtualizationServiceException {
		logger.info("Started ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int levelId = Integer.parseInt(reportInputDto.getSessionContext().getAccountLevel());
		String circleName = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder query = new StringBuilder();
			int paramCount = 1;
			if (circleId != 0) {
				circleName = getCircleName(circleId);
				
					// if circle user, show records of respective circle
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND VLM.STATUS = ? ");
				
			} else {
				// if Administrator , show records from all circles
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
				sql.append(" AND VLM.STATUS = ? ");
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
					sql.append(" AND UPPER(vlm.LOGIN_NAME) LIKE ? ");
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ");
				} 
				if ((0 != circleId)
						&& (searchType.trim()
								.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
					// If user selects search type as ALL
					sql.append("");
				} else if ((circleName.equalsIgnoreCase("Pan India"))
						&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_H2_KEY));
				}
				query
						.append("SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append(") a)b Where rnum<=? and  rnum>=? with ur");
				ps = connection.prepareStatement(query.toString());
				ps.setInt(paramCount++, levelId);
				ps.setInt(paramCount++, levelId);
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
				ps.setString(paramCount++, String.valueOf(ub));
				ps.setString(paramCount++, String.valueOf(lb + 1));
			}
			rs = ps.executeQuery();
			int count=0;
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				count++;
				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRowNum(rs.getString("RNUM"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATED_BY_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				int numberOfPwdHistory = Integer
						.parseInt(ResourceReader
								.getCoreResourceBundleValue(Constants.PASSWORD_HISTORY_MAX_COUNT));
				if (rs.getInt("LOGIN_ATTEMPTED") == numberOfPwdHistory) {
					accountDto.setLocked(true);
				} else {
					accountDto.setLocked(false);
				}
				accountList.add(accountDto);
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
	public ArrayList getChildAccountList(ReportInputs reportInputDto, int lowerBound,
			int upperBound) throws DAOException {

		logger.info("in RDBMSDAO IMPL --- function getChildAccountList " );
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int circleId = reportInputDto.getCircleId();
		int userRoleId = reportInputDto.getUserRole();
		int accountLevelId = userRoleId -1;
//		long accountId = reportInputDto.getSessionContext().getId();
		String levelId = reportInputDto.getSessionContext().getAccountLevel();
		logger.info("Started ...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String parentStatus="";
		DPCreateAccountDto accountDto = null;
		StringBuilder query = new StringBuilder();
		int paramCount = 1;
		boolean roleName = false;		
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		System.out.println("userRoleId:::::"+userRoleId);
		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+"")) && !(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.IT_HELP_DESK_LEVEL+""))){
				
				sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_DIST_KEY));
				sql.append("and details.account_level in ( select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level > ("+
						"SELECT level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE	LEVEL_ID=?)"+
						"AND HIERARCHY_ID = (SELECT 	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=?))  ");
						sql.append( " AND LOGIN.STATUS =  ? ");
						if (circleId != 0) {
							sql.append(" AND DETAILS.CIRCLE_ID = ");
							sql.append(circleId);				
						}
				}else {
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_DIST_KEY)
							+ " AND LOGIN.STATUS =  ? ");
						if (circleId != 0) {
							sql.append(" AND DETAILS.CIRCLE_ID = ");
							sql.append(circleId);				
						}
						if (userRoleId != 0) {
							sql.append(" AND DETAILS.ACCOUNT_LEVEL = ");
							sql.append(userRoleId-1);				
						}
					}else
					{
						sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_KEY));
						sql.append("where LM.STATUS=? AND AD.ACCOUNT_LEVEL="+accountLevelId+" order by ACCOUNT_LEVEL) as RECORDS )a)b Where rnum<=? and rnum>=?");
						if (circleId != 0) {
							sql.append(" And CIRCLE_ID = "+circleId);
						}			
						//sql.append(" With ur");
					}
				}				
				roleName = reportInputDto.getSessionContext().getRoleList().contains("ROLE_AD");
				if(roleName){
					sql.append(" and (DETAILS.PARENT_ACCOUNT= ? OR DETAILS.ROOT_LEVEL_ID= ?) ");
				}
			}
			
			if (startDt != null && !startDt.equals("")) {
				/** search according to Start Date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) >= ? ");
			}
			if (endDt != null && !endDt.equals("")) {
				/** search according to End date */
				sql.append(" AND DATE(DETAILS.CREATED_DT) <= ? ");
			}
			
			System.out.println("----------------------------------------------");
			System.out.println("------------------searchType----------------------------"+searchType);
			System.out.println("------------------userRoleId----------------------------"+userRoleId);
			
			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND DETAILS.MOBILE_NUMBER LIKE '%"+searchValue+"%' ");
					}
					else
					{
						sql.append(" AND MOBILE_NUMBER LIKE '%"+searchValue+"%' ");
					}
					
					
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(L1.LOGIN_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ) ");
					}
					else
					{
						sql.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ) ");
					}
					
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
					else
					{
						sql.append(" AND UPPER(ACCOUNT_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{	
						System.out.println("if ");
						sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
					else
					{
						System.out.println("else ");
						sql.append(" AND UPPER(LOGIN_NAME) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND UPPER(DETAILS.EMAIL) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
					else
					{
						sql.append(" AND UPPER(EMAIL) LIKE '%"+searchValue.toUpperCase()+"%' ");
					}
				}
				 else if (searchType.trim().equalsIgnoreCase(
				 Constants.SEARCH_TYPE_ALL)) {
					 sql.append("");
				 }
				query
						.append("WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT)SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM (");
				query.append(sql);
				query.append("  order by account_level) a)b Where rnum<=? and rnum>=? with ur");
				logger.info("query-----------------" + query);
				logger.info("sql-----------------" + sql.toString());
				
				
				if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){
					ps = connection.prepareStatement(query.toString());
				
				ps.setLong(paramCount++, parentId);
				}else{
					ps = connection.prepareStatement(sql.toString());
				}
				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+"")) && !(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.IT_HELP_DESK_LEVEL+""))){
					ps.setInt(paramCount++, Integer.parseInt(levelId));
					ps.setInt(paramCount++, Integer.parseInt(levelId));
				}
				ps.setString(paramCount++, status);
				if(roleName){
					ps.setLong(paramCount++, parentId);
					ps.setLong(paramCount++, parentId);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
			/*	if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, "'%"+searchValue.toUpperCase() + "%'");
				}*/
				//if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){
				ps.setString(paramCount++, String.valueOf(upperBound));
				ps.setString(paramCount++, String.valueOf(lowerBound + 1));
				//}
			}
			rs = ps.executeQuery();
			int i=1;
			while (rs.next()) {
				accountDto = new DPCreateAccountDto();
				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				parentStatus = rs.getString("PARENT_STATUS");
				
				
				if(parentStatus != null && parentStatus.equalsIgnoreCase("A"))
					parentStatus = "Active";
				else if(parentStatus != null && parentStatus.equalsIgnoreCase("I"))
					parentStatus = "Suspended";
				
				accountDto.setParentAccountStatus(parentStatus);
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
//				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
//				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
//				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
//				accountDto.setRate(rs.getDouble("RATE"));
//				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){	
				accountDto.setRowNum(rs.getString("RNUM"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				}else{
				//	accountDto.setRowNum(String.valueOf(i++));
					accountDto.setRowNum(rs.getString("RNUM"));
				}
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				// accountDto.setTotalRecords(rs.getInt("RECORD_COUNT"));
				accountList.add(accountDto);
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

	public int getAccountListCount(ReportInputs reportInputDto) throws DAOException {
		logger.info("Started getAccountListCount()...reportInputDto " + reportInputDto);

		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int noofRow = 1;
		int noofpages = 0;
		int paramCount = 1;
		String circleName = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();

			if (circleId != 0) {
				circleName = getCircleName(circleId);

				if (circleName.equalsIgnoreCase("NATIONAL")) {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
				} else {
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_KEY));
					sql.append(" AND CM.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			} else {
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
			}
			if (0 != circleId) {
				if ((circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim()
								.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
					// If user selects search type other than ALL
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				} else if (circleName.equalsIgnoreCase("NATIONAL")
						&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
					// If user selects search type as ALL
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
				ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
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

	public ArrayList getAccountListAll(ReportInputs reportInputDto) throws DAOException {
		logger.info("Started getAccountListAll()...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		int circleId = reportInputDto.getCircleId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		String circleName = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

		try {
			StringBuilder sql = new StringBuilder();
			int paramCount = 1;

			if (circleId != 0) {
				circleName = getCircleName(circleId);
				if (circleName.equalsIgnoreCase("NATIONAL")) {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
				} else {
					/** if circle user, show records of respective circle */
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
					sql.append(" AND DETAILS.CIRCLE_ID = ");
					sql.append(circleId);
					sql.append(" AND LOGIN.STATUS = ? ");
				}
			} else {
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

				if ((0 != circleId)
						&& (circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim()
								.equalsIgnoreCase(Constants.SEARCH_TYPE_ALL))) {
					// If user selects search type as ALL
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_NATIONAL_KEY));
				} else if ((circleName.equalsIgnoreCase("NATIONAL"))
						&& (searchType.trim() != Constants.SEARCH_TYPE_ALL)) {
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
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setRate(rs.getDouble("RATE"));
				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(accountDto);
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

	public ArrayList getChildAccountListAll(ReportInputs reportInputDto)
			throws DAOException {

		logger.info("Started getChildAccountListAll() ...");
		/* get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int circleId = reportInputDto.getCircleId();
		int levelId = Integer.parseInt(reportInputDto.getSessionContext().getAccountLevel());
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		int paramCount = 1;
		boolean roleName = false;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();

		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+""))){
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_KEY));
					sql.append("and details.account_level in ( select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level > ("+
							"SELECT level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE	LEVEL_ID=?)"+
							"AND HIERARCHY_ID = (SELECT 	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=?))  ");
					sql.append( " AND LOGIN.STATUS =  ?  AND DETAILS.CIRCLE_ID = "+circleId);
				}else {
					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_DB2_KEY));
					sql.append(" AND LOGIN.STATUS = ? AND DETAILS.CIRCLE_ID = "+circleId);
				// logger.info("SQL APPENDED IS : "+sql);
				}
				roleName = reportInputDto.getSessionContext().getRoleList().contains("ROLE_AD");
				if(roleName){
					sql.append(" and (DETAILS.PARENT_ACCOUNT= ? OR DETAILS.ROOT_LEVEL_ID= ?) ");
				}
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
				 else if (searchType.trim().equalsIgnoreCase(
				 Constants.SEARCH_TYPE_ALL)) {
				
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
					 sql.append(" order by account_level with ur");
				 }
				ps = connection.prepareStatement(sql.toString());

				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+""))){
					ps.setInt(paramCount++, levelId);
					ps.setInt(paramCount++, levelId);
				}
				ps.setString(paramCount++, status);
				if(roleName){
					ps.setLong(paramCount++, parentId);
					ps.setLong(paramCount++, parentId);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}
				if (searchValue != null && !searchValue.equals("")) {
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
				}

			}

			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
//				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
//				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
//				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
//				accountDto.setRate(rs.getDouble("RATE"));
//				accountDto.setThreshold(rs.getDouble("THRESHOLD"));
				accountDto.setCreatedDt(rs.getTimestamp("CREATED_DT"));
				accountDto.setRegionId(rs.getString("REGION_NAME"));
				accountDto.setCreatedBy(rs.getLong("CREATED_BY"));
				accountDto.setCreatedByName(rs.getString("CREATEDBYNAME"));
				accountList.add(accountDto);
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

	public int getChildAccountListCount(ReportInputs reportInputDto) throws DAOException {
		logger.info("Started getChildAccountListCount()...");

		/** get the data from thhe Modern Trade DTO */
		String searchType = reportInputDto.getSearchFieldName();
		String searchValue = reportInputDto.getSearchFieldValue();
		long parentId = reportInputDto.getParentId();
		String status = reportInputDto.getStatus();
		String startDt = reportInputDto.getStartDt();
		String endDt = reportInputDto.getEndDt();
		int noofRow = 1;
		int noofpages = 0;
		int levelId = Integer.parseInt(reportInputDto.getSessionContext().getAccountLevel());
		int circleId = reportInputDto.getCircleId();
		int userRoleId = reportInputDto.getUserRole();
		int accountLevelId = userRoleId -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		boolean flagParent=false;
		boolean roleName = false;
		try {
			StringBuilder sql = new StringBuilder();
			if (0 != parentId) {
				// show records of respective child and grand-child accounts
				// upto n-th level
				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+"")) && !(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.IT_HELP_DESK_LEVEL+""))){

					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY));
					sql.append(" and details.account_level in ( select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level > ("+
							"SELECT level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE	LEVEL_ID=?)"+
							"AND HIERARCHY_ID = (SELECT HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER WHERE	LEVEL_ID=?))  ");
					sql.append( " AND LOGIN.STATUS = ? ");
					if (circleId != 0) {
						sql.append(" AND DETAILS.CIRCLE_ID = ");
						sql.append(circleId);				
					}
				}else {
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){

					sql.append(queryMap.get(SQL_SELECT_ALL_ACC_COUNT_CHILD_KEY));
					sql.append(" AND LOGIN.STATUS = ? ");
						if (circleId != 0) {
							sql.append(" AND DETAILS.CIRCLE_ID = ");
							sql.append(circleId);				
						}
						if (userRoleId != 0) {
							sql.append(" AND DETAILS.ACCOUNT_LEVEL = ");
							sql.append(userRoleId-1);				
						}
					}else
						{
							sql.append(queryMap.get(SQL_SELECT_ALL_ACC_CHILD_COUNT_KEY));
							sql.append("where LM.STATUS=? AND AD.ACCOUNT_LEVEL="+accountLevelId+") as RECORDS");
							if (circleId != 0) {
								sql.append(" where CIRCLE_ID = "+circleId);
							}
						}
				// logger.info("SQL APPENDED IS : "+sql);
				}
				
				roleName = reportInputDto.getSessionContext().getRoleList().contains("ROLE_AD");
				if(roleName){
					sql.append(" and (DETAILS.PARENT_ACCOUNT= ? OR DETAILS.ROOT_LEVEL_ID= ?) ");
				}
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
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND DETAILS.MOBILE_NUMBER LIKE ?");
					}
					else
					{
						sql.append(" AND MOBILE_NUMBER LIKE ?");
					}
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					flagParent = true;
					/*if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND DETAILS.PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN.LOGIN_NAME) LIKE ? ) ");
					}
					else
					{
						sql.append(" AND DETAILS.PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					}*/
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ?");
					}
					else
					{
						sql.append(" AND UPPER(ACCOUNT_NAME) LIKE ?");
					}
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?");
					}
					else
					{
						sql.append(" AND UPPER(LOGIN_NAME) LIKE ?");
					}
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID )
					{
						sql.append(" AND UPPER(DETAILS.EMAIL) LIKE ?");
					}
					else
					{
						sql.append(" AND UPPER(EMAIL) LIKE ?");
					}
				}
				 else if (searchType.trim().equalsIgnoreCase(
				 Constants.SEARCH_TYPE_ALL)) {
				// /*
				// * all child and sub-child and sub-sub-child accounts upto
				// * n-th level
				// */
					sql.append(" "); 	
				 }
				
				sql.append("  with ur ");

				logger.info("sql from account dao......." + sql);
				if(userRoleId==Constants.DISTRIBUTOR_ID || userRoleId==Constants.ADMIN_ID || userRoleId==Constants.MOBILITY_ID || userRoleId==Constants.CIRCLE_ADMIN_ID){
					ps = connection.prepareStatement(sql.toString());
				
				ps.setLong(paramCount++, parentId);
				}else{
					ps = connection.prepareStatement(sql.toString());
				}
				if(!(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.ADMIN_ID+"")) && !(reportInputDto.getSessionContext().getAccountLevel().equalsIgnoreCase(Constants.IT_HELP_DESK_LEVEL+""))){
					ps.setInt(paramCount++, levelId);
					ps.setInt(paramCount++, levelId);
				}
				ps.setString(paramCount++, status);
				if(roleName){
					ps.setLong(paramCount++, parentId);
					ps.setLong(paramCount++, parentId);
				}
				if (startDt != null && !startDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							startDt).getTime()));
				}
				if (endDt != null && !endDt.equals("")) {
					ps.setDate(paramCount++, new java.sql.Date(Utility.getDate(
							endDt).getTime()));
				}

				if (searchValue != null && !searchValue.equals("") && !flagParent ) {
					ps.setString(paramCount++, "%"+searchValue.toUpperCase() + "%");
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
		logger.info("here in rdbms>>>");
		logger.info("Started ...searchType " + searchType
				+ " AND searchValue:=  " + searchValue + " AND circleId "
				+ circleId + " AND  accountLevelId:- " + accountLevelId);

		int accLevelId = Integer.parseInt(accountLevelId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> accountList = new ArrayList<DPCreateAccountDto>();
		String query = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(queryMap.get(SQL_SELECT_ALL_ACC_KEY));
			sql.append(" AND VLM.STATUS = '"+Constants.ACTIVE_STATUS+"' AND DETAILS.circle_id = ?");
			if (searchType != null) {
				// search according to mobile no
				if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_MOBILE)) {
					sql
							.append(" AND DETAILS.MOBILE_NUMBER LIKE ?  ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
					ps.setString(5, String.valueOf(ub));
					ps.setString(6, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
					ps.setString(5, String.valueOf(ub));
					ps.setString(6, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=? with ur";
					logger.info("query in rdbms>>>"+query);
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
					ps.setString(5, String.valueOf(ub));
					ps.setString(6, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(VLM.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a )b "
							+ "Where rnum<=? and rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
					ps.setString(5, String.valueOf(ub));
					ps.setString(6, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, "%"+searchValue.toUpperCase() + "%");
					ps.setString(5, String.valueOf(ub));
					ps.setString(6, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a )b "
							+ "Where rnum<=? and rnum>=? with ur";
					ps = connection.prepareStatement(query);
					ps.setInt(1, accLevelId);
					ps.setInt(2, accLevelId);
					ps.setInt(3, circleId);
					ps.setString(4, String.valueOf(ub));
					ps.setString(5, String.valueOf(lb + 1));
				}
			}
			logger.info(" this is sql getParentSearchAccountList :-"
					+ sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("ACC_PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				accountDto.setTradeId(rs.getInt("DIST_CHANNEL_ID"));
				accountDto.setTradeCategoryId(rs.getInt("CHANNEL_CATEGORY_ID"));
				accountDto.setOutletType(rs.getInt("OUTLET_TYPE")+"");
				accountList.add(accountDto);
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
		DPCreateAccountDto accountDto = null;
		ArrayList<DPCreateAccountDto> billableaccountList = new ArrayList<DPCreateAccountDto>();
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
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_PARENT)) {
					/* search according to parent account */
					sql
							.append(" AND PARENT_ACCOUNT IN (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) LIKE ? ) ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_NAME)) {
					/* search according to account name */
					sql
							.append(" AND UPPER(DETAILS.ACCOUNT_NAME) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_CODE)) {
					/* search ACCORDING TO ACCOUNT CODE */
					sql
							.append(" AND UPPER(LOGIN.LOGIN_NAME) LIKE ?  ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_EMAIL)) {
					/* search according to email */
					sql
							.append(" AND UPPER(DETAILS.EMAIL) LIKE ? ORDER BY LOGIN_NAME ");
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
							+ "Where rnum<=? and rnum>=?";
					ps = connection.prepareStatement(query);
					ps.setInt(1, circleId);
					ps.setString(2, "%"+searchValue.toUpperCase() + "%");
					ps.setString(3, String.valueOf(ub));
					ps.setString(4, String.valueOf(lb + 1));
				} else if (searchType.trim().equalsIgnoreCase(
						Constants.SEARCH_TYPE_ALL)) {
					/* all accounts */
					query = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM ("
							+ sql.toString()
							+ ") a)b "
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
				accountDto = new DPCreateAccountDto();

				// new change group name added
				accountDto.setAccountId(rs.getInt("ACCOUNT_ID"));
				accountDto.setAccountCode(rs.getString("LOGIN_NAME"));
				accountDto.setParentAccount(rs.getString("PARENT"));
				accountDto.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				accountDto.setSimNumber(rs.getString("SIM_NUMBER"));
				accountDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				accountDto.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				accountDto.setEmail(rs.getString("EMAIL"));
				accountDto.setGroupName(rs.getString("GROUP_NAME"));
				accountDto.setCategory(rs.getString("CATEGORY"));
				// accountDto.setParentAccount(rs.getString("PCODE"));
				accountDto.setCircleName(rs.getString("CIRCLE_NAME"));
				accountDto.setStatus(rs.getString("STATUS"));
				accountDto.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
				accountDto.setOperatingBalance(rs.getDouble("OPERATING_BALANCE"));
				accountDto.setAvailableBalance(rs.getDouble("AVAILABLE_BALANCE"));
				billableaccountList.add(accountDto);
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

			ps = connection.prepareStatement(queryMap
					.get(SQL_SELECT_CIRCLE_NAME_KEY));
			ps.setInt(1, circleId);
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
}