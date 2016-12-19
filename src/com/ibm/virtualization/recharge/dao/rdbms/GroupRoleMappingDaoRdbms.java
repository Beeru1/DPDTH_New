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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.GroupRoleMappingDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.GroupRoleMapping;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GroupRoleMappingDaoRdbms implements GroupRoleMappingDao {

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(GroupDaoRdbms.class
			.getName());

	/* SQL Used within DaoImpl */

	protected static final String SQL_ROLE_SELECT_ASSIGNED = "SELECT GM.ROLE_ID,ROLE_NAME FROM VR_ROLE_MASTER RM ,VR_GROUP_ROLE_MAPPING GM WHERE RM.ROLE_ID=GM.ROLE_ID AND GROUP_ID=?";

	/* for Roles assigned to the selected group & ChannelType(if itz not null) */
	protected static final String SQL_ROLE_SELECT_ASSIGNED_CHANNEL_TYPE = "SELECT GM.ROLE_ID,ROLE_NAME,RM.CHANNEL_TYPE  FROM VR_ROLE_MASTER RM ,VR_GROUP_ROLE_MAPPING GM WHERE RM.ROLE_ID=GM.ROLE_ID AND GROUP_ID=? ";

	protected static final String SQL_ROLE_SELECT_NOT_ASSIGNED = "SELECT ROLE_ID,ROLE_NAME FROM VR_ROLE_MASTER WHERE ROLE_ID NOT IN(SELECT ROLE_ID FROM VR_GROUP_ROLE_MAPPING WHERE GROUP_ID=?) ORDER BY ROLE_ID";

	/*
	 * for Roles not assigned to the selected group & ChannelType(if itz not
	 * null)
	 */
	protected static final String SQL_ROLE_SELECT_NOT_ASSIGNED_CHANNEL_TYPE = "SELECT ROLE_ID,ROLE_NAME,CHANNEL_TYPE FROM VR_ROLE_MASTER WHERE ROLE_ID NOT IN(SELECT ROLE_ID FROM VR_GROUP_ROLE_MAPPING WHERE GROUP_ID=?) ";

	/*
	 * Delete Existing roles for the Selected Group + ChannelType(if itz not
	 * null)
	 */
	protected static final String SQL_ROLES_DELETE = "DELETE from VR_GROUP_ROLE_MAPPING WHERE VR_GROUP_ROLE_MAPPING.GROUP_ID = ? AND ROLE_ID IN (select GM.ROLE_ID  from VR_GROUP_ROLE_MAPPING GM, VR_ROLE_MASTER RM WHERE  RM.ROLE_ID=GM.ROLE_ID  AND  GM.GROUP_ID = ? ";

	/* Insert roles for the Selected Group */
	protected static final String SQL_ROLES_INSERT = "INSERT INTO VR_GROUP_ROLE_MAPPING(GROUP_ID,ROLE_ID) VALUES(?,?)";

	/* Update GroupDetails table on updating the GroupRole Mapping */
	protected static final String SQL_GROUPDETAILS_UPDATE = "UPDATE VR_GROUP_DETAILS SET UPDATED_DT=?, UPDATED_BY=? WHERE GROUP_ID=?";

	private Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public GroupRoleMappingDaoRdbms(Connection conn) {
		this.connection = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingDaoRdbms#listRoleAssigned(java.lang.integer)
	 */

	public ArrayList getRoleAssignedList(int groupId) throws DAOException {

		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<GroupRoleMapping> listRoleAssigned = null;

		try {
			/* Get the connection from DBConnection class */
			ps = connection.prepareStatement(SQL_ROLE_SELECT_ASSIGNED);
			ps.setLong(1, groupId);
			rs = ps.executeQuery();
			listRoleAssigned = new ArrayList<GroupRoleMapping>();
			while (rs.next()) {

				GroupRoleMapping groupRole = new GroupRoleMapping();
				groupRole.setRoleId(rs.getInt("ROLE_ID"));
				groupRole.setRoleName(rs.getString("ROLE_NAME"));
				listRoleAssigned.add(groupRole);
			}
			return listRoleAssigned;

		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: ", sqle);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_ROLE_LIST_NOT_EXIST);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.GroupRoleMappingDao#getRoleAssignedList(int,
	 *      com.ibm.virtualization.recharge.common.ChannelType)
	 */
	public ArrayList getRoleAssignedList(int groupId, ChannelType channelType)
			throws DAOException {

		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<GroupRoleMapping> listRoleAssigned = null;
		try {
			StringBuilder queryObj = new StringBuilder();
			queryObj.append(SQL_ROLE_SELECT_ASSIGNED_CHANNEL_TYPE);
			if(ChannelType.ALL!=channelType){
				/* add channel type if channel type != ALL */
				queryObj.append(" AND RM.CHANNEL_TYPE = ?");
			}
			ps = connection.prepareStatement(queryObj.toString());
			ps.setLong(1, groupId);
			/* add channel type if channel type != ALL */
			if(ChannelType.ALL!=channelType){
				ps.setInt(2, channelType.getValue());
			}

			rs = ps.executeQuery();
			listRoleAssigned = new ArrayList<GroupRoleMapping>();
			while (rs.next()) {

				GroupRoleMapping groupRole = new GroupRoleMapping();
				groupRole.setRoleId(rs.getInt("ROLE_ID"));
				/* show role name only if the channel type != ALL */
				if(ChannelType.ALL!=channelType) {
					groupRole.setRoleName(rs.getString("ROLE_NAME"));
				} else {
					/* show role_name$ChannelType if the channel type = ALL */
					groupRole.setRoleName(rs.getString("ROLE_NAME")
							+ "$"
							+ ChannelType.getChannelType(rs
									.getInt("CHANNEL_TYPE")));
				}
				/* add list to the arraylist */
				listRoleAssigned.add(groupRole);
			}
			return listRoleAssigned;

		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: ", sqle);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_ROLE_LIST_NOT_EXIST);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
			logger.info("Executed ....");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingDaoRdbms#getRoleNotAssignedList(java.lang.integer)
	 */

	public ArrayList getRoleNotAssignedList(int groupId) throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<GroupRoleMapping> roleListNotAssigned = null;

		try {
			/* Get the connection from DBConnection class */
			ps = connection.prepareStatement(SQL_ROLE_SELECT_NOT_ASSIGNED);
			ps.setLong(1, groupId);
			rs = ps.executeQuery();
			roleListNotAssigned = new ArrayList<GroupRoleMapping>();
			while (rs.next()) {
				GroupRoleMapping groupRole = new GroupRoleMapping();
				groupRole.setRoleId(rs.getInt("ROLE_ID"));
				groupRole.setRoleName(rs.getString("ROLE_NAME"));
				roleListNotAssigned.add(groupRole);
			}
			return roleListNotAssigned;
		} catch (SQLException sqle) {
			logger.fatal("SQL Exception occured while find."
					+ "Exception Message: ", sqle);
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
	 * @see com.ibm.virtualization.recharge.dao.GroupRoleMappingDao#getRoleNotAssignedList(int,
	 *      com.ibm.virtualization.recharge.common.ChannelType)
	 */
	public ArrayList getRoleNotAssignedList(int groupId, ChannelType channelType)
			throws DAOException {
		logger.info("Started... ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<GroupRoleMapping> roleListNotAssigned = null;

		try {
			StringBuilder queryObj = new StringBuilder();
			queryObj.append(SQL_ROLE_SELECT_NOT_ASSIGNED_CHANNEL_TYPE);
			/* add channel type if channel type = ALL */
			if(ChannelType.ALL!=channelType){
				queryObj.append(" AND CHANNEL_TYPE = ? ORDER BY ROLE_ID");
			} else {
				queryObj.append(" ORDER BY ROLE_ID");
			}
			ps = connection.prepareStatement(queryObj.toString());
			ps.setLong(1, groupId);
			/* set channelType in query if the channel type = ALL */
			if(ChannelType.ALL!=channelType){
				ps.setInt(2, channelType.getValue());
			}
			rs = ps.executeQuery();
			roleListNotAssigned = new ArrayList<GroupRoleMapping>();
			while (rs.next()) {
				GroupRoleMapping groupRole = new GroupRoleMapping();
				groupRole.setRoleId(rs.getInt("ROLE_ID"));
				if(ChannelType.ALL!=channelType){
					/* show role_name only if the channel type = ALL */
					groupRole.setRoleName(rs.getString("ROLE_NAME"));
				} else {
					/* show role_name$ChannelType if the channel type = ALL */
					groupRole.setRoleName(rs.getString("ROLE_NAME")
							+ "$"
							+ ChannelType.getChannelType(rs
									.getInt("CHANNEL_TYPE")));
				}
				roleListNotAssigned.add(groupRole);
			}
			return roleListNotAssigned;
		} catch (SQLException sqle) {
			logger.fatal("SQL Exception occured while find."
					+ "Exception Message: ", sqle);
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
	 * @see com.ibm.virtualization.recharge.service.GroupRoleMappingDaoRdbms#updateRoles(com.ibm.virtualization.recharge.dto.GroupRoleMapping)
	 */

	public void updateRoles(GroupRoleMapping groupRole, ChannelType channelType)
			throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;

		try {
			/*
			 * delete existing roles for the selected Group + channle type(if
			 * itz not null)
			 */
			StringBuilder queryDelObj = new StringBuilder();
			queryDelObj.append(SQL_ROLES_DELETE);
			/* add channel type in query if channel_type != ALL */
			if (ChannelType.ALL!=channelType){	
				queryDelObj.append(" AND RM.CHANNEL_TYPE = ?)");
			} else {
				queryDelObj.append(" )");
			}
			ps = connection.prepareStatement(queryDelObj.toString());
			logger.info("Query is =" + queryDelObj.toString());
			ps.setInt(1, groupRole.getGroupId());
			ps.setInt(2, groupRole.getGroupId());
			/* set channel type in query if channel_type != ALL */
			if (ChannelType.ALL!=channelType){
				ps.setInt(3, channelType.getValue());
			}
			ps.executeUpdate();
			ps.clearParameters();
			/* insert roles for the selected Group */
			ps = connection.prepareStatement(SQL_ROLES_INSERT);
			logger.info("query insert is" + SQL_ROLES_INSERT);
			for (int i = 0; i < groupRole.getRolesId().length; i++) {
				ps.setInt(1, groupRole.getGroupId());
				ps.setInt(2, groupRole.getRolesId()[i]);
				ps.executeUpdate();
			}
			ps.clearParameters();
			/* Update groupDetails table */
			ps = connection.prepareStatement(SQL_GROUPDETAILS_UPDATE);
			logger.info("query insert is" + SQL_GROUPDETAILS_UPDATE);
			ps.setTimestamp(1, Utility.getDateTime());
			ps.setLong(2, groupRole.getUpdatedBy());
			ps.setInt(3, groupRole.getGroupId());
			ps.executeUpdate();
			DBConnectionManager.commitTransaction(connection);
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: ", sqle);
			throw new DAOException(
					ExceptionCode.GroupRole.ERROR_ROLE_NOT_UPDATED);

		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);

		}
		logger.info("Executed ....");
	}
}
