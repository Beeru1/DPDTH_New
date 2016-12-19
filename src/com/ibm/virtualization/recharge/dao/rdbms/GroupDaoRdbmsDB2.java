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

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.DAOException;


/**
 * This class provides the implementation for all the method declarations in
 * GroupDao interface
 * 
 * @author Paras
 */
public class GroupDaoRdbmsDB2 extends GroupDaoRdbms {

	public GroupDaoRdbmsDB2(Connection connection) {
		super(connection);
		queryMap.put(SQL_GROUP_INSERT_KEY,SQL_GROUP_INSERT);
	}

	protected  static final String SQL_GROUP_INSERT = "INSERT INTO VR_GROUP_DETAILS (GROUP_ID, GROUP_NAME, DESCRIPTION, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT,TYPE) VALUES (next value for seq_vr_group_details , ?, ?, ?, ?, ?, ?,?)";


	
}
