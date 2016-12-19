package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dao.RepairSTBDao;
import com.ibm.dp.dao.StockSummaryReportDao;
import com.ibm.dp.dao.impl.StockSummaryReportDaoImpl;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class StockSummaryReportServiceImpl implements StockSummaryReportService{
		private Logger logger = Logger.getLogger(StockSummaryReportServiceImpl.class.getName());

		public List getRevLogTsmAccountDetails(int levelId)throws VirtualizationServiceException
		{
			Connection con=null;
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			List<StockSummaryReportBean> revLogTsmAccDetailList = null;
			try
			{
				revLogTsmAccDetailList = revLogReportDao.getRevLogTsmAccountDetails(levelId);
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
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			List<StockSummaryReportBean> revLogTsmAccDetailList = null;
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
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			List<StockSummaryReportBean> revLogDistAccDetailList = null;
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
		
		public List getRevLogFromDistAccountDetails(int levelId , int circleId)throws VirtualizationServiceException
		{
			Connection con=null;
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			List<StockSummaryReportBean> revLogDistAccDetailList = null;
			try
			{
				revLogDistAccDetailList = revLogReportDao.getRevLogFromDistAccountDetails( levelId , circleId);
			}
			catch (DAOException de) {
				logger.error(" Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			}	
			return revLogDistAccDetailList;
		}

		public List<StockSummaryReportDto> getRevLogReportExcel( int distId , String fromDate, String toDate , String circleId , String tsmId,int loginRole,String productId) throws VirtualizationServiceException
		{
			System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
			Connection connection = null;
			
			List<StockSummaryReportDto> distributorStockReportList = new ArrayList<StockSummaryReportDto>();
			try
			{
				connection = DBConnectionManager.getDBConnection();
				
				StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(connection);
				distributorStockReportList = revLogReportDao.getRevLogReportExcel(distId , fromDate, toDate , circleId , tsmId,loginRole,productId);
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
		public List<ProductMasterDto> getProductTypeMaster(String circleId , String dcNo) throws DPServiceException, VirtualizationServiceException
		{
			logger.info("********************** getProductTypeMaster() **************************************");
			List<ProductMasterDto> productListDTO = null;
			Connection con=null;
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			try
			{
				productListDTO = revLogReportDao.getProductTypeMaster(circleId,dcNo);
			}
			catch (DAOException de) {
				logger.error(" Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			}	
			return productListDTO;
			
			
		}
	
		public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DPServiceException, VirtualizationServiceException
		{
			logger.info("********************** getProductTypeMaster() **************************************");
			List<ProductMasterDto> productListDTO = null;
			Connection con=null;
			StockSummaryReportDao revLogReportDao = new StockSummaryReportDaoImpl(con);
			try
			{
				productListDTO = revLogReportDao.getProductTypeMaster(circleId);
			}
			catch (DAOException de) {
				logger.error(" Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			}	
			return productListDTO;
			
			
		}
}
