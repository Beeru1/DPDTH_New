package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DPHierarchyAcceptDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DPHierarchyAcceptService {

	List<DPHierarchyAcceptDTO> getHierarchyTransferInit(long loginUserId) throws DPServiceException;
	List<DPHierarchyAcceptDTO> viewHierarchyAcceptList(long longLoginId, String strTRNo, Integer intTransferBy, String strTrnsTime) throws DPServiceException;
	List<DPHierarchyAcceptDTO> acceptHierarchy(String[] arrCheckedTR, long loginUserId)throws DPServiceException;
	List<DPHierarchyAcceptDTO> getStockDetails(String account_id, String role)throws DPServiceException;
	
	
	
	
}
