/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.exception;

/**
 * Specialized java class for exception handling at service level.
 * 
 * @author Paras
 * 
 */
public class VirtualizationServiceException extends Exception {

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
	public VirtualizationServiceException(String message) {
		super(message);
	}
    
	public VirtualizationServiceException(String messageId,String message) {
		super(message);
		this.messageId=messageId;
	}
	
	
    public VirtualizationServiceException(String message,Throwable cause) {
    	super(message,cause);
    }
    public VirtualizationServiceException(Throwable cause) {
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