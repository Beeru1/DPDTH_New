package com.ibm.dp.service;

import java.util.List;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPViewHierarchyReportService {

	public List getViewHierarchyDistAccountDetails(int roleId ,  int circleId) throws VirtualizationServiceException;
	public List getviewhierarchyTsmAccountDetails(int roleId)throws VirtualizationServiceException;
	public List getviewhierarchyFromTsmAccountDetails(int roleId)throws VirtualizationServiceException;
	public List getviewhierarchyTsmDistLogin(int distId)throws VirtualizationServiceException;
	public List getHierarchyAll(String[] distList)throws VirtualizationServiceException;
	// Added by parnika 
	public List getSameTransactionTsmAccountDetails(int levelId, long loginId)throws VirtualizationServiceException;
	public List getCategoryWiseFromTsmAccountDetails(int roleId,int busCat)throws VirtualizationServiceException;
	public List getDepositTypeFromTsmAccountDetails(int levelId)throws VirtualizationServiceException;
	public List getDepositTypeTsmAccountDetails(int levelId)throws VirtualizationServiceException;
	public List getCategoryWiseTsmAccountDetails(int roleId, int busCat)throws VirtualizationServiceException;
	
	// End of changes by Parnika
}
