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

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Specialized java class for Distributor Transaction data validation at service
 * level.
 * 
 * @author bhanu
 * 
 */
public class DistributorTransactionValidator {
	private static DistributorTransactionValidator distributorTransactionValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger
			.getLogger(DistributorTransactionValidator.class.getName());

	/**
	 * private constructor for DistributorTransactionValidator
	 * 
	 */
	private DistributorTransactionValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of DistributorTransactionValidator
	 * 
	 * @return distributorTransactionValidator
	 */
	public static DistributorTransactionValidator getInstance() {
		logger
				.info("Entered getInstance method for DistributorTransactionValidator.");
		if (distributorTransactionValidator == null)
			distributorTransactionValidator = new DistributorTransactionValidator();
		logger
				.info("getInstance method successfully retured DistributorTransactionValidator instance.");
		return distributorTransactionValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, distributorTransaction);
	}

	/**
	 * This method sets the mode to UPDATE mode for validate method
	 * 
	 * @param distributorTransaction
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, distributorTransaction);
	}

	/* Method to validate DistributorTransaction Details */
	private void validate(int writeMode,
			DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException {

		if (distributorTransaction == null) {
			logger
					.error("DistributorTransactionValidator validate method found  distributorTransaction dto  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_DISTIBUTOR_TRANSACTION_DATAREQ);
		}

		if (writeMode == WriteMode.INSERT) {
			if (distributorTransaction.getPaymentMode().name()
					.equalsIgnoreCase(Constants.DISTRIBUTOR_TRANS_TYPE_CREDIT)) {
			/*	if ((String.valueOf(distributorTransaction.getChqccNumber())
						.length() < 15)
						|| (String.valueOf(
								distributorTransaction.getChqccNumber())
								.length() > 16)) {*/
				if (String.valueOf(
						distributorTransaction.getChqccNumber())
						.length() > 16) {
					logger
							.error("DistributorTransactionValidator validate method found  Invalid Card No. ..Number Must Be  15 or 16 digit long :- "
									+ String.valueOf(
											distributorTransaction
													.getChqccNumber()).length());
					throw new VirtualizationServiceException(
							ExceptionCode.Transaction.ERROR_TRANSACTION_CARDLENGTH);
				}
			}
			
		}
		
		if (null == distributorTransaction.getReviewComment()
				|| distributorTransaction.getReviewComment().trim()
						.length() == 0) {
			logger
					.error("DistributorTransactionValidator validate method found  Remarks Cannot be Blank . .. :- "
							+ distributorTransaction.getReviewComment()
									.trim().length());
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_REMARKSMINLENGTH);
		}
		if (!ValidatorUtility.isValidMaximumLength(distributorTransaction
				.getReviewComment(), 510)) {
			logger
					.error("DistributorTransactionValidator validate method found  Remarks Length more than database limit. .. :- "
							+ distributorTransaction.getReviewComment()
									.length());
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_REMARKSLENGTH);
		}
		
		if (!(ValidatorUtility
				.isJunkFree(distributorTransaction.getReviewComment()))) {
			logger
					.error("DistributorTransactionValidator validate method found remarks contain junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_REMARKSJUNK);
		}
		
		
	}
}