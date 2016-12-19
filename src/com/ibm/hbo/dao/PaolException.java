/* Avadesh Nagar

* Copyright @ Inc. All Rights Reserved.

 *This software is the confidential and proprietary information of IBM Inc.
* ("Confidential Information"). You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms of the
* license agreement you entered into with IBM
 * ------------------------------------------------------------------------
*/
package com.ibm.hbo.dao;

/**
 * @author avadesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PaolException extends Exception {
	
	private String message = null;
		private String errCode = null;
		private String errMsg = null;
	private int errCtr;
	/**
		 * Default Constructor
		 */
		public PaolException() {

		}
	
	/**
			 * Costructor with arguments initializes error code and error message.
			 * @param className
			 * @param methodName
			 * @param e
			 * @param errCode
			 */
			public PaolException(
				String className,
				String methodName,
				int errCtr,
				String errCode) {
		this.errCtr=errCtr;
				message =
					"An Exception Has Occured in PAOL module in following:-  \n Class name: "
						+ className
						+ "\n"
						+ ":\n Method name : "
						+ methodName
						+ "\n"
						+ "Exception is : "
						+ errCtr;
				this.errCode = errCode;
			}
			
	/**
			 * Costructor with arguments initializes error code and error message.
			 * @param className
			 * @param methodName
			 * @param e
			 * @param errCode
			 */
			public PaolException(
				String className,
				String methodName,
				String errMsg,
				String errCode) {
		this.errMsg=errMsg;
				message =
					"An Exception Has Occured in PAOL module in following:-  \n Class name: "
						+ className
						+ "\n"
						+ ":\n Method name : "
						+ methodName
						+ "\n"
						+ "Exception is : "
						+ errMsg;
				this.errCode = errCode;
			}

		/* (non-Javadoc)
		 * @see java.lang.Throwable#toString()
		 */
		public String toString() {
			
			return message;
		}
		public String getErrCode() {
				
				return errCode;
			}
	public int getErrCtr() {
				
					return errCtr;
				}
		public String getMessage() {
					
					return this.errMsg;
				}		
}
