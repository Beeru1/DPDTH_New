package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DistAvStockUploadDao;
import com.ibm.dp.dao.impl.DistAvStockUploadDaoImpl;
import com.ibm.dp.dao.impl.ViewStockEligibilityDaoImpl;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DistAvStockUploadService;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
public class DistAvStockUploadServiceImpl implements DistAvStockUploadService
{
	
	Logger logger = Logger.getLogger(DistAvStockUploadServiceImpl.class.getName());
	public boolean validateCell(String str)
	{
		if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH_AV)
		{
			return false;
		}
		else if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		return true;
	}
	public List uploadExcel(FormFile myXls, int intCatCode) throws DPServiceException 
	{
		Connection connection = null;
		List list = new ArrayList();
		try 
		{
			logger.info("***************AvStockUploadServiceImpl********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			logger.info("***************in avstockimpl2********");
			list = avStockUploadDao.uploadExcel(myXls, intCatCode);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			throw new DPServiceException(ex.getMessage());
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	
	public String insertAVStockinDP(List list, int intCatCode, long lnDistID, int intCircleID, int intProductID) throws DPServiceException 
	{
		Connection connection = null;
		String insertStatus="";
		try 
		{
			logger.info("***************getting the database connection for insertAVStockinDP********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			logger.info("***************in insertAVStockinDP********");
			insertStatus= avStockUploadDao.insertAVStockinDP(connection,list, intCatCode, lnDistID, intCircleID, intProductID);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			logger.info("*************** exception ocurred @$&%*********");
			throw new DPServiceException(ex.getMessage());
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return insertStatus;
	}
	public List<List<CircleDto>> getInitData(int intCircleID) throws DPServiceException 
	{
		Connection connection = null;
		List<List<CircleDto>> listRet = new ArrayList<List<CircleDto>>();
		try 
		{
			logger.info("***************getting the database connection for getInitData********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			logger.info("***************in insertAVStockinDP********");
			listRet = avStockUploadDao.getInitDataDAO(connection,intCircleID);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			logger.info("*************** exception ocurred @$&%*********");
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return listRet;
	}
	
	//added by aman
	public List<NonSerializedToSerializedDTO> getSerializedConversion(String strOLMId) throws DPServiceException 
	{
		List<NonSerializedToSerializedDTO> viewStockList = new ArrayList<NonSerializedToSerializedDTO>();	
		
		try
		{
			Connection connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			
			viewStockList = avStockUploadDao.serializedConversion(connection,strOLMId);  
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new DPServiceException(de.getMessage());
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
		return viewStockList;
	}
	public List<NonSerializedToSerializedDTO> getALLProdSerialData(String circleId) throws DPServiceException 
	{
		List<NonSerializedToSerializedDTO> getALLProdSerialData = new ArrayList<NonSerializedToSerializedDTO>();	
		
		try
		{
			Connection connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			
			getALLProdSerialData = avStockUploadDao.getALLProdSerialData(connection,circleId);  
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new DPServiceException(de.getMessage());
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
		return getALLProdSerialData;
	}
	public List uploadExcel(InputStream myxls, String distOlmID, String circleId) throws DPServiceException {
		Connection connection = null;
		List list = new ArrayList();
		try 
		{
			logger.info("***************AvStockUploadServiceImpl********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			logger.info("***************in avstockimpl2********");
			list = avStockUploadDao.uploadSerializedExcel(myxls,distOlmID, circleId);	
			logger.info("********return in uploadExcel************");
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			throw new DPServiceException(ex.getMessage());
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	public String convertNserToSerStock(List list, String distOlmID,String circleId) throws DPServiceException 
	{
		Connection connection = null;
		String insertStatus="";
		try 
		{
			logger.info("***************getting the database connection for insertAVStockinDP********");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistAvStockUploadDao avStockUploadDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getuploadFileDist(connection);
			logger.info("***************in insertAVStockinDP********");
			insertStatus= avStockUploadDao.convertNserToSerStock(connection, list, distOlmID, circleId);	
			connection.commit();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			ex.printStackTrace();
			logger.info("*************** exception ocurred @$&%*********");
			throw new DPServiceException(ex.getMessage());
		} 
		
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return insertStatus;
	}
	
	//end of changes by aman

}


