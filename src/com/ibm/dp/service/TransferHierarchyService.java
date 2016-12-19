package com.ibm.dp.service;

import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.TransferHierarchyDto;
import com.ibm.dp.exception.DPServiceException;

public interface TransferHierarchyService 
{

//	List<TransferHierarchyDto> getChildUser(String userName) throws DPServiceException;
//	List<TransferHierarchyDto> getParentUser(String userNAme) throws DPServiceException;
	String saveTransfferedHierarchy(String childParentMapping, String strOLMID) throws DPServiceException;
	Map<String, List<TransferHierarchyDto>> getHierarchyDetails(String childParentMapping) throws DPServiceException;

}
