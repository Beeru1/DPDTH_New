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

import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.Group;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;

/**
 * Specialized java class for Group data validation at service level.
 * 
 * @author Paras
 * 
 */
public class GroupValidator {
	private static GroupValidator groupValidator;

	/*
	 * Logger for this class.
	 */

	private static Logger logger = Logger.getLogger(GroupValidator.class
			.getName());

	/**
	 * private constructor for GroupValidator
	 * 
	 */
	private GroupValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of GroupValidator
	 * 
	 * @return groupValidator
	 */
	public static GroupValidator getInstance() {
		logger.info("Entered getInstance method for GroupValidator.");
		if (groupValidator == null)
			groupValidator = new GroupValidator();
		logger
				.info("getInstance method successfully retured GroupValidator instance.");
		return groupValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param group
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(Group group)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, group);
	}

	/**
	 * This method sets the mode to UPDATE for validate method
	 * 
	 * @param group
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(Group group)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, group);
	}

	/*
	 * Method to validate Group Details
	 */
	private void validate(int writeMode, Group group)
			throws VirtualizationServiceException {
		logger.info("Entered validate method for GroupValidator.");
		/*
		 * Check if group name is non-blank
		 */
		if ((group.getGroupName() == null)
				|| (group.getGroupName().trim().equals(""))) {
			logger
					.fatal("GroupValidator validate method found Group Name as Blank.");
			throw new VirtualizationServiceException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_REQUIRED);
		}
		/*
		 * Check if group name is alpha numeric.
		 */
		if (!(ValidatorUtility.isAlphaNumeric(group.getGroupName()))) {
			logger
					.fatal("GroupValidator validate method found Group Name as Non-Alpha Numeric.");
			throw new VirtualizationServiceException(
					ExceptionCode.GroupRole.ERROR_GROUP_NAME_ALPHANUMERIC);
		}

		/*
		 * Check if group name length is valid
		 */
		if (!(ValidatorUtility.isValidMaximumLength(group.getGroupName(), 64))) {
			logger
					.fatal("GroupValidator validate method found Group Name length more than maximum limit.");
			throw new VirtualizationServiceException(
					ExceptionCode.GroupRole.ERROR_MAX_LENGTH_GROUP_NAME);
		}

		/*
		 * Check if group description length is valid
		 */
		if (!(ValidatorUtility
				.isValidMaximumLength(group.getDescription(), 512))) {
			logger
					.fatal("GroupValidator validate method found Group Description length more than maximum limit.");
			throw new VirtualizationServiceException(
					ExceptionCode.GroupRole.ERROR_MAX_LENGTH_DESC);
		}
		
		if (!(ValidatorUtility
				.isJunkFree(group.getDescription()))) {
			logger
					.fatal("GroupValidator validate method found Group Description contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.GroupRole.ERROR_GROUP_JUNK);
		}
		
		logger.info("validate method finished validating Group Data.");

	}

}