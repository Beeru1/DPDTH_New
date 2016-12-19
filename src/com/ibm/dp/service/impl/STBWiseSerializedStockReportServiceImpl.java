package com.ibm.dp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.STBWiseSerializedStockReportDao;
import com.ibm.dp.dao.impl.STBWiseSerializedStockReportDaoImpl;
import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.STBWiseSerializedStockReportService;
import com.ibm.dpmisreports.common.SelectionCollection;



public class STBWiseSerializedStockReportServiceImpl implements STBWiseSerializedStockReportService
{
	private static  Logger logger = Logger.getLogger(STBWiseSerializedStockReportServiceImpl.class.getName());

	private static STBWiseSerializedStockReportService stbWiseSerializedStockReportService = new STBWiseSerializedStockReportServiceImpl();
	private STBWiseSerializedStockReportServiceImpl() {}
	public static STBWiseSerializedStockReportService getInstance() {
		return stbWiseSerializedStockReportService;
	}
	private STBWiseSerializedStockReportDao stbWiseSerializedStockReportDao = STBWiseSerializedStockReportDaoImpl.getInstance();
	
		
	public List<STBWiseSerializedStockReportDTO> getSTBWiseSerializedStockReport(STBWiseSerializedStockReportDTO stDTO) throws DPServiceException
	{
			List<STBWiseSerializedStockReportDTO> reportStockDataList = null;
			try {
				reportStockDataList = stbWiseSerializedStockReportDao.getSTBWiseSerializedStockReport(stDTO);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	public List<SelectionCollection> getSTBStatusList() throws DPServiceException
	{
			List<SelectionCollection> stbStatusList = null;
			try {
				stbStatusList = stbWiseSerializedStockReportDao.getSTBStatusList();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return stbStatusList;
	}
	
	
}
