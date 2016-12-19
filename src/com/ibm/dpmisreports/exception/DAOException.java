/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dpmisreports.exception;

/**
 * Specialized Java class for Exception Handling at DAO level
 * 
 * @author Puneet Lakhina
 * 
 */
public class DAOException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3427610629676303327L;

	/**
	 * Single argument constructor which accepts the error code key.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}

}