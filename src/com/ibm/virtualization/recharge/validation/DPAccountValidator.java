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
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Specialized java class for Account data validation at service level.
 * 
 * @author bhanu
 * 
 */
public class DPAccountValidator {
	private static DPAccountValidator accountValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(DPAccountValidator.class
			.getName());

	/**
	 * private constructor for AccountValidator
	 * 
	 */
	private DPAccountValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of AccountValidator
	 * 
	 * @return accountValidator
	 */
	public static DPAccountValidator getInstance() {
		logger.info("Entered getInstance method for AccountValidator.");
		if (accountValidator == null)
			accountValidator = new DPAccountValidator();
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
	public void validateInsert(DPCreateAccountDto account)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, account);
	}

	/**
	 * This method sets the mode to UPDATE mode for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(DPCreateAccountDto account)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, account);
	}

	/* Method to validate User Details */
	private void validate(int writeMode, DPCreateAccountDto account)
			throws VirtualizationServiceException {

		System.out.println("account.getAccountCode()::"+account.getAccountCode());
		if (account == null) {
			logger.error("AccountValidator validate method found account dto  as Invalid.");
			throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_DATA_REQUIRED);
		}
		else if(!(account.getAccountLevel().equalsIgnoreCase("8")||account.getAccountLevel().equalsIgnoreCase("7")))
		{
			if (((account.getAccountCode() == null)	|| (account.getAccountCode().trim().equals("")))) {
				logger.error("AccountValidator validate method found Account Code  as Invalid.");
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_CODE);
			}
		}

		else if ((account.getAccountName() == null)	|| (account.getAccountName().trim().equals(""))) {
			throw new VirtualizationServiceException(		ExceptionCode.Account.ERROR_ACCOUNT_NAME);
		} else if ((account.getAccountAddress() == null)				|| (account.getAccountAddress().trim().equals(""))
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

		} 
		 
		 /*if (!(ValidatorUtility
				.isJunkFree(account.getAccountAddress()))) {
			logger
					.error("AccountValidator validate method found account description contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_ACCOUNT_ADDRESS_JUNK);
		}*/
		 
		// only case insert
		if (writeMode == WriteMode.INSERT) {
			if (String.valueOf(account.getCircleId()).equalsIgnoreCase("")) {
				logger
						.error("AccountValidator validate method found Circle Name  as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_CIRCLE_NAME);
			}else
			if(account.getAccountLevelId() < Constants.FSE_ID){
				if ((account.getIPassword() == null)
						|| (account.getIPassword().trim().equals(""))  ) {
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
			else if (!(ValidatorUtility
					.isNumeric(account.getIPassword().trim()))) {
				logger
						.error("AccountValidator validate method found IPassword does not contains numeric characters.");
				throw new VirtualizationServiceException(
						ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_NUMERIC_INVALID);
			} 
			}
			else if ((account.getSimNumber() != null)
					&& (account.getSimNumber()).trim().length() != 0
					&& (account.getSimNumber()).trim().length() != 19 
					&& (account.getSimNumber()).trim().length() != 20
					) {
				logger
						.error("AccountValidator validate method found sim number as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_SIM_LENGTH);

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
