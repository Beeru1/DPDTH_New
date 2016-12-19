package com.ibm.virtualization.recharge.validation;

import org.apache.log4j.Logger;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class LoginValidator {
	private static LoginValidator loginValidator;

	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(LoginValidator.class.getName());

	/**
	 * private constructor for LoginValidator
	 * 
	 */
	private LoginValidator() {
		/* Private Constructor */
	}

	/**
	 * This method returns instance of LoginValidator
	 * 
	 * @return loginValidator
	 */
	public static LoginValidator getInstance() {
		logger.info("Entered getInstance method for LoginValidator.");
		if (loginValidator == null)
			loginValidator = new LoginValidator();
		logger
				.info("getInstance method successfully retured LoginValidator instance.");
		return loginValidator;
	}
	
	public void validate(Login login) throws VirtualizationServiceException{
		
		if (login == null) {
			logger
					.error("LoginValidator validate method found login dto  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_LOGIN_DATA_REQUIRED);
		}
		
		else if ((login.getLoginName().trim() == null)
				|| (login.getLoginName().trim().equals(""))) {
			logger
					.error("LoginValidator validate method found Login Name  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_USER_NAME_REQ);
		}
		
		else if (!(ValidatorUtility
				.isJunkFree(login.getLoginName().trim()))) {
			logger
					.fatal("LoginValidator validate method found Login Name contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_INVALID_NAME_PASSWORD);
		}
		
		else if ((login.getPassword().trim() == null)
				|| (login.getPassword().trim().equals(""))) {
			logger
					.error("LoginValidator validate method found Password  as Invalid.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_PASSWORD_REQ);
		}
		else if (!(ValidatorUtility
				.isJunkFree(login.getPassword().trim()))) {
			logger
					.fatal("LoginValidator validate method found Password contains junk characters.");
			throw new VirtualizationServiceException(
					ExceptionCode.Authentication.ERROR_INVALID_NAME_PASSWORD);
		}
	}
}

