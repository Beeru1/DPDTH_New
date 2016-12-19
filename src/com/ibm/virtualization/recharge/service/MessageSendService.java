/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for  management service.
 * 
 * @author bhanu
 * 
 */
public interface MessageSendService {

	
	/**
	 * This method send sms  of  the reset password .
	 * 
	 * @param loginName
	 * @return TODO
	 * @return loginId
	 * @throws VirtualizationServiceException
	 */
	public boolean sendSMS(String mobileNo, String message)throws VirtualizationServiceException  ;
	
	
	/**
	 * This method send Email  of  the reset password .
	 * 
	 * @param loginName
	 * @return loginId
	 * @throws VirtualizationServiceException
	 */
	
	public void sendMailToRequester(String message,String emailAddress,String subject)throws VirtualizationServiceException; 
}