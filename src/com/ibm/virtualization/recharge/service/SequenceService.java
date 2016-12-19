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

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Service for fetching the next transaction id
 * 
 * @author ashish
 * 
 */
public interface SequenceService {
	/**
	 * Returns a new transaction Id for Query
	 * 
	 * @return a transaction id
	 * @throws VirtualizationServiceException
	 */
	public long getNextTransID() throws VirtualizationServiceException;

	/**
	 * Returns a new transaction Id for recharge
	 * 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public long getNextRechargeTransID() throws VirtualizationServiceException;

}
