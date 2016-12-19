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
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;

/**
 * Specialized java class for circle data validation at service level.
 * 
 * @author Paras
 * 
 */
public class CircleValidator {
	private static CircleValidator circleValidator;

	/*
	 * Logger for this class.
	 */

	private static Logger logger = Logger.getLogger(CircleValidator.class
			.getName());

	/**
	 * private constructor for CircleValidator
	 * 
	 */
	private CircleValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of CircleValidator
	 * 
	 * @return circleValidator
	 */
	public static CircleValidator getInstance() {
		logger.info("Entered getInstance method for CircleValidator.");
		if (circleValidator == null)
			circleValidator = new CircleValidator();
		logger
				.info("getInstance method successfully retured CircleValidator instance.");
		return circleValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(Circle circle)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, circle);
	}

	/**
	 * This method sets the mode to UPDATE for validate method
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(Circle circle)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, circle);
	}

	/*
	 * Method to validate Circle Details
	 */
	private void validate(int writeMode, Circle circle)
			throws VirtualizationServiceException {
		logger.info("Entered validate method for CircleValidator.");
		if ((circle.getCircleCode() == null)
				|| (circle.getCircleCode().trim().equals(""))) {
			logger
					.error("CircleValidator validate method found Circle Code as Blank.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_CODE);
		}
		/*else if (!(ValidatorUtility.isAlphaNumeric(circle.getCircleCode()))) {
			logger
					.error("CircleValidator validate method found Circle Code as Non-Alpha Numeric.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_CODE_ALPHANUMERIC);
		}*/
		else if ((circle.getCircleName() == null)
				|| (circle.getCircleName().trim().equals(""))) {
			logger
					.error("CircleValidator validate method found Circle Name as Blank.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_NAME);
		} else if (!(ValidatorUtility.isAlphaNumeric(circle.getCircleName()))) {
			logger
					.error("CircleValidator validate method found Circle Name as Non-Alpha Numeric.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_NAME_ALPHANUMERIC);
		} 
		/*else if ((0 >= circle.getRate()) || (100 < circle.getRate())) {
			logger
					.error("CircleValidator validate method found Commission Rate as Invalid value.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_RATE);
		} else if (0 > circle.getAvailableBalance()) {
			logger
					.error("CircleValidator validate method found Invalid Available Balance.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_AVAILABLE_BALANCE);
		} else if (0 > circle.getOpeningBalance()) {
			logger
					.error("CircleValidator validate method found Invalid Opening Balance.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_OPENING_BALANCE_REQUIRED);
		} else if (0 > circle.getOperatingBalance()) {
			logger
					.error("CircleValidator validate method found Invalid Operating Balance.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_OPERATING_BALANCE);
		} else if (0 > circle.getThreshold()) {
			logger
					.error("CircleValidator validate method found Invalid Threshold Amount.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_THRESHOLD_BALANCE);
		}*/ else if (0 == circle.getRegionId()) {
			logger
					.error("CircleValidator validate method found Invalid Region Id.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_REGION);
		} else if (!(ValidatorUtility
				.isJunkFree(circle.getDescription()))) {
			logger
					.fatal("CircleValidator validate method found Circle Description contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Circle.ERROR_CIRCLE_DESC_JUNK);
		}
		
		
		if (writeMode == WriteMode.UPDATE) {
			if (!((circle.getStatus().trim().equalsIgnoreCase("A")) || (circle
					.getStatus().trim().equalsIgnoreCase("D")))) {
				logger
						.error("CircleValidator validate method found Invalid Status.");
				throw new VirtualizationServiceException(
						ExceptionCode.Circle.ERROR_STATUS);
			} 

		}

		logger.info("validate method finished validating Circle Data.");
	}

}