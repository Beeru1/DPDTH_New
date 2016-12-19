/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service;

import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface TransactionLoggerService {
	/**
	 * This method log the transactionss
	 * 
	 * @param transactionDetails
	 * @throws VirtualizationServiceException
	 */
	public void log(ActivityLog activityLog) throws VirtualizationServiceException;

	/*
	 * This method is used to insert into transaction master in case of time out at listener.
	 * 
	 */
	public void insertTransactionMaster(TransactionMaster transactionMaster )
	throws VirtualizationServiceException ;
}
