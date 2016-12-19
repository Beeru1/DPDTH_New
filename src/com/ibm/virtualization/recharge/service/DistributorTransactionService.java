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

import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for Distributor Transaction service.
 * 
 * @author bhanu 
 * 
 */
public interface DistributorTransactionService {

	/**
	 * This method insert New Distribtor Transaction data and also calculate
	 * creadited amount
	 * 
	 * @param DistributorTransaction
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public void createDistributorTransaction(
			DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method get All the Distributor Transaction List Based On Search
	 * status like p-pending,R-rejected for the lower bound and upper bound limit
	 * specified only.
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getDistributorTransactionList(ReportInputs mtDTO, int lowerBound , int upperBound)
			throws VirtualizationServiceException;

	/**
	 * This Methos get distributor review details DAO method
	 * 
	 * @param transactionId
	 * @return DistributorTransaction
	 * @throws VirtualizationServiceException
	 */

	public DistributorTransaction getDistributorTransactionReviewDetail(
			long trasactionId) throws VirtualizationServiceException;

	/**
	 * This mehtod update distributor account available balance and tansaction
	 * status
	 * 
	 * @param distributorTransaction
	 * @throws VirtualizationServiceException
	 */
	public void updateDistributorTransaction(
			DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException;

	/**
	 * This method will be used provide total count of the distributor transactions.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * 
	 * @return int - total number of records.
	 * @throws VirtualizationServiceException - exception which will be thrown incase any exception 
	 * occurs at the service level.
	 */
	public int getDistributorTransactionCount(ReportInputs mtDTO)
			throws VirtualizationServiceException;
	
	/**
	 * This method get All the Distributor Transaction List Based On Search
	 * status like p-pending,R-rejected
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getDistributorTransactionListAll(ReportInputs mtDTO)
			throws VirtualizationServiceException;
	
	
}