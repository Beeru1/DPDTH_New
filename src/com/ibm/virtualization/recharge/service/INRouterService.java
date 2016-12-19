/**
 * 
 */
package com.ibm.virtualization.recharge.service;

import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * @author puneetla
 * 
 */
public interface INRouterService {
	/**
	 * Returns the details of the in to which this request should be forwarded.
	 * 
	 * @param subscriberNumber -
	 *            The number for which this information is desired
	 * @return DTO containing the details.
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber)
			throws VirtualizationServiceException;
}
