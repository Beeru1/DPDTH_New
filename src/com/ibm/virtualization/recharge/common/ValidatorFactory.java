/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.common;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.validation.AccountToAccountTransactionValidator;
import com.ibm.virtualization.recharge.validation.AccountValidator;
import com.ibm.virtualization.recharge.validation.ChangePasswordValidator;
import com.ibm.virtualization.recharge.validation.CircleTopupConfigValidator;
import com.ibm.virtualization.recharge.validation.CircleValidator;
import com.ibm.virtualization.recharge.validation.CustomerTransactionValidator;
import com.ibm.virtualization.recharge.validation.DPAccountValidator;
import com.ibm.virtualization.recharge.validation.DistributorTransactionValidator;
import com.ibm.virtualization.recharge.validation.GeographyValidator;
import com.ibm.virtualization.recharge.validation.GroupValidator;
import com.ibm.virtualization.recharge.validation.LoginValidator;
import com.ibm.virtualization.recharge.validation.PostpaidTransactionValidator;
import com.ibm.virtualization.recharge.validation.SysConfigValidator;
import com.ibm.virtualization.recharge.validation.UserValidator;
 

/**
 * This class is a factory to validate DTO values in service layer
 * 
 * @author Paras
 * 
 */
public class ValidatorFactory {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ValidatorFactory.class
			.getName());

	/**
	 * This method registers CircleValidator class with ValidatorFactory
	 * 
	 * @return circleValidator
	 */
	public static CircleValidator getCircleValidator() {
		logger.info("Entered getCircleValidator method.");
		return CircleValidator.getInstance();
	}

	/**
	 * This method registers GroupValidator class with ValidatorFactory
	 * 
	 * @return groupValidator
	 */
	public static GroupValidator getGroupValidator() {
		logger.info("Entered getGroupValidator method.");
		return GroupValidator.getInstance();
	}

	/**
	 * This method registers UserValidator class with ValidatorFactory
	 * 
	 * @return userValidator
	 */
	public static UserValidator getUserValidator() {
		logger.info("Entered getUserValidator method.");
		return UserValidator.getInstance();
	}

	/**
	 * This method registers AccountValidator class with ValidatorFactory
	 * 
	 * @return accountValidator
	 */
	public static AccountValidator getAccountValidator() {
		logger.info("Entering ValidatorFactory getAccountValidator() ");
		return AccountValidator.getInstance();
	}

	// /**
	// * This method registers loginValidator class with ValidatorFactory
	// *
	// * @return LoginValidator
	// */
	// public static LoginValidator getLoginValidator() {
	// logger.info("Entering getLoginValidator () ");
	// return LoginValidator.getInstance();
	// }

	/**
	 * This method registers SysConfigValidator class with ValidatorFactory
	 * 
	 * @return SysConfigValidator
	 */
	public static SysConfigValidator getSysConfigValidator() {
		logger.info("Entered ValidatorFactory getSysConfigValidator method.");
		return SysConfigValidator.getInstance();
	}

	/**
	 * This method registers CustomerTransactionValidator class with
	 * ValidatorFactory
	 * 
	 * @return CustomerTransactionValidator
	 * @throws VirtualizationServiceException
	 */
	public static CustomerTransactionValidator getCustomerTransactionValidator()
			throws VirtualizationServiceException {
		logger
				.info("Entering ValidatorFactory getCustomerTransactionValidator() ");
		return CustomerTransactionValidator.getInstance();
	}

	/**
	 * This method registers PostpaidTransactionValidator class with
	 * ValidatorFactory
	 * 
	 * @return PostpaidTransactionValidator
	 * @throws VirtualizationServiceException
	 */
	public static PostpaidTransactionValidator getPostpaidTransactionValidator()
			throws VirtualizationServiceException {
		logger.info("Entering ValidatorFactory ");
		return PostpaidTransactionValidator.getInstance();
	}

	/**
	 * This method registers AccountToAccountTransactionValidator class with
	 * ValidatorFactory
	 * 
	 * @return AccountToAccountTransactionValidator
	 * @throws VirtualizationServiceException
	 */
	public static AccountToAccountTransactionValidator getAccountTransactionValidator()
			throws VirtualizationServiceException {
		logger
				.info("Entering ValidatorFactory getAccountTransactionValidator() ");
		return AccountToAccountTransactionValidator.getInstance();
	}

	/**
	 * This method registers distributorTransactionValidator class with
	 * ValidatorFactory
	 * 
	 * @return distributorTransactiontValidator
	 * @throws VirtualizationServiceException
	 */
	public static DistributorTransactionValidator getDistributorTransactionValidator()
			throws VirtualizationServiceException {
		logger
				.info("Entering ValidatorFactory getDistributorTransactionValidator() ");
		return DistributorTransactionValidator.getInstance();
	}
	
	/**
	 * This method registers LoginValidator class with ValidatorFactory
	 * 
	 * @return LoginValidator
	 */
	public static LoginValidator getloginValidator() {
		logger.info("Entered getLoginValidator method.");
		return LoginValidator.getInstance();
	}
	
	/**
	 * This method registers ChangePasswordValidator class with ValidatorFactory
	 * 
	 * @return ChangePasswordValidator
	 */
	public static ChangePasswordValidator getChangePasswordValidator() {
		logger.info("Entered getChangePasswordValidator method.");
		return ChangePasswordValidator.getInstance();
	}
	
	/**
	 * This method registers circleTopupConfigValidator class with
	 * ValidatorFactory
	 * 
	 * @return CircleTopupConfigValidator
	 * @throws VirtualizationServiceException
	 */
	public static CircleTopupConfigValidator getCircleTopupConfigValidator()
			throws VirtualizationServiceException {
		logger
				.debug("Entering ValidatorFactory getCircleTopupConfigValidator() ");
		return CircleTopupConfigValidator.getInstance();
	}
	
	/* Added by Anju */
	public static DPAccountValidator getDPAccountValidator() {
		logger.info("Entering ValidatorFactory getAccountValidator() ");
		return DPAccountValidator.getInstance();
	}
	
	public static GeographyValidator getGepgraphyValidator() {
		logger.info("Entered getCircleValidator method.");
		return GeographyValidator.getInstance();
	}
			
}