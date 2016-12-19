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

import com.ibm.virtualization.recharge.dto.EnquiryTransaction;
import com.ibm.virtualization.recharge.dto.QueryBalanceDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Service class for Quries as a facade to encapsulate the complexity of
 * interactions between the business objects participating in a workflow.
 * 
 * Provide a uniform coarse-grained service layer to separate business object
 * implementation from business service abstraction. It hides from the client
 * the underlying interactions and interdependencies between business components
 * specific to the Reports. This provides better manageability, centralization
 * of interactions (responsibility), greater flexibility, and greater ability to
 * cope with changes.
 * 
 * @author prakash
 */
public interface QueryService {
	/**
	 * This method is used to get the account details based on user accId passed
	 * as argument.
	 * 
	 * @param context
	 *            context contains details of logged in user
	 * @param dto
	 * @return QueryDTO object containing account details.
	 * @throws VirtualizationServiceException
	 */
	public QueryBalanceDTO getBalance(EnquiryTransaction dto)
			throws VirtualizationServiceException;

	/**
	 * This method is used to get the account details of child account based on
	 * its and its parent accId passed as argument and its mobileNumber
	 * 
	 * @param context
	 *            context contains details of logged in user
	 * @param childMobileNumber
	 *            Child Mobile Number
	 * @param dto
	 * @return QueryDTO object conntaining the child account details;
	 * @throws VirtualizationServiceException
	 */

	public QueryBalanceDTO getChildBalanceByMobileNo(EnquiryTransaction dto)
			throws VirtualizationServiceException;

}