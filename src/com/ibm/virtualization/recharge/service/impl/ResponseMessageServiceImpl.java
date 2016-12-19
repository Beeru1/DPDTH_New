/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.RecieverType;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.ResponseMessageService;

/**
 * This class provides the implementation for all the method declarations in
 * ResponseMessageService interface
 * 
 * @author ashish
 * 
 */
public class ResponseMessageServiceImpl implements ResponseMessageService {

	/* Logger for this class. */
	private Logger logger = Logger.getLogger(ResponseMessageServiceImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ResponseMessageService#getMessageDetails(java.lang.String)
	 */
	public HashMap<RecieverType, String> getMessageDetails(String msgCode)
			throws VirtualizationServiceException {
		logger.info("Started... message code " + msgCode);
		HashMap<RecieverType, String> map = null;
		map = CacheFactory.getCacheImpl().getMessageDetails(msgCode);
		logger
				.info(" Executed...get message detail corresponding to message code "
						+ msgCode);
		return map;
	}
}
