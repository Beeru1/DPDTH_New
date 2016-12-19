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

import java.util.HashMap;

import com.ibm.virtualization.recharge.common.RecieverType;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * @author ashish
 * 
 */
public interface ResponseMessageService {

	/**
	 * 
	 * Retrieves the message corresponding to the message code
	 * 
	 * @param msgCode
	 * @param detailsDto
	 * @return map of String objects
	 * @throws VirtualizationServiceException
	 */
	public abstract HashMap<RecieverType, String> getMessageDetails(
			String msgCode) throws VirtualizationServiceException;

}