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

import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.TransactionReport;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * ReportDao encapsulates data access and manipulation for transactions. It
 * abstracts data and functionality for a data source that stores information
 * for Transactions
 * 
 * Separates a data resource's interface from its data access implementation. It
 * shields the service from modifications to the underlying data source.
 * 
 * @author ashish
 */
public interface ReportDao {

	/**
	 * This method will be used provide a list of account to account
	 * transactions for internal user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records.
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	/*public ArrayList getTransactionRptA2AList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException;*/

	/**
	 * This method will be used provide a list of account to account
	 * transactions for external user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records.
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransactionRptA2AChildList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException;

	

	

	

	

	/**
	 * This method will be used provide a list of all account to account
	 * transfer summary for internal user logged in independent of pagination
	 * logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransferSummaryRptA2AListAll(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This method will be used provide a list of all account to account
	 * transfer summary for external user logged in independent of pagination
	 * logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getTransferSummaryRptA2AChildListAll(
			ReportInputs mtDTO) throws DAOException;

	
	
	/*

	public ArrayList getTransactionRptListInternal(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport) throws DAOException;

	public ArrayList getTransactionRptChildListExternal(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport) throws DAOException;
	*/
	
	public ArrayList getTransactionRptList(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport) throws DAOException;
	
	/* This method displays the RechargeCustomerTransactionReport */
	public ArrayList getRechargeTransactionRptList(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport,String transId) throws DAOException;

	
	/**
	 * This method returns a details of a transaction based on the transactionId.
	 * 
	 * @param transactionId
	 * @return TransactionReport
	 */
	public TransactionReport getTransactionRptListView(int transactionId,String requestType) throws DAOException;



	public ArrayList getTransactionRptListAll(ReportInputs mtDTO)
			throws DAOException;

	public ArrayList getTransactionRptChildListAll(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type for Transaction Summary Report.(if user internal )
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getTransactionSummaryRptData(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type for Transaction Summary Report.(if user external )
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */

	public ArrayList getTransactionSummaryRptChildData(ReportInputs mtDTO)
			throws DAOException;
	

    /**
     * this method search a2a transaction list 
     * @param mtDTO
     * @param lowerBound
     * @param upperBound
     * @param isExport
     * @return
     * @throws DAOException
     */
	public ArrayList getTransactionRptA2AList(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport) throws DAOException;
	
	/**
	 * This method finds the record for the particular transaction id
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCustomerTransactionListWithId(String transactionId,String transType)	throws DAOException;
	
	
	/**
	 * This method will be used provide a list of customer transactions
	 * for internal user logged in.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * @param lowerBound - specifies the number from which to fetch the records.
	 * @param upperBound - specifies the number up to which to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException - exception which will be thrown incase any exception 
	 * occurs while fetching the records.
	 */
	public ArrayList getCustomerTransactionList(ReportInputs mtDTO, int lowerBound , int upperBound)
			throws DAOException;
	
	/**
	 * This method will be used provide a list of all customer transactions
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
	public ArrayList getCustomerTransactionListAll(ReportInputs mtDTO)
			throws DAOException;
	
	/**
	 * This method will be used provide a list of customer transactions
	 * for external user logged in.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * @param lowerBound - specifies the number from which to fetch the records
	 * @param upperBound - specifies the number up to which to fetch the records
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException - exception which will be thrown incase any exception 
	 * occurs while fetching the records.
	 */
	public ArrayList getCustomerChildAccountList(ReportInputs mtDTO, int lowerBound , int upperBound)
			throws DAOException;
	
	/**
	 * This method will be used provide a list of all customer transactions
	 * for external user logged in. This will provide all the records and is independent of 
	 * pagination logic.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException - exception which will be thrown incase any exception 
	 * occurs while fetching the records.
	 */
	public ArrayList getCustomerChildAccountListAll(ReportInputs mtDTO)
			throws DAOException;
	
	
	
	/**
	 * This method will be used provide a list of all customer transactions
	 * for external user logged in. This will provide all the records and is independent of 
	 * pagination logic.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException - exception which will be thrown incase any exception 
	 * occurs while fetching the records.
	 */
	public ArrayList getCustomerTransactionRptList(ReportInputs mtDTO,
			int lowerBound, int upperBound, boolean isExport,String transId)
			throws DAOException;

}
