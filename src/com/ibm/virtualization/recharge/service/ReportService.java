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

import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.TransactionReport;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Service class for Reports as a facade to encapsulate the complexity of
 * interactions between the business objects participating in a workflow.
 * 
 * Provide a uniform coarse-grained service layer to separate business object
 * implementation from business service abstraction. It hides from the client
 * the underlying interactions and interdependencies between business components
 * specific to the Reports. This provides better manageability, centralization
 * of interactions (responsibility), greater flexibility, and greater ability to
 * cope with changes.
 * 
 * @author ashish
 */
public interface ReportService {

	/**
	 * This method will be used provide a list of account to account
	 * transactions..
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records.
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records.
	 * @param isExport -
	 *            call for export to excel(true)  .
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException -
	 *             exception which will be thrown incase any exception occurs at
	 *             the service level.
	 */
	public ArrayList getTransactionRptA2AData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport)
			throws VirtualizationServiceException;
	
	

	

	/**
	 * This method will be used provide a list of all account to account
	 * transfer summary independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException -
	 *             exception which will be thrown incase any exception occurs at
	 *             the service level.
	 */
	public ArrayList getTransferSummaryRptA2ADataAll(ReportInputs mtDTO)
			throws VirtualizationServiceException;

	
	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type for Transaction Report.
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getTransactionRptData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport)
			throws VirtualizationServiceException;
	
	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type for CustomerRechargeTransaction Report.
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getRechargeTransactionRptData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport,String transId)
			throws VirtualizationServiceException;

	
	
	
	/**
	 * This Method returns details of a transaction for a given transactionId.
	 * 
	 * @param transactionId
	 * @return TransactionReport
	 * @throws VirtualizationServiceException
	 */
	public TransactionReport getTransactionRptDataView(int transactionId,String requestType)
	throws VirtualizationServiceException;
	
	
	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type as a list which will be export to a file system for Transaction
	 * Report.
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getTransactionRptDataAll(ReportInputs mtDTO)
			throws VirtualizationServiceException;

	/**
	 * This mehtod returns list of transaction details searched on : Transaction
	 * Type for Transaction Summary Report.
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getTransactionSummaryRptData(ReportInputs mtDTO)
			throws VirtualizationServiceException;

	
	/**
	 * This method will be used provide a customer transactions with the particular TransactionId.
	 * @param transactionId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	
	public ArrayList getCustomerTransactionListWithId(String transactionId,String transactionType)
	throws VirtualizationServiceException;
		 

	/**
	 * This method will be used provide a list of customer transactions.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * @param lowerBound - specifies the number from which to fetch the records
	 * @param upperBound - specifies the number up to which to fetch the records
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException - exception which will be thrown incase any exception 
	 * occurs at the service level.
	 */
	public ArrayList getCustomerTransactionList(ReportInputs mtDTO, int lowerBound , int upperBound,String transId)
			throws VirtualizationServiceException;

	/**
	 * This method will be used provide a list of all customer transactions. 
	 * This will provide all the records and is independent of 
	 * pagination logic.
	 * 
	 * @param mtDTO - reports input dto which will contain all the mandatory information
	 * required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException - exception which will be thrown incase any exception 
	 * occurs at the service level.
	 */
	public ArrayList getCustomerTransactionListAll(ReportInputs mtDTO,String transId)
			throws VirtualizationServiceException;
		
}
