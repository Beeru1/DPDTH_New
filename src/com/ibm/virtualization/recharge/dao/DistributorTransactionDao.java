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

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface for DistributorTransaction Implementation.
 * 
 * @author bhanu 
 * 
 */

public interface DistributorTransactionDao {

	/**
	 * This methods update the distributor transaction details
	 * 
	 * @param dto
	 * @return update row
	 * @throws DAOException
	 */

	public int updateDistributorTransaction(
			DistributorTransaction distributorTransaction) throws DAOException;

	/**
	 * this method insert new transaction data into table VR_ACCOUNT_TRANSACTION
	 * 
	 * @param DistributorTransaction
	 * @throws DAOException
	 */
	public void insertDistributorTransactionData(
			DistributorTransaction distributorTransaction) throws DAOException;

	/**
	 * this method get distributor transaction list for the lower bound and upper bound limit
	 * specified only.
	 * 
	 * @return ArrayList
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @throws DAOException
	 */
	public ArrayList getDistributorTransactionList(ReportInputs mtDTO, int lowerBound , int upperBound)
			throws DAOException;

	/**
	 * This Methos get Distributor Transaction Details for review purpose
	 * 
	 * @param trasactionId
	 * @return DistributorTransaction
	 * @throws VirtualizationServiceException
	 */

	public DistributorTransaction getDistributorTransactionReviewDetail(
			long transactionId) throws DAOException;
	
	/**
	 * This method is used to get the count of the total transactions done by the distributor.
	 * 
	 * @param mtDTO
	 * @return int
	 * @throws DAOException
	 */
	public int getDistributorTransactionCount(ReportInputs mtDTO) throws DAOException;

	/**
	 * This method will be used provide a list of all distributor transactions
	 * for internal user logged in. This will provide all the records and is independent of 
	 * pagination logic.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException - exception which will be thrown incase any exception 
	 * occurs while fetching the records.
	 */
	public ArrayList getDistributorTransactionListAll(ReportInputs mtDTO)
			throws DAOException;
	
	

}
