/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao;

import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for INRouterDao Implementation.
 * 
 * @author Mohit Aggarwal
 */
public interface INRouterDao {

	/**
	 * Returns the details of the IN to which this request should be forwarded.
	 * 
	 * @param subscriberNumber -
	 *            The number for which this information is desired
	 * @return DTO containing the details.
	 * throws DAOException
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber) throws DAOException;
}
