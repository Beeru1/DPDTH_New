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

import com.ibm.virtualization.recharge.dto.QueryBalanceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * ReportDao encapsulates data access and manipulation for transactions. It
 * abstracts data and functionality for a data source that stores information
 * for Transactions
 * 
 * Separates a data resource's interface from its data access implementation. It
 * shields the service from modifications to the underlying data source.
 * 
 * @author prakash
 */
public interface QueryDao {
	/**
	 * Get the account details based on mobileNumber passed as argument.
	 * 
	 * @param mobileNumber
	 *            Mobile number of Account whose detail we want to fetch.
	 * @return QueryDTO object containing account details.
	 * @throws DAOException
	 */
	public QueryBalanceDTO getBalance(long accountId) throws DAOException;

	/**
	 * This method is used for check parent child relationship between mobile
	 * numbers passed as argument.
	 * 
	 * @param parentAccountCode
	 *            Account Code of parent
	 * @param childAccountCode
	 *            Child Account Code which we want to check
	 * @return child loginID ->if childmobileNumber is a child of
	 *         parentMobileNumber -1 ->if childMobileNumber is not a child of
	 *         parentMobileNumber.
	 * @throws DAOException
	 *             if any exceptiuon occcured
	 */
	public long isImmediateChild(long loginId, String childMobileNumber)
			throws DAOException;
}