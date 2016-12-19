/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.dao;

import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for log transaction implementation
 * 
 * @author sushilku
 * 
 */
public interface LogTransactionDao {
	/**
	 * This method inserts transaction logs.
	 * 
	 * @param transactionDetails
	 * @throws DAOException
	 */
	public void insertLog(ActivityLog activityLog)
			throws DAOException;

}
