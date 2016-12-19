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

import com.ibm.virtualization.recharge.dto.RechargeTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for recharge related methods.
 * 
 * @author Mohit
 * 
 */
public interface RechargeService {

	/**
	 * This method starts recharge transaction if user is authorised for
	 * recharge. before calling this service circle id and source account id
	 * must be set into RechargeTransaction dto
	 * 
	 * @param context
	 *            UserSessionContext
	 * @param rechargeTransaction
	 * @throws VirtualizationServiceException
	 */
	public int startTransaction(
			RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method commits recharge transaction
	 * 
	 * @param accountId
	 * @param transactionId
	 * @param transactionAmount
	 * @throws VirtualizationServiceException
	 */
	public void commitTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method add the given amount in operating balance for the given
	 * accountCode.
	 * 
	 * @param rechargeTransaction
	 * @throws VirtualizationServiceException
	 */
	public void rollbackTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException;

	/**
	 * Account to Customer Transaction: It is invoked through WEB This method
	 * transfers the recharge amount from Sorce account to Customers Account.
	 * 
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */
	public int doCustomerTransaction(
			RechargeTransaction transactionDTO)
			throws VirtualizationServiceException;

	/**
	 * This function finds the next TransactionID
	 * 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public long getTransactionId() throws VirtualizationServiceException;

	/**
	 * Account to Customer Transaction: It is invoked through WEB This method
	 * confirms from the user after displaying the Details for the Transaction.
	 * 
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */
	public void calculateTransactionInfo(
			RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException;

}