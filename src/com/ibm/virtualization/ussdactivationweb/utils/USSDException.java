/*
 * Created on July 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package com.ibm.virtualization.ussdactivationweb.utils;

/**This class is Exception handling class for USSD Activation.
 * All System exception will be wrapped into USSDException .
 * @author Ashad 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class USSDException extends Exception {

	private String message = null;
	private String errCode = null;
	private String errMsg = null;
	/**
		 * Default Constructor
		 */
	public USSDException() {

	}
	public USSDException(String msg) {
			super(msg);
	}
	public USSDException(String msg, Throwable th) {
		super(msg);
	}
	
	/**
		 * Costructor with arguments initializes error code and error message.
		 * @param className
		 * @param methodName
		 * @param errMsg
		 * @param errCode
		 */
	public USSDException(
		String className,
		String methodName,
		String errMsg,
		String errCode) {
		this.errMsg = errMsg;
		message =
			"An Exception Has Occured in Login module in following:-  \n Class name: "
				+ className
				+ "\n"
				+ ":\n Method name : "
				+ methodName
				+ "\n"
				+ "Exception is : "
				+ errMsg;
		this.errCode = errCode;
	}

	/**
	 * @param className
	 * @param methodName
	 * @param errMsg
	 * @param errCode
	 * @param e
	 */
	public USSDException(
		String className,
		String methodName,
		String errMsg,
		String errCode,
		Exception e) {

		message = e.getMessage();

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	public String toString() {

		return message;
	}
	/**
	 * @return
	 */
	public String getErrCode() {

		return errCode;
	}
	/**
	 * getMessage  returns the message String.
	 *
	 * @return String
	 */

	public String getMessage() {

		return this.errMsg;
	}
}
