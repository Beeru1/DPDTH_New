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

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.UploadTopupSlab;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Specialized java class for Circle Topupconfig data validation at service
 * level.
 * 
 * @author bhanu
 * 
 */
public class CircleTopupConfigValidator {
	private static CircleTopupConfigValidator CircleTopupConfigValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger
			.getLogger(CircleTopupConfigValidator.class.getName());

	/**
	 * private constructor for CircleTopupConfigValidator
	 * 
	 */
	private CircleTopupConfigValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of CircleTopupConfigValidator
	 * 
	 * @return CircleTopupConfigValidator
	 */
	public static CircleTopupConfigValidator getInstance() {
		logger
				.info("Entered getInstance method for CircleTopupConfigValidator.");
		if (CircleTopupConfigValidator == null)
			CircleTopupConfigValidator = new CircleTopupConfigValidator();
		logger
				.info("getInstance method successfully retured CircleTopupConfigValidator instance.");
		return CircleTopupConfigValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param user
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(UploadTopupSlab topupDtoList)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, topupDtoList);
	}

	/* Method to validate DistributorTransaction Details */
	private void validate(int writeMode, UploadTopupSlab topupDtoList)
			throws VirtualizationServiceException {
		if (writeMode == WriteMode.INSERT) {

			if (topupDtoList.getStartAmount().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_START_AMOUNT);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getStartAmount()))) {
				logger
						.error("CircleValidator validate method found Start Amount as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_START_AMOUNT_NUMERIC);
			} else if (topupDtoList.getTillAmount().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_TILL_AMOUNT);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getTillAmount()))) {
				logger
						.error("CircleValidator validate method found End Amount as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_TILL_AMOUNT_NUMERIC);
			} else if (topupDtoList.getEspCommission().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_ESP_COMMISSION);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getEspCommission()))) {
				logger
						.error("CircleValidator validate method found ESP commission as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_ESP_COMMISSION_NUMERIC);
			} else if (topupDtoList.getPspCommission().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_PSP_COMMISSION);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getPspCommission()))) {
				logger
						.error("CircleValidator validate method found PSP commission as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_PSP_COMMISSION_NUMERIC);
			} else if (topupDtoList.getProcessingCode().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_PROCESSING_CODE);
			} else if (topupDtoList.getProcessingFee().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_PROCESSING_FEE);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getProcessingFee()))) {
				logger
						.error("CircleValidator validate method found Porcessing Fee as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_PROCESSING_FEE_NUMERIC);
			} else if (topupDtoList.getServiceTax().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_SERVICE_TAX);
			} else if (!(ValidatorUtility.isNumeric(topupDtoList
					.getServiceTax()))) {
				logger
						.error("CircleValidator validate method found Service Tax as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_SERVICE_TAX_NUMERIC);
			} else if (topupDtoList.getInCardGroup().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_IN_CARD);
			} else if (topupDtoList.getValidity().equalsIgnoreCase("")) {
				logger
						.error("CircleTopupConfigValidator validate method found topupSlab Dto as Invalid.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_INVALID_VALIDITY);
			} else if (!(ValidatorUtility.isValidNumber(topupDtoList
					.getValidity()))) {
				logger
						.error("CircleValidator validate method found Validity as as Alpha Numeric.");
				throw new VirtualizationServiceException(
						ExceptionCode.SlabConfiguration.ERROR_VALIDITY_NUMERIC);
			}

		}//end of if 

	} //end of method
}
