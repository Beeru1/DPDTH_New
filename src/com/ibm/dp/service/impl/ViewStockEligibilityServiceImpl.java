package com.ibm.dp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ViewStockEligibilityDAO;
import com.ibm.dp.dao.impl.ViewStockEligibilityDaoImpl;
import com.ibm.dp.dto.UploadedStockEligibilityDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.service.ViewStockEligibilityService;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class ViewStockEligibilityServiceImpl implements ViewStockEligibilityService
{

	private static Logger logger = Logger
			.getLogger(ViewStockEligibilityServiceImpl.class.getName());

	public List<ViewStockEligibilityDTO> getStockEligibility(int distID)
			throws VirtualizationServiceException
	{

		ViewStockEligibilityDAO vseDao = new ViewStockEligibilityDaoImpl();
		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();

		try
		{
			viewStockList = vseDao.getStockEligibility(distID);
			logger.info("in service  viewStockList :" + viewStockList.size());
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}

		return viewStockList;

	}// getStockEligibility

	public ViewStockEligibilityDTO getStockEligibilityMainDtl(int distID)
			throws VirtualizationServiceException
	{

		ViewStockEligibilityDAO vseDao = new ViewStockEligibilityDaoImpl();
		ViewStockEligibilityDTO viewStockList = new ViewStockEligibilityDTO();

		try
		{
			viewStockList = vseDao.getStockEligibilityMainDtl(distID);
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}

		return viewStockList;

	}

	/**
	 * 
	 * @param olmId
	 * @return
	 */
	public UploadedStockEligibilityDto getUploadedStockEligibility(String olmId)
			throws VirtualizationServiceException
	{
		UploadedStockEligibilityDto useDto = null;
		ViewStockEligibilityDAO vseDao = new ViewStockEligibilityDaoImpl();
		try
		{
			useDto = vseDao.getUploadedStockEligibility(olmId);
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}

		return useDto;
	}

	public List<ViewStockEligibilityDTO> getStockEligibilityAdmin(String strOLMId)
			throws VirtualizationServiceException
	{
		ViewStockEligibilityDAO vseDao = new ViewStockEligibilityDaoImpl();
		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();

		try
		{
			viewStockList = vseDao.getStockEligibilityAdminDoa(strOLMId);
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}

		return viewStockList;
	}

	public List<ViewStockEligibilityDTO> getUploadedEligibility(int distID)
			throws VirtualizationServiceException
	{

		ViewStockEligibilityDAO vseDao = new ViewStockEligibilityDaoImpl();
		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();

		try
		{
			viewStockList = vseDao.getStockEligibilityUploaded(distID);
			logger.info("in service  viewStockList :" + viewStockList.size());
		}
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VirtualizationServiceException(e.getMessage());
		}

		return viewStockList;

	}// getStockEligibility
}
