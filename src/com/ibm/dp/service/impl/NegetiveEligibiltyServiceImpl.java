package com.ibm.dp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ActivationDetailReportDao;
import com.ibm.dp.dao.NegetiveEligibilityDao;
import com.ibm.dp.dao.impl.ActivationDetailReportDaoImpl;
import com.ibm.dp.dao.impl.NegetiveEligibilityDaoImpl;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ActivationDetailReportService;
import com.ibm.dp.service.NegetiveEligibiltyService;



public class NegetiveEligibiltyServiceImpl implements NegetiveEligibiltyService 
{
	private static  Logger logger = Logger.getLogger(NegetiveEligibiltyServiceImpl.class.getName());

	private static NegetiveEligibiltyService negetiveEligibiltyService = new NegetiveEligibiltyServiceImpl();
	private NegetiveEligibiltyServiceImpl() {}
	public static NegetiveEligibiltyService getInstance() {
		return negetiveEligibiltyService;
	}
	private NegetiveEligibilityDao negetiveEligibilityDao = NegetiveEligibilityDaoImpl.getInstance();

		
	public List<NegetiveEligibilityDTO> getNegetiveEligibilityReport(String circleIds) throws DPServiceException
	{
			List<NegetiveEligibilityDTO> reportStockDataList = null;
			try {
				reportStockDataList = negetiveEligibilityDao.getNegetiveEligibilityReport(circleIds);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	
	
}
