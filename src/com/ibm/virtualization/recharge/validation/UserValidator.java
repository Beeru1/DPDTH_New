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
import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;
/**
 * Specialized java class for User data validation at service level.
 * 
 * @author Paras
 * 
 */
public class UserValidator {
	private static UserValidator userValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(UserValidator.class
			.getName());

	/**
	 * private constructor for UserValidator
	 * 
	 */
	private UserValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of UserValidator
	 * 
	 * @return userValidator
	 */
	public static UserValidator getInstance() {
		logger.info("Entered getInstance method for UserValidator.");
		if (userValidator == null)
			userValidator = new UserValidator();
		logger
				.info("getInstance method successfully retured UserValidator instance.");
		return userValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(User user) throws VirtualizationServiceException {
		validate(WriteMode.INSERT, user);
	}

	/**
	 * This method sets the mode to UPDATE mode for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(User user) throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, user);
	}

	/* Method to validate User Details */
	private void validate(int writeMode, User user)
			throws VirtualizationServiceException {
		logger.info("Entered validate method for UserValidator.");

		/* Check writeMode if mode is insert */
		if (writeMode == 0) {
			/* Check if user name is non-blank */
			if ((user.getLoginName() == null)
					|| (user.getLoginName().trim().equals(""))) {
				logger
						.fatal("UserValidator validate method found User Name as Blank.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_NAME_REQUIRED);
			} else
			/* Check if user name is alpha numeric. */
			if (!(ValidatorUtility.isAlphaNumeric(user.getLoginName()))) {
				logger
						.fatal("UserValidator validate method found User Name as Non-Alpha Numeric.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_NAME_ALPHANUMERIC);
			} else

			/* Check if user name length is valid */
			if (!(ValidatorUtility
					.isValidMaximumLength(user.getLoginName(), 64))) {
				logger
						.fatal("UserValidator validate method found User Name more than 64 characters.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_MAX_NAME_LENGTH);
						
			} else if (!(ValidatorUtility.isValidMinimumLength(user
					.getLoginName(), 8))) {
				logger
						.fatal("UserValidator validate method found User Name less than 8 characters.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_MIN_NAME_LENGTH);
						
			} else
			/* Check if password length is valid */
				if(user.getNewAccountLevel() < Constants.FSE_ID){
	//will not validate password while FSE or Retailer account creation.				
			if (!(ValidatorUtility
					.isValidMaximumLength(user.getPassword(), 128))) {
				logger
						.fatal("UserValidator validate method found Password more than 128 characters.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_MAX_LENGTH_PASSWORD);
						
			} else if (!(ValidatorUtility.isValidMinimumLength(user.getPassword(), 8))) {
				logger
						.fatal("UserValidator validate method found password less than 8 characters.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_MIN_LENGTH_PASSWORD);
			} else if (!(user.getCheckPassword().trim().equalsIgnoreCase(user.getPassword().trim()))) {
				logger
						.fatal("UserValidator validate method found password and confirm-password not same.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_MATCH_PASSWORD);
			} else if (!(ValidatorUtility
					.isJunkFree(user.getPassword().trim()))) {
				logger
					.fatal("UserValidator validate method found password contains junk characters.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_PASSWORD);
			}
		}
	}
		/* for both INSERT and UPDATE Mode */

		/* Check if email is valid. */
		if (!(ValidatorUtility.isValidEmail(user.getEmailId()))) {
			logger
					.fatal("UserValidator validate method found Email Id as Invalid.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_MAIL);
		} else
		/* Check email Max Length */
		if (!(ValidatorUtility.isValidMaximumLength(user.getEmailId(), 64))) {
			logger
					.fatal("UserValidator validate method found Email Id more than 64 characters.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_INVALID_MAX_LENGTH_MAIL);
		}

		/* Check if Contact Number is blank */
		if ((user.getContactNumber().trim().length() == 0)
				|| ("".equals(user.getContactNumber().trim()))) {
			logger
					.fatal("UserValidator validate method found contact number as blank.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_CONTACT_NO_REQ);
		} else
		/* Check if contact number is valid number */
		if ((user.getContactNumber().length()!=10)) {
			logger
					.fatal("UserValidator validate method found contact number as not a valid number.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_CONTACTNO_INVALID);
		} else
		/* Check if Contact Number length is valid */
		if (!(ValidatorUtility
				.isValidMaximumLength(user.getContactNumber(), 10))) {
			logger
					.fatal("UserValidator validate method found contact number more than 16 characters.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_CONTACT_MIN_LENGTH);
		} else

		/* Check if Group Id is not 0 */
		if (0 == user.getGroupId()) {
			logger
					.fatal("UserValidator validate method found Invalid group id.");
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_GROUP_ID_REQ);
		}

		/* status to be checked only if writeMode is UPDATE */
		if (writeMode == 1) {
			if (!((user.getStatus().trim().equalsIgnoreCase("A")) || (user
					.getStatus().trim().equalsIgnoreCase("D")))) {
				logger
						.fatal("CircleValidator validate method found Invalid Status.");
				throw new VirtualizationServiceException(ExceptionCode.User.ERROR_USER_STATUS_INVALID);
						
			}
		}

		logger.info("validate method finished validating User Data.");

	}

}