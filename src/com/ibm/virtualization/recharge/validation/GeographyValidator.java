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
import com.ibm.dp.dto.Geography;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;

/**
 * Specialized java class for geography data validation at service level.
 * 
 * @author Paras
 * 
 */
public class GeographyValidator {
	private static GeographyValidator geographyValidator;

	/*
	 * Logger for this class.
	 */

	private static Logger logger = Logger.getLogger(GeographyValidator.class
			.getName());

	/**
	 * private constructor for GeographyValidator
	 * 
	 */
	private GeographyValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of GeographyValidator
	 * 
	 * @return geographyValidator
	 */
	public static GeographyValidator getInstance() {
		logger.info("Entered getInstance method for GeographyValidator.");
		if (geographyValidator == null)
			geographyValidator = new GeographyValidator();
		logger
				.info("getInstance method successfully retured GeographyValidator instance.");
		return geographyValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param geography
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(Geography geography)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, geography);
	}

	/**
	 * This method sets the mode to UPDATE for validate method
	 * 
	 * @param geography
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(Geography geography)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, geography);
	}

	/*
	 * Method to validate Geography Details
	 */
	private void validate(int writeMode, Geography geography)
			throws VirtualizationServiceException {
		logger.info("Entered validate method for GeographyValidator.");
		if ((geography.getGeographyCode() == null)
				|| (geography.getGeographyCode().trim().equals(""))) {
			logger
					.error("GeographyValidator validate method found Geography Code as Blank.");
			throw new VirtualizationServiceException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_CODE);
		}
		/*else if (!(ValidatorUtility.isAlphaNumeric(geography.getGeographyCode()))) {
			logger
					.error("GeographyValidator validate method found Geography Code as Non-Alpha Numeric.");
			throw new VirtualizationServiceException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_CODE_ALPHANUMERIC);
		}*/
		else if ((geography.getGeographyName() == null)
				|| (geography.getGeographyName().trim().equals(""))) {
			logger
					.error("GeographyValidator validate method found Geography Name as Blank.");
			throw new VirtualizationServiceException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_NAME);
		} else if (!(ValidatorUtility.isAlphaNumeric(geography.getGeographyName()))) {
			logger
					.error("GeographyValidator validate method found Geography Name as Non-Alpha Numeric.");
			throw new VirtualizationServiceException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_NAME_ALPHANUMERIC);
		
		} else if ((geography.getLevel()>0)&&(0 == geography.getParentId())) {
			logger
					.error("GeographyValidator validate method found Invalid Region Id.");
			throw new VirtualizationServiceException(
					ExceptionCode.Geography.ERROR_REGION);
		} 
		
		if (writeMode == WriteMode.UPDATE) {
			if (!((geography.getStatus().trim().equalsIgnoreCase("A")) || (geography
					.getStatus().trim().equalsIgnoreCase("D")))) {
				logger
						.error("GeographyValidator validate method found Invalid Status.");
				throw new VirtualizationServiceException(
						ExceptionCode.Geography.ERROR_STATUS);
			} 

		}

		logger.info("validate method finished validating Geography Data.");
	}

}