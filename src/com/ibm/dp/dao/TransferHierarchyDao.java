package com.ibm.dp.dao;

import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.TransferHierarchyDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface TransferHierarchyDao 
{
	public String saveTransfferedHierarchy(String childParentMapping, String strOLMID)throws DAOException;
	public Map<String, List<TransferHierarchyDto>> getHierarchyDetailsDao(String userName)throws DAOException;
}
