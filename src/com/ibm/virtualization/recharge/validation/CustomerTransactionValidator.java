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

import com.ibm.virtualization.recharge.dto.RechargeTransaction;
import org.apache.log4j.Logger;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResponseCode;

public class CustomerTransactionValidator {
	private static CustomerTransactionValidator thisInstance = null;

	/**
	 * Logger for CustomerTransactionValidator class.
	 */

	private static Logger logger = Logger
			.getLogger(CustomerTransactionValidator.class.getName());

	/* Default constructor for CustomerTransactionValidator */
	private CustomerTransactionValidator() {

	}

	/* Static method to get an instance of RechargeTransaction Validator */
	public static CustomerTransactionValidator getInstance() {
		logger
				.info("Entered getInstance method for CustomerTransactionValidator.");
		if (thisInstance == null) {
			thisInstance = new CustomerTransactionValidator();
		}
		logger
				.info("getInstance method successfully retured CustomerTransactionValidator instance.");
		return thisInstance;

	}

	/**
	 * This method validates the RechargeTransaction for the Start
	 * Transaction
	 * 
	 * @param RechargeTransaction
	 * @throws VirtualizationServiceException
	 */
	public void validate(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		logger.info(" Entering validate");

		if (rechargeTransaction.getDestinationMobileNo() == null
				|| rechargeTransaction.getDestinationMobileNo().trim().length() == 0) {
			logger.error("Mobile Number is Invalid");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_REQUIRED);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_REQUIRED);
		}
		

		if (rechargeTransaction.getDestinationMobileNo().length() != 10) {
			logger.error("Mobile Number is Invalid");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_INVALID);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_SUBSCRIBER_MOBILENO_INVALID);
		}
		if (rechargeTransaction.getTransactionAmount() <= 0) {
			logger.error("Transaction Amount is Invalid");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
		}
		if (rechargeTransaction.getTransactionType() == null) {
			logger.error("Transaction Type required");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
		}
		if (rechargeTransaction.getTransactionState() == null) {
			logger.error("Transaction State required");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}
		if (rechargeTransaction.getUserSessionContext() == null) {
			logger.error("UserSessionContext required");
			rechargeTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}
	}

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