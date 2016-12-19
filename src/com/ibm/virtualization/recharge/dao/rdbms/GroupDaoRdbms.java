/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.GroupDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * GroupDao interface
 * 
 * @author Paras
 */
public class GroupDaoRdbms extends BaseDaoRdbms implements GroupDao {

	/*
	 * Logger for this class.
	 */
	protected static Logger logger = Logger.getLogger(GroupDaoRdbms.class
			.getName());

	protected static final String SQL_GROUP_SELECT_ALL_KEY1 = "SQL_GROUP_SELECT_ALL1";
	protected static final String SQL_GROUP_SELECT_ALL1 = "SELECT GROUP_ID, GROUP_NAME, TYPE, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT FROM VR_GROUP_DETAILS where GROUP_ID>? ORDER BY GROUP_ID";
	
	protected static final String SQL_GROUP_SELECT_ALL_KEY = "SQL_GROUP_SELECT_ALL";
	protected static final String SQL_GROUP_SELECT_ALL = "SELECT GROUP_ID, GROUP_NAME, TYPE, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT FROM VR_GROUP_DETAILS ORDER BY GROUP_ID";

	protected static final String SQL_GROUP_SELECT_GROUP_ON_NAME_KEY = "SQL_GROUP_SELECT_GROUP_ON_NAME";
	protected static final String SQL_GROUP_SELECT_GROUP_ON_NAME = "SELECT GROUP_ID, GROUP_NAME, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT,TYPE FROM VR_GROUP_DETAILS WHERE UPPER(GROUP_NAME) = ? ";

	protected static final String SQL_GROUP_SELECT_GROUP_ON_ID_KEY = "SQL_GROUP_SELECT_GROUP_ON_ID";
	protected static final String SQL_GROUP_SELECT_GROUP_ON_ID = "SELECT GROUP_ID, GROUP_NAME, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT,TYPE FROM VR_GROUP_DETAILS WHERE GROUP_ID = ? ";
	
	protected static final String SQL_GROUP_UPDATE_KEY = "SQL_GROUP_UPDATE";
	protected static final String SQL_GROUP_UPDATE = "UPDATE VR_GROUP_DETAILS SET GROUP_ID = ?, GROUP_NAME = ?, DESCRIPTION = ?, UPDATED_BY = ?, UPDATED_DT = ? ,TYPE=? WHERE GROUP_ID = ?";

	protected static final String SQL_GROUP_INSERT_KEY = "SQL_GROUP_INSERT";
	protected static final String SQL_GROUP_INSERT = "INSERT INTO VR_GROUP_DETAILS (GROUP_ID, GROUP_NAME, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT,TYPE) VALUES (SEQ_VR_GROUP_DETAILS.nextval, ?, ?, ?, ?, ?, ?,?)";

	protected static final String SQL_GROUP_NAME_KEY = "SQL_GROUP_NAME";
	protected static final String SQL_GROUP_NAME = "SELECT GROUP_NAME FROM VR_GROUP_DETAILS WHERE GROUP_ID = ?";

	protected static final String SQL_GROUP_TYPE_SELECT_ALL_KEY = "SQL_GROUP_TYPE_SELECT_ALL";

	protected static final String SQL_GROUP_TYPE_SELECT_ALL = DBQueries.SQL_GROUP_TYPE_SELECT_ALL;
	//	protected static final String SQL_GROUP_TYPE_SELECT_ALL = "SELECT GROUP_ID, GROUP_NAME, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT FROM VR_GROUP_DETAILS  WHERE TYPE =? ORDER BY GROUP_NAME";

	protected static final String SQL_GROUP_SELECT_FOR_ADMIN_KEY = "SQL_GROUP_SELECT_FOR_ADMIN";
	protected static final String SQL_GROUP_SELECT_FOR_ADMIN = DBQueries.SQL_GROUP_SELECT_FOR_ADMIN;
	
//	protected static final String SQL_GROUP_TYPE_SELECT_ND_KEY = "SQL_GROUP_TYPE_SELECT_ND";
//	protected static final String SQL_GROUP_TYPE_SELECT_ND = DBQueries.SQL_GROUP_TYPE_SELECT_ND;
	

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public GroupDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(SQL_GROUP_SELECT_ALL_KEY,SQL_GROUP_SELECT_ALL);
		queryMap.put(SQL_GROUP_SELECT_GROUP_ON_NAME_KEY,SQL_GROUP_SELECT_GROUP_ON_NAME);
		queryMap.put(SQL_GROUP_SELECT_GROUP_ON_ID_KEY,SQL_GROUP_SELECT_GROUP_ON_ID);
		queryMap.put(SQL_GROUP_UPDATE_KEY,SQL_GROUP_UPDATE);
		queryMap.put(SQL_GROUP_INSERT_KEY,SQL_GROUP_INSERT);
		queryMap.put(SQL_GROUP_NAME_KEY,SQL_GROUP_NAME);
		queryMap.put(SQL_GROUP_TYPE_SELECT_ALL_KEY,SQL_GROUP_TYPE_SELECT_ALL);
		queryMap.put(SQL_GROUP_SELECT_FOR_ADMIN_KEY, SQL_GROUP_SELECT_FOR_ADMIN);
		queryMap.put(SQL_GROUP_SELECT_ALL_KEY1, SQL_GROUP_SELECT_ALL1);
	}

	/**
	 * This method creates a new Group.
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#insertGroup()
	 * @param group
	 * @throws DAOException
	 */
	public void insertGroup(Group group) throws DAOException {

		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_INSERT_KEY));
			int paramCount = 1;

			// ps.setString(paramCount++, group.getGroupName().toUpperCase());
			ps.setString(paramCount++, group.getGroupName());
			ps.setString(paramCount++, group.getDescription());
			ps.setLong(paramCount++, group.getCreatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, group.getUpdatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setString(paramCount, group.getType());
			rowsUpdated = ps.executeUpdate();
			logger
					.info("Row insertion successful on table:VR_GROUP_DETAILS. Inserted:"
							+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage(), e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_ALREADY_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}

	/**
	 * This method updates the details of a group
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#updateGroup()
	 * @param group
	 * @throws DAOException
	 */
	public void updateGroup(Group group) throws DAOException {

		logger.info("Started... ");
		PreparedStatement ps = null;
		int numRows = -1;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_UPDATE_KEY));
			int paramCount = 1;
			ps.setInt(paramCount++, group.getGroupId());
			ps.setString(paramCount++, group.getGroupName());
			ps.setString(paramCount++, group.getDescription());
			ps.setLong(paramCount++, group.getUpdatedBy());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setString(paramCount++, group.getType());
			ps.setInt(paramCount, group.getGroupId());
			numRows = ps.executeUpdate();
			logger
					.info("Update successful on table:VR_GROUP_DETAILS. Updated:"
							+ numRows + " rows");
		} catch (SQLException e) {
			logger.error("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_ALREADY_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
			logger.info("Executed ....");
		}
	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return group
	 * @throws SQLException
	 */
	protected static Group populateDto(ResultSet rs) throws SQLException {
		logger.info("Started...  ");
		Group group = new Group();
		group.setGroupId(Integer.parseInt(rs.getString("GROUP_ID")));
		group.setGroupName(rs.getString("GROUP_NAME"));
		group.setDescription(rs.getString("DESCRIPTION"));
		group.setCreatedBy(rs.getLong("CREATED_BY"));
		group.setCreatedDt(rs.getString("CREATED_DT"));
		group.setUpdatedBy(rs.getLong("UPDATED_BY"));
		group.setUpdatedDt(rs.getString("UPDATED_DT"));
		group.setType(rs.getString("TYPE"));
		logger.info("Executed ....");
		return group;
	}

	/**
	 * This method returns a list of groups.
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#getGroups()
	 * @return groupList
	 * @throws DAOException
	 */
	public ArrayList getGroups(int groupId) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_SELECT_ALL_KEY1));
			ps.setInt(1, groupId);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Group group = new Group();
					group.setDescription(rs.getString("DESCRIPTION"));
					group
							.setGroupId(Integer.parseInt(rs
									.getString("GROUP_ID")));
					group.setGroupName(rs.getString("GROUP_NAME"));
					group.setType(rs.getString("TYPE"));
					groupList.add(group);
				}
			}
			if (0 == groupList.size()) {
				logger.error("No groups found by getGroups method.");
				throw new DAOException(
						ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
			}
			return groupList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getGroups."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	public ArrayList getGroups() throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_SELECT_ALL_KEY));
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Group group = new Group();
					group.setDescription(rs.getString("DESCRIPTION"));
					group
							.setGroupId(Integer.parseInt(rs
									.getString("GROUP_ID")));
					group.setGroupName(rs.getString("GROUP_NAME"));
					group.setType(rs.getString("TYPE"));
					groupList.add(group);
				}
			}
			if (0 == groupList.size()) {
				logger.error("No groups found by getGroups method.");
				throw new DAOException(
						ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
			}
			return groupList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getGroups."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}
	/**
	 * This method returns details of a Group based on groupName
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#getGroup()
	 * @param groupName
	 * @return group
	 * @throws DAOException
	 */
	public Group getGroup(String groupName) throws DAOException {
		logger.info("Started...groupName" + groupName);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Group group = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_SELECT_GROUP_ON_NAME_KEY));
			ps.setString(1, groupName.toUpperCase());
			rs = ps.executeQuery();
			if (rs.next()) {
				group = populateDto(rs);
				return group;
			} else {
				// logger.error("getGroup : No group found by getGroup method
				// with groupName = " + groupName);
				// throw new DAOException("errors.group.nogroup");
				return null;
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement, connection. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}
	
	/**
	 * This method returns details of a Group based on groupId
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#getGroup()
	 * @param groupName
	 * @return group
	 * @throws DAOException
	 */
	public Group getGroup(int groupId) throws DAOException{
		logger.info("Started...groupId" + groupId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Group group = null;
		try {

			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_SELECT_GROUP_ON_ID_KEY));
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				group = populateDto(rs);
				return group;
			} else {
				// logger.error("getGroup : No group found by getGroup method
				// with groupName = " + groupName);
				// throw new DAOException("errors.group.nogroup");
				return null;
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while find."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement, connection. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	
	/**
	 * This method returns name of the Group for the groupId provided.
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#getGroupName()
	 * @param groupId
	 * @return groupName
	 * @throws DAOException
	 */
	public String getGroupName(int groupId) throws DAOException {
		logger.info("Started...");
		String groupName = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(queryMap.get(SQL_GROUP_NAME_KEY));
			ps.setInt(1, groupId);
			logger.info("prepared stmt is " + ps.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				groupName = rs.getString("GROUP_NAME");
				logger.info("Group Name retrieved  is : " + groupName);
				return groupName;
			} else {
				logger
						.error(" No group found by getGroupName method with groupId = "
								+ groupId);
				throw new DAOException(
						ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
			}
		} catch (SQLException e) {
			logger.fatal(
					"SQL Exception occured while retrieving from getGroupName(int groupID)"
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupDao#getGroups(java.lang.String)
	 */
	public ArrayList getGroups(String groupId) throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(queryMap.get(SQL_GROUP_TYPE_SELECT_ALL_KEY));
			if(!(groupId.equalsIgnoreCase("1") || Integer.parseInt(groupId) >= Constants.ND_GROUP_ID  )){
				sql.append(" and group.group_id <11 union select group_id,group_name from VR_GROUP_DETAILS	where 2= ? and group_id > ? and group_id <=11 order by group_id with ur");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, Integer.parseInt(groupId));
			ps.setInt(2, Integer.parseInt(groupId));
			ps.setInt(3, Integer.parseInt(groupId));
			}else if (Integer.parseInt(groupId) >= Constants.ND_GROUP_ID ){
				sql.append(" with ur ");
				ps = connection.prepareStatement(sql.toString());
				ps.setInt(1, Integer.parseInt(groupId));
			}
			else{
				ps = connection.prepareStatement(queryMap.get(SQL_GROUP_SELECT_FOR_ADMIN_KEY));
			}
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Group group = new Group();
					group
							.setGroupId(Integer.parseInt(rs
									.getString("GROUP_ID")));
					group.setGroupName(rs.getString("GROUP_NAME"));
					groupList.add(group);
				}
			}
			if (0 == groupList.size()) {
				logger.error("No groups found by getGroups method.");
				throw new DAOException(
						ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
			}
			return groupList;
		} catch (SQLException e) {
			logger.info("SQL Exception occured while getGroups."
					+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

}
