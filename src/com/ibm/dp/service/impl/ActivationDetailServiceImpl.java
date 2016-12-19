package com.ibm.dp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ActivationDetailReportDao;
import com.ibm.dp.dao.impl.ActivationDetailReportDaoImpl;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ActivationDetailReportService;



public class ActivationDetailServiceImpl implements ActivationDetailReportService
{
	private static  Logger logger = Logger.getLogger(ActivationDetailServiceImpl.class.getName());

	private static ActivationDetailReportService activationDetailReportService = new ActivationDetailServiceImpl();
	private ActivationDetailServiceImpl() {}
	public static ActivationDetailReportService getInstance() {
		return activationDetailReportService;
	}
	private ActivationDetailReportDao activationDetailReportDao = ActivationDetailReportDaoImpl.getInstance();

		
	public List<ActivationDetailReportDTO> getActivationDetailReport(ActivationDetailReportDTO activationDetailReportDTO) throws DPServiceException
	{
			List<ActivationDetailReportDTO> reportStockDataList = null;
			try {
				reportStockDataList = activationDetailReportDao.getActivationDetailReport(activationDetailReportDTO);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	
	
}
