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

import com.ibm.virtualization.recharge.dto.PostpaidTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for post paid related methods.
 * 
 * @author Sushil
 * 
 */
public interface PostPaidService {

	/**
	 * This method starts post paid transaction if user is authorised for post
	 * paid payment. before calling this service circle id and source account id
	 * must be set into dto
	 * 
	 * @param context
	 *            UserSessionContext
	 * @param postpaidTransaction
	 * @throws VirtualizationServiceException
	 */
	public int startPostpaidTransaction(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method commits post paid transaction
	 * 
	 * @param postpaidTransaction
	 * @throws VirtualizationServiceException
	 */
	public void commitPostpaidTransactions(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method add the given amount in operating balance for the given
	 * accountCode.
	 * 
	 * @param postpaidTransaction
	 * @throws VirtualizationServiceException
	 */
	public void rollbackPostpaidTransaction(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method is used.This method transfers the recharge amount from Sorce
	 * account to Customers Account.
	 * 
	 * @param transactionDTO
	 * @throws VirtualizationServiceException
	 */
	public int doPostpaidTransaction(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

	/**
	 * Process selfcare for billing
	 * 
	 * @param postpaidTransaction
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public double processSelfCare(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method calculate sthe ESP +t also checks whether the Transaction is
	 * dublicate or blackout.
	 * 
	 * @param postpaidTransaction
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public void calculateESPforConfirmation(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException;

}