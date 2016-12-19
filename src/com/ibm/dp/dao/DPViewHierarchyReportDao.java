package com.ibm.dp.dao;

import java.util.List;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPViewHierarchyReportDao {

	public List getViewHierarchyDistAccountDetails(int roleId ,  int circleId) throws DAOException;
	public List getviewhierarchyTsmAccountDetails( int levelId) throws DAOException; 
	public List getviewhierarchyFromTsmAccountDetails( int levelId) throws DAOException; 
	public List getviewhierarchyTsmDistLogin( int distId) throws DAOException; 
	public List getHierarchyAll( String[] distList) throws DAOException; 
	
	// Added by parnika for TSM list of same transaction type 	
	public List getSameTransactionTsmAccountDetails(int levelId, long loginId) throws DAOException;
	public List getDepositTypeFromTsmAccountDetails(int levelId) throws DAOException;
	public List getDepositTypeTsmAccountDetails(int levelId) throws DAOException;
	public List getCategoryWiseFromTsmAccountDetails(int levelId, int busCat) throws DAOException;
	public List getCategoryWiseTsmAccountDetails(int levelId, int busCat) throws DAOException;
	
	
	
	// End of changes by Parnika
}
