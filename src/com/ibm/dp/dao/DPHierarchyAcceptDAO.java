package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DPHierarchyAcceptDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPHierarchyAcceptDAO 
{
	/**
	 * This method is used to fetch the data for the Hierarchy Transfers forwarded to a distributor
	 * 
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	List<DPHierarchyAcceptDTO> getHierarchyTransferInitDao(long loginUserId)throws DAOException;

	List<DPHierarchyAcceptDTO> viewHierarchyAcceptList(String strTRNo, long longDistID, Integer intTransferBy, String strTrnsTime)throws DAOException;

	/**
	 * This method is used to save the data for the Hierarchy Transfers Accepted
	 * @param arrCheckedTR
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	List<DPHierarchyAcceptDTO> acceptHierarchyDao(String[] arrCheckedTR, long loginUserId)throws DAOException;
	
	List<DPHierarchyAcceptDTO> getStockDetails(String account_id, String role)throws DPServiceException;



}
