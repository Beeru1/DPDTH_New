package com.ibm.dp.service.impl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dao.DPReverseChurnCollectionDAO;

import com.ibm.dp.dto.DPReverseChurnCollectionDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPReverseChurnCollectionService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.dao.impl.DPReverseChurnCollectionDAOImpl;

public class DPReverseChurnCollectionServiceImpl implements DPReverseChurnCollectionService
{
	
	Logger logger = Logger.getLogger(DPReverseChurnCollectionServiceImpl.class.getName());

	public ArrayList viewPendingChurnSTBList(long accountID)throws DPServiceException, DAOException, SQLException 
	{
		java.sql.Connection connection = null;
		ArrayList pendingChurnList = null;
		
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			
			DPReverseChurnCollectionDAO dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			pendingChurnList = dppod.viewChurnDetails(accountID);
       		//connection.commit();
		} 
		catch (Exception e) 
		{
			/*try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}*/
			e.printStackTrace();
			logger.info("***ERROR IN METHOD viewPendingChurnSTBList() ::  "+e);
			throw new DPServiceException(e.getMessage());
		}
		finally 
		{
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return pendingChurnList;
	}
	
	public List uploadExcel(FormFile myXls)  throws DPServiceException 
	{
		Connection connection = null;
		List list = new ArrayList();
		try		
		{
			connection = DBConnectionManager.getDBConnection();
			DPReverseChurnCollectionDAO churnDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue
						(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			list = churnDAO.uploadExcel(myXls);
			
			//connection.commit();
		}
		catch (Exception e) 
		{
/*			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}*/
			e.printStackTrace();
			logger.info("***ERROR IN METHOD uploadExcel() ::  "+e);
			throw new DPServiceException(e.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	
	
	public ArrayList insertFreshChurnSTB(DPReverseChurnCollectionDTO churnDTO, Long lnUserID, int circleId)
	throws DPServiceException
	{
		ArrayList serialNosList = new ArrayList();
		Connection connection = null;	
		try 
		
		{
			connection = DBConnectionManager.getDBConnection();
			DPReverseChurnCollectionDAO churnDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue
					(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			
		 	serialNosList = churnDAO.insertFreshChurnSTB(churnDTO, lnUserID, circleId);
		 	
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			e.printStackTrace();
			logger.info("***ERROR IN METHOD insertFreshChurnSTB() ::  "+e);
			throw new DPServiceException(e.getMessage());
		}
		
		return serialNosList;
	}
	

	
	

	
	public ArrayList<DPReverseChurnCollectionDTO> reportChurn(String[] arrCheckedChurn, long loginUserId, String strRemarks)throws DPServiceException
	{
		java.sql.Connection connection = null;
		ArrayList<DPReverseChurnCollectionDTO> arrReturn = null;
		try		
		{
			connection = DBConnectionManager.getDBConnection();
			DPReverseChurnCollectionDAO churnDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			arrReturn = churnDao.reportChurnDao(arrCheckedChurn, loginUserId,strRemarks);
			
			connection.commit();
		}	
		catch(Exception e)		
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}  
		finally		
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return arrReturn;
	}
		

	public ArrayList<DPReverseChurnCollectionDTO> reportChurnAddToStock(String[] arrCheckedChurn, long loginUserId, String strRemarks)throws DPServiceException	
	{
		java.sql.Connection connection = null;
		ArrayList<DPReverseChurnCollectionDTO> arrReturn = null;
		try		
		{
			connection = DBConnectionManager.getDBConnection();
			DPReverseChurnCollectionDAO churnDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			
			arrReturn = churnDao.reportChurnAddToStockDao(arrCheckedChurn, loginUserId, strRemarks);
			
			connection.commit();
		}	
		catch(Exception e)		
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}  
		finally		
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return arrReturn;
	}
	public String  checkCPEInventory(String stb_no)throws DPServiceException	{
		java.sql.Connection connection = null;
		String checkCPE = null;
		try		{
			connection = DBConnectionManager.getDBConnection();
			DPReverseChurnCollectionDAO churnDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).reportChurnDao(connection);
			checkCPE = churnDao.checkCPEInventory(stb_no);
			
		}	catch(Exception e)		{
			e.printStackTrace();
			logger.error("***********Exception occured while fetching initial data for DCA************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}  finally		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return checkCPE;
	}
	public int checkTransactionTypeReverse(Long distId)throws DPServiceException
	{
	java.sql.Connection connection  = null;
	int total = 0;
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		total = dppod.checkTransactionTypeReverse(distId);
			
	}catch (Exception e) {
		
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return total;
	}
	
	
}
