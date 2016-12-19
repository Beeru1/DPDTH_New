package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.EditRecoDetailBean;
import com.ibm.dp.dao.EditRecoDetailDao;
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dao.impl.EditRecoDetailDaoImpl;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.EditRecoDetailService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class EditRecoDetailServiceImpl implements EditRecoDetailService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(EditRecoDetailServiceImpl.class.getName());
	Connection connection = null;
	public List<RecoPeriodDTO> getrecoPeriodList() throws DPServiceException
	{ 
		
		List<RecoPeriodDTO> recoPeriodList= null;
			try {
				connection = DBConnectionManager.getDBConnection();
				EditRecoDetailDao dao = new EditRecoDetailDaoImpl(connection);		
				recoPeriodList = dao.getRecoPeriodList();
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return recoPeriodList;
		
		
	}
	
	public List validateExcel(InputStream inputstream) throws DPServiceException
	{
		
		List list = new ArrayList();
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			EditRecoDetailDao dao = new EditRecoDetailDaoImpl(connection);		
			
			list = dao.validateExcel(inputstream);
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
		return list;
	}
	public int updateRecoPeriod(List<RecoPeriodDTO> recoDtoList, int recoID, int gracePeriod) throws DPServiceException
	{
       int updateStatus = 0;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			EditRecoDetailDao updateRecoDao = new EditRecoDetailDaoImpl(connection);		
			updateStatus = updateRecoDao.updateRecoPeriod(recoDtoList, recoID,gracePeriod);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
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
				DBConnectionManager.releaseResources(connection);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
		}
		return updateStatus;
	}

}
