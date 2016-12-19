package com.ibm.dp.service.impl;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.dao.DPViewHierarchyReportDao;
import com.ibm.dp.dao.impl.DPViewHierarchyReportDaoImpl;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public class DPViewHierarchyReportServiceImpl implements DPViewHierarchyReportService {
	private Logger logger = Logger.getLogger(DPViewHierarchyReportServiceImpl.class.getName());
	public List getViewHierarchyDistAccountDetails(int levelId , int circleId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> viewhierarchyDistAccDetailList = null;
		try
		{
			viewhierarchyDistAccDetailList = revLogReportDao.getViewHierarchyDistAccountDetails( levelId , circleId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return viewhierarchyDistAccDetailList;
	}
	
	
	
	public List getviewhierarchyTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getviewhierarchyTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	public List getviewhierarchyFromTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getviewhierarchyFromTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getviewhierarchyTsmDistLogin(int distId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getviewhierarchyTsmDistLogin( distId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}	
	public List getHierarchyAll(String[] distList)throws VirtualizationServiceException
	{
		Connection con=null;
		
		
		
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			con = DBConnectionManager.getDBConnection();
			DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
			revLogTsmAccDetailList = revLogReportDao.getHierarchyAll( distList);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(con);
		}
		return revLogTsmAccDetailList;
	}
	
	// Added by parnika for TSM list of same transaction type 
	
	public List getSameTransactionTsmAccountDetails(int levelId, long loginId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getSameTransactionTsmAccountDetails(levelId, loginId);
			
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getDepositTypeFromTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getDepositTypeFromTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getDepositTypeTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getDepositTypeTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getCategoryWiseFromTsmAccountDetails(int levelId, int busCat)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getCategoryWiseFromTsmAccountDetails(levelId, busCat);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getCategoryWiseTsmAccountDetails(int levelId, int busCat)throws VirtualizationServiceException
	{
		Connection con=null;
		DPViewHierarchyReportDao revLogReportDao = new DPViewHierarchyReportDaoImpl(con);
		List<DPViewHierarchyFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getCategoryWiseTsmAccountDetails(levelId, busCat);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	// End of changes by Parnika
	
}


