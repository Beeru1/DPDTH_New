package com.ibm.virtualization.recharge.validation;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dto.ChangePasswordDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Specialized java class for change password data validation at service level.
 * 
 */
public class ChangePasswordValidator {
	private static ChangePasswordValidator changePasswordValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(ChangePasswordValidator.class.getName());

	/**
	 * private constructor for ChangePasswordValidator
	 * 
	 */
	private ChangePasswordValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of ChangePasswordValidator
	 * 
	 * @return changePasswordValidator
	 */
	public static ChangePasswordValidator getInstance() {
		logger.info("Entered getInstance method for ChangePasswordValidator.");
		if (changePasswordValidator == null)
			changePasswordValidator = new ChangePasswordValidator();
		logger
				.info("getInstance method successfully retured ChangePasswordValidator instance.");
		return changePasswordValidator;
	}
	
	public void validate(ChangePasswordDto changePassword) throws VirtualizationServiceException{
		if (changePassword == null) {
			logger
					.error("ChangePasswordValidator validate method found changePassword dto  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_DATA_REQUIRED);
		}
		
		else if ((changePassword.getOldPassword().trim() == null)
				|| (changePassword.getOldPassword().trim().equals(""))) {
			logger
					.error("ChangePasswordValidator validate method found Old Password as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_OLD_REQ);
		}
		
		else if (!(ValidatorUtility
				.isJunkFree(changePassword.getOldPassword().trim()))) {
			logger
					.fatal("ChangePasswordValidator validate method found Old Password contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_OLD_INVALID);
		}
		
		else if ((changePassword.getNewPassword().trim() == null)
				|| (changePassword.getNewPassword().trim().equals(""))) {
			logger
					.error("ChangePasswordValidator validate method found New Password as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_NEW_REQ);
		}
		
		else if (!(ValidatorUtility
				.isJunkFree(changePassword.getNewPassword().trim()))) {
			logger
					.fatal("ChangePasswordValidator validate method found New Password contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_NEW_INVALID);
		}
		
		else if ((changePassword.getConfirmPassword().trim() == null)
				|| (changePassword.getConfirmPassword().trim().equals(""))) {
			logger
					.error("ChangePasswordValidator validate method found Confirm Password as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_CONFIRMNEW_REQ);
		}
		
		else if (!(ValidatorUtility
				.isJunkFree(changePassword.getConfirmPassword().trim()))) {
			logger
					.fatal("ChangePasswordValidator validate method found Confirm Password contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_CONFIRM_INVALID);
		}
		
		else if(!(changePassword.getNewPassword().trim().equalsIgnoreCase(changePassword.getConfirmPassword().trim()))){
			logger
					.error("ChangePasswordValidator validate method found password and confirm password not same.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_MISMATCH);
		}
		else if (!(ValidatorUtility
				.isNumeric(changePassword.getNewPassword().trim()))) {
			logger
					.fatal("ChangePasswordValidator validate method found Confirm Password contains no numeric characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_NUMERIC_INVALID);
		}else if (!(ValidatorUtility
				.isNumeric(changePassword.getConfirmPassword().trim()))) {
			logger
					.fatal("ChangePasswordValidator validate method found Confirm Password contains no numeric characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_CHANGEPASSWORD_NUMERIC_INVALID);
		}		
	}
}
