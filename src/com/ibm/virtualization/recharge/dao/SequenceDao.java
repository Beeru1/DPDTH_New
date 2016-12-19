/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/
package com.ibm.virtualization.recharge.dao;

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface definition for transaction id generation
 * 
 * @author ashish
 * 
 */
public interface SequenceDao {
	/**
	 * generates a new transaction Id for query and account to account transfer
	 * 
	 * @return
	 * @throws DAOException
	 */
	public long getNextTransID() throws DAOException;

	/**
	 * generates a new transaction Id for recharge
	 * 
	 * @return
	 * @throws DAOException
	 */
	public long getNextTransIDForRecharge() throws DAOException;
}
