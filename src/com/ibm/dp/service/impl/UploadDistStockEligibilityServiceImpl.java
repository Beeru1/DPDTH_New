package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.UploadDistStockEligibilityDao;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dao.impl.UploadDistStockEligibilityDaoImpl;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.UploadDistStockEligibilityService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
public class UploadDistStockEligibilityServiceImpl implements
		UploadDistStockEligibilityService {
	private static Logger logger = Logger.getLogger(
			UploadDistStockEligibilityServiceImpl.class.getName());
	public List<ViewStockEligibilityDTO> getRegionList() throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		UploadDistStockEligibilityDao useDao = new UploadDistStockEligibilityDaoImpl();
		List<ViewStockEligibilityDTO> regionList = new ArrayList<ViewStockEligibilityDTO>();	
		try
		{
			regionList = useDao.getRegionList(); 
			logger.info("in service  getRegionList :"+regionList.size());
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}
		return regionList;
	}

	public List<ViewStockEligibilityDTO> getCircleList(int regionId) throws VirtualizationServiceException {
		
		UploadDistStockEligibilityDao useDao = new UploadDistStockEligibilityDaoImpl();
		List<ViewStockEligibilityDTO> circleList = null;
		try
		{
		circleList = useDao.getCircleList(regionId);
		logger.info("in service  getCircleList :"+circleList.size());
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}
		return circleList;
	}
	
	public String uploadExcel(InputStream inputstream,int productType,int distType,long dthadminid,int regionId) throws DPServiceException
	{
		String returnResult;
		UploadDistStockEligibilityDao bulkupload = null; 
		try
		{
			bulkupload = new UploadDistStockEligibilityDaoImpl();
			returnResult = bulkupload.uploadExcel(inputstream,productType,distType,dthadminid,regionId);
		}
		catch (Exception e) 
		{
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		//logger.error("returnResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+returnResult);
		return returnResult;
	}
	
}
