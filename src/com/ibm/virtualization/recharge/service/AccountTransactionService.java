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

import java.util.ArrayList;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.AccountTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for Account & Customer management service.
 * 
 * @author Mohit
 * 
 */
public interface AccountTransactionService {

	/**
	 * Account to Account Transaction :This method calculates the amount to be
	 * credited , debited + perform some validations during sync call
	 * 
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */

	public void confirmTransaction(AccountTransaction transactionDTO)
			throws VirtualizationServiceException;

	/**
	 * Account to Account Transaction :This method actually updates the Source &
	 * destination balances during sync call after the confirmation is provide
	 * by user.
	 * 
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */
	public int doAccountTransactionSyncAfterConfirmation(
			AccountTransaction transactionDto)
			throws VirtualizationServiceException;

	/**
	 * Account to Account Transaction :This method recharges the destination
	 * account by the transaction amount by async call
	 * 
	 * @param context
	 *            context is basically contains details of logged in user.
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */

	public int doAccountTransaction(AccountTransaction transactionDTO)
			throws VirtualizationServiceException;

	/**
	 * This function gets all the Child Account for the SourceAccount(which is
	 * in session)
	 * 
	 * @param SourceAccountCode
	 * @return ArrayList of childs
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getChildAccounts(long sourceAccountId)
			throws VirtualizationServiceException;

	/**
	 * Updates the status of the transaction in the master table
	 * 
	 * @param loggerDto
	 * @throws VirtualizationServiceException
	 */
	public void updateTransactionStatus(TransactionStatus transactionStatus,
			long accountId, long transId, String reasonId)
			throws VirtualizationServiceException;

	/**
	 * This function retrieves the Available balance for an Account
	 * 
	 * @param accountId
	 * 
	 */
	public double getBalance(long accountId)
			throws VirtualizationServiceException;
	
	/**
	 * This function gets Account details for given account mobileNo
	 * 
	 * @param destinationMobileNo
	 * @return Account Details
	 * @throws VirtualizationServiceException
	 */
	public Account getDestinationAccountDetails(String destinationMobileNo)
	throws VirtualizationServiceException;

}