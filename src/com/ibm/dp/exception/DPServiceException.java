/*****************************************************************************\
 **
 ** DP - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.exception;

/**
 * Specialized java class for exception handling at service level.
 * 
 * @author Paras
 * 
 */
public class DPServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 106181714468503174L;

	private String messageId;
	/**
	 * Single argument constructor which accepts the error code key.
	 * 
	 * @param message
	 */
	public DPServiceException(String message) {
		super(message);
	}
    
	public DPServiceException(String messageId,String message) {
		super(message);
		this.messageId=messageId;
	}
	
	
    public DPServiceException(String message,Throwable cause) {
    	super(message,cause);
    }
    public DPServiceException(Throwable cause) {
    	super(cause);
    }

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	

	
}