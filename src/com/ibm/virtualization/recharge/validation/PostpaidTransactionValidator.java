/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.validation;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dto.PostpaidTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class PostpaidTransactionValidator {
	private static PostpaidTransactionValidator thisInstance = null;

	/**
	 * Logger for CustomerTransactionValidator class.
	 */

	private static Logger logger = Logger
			.getLogger(PostpaidTransactionValidator.class.getName());

	/* Default constructor for PostpaidTransactionValidator */
	private PostpaidTransactionValidator() {

	}

	/* Static method to get an instance of PostpaidTransaction Validator */
	public static PostpaidTransactionValidator getInstance() {
		logger
				.info("Entered getInstance method for PostpaidTransactionValidator.");
		if (thisInstance == null) {
			thisInstance = new PostpaidTransactionValidator();
		}
		logger
				.info("getInstance method successfully retured PostpaidTransactionValidator instance.");
		return thisInstance;

	}

	/**
	 * This method validates the PostpaidTransaction for the Start Transaction
	 * 
	 * @param postpaidTransaction
	 * @throws VirtualizationServiceException
	 */
	public void validate(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		logger.info(" Entering validate");

		if (postpaidTransaction.getDestinationNo() == null
				|| postpaidTransaction.getDestinationNo().trim().length() == 0) {
			logger.error("Mobile Number is Invalid");
			//postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_REQUIRED);
			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_REQUIRED);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_REQUIRED);
					//ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_REQUIRED);
		}
		// TODO : Ashish ,Sept 8 2007, all the validations for mobile should be
		// in a central place. Right now
		// all the validators are implementing their respective mobile
		// validations
//TODO validation for postpaid
//		if (postpaidTransaction.getDestinationNo().length() != 10) {
//			logger.error("Mobile Number is Invalid");
//			//postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_INVALID);
//			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_INVALID);
//			throw new VirtualizationServiceException(
//					ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_INVALID);
//					//ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_INVALID);
//					
//		}
		if (postpaidTransaction.getTransactionAmount() <= 0) {
			logger.error("Transaction Amount is Invalid");
			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
		}
//		if (postpaidTransaction.getTransactionId() <= 0) {
//			logger.error("TransactionId is Invalid");
//			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_ID_REQ);
//			throw new VirtualizationServiceException(
//					ExceptionCode.Transaction.ERROR_TRANSACTION_ID_REQ);
//		}
		if (postpaidTransaction.getTransactionType() == null) {
			logger.error("Transaction Type required");
			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
		}
		if (postpaidTransaction.getTransactionState() == null) {
			logger.error("Transaction state required");
			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}
		if (postpaidTransaction.getUserSessionContext() == null) {
			logger.error("UserSessionContext required");
			postpaidTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}
	}

	/**
	 * This method validates the parameters for Authentication for postpaid
	 * transaction
	 * 
	 * @param requesterMobileNo
	 * @param subscriberMobileNo
	 * @param mPassword
	 * @throws VirtualizationServiceException
	 */
	public void validate(String requesterMobileNo, String mPassword)
			throws VirtualizationServiceException {
		logger.info(" Entering validate for customer authorization");
		if (requesterMobileNo == null || requesterMobileNo.trim().length() == 0) {
			logger.error("Requestor Mobile Number is blank :"
					+ requesterMobileNo);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_REQUESTER_MOBILENO_REQUIRED);
		}

		if (mPassword == null || mPassword.trim().length() == 0) {
			logger.error("mPassword Mobile Number is blank :" + mPassword);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_MPASSWORD);
		}
	}
}