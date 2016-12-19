package com.ibm.virtualization.recharge.validation;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.dto.AccountTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;

public class AccountToAccountTransactionValidator {
	private static AccountToAccountTransactionValidator thisInstance = null;

	/**
	 * Logger for AccountToAccountTransactionValidator class.
	 */

	private static Logger logger = Logger
			.getLogger(AccountToAccountTransactionValidator.class.getName());

	private AccountToAccountTransactionValidator() {

	}

	public static AccountToAccountTransactionValidator getInstance() {
		logger
				.info("Entered getInstance method for AccountToAccountTransactionValidator.");
		if (thisInstance == null) {
			thisInstance = new AccountToAccountTransactionValidator();
		}
		logger
				.info("getInstance method successfully retured AccountToAccountTransactionValidator instance.");
		return thisInstance;

	}

	public void validate(AccountTransaction accountTransaction)
			throws VirtualizationServiceException {
		logger.info(" Entering validate ");
		if (accountTransaction == null) {
			logger.error("Dto is null");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_ACCOUNT_ID_REQ);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_ACCOUNT_ID_REQ);
		}

		if (accountTransaction.getDestinationMobileNo() != null
				&& accountTransaction.getDestinationMobileNo().length() == 0) {
			logger.error("destination mobile no is null");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_RECEIVING_MOB_REQ);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_RECEIVING_MOB_REQ);
		}

		if (accountTransaction.getTransactionAmount() <= 0) {
			logger.error("Transaction amount required");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TRANSFER_AMOUNT_REQUIRED);
		}
		if (accountTransaction.getTransactionType() == null) {
			logger.error("Transaction Type required");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
			new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
		}
		if (accountTransaction.getTransactionState() == null) {
			logger.error("Transaction State required");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}
		if (accountTransaction.getUserSessionContext() == null) {
			logger.error("UserSessionContext required");
			accountTransaction.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_STATE);
		}

	}

}