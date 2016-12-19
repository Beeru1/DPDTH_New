//ask what needs to be done for List<InventoryStatusBean>??
//line no. 150 onwards has confusing methods starting with List..

package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.dao.DPReverseLogisticReportDao;
import com.ibm.dp.dao.impl.DPReverseLogisticReportDaoImpl;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.dp.service.DPReverseLogisticReportService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public class DPReverseLogisticReportServiceImpl implements DPReverseLogisticReportService {
	
	private Logger logger = Logger.getLogger(DPCreateBeatServiceImpl.class.getName());

	public List getRevLogTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(con);
		List<DPReverseLogisticReportFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getRevLogTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	public List getRevLogTsmDistLogin(int distId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(con);
		List<DPReverseLogisticReportFormBean> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getRevLogTsmDistLogin( distId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}	
	public List getRevLogDistAccountDetails(int levelId , int circleId)throws VirtualizationServiceException
	{
		Connection con=null;
		DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(con);
		List<DPReverseLogisticReportFormBean> revLogDistAccDetailList = null;
		try
		{
			revLogDistAccDetailList = revLogReportDao.getRevLogDistAccountDetails( levelId , circleId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogDistAccDetailList;
	}

	public List<DPReverseLogisticReportDTO> getRevLogReportExcel( int distId , String fromDate, String toDate , String circleId , String tsmId) throws VirtualizationServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		
		List<DPReverseLogisticReportDTO> distributorStockReportList = new ArrayList<DPReverseLogisticReportDTO>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
										//	.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
			distributorStockReportList = revLogReportDao.getRevLogReportExcel(distId , fromDate, toDate , circleId , tsmId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return distributorStockReportList;
	}
	public List<DPReverseLogisticReportDTO> getRevLogReportDetailExcel( int distId , String fromDate, String toDate , String circleId , String tsmId) throws VirtualizationServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		
		List<DPReverseLogisticReportDTO> distributorStockReportList = new ArrayList<DPReverseLogisticReportDTO>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
										//	.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
			distributorStockReportList = revLogReportDao.getRevLogReportDetailExcel(distId , fromDate, toDate , circleId , tsmId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return distributorStockReportList;
	}
	public List getRevLogAccountDetails(int roleId, int distId, int tsmId) throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DPReverseLogisticReportDTO> getlastPOReportExcel( int distId , String circleId , String tsmId) throws VirtualizationServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		List<DPReverseLogisticReportDTO> distributorStockReportList = new ArrayList<DPReverseLogisticReportDTO>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
										//	.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
			distributorStockReportList = revLogReportDao.getlastPOReportExcel(distId , circleId , tsmId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return distributorStockReportList;
	}

	
	// Add by harbans on MIS Reports consumption
	public List<DPSCMConsumptionReportDto> getConsumptionReportExcel( int distId , int tsmId, int circleId, String fromDate, String toDate) throws VirtualizationServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		
		List<DPSCMConsumptionReportDto> consumptionReportList = new ArrayList<DPSCMConsumptionReportDto>(); 
		try
		{
			DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
			
//			Add By harabns 27may
			if(circleId == 0)// Pan India
			{
				consumptionReportList = revLogReportDao.getConsumptionReportExcelPanIndia(distId , tsmId, fromDate, toDate);
			}else
			{
				consumptionReportList = revLogReportDao.getConsumptionReportExcel(distId , tsmId, circleId, fromDate, toDate);
			}
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return consumptionReportList;
	}

		
//added by saumya for po status report
public List<DPReverseLogisticReportDTO> getPoReportExcel(int distId, String fromDate, String toDate, String circleId, String tsmId) throws VirtualizationServiceException 
{
	System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
	Connection connection = null;
		List<DPReverseLogisticReportDTO> poReportList = new ArrayList<DPReverseLogisticReportDTO>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
										//	.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
			DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
			//poReportList = revLogReportDao.getRevLogReportExcel(distId , fromDate, toDate , circleId , tsmId);
			
			poReportList = revLogReportDao.getPoReportExcel(distId , fromDate, toDate , circleId , tsmId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return poReportList;
	}
public List<DPReverseLogisticReportDTO> getInHandQtyReportExcel(int distId, String circleId, String tsmId, String productId) throws VirtualizationServiceException {
	System.out.println("inside getInHandQtyReportExcel of DPReportServiceImpl");
	Connection connection = null;
	List<DPReverseLogisticReportDTO> inHandQtyList = new ArrayList<DPReverseLogisticReportDTO>();
	try
	{
		connection = DBConnectionManager.getDBConnection();
		//DPMissingStockApprovalDao dpDCADao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
									//	.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInitMissingStockDao(connection);
		DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(connection);
		inHandQtyList = revLogReportDao.getInHandQtyReportExcel(distId , circleId , tsmId,productId);
	}catch (DAOException de) 
	{
		logger.error(" Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}
	finally
	{
		//Releasing the database connection
		DBConnectionManager.releaseResources(connection);
	}
	return inHandQtyList;
}
public List getRevLogDistAccountDetails(int roleId, int circleId, long accountID) throws VirtualizationServiceException 
{
	Connection con=null;
	DPReverseLogisticReportDao revLogReportDao = new DPReverseLogisticReportDaoImpl(con);
	List<DPReverseLogisticReportFormBean> revLogDistAccDetailList = null;
	try
	{
		revLogDistAccDetailList = revLogReportDao.getRevLogDistAccountDetails( roleId , circleId, accountID);
	}
	catch (DAOException de) {
		logger.error(" Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	}	
	return revLogDistAccDetailList;
}
		

		

}