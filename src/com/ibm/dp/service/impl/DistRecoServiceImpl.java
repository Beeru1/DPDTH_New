package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dao.DistRecoDao;
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DistRecoService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DistRecoServiceImpl implements DistRecoService{

Logger logger = Logger.getLogger(DistRecoServiceImpl.class.getName());
	
	public List<RecoPeriodDTO> getRecoPeriodList(String login_id) throws DPServiceException
	{
		logger.info("********************** getRecoPeriodList() **************************************");
		Connection connection = null;
		List<RecoPeriodDTO> periodListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			periodListDTO =  distRecoDao.getRecoPeriodList(login_id);
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
		return periodListDTO;
		
		
	}
	public List<RecoPeriodDTO> getRecoPeriodListAdmin() throws DPServiceException
	{
		logger.info("********************** getRecoPeriodList() **************************************");
		Connection connection = null;
		List<RecoPeriodDTO> periodListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			periodListDTO =  distRecoDao.getRecoPeriodListAdmin();
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
		return periodListDTO;
		
		
	}
//	added by vishwas
	public String Uploadvalidation(Long distId,int recoId,String productId,int coloumn ,String okWithSystemStock) throws DPServiceException
	{
		Connection connection = null;
		DistRecoDao dao = null;
		String result=null;
		try
		{
		connection = DBConnectionManager.getDBConnection();
		connection.setAutoCommit(false);
		dao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
		result = dao.Uploadvalidation(distId, recoId,productId,coloumn ,okWithSystemStock);
		}
		catch(Exception ex)
		{
			logger.info("Exception in service impl while getting not ok stbs :");
			ex.printStackTrace();
		}
	return result;	
	}
	//end by vishwas
//	added by vishwas
	public String uploadClosingStockDetailsXlsagain(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException
	{
		String excelMessage = null;
		Connection connection = null;
		DistRecoDao uploadExcel = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			uploadExcel = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getDistRecoDao(connection);
			excelMessage = uploadExcel.uploadClosingStockDetailsXlsagain(file,stockType,productId,distId,indexes,recoId);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return excelMessage;
	}
	//end by vishwas

	
	public List<DistRecoDto> getRecoProductList(String stbType, String recoPeriod,String distId,boolean result ,String stockType, String productId) throws DPServiceException
	{
		logger.info("********************** getRecoProductList() **************************************");
		Connection connection = null;
		List<DistRecoDto> periodListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			logger.info("pppppppp pro d:"+productId);
			periodListDTO =  distRecoDao.getRecoProductList(stbType, recoPeriod,distId,result,stockType,productId);
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
		return periodListDTO;
		
		
	}
	public String submitDetail(DistRecoDto distRecoDto) throws DPServiceException
	{
		logger.info("********************** submitDetail() **************************************");
		Connection connection = null;
		String message = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			message =  distRecoDao.submitDetail(distRecoDto);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			message ="-1";
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return message;
		
		
	}
	public List<PrintRecoDto> getRecoPrintList(String certificateId) throws DPServiceException
	{
		logger.info("********************** getRecoPrintList() **************************************");
		Connection connection = null;
		List<PrintRecoDto> printListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			printListDTO =  distRecoDao.getRecoPrintList(certificateId);
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
		return printListDTO;
		
		
	}
	public boolean compareRecoGoLiveDate(String recoPeriod) throws DPServiceException
	{
		logger.info("********************** compareRecoGoLiveDate() **************************************");
		Connection connection = null;
		List<PrintRecoDto> printListDTO = null;
		boolean result=false;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			result =  distRecoDao.compareRecoGoLiveDate(recoPeriod);
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
		return result;
		
		
	}
	public List<DistRecoDto> getDetailsList(String productId, String columnId,Long distId,String tabId,String recoPeriodId) throws DPServiceException
	{
		logger.info("********************** getDetailsList() **************************************");
		Connection connection = null;
		List<DistRecoDto> distRecoDtoList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
			ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			distRecoDtoList =  distRecoDao.getDetailsList(productId, columnId, distId, tabId,recoPeriodId);
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
		return distRecoDtoList;
		
		
	}
	public String uploadClosingStockDetailsXls(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException
	{
		String excelMessage = null;
		Connection connection = null;
		DistRecoDao uploadExcel = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			uploadExcel = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
														.getDistRecoDao(connection);
			excelMessage = uploadExcel.uploadClosingStockDetailsXls(file,stockType,productId,distId,indexes,recoId);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return excelMessage;
	}
	public String findNotOkStbs(Long distId,int recoId,String productId) throws DPServiceException
	{
		Connection connection = null;
		DistRecoDao dao = null;
		String result=null;
		try
		{
		connection = DBConnectionManager.getDBConnection();
		connection.setAutoCommit(false);
		dao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
		result = dao.findNotOkStbs(distId, recoId,productId);
		}
		catch(Exception ex)
		{
			logger.info("Exception in service impl while getting not ok stbs :");
			ex.printStackTrace();
		}
	return result;	
	}
	
	//	added by beeru on 21 march 2014 for geting reco product list
	public List<DistRecoDto> getProductList(String recoPeriod, String distId ,String stockType) throws DPServiceException {
		
		logger.info("********************** getProductList() **************************************");
		Connection connection = null;
		List<DistRecoDto> periodListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DistRecoDao distRecoDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistRecoDao(connection);
			
			periodListDTO =  distRecoDao.getProductList(recoPeriod,distId,stockType);
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
		return periodListDTO;
	}
	
	//	added by beeru end Point
}
