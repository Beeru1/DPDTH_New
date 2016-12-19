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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.WriteMode;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.common.ExceptionCode;

/**
 * Specialized java class for system configuration data validation at service
 * level.
 * 
 * @author Navroze
 * 
 * 
 */
public class SysConfigValidator {

	private static SysConfigValidator sysConfigValidator;

	/* Logger for this class. */

	private static Logger logger = Logger.getLogger(SysConfigValidator.class
			.getName());

	/**
	 * private constructor for SysConfigValidator
	 * 
	 */
	private SysConfigValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of SysConfigValidator
	 * 
	 * @return sysconfigValidator
	 */
	public static SysConfigValidator getInstance() {
		logger.info("Entered getInstance method for SysConfigValidator.");
		if (sysConfigValidator == null) {
			sysConfigValidator = new SysConfigValidator();
			logger
					.info("getInstance method successfully retured SysConfigValidator instance.");
		}
		return sysConfigValidator;
	}

	/**
	 * This method sets the mode to INSERT for validate method
	 * 
	 * @param sysConfig
	 * @throws VirtualizationServiceException
	 */
	public void validateInsert(SysConfig sysConfig)
			throws VirtualizationServiceException {
		validate(WriteMode.INSERT, sysConfig);
	}

	/**
	 * This method sets the mode to UPDATE for validate method
	 * 
	 * @param sysConfig
	 * @throws VirtualizationServiceException
	 */
	public void validateUpdate(SysConfig sysConfig)
			throws VirtualizationServiceException {
		validate(WriteMode.UPDATE, sysConfig);
	}

	/**
	 * 
	 * @param writeMode
	 * @param sysConfig
	 * @throws VirtualizationServiceException
	 */
	private void validate(int writeMode, SysConfig sysConfig)
			throws VirtualizationServiceException {

		if (sysConfig == null) {
			logger
					.error("validate : SysConfig is null : errors.sys.data_required");
			throw new VirtualizationServiceException("errors.sys.data_required");
		}
		TransactionType transactionType = TransactionType.valueOf(sysConfig
				.getTransactionType());
		if (transactionType == null) {
			logger
					.error("validate : Invalid Transaction Type : errors.sys.transtype_invalid"
							+ " : TransactionType = "
							+ sysConfig.getTransactionType());
			throw new VirtualizationServiceException(
					ExceptionCode.SystemConfig.ERROR_TRANSACTION_TYPE_IN_VALID);
		}
		ChannelType channelType = ChannelType.valueOf(sysConfig
				.getChannelName());
		if (channelType == null) {
			logger
					.error("validate : Invalid Channel : errors.sys.channelname_invalid"
							+ " : ChannelName = " + sysConfig.getChannelName());
			throw new VirtualizationServiceException(
					ExceptionCode.SystemConfig.ERROR_CHANNEL_INVALID);
		}
		if (sysConfig.getMinAmount() <= 0) {
			logger
					.error("validate : MinAmount is less than or equals to Zero : errors.sys.minamount_required "
							+ " : MinAmount = " + sysConfig.getMinAmount());
			throw new VirtualizationServiceException(
					ExceptionCode.SystemConfig.ERROR_MINAMOUNT_LESS);
		} else if (sysConfig.getMinAmount() != 0) {
			
			if (!(ValidatorUtility.isValidAmount(Utility.formatAmount(sysConfig
					.getMinAmount())))) {
				logger
						.error("validate : MinAmount is not a valid amount :errors.sys.minamount_invalid "
								+ " : MinAmount = " + sysConfig.getMinAmount());
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_MINAMOUNT_INVALID);
			}
		} else if (sysConfig.getMaxAmount() <= 0) {
			logger
					.error("validate : MaxAmount is less than or equals to Zero : errors.sys.maxamount_required "
							+ " : MaxAmount = " + sysConfig.getMaxAmount());
			throw new VirtualizationServiceException(
					"errors.sys.maxamount_required");
		} else if (sysConfig.getMaxAmount() != 0) {
			if (!(ValidatorUtility.isValidAmount(Utility.formatAmount(sysConfig
					.getMaxAmount())))) {
				logger
						.error("validate : MaxAmount is not a valid amount :errors.sys.maxamount_invalid "
								+ " : MaxAmount = " + sysConfig.getMaxAmount());
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_MAXAMOUNT_INVALID);
			}
		}
		if (sysConfig.getMaxAmount() < sysConfig.getMinAmount()) {
			logger
					.error("validate : MinAmount is Greater than MaxAmount  : errors.sys.max_min_amount"
							+ " : MinAmount = "
							+ sysConfig.getMinAmount()
							+ " : MaxAmount = " + sysConfig.getMaxAmount());
			throw new VirtualizationServiceException(
					ExceptionCode.SystemConfig.ERROR_MAXMIN_AMOUNT);
		}

		logger
				.info("validate method finished validating System Configuration Data.");

	}
}
