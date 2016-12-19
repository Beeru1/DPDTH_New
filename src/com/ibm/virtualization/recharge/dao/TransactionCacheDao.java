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

import java.util.Map;

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for TransactionCacheDao Implementation.
 * 
 * @author Mohit Aggarwal
 */
public interface TransactionCacheDao {

	/**
	 * Method to initialize cache.
	 * 
	 * throws DAOException
	 */
	public Map<Integer, Long> populateTransactionCache() throws DAOException;

}
