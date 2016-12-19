package com.ibm.reports.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dao.UploadTrailReportsDao;
import com.ibm.reports.dao.impl.GenericReportsDaoFactory;
import com.ibm.reports.dao.impl.GenericReportsDaoImpl;
import com.ibm.reports.dao.impl.UploadTrailReportsDaoFactory;
import com.ibm.reports.dao.impl.UploadtrailReportsDaoIml;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.UploadTrailReportDTO;
import com.ibm.reports.dto.UploadTrailReportsOutputDTO;
import com.ibm.reports.dto.UploadTrailReportsParameterDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.UploadTrailReportsService;
import com.ibm.virtualization.recharge.exception.DAOException;

public class UploadTrailReportsServiceImpl implements UploadTrailReportsService {
	private Logger logger = Logger.getLogger(UploadTrailReportsServiceImpl.class.getName());
	
	
	private static UploadTrailReportsService ReportsService = new UploadTrailReportsServiceImpl();

	private UploadTrailReportsServiceImpl()
	{
	}

	public static UploadTrailReportsService getInstance()
	{
		return ReportsService;
	}

	@Override
	public List<UploadTrailReportDTO> getUploadTypeList() throws DAOException, DPServiceException {
		// TODO Auto-generated method stub
		List<UploadTrailReportDTO> UploadTypeList = null;
		try
		{
			UploadTrailReportsDao reportsDao = UploadtrailReportsDaoIml.getInstance();
			UploadTypeList = reportsDao.getUploadTypeList();
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return UploadTypeList;
	}


	@Override
	public UploadTrailReportsOutputDTO exportToExcel(
			UploadTrailReportsParameterDTO trailReportParameterDTO)
	throws DPServiceException, DAOException {

		// TODO Auto-generated method stub

		logger
		.info("********************** exportToExcel() **************************************");
		UploadTrailReportsOutputDTO trailListDTO = new UploadTrailReportsOutputDTO();
		List<GenericReportsOutputDto> listReturn = new ArrayList<GenericReportsOutputDto>();
		try
		{
			UploadTrailReportsDao reportsDao = UploadTrailReportsDaoFactory.getUploadTrailReportDao(trailListDTO.getGroupId());
			
			trailListDTO = reportsDao.exportToExcel(trailReportParameterDTO);


		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}

		return trailListDTO;

	}
	
}
