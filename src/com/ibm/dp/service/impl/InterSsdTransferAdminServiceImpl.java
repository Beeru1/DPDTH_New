package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.InterSsdTransferAdminDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.InterSsdTransferAdminService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class InterSsdTransferAdminServiceImpl implements InterSsdTransferAdminService{

	Logger logger = Logger.getLogger(InterSsdTransferAdminServiceImpl.class.getName());
		
		public List<CircleDto> getAllCircleList() throws DPServiceException
		{
			logger.info("********************** getAllCircleList() **************************************");
			Connection connection = null;
			List<CircleDto> circleListDTO = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				
				InterSsdTransferAdminDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				circleListDTO =  interSSDDao.getAllCircleList();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return circleListDTO;
			
			
		}
		
		public ArrayList getAvailableSerialNos(InterSsdTransferAdminDto ssdDto) throws DPServiceException
		{
			logger.info("********************** getAvailableSerialNos() **************************************");
			Connection connection = null;
			ArrayList serialNos = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				
				InterSsdTransferAdminDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				serialNos =  interSSDDao.getAvailableSerialNos(ssdDto);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return serialNos;
			
			
		}
		public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr)  throws DPServiceException
		{
			logger.info("********************** getAvailableSerialNos() **************************************");
			Connection connection = null;
			String strMessage = "";
			try
			{
				connection = DBConnectionManager.getDBConnection();
				
				InterSsdTransferAdminDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				strMessage =  interSSDDao.insertStockTransfs(interssdDtoListItr);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return strMessage;
			
			
		}

		public List<List<CircleDto>> getInitData() throws DPServiceException 
		{
			logger.info("********************** getInitData() **************************************");
			Connection connection = null;
			List<List<CircleDto>> retListDTO = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				
				InterSsdTransferAdminDao interSSDDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				retListDTO =  interSSDDao.getInitData();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return retListDTO;
			
			
		}

		public List getFromDistAccountDetails(int intTSMID, int circleId, int intBusCat) throws DPServiceException
		{
			Connection connection=null;
			List<StockSummaryReportBean> revLogDistAccDetailList = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				InterSsdTransferAdminDao revLogReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				revLogDistAccDetailList = revLogReportDao.getFromDistAccountDetails(intTSMID , circleId, intBusCat);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return revLogDistAccDetailList;
		}

		public List getToDistAccountDetails(int intTSMID, int circleId, int intBusCat) throws DPServiceException {
			Connection connection=null;
			List<StockSummaryReportBean> revLogDistAccDetailList = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				InterSsdTransferAdminDao revLogReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				revLogDistAccDetailList = revLogReportDao.getToDistAccountDetailsDAO(intTSMID , circleId, intBusCat);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return revLogDistAccDetailList;
		}
		@Override
		public List getFromInactivAccountDetails(int intTSMID, int circleId,
				int intBusCat) throws DPServiceException {
			// TODO Auto-generated method stub
			Connection connection=null;
			List<StockSummaryReportBean> revLogDistAccDetailList = null;
			try
			{
				connection = DBConnectionManager.getDBConnection();
				InterSsdTransferAdminDao revLogReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
						ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getInterSSDTransDao(connection);
				
				revLogDistAccDetailList = revLogReportDao.getFromInactiveDistAccountDetails(intTSMID , circleId, intBusCat);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return revLogDistAccDetailList;
		}


}
