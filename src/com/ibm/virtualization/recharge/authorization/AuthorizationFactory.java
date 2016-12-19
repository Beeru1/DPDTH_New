/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.authorization;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.service.AuthorizationInterface;

/**
 * The AuthorizationFactory class, returns the instance of class for handling
 * authorization related operations
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */
public class AuthorizationFactory {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(AuthorizationFactory.class
			.getName());

	/* Instance of AuthorizerImpl class. */
	private static AuthorizationInterface authorizer = null;

	/**
	 * private constructor
	 */
	private AuthorizationFactory() {
	}

	/**
	 * This method returns an instance of AuthorizationImpl class
	 * 
	 * @return IAuthorizer
	 */
	public static AuthorizationInterface getAuthorizerImpl() {

		if (authorizer == null) {
			authorizer = new AuthorizationServiceImpl();
			logger.info("Authorizer Services initialized for first time usage ");
		}
		return authorizer;
	}
}
