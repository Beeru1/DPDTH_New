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
import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Specialized java class for Account data validation at service level.
 * 
 * @author bhanu
 * 
 */
public class AccountValidator {
	private static AccountValidator accountValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(AccountValidator.class
			.getName());

	/**
	 * private constructor for AccountValidator
	 * 
	 */
	private AccountValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of AccountValidator
	 * 
	 * @return accountValidator
	 */
	public static AccountValidator getInstance() {
		logger.info("Entered getInstance method for AccountValidator.");
		if (accountValidator == null)
			accountValidator = new AccountValidator();
		logger
				.info("getInstance method successfully retured AccountValidator instance.");
		return accountValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(Account account)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, account);
	}

	/**
	 * This method sets the mode to UPDATE mode for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(Account account)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, account);
	}

	/* Method to validate User Details */
	private void validate(int writeMode, Account account)
			throws VirtualizationServiceException {

		if (account == null) {
			logger
					.error("AccountValidator validate method found account dto  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_DATA_REQUIRED);
		}

		else if ((account.getAccountCode() == null)
				|| (account.getAccountCode().trim().equals(""))) {
			logger
					.error("AccountValidator validate method found Account Code  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_CODE);
		}

		else if ((account.getAccountName() == null)
				|| (account.getAccountName().trim().equals(""))) {
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_NAME);
		} else if ((account.getAccountAddress() == null)
				|| (account.getAccountAddress().trim().equals(""))
				|| (account.getAccountAddress().trim().length() > 256)) {
			logger
					.error("AccountValidator validate method found Account Address  as Invalid.You have cross the limit of 256 Characters for Account Address");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_ADDRESS_LIMIT_ERROR);
		}
		 if(account.getEmail()!=null){
			if(!account.getEmail().trim().equalsIgnoreCase("")){ 
					 if (!(ValidatorUtility.isValidEmail(account.getEmail()))) {
						/* Check if email is valid. */
						logger
								.error("AccountValidator validate method found Email Id as Invalid.");
						throw new VirtualizationServiceException(
								ExceptionCode.Account.ERROR_EMAIL);
					} else if (!(ValidatorUtility.isValidMaximumLength(account.getEmail(),
							64))) {
						/* Check email Max Length */
						logger
								.error("AccountValidator validate method found Email Id as Invalid. Length");
						throw new VirtualizationServiceException(
								ExceptionCode.Account.ERROR_EMAIL);
					}
			}	 
       }	
		else if ((account.getCategory() == null)
				|| (account.getCategory().trim().equals(""))) {
			logger
					.error("AccountValidator validate method found Category  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_CATEGORY);
		} else if (account.getMobileNumber().length() != 10) {
			logger
					.error("AccountValidator validate method found Mobile Number  as Invalid. it must be 10 digit ");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_MOBILE_NO);

		} else if (account.getThreshold() < 0) {
			/* Check threshold Max value */
			logger
					.error("AccountValidator validate method found Threshold   Invalid.  "
							+ account.getThreshold());
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_THRESHOLD);
		} 
		 
		 if (!(ValidatorUtility
				.isJunkFree(account.getAccountAddress()))) {
			logger
					.error("AccountValidator validate method found account description contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_ADDRESS_JUNK);
		}
		 
		// only case insert
		if (writeMode == WriteMode.INSERT) {
			if ((account.getCircleId() == 0)) {
				logger
						.error("AccountValidator validate method found Circle Name  as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_CIRCLE_NAME);
			}

			else if(Integer.parseInt(account.getAccountLevelId()) < Constants.FSE_ID){ 
				
				if ((account.getIPassword() == null)
					|| (account.getIPassword().trim().equals(""))) {
				logger
						.error("AccountValidator validate method found IPassword as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_IPASSWORD);

			} 
			
			else if(!(account.getIPassword().trim().equalsIgnoreCase(account.getCheckIPassword().trim()))){
				logger
						.error("AccountValidator validate method found ipassword and checkIpassword not same.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_MISMATCH_IPASSWORD);
			}	
			
			else if (!(ValidatorUtility
					.isJunkFree(account.getIPassword().trim()))) {
				logger
						.error("AccountValidator validate method found IPassword contains junk characters.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_IPASSWORD);
			} 
		}
	/*		else if ((account.getMPassword() == null)
					|| (account.getMPassword().trim().equals(""))) {
				logger
						.error("AccountValidator validate method found MPassword as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_INVALID_MPASSWORD);
			}
			else if (!ValidatorUtility.isValidNumber(account.getMPassword())) {
				logger
						.error("AccountValidator validate method found MPassword as non numeric  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_MPASSWORD_NUMERIC);
			}
			
			else if (account.getMPassword().length()<4) {
				logger
						.error("AccountValidator validate method found MPassword as Invalid. password must be min. of 4 digit long ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_MPASSWORD_MINLENGTH);
			}
			
			else if (account.getMPassword().length()>8) {
				logger
						.error("AccountValidator validate method found MPassword as Invalid. password must not be max of 8 digit long ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_MPASSWORD_MAXLENGTH);
			}
									
			else if(!(account.getMPassword().trim().equalsIgnoreCase(account.getCheckMpassword().trim()))){
				logger
						.error("AccountValidator validate method found mpassword and checkMpassword not same.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_MISMATCH_MPASSWORD);
			}	*/
			
			else if ((account.getSimNumber() != null)
					&& (account.getSimNumber()).trim().length() != 0
					&& (account.getSimNumber()).trim().length() != 19 
					&& (account.getSimNumber()).trim().length() != 20
					) {
				logger
						.error("AccountValidator validate method found sim number as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_SIM_LENGTH);

			} else if ((account.getRate() == 0) || account.getRate() > 100) {
				logger
						.error("AccountValidator validate method found Rate  as Invalid. Rate Must Be 1 to 100");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_INVALID_RATE);
			} else if ((account.getAccountLevel() == null)
					|| account.getAccountLevel().trim().equalsIgnoreCase("0")) {
				logger
						.error("AccountValidator validate method found Invalid Account Level");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_INVALID_ACC_LEVEL);
			}
		}

	}
}
