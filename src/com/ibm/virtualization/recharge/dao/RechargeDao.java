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

import java.sql.SQLException;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for RechargeDao Implementation.
 * 
 * @author Mohit
 * 
 */
public interface RechargeDao {
	/**
	 * This method inserts a record into the table:CUSTOMER_TRANSACTIONS
	 * 
	 * @param RechargeTransaction
	 * @return transactionStatus
	 * @throws DAOException
	 */
	public int insertTransactionMasterPreProcess(TransactionMaster transactionMaster)
			throws DAOException;
	
	
	/**
	 * This method inserts a record into the table:CUSTOMER_TRANSACTIONS
	 * 
	 * @param RechargeTransaction
	 * @return transactionStatus
	 * @throws DAOException
	 */
	public void insertTransactionMaster(TransactionMaster transactionMaster)
	throws  DAOException ;

	/**
	 * This function updates the status of a record(transaction) in the Table:
	 * CUSTOMER_TRANSACTIONS
	 * 
	 * @param transactionId
	 * @param flag
	 *            True : If the transaction status is to be set true. "S" False :
	 *            If the transaction status is to be set to "F"
	 * @throws DAOException
	 */
	public void updateTransactionStatus(long transactionId,
			TransactionStatus transactionStatus, String reasonId, long updatedBy, String responseCode)
			throws DAOException;

	/**
	 * This method returns the next TransactionId
	 * 
	 * @return
	 * @throws DAOException
	 */
	public long getTransactionId() throws DAOException;


}